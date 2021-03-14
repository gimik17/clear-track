import java.io.IOException;
import java.util.Vector;

class Block extends DxfSection {
	public void parse(DxfReader paramDxfReader, DxfDocument paramDxfDocument)
			throws IOException {
		DxfItem localDxfItem = paramDxfReader.readItem();

		DxfDocument localDxfDocument = new DxfDocument();
		localDxfDocument.blocks = paramDxfDocument.blocks;
		localDxfDocument.header = paramDxfDocument.header;

		Vector localVector = null;
		while (!"ENDBLK".equals(localDxfItem.value)) {
			if ((null == localVector) && (2 == localDxfItem.code)) {
				localVector = new Vector(1, 2);
				paramDxfDocument.blocks.put(localDxfItem.value, localVector);

				localDxfDocument.entities = localVector;
			}
			DxfSection localDxfSection = HandlerFactory
					.getHandler(localDxfItem);
			if (null != localDxfSection) {
				localDxfSection.parse(paramDxfReader, localDxfDocument);
			}
			localDxfItem = paramDxfReader.readItem();
		}
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * Block JD-Core Version: 0.6.2
 */