import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

class PositionListener extends Listener {
	ClearTrackShape shapeToPosition;
	Point2D.Double offset;

	public PositionListener(Controller paramController) {
		super(paramController);
		this.shapeToPosition = null;
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		Screen localScreen = (Screen) paramMouseEvent.getSource();
		Controller.setShape(localScreen.selectItem(localScreen
				.transformToModel(paramMouseEvent.getX(),
						paramMouseEvent.getY())));
		this.shapeToPosition = Controller.getShape();
		if (null != this.shapeToPosition) {
			Point2D.Double localDouble = this.shapeToPosition.getPosition();
			if (null != localDouble)
				this.offset = Utils.getDistance(localScreen.transformToModel(
						paramMouseEvent.getX(), paramMouseEvent.getY()),
						localDouble);
			else
				this.offset = new Point2D.Double(0.0D, 0.0D);
		}
		localScreen.repaint();
	}

	public void mouseDragged(MouseEvent paramMouseEvent) {
		if (this.shapeToPosition != null) {
			Screen localScreen = (Screen) paramMouseEvent.getSource();
			Point2D.Double localDouble = localScreen.transformToModel(
					paramMouseEvent.getX(), paramMouseEvent.getY());
			localDouble.x += this.offset.x;
			localDouble.y += this.offset.y;
			this.shapeToPosition.setPosition(localDouble);
			localScreen.repaint();
		}
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * PositionListener JD-Core Version: 0.6.2
 */