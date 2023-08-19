package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import dbAccess.DvdDBAccess;
import object.ArtworkObj;
import object.DvdObj;

public class DvdView extends JFrame {

	// DBアクセスオブジェクト
	private DvdDBAccess dvdDBAccess;

	//	選択中のモード  1:登録  2:更新  3:削除
	private int mode = 0;

	// モード
	private JPanel modeSelectPanel;
	private JRadioButton insertRadioButton;
	private JRadioButton updateRadiuoButton;
	private JRadioButton deleteRadioButton;
	private ButtonGroup modeGroup;

	//	作品番号
	private JLabel artworkNumLabel;
	private JTextField artworkNumField;

	//	枝番
	private JLabel mediaNumLabel;
	private JTextField mediaNumField;

	//	ハイフン
	private JLabel separetorLabel;

	//	検索ボタン
	private JButton searchButton;

	// 作品名欄
	private JLabel artworkNameLabel;
	private JTextField artworkNameField;

	// 新旧区分
	private JLabel recentDivLabel;
	private JComboBox<String> recentDivBox;

	// ジャンル区分
	private JLabel genreLabel;
	private JComboBox<String> genreBox;

	// 入荷日
	private JLabel entryDayLabel;
	private JTextField entryDayField;

	// 削除日
	private JLabel disuseDayLabel;
	private JTextField disuseDayField;

	// 実行ボタン
	private JButton executeButton;

	// 戻るボタン
	private JButton backButton;

	// メッセージラベル
	private JLabel messageLabel;

	//	private final String name, pass, url;// データベース接続用データ

	public DvdView(String url, String name, String pass) {
		//	public DvdView() {

		//	DB使用準備

//		this.name = name;
//		this.pass = pass;
//		this.url = url;

		//		this.name = "root";
		//		this.pass = "password";
		//		this.url = "jdbc:mysql://localhost/RentalDB?useSSL=false&serverTimezone=Asia/Tokyo";

		dvdDBAccess = new DvdDBAccess(url, name, pass);

		// 初期指定
		setTitle("レンタルシステム<DVD管理>");
		setSize(540, 400); // ウィンドウサイズ指定
		setLocationRelativeTo(null); // windowを中央表示

		Container cntnr = getContentPane();
		getContentPane().setLayout(null);

		//	「登録」「更新」「削除」のチェックボックス
		cntnr.add(createModeSelectPanel());

		//	作品番号
		cntnr.add(createArtworkNumLabel());
		cntnr.add(createArtworkNumField());

		//	ハイフン
		cntnr.add(createhyphenLabel());

		//	枝番
		cntnr.add(createMediaNumLabel());
		cntnr.add(createMediaNumField());

		// 検索ボタン
		cntnr.add(createSearchButton());

		//	作品名
		cntnr.add(createArtworkNameLabel());
		cntnr.add(createArtworkNameField());

		//	新旧区分
		cntnr.add(createRecentDivLabel());
		cntnr.add(createRecentDivBox());

		//	ジャンル
		cntnr.add(createGenreLabel());
		cntnr.add(createGenreBox());

		//	入荷日
		cntnr.add(createInsertLabel());
		cntnr.add(createInsertField());

		//	削除日
		cntnr.add(createDeleteLabel());
		cntnr.add(createDeleteField());

		// 実行ボタン
		cntnr.add(createExecuteButton());

		// 戻るボタン
		cntnr.add(createBackButton());

		//	メッセージ
		cntnr.add(createMessageLabel());

		componentAllReset();
	}

	//	public static void main(String[] args) {
	//		DvdView dvdView = new DvdView();
	//		dvdView.setVisible(true);//表示メソッド(true:表示、false:非表示)
	//	}

	//	GUI生成

