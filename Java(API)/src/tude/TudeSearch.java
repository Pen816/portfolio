package tude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class TudeSearch {

	public static double[] getTude(String zipcode) throws IOException {

		double num1 = 0;
		double num2 = 0;

		//	Scanner scan = new Scanner(System.in);
		//	String zipcode = scan.next();

		// URLに接続
		String urlString = "http://geoapi.heartrails.com/api/json?method=searchByPostal&postal=" + zipcode;
		java.net.URL url = new java.net.URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.connect();

		// JSONデータの読み込み
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String tmp;
		tmp = in.readLine();

		// 緯度、経度を抽出
		String[] str = tmp.split(",");

		String longitude = "";
		String latitude = "";

		try {
			longitude += str[4].substring(5, str[4].length() - 5);
			latitude += str[5].substring(5, str[5].length() - 5);
		} catch (ArrayIndexOutOfBoundsException e) {
			double[] nums = null;
			return nums;
		}

		num1 = Double.parseDouble(longitude);
		num2 = Double.parseDouble(latitude);

		double[] nums = new double[2];
		nums[0] = num1;
		nums[1] = num2;

		//		scan.close();
		return nums;
	}
}
