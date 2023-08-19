package rentalSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 会員管理画面
 *
 */
public class MemberView extends JFrame {

	/** 選択中のモード　1:登録　2:更新　3:削除*/
	private int mode = 0;

	private JCheckBox registerCheckBox;
	private JCheckBox updateCheckBox;
	private JCheckBox deleteCheckBox;
	private ButtonGroup modeSelectGroup;

	// 会員番号欄
	private JLabel memberNumberLabel;
	private JTextField memberNumberField;

	// 検索ボタン
	private JButton searchButton;

	// 氏名欄
	private JLabel memberNameLabel;
	private JTextField memberNameField;

	// 年齢欄
	private JLabel memberAgeLabel;
	private JTextField memberAgeField;

	// 入会日欄
	private JLabel enterDayLabel;
	private JTextField enterDayField;

	// 退会日欄
	private JLabel leaveDayLabel;
	private JTextField leaveDayField;

	// 住所欄
	private JLabel memberAddressLabel;
	private JTextField memberAddressField;

	// 電話番号欄
	private JLabel memberTelLabel;
	private JTextField memberTelField;

	// 会員優良区分欄
	private JLabel rankDivLabel;
	private JComboBox<String> rankDivComboBox;

	// メールアドレス欄
	private JLabel memberMailLabel;
	private JTextField memberMailField;

	// 実行、戻るボタン
	private JButton executeButton;
	private JButton backButton;

	// メッセージ欄
	private JLabel messageLabel;

	private MemberDBAccess memberDBAccess;

	/**
	 * コンストラクタ
	 *
	 * @param connection DB接続済みコネクション
	 */
	public MemberView(String url, String userName, String password) {

		super();

		memberDBAccess = new MemberDBAccess(url, userName, password);

		initialize();
	}

	/**
	 * GUI部品群の初期化
	 */
	private void initialize() {

		Container container = this.getContentPane();

		this.setTitle("会員管理");
		this.setSize(560, 500);
//		container.setBackground(new Color(155, 155, 255));

		this.setLayout(null);

		container.add(createPanel());
		container.add(createMemberNumberlabel());
		container.add(createMemberNumberField());
		container.add(createSearchButton());

		container.add(createMemberNameLabel());
		container.add(createMemberNameField());
		container.add(createMemberAgeLabel());
		container.add(createMemberAgeField());
		container.add(createEnterDayLabel());
		container.add(createEnterDayField());
		container.add(createLeaveDayLabel());
		container.add(createLeaveDayField());

		container.add(createMemberAddressLabel());
		container.add(createMemberAddressField());

		container.add(createMemberTelLabel());
		container.add(createMemberTelField());
		container.add(createRankDivLabel());
		container.add(createRankDivComboBox());

		container.add(createMemberMailLabel());
		container.add(createMemberMailField());

		container.add(createExecuteButton());
		container.add(createBackButton());

		container.add(createMessageLabel());

		clear();
	}

	/**
	 * 「登録」「更新」「削除」のチェックボックスのパネルを生成
	 *
	 * @return JPanel チェックボックスのパネル
	 */
	private JPanel createPanel() {

		JPanel panel = new JPanel();
		panel.setBounds(13, 44, 138, 75);
//		panel.setBackground(new Color(153, 255, 204));

		panel.setLayout(new BorderLayout());
		panel.add(createRegisterCheckBox(), BorderLayout.NORTH);
		panel.add(createUpdateCheckBox(), BorderLayout.WEST);
		panel.add(createDeleteCheckBox(), BorderLayout.SOUTH);

		modeSelectGroup = new ButtonGroup();
		modeSelectGroup.add(registerCheckBox);
		modeSelectGroup.add(updateCheckBox);
		modeSelectGroup.add(deleteCheckBox);

		return panel;
	}

