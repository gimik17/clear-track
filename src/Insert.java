import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Vector;

class Insert extends DxfSection {
	public void parse(DxfReader paramDxfReader, DxfDocument paramDxfDocument)
			throws IOException {
		DxfItem localDxfItem = paramDxfReader.goToCode(2);
		String str = localDxfItem.value;
		Vector localVector = (Vector) paramDxfDocument.blocks.get(str);

		Point2D.Double localDouble = paramDxfReader.readPoint(0);
		AffineTransform localAffineTransform = new AffineTransform();
		localAffineTransform.translate(localDouble.x, localDouble.y);
		for (int i = 0; i < localVector.size(); i++) {
			Shape localShape = (Shape) localVector.get(i);
			paramDxfDocument.entities.add(localAffineTransform
					.createTransformedShape(localShape));
		}
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * Insert JD-Core Version: 0.6.2
 */