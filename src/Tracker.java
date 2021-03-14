import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;

public class Tracker implements Trackable {
	static int pointRadius = 500;
	private Point2D.Double lead;
	private Point2D.Double trail;
	private double distance;
	int paintDistance;
	int movedDistance;
//	private SteeringAngle steeringAngle = SteeringAngle.LeadDependent;
	private SteeringAngle steeringAngle = SteeringAngle.Fixed;

	Tracker() {
		this.lead = new Point2D.Double(0.0D, 0.0D);
		this.trail = new Point2D.Double(-2000.0D, 0.0D);
		this.paintDistance = 500;
		this.movedDistance = 0;
	}

	public String toString() {
		return "lead: " + this.lead + "\n trail: " + this.trail;
	}

	public void setLead(Point2D.Double paramDouble) {
		this.lead = paramDouble;
		calculateDistance();
	}

	public void setTrail(Point2D.Double paramDouble) {
		this.trail = paramDouble;
		calculateDistance();
	}

	void setDirection(double paramDouble) {
		this.trail.x = (this.lead.x - this.distance * Math.cos(paramDouble));
		this.trail.y = (this.lead.y - this.distance * Math.sin(paramDouble));
	}

	public Double getLead() {
		return this.lead;
	}

	protected void calculateDistance() {
		this.distance = Math.abs(this.lead.distance(this.trail));
	}

	public Point2D.Double getDirection() {
		return Utils.getDirection(this.trail, this.lead);
	}

	public double getDirectionAngle() {
		return Utils.getDirectionAngle(this.trail, this.lead);
	}

	AffineTransform getAbsoluteTransformation() {
		AffineTransform localAffineTransform = new AffineTransform();
		localAffineTransform.translate(this.lead.x, this.lead.y);
		localAffineTransform.rotate(getDirectionAngle());
		return localAffineTransform;
	}

	Point2D.Double getAbsolutePosition(Point2D.Double paramDouble) {
		AffineTransform localAffineTransform = new AffineTransform();
		localAffineTransform.translate(this.lead.x, this.lead.y);
		localAffineTransform.rotate(getDirectionAngle());
		Point2D.Double localDouble = new Point2D.Double();
		localAffineTransform.transform(paramDouble, localDouble);
		return localDouble;
	}

	/**
	 * @param relativePosition
	 * @param vehicle
	 * @param trailer
	 * @param method
	 */
	public void track(Point2D.Double relativePosition, Vehicle vehicle, Vehicle trailer,CalculationMethod method) {
		int steps = (int) Math.round(Math.abs(relativePosition.distance(0.0D, 0.0D)) + 0.5D);
		Point2D.Double increments = new Point2D.Double(relativePosition.x / steps,relativePosition.y / steps);
		if (null == trailer) {
			for (int i = 0; i < steps; i++) {
				track(increments,method);
				if (hasToPaint())
					vehicle.drawDragShape();
			}
		} else {
			Point2D.Double trailerPositionAfter = null;
			Point2D.Double trailerPositionBefore = getAbsolutePosition(vehicle.trailerPosition);
			for (int i = 0; i < steps; i++) {
				track(increments,method);

				if (hasToPaint())
					vehicle.drawDragShape();

				trailerPositionAfter = getAbsolutePosition(vehicle.trailerPosition);
				trailer.track(Utils.getDistance(trailerPositionBefore,trailerPositionAfter), method);
				trailerPositionBefore = (Point2D.Double) trailerPositionAfter.clone();
			}
		}
	}

	private void track(Point2D.Double relativePosition,CalculationMethod method) {
//		this.lead.x += relativePosition.x;
//		this.lead.y += relativePosition.y;
//
//		Point2D.Double localDouble = Utils.getDirection(this.trail, this.lead);
//
//		this.trail.x = (this.lead.x - this.distance * localDouble.x);
//		this.trail.y = (this.lead.y - this.distance * localDouble.y);
		Point2D.Double newTrail = method.getNewTrailPoint(relativePosition, this);
		
		this.lead.x += relativePosition.x;
		this.lead.y += relativePosition.y;
		
		this.trail.x = newTrail.x;
		this.trail.y = newTrail.y;
	}

	void translate(Point2D.Double paramDouble) {
		this.lead.x += paramDouble.x;
		this.lead.y += paramDouble.y;
		this.trail.x += paramDouble.x;
		this.trail.y += paramDouble.y;
	}

	private boolean hasToPaint() {
		this.movedDistance += 1;
		if (this.paintDistance < this.movedDistance) {
			this.movedDistance = 0;
			return true;
		}
		return false;
	}

	public void paint(Graphics2D paramGraphics2D) {
		paramGraphics2D.draw(new Line2D.Double(this.lead, this.trail));
		paramGraphics2D.fill(new Rectangle2D.Double(this.lead.x - pointRadius
				/ 2, this.lead.y - pointRadius / 2, pointRadius, pointRadius));
		paramGraphics2D.fill(new Ellipse2D.Double(this.trail.x - pointRadius
				/ 2, this.trail.y - pointRadius / 2, pointRadius, pointRadius));
	}

	@Override
	public Trackable getDrawingVehicle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getTrail() {
		// TODO Auto-generated method stub
		return this.trail;
	}

	@Override
	public double getDistance() {
		// TODO Auto-generated method stub
		return this.distance;
	}

	@Override
	public SteeringAngle getSteeringAngle() {
		// TODO Auto-generated method stub
		return this.steeringAngle ;
	}
}