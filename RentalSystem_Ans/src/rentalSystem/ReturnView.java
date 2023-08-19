package rentalSystem;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 返却画面クラス
 *
 */
public class ReturnView extends JFrame {

	private ReturnDBAccess returnDBAccess;

	// 返却日
	private JLabel checkInDayLabel;
	private JTextField checkInDayField;

	// 列ラベル
	private JLabel artworkNumberLabel;
	private JLabel mediaNumberLabel;
	private JLabel artworkNameLabel;
	private JLabel termDayLabel;
	private JLabel arrearsLabel;

	// 返却1行目
	private JTextField artworkNumberField_1;
	private JLabel hyphenLabel_1;
	private JTextField mediaNumberField_1;
	private JTextField artworkNameField_1;
	private JTextField termDayField_1;
	private JTextField arrearsField_1;

	// 返却2行目
	private JTextField artworkNumberField_2;
	private JLabel hyphenLabel_2;
	private JTextField mediaNumberField_2;
	private JTextField artworkNameField_2;
	private JTextField termDayField_2;
	private JTextField arrearsField_2;

	// 返却3行目
	private JTextField artworkNumberField_3;
	private JLabel hyphenLabel_3;
	private JTextField mediaNumberField_3;
	private JTextField artworkNameField_3;
	private JTextField termDayField_3;
	private JTextField arrearsField_3;

	private JLabel totalArrearsLabel;
	private JTextField totalArrearsField;
	private JButton executeButton;
	private JButton backButton;

	private JLabel messageLabel;

	private String memberNumber;

	private String rentalNumber1;
	private String rentalNumber2;
	private String rentalNumber3;

	private String artworkNumber1;
	private String artworkNumber2;
	private String artworkNumber3;

	private String mediaNumber1;
	private String mediaNumber2;
	private String mediaNumber3;

	/**
	 * コンストラクタ
	 */
	public ReturnView(String url, String userName, String password) {

		super();

		initialize();

		returnDBAccess = new ReturnDBAccess(url, userName, password);
	}

	/**
	 * GUI部品群の初期化
	 */
	private void initialize() {

		Container container = this.getContentPane();

		this.setTitle("レンタルビデオシステム　＜返却＞");
		this.setSize(530, 570);
		this.setLayout(null);

//		container.setBackground(new Color(205, 155, 255));

		container.add(createCheckInDayLabel());
		container.add(createCheckInDayField());

		container.add(createArtworkNumberLabel());
		container.add(createMediaNumberLabel());
		container.add(createArtworkNameLabel());
		container.add(createTermDayLabel());
		container.add(createArrearsLabel());

		container.add(createArtworkNumberField_1());
		container.add(createHyphenLabel_1());
		container.add(createMediaNumberField_1());
		container.add(createArtworkNameField_1());
		container.add(createTermDayField_1());
		container.add(createArrearsField_1());

		container.add(createArtworkNumberField_2());
		container.add(createHyphenLabel_2());
		container.add(createMediaNumberField_2());
		container.add(createArtworkNameField_2());
		container.add(createTermDayField_2());
		container.add(createArrearsField_2());

		container.add(createArtworkNumberField_3());
		container.add(createHyphenLabel_3());
		container.add(createMediaNumberField_3());
		container.add(createArtworkNameField_3());
		container.add(createTermDayField_3());
		container.add(createArrearsField_3());

		container.add(createTotalArrearsLabel());
		container.add(createTotalArrearsField());

		container.add(createExecuteButton());
		container.add(createBackButton());

		container.add(createMessageLabel());

		clear();
	}

