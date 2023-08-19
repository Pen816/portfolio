package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import poke.SearchPoke;

public class Tab3 extends JPanel implements ActionListener {
	// Tab部品生成
	JButton button;
	JTextField pokeNo;
	JLabel label3, label5;
	JLabel[] label2 = new JLabel[5];

	//Tabレイアウト
	public Tab3() {
		// 最上部余白
		JPanel inner1 = new JPanel();
		inner1.setPreferredSize(new Dimension(0, 100));
		//	inner1.setBorder(new LineBorder(Color.RED, 2, true));
		add(inner1);

		// 入力ラベル
		JLabel label1 = new JLabel("ポケモン図鑑Noを入力してください");
		label1.setFont(new Font("SansSerif", Font.BOLD, 16));
		label1.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label1);

		JLabel label2 = new JLabel("1～151の範囲で指定してください");
		label2.setFont(new Font("SansSerif", Font.BOLD, 16));
		label2.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label2);

		// TextBox上部余白
		JPanel innerTB_1 = new JPanel();
		innerTB_1.setPreferredSize(new Dimension(0, 5));
		//	innerTB_1.setBorder(new LineBorder(Color.RED, 2, true));
		add(innerTB_1);

		// TextBox + 下部余白
		JPanel innerTB_2 = new JPanel();
		innerTB_2.add(pokeNo = new JTextField(3));
		pokeNo.setPreferredSize(new Dimension(0, 28));
		pokeNo.setFont(new Font("SansSerif", Font.PLAIN, 18));
		innerTB_2.setPreferredSize(new Dimension(0, 45));
		//	innerTB_2.setBorder(new LineBorder(Color.RED, 2, true));
		add(innerTB_2);

		// 検索ボタン
		button = new JButton("検索");
		button.setFont(new Font("SansSerif", Font.BOLD, 16));
		//		button.setHorizontalAlignment(button.CENTER);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(this);
		add(button);

		// 入出力間余白
		JPanel inner2 = new JPanel();
		inner2.setPreferredSize(new Dimension(0, 45));
		//	inner2.setBorder(new LineBorder(Color.RED, 2, true));
		add(inner2);

		//	 出力ラベル（名前）
		label3 = new JLabel("名前はここに表示されます");
		label3.setFont(new Font("SansSerif", Font.BOLD, 16));
		label3.setAlignmentX(Component.CENTER_ALIGNMENT); // 左右中央に配置
		//	label3.setBorder(new LineBorder(Color.RED, 2, true));
		add(label3);

		//	名前解説間余白
		JPanel inner3 = new JPanel();
		inner3.setPreferredSize(new Dimension(0, 5));
		//	inner3.setBorder(new LineBorder(Color.RED, 2, true));
		add(inner3);

		//	出力ラベル（解説）
		label5 = new JLabel("解説はここに表示されます");
		label5.setFont(new Font("SansSerif", Font.BOLD, 16));
		label5.setAlignmentX(Component.CENTER_ALIGNMENT); // 左右中央に配置
		//	label5.setBorder(new LineBorder(Color.RED, 2, true));
		add(label5);

		//		JPanel[] inner4 = new JPanel[3];
		//		for (int i = 0; i < 3; i++) {
		//			inner4[i] = new JPanel();
		//		}
		//
		//		label5 = new JLabel();
		//		JLabel label6 = new JLabel();
		//		JLabel label7 = new JLabel();
		//
		//		for (int i = 0; i < 3; i++) {
		//
		//			inner4[i].setBorder(new LineBorder(Color.RED, 2, true));//	赤枠線
		//
		//		}
		//
		//		inner4[1].setPreferredSize(new Dimension(100, 30));// 横幅指定
		//
		//		//		label5 = new JLabel("解説はここに表示されます");
		//		label5 = new JLabel("<html>ここに文字を書く<br>改行後<html>");
		//
		//		label5.setHorizontalAlignment(JLabel.CENTER);
		//		label5.setFont(new Font("SansSerif", Font.BOLD, 16));
		//		inner4[1].add(label5);
		//
		//		label6 = new JLabel("");
		//		label6.setHorizontalAlignment(JLabel.CENTER);
		//		label6.setFont(new Font("SansSerif", Font.BOLD, 16));
		//		inner4[1].add(label6);
		//
		//		label7 = new JLabel("");
		//		label7.setHorizontalAlignment(JLabel.CENTER);
		//		label7.setFont(new Font("SansSerif", Font.BOLD, 16));
		//		inner4[1].add(label7);
		//
		//		//	3つの図書館を横配置で表示
		//		JPanel inner4_main = new JPanel();
		//		inner4_main.setLayout(new BoxLayout(inner4_main, BoxLayout.LINE_AXIS));
		//		inner4_main.setPreferredSize(new Dimension(0, 100));
		//		//		inner4_main.setBorder(new LineBorder(Color.RED, 2, true));
		//		for (JPanel tmp : inner4) {
		//			inner4_main.add(tmp);
		//		}
		//		add(inner4_main);

		// 最下部余白
		JPanel inner5 = new JPanel();
		inner5.setPreferredSize(new Dimension(0, 100));
		//	inner5.setBorder(new LineBorder(Color.RED, 2, true));
		add(inner5);
	}

	// 検索ボタンが押された時の動作
	public void actionPerformed(ActionEvent ae) {

		try {
			if (Integer.parseInt(pokeNo.getText()) > 0 && Integer.parseInt(pokeNo.getText()) < 152) {

				String[] pokeData;
				pokeData = SearchPoke.searchPoke(Integer.parseInt(pokeNo.getText()));

				label3.setText(pokeData[0]);
				label5.setText(pokeData[1]);

			} else {
				label3.setText("範囲外です　再入力してください");
				label5.setText(" ");
			}

		} catch (Exception e) {
			label3.setText("範囲外です　再入力してください");
			label5.setText(" ");
		}
	}
}
