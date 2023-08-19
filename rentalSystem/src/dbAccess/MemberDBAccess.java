package dbAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MemberDBAccess {

	private String memberNum; // 会員番号
	private String memberName; // 会員名
	private String memberAddress; // 住所
	private String memberAge; // 年齢
	private String memberTel; // 電話番号
	private String memberMail; // メールアドレス
	private String enterDay; // 入会日
	private String leaveDay; // 退会日
	private String category; // 会員優良区分

	private final String url;
	private final String name;
	private final String pass;

	public MemberDBAccess(String url, String name, String pass) {

		this.url = url;
		this.name = name;
		this.pass = pass;
	}

	//	新たな会員番号作成
	public String getNewMemberNum() {

		// 現在の会員番号の最大値
		String max;

		//	会員番号の最大値を取得
		String sql = "SELECT member_num FROM MEMBER_TABLE ORDER BY member_num DESC";
		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());) {

			ResultSet result = preparedStatement.executeQuery();

			//	resultに値が入っていればtrue、なければfalse
			if (result.next()) {
				//	maxに会員番号の最大値を代入
				max = result.getString("member_num");
			} else {
				max = "00000";
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}

		// 新しい会員番号を作成
		int newMeNum = Integer.parseInt(max) + 1;

		// 5桁になるように0埋め
		String zeroPadNum = String.valueOf(newMeNum); //	int型からString型へ
		while (zeroPadNum.length() < 5) { //	5ケタに満たない桁数分0埋め
			zeroPadNum = "0" + zeroPadNum;
		}

		return zeroPadNum;
	}

	//	会員番号をもとに会員情報設定
	public boolean setMember(String memberNum) {

		String sql = "SELECT * FROM MEMBER_TABLE WHERE member_num = ?";
		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

			preparedStatement.setString(1, memberNum);

			ResultSet resultSet = preparedStatement.executeQuery();

			//	会員情報を取得できなければfalseを返す
			if (!resultSet.next()) {
				return false;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

			//	会員情報を設定
			memberName = resultSet.getString("member_name");
			memberAddress = resultSet.getString("member_address");
			memberAge = resultSet.getString("member_age");
			memberTel = resultSet.getString("member_tel");
			memberMail = resultSet.getString("member_mail");

			Date insertDate = resultSet.getDate("enter_day");
			enterDay = sdf.format(insertDate);

			Date deleteDate = resultSet.getDate("leave_day");
			if (deleteDate != null) {
				leaveDay = sdf.format(deleteDate);
			} else {
				leaveDay = null;
			}

			category = resultSet.getString("rank_div");

			return true;

		} catch (SQLException exception) {
			exception.printStackTrace();
			return false;
		}
	}

	//	会員新規登録処理
	public int addMember(String memberNum, String memberName, String memberAddress, String memberAge,
			String memberTel, String memberMail, String entryDay, String rankDiv) {

		String sql = "INSERT INTO MEMBER_TABLE member_num, member_name, member_address, member_age, member_tel, member_mail, enter_day, leave_day, rank_div) VALUES (?, ?, ?, ?, ?, ?, ?,  null, ?)";

		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

			preparedStatement.setString(1, memberNum);
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

	//	会員情報更新処理
	public int updateMember(String memberNum, String memberName, String memberAddress, String memberAge,
			String memberTel, String memberMail, String entryDay, String leaveDay, String category) {

		StringBuffer sql = new StringBuffer(0);
		ArrayList<Object> parameterList = new ArrayList<Object>();

		sql.append("UPDATE MEMBER_TABLE SET ");

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

		if (category != null && !category.equals("")) {

			sql.append(" rank_div = ?,");
			parameterList.add(category);
		}

		// 最後の,をはずす
		String wStr = sql.toString(); //	String型に変換
		if (wStr.endsWith(",")) {
			sql.delete(sql.length() - 1, sql.length());
		}

		//	登録する会員の会員番号を指定
		sql.append(" WHERE member_num = ?");
		parameterList.add(memberNum);

		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

			//	値をセットし
			for (int i = 0; i < parameterList.size(); i++) {
				Object parameter = parameterList.get(i);
				preparedStatement.setObject(i + 1, parameter);
			}

			//	SQL文実行
			return preparedStatement.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return 0;
	}

	//	会員削除処理（退会日を設定するupdateMemberメソッド）
	public int deleteMember(String memberNumber, String leaveDay) {
		return updateMember(memberNumber, null, null, null, null, null, null, leaveDay, null);
	}

	public String getMemberNum() {
		return memberNum;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public String getMemberAge() {
		return memberAge;
	}

	public String getMemberTel() {
		return memberTel;
	}

	public String getMemberMail() {
		return memberMail;
	}

	public String getMemberEnterDay() {
		return enterDay;
	}

	public String getMemberLeaveDay() {
		return leaveDay;
	}

	public String getMemberCategory() {
		return category;
	}

}