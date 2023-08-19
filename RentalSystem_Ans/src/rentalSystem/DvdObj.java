package rentalSystem;

/**
 * DVDテーブルのレコード用オブジェクトクラス
 */
public class DvdObj {

	private String artworkNumber; // 作品番号
	private String mediaNumber; // 媒体番号
	private String entryDay; // 入荷日
	private String disuseDay; // 削除日

	/**
	 * @return artworkNumber
	 */
	public String getArtworkNumber() {
		return artworkNumber;
	}

	/**
	 * awNumber を設定します。
	 *
	 * @param awNumber
	 */
	public void setArtworkNumber(String artworkNumber) {
		this.artworkNumber = artworkNumber;
	}

	/**
	 * @return mediaNumber
	 */
	public String getMediaNumber() {
		return mediaNumber;
	}

	/**
	 * mediaNumber を設定します。
	 *
	 * @param mediaNumber
	 */
	public void setMediaNumber(String mediaNumber) {
		this.mediaNumber = mediaNumber;
	}

	/**
	 * @return entryDay
	 */
	public String getEntryDay() {
		return entryDay;
	}

	/**
	 * insertDay を設定します。
	 *
	 * @param insertDay
	 */
	public void setEntryDay(String entryDay) {
		this.entryDay = entryDay;
	}

	/**
	 * @return disuseDay
	 */
	public String getDisuseDay() {
		return disuseDay;
	}

	/**
	 * deleteDay を設定します。
	 *
	 * @param deleteDay
	 */
	public void setDisuseDay(String disuseDay) {
		this.disuseDay = disuseDay;
	}
}
