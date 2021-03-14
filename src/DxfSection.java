import java.awt.Shape;
import java.io.IOException;

abstract class DxfSection {
	Shape s;

	DxfSection() {
		this.s = null;
	}

	abstract void parse(DxfReader paramDxfReader, DxfDocument paramDxfDocument)
			throws IOException;
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * DxfSection JD-Core Version: 0.6.2
 */