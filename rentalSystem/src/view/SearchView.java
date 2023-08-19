package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.border.LineBorder;

public class SearchView extends JFrame {

	private JLabel artworkNumLabel;
	private JTextField artworkNumField;
	private JButton searchButton;

	private JLabel artworkNameLabel;
	private JTextField artworkNameField;

	private JScrollPane scrollPane;
	private JTable table;

	private JLabel messageLabel;

	private final String name, pass, url;// データベース接続用データ

	public SearchView(String url, String name, String pass) {
		//	public SearchView() {

		this.name = name;
		this.pass = pass;
		this.url = url;

		//		this.name = "root";
		//		this.pass = "password";
		//		this.url = "jdbc:mysql://localhost/RentalDB?useSSL=false&serverTimezone=Asia/Tokyo";

		// 初期指定
		setTitle("レンタルシステム＜DVD検索＞");
		setSize(600, 422); // ウィンドウサイズ指定
		setLocationRelativeTo(null); // windowを中央表示

		Container cntnr = getContentPane();
		getContentPane().setLayout(null);

		cntnr.add(createArtworkNumLabel());
		cntnr.add(createArtworkNumField());
		cntnr.add(createSearchButton());

		cntnr.add(createArtworkNameLabel());
		cntnr.add(createArtworkNameField());

		cntnr.add(createScrollPane());

		cntnr.add(createMessageLabel());

		setTableData(null); // table作成・初期化

	}

	//	GUI生成
	//	public static void main(String[] args) {
	//		SearchView searcView = new SearchView();
	//		searcView.setVisible(true);//表示メソッド(true:表示、false:非表示)
	//	}

	private JLabel createArtworkNumLabel() {
		artworkNumLabel = new JLabel("作品番号");
		artworkNumLabel.setBounds(32, 10, 60, 20);
		return artworkNumLabel;
	}

	private JTextField createArtworkNumField() {
		artworkNumField = new JTextField("");
		artworkNumField.setBounds(92, 11, 335, 20);
		return artworkNumField;
	}

	private JButton createSearchButton() {
		searchButton = new JButton("検索");
		searchButton.setBounds(468, 10, 80, 20);
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				// 作品名検索・出力
				String artworkNum = artworkNumField.getText();
				messageLabel.setText("");

				String sql = "SELECT artwork_name FROM ARTWORK_TABLE WHERE artwork_num = ?";

				try (Connection connection = DriverManager.getConnection(url, name, pass);
						PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

					preparedStatement.setString(1, artworkNum);
					ResultSet result = preparedStatement.executeQuery();
					if (result.next()) {

						artworkNameField.setText(result.getString(1));

						//	DVD情報検索・出力
						String[][] data = getDvdInformation(artworkNum);
						setTableData(data);

					} else {

						artworkNameField.setText("");
						messageLabel.setText(
								"作品番号 " + artworkNum + " は登録されていません。");
						setTableData(null); // table初期化
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		return searchButton;
	}

	private JLabel createArtworkNameLabel() {
		artworkNameLabel = new JLabel("作品名");
		artworkNameLabel.setBounds(32, 39, 70, 20);
		return artworkNameLabel;
	}

	private JTextField createArtworkNameField() {
		artworkNameField = new JTextField("");
		artworkNameField.setBounds(92, 40, 335, 20);
		artworkNameField.setEditable(false); // 編集不可に設定する
		return artworkNameField;
	}

	private JScrollPane createScrollPane() {
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 68, 562, 272);
		return scrollPane;
	}

	private JLabel createMessageLabel() {
		messageLabel = new JLabel("");
		LineBorder border = new LineBorder(Color.BLACK, 1, true);
		messageLabel.setBorder(border);
		messageLabel.setBounds(10, 353, 562, 20);
		return messageLabel;
	}

	private void setTableData(String[][] data) {

		String[] columns = { "媒体番号", "状態", "入荷日", "削除日", "貸出日",
				"返却期限", "貸出番号" };

		if (data == null) {
			// 空のデータを作成
			data = new String[0][7];
		}

		// テーブルを作成し、パネルに登録する
		table = new JTable(data, columns);
		scrollPane.setViewportView(table);
	}

	private String[][] getDvdInformation(String artworkNum) {

		ArrayList<TableRow> dataList = new ArrayList<TableRow>();

		String sql = "SELECT DVD.media_num, entry_day, disuse_day, rental_num, check_out_day, term_day \r\n"
				+ "FROM (SELECT media_num, entry_day, disuse_day FROM  DVD_TABLE WHERE artwork_num = ?) AS DVD \r\n"
				+ "LEFT JOIN (SELECT media_num, rental_num, check_out_day, term_day FROM RENTAL_TABLE WHERE artwork_num = ? AND check_in_day IS NULL) AS RENTAL \r\n"
				+ "ON DVD.media_num = RENTAL.media_num \r\n"
				+ "ORDER BY DVD.media_num;";

		try (Connection connection = DriverManager.getConnection(url, name, pass);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

			preparedStatement.setString(1, artworkNum);
			preparedStatement.setString(2, artworkNum);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				// テーブルの行データの作成
				TableRow tableRow = new TableRow();

				tableRow.mediaNum = result.getString("media_num");
				tableRow.entryDay = result.getDate("entry_day");
				tableRow.disuseDay = result.getDate("disuse_day");
				tableRow.rentalDay = result.getDate("check_out_day");
				tableRow.termDay = result.getDate("term_day");
				tableRow.rentalNumber = result.getString("rental_num");

				dataList.add(tableRow);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		//	コレクションデータを配列へ
		String[][] dataArray = new String[dataList.size()][7];
		for (int i = 0; i < dataList.size(); i++) {

			dataArray[i] = dataList.get(i).toStringArray();
		}

		return dataArray;

	}

	private class TableRow {

		public String mediaNum;
		public Date entryDay;
		public Date disuseDay;
		public Date rentalDay;
		public Date termDay;
		public String rentalNumber;

		public String[] toStringArray() {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

			String[] array = new String[7];

			array[0] = mediaNum;

			if (rentalDay != null) {
				array[1] = "貸出中";
			} else if (disuseDay != null) {
				array[1] = "貸出停止";
			} else {
				array[1] = "貸出可能";
			}

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
			if (rentalDay == null) {
				array[4] = "";
			} else {
				array[4] = sdf.format(rentalDay);
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
	}
}
