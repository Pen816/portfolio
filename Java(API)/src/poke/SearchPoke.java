package poke;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SearchPoke {

	public static String retName(String engName) {

		HashMap<String, String> japaneseName = new HashMap<>();

		japaneseName.put("bulbasaur", "フシギダネ");
		japaneseName.put("ivysaur", "フシギソウ");
		japaneseName.put("venusaur", "フシギバナ");
		japaneseName.put("charmander", "ヒトカゲ");
		japaneseName.put("charmeleon", "リザード");
		japaneseName.put("charizard", "リザードン");
		japaneseName.put("squirtle", "ゼニガメ");
		japaneseName.put("wartortle", "カメール");
		japaneseName.put("blastoise", "カメックス");
		japaneseName.put("caterpie", "キャタピー");
		japaneseName.put("metapod", "トランセル");
		japaneseName.put("butterfree", "バタフリー");
		japaneseName.put("weedle", "ビードル");
		japaneseName.put("kakuna", "コクーン");
		japaneseName.put("beedrill", "スピアー");
		japaneseName.put("pidgey", "ポッポ");
		japaneseName.put("pidgeotto", "ピジョン");
		japaneseName.put("pidgeot", "ピジョット");
		japaneseName.put("rattata", "コラッタ");
		japaneseName.put("raticate", "ラッタ");
		japaneseName.put("spearow", "オニスズメ");
		japaneseName.put("fearow", "オニドリル");
		japaneseName.put("ekans", "アーボ");
		japaneseName.put("arbok", "アーボック");
		japaneseName.put("pikachu", "ピカチュウ");
		japaneseName.put("raichu", "ライチュウ");
		japaneseName.put("sandshrew", "サンド");
		japaneseName.put("sandslash", "サンドパン");
		japaneseName.put("nidoran-f", "ニドラン♀");
		japaneseName.put("nidorina", "ニドリーナ");
		japaneseName.put("nidoqueen", "ニドクイン");
		japaneseName.put("nidoran-m", "ニドラン♂");
		japaneseName.put("nidorino", "ニドリーノ");
		japaneseName.put("nidoking", "ニドキング");
		japaneseName.put("clefairy", "ピッピ");
		japaneseName.put("clefable", "ピクシー");
		japaneseName.put("vulpix", "ロコン");
		japaneseName.put("ninetales", "キュウコン");
		japaneseName.put("jigglypuff", "プリン");
		japaneseName.put("wigglytuff", "プクリン");
		japaneseName.put("zubat", "ズバット");
		japaneseName.put("golbat", "ゴルバット");
		japaneseName.put("oddish", "ナゾノクサ");
		japaneseName.put("gloom", "クサイハナ");
		japaneseName.put("vileplume", "ラフレシア");
		japaneseName.put("paras", "パラス");
		japaneseName.put("parasect", "パラセクト");
		japaneseName.put("venonat", "コンパン");
		japaneseName.put("venomoth", "モルフォン");
		japaneseName.put("diglett", "ディグダ");
		japaneseName.put("dugtrio", "ダグトリオ");
		japaneseName.put("meowth", "ニャース");
		japaneseName.put("persian", "ペルシアン");
		japaneseName.put("psyduck", "コダック");
		japaneseName.put("golduck", "ゴルダック");
		japaneseName.put("mankey", "マンキー");
		japaneseName.put("primeape", "オコリザル");
		japaneseName.put("growlithe", "ガーディ");
		japaneseName.put("arcanine", "ウインディ");
		japaneseName.put("poliwag", "ニョロモ");
		japaneseName.put("poliwhirl", "ニョロゾ");
		japaneseName.put("poliwrath", "ニョロボン");
		japaneseName.put("abra", "ケーシィ");
		japaneseName.put("kadabra", "ユンゲラー");
		japaneseName.put("alakazam", "フーディン");
		japaneseName.put("machop", "ワンリキー");
		japaneseName.put("machoke", "ゴーリキー");
		japaneseName.put("machamp", "カイリキー");
		japaneseName.put("bellsprout", "マダツボミ");
		japaneseName.put("weepinbell", "ウツドン");
		japaneseName.put("victreebel", "ウツボット");
		japaneseName.put("tentacool", "メノクラゲ");
		japaneseName.put("tentacruel", "ドククラゲ");
		japaneseName.put("geodude", "イシツブテ");
		japaneseName.put("graveler", "ゴローン");
		japaneseName.put("golem", "ゴローニャ");
		japaneseName.put("ponyta", "ポニータ");
		japaneseName.put("rapidash", "ギャロップ");
		japaneseName.put("slowpoke", "ヤドン");
		japaneseName.put("slowbro", "ヤドラン");
		japaneseName.put("magnemite", "コイル");
		japaneseName.put("magneton", "レアコイル");
		japaneseName.put("farfetchd", "カモネギ");
		japaneseName.put("doduo", "ドードー");
		japaneseName.put("dodrio", "ドードリオ");
		japaneseName.put("seel", "パウワウ");
		japaneseName.put("dewgong", "ジュゴン");
		japaneseName.put("grimer", "ベトベター");
		japaneseName.put("muk", "ベトベトン");
		japaneseName.put("shellder", "シェルダー");
		japaneseName.put("cloyster", "パルシェン");
		japaneseName.put("gastly", "ゴース");
		japaneseName.put("haunter", "ゴースト");
		japaneseName.put("gengar", "ゲンガー");
		japaneseName.put("onix", "イワーク");
		japaneseName.put("drowzee", "スリープ");
		japaneseName.put("hypno", "スリーパー");
		japaneseName.put("krabby", "クラブ");
		japaneseName.put("kingler", "キングラー");
		japaneseName.put("voltorb", "ビリリダマ");
		japaneseName.put("electrode", "マルマイン");
		japaneseName.put("exeggcute", "タマタマ");
		japaneseName.put("exeggutor", "ナッシー");
		japaneseName.put("cubone", "カラカラ");
		japaneseName.put("marowak", "ガラガラ");
		japaneseName.put("hitmonlee", "サワムラー");
		japaneseName.put("hitmonchan", "エビワラー");
		japaneseName.put("lickitung", "ベロリンガ");
		japaneseName.put("koffing", "ドガース");
		japaneseName.put("weezing", "マタドガス");
		japaneseName.put("rhyhorn", "サイホーン");
		japaneseName.put("rhydon", "サイドン");
		japaneseName.put("chansey", "ラッキー");
		japaneseName.put("tangela", "モンジャラ");
		japaneseName.put("kangaskhan", "ガルーラ");
		japaneseName.put("horsea", "タッツー");
		japaneseName.put("seadra", "シードラ");
		japaneseName.put("goldeen", "トサキント");
		japaneseName.put("seaking", "アズマオウ");
		japaneseName.put("staryu", "ヒトデマン");
		japaneseName.put("starmie", "スターミー");
		japaneseName.put("mr-mime", "バリヤード");
		japaneseName.put("scyther", "ストライク");
		japaneseName.put("jynx", "ルージュラ");
		japaneseName.put("electabuzz", "エレブー");
		japaneseName.put("magmar", "ブーバー");
		japaneseName.put("pinsir", "カイロス");
		japaneseName.put("tauros", "ケンタロス");
		japaneseName.put("magikarp", "コイキング");
		japaneseName.put("gyarados", "ギャラドス");
		japaneseName.put("lapras", "ラプラス");
		japaneseName.put("ditto", "メタモン");
		japaneseName.put("eevee", "イーブイ");
		japaneseName.put("vaporeon", "シャワーズ");
		japaneseName.put("jolteon", "サンダース");
		japaneseName.put("flareon", "ブースター");
		japaneseName.put("porygon", "ポリゴン");
		japaneseName.put("omanyte", "オムナイト");
		japaneseName.put("omastar", "オムスター");
		japaneseName.put("kabuto", "カブト");
		japaneseName.put("kabutops", "カブトプス");
		japaneseName.put("aerodactyl", "プテラ");
		japaneseName.put("snorlax", "カビゴン");
		japaneseName.put("articuno", "フリーザー");
		japaneseName.put("zapdos", "サンダー");
		japaneseName.put("moltres", "ファイヤー");
		japaneseName.put("dratini", "ミニリュウ");
		japaneseName.put("dragonair", "ハクリュー");
		japaneseName.put("dragonite", "カイリュー");
		japaneseName.put("mewtwo", "ミュウツー");
		japaneseName.put("mew", "ミュウ");

		return japaneseName.get(engName);
	}

	public static String[] searchPoke(int pokeCode) {

		String getName = "";
		String[] charactor = new String[2];

		String result = "";

		String url = "https://pokeapi.co/api/v2/pokemon/" + pokeCode;

		try {
			JsonNode getJsonResult = null;
			URL urlString = new URL(url);
			HttpURLConnection con = (HttpURLConnection) urlString.openConnection();
			con.connect();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String tmp = "";

			while ((tmp = br.readLine()) != null) {
				result += tmp;
			}

			ObjectMapper mapper = new ObjectMapper();
			getJsonResult = mapper.readTree(result);

			//=========================日本語の名前で表示するようにする=========================
			String name = getJsonResult.get("species").get("name").asText();
			getName = retName(name);
			url = getJsonResult.get("species").get("url").asText();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("取得失敗");
		}

		result = "";

		try {
			JsonNode getJsonResult = null;

			URL urlString = new URL(url);
			HttpURLConnection con = (HttpURLConnection) urlString.openConnection();
			con.connect();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String tmp = "";

			while ((tmp = br.readLine()) != null) {
				result += tmp;
			}

			ObjectMapper mapper = new ObjectMapper();
			getJsonResult = mapper.readTree(result);

			String text = "";
			int[] nums = { 10, 19, 26, 52, 55, 64, 85, 105, 106, 123, 137, 141, 145, 148 };

			if (pokeCode % nums[0] == 0 || pokeCode % nums[1] == 0 || pokeCode % nums[2] == 0
					|| pokeCode % nums[3] == 0 || pokeCode % nums[4] == 0 || pokeCode % nums[5] == 0
					|| pokeCode % nums[6] == 0
					|| pokeCode % nums[7] == 0 || pokeCode % nums[8] == 0 || pokeCode % nums[9] == 0
					|| pokeCode % nums[10] == 0
					|| pokeCode % nums[11] == 0 || pokeCode % nums[12] == 0 || pokeCode % nums[13] == 0) {
				text = getJsonResult.get("flavor_text_entries").get(30).get("flavor_text").asText();
			}

			else if (pokeCode % 135 == 0 || pokeCode % 132 == 0 || pokeCode % 99 == 0 ||
					pokeCode % 82 == 0 || pokeCode % 66 == 0) {
				text = getJsonResult.get("flavor_text_entries").get(29).get("flavor_text").asText();
			}

			else if (pokeCode % 33 == 0 || pokeCode % 41 == 0 || pokeCode % 79 == 0) {
				text = getJsonResult.get("flavor_text_entries").get(31).get("flavor_text").asText();
			}

			else if (pokeCode % 45 == 0) {
				text = getJsonResult.get("flavor_text_entries").get(1).get("flavor_text").asText();
			}

			else {
				text = getJsonResult.get("flavor_text_entries").get(37).get("flavor_text").asText();
			}

			charactor[0] = getName;
			charactor[1] = text;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("取得失敗");
		}

		return charactor;
	}

	//=============================↓使用例↓================================
	//	public static void main(String[] args) {
	//
	//		Scanner sc = new Scanner(System.in);
	//
	//		System.out.print("1～151の範囲で数字を入力してください => ");
	//		int pokemonNumber = sc.nextInt();
	//		String[] pokeName = new String[2];
	//
	//		while (pokemonNumber > 151) {
	//			System.out.print("範囲外です\n再入力してください => ");
	//			pokemonNumber = sc.nextInt();
	//		}
	//
	//		pokeName = searchPoke(pokemonNumber);
	//		System.out.println("\n" + pokeName[0] + "\n\n" + pokeName[1]);
	//
	//		sc.close();
	//	}

}
