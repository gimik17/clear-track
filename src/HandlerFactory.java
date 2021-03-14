class HandlerFactory {
	static DxfSection getHandler(DxfItem paramDxfItem) {
		if ("ARC".equals(paramDxfItem.value)) {
			return new Arc();
		}
		if ("CIRCLE".equals(paramDxfItem.value)) {
			return new Circle();
		}
		if ("LINE".equals(paramDxfItem.value)) {
			return new Line();
		}
		if ("POLYLINE".equals(paramDxfItem.value)) {
			return new PolyLine();
		}
		if ("LWPOLYLINE".equals(paramDxfItem.value)) {
			return new LwPolyLine();
		}
		if ("INSERT".equals(paramDxfItem.value)) {
			return new Insert();
		}
		if ("HEADER".equals(paramDxfItem.value)) {
			return new Header();
		}
		if ("CLASSES".equals(paramDxfItem.value)) {
			return new Classes();
		}
		if ("TABLES".equals(paramDxfItem.value)) {
			return new Tables();
		}
		if ("BLOCKS".equals(paramDxfItem.value)) {
			return new Blocks();
		}
		if ("BLOCK".equals(paramDxfItem.value)) {
			return new Block();
		}
		if ("ENTITIES".equals(paramDxfItem.value)) {
			return new Entities();
		}
		if ("OBJECTS".equals(paramDxfItem.value)) {
			return new Objects();
		}
		if ("THUMBNAILIMAGE".equals(paramDxfItem.value)) {
			return new ThumbnailImage();
		}
		return null;
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * HandlerFactory JD-Core Version: 0.6.2
 */