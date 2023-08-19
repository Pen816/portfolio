package rentalSystem;

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
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 貸出画面クラス
 *
 * 画面表示と、データベースへのアクセスを兼ねている
 */
public class RentalView extends JFrame {

	private int newRentalNumber = 0; // 貸出番号

	// 会員情報
	private JLabel memberNumberLabel;
	private JTextField memberNumberField;
	private JLabel memberNameLabel;
	private JTextField memberNameField;
	private JLabel rankDivLabel;
	private JTextField rankDivField;

	// 会員情報検索ボタン
	private JButton searchButton;

	// 貸出日
	private JLabel checkOutDayLabel;
	private JTextField checkOutDayField;

	// 列タイトルラベル
	private JLabel rentalNumberLabel;
	private JLabel artworkNumberLabel;
	private JLabel mediaNumberLabel;
	private JLabel artworkNameLabel;
	private JLabel termDayLabel;
	private JLabel rentalChargeLabel;

	// 貸出内容1行目
	private JTextField rentalNumberField_1;
	private JTextField artworkNumberField_1;
	private JLabel hyphenLabel_1;
	private JTextField mediaNumberField_1;
	private JTextField artworkNameField_1;
	private JTextField termDayField_1;
	private JTextField rentalChargeField_1;

	// 貸出内容2行目
	private JTextField rentalNumberField_2;
	private JTextField artworkNumberField_2;
	private JLabel hyphenLabel_2;
	private JTextField mediaNumberField_2;
	private JTextField artworkNameField_2;
	private JTextField termDayField_2;
	private JTextField rentalChargeField_2;

	// 貸出内容3行目
	private JTextField rentalNumberField_3;
	private JTextField artworkNumberField_3;
	private JLabel hyphenLabel_3;
	private JTextField mediaNumberField_3;
	private JTextField artworkNameField_3;
	private JTextField termDayField_3;
	private JTextField rentalChargeField_3;

	// 合計金額
	private JTextField totalField;
	private JLabel totalLabel;

	// 実行ボタン、戻るボタン
	private JButton executeButton;
	private JButton backButton;

	// メッセージラベル（追加)
	private JLabel messageLabel;

	//------------------------------------------------------------------------
	// データベース接続に必要な変数群
	//------------------------------------------------------------------------
	private final String url;
	private final String userName;
	private final String password;

	/**
	 * コンストラクタ
	 *
	 * @param connection データベース接続済みコネクション
	 */
	public RentalView(String url, String userName, String password) {

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

		Container container = this.getContentPane();

		this.setTitle("レンタルシステム<貸出>");
		this.setBounds(0, 0, 550, 600);
//		container.setBackground(new Color(205, 155, 255));

		this.setLayout(null);

		// 会員情報検索
		container.add(createMemberNumberLabel());
		container.add(createMemberNumberField());
		container.add(createSearchButton());

		// 会員情報検索結果
		container.add(createMemberNameLabel());
		container.add(createMemberNameField());
		container.add(createRankDivLabel());
		container.add(createRankDivField());

		// 貸出日の表示
		container.add(createCheckOutDayLabel());
		container.add(createCheckOutDayField());

		// 列タイトル
		container.add(createRentalNumberLabel());
		container.add(createArtworkNumberLabel());
		container.add(createMediaNumberLabel());
		container.add(createArtworkNameLabel());
		container.add(createTermDayLabel());
		container.add(createChargeLabel());

		// 貸出1行目
		container.add(createRentalNumberField_1());
		container.add(createArtworkNumberField_1());
		container.add(createHyphenLabel_1());
		container.add(createMediaNumberField_1());
		container.add(createArtworkNameField_1());
		container.add(createTermDayField_1());
		container.add(createChargeField_1());

		// 貸出2行目
		container.add(createRentalNumberField_2());
		container.add(createArtworkNumberField_2());
		container.add(createHyphenLabel_2());
		container.add(createMediaNumberField_2());
		container.add(createArtworkNameField_2());
		container.add(createTermDayField_2());
		container.add(createChargeField_2());

		// 貸出3行目
		container.add(createRentalNumberField_3());
		container.add(createArtworkNumberField_3());
		container.add(createHyphenLabel_3());
		container.add(createMediaNumberField_3());
		container.add(createArtworkNameField_3());
		container.add(createTermDayField_3());
		container.add(createChargeField_3());

		// 合計金額
		container.add(createTotalField());
		container.add(createTotalLabel());

		// 貸出実行、戻るボタン
		container.add(createExecButton());
		container.add(createBackButton());

		// メッセージラベル
		container.add(createMessageLabel());

		clear(); // 部品のフォーカスを初期状態へ

	}

	//------------------------------------------------------------------------
	// GUI部品群の生成
	//------------------------------------------------------------------------

	/**
	 * 会員番号ラベルの生成
	 *
	 * @return 会員番号ラベル
	 */
	private JLabel createMemberNumberLabel() {

		memberNumberLabel = new JLabel("会員番号");
		memberNumberLabel.setBounds(10, 35, 65, 25);
		memberNumberLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		memberNumberLabel.setBackground(new Color(205, 155, 255));

		return memberNumberLabel;
	}

