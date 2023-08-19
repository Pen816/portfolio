package view;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

//メニューの役割となる各機能の呼び出し部
public class MenuView extends JFrame implements ActionListener {

	private JButton rentalButton = new JButton("貸出");
	private JButton returnButton = new JButton("返却");
	private JButton memberButton = new JButton("会員管理");
	private JButton dvdButton = new JButton("DVD管理");
	private JButton searchButton = new JButton("DVD検索");
	private JButton exitButton = new JButton("終了");

	private final String name, pass, url;// データベース接続用データ

	public MenuView(String name, String pass, String url) {

		this.name = name;
		this.pass = pass;
		this.url = url;

		// 初期指定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); /* ×を押されたらウィンドウを閉じ処理終了 */
		setTitle("機能選択画面"); /* タイトル指定 */
		setSize(300, 325); /* ウィンドウサイズ指定 */
		setLocationRelativeTo(null); /* windowを中央表示 */

		Container cntnr = getContentPane();
		GridLayout grid = new GridLayout(3, 2);
		cntnr.setLayout(grid);

		rentalButton.addActionListener(this);
		returnButton.addActionListener(this);
		memberButton.addActionListener(this);
		dvdButton.addActionListener(this);
		searchButton.addActionListener(this);
		exitButton.addActionListener(this);

		cntnr.add(rentalButton);
		cntnr.add(returnButton);
		cntnr.add(memberButton);
		cntnr.add(dvdButton);
		cntnr.add(searchButton);
		cntnr.add(exitButton);

	}

	// ボタンが押された時の動作
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == rentalButton) {
			RentalView rentalView = new RentalView(url, name, pass);
			rentalView.setVisible(true);
		} else if (ae.getSource() == returnButton) {
			ReturnView returnView = new ReturnView(url, name, pass);
			returnView.setVisible(true);
		} else if (ae.getSource() == memberButton) {
			MemberView memberView = new MemberView(url, name, pass);
			memberView.setVisible(true);
		} else if (ae.getSource() == dvdButton) {
			DvdView dvdView = new DvdView(url, name, pass);
			dvdView.setVisible(true);
		} else if (ae.getSource() == searchButton) {
			SearchView searchView = new SearchView(url, name, pass);
			searchView.setVisible(true);
		} else if (ae.getSource() == exitButton) {
			System.exit(0);
		}
	}

}
