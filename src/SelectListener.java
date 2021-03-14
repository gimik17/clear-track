import java.awt.event.MouseEvent;

class SelectListener extends Listener {
	public SelectListener(Controller paramController) {
		super(paramController);
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
		Screen localScreen = (Screen) paramMouseEvent.getSource();
		Controller.setShape(localScreen.selectItem(localScreen
				.transformToModel(paramMouseEvent.getX(),
						paramMouseEvent.getY())));
		localScreen.repaint();
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * SelectListener JD-Core Version: 0.6.2
 */