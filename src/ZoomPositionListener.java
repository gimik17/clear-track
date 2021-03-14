import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

class ZoomPositionListener extends Listener {
	Point2D.Double p1;

	public ZoomPositionListener(Controller paramController) {
		super(paramController);
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		Screen localScreen = (Screen) paramMouseEvent.getSource();
		this.p1 = localScreen.transformToModel(paramMouseEvent.getX(),
				paramMouseEvent.getY());
	}

	public void mouseDragged(MouseEvent paramMouseEvent) {
		Screen localScreen = (Screen) paramMouseEvent.getSource();
		Point2D.Double localDouble = localScreen.transformToModel(
				paramMouseEvent.getX(), paramMouseEvent.getY());
		if (null != this.p1)
			localScreen.translateZoomRange(Utils.getDistance(localDouble,
					this.p1));
		localScreen.repaint();
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * ZoomPositionListener JD-Core Version: 0.6.2
 */