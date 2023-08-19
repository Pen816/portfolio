package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import dbAccess.ReturnDBAccess;

public class ReturnView extends JFrame {

	private ReturnDBAccess returnDBAccess;

	//	添え字
	private int arrayNum;

	//	返却日
	private JLabel returnDayLabel;
	private JTextField returnDayField;
	//	作品番号
	private JLabel artworkNumLabel;
	//	枝番
	private JLabel mediaNumLabel;
	//	作品名
	private JLabel artworkNameLabel;
	//	返却期限
	private JLabel returnLimitLabel;
	//	延滞料金
	private JLabel arrearsPriceLabel;
	//	入力フィールド
	private JTextField[] artworkNumField = new JTextField[3];
	private JLabel[] hyphenLabel = new JLabel[3];
	private JTextField[] mediaNumField = new JTextField[3];
	private JTextField[] artworkNameField = new JTextField[3];
	private JTextField[] returnLimitField = new JTextField[3];
	private JTextField[] arrearsPriceField = new JTextField[3];
	//	延滞金合計
	private JLabel totalArrearsPriceLabel;
	private JTextField totalArrearsPriceField;
	//	実行、戻るボタン
	private JButton executeButton;
	private JButton backButton;
	//	メッセージ
	private JLabel messageLabel;

	private String memberNum;

	private String[] rentalNum = new String[3];

	private String[] artworkNum = new String[3];

	private String[] mediaNum = new String[3];

//	private final String name, pass, url;// データベース接続用データ

	public ReturnView(String url, String name, String pass) {
		//	public ReturnView() {

		//	DB使用準備

		//		this.name = name;
		//		this.pass = pass;
		//		this.url = url;

		//		this.name = "root";
		//		this.pass = "password";
		//		this.url = "jdbc:mysql://localhost/RentalDB?useSSL=false&serverTimezone=Asia/Tokyo";

		returnDBAccess = new ReturnDBAccess(url, name, pass);

		// 初期指定
		setTitle("レンタルシステム＜返却＞");
		setSize(600, 392); // ウィンドウサイズ指定
		setLocationRelativeTo(null); // windowを中央表示

		Container cntnr = getContentPane();
		getContentPane().setLayout(null);

		//			返却日
		cntnr.add(returnDayLabel());
		cntnr.add(returnDayField());

		//			作品番号
		cntnr.add(createArtworkNumLabel());

		//			枝番
		cntnr.add(createMediaNumLabel());

		//			作品名
		cntnr.add(createArtworkNameLabel());

		//			返却制限
		cntnr.add(createreturnLimitLabel());

		//			延滞料金
		cntnr.add(createpriceLabel());

		//			テキストフィールド
		cntnr.add(createArtworkNumField_1());

		cntnr.add(createHyphenLabel_1());

		cntnr.add(createMediaNumField_1());

		cntnr.add(createArtworkNameField_1());

		cntnr.add(createReturnLimitField_1());

		cntnr.add(createArrearsPriceField_1());

		cntnr.add(createArtworkNumField_2());

		cntnr.add(createHyphenLabel_2());

		cntnr.add(createMediaNumField_2());

		cntnr.add(createArtworkNameField_2());

		cntnr.add(createReturnLimitField_2());

		cntnr.add(createArrearsPriceField_2());

		cntnr.add(createArtworkNumField_3());

		cntnr.add(createHyphenLabel_3());

		cntnr.add(createMediaNumField_3());

		cntnr.add(createArtworkNameField_3());

		cntnr.add(createReturnLimitField_3());

		cntnr.add(createArrearsPriceField_3());

		//			延滞金合計
		cntnr.add(createTotalArrearsPriceLabel());
		cntnr.add(createTotalArrearsPriceField());

		//			最下行
		cntnr.add(createMessageLabel());

		cntnr.add(createExecButton());
		cntnr.add(createBackButton());

		componentReset();

	}