	/**
	 * 画面内容のクリア
	 */
	private void clear() {

		// 作品番号ラベル１以外のEditを不可にし、フォーカスも切る
		checkInDayField.setText("");
		checkInDayField.setEditable(false);
		checkInDayField.setFocusable(false);

		// 返却1行目
		artworkNumberField_1.setText("");

		mediaNumberField_1.setText("");
		mediaNumberField_1.setEditable(false);
		mediaNumberField_1.setFocusable(false);

		artworkNameField_1.setText("");
		artworkNameField_1.setEditable(false);
		artworkNameField_1.setFocusable(false);

		termDayField_1.setText("");
		termDayField_1.setEditable(false);
		termDayField_1.setFocusable(false);

		arrearsField_1.setText("");
		arrearsField_1.setEditable(false);
		arrearsField_1.setFocusable(false);

		// 返却2行目
		artworkNumberField_2.setText("");
		artworkNumberField_2.setEditable(false);
		artworkNumberField_2.setFocusable(false);

		mediaNumberField_2.setText("");
		mediaNumberField_2.setEditable(false);
		mediaNumberField_2.setFocusable(false);

		artworkNameField_2.setText("");
		artworkNameField_2.setEditable(false);
		artworkNameField_2.setFocusable(false);

		termDayField_2.setText("");
		termDayField_2.setEditable(false);
		termDayField_2.setFocusable(false);

		arrearsField_2.setText("");
		arrearsField_2.setEditable(false);
		arrearsField_2.setFocusable(false);

		// 返却3行目
		artworkNumberField_3.setText("");
		artworkNumberField_3.setEditable(false);
		artworkNumberField_3.setFocusable(false);

		mediaNumberField_3.setText("");
		mediaNumberField_3.setEditable(false);
		mediaNumberField_3.setFocusable(false);

		artworkNameField_3.setText("");
		artworkNameField_3.setEditable(false);
		artworkNameField_3.setFocusable(false);

		termDayField_3.setText("");
		termDayField_3.setEditable(false);
		termDayField_3.setFocusable(false);

		arrearsField_3.setText("");
		arrearsField_3.setEditable(false);
		arrearsField_3.setFocusable(false);

		totalArrearsField.setText("");
		totalArrearsField.setEditable(false);
		totalArrearsField.setFocusable(false);

		// 実行ボタンをクリック不可に
		executeButton.setEnabled(false);

		// インスタンス変数の初期化
		memberNumber = null;

		rentalNumber1 = null;
		rentalNumber2 = null;
		rentalNumber3 = null;

		artworkNumber1 = null;
		artworkNumber2 = null;
		artworkNumber3 = null;

		mediaNumber1 = null;
		mediaNumber2 = null;
		mediaNumber3 = null;

		// 今日の日付の設定
		setToday();
	}

	//------------------------------------------------------------------------
	// GUI部品群の生成
	//------------------------------------------------------------------------

	/**
	 * 返却日ラベルの生成
	 *
	 * @return 返却日ラベル
	 */
	private JLabel createCheckInDayLabel() {

		checkInDayLabel = new JLabel("返却日");
		checkInDayLabel.setBounds(10, 50, 60, 25);
		checkInDayLabel.setFont(new Font("Dialog", Font.PLAIN, 14));

		return checkInDayLabel;
	}

	/**
	 * 返却日欄の生成
	 *
	 * @return 返却日欄
	 */
	private JTextField createCheckInDayField() {

		checkInDayField = new JTextField();
		checkInDayField.setSize(170, 35);
		checkInDayField.setLocation(25, 90);
		checkInDayField.setFont(new Font("Dialog", Font.ITALIC, 24));

		return checkInDayField;
	}

	/**
	 * 作品番号ラベルの生成
	 *
	 * @return 作品番号ラベル
	 */
	private JLabel createArtworkNumberLabel() {

		artworkNumberLabel = new JLabel("作品番号");
		artworkNumberLabel.setSize(62, 23);
		artworkNumberLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		artworkNumberLabel.setLocation(20, 220);

		return artworkNumberLabel;
	}

	/**
	 * 枝番（媒体番号）ラベルの生成
	 *
	 * @return 枝番（媒体番号）ラベル
	 */
	private JLabel createMediaNumberLabel() {

		mediaNumberLabel = new JLabel("枝番");
		mediaNumberLabel.setSize(35, 23);
		mediaNumberLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		mediaNumberLabel.setLocation(95, 220);

		return mediaNumberLabel;
	}

