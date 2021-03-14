import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Settings {
	static File dxfFile = new File("./");
	
	static File xlsFile = new File("./");

	static File vehicleFile = new File("./vehicles");

	static AffineTransform dxfTransformation = new AffineTransform();
	
	static CalculationMethod calMethod = new CalculationMethodTenner();

	static void load() {
		try {
			dxfTransformation.scale(1000.0D, 1000.0D);
			FileInputStream localFileInputStream = new FileInputStream(
					"settings.bin");
			ObjectInputStream localObjectInputStream = new ObjectInputStream(
					localFileInputStream);
			dxfFile = (File) localObjectInputStream.readObject();
			vehicleFile = (File) localObjectInputStream.readObject();
			dxfTransformation = (AffineTransform) localObjectInputStream
					.readObject();
			calMethod = (CalculationMethod) localObjectInputStream.readObject();
			xlsFile = (File) localObjectInputStream.readObject();
			localObjectInputStream.close();
		} catch (IOException localIOException) {
			System.out.println(localIOException);
		} catch (ClassNotFoundException localClassNotFoundException) {
			System.out.println(localClassNotFoundException);
		}
	}

	static void save() {
		try {
			FileOutputStream localFileOutputStream = new FileOutputStream(
					"settings.bin");
			ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(
					localFileOutputStream);
			localObjectOutputStream.writeObject(dxfFile);
			localObjectOutputStream.writeObject(vehicleFile);
			localObjectOutputStream.writeObject(dxfTransformation);
			localObjectOutputStream.writeObject(calMethod);
			localObjectOutputStream.writeObject(xlsFile);
			localObjectOutputStream.close();
		} catch (IOException localIOException) {
			System.out.println(localIOException);
		}
	}

}