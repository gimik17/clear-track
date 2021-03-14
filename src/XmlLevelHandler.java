import org.xml.sax.Attributes;

abstract class XmlLevelHandler {
	protected XmlLevelListener levelListener;
	protected XmlLevelHandler levelHandler;

	public XmlLevelHandler(XmlLevelListener paramXmlLevelListener) {
		this.levelListener = paramXmlLevelListener;
	}

	public abstract Object getItem();

	public abstract void startElement(String paramString1, String paramString2,
			String paramString3, Attributes paramAttributes);

	public abstract void characters(char[] paramArrayOfChar, int paramInt1,
			int paramInt2);
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * XmlLevelHandler JD-Core Version: 0.6.2
 */