	/**
	 * 会員番号欄の生成
	 *
	 * @return 会員番号欄
	 */
	private JTextField createMemberNumberField() {

		memberNumberField = new JTextField();
		memberNumberField.setSize(200, 35);
		memberNumberField.setFont(new Font("Dialog", Font.PLAIN, 20));
		memberNumberField.setLocation(25, 60);

		return memberNumberField;
	}

	/**
	 * 検索ボタンの生成
	 *
	 * @return 検索ボタン
	 */
	private JButton createSearchButton() {

		searchButton = new JButton("検索");
		searchButton.setSize(70, 30);
		searchButton.setFont(new Font("Dialog", Font.PLAIN, 18));
		searchButton.setLocation(387, 56);

		searchButton.addActionListener(new ActionListener() {

			// 検索ボタン押下時の処理
			public void actionPerformed(ActionEvent e) {

				// メッセージラベルの初期化
				messageLabel.setText("");

				String sql = "SELECT * FROM MEMBER_TABLE WHERE member_num = ? AND leave_day IS NULL";

				// 会員情報の照会
				try (Connection connection = DriverManager.getConnection(url, userName, password);
						PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

					preparedStatement.setString(1, memberNumberField.getText());

					try (ResultSet resultSet = preparedStatement.executeQuery();) {

						if (resultSet.next()) {

							// 会員情報の設定
							memberNameField.setText(resultSet.getString("member_name"));
							rankDivField.setText(resultSet.getString("rank_div"));

							// 貸出日の設定
							checkOutDayField.setText(getToday());

							// 作品番号欄１の有効化
							artworkNumberField_1.setEditable(true);
							artworkNumberField_1.setFocusable(true);

						} else {

							messageLabel.setText("会員番号 " + memberNumberField.getText() + " が見つかりません。");
						}
					}

				} catch (Exception exception) {

					// 作品番号欄１の無効化
					artworkNumberField_1.setEditable(false);
					artworkNumberField_1.setFocusable(false);

					exception.printStackTrace();
				}
			}
		});

		return searchButton;
	}

	/**
	 * 会員氏名ラベルの生成
	 *
	 * @return 会員氏名
	 */
	private JLabel createMemberNameLabel() {

		memberNameLabel = new JLabel("会員氏名");
		memberNameLabel.setBounds(10, 110, 65, 25);
		memberNameLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		memberNameLabel.setBackground(new Color(205, 155, 255));

		return memberNameLabel;
	}

	/**
	 * 会員氏名欄の生成
	 *
	 * @return 会員氏名欄
	 */
	private JTextField createMemberNameField() {

		memberNameField = new JTextField();
		memberNameField.setBounds(25, 140, 250, 35);
		memberNameField.setFont(new Font("ＭＳ ゴシック", Font.ITALIC, 24));

		return memberNameField;
	}

	/**
	 * 会員優良区分ラベルの生成
	 *
	 * @return 会員優良区分ラベル
	 */
	private JLabel createRankDivLabel() {

		rankDivLabel = new JLabel("会員優良区分");
		rankDivLabel.setSize(85, 23);
		rankDivLabel.setLocation(300, 110);
		rankDivLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		rankDivLabel.setBackground(new Color(205, 155, 255));

		return rankDivLabel;
	}

	/**
	 * 会員優良区分欄の生成
	 *
	 * @return 会員優良区分欄
	 */
	private JTextField createRankDivField() {

		rankDivField = new JTextField();
		rankDivField.setSize(30, 35);
		rankDivField.setLocation(316, 140);
		rankDivField.setFont(new Font("Dialog", Font.PLAIN, 24));

		return rankDivField;
	}

	/**
	 * 貸出日ラベルの生成
	 *
	 * @return 貸出日ラベル
	 */
	private JLabel createCheckOutDayLabel() {

		checkOutDayLabel = new JLabel("貸出日");
		checkOutDayLabel.setSize(60, 25);
		checkOutDayLabel.setLocation(10, 190);
		checkOutDayLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		checkOutDayLabel.setBackground(new Color(205, 155, 255));

		return checkOutDayLabel;
	}

	/**
	 * 貸出日欄の生成
	 *
	 * @return 貸出日欄
	 */
	private JTextField createCheckOutDayField() {

		checkOutDayField = new JTextField();
		checkOutDayField.setSize(170, 35);
		checkOutDayField.setLocation(25, 220);
		checkOutDayField.setFont(new Font("Dialog", Font.ITALIC, 24));

		return checkOutDayField;
	}

	/**
	 * 貸出番号ラベルの生成
	 *
	 * @return JLabel
	 */
	private JLabel createRentalNumberLabel() {

		rentalNumberLabel = new JLabel("貸出番号");
		rentalNumberLabel.setSize(63, 23);
		rentalNumberLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		rentalNumberLabel.setLocation(12, 270);
		rentalNumberLabel.setBackground(new Color(205, 155, 255));

		return rentalNumberLabel;
	}

	/**
	 * 作品番号ラベルの生成
	 *
	 * @return 作品番号ラベル
	 */
	private JLabel createArtworkNumberLabel() {

		artworkNumberLabel = new JLabel("作品番号");
		artworkNumberLabel.setSize(61, 23);
		artworkNumberLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		artworkNumberLabel.setLocation(88, 270);
		artworkNumberLabel.setBackground(new Color(205, 155, 255));

		return artworkNumberLabel;
	}

