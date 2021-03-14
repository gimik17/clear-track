import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;

public class Screen extends JPanel {
	private ShapeRange overAllRange;
	private ShapeRange zoomRange;
	private ShapeRange actualRange;
	AffineTransform transformation;
	private Vector dxfItems;
	private Vector vehicles;
	private Vector dragShapes;
	private Vector tracks;
	Rectangle2D.Double zoomBox;
	GeneralPath track;
	ClearTrackShape selectedItem;
	BufferedImage dxfImage;

	public Screen() {
		this.transformation = new AffineTransform();

		this.overAllRange = new ShapeRange();
		this.actualRange = this.overAllRange;

		this.zoomBox = null;

		this.dxfItems = new Vector();
		this.vehicles = new Vector();
		this.dragShapes = new Vector();
		this.tracks = new Vector();

		this.selectedItem = null;

		setBackground(Color.white);
	}

	protected void calculateTransformation() {
		Dimension localDimension = getSize();
		double d1 = this.actualRange.high.x - this.actualRange.low.x;
		double d2 = this.actualRange.high.y - this.actualRange.low.y;
		double d3 = localDimension.width / d1;
		double d4 = localDimension.height / d2;

		if (d3 < d4) {
			d4 = -1.0D * d3;
		} else {
			d3 = d4;
			d4 *= -1.0D;
		}

		int i = localDimension.width / 2;
		int j = localDimension.height / 2;
		i = (int) (i - d3 * (d1 / 2.0D + this.actualRange.low.x));
		j = (int) (j - d4 * (d2 / 2.0D + this.actualRange.low.y));

		this.transformation.setToIdentity();
		this.transformation.translate(i, j);
		this.transformation.scale(d3, d4);
	}

	Point2D.Double transformToModel(double paramDouble1, double paramDouble2) {
		Point2D.Double localDouble = new Point2D.Double(paramDouble1,
				paramDouble2);
		if (null != this.transformation) {
			try {
				this.transformation.inverseTransform(localDouble, localDouble);
			} catch (Exception localException) {
				Utils.showError(localException, null);
				System.out.println(localException);
			}
		}
		return localDouble;
	}

	Vector getVehicles() {
		return this.vehicles;
	}

	void addDxfItem(Shape paramShape) {
		if (null == paramShape)
			return;
		this.dxfItems.add(paramShape);
		this.overAllRange.evaluateRange(paramShape);
	}

	void addDragShape(ClearTrackShape paramClearTrackShape) {
		if (null != paramClearTrackShape) {
			this.dragShapes.add(paramClearTrackShape);
			paramClearTrackShape.evaluateRange(this.overAllRange);
		}
	}

	void addTrack(ClearTrackShape paramClearTrackShape) {
		if (null == paramClearTrackShape)
			return;
		this.tracks.add(paramClearTrackShape);
		this.selectedItem = paramClearTrackShape;
		paramClearTrackShape.evaluateRange(this.overAllRange);
	}

	void addVehicle(ClearTrackShape paramClearTrackShape) {
		if (null == paramClearTrackShape)
			return;
		this.vehicles.add(paramClearTrackShape);
		this.selectedItem = paramClearTrackShape;
		paramClearTrackShape.evaluateRange(this.overAllRange);
	}

	void removeItem(ClearTrackShape paramClearTrackShape) {
		this.tracks.remove(paramClearTrackShape);
		this.vehicles.remove(paramClearTrackShape);
		this.dragShapes.remove(paramClearTrackShape);

		removeDragShape(paramClearTrackShape);

		if (0 < this.vehicles.size())
			this.selectedItem = ((ClearTrackShape) this.vehicles.lastElement());
		else if (0 < this.tracks.size())
			this.selectedItem = ((ClearTrackShape) this.tracks.lastElement());
		else
			this.selectedItem = null;
	}

	void removeDragShape(ClearTrackShape paramClearTrackShape) {
		Vector localVector = new Vector();
		for (int i = 0; i < this.dragShapes.size(); i++) {
			DragShape localDragShape = (DragShape) this.dragShapes.get(i);
			if (paramClearTrackShape == localDragShape.vehicle)
				localVector.add(localDragShape);
		}
		for (int j = 0; j < localVector.size(); j++)
			this.dragShapes.remove(localVector.get(j));
	}

	void clearDxf() {
		this.dxfItems.clear();
	}

	void clearAll() {
		this.dxfItems.clear();
		this.vehicles.clear();
		this.tracks.clear();
		this.dragShapes.clear();
		this.selectedItem = null;
	}

	ClearTrackShape selectItem(Point2D.Double paramDouble) {
		if (null == paramDouble) {
			return this.selectedItem;
		}
		ClearTrackShape localObject = null;
		for (int i = 0; i < this.vehicles.size(); i++) {
			if (((Vehicle) this.vehicles.get(i)).contains(paramDouble)) {
				Vehicle localVehicle = (Vehicle) this.vehicles.get(i);
				if (localVehicle != this.selectedItem) {
					this.selectedItem = localVehicle;
					return this.selectedItem;
				}

				localObject = localVehicle;
			}
		}

		for (int j = 0; j < this.tracks.size(); j++) {
			if (((ClearTrackShape) this.tracks.get(j)).contains(paramDouble)) {
				ClearTrackShape localClearTrackShape1 = (ClearTrackShape) this.tracks
						.get(j);
				if (localClearTrackShape1 != this.selectedItem) {
					this.selectedItem = localClearTrackShape1;
					return this.selectedItem;
				}
				localObject = localClearTrackShape1;
			}
		}

		for (int k = 0; k < this.dragShapes.size(); k++) {
			if (((ClearTrackShape) this.dragShapes.get(k))
					.contains(paramDouble)) {
				ClearTrackShape localClearTrackShape2 = (ClearTrackShape) this.dragShapes
						.get(k);
				if (localClearTrackShape2 != this.selectedItem) {
					this.selectedItem = localClearTrackShape2;
					return this.selectedItem;
				}
				localObject = localClearTrackShape2;
			}
		}
		this.selectedItem = localObject;
		return localObject;
	}

