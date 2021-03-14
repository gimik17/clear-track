import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

public class Dxf {
	static final String SECTION = "SECTION";
	static final String ENDSEC = "ENDSEC";
	static final String HEADER = "HEADER";
	static final String CLASSES = "CLASSES";
	static final String TABLES = "TABLES";
	static final String BLOCKS = "BLOCKS";
	static final String ENTITIES = "ENTITIES";
	static final String OBJECTS = "OBJECTS";
	static final String THUMBNAILIMAGE = "THUMBNAILIMAGE";
	static final String EOF = "EOF";
	static final String BLOCK = "BLOCK";
	static final String ENDBLK = "ENDBLK";
	static final String ACAD_PROXY_ENTITY = "ACAD_PROXY_ENTITY";
	static final String ARC = "ARC";
	static final String ATTDEF = "ATTDEF";
	static final String ATTRIB = "ATTRIB";
	static final String BODY = "BODY";
	static final String CIRCLE = "CIRCLE";
	static final String DIMENSION = "DIMENSION";
	static final String ELLIPSE = "ELLIPSE";
	static final String HATCH = "HATCH";
	static final String IMAGE = "IMAGE";
	static final String INSERT = "INSERT";
	static final String LEADER = "LEADER";
	static final String LINE = "LINE";
	static final String LWPOLYLINE = "LWPOLYLINE";
	static final String MLINE = "MLINE";
	static final String MTEXT = "MTEXT";
	static final String OLEFRAME = "OLEFRAME";
	static final String OLE2FRAME = "OLE2FRAME";
	static final String POINT = "POINT";
	static final String POLYLINE = "POLYLINE";
	static final String RAY = "RAY";
	static final String REGION = "REGION";
	static final String SEQEND = "SEQEND";
	static final String SHAPE = "SHAPE";
	static final String SOLID = "SOLID";
	static final String SPLINE = "SPLINE";
	static final String TEXT = "TEXT";
	static final String TOLERANCE = "TOLERANCE";
	static final String TRACE = "TRACE";
	static final String VERTEX = "VERTEX";
	static final String VIEWPORT = "VIEWPORT";
	static final String XLINE = "XLINE";
	DxfDocument document;

	Dxf() {
		this.document = null;
	}

	public Vector getShapes() {
		return this.document.entities;
	}

	void load(File paramFile) {
		DxfReader localDxfReader = null;
		this.document = new DxfDocument();
		try {
			localDxfReader = new DxfReader(paramFile);
		} catch (FileNotFoundException localFileNotFoundException) {
			System.out.println(localFileNotFoundException);
			Utils.showError(localFileNotFoundException, null);
			return;
		}
		DxfItem localDxfItem = null;
		try {
			DxfSection localDxfSection = null;
			localDxfItem = localDxfReader.readItem();
			while ((!"EOF".equals(localDxfItem.value))
					|| (0 != localDxfItem.code)) {
				if ("SECTION".equals(localDxfItem.value)) {
					localDxfSection = HandlerFactory.getHandler(localDxfReader
							.readItem());
					if (null != localDxfSection) {
						localDxfSection.parse(localDxfReader, this.document);
					}

					localDxfSection = null;
				}
				localDxfItem = localDxfReader.readItem();
			}
		} catch (IOException localIOException2) {
			System.out.println(localDxfItem);

			System.out.println(localIOException2);
		} catch (NumberFormatException localNumberFormatException) {
			Utils.showError(
					"DXF Format unbekannt (ev. nicht als Text exportiert)",
					null);

			System.out.println(localDxfItem);
		}
		try {
			localDxfReader.close();
		} catch (IOException localIOException3) {
			System.out.println(localIOException3);
		}
	}

	public static void main(String[] paramArrayOfString) {
		File localFile = new File("../../Fahrzeuge/Kreisel_vers-2000.DXF");
		Dxf localDxf = new Dxf();
		localDxf.load(localFile);
		System.out.println(localDxf.document);
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name: Dxf
 * JD-Core Version: 0.6.2
 */