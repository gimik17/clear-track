import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class SelectObjectDialog extends JDialog {
	private static SelectObjectDialog dialog = null;

	private static Object value = null;
	private HashMap map;
	private JList list;

	public static SelectObjectDialog initialize(Component paramComponent,
			HashMap paramHashMap, String paramString1, String paramString2) {
		Frame localFrame = JOptionPane.getFrameForComponent(paramComponent);
		dialog = new SelectObjectDialog(localFrame, paramHashMap, paramString1,
				paramString2);
		return dialog;
	}

	public Object showDialog(Component paramComponent, String paramString) {
		value = null;
		this.list.setSelectedValue(paramString, true);
		dialog.setLocationRelativeTo(paramComponent);
		dialog.setVisible(true);
		return this.map.get(value);
	}

	private SelectObjectDialog(Frame paramFrame, HashMap paramHashMap,
			String paramString1, String paramString2) {
		super(paramFrame, paramString1, true);
		value = null;
		this.map = paramHashMap;

		JButton localJButton1 = new JButton("Cancel");
		JButton localJButton2 = new JButton("OK");
		localJButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				SelectObjectDialog.dialog.setVisible(false);
			}
		});
		localJButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				SelectObjectDialog.value = (SelectObjectDialog.this.list
						.getSelectedValue());
				SelectObjectDialog.dialog.setVisible(false);
			}
		});
		getRootPane().setDefaultButton(localJButton2);

		this.list = new JList(paramHashMap.keySet().toArray());
		this.list.setSelectionMode(1);
		this.list.addMouseListener(new MouseAdapter() {
			private final JButton val$setButton = new JButton();

			public void mouseClicked(MouseEvent paramAnonymousMouseEvent) {
				if (paramAnonymousMouseEvent.getClickCount() == 2)
					this.val$setButton.doClick();
			}
		});
		JScrollPane localJScrollPane = new JScrollPane(this.list);
		localJScrollPane.setPreferredSize(new Dimension(250, 80));

		localJScrollPane.setMinimumSize(new Dimension(250, 80));
		localJScrollPane.setAlignmentX(0.0F);

		JPanel localJPanel1 = new JPanel();
		localJPanel1.setLayout(new BoxLayout(localJPanel1, 1));
		JLabel localJLabel = new JLabel(paramString2);
		localJLabel.setLabelFor(this.list);
		localJPanel1.add(localJLabel);
		localJPanel1.add(Box.createRigidArea(new Dimension(0, 5)));
		localJPanel1.add(localJScrollPane);
		localJPanel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel localJPanel2 = new JPanel();
		localJPanel2.setLayout(new BoxLayout(localJPanel2, 0));
		localJPanel2.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		localJPanel2.add(Box.createHorizontalGlue());
		localJPanel2.add(localJButton1);
		localJPanel2.add(Box.createRigidArea(new Dimension(10, 0)));
		localJPanel2.add(localJButton2);

		Container localContainer = getContentPane();
		localContainer.add(localJPanel1, "Center");
		localContainer.add(localJPanel2, "South");

		pack();
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * SelectObjectDialog JD-Core Version: 0.6.2
 */