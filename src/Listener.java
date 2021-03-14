import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Listener implements MouseListener, MouseMotionListener {
	protected Controller controller;

	public Listener(Controller paramController) {
		this.controller = paramController;
	}

	public Listener() {
		this.controller = null;
	}

	public void mousePressed(MouseEvent paramMouseEvent) {
	}

	public void mouseReleased(MouseEvent paramMouseEvent) {
		this.controller.listenerDone();
	}

	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	public void mouseClicked(MouseEvent paramMouseEvent) {
	}

	public void mouseDragged(MouseEvent paramMouseEvent) {
	}

	public void mouseMoved(MouseEvent paramMouseEvent) {
	}

}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * Listener JD-Core Version: 0.6.2
 */