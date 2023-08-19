package dbAccess;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturnDBAccess {
	private final String url;
	private final String name;
	private final String pass;

	public ReturnDBAccess(String url, String name, String pass) {

		this.url = url;
		this.name = name;
		this.pass = pass;
	}

	//	作品番号より作品名取得
	public String getArtworkName(String artworkNum) {

		// 作品名の取得
		String sql = "SELECT artwork_name FROM ARTWORK_TABLE WHERE artwork_num = ?";
		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

			preparedStatement.setString(1, artworkNum);
			ResultSet result = preparedStatement.executeQuery();

			//	resultSetに値が入っていればtrue、なければfalse
			if (result.next()) {
				//	結果を返す
				return result.getString(1);

			} else {

				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	//	作品番号、枝番より返却期限取得(返却日がNULLの場合は現在貸出中)
	public Date getReturnLimit(String artworkNum, String mediaNum) {

		// 返却期限の取得
		String sql = "SELECT term_day FROM RENTAL_TABLE WHERE artwork_num = ? AND media_num = ? AND check_in_day IS NULL";
		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

			preparedStatement.setString(1, artworkNum);
			preparedStatement.setString(2, mediaNum);

			ResultSet result = preparedStatement.executeQuery();

			//	resultに値が入っていればtrue、なければfalse
			if (result.next()) {
				//	結果を返す
				return result.getDate("term_day");
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	//	作品番号より１日当たりの延滞料金取得
	public int getArrearsPrice(String artworkNum) {

		// 延滞料金取得
		String sql = "SELECT arrears_par_unit_day FROM ARTWORK_TABLE,PRICE_TABLE WHERE ARTWORK_TABLE.recent_div=PRICE_TABLE.recent_div and artwork_num = ?";
		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

			preparedStatement.setString(1, artworkNum);

			ResultSet result = preparedStatement.executeQuery();

			//	resultに値が入っていればtrue、なければfalse
			if (result.next()) {
				//	結果を返す
				return result.getInt("arrears_par_unit_day");
			} else {
				return 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;

		}
	}

	//	作品番号、枝番よりレンタルしている会員番号取得(返却日がNULLの場合は現在貸出中)
	public String getRentalMemberNum(String artworkNum, String mediaNum) {

		// 会員番号取得
		String sql = "SELECT member_num FROM RENTAL_TABLE WHERE artwork_num = ? AND media_num = ? AND check_in_day IS NULL";
		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

			preparedStatement.setString(1, artworkNum);
			preparedStatement.setString(2, mediaNum);

			ResultSet result = preparedStatement.executeQuery();

			//	resultに値が入っていればtrue、なければfalse
			if (result.next()) {
				//	結果を返す
				return result.getString("member_num");
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	//	作品番号、枝番より貸出番号取得(返却日がNULLの場合は現在貸出中)
	public String getRentalNum(String artworkNum, String mediaNum) {

		// 貸出番号取得
		String sql = "SELECT rental_num FROM RENTAL_TABLE WHERE artwork_num = ? AND media_num = ? AND check_in_day IS NULL";
		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

			preparedStatement.setString(1, artworkNum);
			preparedStatement.setString(2, mediaNum);

			ResultSet result = preparedStatement.executeQuery();

			//	resultに値が入っていればtrue、なければfalse
			if (result.next()) {
				//	結果を返す
				return result.getString("rental_num");
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	//	返却処理(貸出番号、返却日、延滞料金)
	public int returnSystem(String rentalNum, String returnDay, String arrearsPrice) {

		// 返却日更新
		String sql = "UPDATE RENTAL_TABLE SET check_in_day = ? WHERE rental_num = ?";
		// 延滞がある場合
		if (arrearsPrice != null && !arrearsPrice.equals("")) {
			sql = "UPDATE RENTAL_TABLE SET check_in_day = ?, arrears = ?  WHERE rental_num = ?";
		}

		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

			preparedStatement.setString(1, returnDay);
			preparedStatement.setString(2, rentalNum);
			// 延滞がある場合
			if (arrearsPrice != null && !arrearsPrice.equals("")) {
				preparedStatement.setString(1, returnDay);
				preparedStatement.setString(2, arrearsPrice);
				preparedStatement.setString(3, rentalNum);
			}

			return preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
