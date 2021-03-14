import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Vehicle implements ClearTrackShape {
	String name;
	double maxTurn;
	Bounds bounds;
	Tracker tracker;
	private Vehicle trailer;
	Point2D.Double trailerPosition;
	private DragShape dragShape;
	private File file;

	Vehicle() {
		this.bounds = null;
		this.tracker = new Tracker();
		this.trailerPosition = new Point2D.Double(-2500.0D, 0.0D);
	}

	public String toString() {
		return "Fahrzeug: " + this.name + "\nTracker: " + this.tracker;
	}

	void setTrailer(Vehicle paramVehicle) {
		this.trailer = paramVehicle;
		if (null != this.trailer)
			this.trailer.translate(this.trailerPosition);
	}

	Vehicle getTrailer() {
		return this.trailer;
	}

	void setDirection(double paramDouble) {
		this.tracker.setDirection(paramDouble);
		if (null != this.trailer) {
			Point2D.Double localDouble = this.tracker
					.getAbsolutePosition(this.trailerPosition);
			this.trailer.setPosition(localDouble);
			this.trailer.setDirection(paramDouble);
		}
	}

	DragShape getDragShape() {
		return this.dragShape;
	}

	void setDragShape(DragShape paramDragShape) {
		this.dragShape = paramDragShape;
		if (null != this.trailer)
			this.trailer.setDragShape(paramDragShape);
	}

	void translate(Point2D.Double paramDouble) {
		if (null != this.tracker) {
			this.tracker.translate(paramDouble);
			if (null != this.trailer)
				this.trailer.translate(paramDouble);
		} else {
			System.out
					.println("Kann Position nicht verschieben (Tracker == null)");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IVehicle#track(java.awt.geom.Point2D.Double, CalculationMethod)
	 */
	public void track(Point2D.Double realtivePosition, CalculationMethod method) {
		if (null != this.tracker) {
			if ((null != this.dragShape) && (this == this.dragShape.vehicle)) {
				double d1 = this.tracker.getDirectionAngle();
				double d2 = Utils.getDirectionAngle(realtivePosition);

				if (Math.PI < d1)
					d1 -= 2 * Math.PI;
				if (Math.PI < d2) {
					d2 -= 2 * Math.PI;
				}
				double d3 = d2 - d1;

				if (Math.PI < d3)
					d3 -= 2 * Math.PI;
				if (-Math.PI > d3) {
					d3 += 2 * Math.PI;
				}
				this.dragShape.addTrackIncrement(
						realtivePosition.distance(0.0D, 0.0D), d3);
			}

			this.tracker.track(realtivePosition, this, this.trailer, method);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IVehicle#orientate(java.awt.geom.Point2D.Double, CalculationMethod)
	 */
	public void orientate(Point2D.Double position, CalculationMethod method) {
		if (null != this.tracker)
			this.tracker.track(position, this, this.trailer, method);
	}

	void drawDragShape() {
		if ((null != this.dragShape) && (null != this.bounds)) {
			this.bounds.transformation = this.tracker
					.getAbsoluteTransformation();
			this.dragShape.addDragShapeItem(this.bounds.getTransformedShape(),
					this);
		}
	}

	public boolean contains(Point2D.Double paramDouble) {
		boolean bool = false;
		if (null != this.bounds)
			bool = (bool) || (this.bounds.contains(paramDouble));
		if (null != this.trailer)
			bool = (bool) || (this.trailer.contains(paramDouble));
		return bool;
	}

	public void evaluateRange(ShapeRange paramShapeRange) {
		if (null != this.bounds) {
			this.bounds.transformation = this.tracker
					.getAbsoluteTransformation();
			paramShapeRange.evaluateRange(this.bounds.getTransformedShape());
		}
		if (null != this.trailer)
			this.trailer.evaluateRange(paramShapeRange);
	}

	public void paint(Graphics2D paramGraphics2D) {
		if (null != this.tracker)
			this.tracker.paint(paramGraphics2D);
		if (null != this.bounds) {
			this.bounds.transformation = this.tracker
					.getAbsoluteTransformation();
			this.bounds.paint(paramGraphics2D);
		}
		if (null != this.trailer)
			this.trailer.paint(paramGraphics2D);
	}

	public String getInfo() {
		return this.name + ". Max Einschlag = "
				+ Utils.round(Math.toDegrees(this.maxTurn), 1) + "°.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IVehicle#getPosition()
	 */
	@Override
	public Point2D.Double getPosition() {
		return this.tracker.getLead();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IVehicle#setPosition(java.awt.geom.Point2D.Double)
	 */
	@Override
	public void setPosition(Point2D.Double paramDouble) {
		if (null != this.tracker) {
			Point2D.Double localDouble = Utils.getDistance(
					this.tracker.getLead(), paramDouble);
			this.tracker.translate(localDouble);
			if (null != this.trailer)
				this.trailer.translate(localDouble);
		} else {
			System.out.println("Kann Position nicht setzen (Tracker == null)");
		}
	}

	public String getName() {
		return this.name;
	}

	public void setFile(File paramFile) {
		this.file = paramFile;
	}

	public String transformToHtml() {
		StringWriter stringWriter = new StringWriter();
		StreamResult out = new StreamResult(stringWriter);
		String xslFileName = this.file.getParentFile() + File.separator
				+ "dimension.xsl";
		StreamSource in = new StreamSource(this.file);
		StreamSource xslStream = new StreamSource(xslFileName);

		Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer(
					xslStream);

			transformer.transform(in, out);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "HTML Transformation failed: " + e;
		}

		return stringWriter.toString();
	}
}