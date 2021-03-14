import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;

class ShapeRange {
	Point2D.Double low;
	Point2D.Double high;
	static int border = 1000;
	private boolean isNew;
	boolean hasChanged;

	ShapeRange() {
		this.isNew = true;
		this.low = new Point2D.Double(-10000.0D, -10000.0D);
		this.high = new Point2D.Double(10000.0D, 10000.0D);
		this.hasChanged = true;
	}

	ShapeRange(Point2D.Double paramDouble1, Point2D.Double paramDouble2) {
		this.low = paramDouble1;
		this.high = paramDouble2;
		this.hasChanged = true;
	}

	void evaluateRange(Shape paramShape) {
		if (null == paramShape)
			return;
		Rectangle localRectangle = paramShape.getBounds();

		if (this.isNew) {
			this.low.x = (localRectangle.getMinX() - border);
			this.low.y = (localRectangle.getMinY() - border);
			this.high.x = (localRectangle.getMaxX() + border);
			this.high.y = (localRectangle.getMaxY() + border);
			this.isNew = false;
		} else {
			if (this.low.x > localRectangle.getMinX()) {
				this.low.x = (localRectangle.getMinX() - border);
				this.hasChanged = true;
			}
			if (this.low.y > localRectangle.getMinY()) {
				this.low.y = (localRectangle.getMinY() - border);
				this.hasChanged = true;
			}
			if (this.high.x < localRectangle.getMaxX()) {
				this.high.x = (localRectangle.getMaxX() + border);
				this.hasChanged = true;
			}
			if (this.high.y < localRectangle.getMaxY()) {
				this.high.y = (localRectangle.getMaxY() + border);
				this.hasChanged = true;
			}
		}
	}

	void translate(Point2D.Double paramDouble) {
		this.low.x += paramDouble.x;
		this.high.x += paramDouble.x;

		this.low.y += paramDouble.y;
		this.high.y += paramDouble.y;
		this.hasChanged = true;
	}

	Point2D.Double getCenter() {
		Point2D.Double localDouble1 = Utils.getDistance(this.low, this.high);
		Point2D.Double localDouble2 = (Point2D.Double) this.low.clone();
		localDouble2.x += localDouble1.x / 2.0D;
		localDouble2.y += localDouble1.y / 2.0D;
		return localDouble2;
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * ShapeRange JD-Core Version: 0.6.2
 */