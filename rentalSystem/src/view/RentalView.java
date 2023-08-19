package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class RentalView extends JFrame {

	// 添え字
	private int arrayNum;

	// 貸出番号
	private int newRentalNum = 0;

	//	会員番号
	private JLabel memberNumLabel;
	private JTextField memberNumField;
	//	検索ボタン
	private JButton searchButton;
	//	会員氏名
	private JLabel memberNameLabel;
	private JTextField memberNameField;
	//	優良区分
	private JLabel categoryLabel;
	private JTextField categoryField;
	//	貸出日
	private JLabel rentalDayLabel;
	private JTextField rentalDayField;
	//	貸出番号
	private JLabel rentalNumLabel;
	//	作品番号
	private JLabel artworkNumLabel;
	//	枝番
	private JLabel mediaNumLabel;
	//	作品名
	private JLabel artworkNameLabel;
	//	返却期限
	private JLabel returnLimitLabel;
	//	料金
	private JLabel priceLabel;
	//	入力フィールド
	private JTextField[] rentalNumField = new JTextField[3];
	private JTextField[] artworkNumField = new JTextField[3];
	private JLabel[] hyphenLabel = new JLabel[3];
	private JTextField[] mediaNumField = new JTextField[3];
	private JTextField[] artworkNameField = new JTextField[3];
	private JTextField[] returnLimitField = new JTextField[3];
	private JTextField[] priceField = new JTextField[3];

	//	合計金額
	private JLabel totalPriceLabel;
	private JTextField totalPriceField;
	//	実行、戻るボタン
	private JButton executeButton;
	private JButton backButton;
	//	メッセージ
	private JLabel messageLabel;

	private final String name, pass, url;// データベース接続用データ

	public RentalView(String url, String name, String pass) { // 本番稼働用
		//	public RentalView() { //試行用

		this.name = name;
		this.pass = pass;
		this.url = url;
		//	試行用
		//		this.name = "root";
		//		this.pass = "password";
		//		this.url = "jdbc:mysql://localhost/RentalDB?useSSL=false&serverTimezone=Asia/Tokyo";

		// 初期指定
		//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("レンタルシステム＜貸出＞");
		setSize(600, 579); // ウィンドウサイズ指定
		setLocationRelativeTo(null); // windowを中央表示

		Container cntnr = getContentPane();
		setLayout(null);

		//			会員番号検索
		cntnr.add(createMemberNumLabel());
		cntnr.add(createMemberNumField());
		cntnr.add(createSearchButton());

		//			会員氏名
		cntnr.add(createMemberNameLabel());
		cntnr.add(createMemberNameField());

		//			優良区分
		cntnr.add(createCategoryLabel());
		cntnr.add(createCategoryField());

		//			貸出日
		cntnr.add(createRentalDayField());
		cntnr.add(createRentalDayLabel());

		//			貸出番号
		cntnr.add(createRentalNumLabel());

		//			作品番号
		cntnr.add(createArtworkNumLabel());

		//			枝番
		cntnr.add(createMediaNumLabel());

		//			作品名
		cntnr.add(createArtworkNameLabel());

		//			返却制限
		cntnr.add(createreturnLimitLabel());

		//			料金
		cntnr.add(createpriceLabel());

		//			テキストフィールド
		cntnr.add(createRentalNumField_1());

		cntnr.add(createArtworkNumField_1());

		cntnr.add(createHyphenLabel_1());

		cntnr.add(createMediaNumField_1());

		cntnr.add(createArtworkNameField_1());

		cntnr.add(createReturnLimitField_1());

		cntnr.add(createPriceField_1());

		cntnr.add(createRentalNumField_2());

		cntnr.add(createArtworkNumField_2());

		cntnr.add(createHyphenLabel_2());

		cntnr.add(createMediaNumField_2());

		cntnr.add(createArtworkNameField_2());

		cntnr.add(createReturnLimitField_2());

		cntnr.add(createPriceField_2());

		cntnr.add(createRentalNumField_3());

		cntnr.add(createArtworkNumField_3());

		cntnr.add(createHyphenLabel_3());

		cntnr.add(createMediaNumField_3());

		cntnr.add(createArtworkNameField_3());

		cntnr.add(createReturnLimitField_3());

		cntnr.add(createPriceField_3());

		//			合計金額
		cntnr.add(createTotalPriceLabel());
		cntnr.add(createTotalPriceField());

		//			最下行
		cntnr.add(createMessageLabel());

		cntnr.add(createExecButton());
		cntnr.add(createBackButton());

		componentReset();

	}

	//	GUI生成
	private JLabel createMemberNumLabel() {
		memberNumLabel = new JLabel("会員番号");
		memberNumLabel.setBounds(15, 35, 55, 15);
		memberNumLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		return memberNumLabel;
	}

	private JTextField createMemberNumField() {
		memberNumField = new JTextField();
		memberNumField.setBounds(25, 53, 210, 35);
		memberNumField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
		return memberNumField;
	}

	private JButton createSearchButton() {
		searchButton = new JButton("検索");
		searchButton.setBounds(410, 53, 70, 30);
		searchButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));

		//		検索ボタン押下時の処理(匿名クラスで記載)
		//		匿名クラス：繰り返し使用しない、一度きりの利用のクラスに有用
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				// メッセージラベル・会員氏名・優良区分の初期化
				messageLabel.setText("");
				memberNameField.setText("");
				categoryField.setText("");
				rentalDayField.setText("");

				String sql = "SELECT member_name,rank_div FROM MEMBER_TABLE WHERE member_num = ?";

				try (Connection connection = DriverManager.getConnection(url, name, pass);
						PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

					preparedStatement.setString(1, memberNumField.getText());

					ResultSet result = preparedStatement.executeQuery();

					//resultSetに値が入っていればtrue、なければfalse
					if (result.next()) {

						memberNameField.setText(result.getString(1));
						categoryField.setText(result.getString(2));

						// 書式を指定してDateTimeFormatterを取得
						DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
						// 書式を指定してLocalDateTimeのインスタンスを作成する
						LocalDateTime nowDate = LocalDateTime.now();
						// システム日付を、DateTimeFormatterで指定したフォーマットで文字列に変換
						rentalDayField.setText(nowDate.format(dateFormat));

						// 作品番号欄１の有効化
						artworkNumField[0].setEditable(true);
						artworkNumField[0].setFocusable(true);

					} else {

						messageLabel.setText("会員番号 " + memberNumField.getText() + " が見つかりません。");
						artworkNumField[0].setEditable(false);
						artworkNumField[0].setFocusable(false);
					}

				} catch (SQLException e) {

					// 作品番号欄１の無効化
					artworkNumField[0].setEditable(false);
					artworkNumField[0].setFocusable(false);

					e.printStackTrace();
				}
			}
		});
		return searchButton;
	}

	private JLabel createMemberNameLabel() {
		memberNameLabel = new JLabel("会員氏名");
		memberNameLabel.setBounds(15, 110, 55, 15);
		memberNameLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		return memberNameLabel;
	}

	private JTextField createMemberNameField() {
		memberNameField = new JTextField();
		memberNameField.setBounds(25, 128, 270, 35);
		memberNameField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
		return memberNameField;
	}

	private JLabel createCategoryLabel() {
		categoryLabel = new JLabel("優良区分");
		categoryLabel.setBounds(340, 110, 55, 15);
		categoryLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		return categoryLabel;
	}

	private JTextField createCategoryField() {
		categoryField = new JTextField();
		categoryField.setBounds(350, 128, 27, 35);
		categoryField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
		return categoryField;
	}

	private JLabel createRentalDayLabel() {
		rentalDayLabel = new JLabel("貸出日");
		rentalDayLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		rentalDayLabel.setBounds(15, 192, 36, 15);
		return rentalDayLabel;

	}

	private JTextField createRentalDayField() {
		rentalDayField = new JTextField();
		rentalDayField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
		rentalDayField.setBounds(25, 210, 188, 35);
		return rentalDayField;
	}

	private JLabel createRentalNumLabel() {
		rentalNumLabel = new JLabel("貸出番号");
		rentalNumLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		rentalNumLabel.setBounds(15, 265, 55, 15);
		return rentalNumLabel;
	}

	private JLabel createArtworkNumLabel() {
		artworkNumLabel = new JLabel("作品番号");
		artworkNumLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		artworkNumLabel.setBounds(82, 265, 52, 15);
		return artworkNumLabel;
	}

	private JLabel createMediaNumLabel() {
		mediaNumLabel = new JLabel("枝番");
		mediaNumLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		mediaNumLabel.setBounds(146, 265, 36, 15);
		return mediaNumLabel;
	}

	private JLabel createArtworkNameLabel() {
		artworkNameLabel = new JLabel("作品名");
		artworkNameLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		artworkNameLabel.setBounds(259, 265, 36, 15);
		return artworkNameLabel;
	}

	private JLabel createreturnLimitLabel() {
		returnLimitLabel = new JLabel("返却期限");
		returnLimitLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		returnLimitLabel.setBounds(410, 265, 55, 15);
		return returnLimitLabel;
	}

	private JLabel createpriceLabel() {
		priceLabel = new JLabel("料金");
		priceLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		priceLabel.setBounds(524, 265, 36, 15);
		return priceLabel;
	}

	private JTextField createRentalNumField_1() {
		rentalNumField[0] = new JTextField();
		rentalNumField[0].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		rentalNumField[0].setBounds(15, 290, 55, 20);
		return rentalNumField[0];
	}

	private JTextField createArtworkNumField_1() {
		artworkNumField[0] = new JTextField();
		artworkNumField[0].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		artworkNumField[0].setBounds(82, 290, 55, 20);
		artworkNumField[0].addFocusListener(new ArtworkNumFEL());
		return artworkNumField[0];
	}

	private JLabel createHyphenLabel_1() {
		hyphenLabel[0] = new JLabel("-");
		hyphenLabel[0].setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		hyphenLabel[0].setBounds(139, 294, 15, 15);
		return hyphenLabel[0];
	}

	private JTextField createMediaNumField_1() {
		mediaNumField[0] = new JTextField();
		mediaNumField[0].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		mediaNumField[0].setBounds(149, 290, 27, 20);
		mediaNumField[0].addFocusListener(new MediaNumFEL());
		return mediaNumField[0];
	}

	private JTextField createArtworkNameField_1() {
		artworkNameField[0] = new JTextField();
		artworkNameField[0].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		artworkNameField[0].setBounds(180, 290, 210, 20);
		return artworkNameField[0];
	}

	private JTextField createReturnLimitField_1() {
		returnLimitField[0] = new JTextField();
		returnLimitField[0].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		returnLimitField[0].setBounds(395, 290, 95, 20);
		return returnLimitField[0];
	}

	private JTextField createPriceField_1() {
		priceField[0] = new JTextField();
		priceField[0].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		priceField[0].setBounds(502, 290, 70, 20);
		return priceField[0];
	}

	private JTextField createRentalNumField_2() {
		rentalNumField[1] = new JTextField();
		rentalNumField[1].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		rentalNumField[1].setBounds(15, 320, 55, 20);
		return rentalNumField[1];
	}

	private JTextField createArtworkNumField_2() {
		artworkNumField[1] = new JTextField();
		artworkNumField[1].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		artworkNumField[1].setBounds(82, 320, 55, 20);
		artworkNumField[1].addFocusListener(new ArtworkNumFEL());
		return artworkNumField[1];
	}

	private JLabel createHyphenLabel_2() {
		hyphenLabel[1] = new JLabel("-");
		hyphenLabel[1].setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		hyphenLabel[1].setBounds(139, 324, 15, 15);
		return hyphenLabel[1];
	}

	private JTextField createMediaNumField_2() {
		mediaNumField[1] = new JTextField();
		mediaNumField[1].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		mediaNumField[1].setBounds(149, 320, 27, 20);
		mediaNumField[1].addFocusListener(new MediaNumFEL());
		return mediaNumField[1];
	}

	private JTextField createArtworkNameField_2() {
		artworkNameField[1] = new JTextField();
		artworkNameField[1].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		artworkNameField[1].setBounds(180, 320, 210, 20);

		return artworkNameField[1];
	}

	private JTextField createReturnLimitField_2() {
		returnLimitField[1] = new JTextField();
		returnLimitField[1].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		returnLimitField[1].setBounds(395, 320, 95, 20);
		return returnLimitField[1];
	}

	private JTextField createPriceField_2() {
		priceField[1] = new JTextField();
		priceField[1].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		priceField[1].setBounds(502, 320, 70, 20);
		return priceField[1];
	}

	private JTextField createRentalNumField_3() {
		rentalNumField[2] = new JTextField();
		rentalNumField[2].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		rentalNumField[2].setBounds(15, 350, 55, 20);
		return rentalNumField[2];
	}

	private JTextField createArtworkNumField_3() {
		artworkNumField[2] = new JTextField();
		artworkNumField[2].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		artworkNumField[2].setBounds(82, 350, 55, 20);
		artworkNumField[2].addFocusListener(new ArtworkNumFEL());
		return artworkNumField[2];
	}

	private JLabel createHyphenLabel_3() {
		hyphenLabel[2] = new JLabel("-");
		hyphenLabel[2].setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		hyphenLabel[2].setBounds(139, 354, 15, 15);
		return hyphenLabel[2];
	}

	private JTextField createMediaNumField_3() {
		mediaNumField[2] = new JTextField();
		mediaNumField[2].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		mediaNumField[2].setBounds(149, 350, 27, 20);
		mediaNumField[2].addFocusListener(new MediaNumFEL());
		return mediaNumField[2];
	}

	private JTextField createArtworkNameField_3() {
		artworkNameField[2] = new JTextField();
		artworkNameField[2].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		artworkNameField[2].setBounds(180, 350, 210, 20);
		return artworkNameField[2];
	}

	private JTextField createReturnLimitField_3() {
		returnLimitField[2] = new JTextField();
		returnLimitField[2].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		returnLimitField[2].setBounds(395, 350, 95, 20);
		return returnLimitField[2];
	}

	private JTextField createPriceField_3() {
		priceField[2] = new JTextField();
		priceField[2].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		priceField[2].setBounds(502, 350, 70, 20);
		return priceField[2];
	}

	private JLabel createTotalPriceLabel() {
		totalPriceLabel = new JLabel("合計金額");
		totalPriceLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 17));
		totalPriceLabel.setBounds(367, 400, 86, 30);
		return totalPriceLabel;
	}

	private JTextField createTotalPriceField() {
		totalPriceField = new JTextField();
		totalPriceField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 18));
		totalPriceField.setBounds(465, 400, 95, 35);
		return totalPriceField;
	}

	private JLabel createMessageLabel() {
		LineBorder border = new LineBorder(Color.BLACK, 2, true);
		messageLabel = new JLabel("");
		messageLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 11));
		messageLabel.setBounds(15, 484, 380, 30);
		messageLabel.setBorder(border);
		return messageLabel;
	}

	private JButton createExecButton() {
		executeButton = new JButton("実行");
		executeButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		executeButton.setBounds(410, 484, 70, 30);
		executeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {

					// 会員番号の取得
					String memberNum = memberNumField.getText();

					// 貸出日の取得
					String rentalDay = rentalDayField.getText();

					for (int i = 0; i < 3; i++) {

						if ((!priceField[i].getText().equals("")) && priceField[i].getText() != null) {

							// 枝番（媒体番号）を取得し2桁形式に
							String mediaNum = mediaNumField[i].getText();
							String mediaNumber_ZS = zeroSet(Integer.parseInt(mediaNum), 2);

							String sql = "INSERT INTO RENTAL_TABLE "
									+ "(rental_num, check_out_day, member_num, artwork_num, media_num, term_day,arrears) "
									+ "VALUES (?, ?, ?, ?, ?, ?,?)";

							try (Connection connection = DriverManager.getConnection(url, name, pass);
									PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

								preparedStatement.setString(1, rentalNumField[i].getText());
								preparedStatement.setString(2, rentalDay);
								preparedStatement.setString(3, memberNum);
								preparedStatement.setString(4, artworkNumField[i].getText());
								preparedStatement.setString(5, mediaNumber_ZS);
								preparedStatement.setString(6, returnLimitField[i].getText());
								preparedStatement.setInt(7, 0);

								preparedStatement.executeUpdate();

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					messageLabel.setText("貸出処理が完了しました。");
					componentReset();

				} catch (Exception e) {

					messageLabel.setText("貸出処理が異常終了しました。");
				}

			}
		});
		return executeButton;
	}

	private JButton createBackButton() {
		backButton = new JButton("戻る");
		backButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		backButton.setBounds(490, 484, 70, 30);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// ウィンドウを閉じる
				RentalView.this.dispose();
			}
		});
		return backButton;
	}

	//	public static void main(String[] args) {
	//
	//		RentalView rentalView = new RentalView();
	//		rentalView.setVisible(true);//表示メソッド(true:表示、false:非表示)
	//
	//	}

	//	貸出番号取得
	private String getNewRentalNum() {

		// 現在の貸出番号が0(最初のアクセス)
		if (newRentalNum == 0) {

			String maxRentalNum = null; // 現在の最大貸出番号

			// 現在の貸出番号の最大を照会
			String sql = "SELECT MAX(rental_num) FROM RENTAL_TABLE";

			try (Connection connection = DriverManager.getConnection(url, name, pass);
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery(sql);) {

				if (resultSet.next()) {
					maxRentalNum = resultSet.getString("MAX(rental_num)");
				}

				// 貸出が登録されていない場合は 0、
				// 登録されている場合は 最大値+1
				if (maxRentalNum == null) {
					newRentalNum = 1;
				} else {
					newRentalNum = Integer.parseInt(maxRentalNum) + 1;
				}

			} catch (SQLException exception) {

				exception.printStackTrace();

				return null;
			}

		} else {

			// +1して新規貸出番号にする
			newRentalNum++;
		}

		String newNumber = zeroSet(newRentalNum, 6);

		return newNumber;
	}

	private String zeroSet(int value, int length) {

		String result = String.valueOf(value);

		while (result.length() < length) {
			result = "0" + result;
		}

		return result;
	}

	//	FEL = FocusEventListener
	private class ArtworkNumFEL implements FocusListener {

		//	フォーカスインした際の処理
		@Override
		public void focusGained(FocusEvent fe) {

			// 添え字取得
			arrayNum = Arrays.asList(artworkNumField).indexOf(fe.getSource());

			// 貸出番号が空なら新規貸出番号を設定する
			if (rentalNumField[arrayNum].getText().equals("")) {
				// 新規貸出番号の設定
				rentalNumField[arrayNum].setText(getNewRentalNum());
			}
		}

		//	フォーカスアウトした際の処理
		@Override
		public void focusLost(FocusEvent fe) {

			// 添え字取得
			arrayNum = Arrays.asList(artworkNumField).indexOf(fe.getSource());

			if (arrayNum == 0)
				messageLabel.setText("");

			// 作品名を取得
			String artworkNumber = artworkNumField[arrayNum].getText();
			String sql = "SELECT artwork_name FROM ARTWORK_TABLE WHERE artwork_num = ?";

			try (Connection connection = DriverManager.getConnection(url, name, pass);
					PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

				preparedStatement.setString(1, artworkNumber);
				ResultSet result = preparedStatement.executeQuery();

				//	resultSetに値が入っていればtrue、なければfalse
				if (result.next()) {
					artworkNameField[arrayNum].setText(result.getString(1));
					//	作品があれば枝番（媒体番号）を入力可能にする
					mediaNumField[arrayNum].setFocusable(true);
					mediaNumField[arrayNum].setEditable(true);

				} else {

					messageLabel.setText("作品番号 " + artworkNumber + " が見つかりません。");
					artworkNameField[arrayNum].setText("");
					mediaNumField[arrayNum].setText("");
					returnLimitField[arrayNum].setText("");
					priceField[arrayNum].setText("");
					mediaNumField[arrayNum].setFocusable(false);
					mediaNumField[arrayNum].setEditable(false);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	//	FEL = FocusEventListener
	private class MediaNumFEL implements FocusListener {

		//	フォーカスインした際の処理
		@Override
		public void focusGained(FocusEvent fe) {
		}

		//	フォーカスアウトした際の処理
		@Override
		public void focusLost(FocusEvent fe) {

			// 添え字取得
			arrayNum = Arrays.asList(mediaNumField).indexOf(fe.getSource());

			if (arrayNum == 0)
				messageLabel.setText("");

			//	mediaNumField(枝番)に値があるとき
			if (!mediaNumField[arrayNum].getText().equals("")) {

				int state = getDvdState(artworkNumField[arrayNum].getText(), mediaNumField[arrayNum].getText());
				switch (state) {
				case 0: //	DVDが登録されていない
					messageLabel.setText(
							"作品番号 " + artworkNumField[arrayNum].getText() + " の "
									+ "枝番（媒体番号） " + mediaNumField[arrayNum].getText() + " が"
									+ "見つかりません。");
					break;

				case 1: //	DVDが削除済み
					messageLabel.setText(
							"作品番号 " + artworkNumField[arrayNum].getText() + " の "
									+ "枝番（媒体番号） " + mediaNumField[arrayNum].getText() + " は"
									+ "貸出を行っておりません。");
					break;

				case 2: //  DVDが貸出中
					messageLabel.setText(
							"作品番号 " + artworkNumField[arrayNum].getText() + " の "
									+ "枝番（媒体番号） " + mediaNumField[arrayNum].getText() + " は"
									+ "現在貸出中です。");
					break;

				case 3: //  DVDが貸出可能

					// 料金と貸出期限の設定
					String sql = "SELECT rental_charge,rental_period FROM artwork_table JOIN price_table ON artwork_table.recent_div=price_table.recent_div WHERE artwork_num=?";

					try (Connection connection = DriverManager.getConnection(url, name, pass);
							PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

						preparedStatement.setString(1, artworkNumField[arrayNum].getText());
						ResultSet result = preparedStatement.executeQuery();

						if (result.next()) {
							//	料金出力
							priceField[arrayNum].setText(result.getString("rental_charge"));

							//	貸出期限出力
							int returnLimitDay = Integer.parseInt(result.getString("rental_period"));
							// 加算される現在時間の取得(Date型)
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
							Date date = new Date();
							// Date型の日時をCalendar型に変換
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(date);
							// 日時を加算する
							calendar.add(Calendar.DAY_OF_MONTH, returnLimitDay);
							// Calendar型の日時をDate型に戻す
							date = calendar.getTime();
							returnLimitField[arrayNum].setText(sdf.format(date));
						}

					} catch (SQLException exception) {
						exception.printStackTrace();
					}

					// 作品番号欄の有効化
					if (arrayNum != 3) {
						artworkNumField[arrayNum + 1].setFocusable(true);
						artworkNumField[arrayNum + 1].setEditable(true);
					}

					// 合計金額の算出
					setTotalPrice();

					break;
				}

				if (state != 3) {
					// 料金と貸出期限を初期化
					returnLimitField[arrayNum].setText("");
					priceField[arrayNum].setText("");

					// 作品番号欄の無効化
					if (arrayNum != 3) {
						artworkNumField[arrayNum + 1].setFocusable(false);
						artworkNumField[arrayNum + 1].setEditable(false);
					}
				}

			}

		}

		public int getDvdState(String artworkNum, String mediaNum) {

			//	ZS = ZeroSet
			String mediaNum_ZS = zeroSet(Integer.parseInt(mediaNum), 2);

			try (Connection connection = DriverManager.getConnection(url, name, pass)) {

				String sql_1 = "SELECT disuse_day FROM DVD_TABLE WHERE artwork_num = ? AND media_num = ?";
				try (PreparedStatement preparedStatement = connection.prepareStatement(sql_1);) {

					preparedStatement.setString(1, artworkNum);
					preparedStatement.setString(2, mediaNum_ZS);
					ResultSet result = preparedStatement.executeQuery();

					//	resultSetに値が入っていればtrue、なければfalse
					if (!result.next()) { // DVDが登録されていない
						return 0;
					} else if (result.getDate("disuse_day") != null) { // DVDが削除済み
						return 1;
					}
				}

				String sql_2 = "SELECT * FROM RENTAL_TABLE WHERE artwork_num = ? AND media_num = ? AND check_in_day IS NULL";
				try (PreparedStatement preparedStatement = connection.prepareStatement(sql_2);) {
					preparedStatement.setString(1, artworkNum);
					preparedStatement.setString(2, mediaNum_ZS);
					ResultSet result = preparedStatement.executeQuery();

					if (result.next()) { // DVDが貸出中
						return 2;
					} else { // DVDが貸出可能
						return 3;
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();

				return 0;
			}

		}

	}

	private void componentReset() {

		// 会員情報
		memberNumField.setText("");

		memberNameField.setText("");
		memberNameField.setEditable(false);
		memberNameField.setFocusable(false);

		categoryField.setText("");
		categoryField.setEditable(false);
		categoryField.setFocusable(false);

		// 貸出日
		rentalDayField.setText("");
		rentalDayField.setEditable(false);
		rentalDayField.setFocusable(false);

		// テキストフィールド
		for (int i = 0; i < 3; i++) {

			rentalNumField[i].setText("");
			rentalNumField[i].setEditable(false);
			rentalNumField[i].setFocusable(false);

			artworkNumField[i].setText("");
			artworkNumField[i].setEditable(false);
			artworkNumField[i].setFocusable(false);

			mediaNumField[i].setText("");
			mediaNumField[i].setEditable(false);
			mediaNumField[i].setFocusable(false);

			artworkNameField[i].setText("");
			artworkNameField[i].setEditable(false);
			artworkNameField[i].setFocusable(false);

			returnLimitField[i].setText("");
			returnLimitField[i].setEditable(false);
			returnLimitField[i].setFocusable(false);

			priceField[i].setText("");
			priceField[i].setEditable(false);
			priceField[i].setFocusable(false);

		}

		// 合計金額
		totalPriceField.setText("");
		totalPriceField.setEditable(false);
		totalPriceField.setFocusable(false);

		// 実行ボタン
		executeButton.setEnabled(false);

	}

	private void setTotalPrice() {

		int totalPrice = 0;

		for (int i = 0; i < 3; i++) {
			String price = priceField[i].getText();
			if (!price.equals(""))
				totalPrice += Integer.parseInt(price);
		}

		// 合計金額の設定
		totalPriceField.setText(String.valueOf(totalPrice));

		// 合計金額が0でなければ「実行」ボタンの使用を可にする
		if (totalPrice != 0) {
			executeButton.setEnabled(true);
		} else {
			executeButton.setEnabled(false);
		}
	}

}
