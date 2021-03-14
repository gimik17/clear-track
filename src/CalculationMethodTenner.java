

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import org.apache.poi.ss.formula.eval.NotImplementedException;

public class CalculationMethodTenner implements CalculationMethod {

	@Override
	public Double getNewTrailPoint(Double incrementLead, Trackable tracker) {
		switch(tracker.getSteeringAngle())
		{
		case Fixed:
		{
			Point2D.Double localP1=new Point2D.Double(tracker.getLead().x+incrementLead.x,tracker.getLead().y+incrementLead.y);
			
			//Hilfsvektor bestimmen
			Point2D.Double drawDirection = Utils.getDirection(tracker.getTrail(), localP1);
			
			//neues trail berechnen
			Point2D.Double back = new Point2D.Double(localP1.x- tracker.getDistance() * drawDirection.x, localP1.y- tracker.getDistance() * drawDirection.y);
				
			return back;
			
		}
		case LeadDependent:
		{
			Point2D.Double localP1=new Point2D.Double(tracker.getLead().x+incrementLead.x,tracker.getLead().y+incrementLead.y);
						
			double backDirection = 2 * Math.PI - (Utils.getDirectionAngle(incrementLead) / 5);			
			
			//neues gestrecktes trail berechnen
			Point2D.Double back = new Point2D.Double(incrementLead.x * Math.cos(backDirection) + tracker.getTrail().x,  incrementLead.y * Math.sin(backDirection) + tracker.getTrail().y);

			//Hilfsvektor bestimmen
			Point2D.Double drawDirection = Utils.getDirection(back, localP1);

			back = new Point2D.Double(localP1.x- tracker.getDistance() * drawDirection.x, localP1.y- tracker.getDistance() * drawDirection.y);
			
				
			return back;
			
		}
		case DrawingVehicleDependant:
			throw new NotImplementedException("DrawingVehicleDependant");
		}
		return new Double();
	}

}
