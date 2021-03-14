import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class Bounds extends ClearTrackShapeAdapter {
	private GeneralPath polygon;
	boolean bNew;
	AffineTransform transformation;

	Bounds() {
		this.polygon = new GeneralPath();

		this.transformation = null;
		this.bNew = true;
	}

	public String toString() {
		return this.polygon.toString();
	}

	public boolean contains(Point2D.Double paramDouble) {
		return this.polygon.createTransformedShape(this.transformation)
				.contains(paramDouble);
	}

	public void paint(Graphics2D paramGraphics2D) {
		AffineTransform localAffineTransform = paramGraphics2D.getTransform();
		if (null != this.transformation)
			paramGraphics2D.transform(this.transformation);
		paramGraphics2D.draw(this.polygon);

		paramGraphics2D.setTransform(localAffineTransform);
	}

	Shape getTransformedShape() {
		return this.polygon.createTransformedShape(this.transformation);
	}

	void addPoint(Point2D.Double paramDouble) {
		if (this.bNew) {
			this.polygon.moveTo((float) paramDouble.x, (float) paramDouble.y);
			this.bNew = false;
		} else {
			this.polygon.lineTo((float) paramDouble.x, (float) paramDouble.y);
		}
	}

	void closePolygon() {
		this.polygon.closePath();
	}

}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * Bounds JD-Core Version: 0.6.2
 */