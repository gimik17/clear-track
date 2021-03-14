import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public abstract interface ClearTrackShape {
	public abstract boolean contains(Point2D.Double paramDouble);

	public abstract void paint(Graphics2D paramGraphics2D);

	public abstract String getInfo();

	public abstract void setPosition(Point2D.Double paramDouble);

	public abstract Point2D.Double getPosition();

	public abstract void evaluateRange(ShapeRange paramShapeRange);
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * ClearTrackShape JD-Core Version: 0.6.2
 */