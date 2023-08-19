package rentalSystem;

/**
 * 作品テーブルのレコードオブジェクトクラス
 */
public class ArtworkObj {

	private String artworkNumber = null; // 作品番号
	private String artworkName = null; // 作品名
	private String recentDiv = null; // 区分
	private String genreName = null; // ジャンル名

	/**
	 * @return artworkNumber
	 */
	public String getArtworkNumber() {
		return artworkNumber;
	}

	/**
	 * artworkNumber を設定します。
	 *
	 * @param artworkNumber
	 */
	public void setArtworkNumber(String artworkNumber) {
		this.artworkNumber = artworkNumber;
	}

	/**
	 * @return artworkName
	 */
	public String getArtworkName() {
		return artworkName;
	}

	/**
	 * artworkName を設定します。
	 *
	 * @param artworkName
	 */
	public void setArtworkName(String artworkName) {
		this.artworkName = artworkName;
	}

	/**
	 * @return recentDiv
	 */
	public String getRecentDiv() {
		return recentDiv;
	}

	/**
	 * recentDiv を設定します。
	 *
	 * @param recentDiv
	 */
	public void setRecentDiv(String recentDiv) {
		this.recentDiv = recentDiv;
	}

	/**
	 * @return artworkGenumber
	 */
	public String getGenreName() {
		return genreName;
	}

	/**
	 * artworkGenumber を設定します。
	 *
	 * @param artworkGenumber
	 */
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
}
