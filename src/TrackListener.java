import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

class TrackListener extends Listener {
	Vehicle vehicleToTrack;
	Point2D.Double p1;

	public TrackListener(Controller paramController) {
		super(paramController);
		this.vehicleToTrack = null;
		this.p1 = null;
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		Screen localScreen = (Screen) paramMouseEvent.getSource();
		Controller.setShape(localScreen.selectItem(localScreen
				.transformToModel(paramMouseEvent.getX(),
						paramMouseEvent.getY())));
		this.vehicleToTrack = Controller.getVehicle();
		if (null != this.vehicleToTrack) {
			this.p1 = localScreen.transformToModel(paramMouseEvent.getX(),
					paramMouseEvent.getY());
			DragShape localDragShape = new DragShape(this.vehicleToTrack);
			this.vehicleToTrack.setDragShape(localDragShape);
			localScreen.addDragShape(localDragShape);

			this.vehicleToTrack.drawDragShape();
		}
		localScreen.repaint();
	}

	public void mouseDragged(MouseEvent paramMouseEvent) {
		if (null != this.vehicleToTrack) {
			Screen localScreen = (Screen) paramMouseEvent.getSource();
			Point2D.Double localDouble = localScreen.transformToModel(
					paramMouseEvent.getX(), paramMouseEvent.getY());
			this.vehicleToTrack.track(Utils.getDistance(this.p1, localDouble),Settings.calMethod);
			this.p1 = localDouble;
			localScreen.repaint();
		}
	}
}