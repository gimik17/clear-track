import java.awt.geom.Point2D;
import java.io.File;

import org.xml.sax.Attributes;

class TrailerLevelHandler extends XmlLevelHandler implements XmlLevelListener {
	File file;
	Point2D.Double position;
	VehicleReader vehicleReader;
	boolean inFile;

	public TrailerLevelHandler(XmlLevelListener paramXmlLevelListener,
			File paramFile) {
		super(paramXmlLevelListener);
		this.inFile = false;
		this.levelHandler = null;
		this.position = null;
		this.vehicleReader = null;
		this.file = paramFile;
	}

	public void levelDone() {
		this.position = ((Point2D.Double) this.levelHandler.getItem());
		this.levelListener.levelDone();
		this.levelHandler = null;
	}

	public Object getItem() {
		return this.position;
	}

	public Vehicle getVehicle() {
		return this.vehicleReader.getVehicle();
	}

	public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2) {
		if (null != this.levelHandler) {
			this.levelHandler
					.characters(paramArrayOfChar, paramInt1, paramInt2);
		} else if (this.inFile) {
			this.vehicleReader = new VehicleReader();
			File localFile = new File(this.file, new String(paramArrayOfChar,
					paramInt1, paramInt2));

			this.vehicleReader.load(localFile);
			this.inFile = false;
		}
	}

	public void startElement(String paramString1, String paramString2,
			String paramString3, Attributes paramAttributes) {
		if (null != this.levelHandler) {
			this.levelHandler.startElement(paramString1, paramString2,
					paramString3, paramAttributes);
		} else if (paramString3.equals("file")) {
			this.inFile = true;
		} else if (paramString3.equals("position"))
			this.levelHandler = new PositionLevelHandler(this);
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * TrailerLevelHandler JD-Core Version: 0.6.2
 */