import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

class ClearTrackAction extends AbstractAction {
	ActionViewBinder binder;

	ClearTrackAction(String paramString, ImageIcon paramImageIcon,
			ActionViewBinder paramActionViewBinder) {
		super(paramString, paramImageIcon);
		this.binder = paramActionViewBinder;
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		if ((null != this.binder) && (null != this.binder.controller)) {
			this.binder.controller.processAction(this.binder);
		}
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * ClearTrackAction JD-Core Version: 0.6.2
 */