import java.awt.Graphics2D;
import java.awt.geom.Point2D;

class ClearTrackShapeAdapter implements ClearTrackShape {
	public boolean contains(Point2D.Double paramDouble) {
		return false;
	}

	public void paint(Graphics2D paramGraphics2D) {
	}

	public String getInfo() {
		return "";
	}

	public void setPosition(Point2D.Double paramDouble) {
	}

	public Point2D.Double getPosition() {
		return null;
	}

	public void evaluateRange(ShapeRange paramShapeRange) {
	}

}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * ClearTrackShapeAdapter JD-Core Version: 0.6.2
 */