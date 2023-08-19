package object;

//	作品テーブルのレコードオブジェクトクラス
public class ArtworkObj {

	private String artworkNum = null; // 作品番号
	private String artworkName = null; // 作品名
	private String category = null; // 区分
	private String genreName = null; // ジャンル名

	//	作品番号
	public String getArtworkNum() {
		return artworkNum;
	}

	public void setArtworkNum(String artworkNum) {
		this.artworkNum = artworkNum;
	}

	//	作品名
	public String getArtworkName() {
		return artworkName;
	}

	public void setArtworkName(String artworkName) {
		this.artworkName = artworkName;
	}

	//	区分
	public String getRecentDiv() {
		return category;
	}

	public void setRecentDiv(String category) {
		this.category = category;
	}

	//	ジャンル
	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
}
