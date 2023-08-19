package rentalSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 会員管理用DBアクセスクラス
 *
 * レコード用のオブジェクトを兼ねている
 *
 */
public class MemberDBAccess {

	private String memberNumber; // 会員番号
	private String memberName; // 会員名
	private String memberAddress; // 住所
	private String memberAge; // 年齢
	private String memberTel; // 電話番号
	private String memberMail; // メールアドレス
	private String enterDay; // 入会日
	private String leaveDay; // 退会日
	private String rankDiv; // 会員優良区分

	private final String url;
	private final String userName;
	private final String password;

	/**
	 * コンストラクタ
	 *
	 * @param url データベース接続URL
	 * @param userName ユーザ名
	 * @param password パスワード
	 */
	public MemberDBAccess(String url, String userName, String password) {

		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * 会員を新規登録する
	 *
	 * @param memberNumber   会員番号
	 * @param memberName     氏名
	 * @param memberAddress  住所
	 * @param memberAge      年齢
	 * @param memberTel      電話番号
	 * @param memberMail     メールアドレス
	 * @param entryDay       入会日
	 * @param rankDiv        区分
	 *
	 * @return 登録結果
	 */
	public int addMember(String memberNumber, String memberName, String memberAddress, String memberAge,
			String memberTel, String memberMail, String entryDay, String rankDiv) {

		StringBuffer sql = new StringBuffer(0);

		sql.append("INSERT INTO ");
		sql.append("MEMBER_TABLE");
		sql.append(
				"(member_num, member_name, member_address, member_age, member_tel, member_mail, enter_day, leave_day, rank_div) ");
		sql.append("VALUES ");
		sql.append("(?, ?, ?, ?, ?, ?, ?,  null, ?)");

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

			preparedStatement.setString(1, memberNumber);
			preparedStatement.setString(2, memberName);
			preparedStatement.setString(3, memberAddress);
			preparedStatement.setString(4, memberAge);
			preparedStatement.setString(5, memberTel);
			preparedStatement.setString(6, memberMail);
			preparedStatement.setString(7, entryDay);
			preparedStatement.setString(8, rankDiv);

			return preparedStatement.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return 0;
	}

	/**
	 * 会員情報を更新する
	 *
	 * @param memberNumber 更新する会員番号
	 * @param memberName 新しい氏名（変更しない場合はnullまたは空文字）
	 * @param memberAddress 新しい住所（変更しない場合はnullまたは空文字）
	 * @param memberAge 新しい年齢（変更しない場合はnullまたは空文字）
	 * @param memberTel 新しい電話番号（変更しない場合はnullまたは空文字）
	 * @param memberMail 新しいメールアドレス（変更しない場合はnullまたは空文字）
	 * @param entryDay 新しい入会日（変更しない場合はnullまたは空文字）
	 * @param leaveDay 新しい退会日（変更しない場合はnullまたは空文字）
	 * @param rankDiv 新しい区分（変更しない場合はnullまたは空文字）
	 *
	 * @return 更新結果
	 */
	public int updateMember(String memberNumber, String memberName, String memberAddress, String memberAge,
			String memberTel, String memberMail, String entryDay, String leaveDay, String rankDiv) {

		StringBuffer sql = new StringBuffer(0);
		ArrayList<Object> parameterList = new ArrayList<Object>();

		sql.append("UPDATE ");
		sql.append("MEMBER_TABLE ");
		sql.append("SET ");

		if (memberName != null && !memberName.equals("")) {

			sql.append(" member_name = ?,");
			parameterList.add(memberName);
		}

		if (memberAddress != null && !memberAddress.equals("")) {

			sql.append(" member_address = ?,");
			parameterList.add(memberAddress);
		}

		if (memberAge != null && !memberAge.equals("")) {

			sql.append(" member_age = ?,");
			parameterList.add(Integer.valueOf(memberAge));
		}

		if (memberTel != null && !memberTel.equals("")) {

			sql.append(" member_tel = ?,");
			parameterList.add(memberTel);
		}

		if (memberMail != null && !memberMail.equals("")) {

			sql.append(" member_mail = ?,");
			parameterList.add(memberMail);
		}

		if (entryDay != null && !entryDay.equals("")) {

			sql.append(" enter_day = ?,");
			parameterList.add(entryDay);
		}

		if (leaveDay != null && !leaveDay.equals("")) {

			sql.append(" leave_day = ?,");
			parameterList.add(leaveDay);
		}

		if (rankDiv != null && !rankDiv.equals("")) {

			sql.append(" rank_div = ?,");
			parameterList.add(rankDiv);
		}

		// 最後の,をはずす
		String wStr = sql.toString();
		if (wStr.endsWith(",")) {
			sql.delete(sql.length() - 1, sql.length());
		}

		sql.append(" WHERE ");
		sql.append("member_num = ?");

		parameterList.add(memberNumber);

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

			for (int i = 0; i < parameterList.size(); i++) {

				Object parameter = parameterList.get(i);

				preparedStatement.setObject(i + 1, parameter);
			}

			return preparedStatement.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return 0;
	}

	/**
	 * 会員削除メソッド
	 *
	 * 退会日を設定するUpdateを呼び出す
	 *
	 * @param memberNumber 会員番号
	 * @param leaveDay 退会日
	 *
	 * @return 削除結果
	 */
	public int deleteMember(String memberNumber, String leaveDay) {
		return updateMember(memberNumber, null, null, null, null, null, null, leaveDay, null);
	}

	/**
	 * 数値を受け取り、希望の長さまで先頭を0で埋めた 文字列として返す内部メソッド
	 *
	 * @param value 0埋めして変換したい数値
	 * @param length 希望の長さ
	 *
	 * @return 希望の長さまで先頭を0で埋めた文字列
	 */
	private String zeroPad(int value, int length) {

		String result = String.valueOf(value);

		while (result.length() < length) {
			result = "0" + result;
		}

		return result;
	}

	/**
	 * 新しい会員番号を作成する
	 *
	 * 新しい会員番号は、現在の会員番号の最大値＋1となる。
	 *
	 * @return 新しい会員番号（エラーが発生した場合はnull）
	 */
	public String getNewMemberNumber() {

		StringBuffer sql = new StringBuffer(0);
		sql.append("SELECT ");
		sql.append("member_num ");
		sql.append("FROM ");
		sql.append("MEMBER_TABLE ");
		sql.append("ORDER BY ");
		sql.append("member_num DESC");

		// 現在の会員番号の最大値
		String max;

		// SELECT文の実行
		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());) {

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (resultSet.next()) {
					max = resultSet.getString("member_num");
				} else {
					max = "00000";
				}
			}

		} catch (SQLException exception) {

			exception.printStackTrace();

			return null;
		}

		// 新しい会員番号を作成
		int newMeNumber = Integer.parseInt(max) + 1;

		// 5桁になるように0埋め
		return zeroPad(newMeNumber, 5);
	}

	/**
	 * 会員番号をもとに会員情報を設定する。
	 *
	 * 設定した会員情報は別途getメソッドを利用して取得する
	 *
	 * @param memberNumber 会員番号
	 *
	 * @return 会員情報が設定できた場合は true、設定できなかった場合は fales
	 */
	public boolean setMember(String memberNumber) {

		StringBuffer sql = new StringBuffer(0);
		sql.append("SELECT ");
		sql.append("* ");
		sql.append("FROM ");
		sql.append("MEMBER_TABLE ");
		sql.append("WHERE ");
		sql.append("member_num = ?");

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

			preparedStatement.setString(1, memberNumber);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (!resultSet.next()) {
					return false;
				}

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

				memberName = resultSet.getString("member_name");
				memberAddress = resultSet.getString("member_address");
				memberAge = resultSet.getString("member_age");
				memberTel = resultSet.getString("member_tel");
				memberMail = resultSet.getString("member_mail");

				Date insertDate = resultSet.getDate("enter_day");
				if (insertDate != null) {
					enterDay = sdf.format(insertDate);
				} else {
					enterDay = null;
				}

				Date deleteDate = resultSet.getDate("leave_day");
				if (deleteDate != null) {
					leaveDay = sdf.format(deleteDate);
				} else {
					leaveDay = null;
				}

				rankDiv = resultSet.getString("rank_div");

				return true;
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @return memberAddress
	 */
	public String getMemberAddress() {
		return memberAddress;
	}

	/**
	 * @return memberAge
	 */
	public String getMemberAge() {
		return memberAge;
	}

	/**
	 * @return memberDelete
	 */
	public String getMemberDelete() {
		return leaveDay;
	}

	/**
	 * @return memberInsert
	 */
	public String getMemberInsert() {
		return enterDay;
	}

	/**
	 * @return memberMail
	 */
	public String getMemberMail() {
		return memberMail;
	}

	/**
	 * @return memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @return memberNumber
	 */
	public String getMemberNumber() {
		return memberNumber;
	}

	/**
	 * @return memberRank
	 */
	public String getMemberRank() {
		return rankDiv;
	}

	/**
	 * @return memberTel
	 */
	public String getMemberTel() {
		return memberTel;
	}
}
