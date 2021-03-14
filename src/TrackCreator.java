import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

class TrackCreator extends Listener {
	GeneralPath track;

	public TrackCreator(Controller paramController) {
		super(paramController);
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		Screen localScreen = (Screen) paramMouseEvent.getSource();
		this.track = new GeneralPath();
		Point2D.Double localDouble = localScreen.transformToModel(
				paramMouseEvent.getX(), paramMouseEvent.getY());
		this.track.moveTo((float) localDouble.getX(),
				(float) localDouble.getY());
		localScreen.track = this.track;
		localScreen.repaint();
	}

	public void mouseDragged(MouseEvent paramMouseEvent) {
		Screen localScreen = (Screen) paramMouseEvent.getSource();
		Point2D.Double localDouble = localScreen.transformToModel(
				paramMouseEvent.getX(), paramMouseEvent.getY());
		this.track.lineTo((float) localDouble.getX(),
				(float) localDouble.getY());
		localScreen.repaint();
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		Screen localScreen = (Screen) paramMouseEvent.getSource();
		localScreen.track = null;
		this.controller.listenerDone();
		localScreen.repaint();
	}

}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * TrackCreator JD-Core Version: 0.6.2
 */