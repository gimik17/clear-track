class DxfItem {
	public int code;
	public String value;

	public DxfItem() {
		this.code = 0;
		this.value = "";
	}

	public DxfItem(int paramInt, String paramString) {
		this.code = paramInt;
		this.value = paramString;
	}

	public String toString() {
		return this.code + ": " + this.value;
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * DxfItem JD-Core Version: 0.6.2
 */