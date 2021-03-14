import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

class PointInserter extends Listener {
	public PointInserter(Controller paramController) {
		super(paramController);
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		Screen localScreen = (Screen) paramMouseEvent.getSource();
		Point2D.Double localDouble = localScreen.transformToModel(
				paramMouseEvent.getX(), paramMouseEvent.getY());
		if (null != Controller.getTrack())
			Controller.getTrack().insertPoint(localDouble);
		this.controller.listenerDone();
		localScreen.repaint();
	}

}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * PointInserter JD-Core Version: 0.6.2
 */