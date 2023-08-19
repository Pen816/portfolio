package rentalSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 返却用DBアクセスクラス
 */
public class ReturnDBAccess {

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
	public ReturnDBAccess(String url, String userName, String password) {

		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * 作品番号をもとに作品名を取得する
	 *
	 * @param artworkNumber
	 *
	 * @return 作品名 (取得失敗時はnull)
	 */
	public String getArtworkName(String artworkNumber) {

		// 作品名の取得
		StringBuffer sql = new StringBuffer(0);
		sql.append("SELECT ");
		sql.append("artwork_name, recent_div ");
		sql.append("FROM ");
		sql.append("ARTWORK_TABLE ");
		sql.append("WHERE ");
		sql.append("artwork_num = ?");

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());) {

			preparedStatement.setString(1, artworkNumber);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (!resultSet.next()) {
					return null;
				}

				return resultSet.getString("artwork_name");
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return null;
	}

	/**
	 * 作品番号と連番の組み合わせをもとに
	 * 貸出テーブルから返却期限を取得する
	 * 前提:返却日がNULLなら現在貸出中
	 *
	 * @param artworkNumber 作品番号
	 * @param mediaNumber 媒体番号
	 *
	 * @return 返却期限 (取得失敗時はnull)
	 */
	public Date getTermDay(String artworkNumber, String mediaNumber) {

		StringBuffer sql = new StringBuffer(0);
		sql.append("SELECT ");
		sql.append("term_day ");
		sql.append("FROM ");
		sql.append("RENTAL_TABLE ");
		sql.append("WHERE ");
		sql.append("artwork_num = ? ");
		sql.append("AND media_num = ?");
		sql.append("AND check_in_day IS NULL");

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());) {

			preparedStatement.setString(1, artworkNumber);
			preparedStatement.setString(2, mediaNumber);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (!resultSet.next()) {
					return null;
				}

				return resultSet.getDate("term_day");
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return null;
	}

	/**
	 * 作品番号をもとに、その作品１日あたりの
	 * 延滞料金を取得する
	 *
	 * @param artworkNumber 作品番号
	 *
	 * @return 延滞料金
	 */
	public int getArrearsPerUnitDay(String artworkNumber) {

		StringBuffer sql1 = new StringBuffer(0);
		sql1.append("SELECT ");
		sql1.append("recent_div ");
		sql1.append("FROM ");
		sql1.append("ARTWORK_TABLE ");
		sql1.append("WHERE ");
		sql1.append("artwork_num = ?");

		String recentDiv = null;

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql1.toString());) {

			preparedStatement.setString(1, artworkNumber);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (!resultSet.next()) {
					return 0;
				}

				recentDiv = resultSet.getString("recent_div");
			}

		} catch (SQLException exception) {

			exception.printStackTrace();

			return 0;
		}

		StringBuffer sql2 = new StringBuffer(0);
		sql2.append("SELECT arrears FROM PRICE_TABLE ");
		sql2.append("WHERE recent_div = ?");

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql2.toString());) {

			preparedStatement.setString(1, recentDiv);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (!resultSet.next()) {
					return 0;
				}

				return resultSet.getInt("arrears");
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return 0;
	}

	/**
	 * 作品番号と連番の組み合わせをもとに
	 * 貸出テーブルから会員番号を取得する
	 * 前提:返却日がNULLなら現在貸出中
	 *
	 * @param artworkNumber 作品番号
	 * @param mediaNumber 媒体番号
	 *
	 * @return 借りている会員の番号 (取得失敗時はnull)
	 */
	public String getBorrowersNumber(String artworkNumber, String mediaNumber) {

		StringBuffer sql = new StringBuffer(0);
		sql.append("SELECT ");
		sql.append("member_num ");
		sql.append("FROM ");
		sql.append("RENTAL_TABLE ");
		sql.append("WHERE ");
		sql.append("artwork_num = ? ");
		sql.append("AND media_num = ? ");
		sql.append("AND check_in_day IS NULL");

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());) {

			preparedStatement.setString(1, artworkNumber);
			preparedStatement.setString(2, mediaNumber);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (!resultSet.next()) {
					return null;
				}

				return resultSet.getString("member_num");
			}

		} catch (SQLException exception) {

			exception.printStackTrace();
		}

		return null;
	}

	/**
	 * 作品番号と連番の組み合わせをもとに
	 * 貸出テーブルから貸出番号を取得する
	 * 前提:返却日がNULLなら現在貸出中
	 *
	 * @param artworkNumber 作品番号
	 * @param mediaNumber 連番
	 * @return 会員番号
	 */
	public String getRentalNumber(String artworkNumber, String mediaNumber) {

		StringBuffer sql = new StringBuffer(0);
		sql.append("SELECT ");
		sql.append("rental_num ");
		sql.append("FROM ");
		sql.append("RENTAL_TABLE ");
		sql.append("WHERE ");
		sql.append("artwork_num = ? ");
		sql.append("AND media_num = ? ");
		sql.append("AND check_in_day is NULL");

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());) {

			preparedStatement.setString(1, artworkNumber);
			preparedStatement.setString(2, mediaNumber);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (!resultSet.next()) {
					return null;
				}

				return resultSet.getString("rental_num");
			}

		} catch (SQLException exception) {

			exception.printStackTrace();
		}

		return null;
	}

	/**
	 * 返却処理
	 *
	 * @param rentalNumber 貸出番号
	 * @param checkInDay 返却日
	 * @param arrears 延滞料金
	 *
	 * @return
	 */
	public int checkIn(String rentalNumber, String checkInDay, String arrears) {

		StringBuffer sql = new StringBuffer(0);
		sql.append("UPDATE ");
		sql.append("RENTAL_TABLE ");
		sql.append("SET ");
		sql.append("check_in_day = ? ");

		// 延滞がある場合
		if (arrears != null && !arrears.equals("")) {
			sql.append(", arrears = ? ");
		}

		sql.append("WHERE ");
		sql.append("rental_num = ?");

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());) {

			preparedStatement.setString(1, checkInDay);

			if (arrears != null && !arrears.equals("")) {
				// 延滞がある場合

				preparedStatement.setString(2, arrears);
				preparedStatement.setString(3, rentalNumber);

			} else {
				// 延滞がない場合

				preparedStatement.setString(2, rentalNumber);
			}

			// SQLを実行
			return preparedStatement.executeUpdate();

		} catch (SQLException exception) {

			exception.printStackTrace();
		}

		return 0;
	}
}
