package dbAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import object.ArtworkObj;
import object.DvdObj;

public class DvdDBAccess {

	private final String url;
	private final String name;
	private final String pass;

	public DvdDBAccess(String url, String name, String pass) {

		this.url = url;
		this.name = name;
		this.pass = pass;
	}

	//	作品番号から作品情報取得
	public ArtworkObj getArtwork(String artworkNumber) {
		String sql = "SELECT aw.artwork_num, aw.artwork_name, aw.recent_div, g.genre_name FROM ARTWORK_TABLE aw, GENRE_TABLE g WHERE aw.genre_num = g.genre_num AND aw.artwork_num = ?";

		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

			preparedStatement.setString(1, artworkNumber);
			ResultSet result = preparedStatement.executeQuery();

			//	resultに値が入っていなければ
			if (!result.next()) {
				return null;
			}

			// 作品情報オブジェクト
			ArtworkObj artworkObj = new ArtworkObj();
			artworkObj.setArtworkNum(result.getString(1));
			artworkObj.setArtworkName(result.getString(2));
			artworkObj.setRecentDiv(result.getString(3));
			artworkObj.setGenreName(result.getString(4));
			return artworkObj;

		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	//	作品番号より新規媒体番号取得
	public String getNextMediaNum(String artworkNum) {

		String sql = "SELECT media_num FROM DVD_TABLE WHERE artwork_num = ? ORDER BY media_num DESC";

		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

			preparedStatement.setString(1, artworkNum);
			ResultSet result = preparedStatement.executeQuery();

			int max;
			//	resultに値が入っている場合
			if (result.next()) {
				max = Integer.parseInt(result.getString("media_num"));
			} else {
				max = 0;
			}

			//	新規媒体番号の先頭を0で埋める
			String newMediaNum = String.valueOf(max + 1);
			while (newMediaNum.length() < 2) {
				newMediaNum = "0" + newMediaNum;
			}

			return newMediaNum;

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	//	作品番号・媒体番号よりDVDテーブルのレコード情報取得
	public DvdObj getDvdRecord(String artworkNum, String mediaNum) {

		String sql = "SELECT artwork_num, media_num, entry_day, disuse_day FROM DVD_TABLE WHERE artwork_num = ? AND media_num = ?";

		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

			preparedStatement.setString(1, artworkNum);
			preparedStatement.setString(2, mediaNum);
			ResultSet result = preparedStatement.executeQuery();

			//	resultに値が入っていなければ
			if (!result.next()) {
				return null;
			}

			//	データフォーマット指定
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

			//	データベースより取得したデータをDVDテーブルのレコードオブジェクトへ
			DvdObj dvdObj = new DvdObj();
			dvdObj.setArtworkNumber(result.getString("artwork_num"));
			dvdObj.setMediaNumber(result.getString("media_num"));

			Date entryDate = result.getDate("entry_day");
			if (entryDate != null) {
				dvdObj.setEntryDay(sdf.format(entryDate));
			}

			Date disuseDate = result.getDate("disuse_day");
			if (disuseDate != null) {
				dvdObj.setDisuseDay(sdf.format(disuseDate));
			}

			return dvdObj;

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return null;

	}

	//	DVDをデータベースに登録
	public int entryDvd(String artworkNum, String mediaNum, String entryDate) {

		String sql = "INSERT INTO DVD_TABLE (artwork_num, media_num, entry_day) VALUES (?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

			preparedStatement.setString(1, artworkNum);
			preparedStatement.setString(2, mediaNum);
			preparedStatement.setString(3, entryDate);

			return preparedStatement.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	//	データベースの作品情報(作品名・新旧区分・ジャンル番号)を更新
	public int updateArtwork(ArtworkObj artworkObj) {

		// ジャンル名からジャンル番号を取得する
		String sql1 = "SELECT genre_num FROM GENRE_TABLE genre_name = ? ";

		String genreNum;
		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql1.toString());) {

			preparedStatement.setString(1, artworkObj.getGenreName());
			ResultSet result = preparedStatement.executeQuery();

			//	resultに値が入っていなければ
			if (!result.next()) {
				return 0;
			}
			genreNum = result.getString("genre_num");

		} catch (SQLException exception) {
			exception.printStackTrace();
			return 0;
		}

		// 作品情報を更新する
		StringBuilder sqlBuilder = new StringBuilder(0);
		ArrayList<String> parameterList = new ArrayList<String>();

		sqlBuilder.append("UPDATE ARTWORK_TABLE SET ");

		//	作品名
		if (artworkObj.getArtworkName() != null && !artworkObj.getArtworkName().equals("")) {
			sqlBuilder.append("artwork_name = ?,");
			parameterList.add(artworkObj.getArtworkName());
		}
		//	新旧区分
		if (artworkObj.getRecentDiv() != null && !artworkObj.getRecentDiv().equals("")) {
			sqlBuilder.append(" recent_div = ?,");
			parameterList.add(artworkObj.getRecentDiv());
		}
		//	ジャンル番号
		if (genreNum != null && !genreNum.equals("")) {
			sqlBuilder.append(" genre_num = ?,");
			parameterList.add(genreNum);
		}

		// 最後の「,」を削除する
		if (sqlBuilder.toString().endsWith(",")) {
			sqlBuilder.delete(sqlBuilder.length() - 1, sqlBuilder.length());
		}

		//	更新する作品番号指定
		sqlBuilder.append(" WHERE artwork_num = ?");
		parameterList.add(artworkObj.getArtworkNum());

		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString())) {

			//	更新するパラメータをセット
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

	//	DVD情報（入荷日・削除日）更新
	public int updateDvd(DvdObj dvdObj) {

		StringBuilder sql = new StringBuilder(0);
		ArrayList<String> parameterList = new ArrayList<String>();

		sql.append("UPDATE DVD_TABLE SET ");

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

		sql.append(" WHERE artwork_num = ? AND media_num = ?");
		parameterList.add(dvdObj.getArtworkNumber());
		parameterList.add(dvdObj.getMediaNumber());

		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

			//	削除するパラメータをセット
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
