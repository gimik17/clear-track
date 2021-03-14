import java.awt.geom.Point2D;
import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class VehicleReader extends DefaultHandler implements XmlLevelListener {
	Vehicle vehicle;
	XmlLevelHandler levelHandler;
	boolean inTracker;
	boolean inBounds;
	boolean inTrailer;
	boolean inName;
	boolean inTurnAngle;
	File file;

	public VehicleReader() {
		this.vehicle = new Vehicle();
		this.levelHandler = null;
		this.inTracker = false;
		this.inBounds = false;
		this.inTrailer = false;
		this.inName = false;
		this.inTurnAngle = false;
	}

	public void levelDone() {
		if (this.inTracker) {
			this.vehicle.tracker = ((Tracker) this.levelHandler.getItem());
			this.inTracker = false;
		} else if (this.inBounds) {
			this.vehicle.bounds = ((Bounds) this.levelHandler.getItem());
			this.inBounds = false;
		}
		if (this.inTrailer) {
			this.vehicle.trailerPosition = ((Point2D.Double) this.levelHandler
					.getItem());
			TrailerLevelHandler localTrailerLevelHandler = (TrailerLevelHandler) this.levelHandler;
			this.vehicle.setTrailer(localTrailerLevelHandler.getVehicle());
			this.inTrailer = false;
		}

		this.levelHandler = null;
	}

	public Vehicle getVehicle() {
		return this.vehicle;
	}

	public void startElement(String paramString1, String paramString2,
			String paramString3, Attributes paramAttributes) {
		if (null != this.levelHandler) {
			this.levelHandler.startElement(paramString1, paramString2,
					paramString3, paramAttributes);
		} else if (paramString3.equals("tracker")) {
			this.levelHandler = new TrackerLevelHandler(this);
			this.inTracker = true;
		} else if (paramString3.equals("bounds")) {
			this.levelHandler = new BoundsLevelHandler(this);
			this.inBounds = true;
		} else if (paramString3.equals("trailer")) {
			this.levelHandler = new TrailerLevelHandler(this,
					this.file.getParentFile());
			this.inTrailer = true;
		} else if (paramString3.equals("name")) {
			this.inName = true;
		} else if (paramString3.equals("turnangle")) {
			this.inTurnAngle = true;
		}
	}

	public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2) {
		if (null != this.levelHandler) {
			this.levelHandler
					.characters(paramArrayOfChar, paramInt1, paramInt2);
		} else if (this.inName) {
			this.vehicle.name = new String(paramArrayOfChar, paramInt1,
					paramInt2);
			this.inName = false;
		} else if (this.inTurnAngle) {
			this.vehicle.maxTurn = Math.toRadians(Double
					.parseDouble(new String(paramArrayOfChar, paramInt1,
							paramInt2)));
			this.inTurnAngle = false;
		}
	}

	public void load(File paramFile) {
		this.file = paramFile;
		this.vehicle.setFile(paramFile);
		SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
		localSAXParserFactory.setValidating(true);
		SAXParser localSAXParser = null;
		try {
			localSAXParser = localSAXParserFactory.newSAXParser();
		} catch (Exception localException1) {
			Utils.showError(localException1, null);
			System.out.println(localException1);
			Utils.showError(localException1, null);
			return;
		}
		try {
			localSAXParser.parse(this.file, this);
		} catch (Exception localException2) {
			System.out.println(localException2);
			Utils.showError(localException2, null);
			return;
		}
	}

	public static void main(String[] paramArrayOfString) {
		VehicleReader localVehicleReader = new VehicleReader();
		localVehicleReader
				.load(new File("vehicles/Lastwagen_mit_Anhaenger.xml"));
		System.out.println(localVehicleReader.getVehicle());
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * VehicleReader JD-Core Version: 0.6.2
 */