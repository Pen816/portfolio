package object;

//DVDテーブルのレコードオブジェクトクラス
public class DvdObj {

	private String artworkNum; // 作品番号
	private String mediaNum; // 媒体番号
	private String entryDay; // 入荷日
	private String disuseDay; // 削除日

	//	作品番号
	public String getArtworkNumber() {
		return artworkNum;
	}

	public void setArtworkNumber(String artworkNum) {
		this.artworkNum = artworkNum;
	}

	//	媒体番号
	public String getMediaNumber() {
		return mediaNum;
	}

	public void setMediaNumber(String mediaNum) {
		this.mediaNum = mediaNum;
	}

	//	入荷日
	public String getEntryDay() {
		return entryDay;
	}

	public void setEntryDay(String entryDay) {
		this.entryDay = entryDay;
	}

	//	削除日
	public String getDisuseDay() {
		return disuseDay;
	}

	public void setDisuseDay(String disuseDay) {
		this.disuseDay = disuseDay;
	}
}
