import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class DxfReader extends BufferedReader {
	public DxfReader(String paramString) throws FileNotFoundException {
		super(new FileReader(paramString));
	}

	public DxfReader(File paramFile) throws FileNotFoundException {
		super(new FileReader(paramFile));
	}

	public DxfItem readItem() throws IOException, NumberFormatException {
		DxfItem localDxfItem = new DxfItem();
		localDxfItem.code = readInt();
		localDxfItem.value = readLineDXF();
		return localDxfItem;
	}

	public void goToItem(DxfItem paramDxfItem) throws IOException {
		DxfItem localDxfItem = null;
		do
			localDxfItem = readItem();
		while ((localDxfItem.code != paramDxfItem.code)
				|| (!localDxfItem.value.equals(paramDxfItem.value)));
	}

	public DxfItem goToCode(int paramInt) throws IOException {
		DxfItem localDxfItem = readItem();
		while (paramInt != localDxfItem.code)
			localDxfItem = readItem();
		return localDxfItem;
	}

	public int readInt() throws IOException {
		String str = readLine();
		if (str == null) {
			throw new IOException();
		}
		return Integer.parseInt(str.trim());
	}

	public String readLineDXF() throws IOException {
		String str = readLine();
		if (str == null) {
			throw new IOException();
		}
		return str.trim().toUpperCase();
	}

	Point2D.Double readPoint(int paramInt) throws IOException {
		Point2D.Double localDouble = new Point2D.Double();

		DxfItem localDxfItem = goToCode(10 + paramInt);
		localDouble.x = java.lang.Double.parseDouble(localDxfItem.value);
		localDxfItem = goToCode(20 + paramInt);
		localDouble.y = java.lang.Double.parseDouble(localDxfItem.value);

		return localDouble;
	}
}