	/**
	 * 枝番（媒体番号）ラベルの生成
	 *
	 * @return 枝番（媒体番号）ラベル
	 */
	private JLabel createMediaNumberLabel() {

		mediaNumberLabel = new JLabel("枝番");
		mediaNumberLabel.setSize(36, 23);
		mediaNumberLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		mediaNumberLabel.setLocation(155, 270);
		mediaNumberLabel.setBackground(new Color(205, 155, 255));

		return mediaNumberLabel;
	}

	/**
	 * 作品名ラベルの生成
	 *
	 * @return 作品名ラベル
	 */
	private JLabel createArtworkNameLabel() {

		artworkNameLabel = new JLabel("作品名");
		artworkNameLabel.setSize(55, 23);
		artworkNameLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		artworkNameLabel.setLocation(250, 270);
		artworkNameLabel.setBackground(new Color(205, 155, 255));

		return artworkNameLabel;
	}

	/**
	 * 返却期限ラベルの生成
	 *
	 * @return 返却期限ラベル
	 */
	private JLabel createTermDayLabel() {

		termDayLabel = new JLabel("返却期限");
		termDayLabel.setSize(77, 23);
		termDayLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		termDayLabel.setLocation(372, 270);
		termDayLabel.setBackground(new Color(205, 155, 255));

		return termDayLabel;
	}

	/**
	 * 料金ラベルの生成
	 *
	 * @return 料金ラベル
	 */
	private JLabel createChargeLabel() {

		rentalChargeLabel = new JLabel("料金");
		rentalChargeLabel.setSize(33, 23);
		rentalChargeLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		rentalChargeLabel.setLocation(480, 270);
		rentalChargeLabel.setBackground(new Color(205, 155, 255));

		return rentalChargeLabel;
	}

	//-------------
	// 貸出１
	//-------------
	/**
	 * 貸出番号欄１の生成
	 *
	 * @return 貸出番号欄１
	 */
	private JTextField createRentalNumberField_1() {

		rentalNumberField_1 = new JTextField();
		rentalNumberField_1.setSize(65, 20);
		rentalNumberField_1.setColumns(6);
		rentalNumberField_1.setLocation(12, 305);

		return rentalNumberField_1;
	}

	/**
	 * 作品番号欄１の生成
	 *
	 * @return 作品番号欄１
	 */
	private JTextField createArtworkNumberField_1() {

		artworkNumberField_1 = new JTextField();
		artworkNumberField_1.setSize(55, 20);
		artworkNumberField_1.setLocation(89, 305);

		artworkNumberField_1.addFocusListener(new FocusListener() {

			// この欄にフォーカスした時の処理
			@Override
			public void focusGained(FocusEvent e) {

				// 貸出番号欄1が空なら新規貸出番号を設定する
				if (rentalNumberField_1.getText().equals("")) {

					// 新規貸出番号の設定
					rentalNumberField_1.setText(getNewRentalNumber());
				}
			}

			// この欄からフォーカスを外した時の処理
			@Override
			public void focusLost(FocusEvent e) {

				messageLabel.setText("");

				// 作品名を取得
				String artworkName = getArtworkName(artworkNumberField_1.getText());

				if (artworkName != null) {
					// 作品があれば枝番（媒体番号）を入力可能にする

					artworkNameField_1.setText(artworkName);
					mediaNumberField_1.setFocusable(true);
					mediaNumberField_1.setEditable(true);

				} else {
					// 作品がない場合エラーメッセージを表示する

					messageLabel.setText("作品番号 " + artworkNumberField_1.getText() + " が見つかりません。");

					artworkNameField_1.setText("");
					mediaNumberField_1.setText("");
					termDayField_1.setText("");
					rentalChargeField_1.setText("");
					mediaNumberField_1.setFocusable(false);
					mediaNumberField_1.setEditable(false);
				}
			}
		});

		return artworkNumberField_1;
	}

	/**
	 * ハイフンラベル１
	 *
	 * @return ハイフンラベル１
	 */
	private JLabel createHyphenLabel_1() {

		hyphenLabel_1 = new JLabel("－");
		hyphenLabel_1.setSize(14, 23);
		hyphenLabel_1.setLocation(145, 305);
		hyphenLabel_1.setBackground(new Color(205, 155, 255));

		return hyphenLabel_1;
	}

