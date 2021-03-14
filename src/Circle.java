import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.IOException;

class Circle extends DxfSection {
	public void parse(DxfReader paramDxfReader, DxfDocument paramDxfDocument)
			throws IOException {
		Point2D.Double localDouble = paramDxfReader.readPoint(0);

		DxfItem localDxfItem = paramDxfReader.goToCode(40);
		double d = Double.parseDouble(localDxfItem.value);

		this.s = new Ellipse2D.Double(localDouble.x - d, localDouble.y - d,
				2.0D * d, 2.0D * d);
		paramDxfDocument.entities.add(this.s);
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * Circle JD-Core Version: 0.6.2
 */