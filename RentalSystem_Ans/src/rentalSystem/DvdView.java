package rentalSystem;

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

/**
 * DVD管理画面
 *
 */
public class DvdView extends JFrame {

	/** 選択中のモード　1:登録　2:更新　3:削除*/
	private int selectedMode = 0;

	// 登録、更新、削除のモード選択
	private JPanel modeSelectPanel;
	private JRadioButton registerRadioButton;
	private JRadioButton updateRadiuoButton;
	private JRadioButton deleteRadioButton;
	private ButtonGroup modeGroup;

	// 作品番号と媒体番号（媒体番号）欄
	private JLabel artworkNumberLabel;
	private JTextField artworkNumberField;
	private JLabel mediaNumberLabel;
	private JTextField mediaNumberField;
	private JLabel separetorLabel;
	private JButton searchButton;

	// 作品名欄
	private JLabel artworkNameLabel;
	private JTextField artworkNameField;

	// 新旧区分選択ボックス
	private JLabel recentDivLabel;
	private JComboBox<String> recentDivBox;

	// ジャンル選択ボックス
	private JLabel genreLabel;
	private JComboBox<String> genreBox;

	// 入荷日欄
	private JLabel entryDayLabel;
	private JTextField entryDayField;

	// 削除日欄
	private JLabel disuseDayLabel;
	private JTextField disuseDayField;

	// 実行、戻るボタン
	private JButton executeButton;
	private JButton backButton;

	// メッセージラベル
	private JLabel messageLabel;

	// DBアクセスオブジェクト
	private DvdDBAccess dvdDBAccess;

	/**
	 * コンストラクタ
	 *
	 * @param url データベース接続URL
	 * @param userName ユーザ名
	 * @param password パスワード
	 */
	public DvdView(String url, String userName, String password) {

		super();

		dvdDBAccess = new DvdDBAccess(url, userName, password);

		initialize();
	}

	/**
	 * GUI部品群の初期化
	 */
	private void initialize() {

		this.setTitle("レンタルシステム<DVD管理>");

		this.setBounds(0, 0, 540, 400);
		this.setLayout(null);

		Container container = this.getContentPane();
//		container.setBackground(new Color(155, 155, 255));

		// 機能選択パネル
		container.add(createModeSelectPanel());

		artworkNumberLabel = new JLabel("作品番号");
		artworkNumberLabel.setBounds(175, 44, 90, 25);
		artworkNumberLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		container.add(artworkNumberLabel);
		container.add(createArtworkNumberField());

		separetorLabel = new JLabel("－");
		separetorLabel.setBounds(294, 71, 34, 25);
		separetorLabel.setHorizontalAlignment(JLabel.CENTER);
		separetorLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
		container.add(separetorLabel);

		mediaNumberLabel = new JLabel("媒体番号");
		mediaNumberLabel.setBounds(334, 41, 70, 25);
		mediaNumberLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		container.add(mediaNumberLabel);
		container.add(createMediaNumberField());

		// 検索ボタン
		container.add(createSearchButton());

		artworkNameLabel = new JLabel("作品名");
		artworkNameLabel.setBounds(15, 150, 70, 25);
		artworkNameLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		container.add(artworkNameLabel);
		container.add(createArtworkNameField());

		recentDivLabel = new JLabel("新旧区分");
		recentDivLabel.setBounds(290, 150, 80, 25);
		recentDivLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		container.add(recentDivLabel);
		container.add(createRecentDivBox());

		genreLabel = new JLabel("ジャンル");
		genreLabel.setBounds(390, 150, 80, 25);
		genreLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		container.add(genreLabel);
		container.add(createGenreBox());

		entryDayLabel = new JLabel("入荷日");
		entryDayLabel.setBounds(15, 235, 70, 25);
		entryDayLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		container.add(entryDayLabel);
		container.add(createInsertField());

		disuseDayLabel = new JLabel("削除日");
		disuseDayLabel.setBounds(200, 235, 70, 25);
		disuseDayLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		container.add(disuseDayLabel);
		container.add(createDeleteField());

		// 実行ボタン
		container.add(createExecuteButton());

		// 戻るボタン
		container.add(createBackButton());

		// メッセージラベル
		messageLabel = new JLabel("");
		messageLabel.setBounds(15, 310, 336, 25);
		container.add(messageLabel);

		clearAll();
	}