	/**
	 * 枝番（媒体番号）欄１の生成
	 *
	 * @return 枝番（媒体番号）欄１
	 */
	private JTextField createMediaNumberField_1() {

		mediaNumberField_1 = new JTextField();

		mediaNumberField_1.setSize(24, 20);
		mediaNumberField_1.setLocation(160, 305);
		mediaNumberField_1.setColumns(2);

		mediaNumberField_1.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {

				messageLabel.setText("");

				if (!mediaNumberField_1.getText().equals("")) {

					int dvdState = getDvdState(artworkNumberField_1.getText(), mediaNumberField_1.getText());

					if (dvdState == 0) {
						// 貸出可能な場合

						// 料金と貸出期限の設定
						rentalChargeField_1.setText(getRentalCharge(artworkNumberField_1.getText()));
						termDayField_1.setText(getTermDay(artworkNumberField_1.getText()));

						// 作品番号欄２の有効化
						artworkNumberField_2.setFocusable(true);
						artworkNumberField_2.setEditable(true);

						// 合計金額の算出
						setTotal();

					} else {
						// 貸出不可能な場合エラーメッセージを表示する

						if (dvdState == -1) {
							// DVDが登録されていない場合

							messageLabel.setText(
									"作品番号 " + artworkNumberField_1.getText() + " の "
											+ "枝番（媒体番号） " + mediaNumberField_1.getText() + " が"
											+ "見つかりません。");

						} else if (dvdState == 2) {
							// DVDが貸出停止の場合

							messageLabel.setText(
									"作品番号 " + artworkNumberField_1.getText() + " の "
											+ "枝番（媒体番号） " + mediaNumberField_1.getText() + " は"
											+ "貸出を行っておりません。");

						} else if (dvdState == 1) {
							// DVDが貸出中の場合

							messageLabel.setText(
									"作品番号 " + artworkNumberField_1.getText() + " の "
											+ "枝番（媒体番号） " + mediaNumberField_1.getText() + " は"
											+ "現在貸出中です。");

						}

						// 料金と貸出期限を初期化
						termDayField_1.setText("");
						rentalChargeField_1.setText("");

						// 作品番号欄２の無効化
						artworkNumberField_2.setFocusable(false);
						artworkNumberField_2.setEditable(false);
					}
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
		artworkNameField_1.setLocation(187, 305);
		artworkNameField_1.setColumns(30);

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
		termDayField_1.setLocation(370, 305);
		termDayField_1.setColumns(10);

		return termDayField_1;
	}

	/**
	 * 料金欄１の生成
	 *
	 * @return 料金欄１
	 */
	private JTextField createChargeField_1() {

		rentalChargeField_1 = new JTextField();
		rentalChargeField_1.setSize(50, 20);
		rentalChargeField_1.setColumns(4);
		rentalChargeField_1.setLocation(470, 305);

		return rentalChargeField_1;
	}

	//------------
	// 貸出２
	//------------
	/**
	 * 貸出番号欄２の生成
	 *
	 * @return 貸出番号欄２
	 */
	private JTextField createRentalNumberField_2() {

		rentalNumberField_2 = new JTextField();
		rentalNumberField_2.setSize(65, 20);
		rentalNumberField_2.setLocation(12, 340);

		return rentalNumberField_2;
	}

	/**
	 * 作品番号欄２の生成
	 *
	 * @return 作品番号欄２
	 */
	private JTextField createArtworkNumberField_2() {

		artworkNumberField_2 = new JTextField();
		artworkNumberField_2.setSize(55, 20);
		artworkNumberField_2.setLocation(89, 340);

		artworkNumberField_2.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {

				if (rentalNumberField_2.getText().equals("")) {
					rentalNumberField_2.setText(getNewRentalNumber());
				}
			}

			@Override
			public void focusLost(FocusEvent e) {

				// 作品名を取得
				String artworkName = getArtworkName(artworkNumberField_2.getText());

				// 作品があれば枝番（媒体番号）を入力可能にする
				if (artworkName != null) {

					artworkNameField_2.setText(artworkName);
					mediaNumberField_2.setEditable(true);
					mediaNumberField_2.setFocusable(true);

				} else {
					// 作品がない場合エラーメッセージを表示する

					messageLabel.setText("作品番号 " + artworkNumberField_2.getText() + " が見つかりません。");

					artworkNameField_2.setText("");
					mediaNumberField_2.setText("");
					termDayField_2.setText("");
					rentalChargeField_2.setText("");
					mediaNumberField_2.setFocusable(false);
					mediaNumberField_2.setEditable(false);
				}
			}
		});

		return artworkNumberField_2;
	}

