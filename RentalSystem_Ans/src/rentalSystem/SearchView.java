package rentalSystem;

import java.awt.Container;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * DVD検索画面
 *
 * 作品番号で検索し、登録されているDVDの一覧を表示する。
 */
public class SearchView extends JFrame {

	private JLabel artworkNumberLabel;
	private JTextField artworkNumberField;
	private JButton searchButton;

	private JLabel artworkNameLabel;
	private JTextField artworkNameField;

	private JScrollPane scrollPanel;
	private JTable resultTable;

	private JLabel messageLabel;

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
	public SearchView(String url, String userName, String password) {

		super();

		this.url = url;
		this.userName = userName;
		this.password = password;

		initialize();
	}

	/**
	 * GUI部品群の初期化
	 */
	private void initialize() {

		this.setTitle("レンタルシステム<DVD検索>");

		this.setBounds(0, 0, 540, 400);
		this.setLayout(null);

		Container container = this.getContentPane();

		container.add(createArtworkNumberLabel());
		container.add(createArtworkNumberField());
		container.add(createSearchButton());

		container.add(createArtworkNameLabel());
		container.add(createArtworkNameField());

		container.add(createScrollPane());

		container.add(createMessageLabel());

		clearTableData();

	}

	/**
	 * 作品番号ラベルを作成する
	 *
	 * @return 作品番号ラベル
	 */
	private JLabel createArtworkNumberLabel() {

		artworkNumberLabel = new JLabel("作品番号");
		artworkNumberLabel.setBounds(10, 10, 70, 20);

		return artworkNumberLabel;
	}

	/**
	 * 作品番号欄を作成する
	 *
	 * @return 作品番号欄
	 */
	private JTextField createArtworkNumberField() {

		artworkNumberField = new JTextField("");
		artworkNumberField.setBounds(70, 10, 300, 20);

		return artworkNumberField;
	}

	/**
	 * 検索ボタンを作成する
	 *
	 * 検索ボタンをクリックすると、作品番号でデータベースを検索し、
	 * 作品名やDVDの情報を表示する
	 *
	 *
	 * @return 検索ボタン
	 */
	private JButton createSearchButton() {

		searchButton = new JButton("検索");
		searchButton.setBounds(400, 10, 80, 20);

		searchButton.addActionListener((e) -> {

			messageLabel.setText("");

			String artworkNumber = artworkNumberField.getText();

			// 作品番号から作品名を検索する
			String artworkName = getArtworkName(artworkNumber);

			// 該当する作品んが登録されていない場合はメッセージを表示し、
			// 検索結果を初期化する
			if (artworkName == null) {

				messageLabel.setText(
						"作品番号 " + artworkNumber + " は登録されていません。");

				artworkNameField.setText("");
				clearTableData();

				return;
			}

			// DVDの情報を検索する
			String[][] data = getDvdInformation(artworkNumber);

			// 検索結果を反映する
			artworkNameField.setText(artworkName);
			setTableData(data);
		});

		return searchButton;
	}

	/**
	 * 作品名ラベルを作成する
	 *
	 * @return 作品名ラベル
	 */
	private JLabel createArtworkNameLabel() {

		artworkNameLabel = new JLabel("作品名");
		artworkNameLabel.setBounds(10, 35, 70, 20);

		return artworkNameLabel;
	}

	/**
	 * 作品名欄を作成する
	 *
	 * 作品名欄は編集不可とする。
	 *
	 * @return 作品名欄
	 */
	private JTextField createArtworkNameField() {

		artworkNameField = new JTextField("");
		artworkNameField.setBounds(70, 35, 300, 20);

		// 編集不可に設定する
		artworkNameField.setEditable(false);

		return artworkNameField;
	}

	/**
	 * DVD情報のテーブルようにスクロール可能なパネルを作成する
	 *
	 * @return スクロールパネル
	 */
	private JScrollPane createScrollPane() {

		scrollPanel = new JScrollPane();
		scrollPanel.setBounds(10, 60, 505, 280);

		return scrollPanel;
	}

	/**
	 * メッセージラベルを作成する
	 *
	 * @return メッセージラベル
	 */
	private JLabel createMessageLabel() {

		messageLabel = new JLabel("");
		messageLabel.setBounds(10, 340, 510, 20);

		return messageLabel;
	}

	/**
	 * 作品番号から作品名を取得する
	 *
	 * @param artworkNumber 作品番号
	 * @return 作品名（作品名が取得できない場合はnull）
	 */
	private String getArtworkName(String artworkNumber) {

		// 作品番号から作品名を照会する
		String sql = "SELECT artwork_name FROM ARTWORK_TABLE WHERE artwork_num = ?;";

		try (Connection connection =
				DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement =
						connection.prepareStatement(sql);) {

			preparedStatement.setString(1, artworkNumber);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (resultSet.next()) {
					return resultSet.getString("artwork_name");
				}
			}

		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}

