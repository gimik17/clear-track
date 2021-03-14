import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.io.IOException;

class LwPolyLine extends DxfSection {
	GeneralPath path;

	LwPolyLine() {
		this.path = new GeneralPath();
		this.s = this.path;
	}

	public void parse(DxfReader paramDxfReader, DxfDocument paramDxfDocument)
			throws IOException {

		DxfItem localDxfItem = paramDxfReader.goToCode(90);
		int j = Integer.parseInt(localDxfItem.value);
		int k = 0;
		double d1 = 0.0D;
		Point2D.Double localDouble1 = new Point2D.Double();
		int m = 0;
		while (k < j) {
			localDxfItem = paramDxfReader.readItem();
			if (10 == localDxfItem.code) {
				localDouble1.x = Float.parseFloat(localDxfItem.value);
			}
			if (20 == localDxfItem.code) {
				localDouble1.y = java.lang.Double
						.parseDouble(localDxfItem.value);
				m = 1;
			}
			if (42 == localDxfItem.code) {
				d1 = Float.parseFloat(localDxfItem.value);
			}

			if (m != 0) {
				if (0 < k) {
					if (d1 != 0.0D) {
						Point2D.Double localDouble2 = new Point2D.Double(
								this.path.getCurrentPoint().getX(), this.path
										.getCurrentPoint().getY());
						double d2;
						double d3;
						if ((1.0D > d1) && (-1.0D < d1)) {
							d2 = Math.abs(2.0D * Math.atan(d1));

							d3 = localDouble2.distance(localDouble1) / 2.0D;
							double d4 = d3 * Math.tan(d2);

							Point2D.Double localObject = new Point2D.Double();
							if (0.0D < d1)
								((Point2D.Double) localObject).setLocation(d3,
										-d4);
							else {
								((Point2D.Double) localObject).setLocation(d3,
										d4);
							}
							AffineTransform localAffineTransform2 = new AffineTransform();
							localAffineTransform2.rotate(Utils
									.getDirectionAngle(localDouble2,
											localDouble1));

							localAffineTransform2.transform(
									(Point2D) localObject,
									(Point2D) localObject);

							localObject.x += localDouble2.x;
							localObject.y += localDouble2.y;

							double d5 = d3 / Math.sin(d2);

							Arc2D.Double localDouble4 = new Arc2D.Double();
							localDouble4.setArcByTangent(localDouble2,
									(Point2D) localObject, localDouble1, d5);
							this.path.append(localDouble4, true);
						} else {
							d2 = -180.0D;
							if (0.0D > d1) {
								d2 = 180.0D;
							}
							d3 = localDouble2.distance(localDouble1) / 2.0D;

							Arc2D.Double localDouble3 = new Arc2D.Double(0.0D,
									-d3, 2.0D * d3, 2.0D * d3, 180.0D, d2, 0);
							AffineTransform localAffineTransform1 = new AffineTransform();
							localAffineTransform1.rotate(Utils
									.getDirectionAngle(localDouble2,
											localDouble1));
							Shape localObject = localAffineTransform1
									.createTransformedShape(localDouble3);

							localAffineTransform1 = new AffineTransform();
							localAffineTransform1.translate(localDouble2.x,
									localDouble2.y);

							this.path
									.append(localAffineTransform1
											.createTransformedShape((Shape) localObject),
											true);
						}
					} else {
						this.path.lineTo((float) localDouble1.x,
								(float) localDouble1.y);
					}
				} else {
					this.path.moveTo((float) localDouble1.x,
							(float) localDouble1.y);
				}
				m = 0;
				d1 = 0.0D;
				k++;
			}
		}
		paramDxfDocument.entities.add(this.s);
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * LwPolyLine JD-Core Version: 0.6.2
 */