package rentalSystem;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * 機能選択画面クラス
 *
 * 「会員管理」「DVD管理」「貸出」「返却」「DVD検索」の各機能を選択する。
 * 終了する場合は「終了」を選択する。
 */
public class MenuView extends JFrame implements ActionListener {

	private JButton memberButton = new JButton("会員管理");
	private JButton dvdsButton = new JButton("DVD管理");
	private JButton rentalButton = new JButton("貸出");
	private JButton giveBackButton = new JButton("返却");
	private JButton searchButton = new JButton("DVD検索");
	private JButton exitButton = new JButton("終了");

	private final String url = "jdbc:mysql://localhost/RentalSystemDB?useSSL=false&serverTimezone=JST";
	private final String userName = "username";
	private final String password = "password";

	/**
	 * コンストラクタ
	 *
	 */
	public MenuView() {

		super("レンタルシステム<メニュー>");

		setLocation(300, 300);
		setSize(320, 300);

		Container container = getContentPane();

		GridLayout gridLayout = new GridLayout(3, 2, 5, 5);
		container.setLayout(gridLayout);

		container.add(rentalButton);
		container.add(giveBackButton);
		container.add(memberButton);
		container.add(dvdsButton);
		container.add(searchButton);
		container.add(exitButton);

		/**
		 * ActionListenerの実装方法には様々あるため
		 * ここでは実装の際の参考として、いくつかの例を提示しています。
		 * 実際に実装する場合は、このようにバラバラに実装するのではなく
		 * 統一すべきです
		 */

		// 例1．インターフェースによる実装
		memberButton.addActionListener(this);
		dvdsButton.addActionListener(this);

		// 例2．クラス(サブクラス)の実装
		rentalButton.addActionListener(new RentalButtonActionListener());

		// 例3．匿名クラスによる実装
		giveBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				// 返却画面を生成・表示する
				ReturnView returnView = new ReturnView(url, userName, password);
				returnView.setVisible(true);
			}
		});

		// 例4．ラムダ式による実装①
		searchButton.addActionListener(
				(ActionEvent event) -> {

					// DVD検索画面を生成・表示する
					SearchView searchView = new SearchView(url, userName, password);
					searchView.setVisible(true);
				});

		// 例5．ラムダ式による実装②
		exitButton.addActionListener(
				(ActionEvent) -> {
					MenuView.this.dispose();
					System.exit(0);
				});
	}

	/**
	 * インターフェースによる実装のため、インターフェースで定義されている
	 * メソッドをオーバーライドする
	 *
	 * この方式で複数のボタンのActionListenerの動作を実装する場合、
	 * ActionEventからどのボタンが押されたのかを判別する必要がある
	 *
	 * @param event 発生したイベント
	 */
	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == memberButton) {

			// 会員管理ボタンが押された場合は、会員管理画面を生成・表示する
			MemberView memberView = new MemberView(url, userName, password);
			memberView.setVisible(true);

		} else if (event.getSource() == dvdsButton) {

			// DVD管理ボタンが押された場合は、DVD管理画面を生成・表示する
			DvdView dvdView = new DvdView(url, userName, password);
			dvdView.setVisible(true);
		}
	}

	/**
	 * 貸出ボタンを押した際のイベント処理をおこなうクラス（サブクラス）を定義
	 */
	private class RentalButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {

			// 貸出画面を生成する
			RentalView rentalView = new RentalView(url, userName, password);

			// 貸出画面を表示する
			rentalView.setVisible(true);
		}

	}
}
