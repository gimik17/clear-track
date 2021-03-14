import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

class SplineTrack extends Track {
	Ellipse2D.Double startPoint;
	Ellipse2D.Double actualPoint;
	Vector points;
	Vector lines;
	final int maxSegments = 30;

	final double minSegLength = 7500.0D;

	double pointRadius = 750.0D;

	public String toString() {
		return this.startPoint.toString();
	}

	public SplineTrack(GeneralPath paramGeneralPath) {
		this.version = "SplineTrack";
		this.pointRadius = 750.0D;
		this.startPoint = null;
		this.actualPoint = null;
		this.points = new Vector();
		this.lines = new Vector();

		int i = 0;
		PathIterator localPathIterator = paramGeneralPath.getPathIterator(null);
		double[] arrayOfDouble1 = { 0.0D, 0.0D, 0.0D };
		double[] arrayOfDouble2 = { 0.0D, 0.0D, 0.0D };
		double d = 0.0D;
		localPathIterator.currentSegment(arrayOfDouble1);
		arrayOfDouble2[0] = arrayOfDouble1[0];
		arrayOfDouble2[1] = arrayOfDouble1[1];
		this.points.add(new Ellipse2D.Double(arrayOfDouble1[0]
				- this.pointRadius / 2.0D, arrayOfDouble1[1] - this.pointRadius
				/ 2.0D, this.pointRadius, this.pointRadius));

		while ((!localPathIterator.isDone()) && (30 > i)) {
			if (1 == localPathIterator.currentSegment(arrayOfDouble1)) {
				d += Math
						.sqrt(Math.pow(arrayOfDouble1[0] - arrayOfDouble2[0],
								2.0D)
								+ Math.pow(arrayOfDouble1[1]
										- arrayOfDouble2[1], 2.0D));
				arrayOfDouble2[0] = arrayOfDouble1[0];
				arrayOfDouble2[1] = arrayOfDouble1[1];

				if (7500.0D < d) {
					this.points.add(new Ellipse2D.Double(arrayOfDouble1[0]
							- this.pointRadius / 2.0D, arrayOfDouble1[1]
							- this.pointRadius / 2.0D, this.pointRadius,
							this.pointRadius));
					d = 0.0D;
					i++;
				}
			}
			localPathIterator.next();
		}

		if (30 <= i)
			Utils.showInformation("Spline nicht komplett! Spur zu lang.", null);
		if (2 > i) {
			this.points.add(new Ellipse2D.Double(arrayOfDouble1[0]
					+ this.pointRadius / 2.0D, arrayOfDouble1[1]
					+ this.pointRadius / 2.0D, this.pointRadius,
					this.pointRadius));
		}
		this.startPoint = ((Ellipse2D.Double) this.points.get(0));

		makeSpline();
	}

	public SplineTrack() {
		this.version = "SplineTrack";
		this.startPoint = null;
		this.actualPoint = null;
		this.points = new Vector();
		this.lines = new Vector();
	}

	private void makeSpline() {
		int i = this.points.size();

		if (2 > i)
			return;

		double[] arrayOfDouble1 = new double[i];
		double[] arrayOfDouble2 = new double[i];
		for (int j = 0; j < i; j++) {
			arrayOfDouble1[j] = ((Ellipse2D) this.points.get(j)).getCenterX();
			arrayOfDouble2[j] = ((Ellipse2D) this.points.get(j)).getCenterY();
		}

		Spline localSpline1 = new Spline(arrayOfDouble1);
		Spline localSpline2 = new Spline(arrayOfDouble2);
		Point2D.Double localDouble1 = new Point2D.Double();
		Point2D.Double localDouble2 = new Point2D.Double();
		this.lines.clear();
		this.trackLength = 0.0D;
		for (int k = 0; k < i - 1; k++) {
			localDouble1.setLocation(localSpline1.fn(k, 0.0D),
					localSpline2.fn(k, 0.0D));
			for (int m = 1; m < 31; m++) {
				localDouble2.setLocation(localSpline1.fn(k, m / 30.0D),
						localSpline2.fn(k, m / 30.0D));

				this.lines.add(new Line2D.Double(localDouble1, localDouble2));

				this.trackLength += localDouble1.distance(localDouble2);

				localDouble1.setLocation(localDouble2);
			}
		}
	}

	public void save() {
		try {
			FileOutputStream localFileOutputStream = new FileOutputStream(
					this.file);
			ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(
					localFileOutputStream);

			localObjectOutputStream.writeObject(this.version);
			localObjectOutputStream.writeDouble(this.pointRadius);
			for (int i = 0; i < this.points.size(); i++) {
				Ellipse2D.Double localDouble = (Ellipse2D.Double) this.points
						.get(i);
				localObjectOutputStream.writeDouble(localDouble.x);
				localObjectOutputStream.writeDouble(localDouble.y);
			}
			localObjectOutputStream.close();
		} catch (IOException localIOException) {
			Utils.showError("Kann Spur nicht speichern! " + localIOException,
					null);
			System.out.println(localIOException);
		}
	}

	public void load(ObjectInputStream paramObjectInputStream) {
		try {
			this.pointRadius = paramObjectInputStream.readDouble();
			while (true) {
				this.points.add(new Ellipse2D.Double(paramObjectInputStream
						.readDouble(), paramObjectInputStream.readDouble(),
						this.pointRadius, this.pointRadius));
			}
		} catch (EOFException localEOFException) {
		} catch (IOException localIOException) {
			System.out.println(localIOException);
			Utils.showError("Kann Datei nicht lesen! " + localIOException, null);
		}
		this.startPoint = ((Ellipse2D.Double) this.points.get(0));

		makeSpline();
	}

