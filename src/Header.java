import java.io.IOException;
import java.util.Vector;

class Header extends DxfSection {
	public void parse(DxfReader paramDxfReader, DxfDocument paramDxfDocument)
			throws IOException {
		DxfItem localDxfItem = paramDxfReader.readItem();
		Vector<DxfItem> localVector = null;
		while ((!"ENDSEC".equals(localDxfItem.value))
				|| (0 != localDxfItem.code)) {
			if (9 == localDxfItem.code) {
				localVector = new Vector<DxfItem>(1, 2);
				paramDxfDocument.header.put(localDxfItem.value, localVector);
			} else if (null != localVector) {
				localVector.add(localDxfItem);
			} else {
				System.out.println("Header ???");
			}
			localDxfItem = paramDxfReader.readItem();
		}
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * Header JD-Core Version: 0.6.2
 */