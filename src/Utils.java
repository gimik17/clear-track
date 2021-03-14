import java.awt.Component;
import java.awt.Image;
import java.awt.TrayIcon.MessageType;
import java.awt.geom.Point2D;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class Utils {
	static String applicationName = "ClearTrack";
	static Component parent = null; // wird beim Starten auf Cleartrack-JFrame
									// gesetzt.
	static Image icon = null; // wird beim Straten mit wert belegt.

	static FileFilter getFileFilter(String paramString1, String paramString2) {
		return new GenericFileFilter(paramString1, paramString2);
	}

	static int askUser(String paramString1, String paramString2) {
		if (null == paramString2)
			paramString2 = applicationName;
		return JOptionPane.showConfirmDialog(Utils.parent, paramString1,
				paramString2, JOptionPane.YES_NO_OPTION);
	}

	static void showError(Object paramObject, String paramString) {
		if (null == paramString)
			paramString = applicationName;
		JOptionPane.showMessageDialog(Utils.parent, paramObject, paramString,
				JOptionPane.ERROR_MESSAGE);
	}

	static void showInformation(String paramString1, String paramString2) {
		if (null == paramString2)
			paramString2 = applicationName;
		JOptionPane.showMessageDialog(Utils.parent, paramString1, paramString2,
				JOptionPane.INFORMATION_MESSAGE);
	}

	static Point2D.Double getDistance(Point2D.Double paramDouble1,
			Point2D.Double paramDouble2) {
		return new Point2D.Double(paramDouble2.x - paramDouble1.x,
				paramDouble2.y - paramDouble1.y);
	}

	static Point2D.Double getDirection(Point2D.Double paramDouble1,
			Point2D.Double paramDouble2) {
		return getDirection(getDistance(paramDouble1, paramDouble2));
	}

	static Point2D.Double getDirection(Point2D.Double paramDouble) {
		Point2D.Double localDouble = (Point2D.Double) paramDouble.clone();
		double d = paramDouble.distance(0.0D, 0.0D);
		localDouble.x /= d;
		localDouble.y /= d;
		return localDouble;
	}

	static double getDirectionAngle(Point2D.Double paramDouble1,
			Point2D.Double paramDouble2) {
		Point2D.Double localDouble = getDirection(paramDouble1, paramDouble2);
		double d = Math.atan(localDouble.y / localDouble.x);
		if (0.0D > localDouble.x)
			d += Math.PI;
		else if (0.0D > d)
			d += 2 * Math.PI;
		return d;
	}

	static double getDirectionAngle(Point2D.Double paramDouble) {
		Point2D.Double localDouble = getDirection(paramDouble);
		double d = Math.atan(localDouble.y / localDouble.x);
		if (0.0D > localDouble.x)
			d += Math.PI;
		else if (0.0D > d)
			d += 2 * Math.PI;
		return d;
	}

	static double round(double paramDouble, int paramInt) {
		int i = (int) Math.pow(10.0D, paramInt);
		return Math.floor(paramDouble * i) / i;
	}

	public static void main(String[] paramArrayOfString) {
		System.out.println(getDirection(new Point2D.Double(-1.0D, -1.0D)));
	}

}