	public void evaluateRange(ShapeRange paramShapeRange) {
		for (int i = 0; i < this.points.size(); i++)
			paramShapeRange.evaluateRange((Shape) this.points.get(i));
	}

	public boolean contains(Point2D.Double paramDouble) {
		if (this.startPoint.contains(paramDouble)) {
			if (this.actualPoint != this.startPoint)
				this.actualPoint = this.startPoint;
			else
				this.actualPoint = null;
			return true;
		}

		for (int i = 0; i < this.points.size(); i++) {
			if (((Shape) this.points.get(i)).contains(paramDouble)) {
				this.actualPoint = ((Ellipse2D.Double) this.points.get(i));
				return true;
			}
		}

		return false;
	}

	public Point2D.Double getPosition() {
		if (null != this.actualPoint) {
			return new Point2D.Double(this.actualPoint.getCenterX(),
					this.actualPoint.getCenterY());
		}
		return null;
	}

	public void setPosition(Point2D.Double paramDouble) {
		Point2D.Double localDouble;
		if (null == this.actualPoint) {
			localDouble = Utils.getDistance(
					new Point2D.Double(this.startPoint.getCenterX(),
							this.startPoint.getCenterY()), paramDouble);
			for (int i = 0; i < this.points.size(); i++) {
				Ellipse2D.Double localDouble1 = (Ellipse2D.Double) this.points
						.get(i);
				localDouble1.x += localDouble.x;
				localDouble1.y += localDouble.y;
			}
		} else {
			localDouble = Utils.getDistance(
					new Point2D.Double(this.actualPoint.getCenterX(),
							this.actualPoint.getCenterY()), paramDouble);
			this.actualPoint.x += localDouble.x;
			this.actualPoint.y += localDouble.y;
		}

		makeSpline();
	}

	public void paint(Graphics2D paramGraphics2D) {
		RectangularShape localRectangularShape = (RectangularShape) this.points
				.get(0);

		if (this.actualPoint == localRectangularShape)
			paramGraphics2D.draw(localRectangularShape.getFrame());
		else {
			paramGraphics2D.fill(localRectangularShape.getFrame());
		}
		for (int i = 1; i < this.points.size(); i++) {
			localRectangularShape = (RectangularShape) this.points.get(i);

			if (this.actualPoint == localRectangularShape)
				paramGraphics2D.draw(localRectangularShape);
			else {
				paramGraphics2D.fill(localRectangularShape);
			}
		}
		for (int j = 1; j < this.lines.size(); j++)
			paramGraphics2D.draw((Shape) this.lines.get(j));
	}

	public void doTracking(Vehicle paramVehicle, CalculationMethod method) {
		//System.out.println(method.getClass().getName());
		Line2D localLine2D = (Line2D) this.lines.get(0);
		paramVehicle.setDirection(Utils.getDirectionAngle(
				(Point2D.Double) localLine2D.getP1(),
				(Point2D.Double) localLine2D.getP2()));
		paramVehicle.setPosition(new Point2D.Double(this.startPoint
				.getCenterX(), this.startPoint.getCenterY()));
		DragShape localDragShape = paramVehicle.getDragShape();

		paramVehicle.drawDragShape();
		for (int i = 0; i < this.lines.size(); i++) {
			localLine2D = (Line2D) this.lines.get(i);

			if (localDragShape.maxTurn > paramVehicle.maxTurn) {
				Utils.showError(
						"Einschlag zu gross!\n\nFahrzeug " + paramVehicle.name
								+ " kann maximal "
								+ Math.toDegrees(paramVehicle.maxTurn)
								+ "° einlenken.", null);
				break;
			}

//			paramVehicle.track(Utils.getDistance((Point2D.Double) localLine2D.getP1(),(Point2D.Double) localLine2D.getP2()));
//--------------------------Neu-------------------------------------
			paramVehicle.track(Utils.getDistance((Point2D.Double)localLine2D.getP1(), (Point2D.Double)localLine2D.getP2()),method);
//--------------------------Neu-------------------------------------
		}
	}

	public boolean removePoint() {
		if ((null != this.actualPoint) && (2 < this.points.size())) {
			Ellipse2D.Double localDouble1 = this.actualPoint;
			if (this.actualPoint != this.startPoint) {
				for (int i = 0; i < this.points.size(); i++) {
					Ellipse2D.Double localDouble2 = (Ellipse2D.Double) this.points
							.get(i);
					if (localDouble1 == localDouble2)
						break;
					this.actualPoint = localDouble2;
				}
			} else {
				this.startPoint = ((Ellipse2D.Double) this.points.get(1));
				this.actualPoint = this.startPoint;
			}
			this.points.remove(localDouble1);
			makeSpline();
			return false;
		}

		return true;
	}

	public void insertPoint(Point2D.Double paramDouble) {
		Ellipse2D.Double localDouble = new Ellipse2D.Double(paramDouble.x
				- this.pointRadius / 2.0D, paramDouble.y - this.pointRadius
				/ 2.0D, this.pointRadius, this.pointRadius);
		if (null != this.actualPoint) {
			this.points.insertElementAt(localDouble,
					this.points.indexOf(this.actualPoint) + 1);
			this.actualPoint = localDouble;
		} else {
			this.points.add(localDouble);
			this.actualPoint = localDouble;
		}
		makeSpline();
	}
}