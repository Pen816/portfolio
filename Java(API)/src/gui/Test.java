package gui;

import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Test extends JFrame {

	JTabbedPane tabbedPane;

	public Test() {

		// 初期指定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); /* ×を押されたらウィンドウを閉じ処理終了 */
		setTitle("API活用試作"); /* タイトル指定 */
		setSize(1200, 700); /* ウィンドウサイズ指定 */
		setLocationRelativeTo(null); /* windowを中央表示 */

		// JTabbedPane
		tabbedPane = new JTabbedPane();
		add(tabbedPane);

		// Tab 1
		JPanel tab1 = new Tab1();
		Setting.tabSet("地域検索", tab1, tabbedPane);

		// Tab 2
		JPanel tab2 = new Tab2();
		Setting.tabSet("動画検索", tab2, tabbedPane);

		// Tab 3
		JPanel tab3 = new Tab3();
		Setting.tabSet("ポケモン図鑑", tab3, tabbedPane);

	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() { /* 処理をGUIスレッドにのせる */
			@Override
			public void run() {
				new Test().setVisible(true); /* 画面出力 */
			}
		});
	}
}

//	タブ生成
class Setting {
	public static void tabSet(String title, JPanel tab, JTabbedPane tabPane) {
		tab.setLayout(new BoxLayout(tab, BoxLayout.Y_AXIS));
		tabPane.addTab(title, tab);
	}
}