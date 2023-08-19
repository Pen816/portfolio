package view;

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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import dbAccess.MemberDBAccess;

public class MemberView extends JFrame {

	// DBアクセスオブジェクト
	private MemberDBAccess memberDBAccess;

	//	選択中のモード  1:登録  2:更新  3:削除
	private int mode = 0;

	//	モード選択
	//	private ButtonGroup modeSelectGroup;
	private JCheckBox insertCheckBox;
	private JCheckBox updateCheckBox;
	private JCheckBox deleteCheckBox;

	// 会員番号
	private JLabel memberNumLabel;
	private JTextField memberNumField;

	// 検索ボタン
	private JButton searchButton;

	// 氏名
	private JLabel memberNameLabel;
	private JTextField memberNameField;

	// 年齢
	private JLabel memberAgeLabel;
	private JTextField memberAgeField;

	// 入会日
	private JLabel enterDayLabel;
	private JTextField enterDayField;

	// 退会日
	private JLabel leaveDayLabel;
	private JTextField leaveDayField;

	// 住所
	private JLabel memberAddressLabel;
	private JTextField memberAddressField;

	// 電話番号
	private JLabel memberTelLabel;
	private JTextField memberTelField;

	// 会員優良区分
	private JLabel categoryLabel;
	private JComboBox<String> categoryComboBox;

	// メールアドレス
	private JLabel memberMailLabel;
	private JTextField memberMailField;

	// 実行、戻るボタン
	private JButton executeButton;
	private JButton backButton;

	// メッセージ
	private JLabel messageLabel;

	//	private final String name, pass, url;// データベース接続用データ

	public MemberView(String url, String name, String pass) {
		//	public MemberView() {

		//	DB使用準備

		//		this.name = name;
		//		this.pass = pass;
		//		this.url = url;

		//		this.name = "root";
		//		this.pass = "password";
		//		this.url = "jdbc:mysql://localhost/RentalDB?useSSL=false&serverTimezone=Asia/Tokyo";

		memberDBAccess = new MemberDBAccess(url, name, pass);

		// 初期指定
		setTitle("会員管理");
		setSize(560, 550); // ウィンドウサイズ指定
		setLocationRelativeTo(null); // windowを中央表示

		Container cntnr = getContentPane();
		getContentPane().setLayout(null);

		//	「登録」「更新」「削除」のチェックボックス
		cntnr.add(createPanel());

		//	会員番号
		cntnr.add(createMemberNumlabel());
		cntnr.add(createMemberNumField());
		//	検索ボタン
		cntnr.add(createSearchButton());

		//	氏名
		cntnr.add(createMemberNameLabel());
		cntnr.add(createMemberNameField());
		//	年齢
		cntnr.add(createMemberAgeLabel());
		cntnr.add(createMemberAgeField());
		//	入会日
		cntnr.add(createEnterDayLabel());
		cntnr.add(createEnterDayField());
		//	退会日
		cntnr.add(createLeaveDayLabel());
		cntnr.add(createLeaveDayField());
		//	住所
		cntnr.add(createMemberAddressLabel());
		cntnr.add(createMemberAddressField());
		//	電話番号
		cntnr.add(createMemberTelLabel());
		cntnr.add(createMemberTelField());
		//	優良区分
		cntnr.add(createCategoryLabel());
		cntnr.add(createCategoryComboBox());
		//	メールアドレス
		cntnr.add(createMemberMailLabel());
		cntnr.add(createMemberMailField());

		//	実行ボタン
		cntnr.add(createExecuteButton());
		//	戻るボタン
		cntnr.add(createBackButton());
		//	メッセージ
		cntnr.add(createMessageLabel());

		componentReset();

	}

	//	public static void main(String[] args) {
	//		MemberView memberView = new MemberView();
	//		memberView.setVisible(true);//表示メソッド(true:表示、false:非表示)
	//	}

	// GUI生成

	//	「登録」「更新」「削除」のチェックボックスパネル
	private JPanel createPanel() {

		JPanel panel = new JPanel();
		panel.setBounds(46, 43, 100, 75);

		//	チェックボックスを縦に並べて配置
		panel.setLayout(new BorderLayout());
		panel.add(createInsertCheckBox(), BorderLayout.NORTH);
		panel.add(createUpdateCheckBox(), BorderLayout.WEST);
		panel.add(createDeleteCheckBox(), BorderLayout.SOUTH);

		return panel;
	}

