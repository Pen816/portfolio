package library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Library {

	public static final int LIBRARIES = 3;//検索する図書館の数

	public static final int INFORMATION = 3;//取得したい図書館の情報の数

	public static final String APIKEY = "f5a6e50a1fac601cb951e3ad559e04e5";

	public static JsonNode jsonResult(double latitude, double longitude) {//latitudeとlongitudeは郵便番号から取得したものを代入する

		JsonNode jsonResultArray = null; //jsonResultメソッドの戻り値

		String result = "";
		String url = "https://api.calil.jp/library?apikey={" + APIKEY + "}&geocode=" + longitude + "," + latitude
				+ "&LIBRARIES=" + LIBRARIES + "&format=json&callback=";

		try {

			//HTTP接続処理
			URL urlString = new URL(url);
			HttpURLConnection con = (HttpURLConnection) urlString.openConnection();
			con.connect();

			//JSON形式で取得
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String tmp = "";

			//readLineで読み込んだ値がnullになるまでループして
			//resultに1行の文字列として代入
			while ((tmp = br.readLine()) != null) {
				result += tmp;
			}

			//resultに代入した1行の文字列をJsonNode型に変換
			ObjectMapper mapper = new ObjectMapper();
			jsonResultArray = mapper.readTree(result);

			br.close();
			con.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("取得失敗");
		}

		return jsonResultArray; //JsonNode型として返す
	}

	/*
	 * ①JsonNode型の変数を宣言してその変数にjsonResultメソッドの戻り値を代入
	 *
	 * ②jsonResultメソッドの引数には
	 * 郵便番号を入力して得られる緯度と経度を渡す(緯度(latitude) < 経度(longitude))
	 *
	 * ③0から定数LIBRARIESまでループさせる
	 *
	 * ④getJson.get( 添字 ).get("formal") ->正式名称を得る時は"formal"を指定
	 * ⑤getJson.get( 添字 ).get("short")  ->略称を得るときは"short"を指定
	 * ⑥getJson.get( 添字 ).get("address")->その図書館の住所を得る時は"address"を指定
	 */

	public static String[][] getLibInfo(double[] nums) throws IOException {

		//		double[] nums = { 0, 0 };

		//		nums = ZipcodeSearch.address();
		//
		//		double latitude = nums[1];
		//		double longitude =nums[0];

		JsonNode getJson = jsonResult(nums[1], nums[0]);
		String[][] retLibInfo = new String[LIBRARIES][INFORMATION];

		//図書館の正式名称(formal)と略称(short)と住所(address)を1つの行にそれぞれ格納
		//追加で情報を取得したい場合は定数INFORMATIONを変更する

		for (int j = 0; j < LIBRARIES; j++) {
			retLibInfo[j][0] = getJson.get(j).get("formal").asText();
			retLibInfo[j][1] = getJson.get(j).get("short").asText();
			retLibInfo[j][2] = getJson.get(j).get("address").asText();
		}

		return retLibInfo;
	}

	//=============呼び出すときの処理例===============↓

	//	public static void main(String[] args) throws IOException {
	//
	//		//検索する件数分の行を用意して列は3
	//		String[][] LibInfo = new String[LIBRARIES][INFORMATION];
	//
	//		//getLibInfoメソッドを呼び出して戻り値(String[][]型)を代入
	//		LibInfo = getLibInfo();
	//
	//		//図書館情報の表示
	//		for (int j = 0; j < LIBRARIES; j++) {
	//
	//			System.out.println("No." + (j + 1));
	//
	//			for (int i = 0; i < INFORMATION; i++) {
	//				System.out.println(LibInfo[j][i]);
	//			}
	//
	//			if (j != LIBRARIES - 1)
	//				System.out.println("\n");
	//		}
	//	}
}