package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import search.Search;

public class Tab2 extends JPanel implements ActionListener {
	// Tab部品生成
	JButton button;
	JTextField word;
	JLabel[] label2 = new JLabel[5];

	//Tabレイアウト
	public Tab2() {
		// 最上部余白
		JPanel inner1 = new JPanel();
		inner1.setPreferredSize(new Dimension(0, 100));
		//	inner1.setBorder(new LineBorder(Color.RED, 2, true));
		add(inner1);

		// 入力ラベル
		JLabel label1_1 = new JLabel("Youtubeの動画のタイトル検索を行います");
		label1_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		label1_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label1_1);
		JLabel label1_2 = new JLabel("検索ワードを入力してください");
		label1_2.setFont(new Font("SansSerif", Font.BOLD, 16));
		label1_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label1_2);

		// TextBox上部余白
		JPanel innerTB_1 = new JPanel();
		innerTB_1.setPreferredSize(new Dimension(0, 5));
		//	innerTB_1.setBorder(new LineBorder(Color.RED, 2, true));
		add(innerTB_1);

		// TextBox + 下部余白
		JPanel innerTB_2 = new JPanel();
		innerTB_2.add(word = new JTextField(20));
		word.setPreferredSize(new Dimension(0, 28));
		word.setFont(new Font("SansSerif", Font.PLAIN, 18));
		innerTB_2.setPreferredSize(new Dimension(0, 45));
		//	innerTB_2.setBorder(new LineBorder(Color.RED, 2, true));
		add(innerTB_2);

		// 検索ボタン
		button = new JButton("検索");
		button.setFont(new Font("SansSerif", Font.BOLD, 16));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(this);
		add(button);

		// 入出力間余白
		JPanel inner2 = new JPanel();
		inner2.setPreferredSize(new Dimension(0, 55));
		//	inner2.setBorder(new LineBorder(Color.RED, 2, true));
		add(inner2);

		// 出力ラベル
		for (int i = 0; i < 5; i++) {
			label2[i] = new JLabel(i + 1 + "." + "検索結果はここに表示されます");
			label2[i].setFont(new Font("SansSerif", Font.BOLD, 14));
			label2[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			label2[i].setBorder(BorderFactory.createEmptyBorder(0, 0, 7, 0));
			add(label2[i]);
		}

		// 最下部余白
		JPanel inner3 = new JPanel();
		inner3.setPreferredSize(new Dimension(0, 100));
		//	inner3.setBorder(new LineBorder(Color.RED, 2, true));
		add(inner3);

	}

	// 検索ボタンが押された時の動作
	public void actionPerformed(ActionEvent ae) {
		String[] result = new String[5];
		int i = 0;

		//	word(textBox)が空値のとき
		if ("".equals(word.getText())) {
			for (; i < 5; i++) {
				if (i == 2) {
					label2[2].setText("検索ワードを入力してください");
				} else {
					label2[i].setText("");
				}
			}
		} else {
			// word(textBox)に値がある場合検索処理呼び出し
			result = Search.titlename(word.getText());

			// resultの内容でlabel2変更
			for (String str : result) {
				label2[i].setText(i + 1 + " . " + str);
				i++;
			}
		}

	}
}