	//	「登録」「更新」「削除」のラジオボタンパネル
	private JPanel createModeSelectPanel() {

		modeSelectPanel = new JPanel();
		modeSelectPanel.setBounds(59, 44, 104, 75);

		//	縦並びに配置
		GridLayout gridLayout = new GridLayout();
		modeSelectPanel.setLayout(gridLayout);
		gridLayout.setRows(3);
		gridLayout.setColumns(1);

		// パネルにラジオボタンを追加
		modeSelectPanel.add(createInsertRadioButton());
		modeSelectPanel.add(createUpdateRadiuoButton());
		modeSelectPanel.add(createDeleteRadioButton());

		//		// ラジオボタンのグループ化
		modeGroup = new ButtonGroup();
		modeGroup.add(insertRadioButton);
		modeGroup.add(updateRadiuoButton);
		modeGroup.add(deleteRadioButton);

		return modeSelectPanel;
	}

	//	ラジオボタン(登録)の生成
	private JRadioButton createInsertRadioButton() {

		insertRadioButton = new JRadioButton("登録");
		//		insertRadioButton.setOpaque(false);

		// 選択された際の処理を追加
		insertRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {

				componentReset();

				// 選択フラグを1(登録)にする
				mode = 1;

				// 作品番号を入力可能にする
				artworkNumField.setFocusable(true);
				artworkNumField.setEditable(true);

				// 媒体番号を入力不可にする
				mediaNumField.setFocusable(false);
				mediaNumField.setEditable(false);

				// 検索ボタンを使用可能にする
				searchButton.setEnabled(true);
			}
		});
		return insertRadioButton;
	}

	//	ラジオボタン(更新)の生成
	private JRadioButton createUpdateRadiuoButton() {

		updateRadiuoButton = new JRadioButton("更新");
		//		updateRadiuoButton.setOpaque(false);

		// 選択された際の処理を追加
		updateRadiuoButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {

				componentReset();

				// 選択フラグを2(更新)状態にする
				mode = 2;

				// 作品番号を入力可能にする
				artworkNumField.setFocusable(true);
				artworkNumField.setEditable(true);

				// 媒体番号を入力不可にする
				mediaNumField.setFocusable(false);
				mediaNumField.setEditable(false);

				// 検索ボタンを使用可能にする
				searchButton.setEnabled(true);
			}
		});
		return updateRadiuoButton;
	}

	//	ラジオボタン(更新)の生成
	private JRadioButton createDeleteRadioButton() {

		deleteRadioButton = new JRadioButton("削除");
		deleteRadioButton.setOpaque(false);

		deleteRadioButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent event) {

				componentReset();

				// 選択フラグを3(削除)にする
				mode = 3;

				// 作品番号と媒体番号を入力可能にする
				artworkNumField.setFocusable(true);
				artworkNumField.setEditable(true);
				mediaNumField.setFocusable(true);
				mediaNumField.setEditable(true);

				// 検索ボタンを使用可能にする
				searchButton.setEnabled(true);
			}
		});
		return deleteRadioButton;
	}

	//	作品番号
	private JLabel createArtworkNumLabel() {
		artworkNumLabel = new JLabel("作品番号");
		artworkNumLabel.setBounds(175, 44, 90, 25);
		artworkNumLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return artworkNumLabel;
	}

	private JTextField createArtworkNumField() {
		artworkNumField = new JTextField();
		artworkNumField.setBounds(177, 69, 110, 30);
		artworkNumField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return artworkNumField;
	}

	//	ハイフン
	private JLabel createhyphenLabel() {
		separetorLabel = new JLabel("－");
		separetorLabel.setBounds(294, 71, 34, 25);
		separetorLabel.setHorizontalAlignment(JLabel.CENTER);
		separetorLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 24));
		return separetorLabel;
	}

	//	枝番
	private JLabel createMediaNumLabel() {
		mediaNumLabel = new JLabel("媒体番号");
		mediaNumLabel.setBounds(334, 41, 70, 25);
		mediaNumLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return mediaNumLabel;
	}

	private JTextField createMediaNumField() {
		mediaNumField = new JTextField();
		mediaNumField.setBounds(334, 68, 60, 30);
		mediaNumField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return mediaNumField;
	}

	//	検索ボタン
	private JButton createSearchButton() {
		searchButton = new JButton("検索");
		searchButton.setBounds(427, 87, 70, 30);
		searchButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 18));
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//	messageLabelテキスト初期化
				messageLabel.setText("");

				//	作品番号に値が入力されていないとき
				if (artworkNumField.getText() == null || artworkNumField.getText().equals("")) {
					messageLabel.setText("作品番号 を入力してください");
					return;
				}

				//	入力された作品番号に対応する作品情報（作品名・区分・ジャンル）取得
				//	作品テーブルのレコードオブジェクト
				ArtworkObj artworkObj = dvdDBAccess.getArtwork(artworkNumField.getText());

				if (artworkObj != null) {
					// 取得した作品情報を表示する
					artworkNameField.setText(artworkObj.getArtworkName());
					recentDivBox.setSelectedItem(artworkObj.getRecentDiv());
					genreBox.setSelectedItem(artworkObj.getGenreName());

				} else { // 入力された作品番号に対応する作品が登録されていない場合
					messageLabel.setText("作品番号 " + artworkNumField.getText() + " 登録されていません。");
					// 新旧区分を選択不可能に
					recentDivBox.setEnabled(false);
					recentDivBox.setFocusable(false);
					// ジャンルを選択不可能に
					genreBox.setEnabled(false);
					genreBox.setFocusable(false);
				}

				if (mode == 1) {
					// 登録準備

					//	作品番号より新規の媒体番号取得・出力
					String newMediaNum = dvdDBAccess.getNextMediaNum(artworkNumField.getText());
					mediaNumField.setText(newMediaNum);

					// 新旧区分を選択不可に
					recentDivBox.setEnabled(false);
					recentDivBox.setFocusable(false);
					// ジャンルを選択不可に
					genreBox.setEnabled(false);
					genreBox.setFocusable(false);
					// 入荷日を入力可能にする
					entryDayField.setFocusable(true);
					entryDayField.setEditable(true);
					// 入荷日に今日の日付を入れる
					entryDayField.setText(getToday());
					// 実行ボタンを使用可能に
					executeButton.setEnabled(true);

				} else if (mode == 2) {
					// 更新準備

					// 新旧区分を選択可能に
					recentDivBox.setEnabled(true);
					recentDivBox.setFocusable(true);
					// ジャンルを選択可能に
					genreBox.setEnabled(true);
					genreBox.setFocusable(true);
					// 実行ボタンを使用可能にする
					executeButton.setEnabled(true);

				} else if (mode == 3) {
					// 削除準備

					//	作品番号フィールドが空値のとき
					if (artworkNumField.getText() == null || artworkNumField.getText().equals("")) {
						messageLabel.setText("作品番号 を入力してください");

					} else {
						//	作品番号から作品名・区分・ジャンルを取得し、オブジェクトを生成
						ArtworkObj obj = dvdDBAccess.getArtwork(artworkNumField.getText());

						//	作品情報が取得できたとき
						if (obj != null) {
							// 作品情報を表示
							artworkNameField.setText(obj.getArtworkName());
							recentDivBox.setSelectedItem(obj.getRecentDiv());
							genreBox.setSelectedItem(obj.getGenreName());
						}
					}

					//	作品番号・媒体番号よりDVDテーブルオブジェクト生成
					DvdObj dvdObj = dvdDBAccess.getDvdRecord(artworkNumField.getText(), mediaNumField.getText());

					//	作品番号・媒体番号に対応するDVD情報が見つからない場合
					if (dvdObj == null) {
						messageLabel.setText("作品番号 " + artworkNumField.getText() + " の"
								+ "媒体番号 " + mediaNumField.getText() + " が見つかりません");
						mediaNumField.setText("");
						return;
					}

					//	substring(開始位置,終了位置)：文字列抜き出しメソッド
					entryDayField.setText(dvdObj.getEntryDay().substring(0, 10));

					//	削除日に値がある場合
					if (dvdObj.getDisuseDay() != null && !dvdObj.getDisuseDay().equals("")) {
						messageLabel.setText("作品番号 " + artworkNumField.getText() + " の"
								+ "媒体番号 " + mediaNumField.getText() + " は既に削除されています。");
						disuseDayField.setText(dvdObj.getDisuseDay().substring(0, 10));
						mediaNumField.setText("");
						return;
					}

					// 実行ボタンを使用可能にする
					executeButton.setEnabled(true);

					// 新旧区分を選択不可に
					recentDivBox.setEnabled(false);
					recentDivBox.setFocusable(false);
					// ジャンルを選択不可に
					genreBox.setEnabled(false);
					genreBox.setFocusable(false);
					// 削除日に今日の日付を入れる
					disuseDayField.setText(getToday());
				}
			}
		});
		return searchButton;
	}

	//	作品名
	private JLabel createArtworkNameLabel() {
		artworkNameLabel = new JLabel("作品名");
		artworkNameLabel.setBounds(15, 150, 70, 25);
		artworkNameLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return artworkNameLabel;
	}

	private JTextField createArtworkNameField() {
		artworkNameField = new JTextField();
		artworkNameField.setBounds(15, 180, 265, 30);
		artworkNameField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return artworkNameField;
	}

	//	新旧区分
	private JLabel createRecentDivLabel() {
		recentDivLabel = new JLabel("新旧区分");
		recentDivLabel.setBounds(290, 150, 80, 25);
		recentDivLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		;
		return recentDivLabel;
	}

	private JComboBox<String> createRecentDivBox() {
		recentDivBox = new JComboBox<String>();
		recentDivBox.setBounds(290, 180, 75, 30);
		recentDivBox.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));

		recentDivBox.addItem("新");
		recentDivBox.addItem("準");
		recentDivBox.addItem("旧");

		return recentDivBox;
	}

	//	ジャンル
	private JLabel createGenreLabel() {
		genreLabel = new JLabel("ジャンル");
		genreLabel.setBounds(390, 150, 80, 25);
		genreLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return genreLabel;
	}

	private JComboBox<String> createGenreBox() {
		genreBox = new JComboBox<String>();
		genreBox.setBounds(390, 180, 130, 30);
		genreBox.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));

		genreBox.addItem("ホラー");
		genreBox.addItem("人生ドラマ");
		genreBox.addItem("アクション");
		genreBox.addItem("カンフー");
		genreBox.addItem("戦争");
		genreBox.addItem("その他");

		return genreBox;
	}

	//	入荷日
	private JLabel createInsertLabel() {
		entryDayLabel = new JLabel("入荷日");
		entryDayLabel.setBounds(15, 235, 70, 25);
		entryDayLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return entryDayLabel;
	}

	private JTextField createInsertField() {
		entryDayField = new JTextField();
		entryDayField.setBounds(15, 265, 150, 30);
		return entryDayField;
	}

	//	削除日
	private JLabel createDeleteLabel() {
		disuseDayLabel = new JLabel("削除日");
		disuseDayLabel.setBounds(200, 235, 70, 25);
		disuseDayLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return disuseDayLabel;
	}

	private JTextField createDeleteField() {
		disuseDayField = new JTextField();
		disuseDayField.setBounds(200, 265, 150, 30);
		return disuseDayField;
	}

	//	実行ボタン
	private JButton createExecuteButton() {

		executeButton = new JButton("実行");
		executeButton.setBounds(370, 280, 70, 30);
		executeButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 18));
		executeButton.setBackground(Color.orange);

		executeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				messageLabel.setText("");

				if (mode == 1) { // 登録の場合
					int result = 0;

					String artworkNum = artworkNumField.getText();
					String mediaNum = mediaNumField.getText();
					String entryDay = entryDayField.getText();

					//	DVDをデータベースに登録
					result = dvdDBAccess.entryDvd(artworkNum, mediaNum, entryDay);

					//	登録処理が正常に行えたとき
					if (result == 1) {
						componentAllReset();
						messageLabel.setText("作品番号 " + artworkNum + " の 媒体番号 " + mediaNum + " を追加しました。");
					}

				} else if (mode == 2) { // 更新の場合

					String artworkNum = artworkNumField.getText();
					String recentDiv = recentDivBox.getItemAt(recentDivBox.getSelectedIndex());
					String genre = genreBox.getItemAt(genreBox.getSelectedIndex());

					//	作品テーブルのレコードオブジェクト生成
					ArtworkObj artworkObj = new ArtworkObj();
					//	レコードオブジェクトに値をセット
					//	作品番号
					if (artworkNum != null && !artworkNum.equals("")) {
						artworkObj.setArtworkNum(artworkNum);
					}
					// 新旧区分
					if (recentDiv != null && !recentDiv.equals("")) {
						artworkObj.setRecentDiv(recentDiv);
					}
					// ジャンル
					if (genre != null && !genre.equals("")) {
						artworkObj.setGenreName(genre);
					}

					// 更新の実行
					int result = dvdDBAccess.updateArtwork(artworkObj);

					if (result == 1) {
						componentAllReset();
						messageLabel.setText("作品番号 " + artworkNum + " を更新しました。");

					} else {
						messageLabel.setText("作品番号 " + artworkNum + " の更新に失敗しました。");
					}

				} else if (mode == 3) { // 削除の場合

					String artworkNum = artworkNumField.getText();
					String mediaNum = mediaNumField.getText();
					String disuseDay = getToday();

					DvdObj dvdObj = new DvdObj();

					dvdObj.setArtworkNumber(artworkNum);
					dvdObj.setMediaNumber(mediaNum);
					dvdObj.setDisuseDay(disuseDay);

					int result = dvdDBAccess.updateDvd(dvdObj);

					if (result == 1) {
						componentAllReset();
						messageLabel.setText("作品番号 " + artworkNum + " の 媒体番号 " + mediaNum + " を削除しました。");

					} else {
						messageLabel.setText("削除処理に失敗しました。");
					}
				}
			}
		});

		return executeButton;
	}

	//	戻るボタン
	private JButton createBackButton() {
		backButton = new JButton();
		backButton.setBounds(450, 280, 70, 30);
		backButton.setText("戻る");
		backButton.setFont(new Font("Dialog", Font.PLAIN, 18));
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				messageLabel.setText("");
				// ウィンドウを閉じる
				DvdView.this.dispose();
			}
		});
		return backButton;
	}

	//	メッセージ
	private JLabel createMessageLabel() {
		messageLabel = new JLabel("");
		messageLabel.setBounds(15, 310, 336, 25);
		return messageLabel;
	}

	private void componentAllReset() {

		//	モード選択解除
		insertRadioButton.setSelected(false);
		updateRadiuoButton.setSelected(false);
		deleteRadioButton.setSelected(false);

		componentReset();
	}

	private void componentReset() {

		artworkNumField.setText("");
		artworkNumField.setEditable(false);
		artworkNumField.setFocusable(false);

		mediaNumField.setText("");
		mediaNumField.setEditable(false);
		mediaNumField.setFocusable(false);

		artworkNameField.setText("");
		artworkNameField.setEditable(false);
		artworkNameField.setFocusable(false);

		recentDivBox.setSelectedIndex(0);
		recentDivBox.setFocusable(false);
		recentDivBox.setEnabled(false);

		genreBox.setSelectedIndex(0);
		genreBox.setFocusable(false);
		genreBox.setEnabled(false);

		entryDayField.setText("");
		entryDayField.setEditable(false);
		entryDayField.setFocusable(false);

		disuseDayField.setText("");
		disuseDayField.setEditable(false);
		disuseDayField.setFocusable(false);

		searchButton.setEnabled(false);

		executeButton.setEnabled(false);

		messageLabel.setText("");
	}

	//	今日の日付（文字列）取得
	private String getToday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(new Date());
	}

}
