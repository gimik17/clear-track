import java.awt.Cursor;

import javax.swing.AbstractButton;
import javax.swing.Action;

class ActionViewBinder {
	Action action;
	ActionController controller;
	AbstractButton button;
	AbstractButton menuItem;
	Listener listener;
	Cursor cursor;

	ActionViewBinder(ActionController paramActionController,
			Listener paramListener) {
		this.controller = paramActionController;
		this.listener = paramListener;
		this.cursor = Cursor.getDefaultCursor();
		this.action = null;
		this.button = null;
		this.menuItem = null;
	}

	ActionViewBinder() {
		this.controller = null;
		this.listener = null;
		this.action = null;
		this.button = null;
		this.menuItem = null;
		this.cursor = Cursor.getDefaultCursor();
	}

	void setSelected(boolean paramBoolean) {
		if (null != this.button)
			this.button.setSelected(paramBoolean);
		if (null != this.menuItem)
			this.menuItem.setSelected(paramBoolean);
	}

	void setEnabled(boolean paramBoolean) {
		if (null != this.button)
			this.button.setEnabled(paramBoolean);
		this.action.setEnabled(paramBoolean);
	}

	Cursor getCursor() {
		return this.cursor;
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * ActionViewBinder JD-Core Version: 0.6.2
 */