	/**
	 * 枝番（媒体番号）欄２の生成
	 *
	 * @return 枝番（媒体番号）欄２
	 */
	private JTextField createMediaNumberField_2() {

		mediaNumberField_2 = new JTextField();
		mediaNumberField_2.setSize(24, 20);
		mediaNumberField_2.setLocation(160, 340);

		mediaNumberField_2.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {

				if (!mediaNumberField_2.getText().equals("")) {

					int dvdState = getDvdState(artworkNumberField_2.getText(), mediaNumberField_2.getText());

					if (dvdState == 0) {
						// 貸出可能な場合

						// 料金と貸出期限の設定
						rentalChargeField_2.setText(getRentalCharge(artworkNumberField_2.getText()));
						termDayField_2.setText(getTermDay(artworkNumberField_2.getText()));

						// 作品番号欄３の有効化
						artworkNumberField_3.setFocusable(true);
						artworkNumberField_3.setEditable(true);

						// 合計金額の算出
						setTotal();

					} else {
						// 貸出不可能な場合エラーメッセージを表示する

						if (dvdState == -1) {
							// DVDが登録されていない場合

							messageLabel.setText(
									"作品番号 " + artworkNumberField_2.getText() + " の "
											+ "枝番（媒体番号） " + mediaNumberField_2.getText() + " が"
											+ "見つかりません。");

						} else if (dvdState == 2) {
							// DVDが貸出停止の場合

							messageLabel.setText(
									"作品番号 " + artworkNumberField_2.getText() + " の "
											+ "枝番（媒体番号） " + mediaNumberField_2.getText() + " は"
											+ "貸出を行っておりません。");

						} else if (dvdState == 1) {
							// DVDが貸出中の場合

							messageLabel.setText(
									"作品番号 " + artworkNumberField_2.getText() + " の "
											+ "枝番（媒体番号） " + mediaNumberField_2.getText() + " は"
											+ "現在貸出中です。");

						}

						// 料金と貸出期限を初期化
						termDayField_2.setText("");
						rentalChargeField_2.setText("");

						// 作品番号欄３の無効化
						artworkNumberField_3.setFocusable(false);
						artworkNumberField_3.setEditable(false);
					}
				}
			}
		});

		return mediaNumberField_2;
	}

	/**
	 * ハイフンラベル２
	 *
	 * @return ハイフンラベル２
	 */
	private JLabel createHyphenLabel_2() {

		hyphenLabel_2 = new JLabel("－");
		hyphenLabel_2.setSize(14, 23);
		hyphenLabel_2.setLocation(145, 340);
		hyphenLabel_2.setBackground(new Color(205, 155, 255));

		return hyphenLabel_2;
	}

	/**
	 * 作品名欄２の生成
	 *
	 * @return 作品名欄２
	 */
	private JTextField createArtworkNameField_2() {

		artworkNameField_2 = new JTextField();
		artworkNameField_2.setSize(175, 20);
		artworkNameField_2.setLocation(187, 340);

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
		termDayField_2.setLocation(370, 340);

		return termDayField_2;
	}

	/**
	 * 料金欄２の生成
	 *
	 * @return 料金欄２
	 */
	private JTextField createChargeField_2() {

		rentalChargeField_2 = new JTextField();
		rentalChargeField_2.setSize(50, 20);
		rentalChargeField_2.setLocation(470, 340);

		return rentalChargeField_2;
	}

	//------------
	// 貸出３
	//------------
	/**
	 * 貸出番号欄３の生成
	 *
	 * @return 貸出番号欄３
	 */
	private JTextField createRentalNumberField_3() {

		rentalNumberField_3 = new JTextField();
		rentalNumberField_3.setSize(65, 20);
		rentalNumberField_3.setLocation(12, 375);

		return rentalNumberField_3;
	}

	/**
	 * 作品番号欄３の生成
	 *
	 * @return 作品番号欄３
	 */
	private JTextField createArtworkNumberField_3() {

		artworkNumberField_3 = new JTextField();
		artworkNumberField_3.setSize(55, 20);
		artworkNumberField_3.setLocation(89, 375);

		artworkNumberField_3.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (rentalNumberField_3.getText().equals("")) {
					rentalNumberField_3.setText(getNewRentalNumber());
				}
			}

			@Override
			public void focusLost(FocusEvent e) {

				// 作品名を取得
				String artworkName = getArtworkName(artworkNumberField_3.getText());

				// 作品があれば枝番（媒体番号）を入力可能にする
				if (artworkName != null) {

					artworkNameField_3.setText(artworkName);
					mediaNumberField_3.setEditable(true);
					mediaNumberField_3.setFocusable(true);

				} else {
					// 作品がない場合エラーメッセージを表示する

					messageLabel.setText("作品番号 " + artworkNumberField_2.getText() + " が見つかりません。");

					artworkNameField_3.setText("");
					mediaNumberField_3.setText("");
					termDayField_3.setText("");
					rentalChargeField_3.setText("");
					mediaNumberField_3.setFocusable(false);
					mediaNumberField_3.setEditable(false);
				}
			}
		});

		return artworkNumberField_3;
	}

	/**
	 * ハイフンラベル３の生成
	 *
	 * @return ハイフンラベル３
	 */
	private JLabel createHyphenLabel_3() {

		hyphenLabel_3 = new JLabel("－");
		hyphenLabel_3.setSize(14, 23);
		hyphenLabel_3.setLocation(145, 375);
		hyphenLabel_3.setBackground(new Color(205, 155, 255));

		return hyphenLabel_3;
	}

	/**
	 * 枝番（媒体番号）欄３の生成
	 *
	 * @return 枝番（媒体番号）欄３
	 */
	private JTextField createMediaNumberField_3() {

		mediaNumberField_3 = new JTextField();
		mediaNumberField_3.setSize(24, 20);
		mediaNumberField_3.setLocation(160, 375);

		mediaNumberField_3.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {

				if (!mediaNumberField_3.getText().equals("")) {

					int dvdState = getDvdState(artworkNumberField_3.getText(), mediaNumberField_3.getText());

					if (dvdState == 0) {
						// 貸出可能な場合

						// 料金と貸出期限の設定
						rentalChargeField_3.setText(getRentalCharge(artworkNumberField_3.getText()));
						termDayField_3.setText(getTermDay(artworkNumberField_3.getText()));

						// 合計金額の算出
						setTotal();

					} else {
						// 貸出不可能な場合エラーメッセージを表示する

						if (dvdState == -1) {
							// DVDが登録されていない場合

							messageLabel.setText(
									"作品番号 " + artworkNumberField_3.getText() + " の "
											+ "枝番（媒体番号） " + mediaNumberField_3.getText() + " が"
											+ "見つかりません。");

						} else if (dvdState == 2) {
							// DVDが貸出停止の場合

							messageLabel.setText(
									"作品番号 " + artworkNumberField_3.getText() + " の "
											+ "枝番（媒体番号） " + mediaNumberField_3.getText() + " は"
											+ "貸出を行っておりません。");

						} else if (dvdState == 1) {
							// DVDが貸出中の場合

							messageLabel.setText(
									"作品番号 " + artworkNumberField_3.getText() + " の "
											+ "枝番（媒体番号） " + mediaNumberField_3.getText() + " は"
											+ "現在貸出中です。");

						}

						// 料金と貸出期限を初期化
						termDayField_3.setText("");
						rentalChargeField_3.setText("");
					}
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
		artworkNameField_3.setLocation(187, 375);

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
		termDayField_3.setLocation(370, 375);

		return termDayField_3;
	}

	/**
	 * 料金欄３の生成
	 *
	 * @return 料金欄３
	 */
	private JTextField createChargeField_3() {

		rentalChargeField_3 = new JTextField();
		rentalChargeField_3.setSize(50, 20);
		rentalChargeField_3.setLocation(470, 375);

		return rentalChargeField_3;
	}

	/**
	 * 合計金額ラベルの生成
	 *
	 * @return 合計金額ラベル
	 */
	private JLabel createTotalLabel() {

		totalLabel = new JLabel("合計金額");
		totalLabel.setBounds(335, 415, 87, 23);
		totalLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		totalLabel.setBackground(new Color(205, 155, 255));

		return totalLabel;
	}

	/**
	 * 合計欄の生成
	 *
	 * @return 合計欄
	 */
	private JTextField createTotalField() {

		totalField = new JTextField();
		totalField.setSize(90, 30);
		totalField.setLocation(430, 411);

		return totalField;
	}

	/**
	 * 実行ボタンの生成
	 *
	 * @return 実行ボタン
	 */
	private JButton createExecButton() {

		executeButton = new JButton("実行");
		executeButton.setSize(70, 30);
		executeButton.setLocation(370, 500);
		executeButton.setFont(new Font("Dialog", Font.PLAIN, 18));
		executeButton.setBackground(Color.orange);

		executeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeRental();
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
		backButton.setLocation(450, 500);
		backButton.setFont(new Font("Dialog", Font.PLAIN, 18));

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// ウィンドウを閉じる
				RentalView.this.dispose();
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
		messageLabel.setBounds(18, 529, 450, 28);
		messageLabel.setText("");

		return messageLabel;
	}

	//------------------------------------------------------------------------
	// 内部メソッド
	//------------------------------------------------------------------------

	/**
	 * 新規貸出番号取得メソッド
	 *
	 * データベースから現在の貸出番号の最大値を取得し、
	 * インスタンス変数newRentalNumberに設定する。
	 * 2回目からのアクセスはnewRentalNumberをインクリメントして、
	 * 貸出番号の文字列にして返す。
	 *
	 * @return 新規貸出番号
	 */
	private String getNewRentalNumber() {

		// 現在の貸出番号が0(最初のアクセス)
		if (newRentalNumber == 0) {

			String currentNumber = null; // 現在の最大貸出番号

			// 現在の貸出番号の最大を照会
			String sql = "SELECT rental_num FROM RENTAL_TABLE ORDER BY rental_num DESC";

			try (Connection connection = DriverManager.getConnection(url, userName, password);
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery(sql);) {

				if (resultSet.next()) {
					currentNumber = resultSet.getString("rental_num");
				}

				// 貸出が登録されていない場合は 0、
				// 登録されている場合は 最大値+1
				if (currentNumber == null) {
					newRentalNumber = 1;
				} else {
					newRentalNumber = Integer.parseInt(currentNumber) + 1;
				}

			} catch (SQLException exception) {

				exception.printStackTrace();

				return null;
			}

		} else {

			// +1して新規貸出番号にする
			newRentalNumber++;
		}
		String newNumber = zeroPad(newRentalNumber, 6);

		return newNumber;
	}

	/**
	 * 数値を受け取り、希望の長さまで先頭を0で埋めた
	 * 文字列として返す内部メソッド
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
	 * 返却期限の取得
	 *
	 * 作品番号を基に返却期限を取得する
	 *
	 * @return 返却期限
	 */
	private String getTermDay(String artworkNumber) {

		// 作品番号を基に新旧区分を取得
		String recentDiv = getRecentDiv(artworkNumber);

		// 料金テーブルから貸出期間を照会する
		String sql = "SELECT rental_period FROM PRICE_TABLE WHERE recent_div = ?";

		String rentalPeriod;

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, recentDiv);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (!resultSet.next()) {
					return null;
				}

				rentalPeriod = resultSet.getString("rental_period");
			}

		} catch (SQLException exception) {

			exception.printStackTrace();

			return null;
		}

		int period = Integer.parseInt(rentalPeriod); // int型に変換

		// 本日日付に貸出期間を加えて返却期限にする
		Calendar termDayCalendar = Calendar.getInstance();
		termDayCalendar.add(Calendar.DATE, period);

		// yyyy/MM/ddの形式に変換
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date termDay = termDayCalendar.getTime();

		return sdf.format(termDay);
	}

	/**
	 * DVDの状態を取得する
	 *
	 * @param artworkNumber 作品番号
	 * @param mediaNumber 枝番（媒体番号）
	 *
	 * @return -1:DVDが登録されていない、0:DVDが貸出可能、1:DVDが貸出中、2:DVDが貸出終了
	 */
	private int getDvdState(String artworkNumber, String mediaNumber) {

		// 枝番（媒体番号）を2桁になるように処理
		String w_mediaNumber = zeroPad(Integer.parseInt(mediaNumber), 2);

		try (Connection connection = DriverManager.getConnection(url, userName, password);) {

			// DVDの存在を確認
			String sql1 = "SELECT * FROM DVD_TABLE WHERE artwork_num = ? AND media_num = ?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql1);) {

				preparedStatement.setString(1, artworkNumber);
				preparedStatement.setString(2, w_mediaNumber);

				try (ResultSet resultSet = preparedStatement.executeQuery();) {

					if (!resultSet.next()) {

						// DVDが登録されていない
						return -1;
					}

					Date disuseDay = resultSet.getDate("disuse_day");

					if (disuseDay != null) {

						// DVDが貸出終了
						return 2;
					}
				}
			}

			// DVDが貸出中の確認
			String sql2 = "SELECT * FROM RENTAL_TABLE WHERE artwork_num = ? AND media_num = ? AND check_in_day IS NULL";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql2);) {

				preparedStatement.setString(1, artworkNumber);
				preparedStatement.setString(2, w_mediaNumber);

				try (ResultSet resultSet = preparedStatement.executeQuery();) {

					if (resultSet.next()) {

						// DVDが貸出中
						return 1;

					} else {

						// DVDが貸出可能
						return 0;
					}
				}
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return -1;
	}

	/**
	 * 料金取得メソッド
	 *
	 * 作品番号を基に料金を取得するメソッド
	 *
	 * @param artworkNumber 作品番号
	 * @return 料金
	 */
	private String getRentalCharge(String artworkNumber) {

		// 作品番号を基に新旧区分を取得
		String saPridiv = getRecentDiv(artworkNumber);

		// 取得した新旧区分を基に料金を照会
		String sql = "SELECT rental_charge FROM PRICE_TABLE WHERE recent_div = ?";

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

			preparedStatement.setString(1, saPridiv);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (resultSet.next()) {
					return resultSet.getString("rental_charge");
				}
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return null;
	}

	/**
	 * 作品番号を基に当該作品の新旧区分を取得する
	 *
	 * @param artworkNumber 作品番号
	 *
	 * @return 新旧区分
	 */
	private String getRecentDiv(String artworkNumber) {

		// 作品テーブルから新旧区分を照会
		String sql = "SELECT recent_div FROM ARTWORK_TABLE WHERE artwork_num = ?";

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

			preparedStatement.setString(1, artworkNumber);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (resultSet.next()) {
					return resultSet.getString("recent_div");
				}
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return null;
	}

	/**
	 * 作品番号をもとに作品名を取得する
	 *
	 * @param artworkNumber 作品番号
	 *
	 * @return 作品名
	 */
	private String getArtworkName(String artworkNumber) {

		// 作品名の照会
		StringBuffer sql = new StringBuffer(0);
		sql.append("SELECT artwork_name, recent_div ");
		sql.append("FROM ARTWORK_TABLE ");
		sql.append("WHERE ");
		sql.append("artwork_num = ?");

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());) {

			preparedStatement.setString(1, artworkNumber);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (resultSet.next()) {
					return resultSet.getString("artwork_name");
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return null;
	}

	/**
	 * 合計金額の表示メソッド
	 * 合計金額を表示し、結果として
	 * 0でなければ、「実行」ボタンの押下を可能にする
	 */
	private void setTotal() {

		String rentalCharge1 = rentalChargeField_1.getText();
		String rentalCharge2 = rentalChargeField_2.getText();
		String rentalCharge3 = rentalChargeField_3.getText();

		int total = 0;

		if (!rentalCharge1.equals("")) {
			total += Integer.parseInt(rentalCharge1);
		}

		if (!rentalCharge2.equals("")) {
			total += Integer.parseInt(rentalCharge2);
		}

		if (!rentalCharge3.equals("")) {
			total += Integer.parseInt(rentalCharge3);
		}

		// 合計金額の設定
		totalField.setText(String.valueOf(total));

		// 合計金額が0でなければ「実行」ボタンの使用を可にする
		if (total != 0) {
			executeButton.setEnabled(true);
		} else {
			executeButton.setEnabled(false);
		}
	}

	/**
	 * 貸出処理
	 *
	 */
	private void executeRental() {

		int result = 0;

		// 貸出詳細１
		if ((!rentalChargeField_1.getText().equals("")) && rentalChargeField_1.getText() != null) {

			result = insert(rentalNumberField_1.getText(), artworkNumberField_1.getText(),
					mediaNumberField_1.getText(),
					termDayField_1.getText());
		}

		// 貸出詳細２
		if ((!rentalChargeField_2.getText().equals("")) && rentalChargeField_2.getText() != null) {

			result = insert(rentalNumberField_2.getText(), artworkNumberField_2.getText(),
					mediaNumberField_2.getText(),
					termDayField_2.getText());
		}

		// 貸出詳細３
		if ((!rentalChargeField_3.getText().equals("")) && rentalChargeField_3.getText() != null) {

			result = insert(rentalNumberField_3.getText(), artworkNumberField_3.getText(),
					mediaNumberField_3.getText(),
					termDayField_3.getText());
		}

		// 挿入結果
		if (result == 1) {

			messageLabel.setText("貸出処理が完了しました。");
			clear();

		} else {

			messageLabel.setText("貸出処理が異常終了しました。");
		}
	}

	/**
	 * データベース挿入メソッド
	 *
	 * パラメータを基に貸出情報をデータベースに登録する
	 *
	 * @param rentalNumber 貸出番号
	 * @param artworkNumber 作品番号
	 * @param mediaNumber 作品枝番（媒体番号）
	 * @param termDay 返却期限
	 *
	 * @return 0:失敗(0行)
	 *          1:成功(1行)
	 */
	private int insert(String rentalNumber, String artworkNumber, String mediaNumber, String termDay) {

		// 会員番号の取得
		String memberNumber = memberNumberField.getText();

		// 貸出日の取得
		// yyyyMMddの形式に変換
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String checkOutDay = sdf.format(new Date());

		// 枝番（媒体番号）は2桁形式に
		String w_mediaNumber = zeroPad(Integer.parseInt(mediaNumber), 2);

		String sql = "INSERT INTO RENTAL_TABLE "
				+ "(rental_num, check_out_day, member_num, artwork_num, media_num, term_day) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(url, userName, password);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

			preparedStatement.setString(1, rentalNumber);
			preparedStatement.setString(2, checkOutDay);
			preparedStatement.setString(3, memberNumber);
			preparedStatement.setString(4, artworkNumber);
			preparedStatement.setString(5, w_mediaNumber);
			preparedStatement.setString(6, termDay);

			return preparedStatement.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return 0;

	}

	/**
	 * クリアメソッド
	 * 部品をクリアし、フォーカスを初期状態にする
	 * メソッド。起動時と貸出完了時に呼び出される
	 */
	private void clear() {

		// 会員情報
		memberNumberField.setText("");

		memberNameField.setText("");
		memberNameField.setEditable(false);
		memberNameField.setFocusable(false);

		rankDivField.setText("");
		rankDivField.setEditable(false);
		rankDivField.setFocusable(false);

		// 貸出日
		checkOutDayField.setText("");
		checkOutDayField.setEditable(false);
		checkOutDayField.setFocusable(false);

		// 貸出1行目
		rentalNumberField_1.setText("");
		rentalNumberField_1.setEditable(false);
		rentalNumberField_1.setFocusable(false);

		artworkNumberField_1.setText("");
		artworkNumberField_1.setEditable(false);
		artworkNumberField_1.setFocusable(false);

		artworkNameField_1.setText("");
		artworkNameField_1.setEditable(false);
		artworkNameField_1.setFocusable(false);

		mediaNumberField_1.setText("");
		mediaNumberField_1.setEditable(false);
		mediaNumberField_1.setFocusable(false);

		rentalChargeField_1.setText("");
		rentalChargeField_1.setEditable(false);
		rentalChargeField_1.setFocusable(false);

		termDayField_1.setText("");
		termDayField_1.setEditable(false);
		termDayField_1.setFocusable(false);

		// 貸出2行目
		rentalNumberField_2.setText("");
		rentalNumberField_2.setEditable(false);
		rentalNumberField_2.setFocusable(false);

		artworkNumberField_2.setText("");
		artworkNumberField_2.setEditable(false);
		artworkNumberField_2.setFocusable(false);

		artworkNameField_2.setText("");
		artworkNameField_2.setEditable(false);
		artworkNameField_2.setFocusable(false);

		mediaNumberField_2.setText("");
		mediaNumberField_2.setEditable(false);
		mediaNumberField_2.setFocusable(false);

		rentalChargeField_2.setText("");
		rentalChargeField_2.setEditable(false);
		rentalChargeField_2.setFocusable(false);

		termDayField_2.setText("");
		termDayField_2.setEditable(false);
		termDayField_2.setFocusable(false);

		// 貸出3行目
		rentalNumberField_3.setText("");
		rentalNumberField_3.setEditable(false);
		rentalNumberField_3.setFocusable(false);

		artworkNumberField_3.setText("");
		artworkNumberField_3.setEditable(false);
		artworkNumberField_3.setFocusable(false);

		artworkNameField_3.setText("");
		artworkNameField_3.setEditable(false);
		artworkNameField_3.setFocusable(false);

		mediaNumberField_3.setText("");
		mediaNumberField_3.setEditable(false);
		mediaNumberField_3.setFocusable(false);

		rentalChargeField_3.setText("");
		rentalChargeField_3.setEditable(false);
		rentalChargeField_3.setFocusable(false);

		termDayField_3.setText("");
		termDayField_3.setEditable(false);
		termDayField_3.setFocusable(false);

		// 合計金額
		totalField.setText("");
		totalField.setEditable(false);
		totalField.setFocusable(false);

		// 実行ボタン
		executeButton.setEnabled(false);

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
