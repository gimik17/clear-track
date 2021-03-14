public abstract class Controller implements ActionController {
	private static ClearTrackShape selectedShape = null;

	private static Vehicle actualVehicle = null;

	private static Track actualTrack = null;

	private static DragShape actualDragShape = null;

	static ToolController toolController = null;
	Screen screen;

	Controller(Screen paramScreen) {
		this.screen = paramScreen;
	}

	static void setShape(ClearTrackShape paramClearTrackShape) {
		selectedShape = paramClearTrackShape;
		actualTrack = null;
		actualVehicle = null;
		actualDragShape = null;
		try {
			actualVehicle = (Vehicle) paramClearTrackShape;
		} catch (Exception localException1) {
		}
		try {
			actualTrack = (Track) paramClearTrackShape;
		} catch (Exception localException2) {
		}
		try {
			actualDragShape = (DragShape) paramClearTrackShape;
		} catch (Exception localException3) {
		}
		MenuFactory.pointInserter.setEnabled(null != actualTrack);
		MenuFactory.saveSelected.setEnabled((null != actualTrack)
				|| (null != actualDragShape));
		MenuFactory.removeItem.setEnabled(null != selectedShape);
		MenuFactory.driveAlongTrack.setEnabled(null != actualTrack);
		MenuFactory.showTurnDiagram.setEnabled(null != actualDragShape || null != actualVehicle);
		MenuFactory.track.setEnabled(null != actualVehicle);
		MenuFactory.orientation.setEnabled(null != actualVehicle);
		MenuFactory.position.setEnabled(null != selectedShape);
	}

	static ClearTrackShape getShape() {
		return selectedShape;
	}

	static Vehicle getVehicle() {
		return actualVehicle;
	}

	static Track getTrack() {
		return actualTrack;
	}

	static DragShape getDragShape() {
		return actualDragShape;
	}

	abstract void listenerDone();

}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * Controller JD-Core Version: 0.6.2
 */