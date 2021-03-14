import java.awt.geom.Point2D;

import org.xml.sax.Attributes;

class PositionLevelHandler extends XmlLevelHandler {
	double x;
	double y;
	boolean inX;
	boolean inY;

	public PositionLevelHandler(XmlLevelListener paramXmlLevelListener) {
		super(paramXmlLevelListener);
		this.inX = false;
		this.inY = false;
		this.y = 0.0D;
		this.x = 0.0D;
	}

	public Object getItem() {
		return new Point2D.Double(this.x, this.y);
	}

	public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2) {
		if (this.inX) {
			this.x = Double.parseDouble(new String(paramArrayOfChar, paramInt1,
					paramInt2));
			this.inX = false;
		} else if (this.inY) {
			this.y = Double.parseDouble(new String(paramArrayOfChar, paramInt1,
					paramInt2));
			this.inY = false;
			this.levelListener.levelDone();
		}
	}

	public void startElement(String paramString1, String paramString2,
			String paramString3, Attributes paramAttributes) {
		if (paramString3.equals("x"))
			this.inX = true;
		else if (paramString3.equals("y"))
			this.inY = true;
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * PositionLevelHandler JD-Core Version: 0.6.2
 */