	/**
	 * 機能選択パネルの生成
	 *
	 * 「登録」「更新」「削除」の機能選択のラジオボタンが登録されたパネルを生成する
	 *
	 * @return 機能選択パネル
	 */
	private JPanel createModeSelectPanel() {

		modeSelectPanel = new JPanel();
		modeSelectPanel.setBounds(15, 45, 140, 75);
//		modeSelectPanel.setBackground(new Color(153, 255, 204));

		GridLayout gridLayout = new GridLayout();
		modeSelectPanel.setLayout(gridLayout);
		gridLayout.setRows(3);
		gridLayout.setColumns(1);

		// パネルにラジオボタンを追加
		modeSelectPanel.add(createRegisterRadioButton());
		modeSelectPanel.add(createUpdateRadiuoButton());
		modeSelectPanel.add(createDeleteRadioButton());

		// ラジオボタンのグループ化
		modeGroup = new ButtonGroup();
		modeGroup.add(registerRadioButton);
		modeGroup.add(updateRadiuoButton);
		modeGroup.add(deleteRadioButton);

		return modeSelectPanel;
	}

	/**
	 * 登録のラジオボタンの生成
	 *
	 * 登録選択時には作品番号欄のみ入力可能となる
	 *
	 * @return 登録のラジオボタン
	 */
	private JRadioButton createRegisterRadioButton() {

		registerRadioButton = new JRadioButton("登録");
		registerRadioButton.setOpaque(false);

		// 選択された際の処理を追加
		registerRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {

				clearDvdInformation();

				// 選択フラグを1(登録)にする
				selectedMode = 1;

				// 作品番号を入力可能にする
				artworkNumberField.setFocusable(true);
				artworkNumberField.setEditable(true);

				// 媒体番号を入力不可にする
				mediaNumberField.setFocusable(false);
				mediaNumberField.setEditable(false);

				// 検索ボタンを使用可能にする
				searchButton.setEnabled(true);

			}
		});

		return registerRadioButton;
	}

	/**
	 * 更新のラジオボタンの生成
	 *
	 * 更新選択時には作品番号欄のみ入力可能となる
	 *
	 * @return 更新のラジオボタン
	 */
	private JRadioButton createUpdateRadiuoButton() {

		updateRadiuoButton = new JRadioButton("更新");
		updateRadiuoButton.setOpaque(false);

		// 選択された際の処理を追加
		updateRadiuoButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {

				clearDvdInformation();

				// 選択フラグを2(更新)状態にする
				selectedMode = 2;

				// 作品番号を入力可能にする
				artworkNumberField.setFocusable(true);
				artworkNumberField.setEditable(true);

				// 媒体番号を入力不可にする
				mediaNumberField.setFocusable(false);
				mediaNumberField.setEditable(false);

				// 検索ボタンを使用可能にする
				searchButton.setEnabled(true);
			}
		});

		return updateRadiuoButton;
	}

	/**
	 * 削除のラジオボタンの生成
	 *
	 * 削除選択時には作品番号と媒体番号の欄のみ入力可能となる
	 *
	 * @return 削除のラジオボタン
	 */
	private JRadioButton createDeleteRadioButton() {

		deleteRadioButton = new JRadioButton("削除");
		deleteRadioButton.setOpaque(false);

		deleteRadioButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent event) {

				clearDvdInformation();

				// 選択フラグを3(削除)にする
				selectedMode = 3;

				// 作品番号と媒体番号を入力可能にする
				artworkNumberField.setFocusable(true);
				artworkNumberField.setEditable(true);
				mediaNumberField.setFocusable(true);
				mediaNumberField.setEditable(true);

				// 検索ボタンを使用可能にする
				searchButton.setEnabled(true);
			}
		});

		return deleteRadioButton;
	}

	/**
	 * 作品番号欄の生成
	 *
	 * @return 作品番号欄
	 */
	private JTextField createArtworkNumberField() {

		artworkNumberField = new JTextField();
		artworkNumberField.setBounds(177, 69, 110, 30);
		artworkNumberField.setFont(new Font("Dialog", Font.PLAIN, 14));

		return artworkNumberField;
	}

	/**
	 * 作品媒体番号欄の生成
	 *
	 * @return 作品媒体番号欄
	 */
	private JTextField createMediaNumberField() {

		mediaNumberField = new JTextField();
		mediaNumberField.setBounds(334, 68, 60, 30);
		mediaNumberField.setFont(new Font("Dialog", Font.PLAIN, 14));

		return mediaNumberField;
	}

	/**
	 * 検索ボタンの生成
	 *
	 * 検索ボタンを押した際の処理はモードによって以下のように分岐する
	 * ・登録
	 * 　　入力された作品番号が存在する場合は、作品情報を表示する<br>
	 * 　　入荷日を編集可能にし、初期値として本日の日付を入力する<br>
	 *
	 * ・更新
	 * 　　作品情報を表示し、編集可能にする
	 *
	 * ・削除
	 * 　　作品情報を表示し、編集可能にする
	 *
	 * @return 検索ボタン
	 */
	private JButton createSearchButton() {

		searchButton = new JButton("検索");
		searchButton.setBounds(427, 87, 70, 30);
		searchButton.setFont(new Font("Dialog", Font.PLAIN, 18));

		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				messageLabel.setText("");

				// 入力された作品番号に対応する作品が登録されていない場合、
				// エラーメッセージを表示する
				if (!setArtWork()) {
					messageLabel.setText("作品番号 " + artworkNumberField.getText() + " 登録されていません。");

					// 新旧区分を選択不可能に
					recentDivBox.setEnabled(false);
					recentDivBox.setFocusable(false);

					// ジャンルを選択不可能に
					genreBox.setEnabled(false);
					genreBox.setFocusable(false);

					return;
				}

				if (selectedMode == 1) {
					// 登録処理

					setNewMediaNumber();

					// 新旧区分を選択不可能に
					recentDivBox.setEnabled(false);
					recentDivBox.setFocusable(false);

					// ジャンルを選択不可能に
					genreBox.setEnabled(false);
					genreBox.setFocusable(false);

					// 入荷日を入力可能にする
					entryDayField.setFocusable(true);
					entryDayField.setEditable(true);

					// 入荷日に今日の日付を入れる
					entryDayField.setText(getToday());

					// 実行ボタンを使用可能にする
					executeButton.setEnabled(true);

				} else if (selectedMode == 2) {
					// 更新処理

					// 新旧区分を選択可能に
					recentDivBox.setEnabled(true);
					recentDivBox.setFocusable(true);

					// ジャンルを選択可能に
					genreBox.setEnabled(true);
					genreBox.setFocusable(true);

					// 実行ボタンを使用可能にする
					executeButton.setEnabled(true);

				} else if (selectedMode == 3) {
					// 削除処理

					setArtWork();
					setEntryDay();

					// 新旧区分を選択不可能に
					recentDivBox.setEnabled(false);
					recentDivBox.setFocusable(false);

					// ジャンルを選択不可能に
					genreBox.setEnabled(false);
					genreBox.setFocusable(false);

					// 削除日に今日の日付を入れる
					disuseDayField.setText(getToday());
				}
			}
		});

		return searchButton;
	}

	/**
	 * 作品区分選択ボックスの生成
	 *
	 * @return 作品区分選択ボックス
	 */
	private JComboBox<String> createRecentDivBox() {

		recentDivBox = new JComboBox<String>();
		recentDivBox.setBounds(290, 180, 75, 30);
		recentDivBox.setFont(new Font("Dialog", Font.PLAIN, 14));

		recentDivBox.addItem("新");
		recentDivBox.addItem("準");
		recentDivBox.addItem("旧");

		return recentDivBox;
	}

	/**
	 * 作品名欄の生成
	 *
	 * @return 作品名欄
	 */
	private JTextField createArtworkNameField() {

		artworkNameField = new JTextField();
		artworkNameField.setBounds(15, 180, 265, 30);
		artworkNameField.setFont(new Font("Dialog", Font.PLAIN, 14));

		return artworkNameField;
	}

	/**
	 * 作品ジャンル選択ボックスの生成
	 *
	 * @return 作品ジャンル選択ボックス
	 */
	private JComboBox<String> createGenreBox() {

		genreBox = new JComboBox<String>();
		genreBox.setBounds(390, 180, 130, 30);
		genreBox.setFont(new Font("Dialog", Font.PLAIN, 14));

		genreBox.addItem("ホラー");
		genreBox.addItem("人生ドラマ");
		genreBox.addItem("アクション");
		genreBox.addItem("カンフー");
		genreBox.addItem("戦争");
		genreBox.addItem("その他");

		return genreBox;
	}

	/**
	 * 入荷日欄の生成
	 *
	 * @return 入荷日欄
	 */
	private JTextField createInsertField() {

		entryDayField = new JTextField();
		entryDayField.setBounds(15, 265, 150, 30);

		return entryDayField;
	}

	/**
	 * 削除日欄の生成
	 *
	 * @return 削除日欄
	 */
	private JTextField createDeleteField() {

		disuseDayField = new JTextField();
		disuseDayField.setBounds(200, 265, 150, 30);

		return disuseDayField;
	}

	/**
	 * 実行ボタンの生成
	 *
	 * 実行ボタンを押した際はモードによって各処理メソッドを実行する
	 *
	 * @return 実行ボタン
	 */
	private JButton createExecuteButton() {

		executeButton = new JButton("実行");
		executeButton.setBounds(370, 280, 70, 30);
		executeButton.setFont(new Font("Dialog", Font.PLAIN, 18));
		executeButton.setBackground(Color.orange);

		executeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				messageLabel.setText("");

				if (selectedMode == 1) {
					// 登録の場合

					entryDvd();

				} else if (selectedMode == 2) {
					// 更新の場合

					updateArtwork();

				} else if (selectedMode == 3) {
					// 削除の場合

					disuseDvd();
				}
			}
		});

		return executeButton;
	}

	/**
	 * 戻るボタンの生成
	 *
	 * 戻るボタンが押された場合は、このフレームを廃棄する
	 *
	 * @return 戻るボタン
	 */
	private JButton createBackButton() {

		backButton = new JButton();
		backButton.setBounds(450, 280, 70, 30);
		backButton.setText("戻る");
		backButton.setFont(new Font("Dialog", Font.PLAIN, 18));

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				messageLabel.setText("");

				DvdView.this.dispose();
			}
		});

		return backButton;
	}

	/**
	 * 作品番号から作品名・区分・ジャンルを取得し、各部品に値を設定する
	 *
	 * @return 取得に成功した場合はtrue、取得できなかった場合はfalse
	 */
	private boolean setArtWork() {

		if (artworkNumberField.getText() == null || artworkNumberField.getText().equals("")) {

			messageLabel.setText("作品番号 を入力してください");
			return false;
		}

		ArtworkObj artworkObj = dvdDBAccess.getArtwork(artworkNumberField.getText());

		if (artworkObj == null) {
			return false;
		}

		// 取得した作品情報を表示する
		artworkNameField.setText(artworkObj.getArtworkName());
		recentDivBox.setSelectedItem(artworkObj.getRecentDiv());
		genreBox.setSelectedItem(artworkObj.getGenreName());

		return true;

	}

	/**
	 * 作品番号と媒体番号から入荷日を取得し、入荷日欄に設定する
	 */
	private void setEntryDay() {

		DvdObj dvdObj = dvdDBAccess.getDvdRecord(artworkNumberField.getText(), mediaNumberField.getText());

		if (dvdObj == null) {

			messageLabel.setText("作品番号 " + artworkNumberField.getText() + " の"
					+ "媒体番号 " + mediaNumberField.getText() + " が見つかりません");

			mediaNumberField.setText("");

			return;
		}

		entryDayField.setText(dvdObj.getEntryDay().substring(0, 10));

		if (dvdObj.getDisuseDay() != null && !dvdObj.getDisuseDay().equals("")) {

			messageLabel.setText("作品番号 " + artworkNumberField.getText() + " の"
					+ "媒体番号 " + mediaNumberField.getText() + " は既に削除されています。");

			disuseDayField.setText(dvdObj.getDisuseDay().substring(0, 10));
			mediaNumberField.setText("");

			return;
		}

		executeButton.setEnabled(true);
	}

	/**
	 * 対象の作品について、新規の媒体番号を取得し、媒体番号欄に設定する
	 *
	 * 新規の媒体番号は、現在の媒体番号の最大値＋1の番号となる
	 *
	 */
	private void setNewMediaNumber() {

		String newMediaNumber = dvdDBAccess.getNextMediaNumber(artworkNumberField.getText());

		mediaNumberField.setText(newMediaNumber);
	}

	/**
	 * DVD情報のクリア
	 *
	 */
	private void clearDvdInformation() {

		artworkNumberField.setText("");
		artworkNumberField.setEditable(false);
		artworkNumberField.setFocusable(false);

		mediaNumberField.setText("");
		mediaNumberField.setEditable(false);
		mediaNumberField.setFocusable(false);

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

	/**
	 * 画面内容のクリア
	 *
	 */
	private void clearAll() {

		modeGroup.clearSelection();

		clearDvdInformation();
	}

	/**
	 * DVDの入荷処理
	 */
	private void entryDvd() {

		int result = 0;

		String artworkNumber = artworkNumberField.getText();
		String mediaNumber = mediaNumberField.getText();
		String entryDay = entryDayField.getText();

		result = dvdDBAccess.entryDvd(artworkNumber, mediaNumber, entryDay);

		if (result == 1) {

			clearAll();

			messageLabel.setText("作品番号 " + artworkNumber + " の 媒体番号 " + mediaNumber + " を追加しました。");
		}
	}

	/**
	 * 作品情報の更新処理
	 */
	private void updateArtwork() {

		String artworkNumber = artworkNumberField.getText();
		String recentDiv = recentDivBox.getItemAt(recentDivBox.getSelectedIndex());
		String genre = genreBox.getItemAt(genreBox.getSelectedIndex());

		ArtworkObj artworkObj = new ArtworkObj();

		if (artworkNumber != null && !artworkNumber.equals("")) {
			artworkObj.setArtworkNumber(artworkNumber);
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

			clearAll();
			messageLabel.setText("作品番号 " + artworkNumber + " を更新しました。");

		} else {

			messageLabel.setText("作品番号 " + artworkNumber + " の更新に失敗しました。");
		}
	}

	/**
	 * DVDの削除処理
	 */
	private void disuseDvd() {

		String artworkNumber = artworkNumberField.getText();
		String mediaNumber = mediaNumberField.getText();
		String disuseDay = getToday();

		DvdObj dvdObj = new DvdObj();

		dvdObj.setArtworkNumber(artworkNumber);
		dvdObj.setMediaNumber(mediaNumber);
		dvdObj.setDisuseDay(disuseDay);

		int result = dvdDBAccess.updateDvd(dvdObj);

		if (result == 1) {

			clearAll();
			messageLabel.setText("作品番号 " + artworkNumber + " の 媒体番号 " + mediaNumber + " を削除しました。");

		} else {
			messageLabel.setText("削除処理に失敗しました。");
		}
	}

	/**
	 * 今日の日付を表す文字列を取得する
	 *
	 * @return 今日の日付の文字列（yyyy/MM/dd 形式）
	 */
	private String getToday() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		return sdf.format(new Date());
	}
}