	void pickZoomRange() {
		if (null != this.zoomBox) {
			this.zoomRange = new ShapeRange();
			this.zoomRange.evaluateRange(this.zoomBox);
			this.actualRange = this.zoomRange;
		}
	}

	void translateZoomRange(Point2D.Double paramDouble) {
		if ((null != this.zoomRange) && (this.actualRange == this.zoomRange))
			this.zoomRange.translate(paramDouble);
	}

	Point2D.Double getCenter() {
		return this.actualRange.getCenter();
	}

	void viewAll() {
		this.overAllRange = new ShapeRange();

		for (int i = 0; i < this.dxfItems.size(); i++)
			this.overAllRange.evaluateRange((Shape) this.dxfItems.get(i));
		for (int j = 0; j < this.tracks.size(); j++)
			((ClearTrackShape) this.tracks.get(j))
					.evaluateRange(this.overAllRange);
		for (int k = 0; k < this.dragShapes.size(); k++)
			((ClearTrackShape) this.dragShapes.get(k))
					.evaluateRange(this.overAllRange);
		for (int m = 0; m < this.vehicles.size(); m++) {
			((ClearTrackShape) this.vehicles.get(m))
					.evaluateRange(this.overAllRange);
		}
		this.actualRange = this.overAllRange;
	}

	private void paintDxf() {
		Dimension localDimension = getSize();
		int i = localDimension.width;
		int j = localDimension.height;
		boolean bool = this.actualRange.hasChanged;
		if (null != this.dxfImage) {
			bool = (bool) || (i != this.dxfImage.getWidth())
					|| (j != this.dxfImage.getHeight());
		}
		if (bool) {
			Cursor localCursor = getCursor();
			setCursor(new Cursor(3));

			this.dxfImage = new BufferedImage(i, j, 2);
			Graphics2D localGraphics2D = this.dxfImage.createGraphics();

			localGraphics2D.setPaint(Color.green);
			localGraphics2D.setTransform(this.transformation);
			localGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			localGraphics2D.setStroke(new BasicStroke(
					(float) (2.0D / this.transformation.getScaleX())));

			for (int k = 0; k < this.dxfItems.size(); k++) {
				localGraphics2D.draw((Shape) this.dxfItems.get(k));
			}

			setCursor(localCursor);
		}
		this.actualRange.hasChanged = false;
	}

	public void paint(Graphics paramGraphics) {
		super.paint(paramGraphics);
		Graphics2D localGraphics2D = (Graphics2D) paramGraphics;
		localGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		AffineTransform localAffineTransform = localGraphics2D.getTransform();
		calculateTransformation();

		paintDxf();

		float f = (float) (2.0D / this.transformation.getScaleX());

		localGraphics2D.setStroke(new BasicStroke(f));

		localGraphics2D.transform(this.transformation);

		localGraphics2D.setColor(Color.lightGray);
		for (int i = 0; i < this.dragShapes.size(); i++) {
			DragShape localDragShape1 = (DragShape) this.dragShapes.get(i);
			localGraphics2D.fill(localDragShape1.getArea());
		}

		localGraphics2D.setColor(Color.gray);
		for (int j = 0; j < this.dragShapes.size(); j++) {
			DragShape localDragShape2 = (DragShape) this.dragShapes.get(j);
			localDragShape2.paint(localGraphics2D);
		}

		localGraphics2D.setTransform(localAffineTransform);

		if (null != this.dxfImage) {
			localGraphics2D.drawImage(this.dxfImage, 0, 0, this);
		}
		localGraphics2D.transform(this.transformation);

		localGraphics2D.setColor(Color.black);
		for (int k = 0; k < this.tracks.size(); k++) {
			Track localTrack = (Track) this.tracks.get(k);
			localTrack.paint(localGraphics2D);
		}

		localGraphics2D.setColor(Color.black);
		for (int m = 0; m < this.vehicles.size(); m++) {
			Vehicle localVehicle = (Vehicle) this.vehicles.get(m);
			localVehicle.paint(localGraphics2D);
		}

		if (null != this.selectedItem) {
			localGraphics2D.setColor(Color.blue);
			this.selectedItem.paint(localGraphics2D);
		}

		if (null != this.zoomBox) {
			localGraphics2D.setColor(Color.black);
			localGraphics2D.draw(this.zoomBox);
		}

		if (null != this.track) {
			localGraphics2D.setColor(Color.blue);
			localGraphics2D.draw(this.track);
		}

		localGraphics2D.setTransform(localAffineTransform);

		localGraphics2D.setColor(Color.black);

		if (null != this.selectedItem)
			localGraphics2D.drawString(this.selectedItem.getInfo(), 10, 20);
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * Screen JD-Core Version: 0.6.2
 */