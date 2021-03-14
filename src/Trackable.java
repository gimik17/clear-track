import java.awt.geom.Point2D.Double;

public interface Trackable {
	enum SteeringAngle {
		Fixed,
		LeadDependent,
		DrawingVehicleDependant
	}
	public abstract Trackable getDrawingVehicle();
	public abstract Double getLead();
	public abstract Double getTrail();
	public abstract double getDistance();
	public abstract SteeringAngle getSteeringAngle();
}