	/**
	 * 作品名ラベルの生成
	 *
	 * @return 作品名ラベル
	 */
	private JLabel createArtworkNameLabel() {

		artworkNameLabel = new JLabel("作品名");
		artworkNameLabel.setSize(58, 23);
		artworkNameLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		artworkNameLabel.setLocation(201, 220);

		return artworkNameLabel;
	}

	/**
	 * 返却期限ラベルの生成
	 *
	 * @return 返却期限ラベル
	 */
	private JLabel createTermDayLabel() {

		termDayLabel = new JLabel("返却期限");
		termDayLabel.setSize(79, 23);
		termDayLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		termDayLabel.setLocation(335, 220);

		return termDayLabel;
	}

	/**
	 * 延滞金ラベルの生成
	 *
	 * @return 延滞金ラベル
	 */
	private JLabel createArrearsLabel() {

		arrearsLabel = new JLabel("延滞金");
		arrearsLabel.setSize(58, 23);
		arrearsLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		arrearsLabel.setLocation(440, 220);

		return arrearsLabel;
	}

	/**
	 * 延滞金合計ラベルの生成
	 *
	 * @return 延滞金合計ラベル
	 */
	private JLabel createTotalArrearsLabel() {

		totalArrearsLabel = new JLabel("延滞金合計");
		totalArrearsLabel.setSize(101, 23);
		totalArrearsLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		totalArrearsLabel.setLocation(289, 365);

		return totalArrearsLabel;
	}

	/**
	 * 延滞金合計欄の生成
	 *
	 * @return 延滞金合計欄
	 */
	private JTextField createTotalArrearsField() {

		totalArrearsField = new JTextField();
		totalArrearsField.setSize(90, 30);
		totalArrearsField.setLocation(395, 360);

		return totalArrearsField;
	}

