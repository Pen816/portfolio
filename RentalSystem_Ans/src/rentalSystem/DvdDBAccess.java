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
 * DVD管理用データベースアクセスクラス
 */
public class DvdDBAccess {

	private final String url;
	private final String userName;
	private final String password;

	/**
	 * コンストラクタ
	 *
	 * データベースの接続情報を設定する。
	 *
	 * @param url データベース接続URL
	 * @param userName ユーザ名
	 * @param password パスワード
	 */
	public DvdDBAccess(String url, String userName, String password) {

		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * 作品番号から作品情報を取得する
	 *
	 * 失敗時はnullを返す。
	 *
	 * @param artworkNumber 作品番号
	 *
	 * @return 作品情報
	 */
	public ArtworkObj getArtwork(String artworkNumber) {

		StringBuilder sql = new StringBuilder(0);
		sql.append("SELECT ");
		sql.append("aw.artwork_num, aw.artwork_name, aw.recent_div, g.genre_name ");
		sql.append("FROM ");
		sql.append("ARTWORK_TABLE aw, GENRE_TABLE g ");
		sql.append("WHERE ");
		sql.append("aw.genre_num = g.genre_num ");
		sql.append("AND ");
		sql.append("aw.artwork_num = ?");

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

			preparedStatement.setString(1, artworkNumber);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (!resultSet.next()) {
					return null;
				}

				// 作品情報
				ArtworkObj artworkObj = new ArtworkObj();

				artworkObj.setArtworkNumber(resultSet.getString(1));
				artworkObj.setArtworkName(resultSet.getString(2));
				artworkObj.setRecentDiv(resultSet.getString(3));
				artworkObj.setGenreName(resultSet.getString(4));

				return artworkObj;
			}

		} catch (SQLException exception) {

			exception.printStackTrace();

			return null;
		}
	}

	/**
	 * データベースからDVDテーブルのレコードを取得する
	 *
	 * 取得したレコードの情報は対応するオブジェクトに保存する。
	 *
	 * @param artworkNumber 作品番号
	 * @param mediaNumber 媒体番号
	 *
	 * @return DVDテーブルのレコード情報
	 */
	public DvdObj getDvdRecord(String artworkNumber, String mediaNumber) {

		StringBuilder sql = new StringBuilder(0);

		sql.append("SELECT ");
		sql.append("artwork_num, media_num, entry_day, disuse_day ");
		sql.append("FROM ");
		sql.append("DVD_TABLE ");
		sql.append("WHERE ");
		sql.append("artwork_num = ? AND media_num = ?");

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

			preparedStatement.setString(1, artworkNumber);
			preparedStatement.setString(2, mediaNumber);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (!resultSet.next()) {
					return null;
				}

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

				DvdObj dvdObj = new DvdObj();
				dvdObj.setArtworkNumber(resultSet.getString("artwork_num"));
				dvdObj.setMediaNumber(resultSet.getString("media_num"));

				Date entryDate = resultSet.getDate("entry_day");
				if (entryDate != null) {
					dvdObj.setEntryDay(sdf.format(entryDate));
				}

				Date disuseDate = resultSet.getDate("disuse_day");
				if (disuseDate != null) {
					dvdObj.setDisuseDay(sdf.format(disuseDate));
				}

				return dvdObj;
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return null;

	}

	/**
	 * 作品番号をもとに新しいDVDの媒体番号を取得する
	 *
	 * 新しいDVDの媒体番号は、現在登録されている媒体番号の最大値+1とする。
	 *
	 * @param artworkNumber 作品番号
	 *
	 * @return 新しいDVDの媒体番号
	 */
	public String getNextMediaNumber(String artworkNumber) {

		StringBuilder sql = new StringBuilder(0);

		sql.append("SELECT ");
		sql.append("media_num ");
		sql.append("FROM ");
		sql.append("DVD_TABLE ");
		sql.append("WHERE ");
		sql.append("artwork_num = ? ");
		sql.append("ORDER BY ");
		sql.append("media_num DESC");

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

			preparedStatement.setString(1, artworkNumber);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				int max;

				if (resultSet.next()) {
					max = Integer.parseInt(resultSet.getString("media_num"));
				} else {
					max = 0;
				}

				int newMediaNumber = max + 1;

				return zeroPad(newMediaNumber, 2);

			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return null;
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
	 * DVDをデータベースに登録する
	 *
	 * @param artworkNumber 作品番号
	 * @param mediaNumber 媒体番号
	 * @param entryDate 入荷日
	 *
	 * @return データベースへの登録結果
	 */
	public int entryDvd(String artworkNumber, String mediaNumber, String entryDate) {

		StringBuilder sql = new StringBuilder(0);
		sql.append("INSERT ");
		sql.append("INTO ");
		sql.append("DVD_TABLE ");
		sql.append("(artwork_num, media_num, entry_day) ");
		sql.append("VALUES ");
		sql.append("(?, ?, ?)");

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

			preparedStatement.setString(1, artworkNumber);
			preparedStatement.setString(2, mediaNumber);
			preparedStatement.setString(3, entryDate);

			return preparedStatement.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return 0;
	}

	/**
	 * データベースの作品情報を更新する
	 *
	 * @param artworkObj 作品情報（nullまたは空文字の情報は更新しない）
	 *
	 * @return データベースの更新結果
	 */
	public int updateArtwork(ArtworkObj artworkObj) {

		// ジャンル名からジャンル番号を取得する
		StringBuilder sql1 = new StringBuilder(0);
		sql1.append("SELECT ");
		sql1.append("genre_num ");
		sql1.append("FROM ");
		sql1.append("GENRE_TABLE ");
		sql1.append("WHERE ");
		sql1.append("genre_name = ? ");

		String genreNumber;
		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql1.toString());) {

			preparedStatement.setString(1, artworkObj.getGenreName());

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (!resultSet.next()) {
					return 0;
				}

				genreNumber = resultSet.getString("genre_num");
			}

		} catch (SQLException exception) {

			exception.printStackTrace();

			return 0;
		}

		// 作品情報を更新する
		StringBuilder sql2 = new StringBuilder(0);
		ArrayList<String> parameterList = new ArrayList<String>();

		sql2.append("UPDATE ");
		sql2.append("ARTWORK_TABLE ");
		sql2.append("SET ");

		if (artworkObj.getArtworkName() != null && !artworkObj.getArtworkName().equals("")) {

			sql2.append("artwork_name = ?,");
			parameterList.add(artworkObj.getArtworkName());
		}

		if (artworkObj.getRecentDiv() != null && !artworkObj.getRecentDiv().equals("")) {

			sql2.append(" recent_div = ?,");
			parameterList.add(artworkObj.getRecentDiv());
		}

		if (genreNumber != null && !genreNumber.equals("")) {

			sql2.append(" genre_num = ?,");
			parameterList.add(genreNumber);
		}

		// 最後の「,」を削除する
		if (sql2.toString().endsWith(",")) {
			sql2.delete(sql2.length() - 1, sql2.length());
		}

		sql2.append(" WHERE ");
		sql2.append("artwork_num = ?");
		parameterList.add(artworkObj.getArtworkNumber());

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql2.toString())) {

			for (int i = 0; i < parameterList.size(); i++) {

				String parameter = parameterList.get(i);

				preparedStatement.setString(i + 1, parameter);
			}

			return preparedStatement.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return 0;
	}

	/**
	 * DVD情報を更新する
	 *
	 * @param dvdObj DVDレコード情報（nullまたは空文字の情報は更新しない）
	 *
	 * @return データベースの更新結果
	 */
	public int updateDvd(DvdObj dvdObj) {

		StringBuilder sql = new StringBuilder(0);
		ArrayList<String> parameterList = new ArrayList<String>();

		sql.append("UPDATE ");
		sql.append("DVD_TABLE ");
		sql.append("SET ");

		if (dvdObj.getEntryDay() != null && !dvdObj.getEntryDay().equals("")) {

			sql.append("entry_day = ?,");
			parameterList.add(dvdObj.getEntryDay());
		}

		if (dvdObj.getDisuseDay() != null && !dvdObj.getDisuseDay().equals("")) {

			sql.append("disuse_day = ?,");
			parameterList.add(dvdObj.getDisuseDay());
		}

		// 最後の「,」を削除する
		if (sql.toString().endsWith(",")) {
			sql.delete(sql.length() - 1, sql.length());
		}

		sql.append(" WHERE ");
		sql.append("artwork_num = ? AND media_num = ?");

		parameterList.add(dvdObj.getArtworkNumber());
		parameterList.add(dvdObj.getMediaNumber());

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

			for (int i = 0; i < parameterList.size(); i++) {

				String parameter = parameterList.get(i);

				preparedStatement.setString(i + 1, parameter);
			}

			return preparedStatement.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return 0;
	}
}