	//	登録チェックボックス
	private JCheckBox createInsertCheckBox() {

		insertCheckBox = new JCheckBox("登録", false);

		insertCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) { //	ItemEvent：Itemの選択状態を保持
				//	登録チェックボックスの選択が解除された場合
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					componentReset();
					return;
				}

				// 選択状態フラグを新規登録へ
				mode = 1;

				// ラジオボタンを使用不可に
				insertCheckBox.setEnabled(false);
				updateCheckBox.setEnabled(false);
				deleteCheckBox.setEnabled(false);

				// 会員情報を入力可能に
				memberNameField.setEditable(true);
				memberAgeField.setEditable(true);
				memberAddressField.setEditable(true);
				memberTelField.setEditable(true);
				memberMailField.setEditable(true);
				categoryComboBox.setEnabled(true);

				// 実行ボタンを使用可能に
				executeButton.setEnabled(true);

				// 新規会員番号セット
				memberNumField.setText(memberDBAccess.getNewMemberNum());

				//	今日の日付セット
				enterDayField.setText(getToday());

				// メッセージラベルのクリア
				messageLabel.setText("");
			}
		});
		return insertCheckBox;
	}

	//	更新チェックボックス
	private JCheckBox createUpdateCheckBox() {

		updateCheckBox = new JCheckBox("更新", false);

		updateCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//	登録チェックボックスの選択が解除された場合
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					componentReset();
					return;
				}

				// 選択状態フラグを更新へ
				mode = 2;

				// チェックボックスを使用不可へ
				insertCheckBox.setEnabled(false);
				updateCheckBox.setEnabled(false);
				deleteCheckBox.setEnabled(false);

				// 会員番号を入力可能へ
				memberNumField.setEditable(true);

				// 検索ボタンを使用可能へ
				searchButton.setEnabled(true);

				// メッセージラベルのクリア
				messageLabel.setText("");
			}
		});
		return updateCheckBox;
	}

	//	削除チェックボックス
	private JCheckBox createDeleteCheckBox() {

		deleteCheckBox = new JCheckBox("削除", false);

		deleteCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//	登録チェックボックスの選択が解除された場合
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					componentReset();
					return;
				}

				// 選択状態フラグを削除へ
				mode = 3;

				// チェックボックスを使用不可へ
				insertCheckBox.setEnabled(false);
				updateCheckBox.setEnabled(false);
				deleteCheckBox.setEnabled(false);

				// 会員番号を入力可能へ
				memberNumField.setEditable(true);

				// 検索ボタンを使用可能へ
				searchButton.setEnabled(true);

				// メッセージラベルのクリア
				messageLabel.setText("");
			}
		});
		return deleteCheckBox;
	}

	private JLabel createMemberNumlabel() {
		memberNumLabel = new JLabel("会員番号");
		memberNumLabel.setBounds(174, 53, 126, 21);
		memberNumLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return memberNumLabel;
	}

	private JTextField createMemberNumField() {
		memberNumField = new JTextField();
		memberNumField.setBounds(173, 79, 188, 39);
		memberNumField.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 23));
		return memberNumField;
	}

	//	検索ボタン
	private JButton createSearchButton() {

		searchButton = new JButton("検索");
		searchButton.setBounds(394, 88, 70, 30);
		searchButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 18));

		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//	会員番号フィールドに値がある時
				if (memberNumField.getText() != null && !memberNumField.getText().equals("")) {

					messageLabel.setText("");

					//	会員番号フィールドの値をもとに会員情報設定
					//	true(1)：設定完了 false(0)：設定できない
					boolean result = memberDBAccess.setMember(memberNumField.getText());

					//	会員情報設定が行えていれば
					if (result) {

						// 会員情報を表示設定する
						memberNameField.setText(memberDBAccess.getMemberName());
						memberAgeField.setText(memberDBAccess.getMemberAge());
						enterDayField.setText(memberDBAccess.getMemberEnterDay());
						leaveDayField.setText(memberDBAccess.getMemberLeaveDay());
						memberAddressField.setText(memberDBAccess.getMemberAddress());
						memberTelField.setText(memberDBAccess.getMemberTel());
						categoryComboBox.setSelectedItem(memberDBAccess.getMemberCategory());
						memberMailField.setText(memberDBAccess.getMemberMail());

						if (mode == 2) { //	更新時

							// フィールド・コンボボックスをエディット(編集)可能にする
							memberNameField.setEditable(true);
							memberAgeField.setEditable(true);
							enterDayField.setEditable(true);
							leaveDayField.setEditable(true);
							memberAddressField.setEditable(true);
							memberTelField.setEditable(true);
							memberMailField.setEditable(true);
							categoryComboBox.setEnabled(true);
						}

						// 実行ボタンを使用可能にする
						executeButton.setEnabled(true);

					} else {

						// 会員情報が見つからない場合エラーメッセージを表示
						messageLabel.setText("会員番号 " + memberNumField.getText() + " の会員が見つかりませんでした");
					}
				}
			}
		});

		return searchButton;
	}

	private JLabel createMemberNameLabel() {
		memberNameLabel = new JLabel("氏名");
		memberNameLabel.setBounds(14, 145, 69, 24);
		memberNameLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return memberNameLabel;
	}

	private JTextField createMemberNameField() {
		memberNameField = new JTextField();
		memberNameField.setBounds(13, 175, 153, 30);
		memberNameField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 18));
		return memberNameField;
	}

	private JLabel createMemberAgeLabel() {
		memberAgeLabel = new JLabel("年齢");
		memberAgeLabel.setBounds(177, 147, 42, 24);
		memberAgeLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return memberAgeLabel;
	}

	private JTextField createMemberAgeField() {
		memberAgeField = new JTextField();
		memberAgeField.setBounds(176, 175, 100, 30);
		memberAgeField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 18));
		return memberAgeField;
	}

	private JLabel createEnterDayLabel() {
		enterDayLabel = new JLabel("入会日");
		enterDayLabel.setBounds(288, 146, 73, 25);
		enterDayLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return enterDayLabel;
	}

	private JTextField createEnterDayField() {
		enterDayField = new JTextField();
		enterDayField.setBounds(288, 175, 100, 30);
		enterDayField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 18));
		return enterDayField;
	}

	private JLabel createLeaveDayLabel() {
		leaveDayLabel = new JLabel("退会日");
		leaveDayLabel.setBounds(406, 146, 78, 25);
		leaveDayLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return leaveDayLabel;
	}

	private JTextField createLeaveDayField() {
		leaveDayField = new JTextField();
		leaveDayField.setBounds(405, 175, 100, 30);
		leaveDayField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 18));
		return leaveDayField;
	}

	private JLabel createMemberAddressLabel() {
		memberAddressLabel = new JLabel("住所");
		memberAddressLabel.setBounds(14, 218, 70, 27);
		memberAddressLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return memberAddressLabel;
	}

	private JTextField createMemberAddressField() {
		memberAddressField = new JTextField();
		memberAddressField.setBounds(13, 250, 495, 30);
		memberAddressField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 18));
		return memberAddressField;
	}

	private JLabel createMemberTelLabel() {
		memberTelLabel = new JLabel("電話番号");
		memberTelLabel.setBounds(14, 287, 71, 27);
		memberTelLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return memberTelLabel;
	}

	private JTextField createMemberTelField() {
		memberTelField = new JTextField();
		memberTelField.setBounds(13, 320, 245, 30);
		memberTelField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 18));
		return memberTelField;
	}

	private JLabel createCategoryLabel() {
		categoryLabel = new JLabel("会員種別");
		categoryLabel.setBounds(285, 288, 71, 24);
		categoryLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return categoryLabel;
	}

	//	優良区分選択ボックス
	private JComboBox<String> createCategoryComboBox() {
		categoryComboBox = new JComboBox<String>();
		categoryComboBox.setBounds(285, 320, 60, 30);
		categoryComboBox.addItem("優");
		categoryComboBox.addItem("良");
		categoryComboBox.addItem("可");
		categoryComboBox.addItem("不");
		return categoryComboBox;
	}

	private JLabel createMemberMailLabel() {
		memberMailLabel = new JLabel("メールアドレス");
		memberMailLabel.setBounds(15, 358, 100, 22);
		memberMailLabel.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
		return memberMailLabel;
	}

	private JTextField createMemberMailField() {
		memberMailField = new JTextField();
		memberMailField.setBounds(13, 385, 350, 30);
		memberMailField.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 18));
		return memberMailField;
	}

	//	実行ボタン
	private JButton createExecuteButton() {
		executeButton = new JButton("実行");
		executeButton.setBounds(380, 385, 70, 30);
		executeButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 18));

		executeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 新規登録
				if (mode == 1) {
					insertMember();
				}

				// 更新
				if (mode == 2) {
					updateMember();
				}

				// 削除
				if (mode == 3) {
					deleteMember();
				}
			}
		});
		return executeButton;
	}

	//	戻るボタン
	private JButton createBackButton() {
		backButton = new JButton("戻る");
		backButton.setBounds(460, 385, 70, 30);
		backButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 18));
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ウィンドウを閉じる
				MemberView.this.dispose();
			}
		});
		return backButton;
	}

	private JLabel createMessageLabel() {
		LineBorder border = new LineBorder(Color.BLACK, 2, true);
		messageLabel = new JLabel("");
		messageLabel.setBounds(13, 438, 517, 39);
		messageLabel.setBorder(border);
		return messageLabel;
	}

	//	会員登録処理
	private void insertMember() {

		int result = memberDBAccess.addMember(
				memberNumField.getText(),
				memberNameField.getText(),
				memberAddressField.getText(),
				memberAgeField.getText(),
				memberTelField.getText(),
				memberMailField.getText(),
				enterDayField.getText(),
				categoryComboBox.getItemAt(categoryComboBox.getSelectedIndex()));

		if (result == 1) {
			componentReset();
			messageLabel.setText("会員情報を登録しました。");
		} else {
			messageLabel.setText("会員登録に失敗しました。");
		}
	}

	//	会員情報更新処理
	private void updateMember() {

		int result = memberDBAccess.updateMember(
				memberNumField.getText(),
				memberNameField.getText(),
				memberAddressField.getText(),
				memberAgeField.getText(),
				memberTelField.getText(),
				memberMailField.getText(),
				enterDayField.getText(),
				leaveDayField.getText(),
				categoryComboBox.getItemAt(categoryComboBox.getSelectedIndex()));

		if (result == 1) {
			componentReset();
			messageLabel.setText("会員情報を更新しました。");
		} else {
			messageLabel.setText("会員更新に失敗しました。");
		}
	}

	//	会員削除処理
	private void deleteMember() {

		// 今日の日付の取得
		String leaveDay = getToday();

		int result = memberDBAccess.deleteMember(memberNumField.getText(), leaveDay);

		if (result == 1) {
			componentReset();
			messageLabel.setText("会員情報を削除しました。");
		} else {
			messageLabel.setText("会員削除に失敗しました。");
		}
	}

	//	今日の日付（文字列）取得
	private String getToday() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(new Date());
	}

	private void componentReset() {

		mode = 0;

		// フィールドのクリア
		memberNumField.setText("");
		memberNameField.setText("");
		memberAgeField.setText("");
		enterDayField.setText("");
		leaveDayField.setText("");
		memberAddressField.setText("");
		memberTelField.setText("");
		memberMailField.setText("");

		//		// チェックボックスを未選択に変更
		//		modeSelectGroup.clearSelection();

		// チェックボックスを未選択に変更
		insertCheckBox.setSelected(false);
		updateCheckBox.setSelected(false);
		deleteCheckBox.setSelected(false);

		// チェックボックスを選択可能に変更
		insertCheckBox.setEnabled(true);
		updateCheckBox.setEnabled(true);
		deleteCheckBox.setEnabled(true);

		// テキストボックスの初期化
		memberNumField.setEditable(false);
		memberNameField.setEditable(false);
		memberAgeField.setEditable(false);
		enterDayField.setEditable(false);
		leaveDayField.setEditable(false);
		memberAddressField.setEditable(false);
		memberTelField.setEditable(false);
		memberMailField.setEditable(false);

		// 会員区分の初期化
		categoryComboBox.setSelectedIndex(0);
		categoryComboBox.setEnabled(false);

		enterDayField.setFocusable(false);
		leaveDayField.setFocusable(false);

		//	検索・実行ボタンを使用不可
		searchButton.setEnabled(false);
		executeButton.setEnabled(false);
	}
}