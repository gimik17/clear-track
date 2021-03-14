import java.io.IOException;

class Tables extends DxfSection {
	public void parse(DxfReader paramDxfReader, DxfDocument paramDxfDocument)
			throws IOException {
		DxfItem localDxfItem = paramDxfReader.readItem();
		while ((!"ENDSEC".equals(localDxfItem.value))
				|| (0 != localDxfItem.code))
			localDxfItem = paramDxfReader.readItem();
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * Tables JD-Core Version: 0.6.2
 */