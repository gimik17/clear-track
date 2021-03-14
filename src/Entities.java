import java.io.IOException;

class Entities extends DxfSection {
	public void parse(DxfReader paramDxfReader, DxfDocument paramDxfDocument)
			throws IOException {
		DxfItem localDxfItem = paramDxfReader.readItem();
		while ((!"ENDSEC".equals(localDxfItem.value))
				|| (0 != localDxfItem.code)) {
			DxfSection localDxfSection = HandlerFactory
					.getHandler(localDxfItem);
			if (null != localDxfSection) {
				localDxfSection.parse(paramDxfReader, paramDxfDocument);
			}
			localDxfItem = paramDxfReader.readItem();
		}
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * Entities JD-Core Version: 0.6.2
 */