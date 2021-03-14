import java.awt.geom.Point2D;

import org.xml.sax.Attributes;

class BoundsLevelHandler extends XmlLevelHandler implements XmlLevelListener {
	Bounds bounds;
	boolean inSize;
	int actualPoint;
	int pointCount;

	public BoundsLevelHandler(XmlLevelListener paramXmlLevelListener) {
		super(paramXmlLevelListener);
		this.inSize = false;
		this.levelHandler = null;
		this.actualPoint = 0;
		this.pointCount = 0;
		this.bounds = new Bounds();
	}

	public void levelDone() {
		Point2D.Double localDouble = (Point2D.Double) this.levelHandler
				.getItem();

		this.bounds.addPoint(localDouble);
		if (this.actualPoint >= this.pointCount) {
			this.bounds.closePolygon();
			this.levelListener.levelDone();
		}
		this.levelHandler = null;
		this.actualPoint += 1;
	}

	public Object getItem() {
		return this.bounds;
	}

	public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2) {
		if (null != this.levelHandler) {
			this.levelHandler
					.characters(paramArrayOfChar, paramInt1, paramInt2);
		} else if (this.inSize) {
			this.pointCount = (Integer.parseInt(new String(paramArrayOfChar,
					paramInt1, paramInt2)) - 1);
			this.inSize = false;
		}
	}

	public void startElement(String paramString1, String paramString2,
			String paramString3, Attributes paramAttributes) {
		if (null != this.levelHandler) {
			this.levelHandler.startElement(paramString1, paramString2,
					paramString3, paramAttributes);
		} else if (paramString3.equals("size")) {
			this.inSize = true;
		} else if (paramString3.equals("position"))
			this.levelHandler = new PositionLevelHandler(this);
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * BoundsLevelHandler JD-Core Version: 0.6.2
 */