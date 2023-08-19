package rentalSystem;

import javax.swing.JFrame;


/**
 * レンタルシステム
 *
 * 起動用クラス
 */
public class Main {

	/**
	 * メインメソッド
	 *
	 * プログラムを起動した際に最初に呼び出されるメソッド。
	 *
	 * @param args 起動時に指定した引数
	 */
	public static void main(String[] args) throws Exception {

		MenuView menuView = new MenuView();
		menuView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuView.setVisible(true);
	}
}