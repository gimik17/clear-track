import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.IOException;

class Line extends DxfSection {
	public void parse(DxfReader paramDxfReader, DxfDocument paramDxfDocument)
			throws IOException {
		Point2D.Double localDouble1 = paramDxfReader.readPoint(0);
		Point2D.Double localDouble2 = paramDxfReader.readPoint(1);
		this.s = new Line2D.Double(localDouble1, localDouble2);
		paramDxfDocument.entities.add(this.s);
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * Line JD-Core Version: 0.6.2
 */