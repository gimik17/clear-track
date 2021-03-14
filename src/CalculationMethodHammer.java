

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class CalculationMethodHammer implements CalculationMethod {

	@Override
	public Double getNewTrailPoint(Double incrementLead, Trackable tracker) {
		Point2D.Double localP1=new Point2D.Double(tracker.getLead().x+incrementLead.x,tracker.getLead().y+incrementLead.y);

		//Hilfsvektor berechnen
		double lambda = 6.0/10.0*(localP1.distance(tracker.getTrail())-tracker.getDistance());
		Point2D.Double localTemp1 = Utils.getDirection(tracker.getLead(), tracker.getTrail());
		localTemp1.x = tracker.getTrail().x+lambda*localTemp1.x;
		localTemp1.y = tracker.getTrail().y+lambda*localTemp1.y;
		
		//neues trail berechnen
		localTemp1 = Utils.getDirection(localP1, localTemp1);
		Point2D.Double back = new Point2D.Double(localP1.x + tracker.getDistance() * localTemp1.x, localP1.y + tracker.getDistance() * localTemp1.y);
			
		return back;
	}

}
