import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Vector;

class DxfDocument {
	HashMap header = new HashMap();

	HashMap blocks = new HashMap();

	HashMap layers = new HashMap();

	Vector entities = new Vector();

	public String toString() {
		String str = "  Header:\n" + this.header;
		str = str + "\n  Blocks:\n" + this.blocks;
		str = str + "\n  Entities:\n" + this.entities;
		return str;
	}

	int angleDirection() {
		Vector localVector = (Vector) this.header.get("$ANGDIR");
		DxfItem localDxfItem = (DxfItem) localVector.get(0);
		if (0 < Integer.parseInt(localDxfItem.value)) {
			return 1;
		}
		return -1;
	}

	double angleBase() {
		Vector localVector = (Vector) this.header.get("$ANGBASE");
		DxfItem localDxfItem = (DxfItem) localVector.get(0);
		return Double.parseDouble(localDxfItem.value);
	}

	ShapeRange getRange() {
		Vector localVector = (Vector) this.header.get("$EXTMIN");
		Point2D.Double localDouble1 = new Point2D.Double();
		localDouble1.x = Double
				.parseDouble(((DxfItem) localVector.get(0)).value);
		localDouble1.y = Double
				.parseDouble(((DxfItem) localVector.get(1)).value);

		localVector = (Vector) this.header.get("$EXTMAX");
		Point2D.Double localDouble2 = new Point2D.Double();
		localDouble2.x = Double
				.parseDouble(((DxfItem) localVector.get(0)).value);
		localDouble2.y = Double
				.parseDouble(((DxfItem) localVector.get(1)).value);

		return new ShapeRange(localDouble1, localDouble2);
	}

	double getUnits() {
		return 1000.0D;
	}

	String getVersion() {
		Vector localVector = (Vector) this.header.get("$ACADVER");
		return ((DxfItem) localVector.get(0)).value;
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * DxfDocument JD-Core Version: 0.6.2
 */