	/**
	 * 実行ボタンの生成
	 *
	 * @return 実行ボタン
	 */
	private JButton createExecuteButton() {

		executeButton = new JButton("実行");
		executeButton.setSize(70, 30);
		executeButton.setLocation(340, 470);
		executeButton.setFont(new Font("Dialog", Font.PLAIN, 18));
//		executeButton.setBackground(Color.orange);

		executeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				executeTermDay();
			}
		});

		return executeButton;
	}

	/**
	 * 戻るボタンの生成
	 *
	 * @return 戻るボタン
	 */
	private JButton createBackButton() {

		backButton = new JButton("戻る");
		backButton.setSize(70, 30);
		backButton.setLocation(420, 470);
		backButton.setFont(new Font("Dialog", Font.PLAIN, 18));

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ReturnView.this.dispose();
			}
		});

		return backButton;
	}

	/**
	 * メッセージラベルの生成
	 *
	 * @return メッセージラベル
	 */
	private JLabel createMessageLabel() {

		messageLabel = new JLabel("");
		messageLabel.setBounds(29, 493, 470, 43);

		return messageLabel;
	}

	//------------------------------------------
	// 返却作品１
	//------------------------------------------

	/**
	 * 「-」ラベルの生成
	 *
	 * @return 「-」ラベル
	 */
	private JLabel createHyphenLabel_1() {

		hyphenLabel_1 = new JLabel("－");
		hyphenLabel_1.setSize(19, 23);
		hyphenLabel_1.setFont(new Font("Dialog", Font.PLAIN, 14));
		hyphenLabel_1.setLocation(78, 249);

		return hyphenLabel_1;
	}

	/**
	 * 作品番号欄１の生成
	 *
	 * @return 作品番号欄１
	 */
	private JTextField createArtworkNumberField_1() {

		artworkNumberField_1 = new JTextField();
		artworkNumberField_1.setSize(55, 20);
		artworkNumberField_1.setLocation(20, 250);

		artworkNumberField_1.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {

				// 変更されていなければ何もしない
				if (artworkNumber1 != null && artworkNumber1.equals(artworkNumberField_1.getText())) {
					return;
				}

				artworkNumber1 = artworkNumberField_1.getText();

				mediaNumberField_1.setText("");
				artworkNameField_1.setText("");
				termDayField_1.setText("");
				arrearsField_1.setText("");
				setTotalArrears();

				messageLabel.setText("");
				setArtworkName(artworkNumberField_1, mediaNumberField_1, artworkNameField_1);
			}
		});

		return artworkNumberField_1;
	}

	/**
	 * 媒体番号欄１の生成
	 *
	 * @return 媒体番号欄１
	 */
	private JTextField createMediaNumberField_1() {

		mediaNumberField_1 = new JTextField();
		mediaNumberField_1.setSize(24, 20);
		mediaNumberField_1.setLocation(98, 250);

		mediaNumberField_1.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {

				// 変更されていなければ何もしない
				if (mediaNumber1 != null && mediaNumber1.equals(mediaNumberField_1.getText())) {
					return;
				}

				mediaNumber1 = mediaNumberField_1.getText();

				termDayField_1.setText("");
				arrearsField_1.setText("");
				setTotalArrears();

				setTermDayInfo(artworkNumberField_1, mediaNumberField_1, termDayField_1, arrearsField_1);

				if (termDayField_1.getText().equals("")) {

					rentalNumber3 = null;

					// 2行目の作品番号入力欄の無効化
					artworkNumberField_2.setEditable(false);
					artworkNumberField_2.setFocusable(false);

				} else {

					rentalNumber1 = returnDBAccess.getRentalNumber(artworkNumberField_1.getText(),
							mediaNumberField_1.getText());

					// 2行目の作品番号入力欄の有効化
					artworkNumberField_2.setEditable(true);
					artworkNumberField_2.setFocusable(true);

					// 実行ボタンの有効化
					executeButton.setEnabled(true);
				}

			}
		});

		return mediaNumberField_1;
	}

	/**
	 * 作品名欄１の生成
	 *
	 * @return 作品名欄１
	 */
	private JTextField createArtworkNameField_1() {

		artworkNameField_1 = new JTextField();
		artworkNameField_1.setSize(175, 20);
		artworkNameField_1.setLocation(140, 250);

		return artworkNameField_1;
	}

	/**
	 * 返却期限欄１の生成
	 *
	 * @return 返却期限欄１
	 */
	private JTextField createTermDayField_1() {

		termDayField_1 = new JTextField();
		termDayField_1.setSize(81, 20);
		termDayField_1.setLocation(330, 250);

		return termDayField_1;
	}

	/**
	 * 延滞金欄１の生成
	 *
	 * @return 延滞金欄１
	 */
	private JTextField createArrearsField_1() {

		arrearsField_1 = new JTextField();
		arrearsField_1.setSize(50, 20);
		arrearsField_1.setLocation(436, 250);

		return arrearsField_1;
	}

	//------------------------------------------
	// 返却作品２
	//------------------------------------------

	/**
	 * 「-」ラベルの生成
	 *
	 * @return 「-」ラベル
	 */
	private JLabel createHyphenLabel_2() {

		hyphenLabel_2 = new JLabel();
		hyphenLabel_2.setSize(19, 23);
		hyphenLabel_2.setText("－");
		hyphenLabel_2.setLocation(78, 290);
		hyphenLabel_2.setFont(new Font("Dialog", Font.PLAIN, 14));

		return hyphenLabel_2;
	}

	/**
	 * 作品番号欄２の生成
	 *
	 * @return 作品番号欄２
	 */
	private JTextField createArtworkNumberField_2() {

		artworkNumberField_2 = new JTextField();
		artworkNumberField_2.setSize(55, 20);
		artworkNumberField_2.setLocation(20, 290);

		artworkNumberField_2.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {

				// 変更されていなければ何もしない
				if (artworkNumber2 != null && artworkNumber2.equals(artworkNumberField_2.getText())) {
					return;
				}

				artworkNumber2 = artworkNumberField_2.getText();

				mediaNumberField_2.setText("");
				artworkNameField_2.setText("");
				termDayField_2.setText("");
				arrearsField_2.setText("");
				setTotalArrears();

				messageLabel.setText("");
				setArtworkName(artworkNumberField_2, mediaNumberField_2, artworkNameField_2);
			}
		});

		return artworkNumberField_2;
	}

	/**
	 * 媒体番号欄２の生成
	 *
	 * @return 媒体番号欄２
	 */
	private JTextField createMediaNumberField_2() {

		mediaNumberField_2 = new JTextField();
		mediaNumberField_2.setSize(24, 20);
		mediaNumberField_2.setLocation(98, 290);

		mediaNumberField_2.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {

				// 変更されていなければ何もしない
				if (mediaNumber2 != null && mediaNumber2.equals(mediaNumberField_2.getText())) {
					return;
				}

				mediaNumber2 = mediaNumberField_2.getText();

				termDayField_2.setText("");
				arrearsField_2.setText("");
				setTotalArrears();

				setTermDayInfo(artworkNumberField_2, mediaNumberField_2, termDayField_2, arrearsField_2);

				if (termDayField_2.getText().equals("")) {

					rentalNumber2 = null;

					// 3行目の作品番号入力欄の無効化
					artworkNumberField_3.setEditable(false);
					artworkNumberField_3.setFocusable(false);

				} else {

					rentalNumber2 = returnDBAccess.getRentalNumber(artworkNumberField_2.getText(),
							mediaNumberField_2.getText());

					// 3行目の作品番号入力欄の有効化
					artworkNumberField_3.setEditable(true);
					artworkNumberField_3.setFocusable(true);
				}

			}
		});

		return mediaNumberField_2;
	}

	/**
	 * 作品名欄２の生成
	 *
	 * @return 作品名欄２
	 */
	private JTextField createArtworkNameField_2() {

		artworkNameField_2 = new JTextField();
		artworkNameField_2.setSize(175, 20);
		artworkNameField_2.setLocation(140, 290);

		return artworkNameField_2;
	}

	/**
	 * 返却期限欄２の生成
	 *
	 * @return 返却期限欄２
	 */
	private JTextField createTermDayField_2() {

		termDayField_2 = new JTextField();
		termDayField_2.setSize(81, 20);
		termDayField_2.setLocation(330, 290);

		return termDayField_2;
	}

	/**
	 * 延滞金欄２の生成
	 *
	 * @return 延滞金欄２
	 */
	private JTextField createArrearsField_2() {

		arrearsField_2 = new JTextField();
		arrearsField_2.setSize(50, 20);
		arrearsField_2.setLocation(436, 290);

		return arrearsField_2;
	}

	//------------------------------------------
	// 返却作品３
	//------------------------------------------

	/**
	 * 「-」ラベルの生成
	 *
	 * @return 「-」ラベル
	 */
	private JLabel createHyphenLabel_3() {

		hyphenLabel_3 = new JLabel();
		hyphenLabel_3.setSize(19, 23);
		hyphenLabel_3.setText("－");
		hyphenLabel_3.setFont(new Font("Dialog", Font.PLAIN, 14));
		hyphenLabel_3.setLocation(78, 330);

		return hyphenLabel_3;
	}

	/**
	 * 作品番号欄３の生成
	 *
	 * @return 作品番号欄
	 */
	private JTextField createArtworkNumberField_3() {

		artworkNumberField_3 = new JTextField();
		artworkNumberField_3.setSize(55, 20);
		artworkNumberField_3.setLocation(20, 330);

		artworkNumberField_3.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {

				// 変更されていなければ何もしない
				if (artworkNumber3 != null && artworkNumber3.equals(artworkNumberField_3.getText())) {
					return;
				}

				artworkNumber3 = artworkNumberField_3.getText();

				mediaNumberField_3.setText("");
				artworkNameField_3.setText("");
				termDayField_3.setText("");
				arrearsField_3.setText("");
				setTotalArrears();

				messageLabel.setText("");
				setArtworkName(artworkNumberField_3, mediaNumberField_3, artworkNameField_3);
			}
		});

		return artworkNumberField_3;
	}

	/**
	 * 媒体番号欄３の生成
	 *
	 * @return 媒体番号欄３
	 */
	private JTextField createMediaNumberField_3() {

		mediaNumberField_3 = new JTextField();
		mediaNumberField_3.setSize(24, 20);
		mediaNumberField_3.setLocation(98, 330);

		mediaNumberField_3.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {

				// 変更されていなければ何もしない
				if (mediaNumber3 != null && mediaNumber3.equals(mediaNumberField_3.getText())) {
					return;
				}

				mediaNumber3 = mediaNumberField_3.getText();

				termDayField_3.setText("");
				arrearsField_3.setText("");
				setTotalArrears();

				setTermDayInfo(artworkNumberField_3, mediaNumberField_3, termDayField_3, arrearsField_3);

				if (!termDayField_3.getText().equals("")) {
					rentalNumber3 = returnDBAccess.getRentalNumber(artworkNumberField_3.getText(),
							mediaNumberField_3.getText());
				} else {
					rentalNumber3 = null;
				}
			}
		});

		return mediaNumberField_3;
	}

	/**
	 * 作品名欄３の生成
	 *
	 * @return 作品名欄３
	 */
	private JTextField createArtworkNameField_3() {

		artworkNameField_3 = new JTextField();
		artworkNameField_3.setSize(175, 20);
		artworkNameField_3.setLocation(140, 330);

		return artworkNameField_3;
	}

	/**
	 * 返却期限欄３の生成
	 *
	 * @return 返却期限欄３
	 */
	private JTextField createTermDayField_3() {

		termDayField_3 = new JTextField();
		termDayField_3.setSize(81, 20);
		termDayField_3.setLocation(330, 330);

		return termDayField_3;
	}

	/**
	 * 延滞金欄３の生成
	 *
	 * @return 延滞金欄３
	 */
	private JTextField createArrearsField_3() {

		arrearsField_3 = new JTextField();
		arrearsField_3.setSize(50, 20);
		arrearsField_3.setLocation(436, 330);

		return arrearsField_3;
	}

	//------------------------------------------------------------------------
	// 内部メソッド群
	//------------------------------------------------------------------------

	/**
	 * 日付設定
	 */
	private void setToday() {

		// 本日日付の取得
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		checkInDayField.setText(sdf.format(new Date()));
	}

	/**
	 * 作品名設定メソッド
	 *
	 * 作品番号、媒体番号、作品名の欄
	 * を受け取り、作品番号から作品名が取得できれば
	 * それを表示し、媒体番号欄を有効化する。
	 *
	 * @param artworkNumberField
	 * @param mediaNumberField
	 * @param artworkNameField
	 */
	private void setArtworkName(JTextField artworkNumberField, JTextField mediaNumberField,
			JTextField artworkNameField) {

		// 作品番号が空白なら何もしない
		if (artworkNumberField.getText().equals("")) {
			return;
		}

		// 作品名の取得
		String artworkName = returnDBAccess.getArtworkName(artworkNumberField.getText());

		// 作品名が取得できない場合はエラーメッセージを表示する
		if (artworkName == null) {

			messageLabel.setText("作品番号 " + artworkNumberField.getText() + " が見つかりませんでした。");

			return;
		}

		// 作品名の表示
		artworkNameField.setText(artworkName);

		// 媒体番号欄の有効化
		mediaNumberField.setEditable(true);
		mediaNumberField.setFocusable(true);
	}

	/**
	 * 返却情報設定
	 *
	 * 作品番号と媒体番号を基にDBに対して
	 * 問い合わせを行い、返却期限、延滞料金を設定する
	 *
	 * @param artworkNumberField
	 * @param mediaNumberField
	 * @param termDayField
	 * @param arrearsField
	 */
	private void setTermDayInfo(JTextField artworkNumberField, JTextField mediaNumberField, JTextField termDayField,
			JTextField arrearsField) {

		// 媒体番号欄が空なら何もしない
		if (mediaNumberField.getText().equals("")) {
			return;
		}

		// 貸出会員番号を取得
		String borrowersNumber = returnDBAccess.getBorrowersNumber(artworkNumberField.getText(),
				mediaNumberField.getText());

		// 会員番号を取得できない場合はエラーメッセージを表示する
		if (borrowersNumber == null) {

			messageLabel.setText(
					"作品番号 " + artworkNumberField.getText() + " の "
							+ "枝番（媒体番号） " + mediaNumberField.getText() + " は貸出中ではありません。");

			return;
		}

		// memberNuberが null
		if (memberNumber == null) {

			memberNumber = borrowersNumber; // 会員番号を設定

		} else {

			// 同じ会員による返却でない場合はエラーメッセージを表示する
			if (!borrowersNumber.equals(memberNumber)) {

				messageLabel.setText("利用者が異なります");
				executeButton.setEnabled(false);

				return;
			}
		}

		// 返却期限の取得
		Date termDay = returnDBAccess.getTermDay(artworkNumberField.getText(), mediaNumberField.getText());

		if (termDay != null) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

			// 返却期限の表示
			termDayField.setText(sdf.format(termDay));

			// 遅延日数を計算
			long between = calcArrears(termDay);

			// 延滞している場合
			if (between > 0) {

				int arrearsPerUnitDay = returnDBAccess.getArrearsPerUnitDay(artworkNumberField.getText());

				// 延滞料金を表示（延滞料金 = 1日あたりの延滞料金 * 延滞日数）
				arrearsField.setText(String.valueOf(arrearsPerUnitDay * between));

				setTotalArrears();

			}

			// 実行ボタンの有効化
			executeButton.setEnabled(true);

		}
	}

	/**
	 * 延滞日数計算
	 *
	 * @param termDayLimit 返却期限
	 */
	private long calcArrears(Date termDayLimit) {

		Date today = new Date();

		// 今日の日付-返却期限
		long between = today.getTime() - termDayLimit.getTime();

		// ミリ秒を日に換算してリターン
		return between / (1000 * 60 * 60 * 24);
	}

	/**
	 * 合計金額の表示メソッド
	 *
	 * 合計金額を表示し、結果として
	 * 0でなければ、「実行」ボタンの押下を可能にする
	 */
	private void setTotalArrears() {

		int total = 0;

		String arrears1 = arrearsField_1.getText();
		String arrears2 = arrearsField_2.getText();
		String arrears3 = arrearsField_3.getText();

		if (!arrears1.equals("")) {
			total += Integer.parseInt(arrears1);
		}

		if (!arrears2.equals("")) {
			total += Integer.parseInt(arrears2);
		}

		if (!arrears3.equals("")) {
			total += Integer.parseInt(arrears3);
		}

		totalArrearsField.setText(total + "");
	}

	/**
	 * 返却メソッド
	 *
	 * 既に設定済みのi_rentalNumberXの値を基に
	 * DB貸出テーブルの返却日・延滞金欄に
	 * 更新を行う
	 */
	private void executeTermDay() {

		int result = 0;

		if (!termDayField_1.getText().equals("")) {
			result = returnDBAccess.checkIn(rentalNumber1, checkInDayField.getText(), arrearsField_1.getText());
		}

		if (!termDayField_2.getText().equals("")) {
			result = returnDBAccess.checkIn(rentalNumber2, checkInDayField.getText(), arrearsField_2.getText());
		}

		if (!termDayField_3.getText().equals("")) {
			result = returnDBAccess.checkIn(rentalNumber3, checkInDayField.getText(), arrearsField_3.getText());
		}

		if (result == 1) {

			clear();
			messageLabel.setText("返却処理が完了しました。");

		} else {
			messageLabel.setText("返却処理に失敗しました。");
		}
	}
}