	//	public static void main(String[] args) {
	//		ReturnView returnView = new ReturnView();
	//		returnView.setVisible(true);//表示メソッド(true:表示、false:非表示)
	//	}

	// GUI生成
	private JLabel returnDayLabel() {
		returnDayLabel = new JLabel("返却日");
		returnDayLabel.setBounds(23, 22, 55, 19);
		returnDayLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 18));
		return returnDayLabel;
	}

	private JTextField returnDayField() {
		returnDayField = new JTextField();
		returnDayField.setBounds(23, 47, 210, 41);
		returnDayField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
		// 今日の日付の設定
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		returnDayField.setText(sdf.format(new Date()));
		return returnDayField;
	}

	private JLabel createArtworkNumLabel() {
		artworkNumLabel = new JLabel("作品番号");
		artworkNumLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		artworkNumLabel.setBounds(54, 98, 52, 15);
		return artworkNumLabel;
	}

	private JLabel createMediaNumLabel() {
		mediaNumLabel = new JLabel("枝番");
		mediaNumLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		mediaNumLabel.setBounds(120, 98, 36, 15);
		return mediaNumLabel;
	}

	private JLabel createArtworkNameLabel() {
		artworkNameLabel = new JLabel("作品名");
		artworkNameLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		artworkNameLabel.setBounds(233, 98, 36, 15);
		return artworkNameLabel;
	}

	private JLabel createreturnLimitLabel() {
		returnLimitLabel = new JLabel("返却期限");
		returnLimitLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		returnLimitLabel.setBounds(384, 98, 55, 15);
		return returnLimitLabel;
	}

	private JLabel createpriceLabel() {
		arrearsPriceLabel = new JLabel("延滞料金");
		arrearsPriceLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		arrearsPriceLabel.setBounds(482, 98, 55, 15);
		return arrearsPriceLabel;
	}

	private JTextField createArtworkNumField_1() {
		artworkNumField[0] = new JTextField();
		artworkNumField[0].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		artworkNumField[0].setBounds(50, 125, 55, 20);
		artworkNumField[0].addFocusListener(new ArtWorkNumFEL());
		return artworkNumField[0];
	}

	private JLabel createHyphenLabel_1() {
		hyphenLabel[0] = new JLabel("-");
		hyphenLabel[0].setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		hyphenLabel[0].setBounds(107, 129, 15, 15);
		return hyphenLabel[0];
	}

	private JTextField createMediaNumField_1() {
		mediaNumField[0] = new JTextField();
		mediaNumField[0].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		mediaNumField[0].setBounds(117, 125, 27, 20);
		mediaNumField[0].addFocusListener(new MediaNumFEL());
		return mediaNumField[0];
	}

	private JTextField createArtworkNameField_1() {
		artworkNameField[0] = new JTextField();
		artworkNameField[0].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		artworkNameField[0].setBounds(148, 125, 210, 20);
		return artworkNameField[0];
	}

	private JTextField createReturnLimitField_1() {
		returnLimitField[0] = new JTextField();
		returnLimitField[0].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		returnLimitField[0].setBounds(363, 125, 95, 20);
		return returnLimitField[0];
	}

	private JTextField createArrearsPriceField_1() {
		arrearsPriceField[0] = new JTextField();
		arrearsPriceField[0].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		arrearsPriceField[0].setBounds(470, 125, 70, 20);
		return arrearsPriceField[0];
	}

	private JTextField createArtworkNumField_2() {
		artworkNumField[1] = new JTextField();
		artworkNumField[1].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		artworkNumField[1].setBounds(50, 155, 55, 20);
		artworkNumField[1].addFocusListener(new ArtWorkNumFEL());
		return artworkNumField[1];
	}

	private JLabel createHyphenLabel_2() {
		hyphenLabel[1] = new JLabel("-");
		hyphenLabel[1].setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		hyphenLabel[1].setBounds(107, 155, 15, 15);
		return hyphenLabel[1];
	}

	private JTextField createMediaNumField_2() {
		mediaNumField[1] = new JTextField();
		mediaNumField[1].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		mediaNumField[1].setBounds(117, 155, 27, 20);
		mediaNumField[1].addFocusListener(new MediaNumFEL());
		return mediaNumField[1];
	}

	private JTextField createArtworkNameField_2() {
		artworkNameField[1] = new JTextField();
		artworkNameField[1].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		artworkNameField[1].setBounds(148, 155, 210, 20);

		return artworkNameField[1];
	}

	private JTextField createReturnLimitField_2() {
		returnLimitField[1] = new JTextField();
		returnLimitField[1].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		returnLimitField[1].setBounds(363, 155, 95, 20);
		return returnLimitField[1];
	}

	private JTextField createArrearsPriceField_2() {
		arrearsPriceField[1] = new JTextField();
		arrearsPriceField[1].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		arrearsPriceField[1].setBounds(470, 155, 70, 20);
		return arrearsPriceField[1];
	}

	private JTextField createArtworkNumField_3() {
		artworkNumField[2] = new JTextField();
		artworkNumField[2].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		artworkNumField[2].setBounds(50, 185, 55, 20);
		artworkNumField[2].addFocusListener(new ArtWorkNumFEL());
		return artworkNumField[2];
	}

	private JLabel createHyphenLabel_3() {
		hyphenLabel[2] = new JLabel("-");
		hyphenLabel[2].setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		hyphenLabel[2].setBounds(107, 185, 15, 15);
		return hyphenLabel[2];
	}

	private JTextField createMediaNumField_3() {
		mediaNumField[2] = new JTextField();
		mediaNumField[2].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		mediaNumField[2].setBounds(117, 185, 27, 20);
		mediaNumField[2].addFocusListener(new MediaNumFEL());
		return mediaNumField[2];
	}

	private JTextField createArtworkNameField_3() {
		artworkNameField[2] = new JTextField();
		artworkNameField[2].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		artworkNameField[2].setBounds(148, 185, 210, 20);
		return artworkNameField[2];
	}

	private JTextField createReturnLimitField_3() {
		returnLimitField[2] = new JTextField();
		returnLimitField[2].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		returnLimitField[2].setBounds(363, 185, 95, 20);
		return returnLimitField[2];
	}

	private JTextField createArrearsPriceField_3() {
		arrearsPriceField[2] = new JTextField();
		arrearsPriceField[2].setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		arrearsPriceField[2].setBounds(470, 185, 70, 20);
		return arrearsPriceField[2];
	}

	private JLabel createTotalArrearsPriceLabel() {
		totalArrearsPriceLabel = new JLabel("延滞金合計");
		totalArrearsPriceLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 17));
		totalArrearsPriceLabel.setBounds(368, 239, 86, 30);
		return totalArrearsPriceLabel;
	}

	private JTextField createTotalArrearsPriceField() {
		totalArrearsPriceField = new JTextField();
		totalArrearsPriceField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 18));
		totalArrearsPriceField.setBounds(466, 239, 95, 35);
		return totalArrearsPriceField;
	}

	private JLabel createMessageLabel() {
		LineBorder border = new LineBorder(Color.BLACK, 2, true);
		messageLabel = new JLabel("");
		messageLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 11));
		messageLabel.setBounds(23, 304, 373, 30);
		messageLabel.setBorder(border);
		return messageLabel;
	}

	private JButton createExecButton() {
		executeButton = new JButton("実行");
		executeButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		executeButton.setBounds(411, 304, 70, 30);
		executeButton.addActionListener(new ActionListener() {

			@Override
			//	返却メソッド
			public void actionPerformed(ActionEvent e) {

				int[] result = new int[3];
				Arrays.fill(result, 1);//	配列を1で初期化

				for (int i = 0; i < 3; i++) {

					if (!returnLimitField[i].getText().equals("")) {
						result[i] = returnDBAccess.returnSystem(rentalNum[i], returnDayField.getText(),
								arrearsPriceField[i].getText());
					}

				}

				if (result[0] == 1 && result[1] == 1 && result[2] == 1) {

					componentReset();
					messageLabel.setText("返却処理が完了しました。");

				} else {

					messageLabel.setText("返却処理に失敗しました。");
				}
			}
		});
		return executeButton;
	}

	private JButton createBackButton() {
		backButton = new JButton("戻る");
		backButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 16));
		backButton.setBounds(491, 304, 70, 30);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ウィンドウを閉じる
				ReturnView.this.dispose();
			}
		});
		return backButton;
	}

	private void componentReset() {

		// 作品番号ラベル１以外の編集を不可にし、フォーカスも切る
		//		returnDayField.setText("");
		returnDayField.setEditable(false);
		returnDayField.setFocusable(false);

		// テキストフィールド
		for (int i = 0; i < 3; i++) {

			artworkNumField[i].setText("");
			if (i != 0) {
				artworkNumField[i].setEditable(false);
				artworkNumField[i].setFocusable(false);
			}

			mediaNumField[i].setText("");
			mediaNumField[i].setEditable(false);
			mediaNumField[i].setFocusable(false);

			artworkNameField[i].setText("");
			artworkNameField[i].setEditable(false);
			artworkNameField[i].setFocusable(false);

			returnLimitField[i].setText("");
			returnLimitField[i].setEditable(false);
			returnLimitField[i].setFocusable(false);

			arrearsPriceField[i].setText("");
			arrearsPriceField[i].setEditable(false);
			arrearsPriceField[i].setFocusable(false);

		}

		totalArrearsPriceField.setText("");
		totalArrearsPriceField.setEditable(false);
		totalArrearsPriceField.setFocusable(false);

		// 実行ボタンをクリック不可に
		executeButton.setEnabled(false);

		// インスタンス変数の初期化
		memberNum = null;
		for (int i = 0; i < 3; i++) {
			rentalNum[i] = null;
			artworkNum[i] = null;
			mediaNum[i] = null;
		}
	}

	//	延滞金合計算出
	private void setTotalArrearsPrice() {

		int total = 0;

		for (int i = 0; i < 3; i++) {
			String arrearsPrice = arrearsPriceField[i].getText();
			if (!arrearsPrice.equals("")) {
				total += Integer.parseInt(arrearsPrice);
			}
		}

		totalArrearsPriceField.setText(total + "");
	}

	private class ArtWorkNumFEL implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent fe) {

			// 添え字取得
			arrayNum = Arrays.asList(artworkNumField).indexOf(fe.getSource());

			// 変更されていなければ何もしない
			if (artworkNum[arrayNum] != null && artworkNum[arrayNum].equals(artworkNumField[arrayNum].getText())) {
				return;
			}

			//	作品番号取得
			artworkNum[arrayNum] = artworkNumField[arrayNum].getText();

			//	各テキストフィールド初期化
			mediaNumField[arrayNum].setText("");
			artworkNameField[arrayNum].setText("");
			returnLimitField[arrayNum].setText("");
			arrearsPriceField[arrayNum].setText("");
			setTotalArrearsPrice();

			messageLabel.setText("");

			// 作品番号が空白でないとき
			if (!artworkNumField[arrayNum].getText().equals("")) {
				// 作品名取得
				String artworkName = returnDBAccess.getArtworkName(artworkNumField[arrayNum].getText());

				// 作品名が取得できない時、エラーメッセージ表示
				if (artworkName == null) {
					messageLabel.setText("作品番号 " + artworkNumField[arrayNum].getText() + " が見つかりませんでした。");
					return;
				}

				// 作品名の表示
				artworkNameField[arrayNum].setText(artworkName);
				// 媒体番号欄の有効化
				mediaNumField[arrayNum].setEditable(true);
				mediaNumField[arrayNum].setFocusable(true);

			}
		}

	}

	private class MediaNumFEL implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent fe) {

			// 添え字取得
			arrayNum = Arrays.asList(mediaNumField).indexOf(fe.getSource());

			// 変更されていなければ何もしない
			if (mediaNum[arrayNum] != null && mediaNum[arrayNum].equals(mediaNumField[arrayNum].getText())) {
				return;
			}

			//	媒体番号取得
			mediaNum[arrayNum] = mediaNumField[arrayNum].getText();

			//	各テキストフィールド初期化
			returnLimitField[arrayNum].setText("");
			arrearsPriceField[arrayNum].setText("");
			setTotalArrearsPrice();

			// 媒体番号が空白でないとき
			if (!artworkNumField[arrayNum].getText().equals("")) {
				//	貸出会員番号を取得
				String rentalMemberNum = returnDBAccess.getRentalMemberNum(artworkNumField[arrayNum].getText(),
						mediaNum[arrayNum]);

				// 会員番号を取得できない時、エラーメッセージ表示
				if (rentalMemberNum == null) {
					messageLabel.setText(
							"作品番号 " + artworkNumField[arrayNum].getText() + " の " + "枝番（媒体番号） "
									+ mediaNumField[arrayNum].getText() + " は貸出中ではありません。");
					return;
				}

				// memberNuがnullの時、rentalMemberNumeを設定
				if (memberNum == null) {
					memberNum = rentalMemberNum;

					// 同じ会員による返却でない時、エラーメッセージ表示
				} else if (!rentalMemberNum.equals(memberNum)) {
					messageLabel.setText("利用者が異なります");
					executeButton.setEnabled(false);
					return;
				}

				// 返却期限の取得
				Date returnLimit = returnDBAccess.getReturnLimit(artworkNumField[arrayNum].getText(),
						mediaNumField[arrayNum].getText());

				if (returnLimit != null) {

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					// 返却期限の表示
					returnLimitField[arrayNum].setText(sdf.format(returnLimit));

					// 遅延日数を計算
					long between = calcArrears(returnLimit);

					// 延滞している時
					if (between > 0) {
						//	1日当たりの延滞料金取得
						int arrearsPrice = returnDBAccess.getArrearsPrice(artworkNumField[arrayNum].getText());

						// 延滞料金を表示（延滞料金 = 1日あたりの延滞料金 * 延滞日数）
						arrearsPriceField[arrayNum].setText(String.valueOf(arrearsPrice * between));
						//	延滞金合計算出
						setTotalArrearsPrice();

					}

					// 実行ボタンの有効化
					executeButton.setEnabled(true);
				}
			}

			//	媒体番号フォーカスアウト時返却期限が空欄の時
			if (returnLimitField[arrayNum].getText().equals("")) {

				//	貸出番号リセット
				rentalNum[arrayNum] = null;

				if (arrayNum != 2) {
					// 次の作品番号入力欄の無効化
					artworkNumField[arrayNum + 1].setEditable(false);
					artworkNumField[arrayNum + 1].setFocusable(false);
				}

				//	媒体番号フォーカスアウト時返却期限に値が設定されている時
			} else {
				//	貸出番号取得
				rentalNum[arrayNum] = returnDBAccess.getRentalNum(artworkNumField[arrayNum].getText(),
						mediaNumField[arrayNum].getText());

				if (arrayNum != 2) {
					// 3行目の作品番号入力欄の有効化
					artworkNumField[arrayNum + 1].setEditable(true);
					artworkNumField[arrayNum + 1].setFocusable(true);
				}
			}
		}
	}

	//	遅延日数計算
	private long calcArrears(Date returnLimit) {

		Date today = new Date();//	今日の日付取得
		// 今日の日付-返却期限
		long between = today.getTime() - returnLimit.getTime();

		// ミリ秒を日に換算してリターン
		return between / (1000 * 60 * 60 * 24);

	}
}
