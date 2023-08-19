package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import WeatherSearch.Weather;
import library.Library;
import tude.TudeSearch;
import zipcodeSearch.Zipcode;

public class Tab1 extends JPanel implements ActionListener {

	//	定数宣言
	final static int LIBRARY_NUM = 3;// 図書館情報表示数

	// Tab部品生成
	JButton button;
	JTextField postCode;
	JLabel label2, label3;
	public static JLabel label4;
	public static JLabel label5[] = new JLabel[LIBRARY_NUM];
	public static JLabel label6[] = new JLabel[LIBRARY_NUM];
	public static JLabel label7[] = new JLabel[LIBRARY_NUM];

	//Tabレイアウト
	public Tab1() {
		// 最上部余白
		JPanel inner1 = new JPanel();
		inner1.setPreferredSize(new Dimension(0, 100));
		//	inner1.setBorder(new LineBorder(Color.RED, 2, true));
		add(inner1);

		// 入力ラベル
		JLabel label1 = new JLabel("郵便番号を入力してください（ハイフンなし）");
		label1.setFont(new Font("SansSerif", Font.BOLD, 16));
		label1.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label1);

		// TextBox上部余白
		JPanel innerTB_1 = new JPanel();
		innerTB_1.setPreferredSize(new Dimension(0, 15));
		//	innerTB_1.setBorder(new LineBorder(Color.RED, 2, true));
		add(innerTB_1);

		// TextBox + 下部余白
		JPanel innerTB_2 = new JPanel();
		innerTB_2.add(postCode = new JTextField(6));
		postCode.setPreferredSize(new Dimension(0, 28));
		postCode.setFont(new Font("SansSerif", Font.PLAIN, 18));
		innerTB_2.setPreferredSize(new Dimension(0, 45));
		//	innerTB_2.setBorder(new LineBorder(Color.RED, 2, true));
		add(innerTB_2);

		// 変換ボタン
		button = new JButton("変換");
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setFont(new Font("SansSerif", Font.BOLD, 14));
		button.addActionListener(this);
		add(button);

		// 入出力間余白
		JPanel inner2 = new JPanel();
		inner2.setPreferredSize(new Dimension(0, 40));
		//	inner2.setBorder(new LineBorder(Color.RED, 2, true));
		add(inner2);

		// 出力ラベル
		label2 = new JLabel("ｼﾞｭｳｼｮ ﾊ ｺｺ ﾆ ﾋｮｳｼﾞ ｻﾚﾏｽ");
		label2.setFont(new Font("SansSerif", Font.BOLD, 14));
		label2.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label2);

		// 出力ラベル
		label3 = new JLabel("住所はここに表示されます");
		label3.setFont(new Font("SansSerif", Font.BOLD, 16));
		label3.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label3);

		// 住所天気間余白
		JPanel inner3 = new JPanel();
		inner3.setPreferredSize(new Dimension(0, 20));
		//	inner3.setBorder(new LineBorder(Color.RED, 2, true));
		add(inner3);

		// 出力ラベル
		label4 = new JLabel("天気はここに表示されます");
		label4.setFont(new Font("SansSerif", Font.BOLD, 16));
		label4.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label4);

		// 天気図書館間余白
		JPanel inner4 = new JPanel();
		inner4.setPreferredSize(new Dimension(0, 40));
		//	inner4.setBorder(new LineBorder(Color.RED, 2, true));
		add(inner4);

		// 出力ラベル
		JPanel[] inner5 = new JPanel[LIBRARY_NUM];

		for (int i = 0; i < LIBRARY_NUM; i++) {
			inner5[i] = new JPanel();
		}

		for (int i = 0; i < LIBRARY_NUM; i++) {

			inner5[i].setPreferredSize(new Dimension(0, 20));
			//	inner5_1.setBorder(new LineBorder(Color.RED, 2, true));

			label5[i] = new JLabel("No" + (i + 1) + ".");
			label5[i].setPreferredSize(new Dimension(300, 20));
			label5[i].setHorizontalAlignment(JLabel.LEFT);
			label5[i].setFont(new Font("SansSerif", Font.BOLD, 16));
			inner5[i].add(label5[i]);

			label6[i] = new JLabel("付近の図書館名はここに表示されます");
			label6[i].setPreferredSize(new Dimension(300, 20));
			label6[i].setHorizontalAlignment(JLabel.CENTER);
			label6[i].setFont(new Font("SansSerif", Font.BOLD, 16));
			inner5[i].add(label6[i]);

			label7[i] = new JLabel("付近の図書館の住所はここに表示されます");
			label7[i].setFont(new Font("SansSerif", Font.BOLD, 16));
			inner5[i].add(label7[i]);

		}

		//	3つの図書館を横配置で表示
		JPanel inner5_main = new JPanel();
		inner5_main.setLayout(new BoxLayout(inner5_main, BoxLayout.LINE_AXIS));
		inner5_main.setPreferredSize(new Dimension(0, 100));
		//	inner5_main.setBorder(new LineBorder(Color.RED, 2, true));
		for (JPanel tmp : inner5) {
			inner5_main.add(tmp);
		}
		add(inner5_main);

		// 最下部余白
		JPanel inner6 = new JPanel();
		inner6.setPreferredSize(new Dimension(0, 90));
		//	inner6.setBorder(new LineBorder(Color.RED, 2, true));
		add(inner6);

	}

	// 変換ボタンが押された時の動作
	public void actionPerformed(ActionEvent ae) {

		try {
			//***** 住所出力 *****
			String str[] = Zipcode.return_address(postCode.getText());

			label2.setText(str[0]);
			label3.setText(str[1]);

			//***** 緯度・経度取得 *****
			double tude[] = TudeSearch.getTude(postCode.getText());

			//	error値が返ってきたときはerrorをthrow
			if (tude == null) {
				throw new IOException();
			}

			//***** 天気出力 *****
			String tenki = Weather.search_weather(tude);
			label4.setText(tenki);

			//***** 図書館出力 *****
			String[][] LibInfo = new String[Library.LIBRARIES][Library.INFORMATION];

			LibInfo = Library.getLibInfo(tude);
			for (int j = 0; j < Library.LIBRARIES; j++) {
				label6[j].setText(LibInfo[j][0]);
				label7[j].setText(LibInfo[j][2]);

			}

		} catch (ArrayIndexOutOfBoundsException | IOException e) {

			label4.setText("天気はここに表示されます");

			for (int i = 0; i < LIBRARY_NUM; i++) {

				label5[i].setText("No" + (i + 1) + ".");
				label6[i].setText("付近の図書館名はここに表示されます");
				label7[i].setText("付近の図書館の住所はここに表示されます");

			}

		}

	}

}