		return null;
	}

	/**
	 * 作品番号からDVDの情報の一覧を取得する
	 *
	 * DVD情報は、媒体番号、状態、入荷日、貸出停止日、
	 * 貸出日、返却期限、貸出番号で構成する情報で、
	 * それぞれを文字列として扱う。
	 * 戻り値であるDVD情報の一覧は、文字列型の2次元配列として返す
	 *
	 * @param artworkNumber 作品番号
	 *
	 * @return DVD情報の一覧
	 */
	private String[][] getDvdInformation(String artworkNumber) {

		ArrayList<TableRow> dataList = new ArrayList<TableRow>();

		// DVDテーブルに登録されている情報と、
		// 貸出テーブルに登録されている貸出中の情報を結合して紹介する
		// 紹介の結果は媒体番号順に並べ替える
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("DVD.media_num, entry_day, disuse_day, ");
		sql.append("rental_num, check_out_day, term_day ");
		sql.append("FROM");
		sql.append("(SELECT media_num, entry_day, disuse_day ");
		sql.append("FROM DVD_TABLE ");
		sql.append("WHERE artwork_num = ?) AS DVD ");
		sql.append("LEFT JOIN ");
		sql.append("(SELECT media_num, rental_num, check_out_day, term_day  ");
		sql.append("FROM RENTAL_TABLE ");
		sql.append("WHERE artwork_num = ? AND check_in_day IS NULL) AS RENTAL ");
		sql.append("ON DVD.media_num = RENTAL.media_num ");
		sql.append("ORDER BY DVD.media_num;");

		try (Connection connection =
				DriverManager.getConnection(url, userName, password);) {

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(sql.toString());) {

				preparedStatement.setString(1, artworkNumber);
				preparedStatement.setString(2, artworkNumber);

				// DVD情報の照会
				try (ResultSet resultSet = preparedStatement.executeQuery();) {

					while (resultSet.next()) {

						// テーブルの行データの作成
						TableRow tableRow =
								new TableRow(resultSet.getString("media_num"));

						tableRow.entryDay = resultSet.getDate("entry_day");
						tableRow.disuseDay = resultSet.getDate("disuse_day");
						tableRow.checkOutDay = resultSet.getDate("check_out_day");
						tableRow.termDay = resultSet.getDate("term_day");
						tableRow.rentalNumber = resultSet.getString("rental_num");

						dataList.add(tableRow);
					}
				}
			}

		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}

		String[][] dataArray = new String[dataList.size()][7];
		for (int i = 0; i < dataList.size(); i++) {

			TableRow tableRow = dataList.get(i);
			dataArray[i] = tableRow.toStringArray();
		}

		return dataArray;
	}

	/**
	 * 表の内容を削除する
	 */
	private void clearTableData() {
		setTableData(null);
	}

	/**
	 * 渡されたDVD情報の一覧から表を作成する
	 * nullが渡された場合は空の表を作成する
	 *
	 * @param data DVD情報の一覧
	 */
	private void setTableData(String[][] data) {

		// テーブルのタイトル
		String[] columns = { "媒体番号", "状態", "入荷日", "削除日", "貸出日",
				"返却期限", "貸出番号" };

		if (data == null) {
			// 空のデータを作成
			data = new String[0][7];
		}

		// テーブルを作成し、パネルに登録する
		resultTable = new JTable(data, columns);
		scrollPanel.setViewportView(resultTable);
	}

	/**
	 * テーブルの行データを扱うクラス
	 *
	 *  1行分の「媒体番号」「入荷日」「貸出停止日」「貸出日」
	 * 「返却期限」「貸出番号」を保持する。
	 * 「状態」は他の情報から決定する。
	 */
	private class TableRow {

		public String mediaNumber;
		public Date entryDay;
		public Date disuseDay;
		public Date checkOutDay;
		public Date termDay;
		public String rentalNumber;

		/**
		 * コンストラクタ
		 *
		 * @param mediaNumber 媒体番号
		 */
		public TableRow(String mediaNumber) {
			this.mediaNumber = mediaNumber;
		}

		/**
		 * 1行分のデータをStringの配列形式に変換する
		 *
		 * 「状態」は他の情報から決定する。
		 * 値がnullの場合は空文字列（""）として扱う
		 *
		 * @return 行データのString配列
		 */
		public String[] toStringArray() {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

			String[] array = new String[7];

			array[0] = mediaNumber;
			array[1] = getStatus();

			if (entryDay == null) {
				array[2] = "";
			} else {
				array[2] = sdf.format(entryDay);
			}
			if (disuseDay == null) {
				array[3] = "";
			} else {
				array[3] = sdf.format(disuseDay);
			}
			if (checkOutDay == null) {
				array[4] = "";
			} else {
				array[4] = sdf.format(checkOutDay);
			}
			if (termDay == null) {
				array[5] = "";
			} else {
				array[5] = sdf.format(termDay);
			}
			if (rentalNumber == null) {
				array[6] = "";
			} else {
				array[6] = rentalNumber;
			}

			return array;
		}

		/**
		 * 状態を判定する
		 *
		 * 状態は以下のように判定する
		 * 1. 貸出中・・・・貸出日が登録されている
		 * 2. 貸出停止・・・貸出停止日が登録されている
		 * 3. 貸出可能・・・上記以外
		 *
		 * @return 状態の判定結果
		 */
		private String getStatus() {

			if (this.checkOutDay != null) {
				return "貸出中";
			} else if (disuseDay != null) {
				return "貸出停止";
			} else {
				return "貸出可能";
			}
		}
	}

}