	/**
	 * 登録チェックボックスの生成
	 *
	 * @return 登録チェックボックス
	 */
	private JCheckBox createRegisterCheckBox() {

		registerCheckBox = new JCheckBox("登録", false);
		registerCheckBox.setOpaque(false);

		registerCheckBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent event) {

				if (event.getStateChange() == ItemEvent.DESELECTED) {

					// 選択が解除された場合は画面を初期化する
					clear();

					return;
				}

				// 選択状態フラグを新規登録へ
				mode = 1;

				// ラジオボタンを使用不可にする
				registerCheckBox.setEnabled(false);
				updateCheckBox.setEnabled(false);
				deleteCheckBox.setEnabled(false);

				// 会員情報を入力可能にする
				memberNameField.setEditable(true);
				memberAgeField.setEditable(true);
				memberAddressField.setEditable(true);
				memberTelField.setEditable(true);
				memberMailField.setEditable(true);
				rankDivComboBox.setEnabled(true);

				// 実行ボタンを使用可能に
				executeButton.setEnabled(true);

				// 新規会員番号と今日の日付のセット
				memberNumberField.setText(getNewMemberNumber());
				enterDayField.setText(getToday());

				// メッセージラベルのクリア
				messageLabel.setText("");
			}
		});

		return registerCheckBox;
	}

	/**
	 * 更新チェックボックスの生成
	 *
	 * @return 更新チェックボックス
	 */
	private JCheckBox createUpdateCheckBox() {

		updateCheckBox = new JCheckBox("更新", false);
		updateCheckBox.setOpaque(false);

		updateCheckBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == ItemEvent.DESELECTED) {

					// 選択が解除された場合は画面を初期化する
					clear();

					return;
				}

				// 選択状態フラグを更新へ
				mode = 2;

				// チェックボックスを使用不可へ
				registerCheckBox.setEnabled(false);
				updateCheckBox.setEnabled(false);
				deleteCheckBox.setEnabled(false);

				// 会員番号を入力可能へ
				memberNumberField.setEditable(true);

				// 検索ボタンを使用可能へ
				searchButton.setEnabled(true);

				// メッセージラベルのクリア
				messageLabel.setText("");
			}
		});

		return updateCheckBox;
	}

	/**
	 * 削除チェックボックスの生成
	 *
	 * @return 削除チェックボックス
	 */
	private JCheckBox createDeleteCheckBox() {

		deleteCheckBox = new JCheckBox("削除", false);
		deleteCheckBox.setOpaque(false);

		deleteCheckBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == ItemEvent.DESELECTED) {

					// 選択が解除された場合は画面を初期化する
					clear();

					return;
				}

				// 選択状態フラグを削除へ
				mode = 3;

				// チェックボックスを使用不可へ
				registerCheckBox.setEnabled(false);
				updateCheckBox.setEnabled(false);
				deleteCheckBox.setEnabled(false);

				// 会員番号を入力可能へ
				memberNumberField.setEditable(true);

				// 検索ボタンを使用可能へ
				searchButton.setEnabled(true);

				// メッセージラベルのクリア
				messageLabel.setText("");
			}
		});

		return deleteCheckBox;
	}

	/**
	 * 会員番号ラベルの生成
	 *
	 * @return 会員番号ラベル
	 */
	private JLabel createMemberNumberlabel() {

		memberNumberLabel = new JLabel("会員番号");
		memberNumberLabel.setBounds(174, 53, 126, 21);
		memberNumberLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		memberNumberLabel.setForeground(new Color(0, 6, 0));

		return memberNumberLabel;
	}

	/**
	 * 会員番号欄の生成
	 *
	 * @return 会員番号欄
	 */
	private JTextField createMemberNumberField() {

		memberNumberField = new JTextField();
		memberNumberField.setBounds(173, 79, 188, 39);
		memberNumberField.setFont(new Font("Dialog", Font.BOLD, 20));

		return memberNumberField;
	}

	/**
	 * 検索ボタンの生成
	 *
	 * @return 検索ボタン
	 */
	private JButton createSearchButton() {

		searchButton = new JButton("検索");
		searchButton.setBounds(380, 91, 70, 30);
		searchButton.setFont(new Font("Dialog", Font.PLAIN, 18));

		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (memberNumberField.getText() != null && !memberNumberField.getText().equals("")) {

					messageLabel.setText("");

					boolean result = memberDBAccess.setMember(memberNumberField.getText());

					if (result) {

						// 会員情報を設定する
						memberNameField.setText(memberDBAccess.getMemberName());
						memberAgeField.setText(memberDBAccess.getMemberAge());
						enterDayField.setText(memberDBAccess.getMemberInsert());
						leaveDayField.setText(memberDBAccess.getMemberDelete());
						memberAddressField.setText(memberDBAccess.getMemberAddress());
						memberTelField.setText(memberDBAccess.getMemberTel());
						rankDivComboBox.setSelectedItem(memberDBAccess.getMemberRank());
						memberMailField.setText(memberDBAccess.getMemberMail());

						if (mode == 2) {

							// 更新時は欄群をエディット可能にする
							memberNameField.setEditable(true);
							memberAgeField.setEditable(true);
							enterDayField.setEditable(true);
							leaveDayField.setEditable(true);
							memberAddressField.setEditable(true);
							memberTelField.setEditable(true);
							memberMailField.setEditable(true);

							rankDivComboBox.setEnabled(true);
						}

						// 実行ボタンを使用可能にする
						executeButton.setEnabled(true);

					} else {

						// 会員情報が見つからない場合はエラーメッセージを表示する
						messageLabel.setText("会員番号 " + memberNumberField.getText() + " の会員が見つかりませんでした");
					}
				}
			}
		});

		return searchButton;
	}

	/**
	 * 氏名ラベルの生成
	 *
	 * @return 氏名ラベル
	 */
	private JLabel createMemberNameLabel() {

		memberNameLabel = new JLabel("氏名");
		memberNameLabel.setBounds(14, 145, 69, 24);
		memberNameLabel.setFont(new Font("Dialog", Font.PLAIN, 14));

		return memberNameLabel;
	}

	/**
	 * 氏名欄の生成
	 *
	 * @return 氏名欄
	 */
	private JTextField createMemberNameField() {

		memberNameField = new JTextField();
		memberNameField.setBounds(13, 175, 153, 30);
		memberNameField.setFont(new Font("Dialog", Font.PLAIN, 18));

		return memberNameField;
	}

	/**
	 * 年齢ラベル
	 *
	 * @return 年齢ラベル
	 */
	private JLabel createMemberAgeLabel() {

		memberAgeLabel = new JLabel("年齢");
		memberAgeLabel.setBounds(177, 147, 42, 24);
		memberAgeLabel.setFont(new Font("Dialog", Font.PLAIN, 14));

		return memberAgeLabel;
	}

	/**
	 * 年齢欄の取得
	 *
	 * @return 年齢欄
	 */
	private JTextField createMemberAgeField() {

		memberAgeField = new JTextField();
		memberAgeField.setBounds(176, 175, 100, 30);
		memberAgeField.setFont(new Font("Dialog", Font.PLAIN, 18));

		return memberAgeField;
	}

	/**
	 * 入会日ラベルの生成
	 *
	 * @return JLabel
	 */
	private JLabel createEnterDayLabel() {

		enterDayLabel = new JLabel("入会日");
		enterDayLabel.setBounds(288, 146, 73, 25);
		enterDayLabel.setFont(new Font("Dialog", Font.PLAIN, 14));

		return enterDayLabel;
	}

	/**
	 * 入会日欄の生成
	 *
	 * @return 入会日欄
	 */
	private JTextField createEnterDayField() {

		enterDayField = new JTextField();
		enterDayField.setBounds(288, 175, 100, 30);
		enterDayField.setFont(new Font("Dialog", Font.PLAIN, 18));

		return enterDayField;
	}

	/**
	 * 退会日ラベルの生成
	 *
	 * @return 退会日ラベル
	 */
	private JLabel createLeaveDayLabel() {

		leaveDayLabel = new JLabel("退会日");
		leaveDayLabel.setBounds(406, 146, 78, 25);
		leaveDayLabel.setFont(new Font("Dialog", Font.PLAIN, 14));

		return leaveDayLabel;
	}

	/**
	 * 退会日欄の生成
	 *
	 * @return JTextField
	 */
	private JTextField createLeaveDayField() {

		leaveDayField = new JTextField();
		leaveDayField.setBounds(405, 175, 100, 30);
		leaveDayField.setFont(new Font("Dialog", Font.PLAIN, 18));

		return leaveDayField;
	}

	/**
	 * 住所ラベルの生成
	 *
	 * @return JLabel
	 */
	private JLabel createMemberAddressLabel() {

		memberAddressLabel = new JLabel("住所");
		memberAddressLabel.setBounds(14, 218, 70, 27);
		memberAddressLabel.setFont(new Font("Dialog", Font.PLAIN, 14));

		return memberAddressLabel;
	}

	/**
	 * 住所欄の生成
	 *
	 * @return 住所欄
	 */
	private JTextField createMemberAddressField() {

		memberAddressField = new JTextField();
		memberAddressField.setBounds(13, 250, 495, 30);
		memberAddressField.setFont(new Font("Dialog", Font.PLAIN, 18));

		return memberAddressField;
	}

	/**
	 * TELラベルの生成
	 *
	 * @return TELラベル
	 */
	private JLabel createMemberTelLabel() {

		memberTelLabel = new JLabel("電話番号");
		memberTelLabel.setBounds(14, 287, 71, 27);
		memberTelLabel.setFont(new Font("Dialog", Font.PLAIN, 14));

		return memberTelLabel;
	}

	/**
	 * 電話番号欄の生成
	 *
	 * @return 電話番号欄
	 */
	private JTextField createMemberTelField() {

		memberTelField = new JTextField();
		memberTelField.setBounds(13, 320, 245, 30);
		memberTelField.setFont(new Font("Dialog", Font.PLAIN, 18));

		return memberTelField;
	}

	/**
	 * 会員種別ラベルの生成
	 *
	 * @return 会員種別ラベル
	 */
	private JLabel createRankDivLabel() {

		rankDivLabel = new JLabel("会員種別");
		rankDivLabel.setBounds(285, 288, 71, 24);
		rankDivLabel.setFont(new Font("Dialog", Font.PLAIN, 14));

		return rankDivLabel;
	}

	/**
	 * 会員優良区分選択ボックスの生成
	 *
	 * @return 会員優良区分選択ボックス
	 */
	private JComboBox<String> createRankDivComboBox() {

		rankDivComboBox = new JComboBox<String>();
		rankDivComboBox.setBounds(285, 320, 60, 30);
		rankDivComboBox.addItem("優");
		rankDivComboBox.addItem("良");
		rankDivComboBox.addItem("可");
		rankDivComboBox.addItem("不");

		return rankDivComboBox;
	}

	/**
	 * メールアドレスラベルの生成
	 *
	 * @return メールアドレスラベル
	 */
	private JLabel createMemberMailLabel() {

		memberMailLabel = new JLabel("メールアドレス");
		memberMailLabel.setBounds(15, 358, 100, 22);
		memberMailLabel.setFont(new Font("Dialog", Font.PLAIN, 14));

		return memberMailLabel;
	}

	/**
	 * メールアドレス欄の生成
	 *
	 * @return メールアドレス欄
	 */
	private JTextField createMemberMailField() {

		memberMailField = new JTextField();
		memberMailField.setBounds(13, 385, 350, 30);
		memberMailField.setFont(new Font("Dialog", Font.PLAIN, 18));

		return memberMailField;
	}

	/**
	 * 実行ボタンの生成
	 *
	 * @return 実行ボタン
	 */
	private JButton createExecuteButton() {

		executeButton = new JButton("実行");
		executeButton.setBounds(380, 385, 70, 30);
		executeButton.setFont(new Font("Dialog", Font.PLAIN, 18));
		executeButton.setBackground(Color.orange);

		executeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 実行ボタン押下

				// 新規登録
				if (mode == 1) {
					entryMember();
				}

				// 更新
				if (mode == 2) {
					updateMember();
				}

				// 削除
				if (mode == 3) {
					leaveMember();
				}
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
		backButton.setBounds(460, 385, 70, 30);
		backButton.setFont(new Font("Dialog", Font.PLAIN, 18));

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				MemberView.this.dispose();
			}
		});

		return backButton;
	}

	private JLabel createMessageLabel() {

		messageLabel = new JLabel();
		messageLabel.setBounds(13, 427, 348, 26);
		messageLabel.setText("");
		return messageLabel;
	}

	/**
	 * 会員の登録処理
	 */
	private void entryMember() {

		int result = memberDBAccess.addMember(
				memberNumberField.getText(),
				memberNameField.getText(),
				memberAddressField.getText(),
				memberAgeField.getText(),
				memberTelField.getText(),
				memberMailField.getText(),
				enterDayField.getText(),
				rankDivComboBox.getItemAt(rankDivComboBox.getSelectedIndex()));

		if (result == 1) {

			clear();
			messageLabel.setText("会員情報を登録しました。");

		} else {

			messageLabel.setText("会員登録に失敗しました。");
		}
	}

	/**
	 * 会員の更新処理
	 */
	private void updateMember() {

		int result = memberDBAccess.updateMember(
				memberNumberField.getText(),
				memberNameField.getText(),
				memberAddressField.getText(),
				memberAgeField.getText(),
				memberTelField.getText(),
				memberMailField.getText(),
				enterDayField.getText(),
				leaveDayField.getText(),
				rankDivComboBox.getItemAt(rankDivComboBox.getSelectedIndex()));

		if (result == 1) {

			clear();
			messageLabel.setText("会員情報を更新しました。");

		} else {

			messageLabel.setText("会員更新に失敗しました。");
		}

	}

	/**
	 * 会員の削除処理
	 *
	 * 退会日の入力をもって、退会（削除）とする。
	 */
	private void leaveMember() {

		// 今日の日付の取得
		String leaveDay = getToday();

		int result = memberDBAccess.deleteMember(memberNumberField.getText(), leaveDay);

		if (result == 1) {

			clear();
			messageLabel.setText("会員情報を削除しました。");

		} else {

			messageLabel.setText("会員削除に失敗しました。");
		}
	}

	/**
	 * 今日の日付文字列の取得
	 *
	 * @return 今日の日付文字列（yyyy/MM/dd）
	 */
	private String getToday() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		return sdf.format(new Date());
	}

	/**
	 * 新規会員番号の取得
	 *
	 * @return 新規会員番号
	 */
	private String getNewMemberNumber() {

		return memberDBAccess.getNewMemberNumber();
	}

	/**
	 * 画面内容のクリア
	 *
	 */
	private void clear() {

		mode = 0;

		// フィールドのクリア
		memberNumberField.setText("");
		memberNameField.setText("");
		memberAgeField.setText("");
		enterDayField.setText("");
		leaveDayField.setText("");
		memberAddressField.setText("");
		memberTelField.setText("");
		memberMailField.setText("");

		// チェックボックスを未選択に変更
		modeSelectGroup.clearSelection();

		// チェックボックスを選択可能に変更
		registerCheckBox.setEnabled(true);
		updateCheckBox.setEnabled(true);
		deleteCheckBox.setEnabled(true);

		// テキストボックスの初期化
		memberNumberField.setEditable(false);
		memberNameField.setEditable(false);
		memberAgeField.setEditable(false);
		enterDayField.setEditable(false);
		leaveDayField.setEditable(false);
		memberAddressField.setEditable(false);
		memberTelField.setEditable(false);
		memberMailField.setEditable(false);

		// 会員区分の初期化
		rankDivComboBox.setSelectedIndex(0);
		rankDivComboBox.setEnabled(false);

		enterDayField.setFocusable(false);
		leaveDayField.setFocusable(false);

		searchButton.setEnabled(false);
		executeButton.setEnabled(false);
	}
}