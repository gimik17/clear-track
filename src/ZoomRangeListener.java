import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

;

class ZoomRangeListener extends Listener {
	Point2D.Double p1;

	public ZoomRangeListener(Controller paramController) {
		super(paramController);
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		Screen localScreen = (Screen) paramMouseEvent.getSource();
		this.p1 = localScreen.transformToModel(paramMouseEvent.getX(),
				paramMouseEvent.getY());
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		Screen localScreen = (Screen) paramMouseEvent.getSource();
		localScreen.pickZoomRange();
		localScreen.zoomBox = null;
		localScreen.repaint();
		this.controller.listenerDone();
	}

	public void mouseDragged(MouseEvent paramMouseEvent) {
		Screen localScreen = (Screen) paramMouseEvent.getSource();

		Point2D.Double localDouble = Utils.getDistance(this.p1, localScreen
				.transformToModel(paramMouseEvent.getX(),
						paramMouseEvent.getY()));

		localScreen.zoomBox = new Rectangle2D.Double(this.p1.x, this.p1.y,
				Math.abs(localDouble.x), Math.abs(localDouble.y));
		if (0.0D > localDouble.x)
			localScreen.zoomBox.x += localDouble.x;
		if (0.0D > localDouble.y) {
			localScreen.zoomBox.y += localDouble.y;
		}
		localScreen.repaint();
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * ZoomRangeListener JD-Core Version: 0.6.2
 */