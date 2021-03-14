import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class DxfScaleDialog extends JDialog {
	private static DxfScaleDialog dialog = null;
	AffineTransform transformation;
	JTextField tfScale;
	JTextField tfX;
	JTextField tfY;

	public static DxfScaleDialog initialize(Component paramComponent,
			AffineTransform paramAffineTransform, String paramString) {
		Frame localFrame = JOptionPane.getFrameForComponent(paramComponent);
		dialog = new DxfScaleDialog(localFrame, paramAffineTransform,
				paramString);
		return dialog;
	}

	public AffineTransform showDialog(Component paramComponent) {
		dialog.setLocationRelativeTo(paramComponent);
		dialog.setVisible(true);
		return this.transformation;
	}

	private DxfScaleDialog(Frame paramFrame,
			AffineTransform paramAffineTransform, String paramString) {
		super(paramFrame, paramString, true);
		if (null != paramAffineTransform) {
			this.transformation = paramAffineTransform;
		} else {
			this.transformation = new AffineTransform();
			this.transformation.scale(1000.0D, 1000.0D);
		}

		JButton localJButton1 = new JButton("Cancel");
		JButton localJButton2 = new JButton("OK");
		localJButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				DxfScaleDialog.dialog.setVisible(false);
			}
		});
		localJButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				try {
					AffineTransform localAffineTransform = new AffineTransform();
					localAffineTransform.translate(
							Double.parseDouble(DxfScaleDialog.this.tfX
									.getText()) * 1000.0D, Double
									.parseDouble(DxfScaleDialog.this.tfY
											.getText()) * 1000.0D);
					localAffineTransform.scale(
							Double.parseDouble(DxfScaleDialog.this.tfScale
									.getText()), Double
									.parseDouble(DxfScaleDialog.this.tfScale
											.getText()));
					DxfScaleDialog.this.transformation = localAffineTransform;
				} catch (NumberFormatException localNumberFormatException) {
					Utils.showError("Bitte ein ganze Zahl eingeben", null);
					System.out.println(localNumberFormatException);
					return;
				}

				DxfScaleDialog.dialog.setVisible(false);
			}
		});
		getRootPane().setDefaultButton(localJButton2);

		JPanel localJPanel1 = new JPanel();
		localJPanel1.setLayout(new BoxLayout(localJPanel1, 1));

		JLabel localJLabel1 = new JLabel("Massstab");
		this.tfScale = new JTextField(
				Integer.toString((int) this.transformation.getScaleX()), 10);
		JLabel localJLabel2 = new JLabel("Verschiebung X [m]");
		this.tfX = new JTextField(Integer.toString((int) this.transformation
				.getTranslateX() / 1000), 10);
		JLabel localJLabel3 = new JLabel("Verschiebung Y [m]");
		this.tfY = new JTextField(Integer.toString((int) this.transformation
				.getTranslateY() / 1000), 10);

		localJPanel1.add(localJLabel1);
		localJPanel1.add(this.tfScale);
		localJPanel1.add(localJLabel2);
		localJPanel1.add(this.tfX);
		localJPanel1.add(localJLabel3);
		localJPanel1.add(this.tfY);

		localJPanel1.add(Box.createRigidArea(new Dimension(0, 5)));
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
 * DxfScaleDialog JD-Core Version: 0.6.2
 */