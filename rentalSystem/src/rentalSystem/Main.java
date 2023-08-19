package rentalSystem;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import view.MenuView;

//アプリケーションの初期起動部
public class Main extends JFrame implements ActionListener {

	private JLabel userNameLabel = new JLabel("ユーザ名：");;
	private JTextField userNameField = new JTextField("root", 15);
	private JLabel passwordLabel = new JLabel("パスワード：");
	private JPasswordField passwordField = new JPasswordField("password", 15);
	private JButton inputButton = new JButton("入力");
	private final String url = "jdbc:mysql://localhost/RentalDB?useSSL=false&serverTimezone=Asia/Tokyo";

	public static void main(String[] args) {

		Main main = new Main();
		main.setVisible(true);//表示メソッド(true:表示、false:非表示)

	}

	public Main() {

		setTitle("データベース接続"); // タイトル指定
		setSize(300, 150); // ウィンドウサイズ指定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ✖を押されたらウィンドウを閉じ処理終了
		setLocationRelativeTo(null); // windowを中央表示

		Container cntnr = getContentPane();
		GridLayout grid = new GridLayout(4, 2);
		cntnr.setLayout(grid);

		//ラベルテキストを右詰め
		userNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		cntnr.add(userNameLabel);
		cntnr.add(userNameField);
		cntnr.add(passwordLabel);
		cntnr.add(passwordField);
		cntnr.add(inputButton);

		inputButton.addActionListener(this);

	}

	//	入力(input)ボタン押下時の処理
	@Override
	public void actionPerformed(ActionEvent ae) {

		//		入力値取得
		String name = userNameField.getText();
		String pass = new String(passwordField.getPassword());
		;

		//		データベース接続
		try (Connection connection = DriverManager.getConnection(url, name, pass);) {
			MenuView menuView = new MenuView(url, name, pass);
			menuView.setVisible(true);// メニューウィンドウ出力

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
