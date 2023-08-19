package WeatherSearch;

//bbb.javaの天気調べをメソッド？でまとめてみる

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Weather {

	public static String search_weather(double[] nums) throws IOException {

		//		//		Scanner scan = new Scanner(System.in);
		//		//		String zipcode = scan.next();
		//
		//		// URLに接続
		//		String urlString = "http://geoapi.heartrails.com/api/json?method=searchByPostal&postal=" + zipcode;
		//		java.net.URL url = new java.net.URL(urlString);
		//		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		//		con.connect();
		//
		//		// JSONデータの読み込み
		//		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		//		String tmp;
		//		tmp = in.readLine();
		//
		//		// 緯度、経度を抽出
		//		String[] str = tmp.split(",");
		//		//		for (String st : str) {
		//		//System.out.println(st);
		//		//		}
		//
		//		String longitude = "";
		//		String latitude = "";
		//
		//		try {
		//			longitude += str[4].substring(5, str[4].length() - 5);
		//			latitude += str[5].substring(5, str[5].length() - 5);
		//
		//		} catch (ArrayIndexOutOfBoundsException e) {// 捕まえる例外の型 例外の変数名
		//			// 例外発生時の処理
		//			//			String error = "存在しないコードです";
		//			return "天気はここに表示されます";
		//			//			System.out.println(error);
		//		}
		//		double num1 = Double.parseDouble(longitude);
		//		double num2 = Double.parseDouble(latitude);

		// 天気を取得
		String requestUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + nums[1] + "&longitude=" + nums[0]
				+ "&daily=weathercode&forecast_days=1&timezone=Asia%2FTokyo";
		//System.out.println(longitude);
		//System.out.println(latitud);
		//System.out.println(requestUrl);

		URL url2 = new URL(requestUrl);
		HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();

		int responseCode = connection.getResponseCode();
		String weather = new String();
		//System.out.println(responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) {
			InputStreamReader isr = new InputStreamReader(connection.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String wth = br.readLine();
			//System.out.println(br.readLine()); //Json形式でデータが取得できるのでJackson等で処理する

			// ウェザーコードを2文字に切り取る
			String code = wth.substring(wth.length() - 5, wth.length() - 3);
			//System.out.println(code);
			// 正規表現のパターンを生成
			Pattern pattern = Pattern.compile("[0-9]+");
			// 正規表現処理をおこなうためのクラスを取得
			Matcher matcher = pattern.matcher(code);
			// 正規表現のパターンに適合するかチェック
			//System.out.println(matcher.matches());
			// ウェザーコードが1文字にする
			if ((matcher.matches()) == false) {
				code = code.substring(code.length() - 1, code.length() - 0);
				//System.out.println(weather);
			}

			// 天気をウェザーコードで判別して表示
			int weather_code = Integer.parseInt(code);
			//System.out.println(weather_code);

			switch (weather_code) {
			case 0:
				weather = "快晴";
				break;
			case 1:
				weather = "晴れ";
				break;
			case 2:
				weather = "ときどき曇";
				break;
			case 3:
				weather = "曇り";
				break;
			case 45:
			case 48:
				weather = "霧";
				break;
			case 51:
			case 53:
			case 55:
			case 56:
			case 57:
				weather = "霧雨";
				break;
			case 61:
			case 63:
			case 65:
			case 66:
			case 67:
				weather = "雨";
				break;
			case 71:
			case 73:
			case 75:
			case 77:
				weather = "雪";
				break;
			case 80:
			case 81:
			case 82:
				weather = "にわか雨";
				break;
			case 85:
			case 86:
				weather = "にわか雪";
				break;
			default:
				weather = "不明";
			}
			//System.out.println(weather);
		} else {
			System.out.println("取得失敗");
		}

		// 終了処理
		//		in.close();
		//		scan.close();
		//		con.disconnect();
		return weather;
	}

	//	public static void main(String[] args) throws IOException {
	//		String tenki=search_weather();
	//		System.out.println(tenki);
	//		
	//		
	//	}
}
