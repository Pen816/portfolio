package zipcodeSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

//import Weather;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gui.Tab1;

public class Zipcode {

	//	public static String zipMethod() {
	//		// 郵便番号入力 (例)811-3135 福岡県古賀市小竹
	//		Scanner scan = new Scanner(System.in);
	//		String zipcode = scan.next();
	//		scan.close();
	//		return zipcode;
	//	}

	public static String[] return_address(String zipcode) throws IOException {

		//		String zipcode = zipMethod();

		// URL作って接続
		String urlString = "https://zipcloud.ibsnet.co.jp/api/search" + "?zipcode=" + zipcode;
		java.net.URL url = new java.net.URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.connect();

		// JSONデータを読み込み、カナと住所を抽出
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String[] kana = new String[2];

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(new BufferedReader(new InputStreamReader(con.getInputStream())));

			String kana1 = node.get("results").get(0).get("kana1").asText();
			String kana2 = node.get("results").get(0).get("kana2").asText();
			String kana3 = node.get("results").get(0).get("kana3").asText();
			kana[0] = kana1 + kana2 + kana3;
			//System.out.println(kana[0]);

			String address1 = node.get("results").get(0).get("address1").asText();
			String address2 = node.get("results").get(0).get("address2").asText();
			String address3 = node.get("results").get(0).get("address3").asText();
			kana[1] = address1 + address2 + address3;
			//System.out.println(kana[1]);

		} catch (NullPointerException e) {

			kana[1] = "存在しない郵便番号です";
			Tab1.label4.setText("天気はここに表示されます");

			for (int i = 0; i < 3; i++) {
				Tab1.label5[i].setText("No" + i + 1 + ".");
				Tab1.label6[i].setText("付近の図書館名はここに表示されます");
				Tab1.label7[i].setText("付近の図書館の住所はここに表示されます");
			}
		}

		in.close();
		con.disconnect();
		return kana;
	}

	//	public static void main(String[] args) throws IOException {
	//		String zipcode = zipMethod();
	//		String[] str=return_address(zipcode);
	//		System.out.println(str[0]);//kana
	//		System.out.println(str[1]);//kanji
	//		System.out.println(Weather.search_weather(zipcode));//tenki
	//	}
}
