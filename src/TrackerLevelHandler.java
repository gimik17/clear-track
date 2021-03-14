import java.awt.geom.Point2D;

import org.xml.sax.Attributes;

class TrackerLevelHandler extends XmlLevelHandler implements XmlLevelListener {
	Tracker tracker;
	boolean inLead;
	boolean inTrail;

	public TrackerLevelHandler(XmlLevelListener paramXmlLevelListener) {
		super(paramXmlLevelListener);
		this.inLead = false;
		this.inTrail = false;
		this.tracker = new Tracker();
		this.levelHandler = null;
	}

	public void levelDone() {
		if (this.inLead) {
			this.tracker.setLead((Point2D.Double) this.levelHandler.getItem());
			this.inLead = false;
		} else if (this.inTrail) {
			this.tracker.setTrail((Point2D.Double) this.levelHandler.getItem());
			this.inTrail = false;
			this.levelListener.levelDone();
		}
		this.levelHandler = null;
	}

	public Object getItem() {
		return this.tracker;
	}

	public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2) {
		if (null != this.levelHandler)
			this.levelHandler
					.characters(paramArrayOfChar, paramInt1, paramInt2);
	}

	public void startElement(String paramString1, String paramString2,
			String paramString3, Attributes paramAttributes) {
		if (null != this.levelHandler) {
			this.levelHandler.startElement(paramString1, paramString2,
					paramString3, paramAttributes);
		} else if (paramString3.equals("lead")) {
			this.inLead = true;
		} else if (paramString3.equals("trail")) {
			this.inTrail = true;
		} else if (paramString3.equals("position"))
			this.levelHandler = new PositionLevelHandler(this);
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * TrackerLevelHandler JD-Core Version: 0.6.2
 */