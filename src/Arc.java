import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.io.IOException;

class Arc extends DxfSection {
	public void parse(DxfReader paramDxfReader, DxfDocument paramDxfDocument)
			throws IOException {
		Point2D.Double localDouble = paramDxfReader.readPoint(0);
		DxfItem localDxfItem = paramDxfReader.goToCode(40);
		double d1 = java.lang.Double.parseDouble(localDxfItem.value);
		localDxfItem = paramDxfReader.goToCode(50);
		double d2 = java.lang.Double.parseDouble(localDxfItem.value);
		localDxfItem = paramDxfReader.goToCode(51);
		double d3 = java.lang.Double.parseDouble(localDxfItem.value);

		d2 = 360.0D - d2;
		d3 = 360.0D - d3;

		if (d2 < d3)
			this.s = new Arc2D.Double(localDouble.x - d1, localDouble.y - d1,
					2.0D * d1, 2.0D * d1, d3, 360.0D + d2 - d3, 0);
		else
			this.s = new Arc2D.Double(localDouble.x - d1, localDouble.y - d1,
					2.0D * d1, 2.0D * d1, d2, d3 - d2, 0);
		paramDxfDocument.entities.add(this.s);
	}
}
