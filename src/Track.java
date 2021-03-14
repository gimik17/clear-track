import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public abstract class Track extends ClearTrackShapeAdapter {
	String version = "Track";
	File file;
	double trackLength;

	Track() {
		this.file = null;
		this.trackLength = 0.0D;
	}

	abstract void doTracking(Vehicle paramVehicle, CalculationMethod method);

	abstract boolean removePoint();

	abstract void insertPoint(Point2D.Double paramDouble);

	abstract void save();

	static Track load(File paramFile) {
		FileInputStream localFileInputStream = null;
		ObjectInputStream localObjectInputStream = null;
		try {
			SplineTrack localSplineTrack = null;
			localFileInputStream = new FileInputStream(paramFile);
			localObjectInputStream = new ObjectInputStream(localFileInputStream);
			String str = (String) localObjectInputStream.readObject();
			if (!str.equals("SplineTrack")) {
				Utils.showError("Format nicht erkannt! Track Version: " + str,
						null);
				return null;
			}

			localSplineTrack = new SplineTrack();
			localSplineTrack.load(localObjectInputStream);
			localSplineTrack.file = paramFile;

			localObjectInputStream.close();
			localFileInputStream.close();

			return localSplineTrack;
		} catch (ClassNotFoundException localClassNotFoundException) {
			System.out.println(localClassNotFoundException);
			Utils.showError("Kann Datei nicht lesen! "
					+ localClassNotFoundException, null);
			return null;
		} catch (IOException localIOException) {
			System.out.println(localIOException);
			Utils.showError("Kann Datei nicht lesen! " + localIOException, null);
		} finally {

			try {
				if (null != localObjectInputStream)
					localObjectInputStream.close();
				if (null != localFileInputStream)
					localFileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	public String getInfo() {
		String str = "";
		if (null != this.file)
			str = this.file.getName();
		else
			str = "Neue Fahrspur";
		str = str + ", Länge: " + Utils.round(this.trackLength / 1000.0D, 1)
				+ "m";
		return str;
	}

	double getTrackLength() {
		return this.trackLength;
	}

}