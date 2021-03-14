import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

class OrientationListener extends Listener {
	Vehicle vehicleToOrientate;
	Point2D.Double p1;

	public OrientationListener(Controller paramController) {
		super(paramController);
		this.vehicleToOrientate = null;
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		Screen localScreen = (Screen) paramMouseEvent.getSource();
		Controller.setShape(localScreen.selectItem(localScreen
				.transformToModel(paramMouseEvent.getX(),
						paramMouseEvent.getY())));
		this.vehicleToOrientate = Controller.getVehicle();
		if (null != this.vehicleToOrientate) {
			this.vehicleToOrientate.setDragShape(null);
			this.p1 = localScreen.transformToModel(paramMouseEvent.getX(),
					paramMouseEvent.getY());
		}
		localScreen.repaint();
	}

	public void mouseDragged(MouseEvent paramMouseEvent) {
		if (null != this.vehicleToOrientate) {
			Screen localScreen = (Screen) paramMouseEvent.getSource();
			Point2D.Double localDouble = localScreen.transformToModel(
					paramMouseEvent.getX(), paramMouseEvent.getY());
			this.vehicleToOrientate.orientate(Utils.getDistance(this.p1,localDouble),Settings.calMethod);
			this.p1 = localDouble;
			localScreen.repaint();
		}
	}
}
