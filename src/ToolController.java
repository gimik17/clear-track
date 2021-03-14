import java.awt.Cursor;

class ToolController extends Controller {
	ActionViewBinder actualTool;

	public ToolController(Screen paramScreen) {
		super(paramScreen);
		if (null != Controller.toolController)
			System.out.println("Call me only once!!!! (ToolController())");
		this.screen = paramScreen;
		this.actualTool = null;
		Controller.toolController = this;
	}

	public void activateTool(ActionViewBinder paramActionViewBinder) {
		if (paramActionViewBinder == MenuFactory.viewAll) {
			this.screen.viewAll();
			this.screen.repaint();
			return;
		}

		if (null != this.actualTool) {
			this.actualTool.setSelected(false);
		}

		if (null != this.actualTool) {
			this.screen.removeMouseListener(this.actualTool.listener);
			this.screen.removeMouseMotionListener(this.actualTool.listener);
		}
		this.actualTool = paramActionViewBinder;
		if (null != this.actualTool) {
			this.actualTool.setSelected(true);

			this.screen.addMouseListener(this.actualTool.listener);
			this.screen.addMouseMotionListener(this.actualTool.listener);

			this.screen.setCursor(this.actualTool.getCursor());
		} else {
			this.screen.setCursor(Cursor.getDefaultCursor());
		}
	}

	public void processAction(ActionViewBinder paramActionViewBinder) {
		activateTool(paramActionViewBinder);
	}

	public void listenerDone() {
		if (this.actualTool == MenuFactory.trackCreator) {
			this.screen.addTrack(new SplineTrack(
					((TrackCreator) this.actualTool.listener).track));

			setShape(this.screen.selectItem(null));
			Controller.toolController.activateTool(MenuFactory.position);
		} else if (this.actualTool != MenuFactory.zoomRange)
			;
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * ToolController JD-Core Version: 0.6.2
 */