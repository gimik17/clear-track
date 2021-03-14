

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class CalculationMethodHalter implements CalculationMethod {

	@Override
	public Point2D.Double getNewTrailPoint(Double incrementLead, Trackable tracker) {
		Point2D.Double localP1=new Point2D.Double(tracker.getLead().x+incrementLead.x,tracker.getLead().y+incrementLead.y);
		
		//Winkelhalbierende bilden
		Point2D.Double localTemp1= Utils.getDirection(tracker.getTrail(), tracker.getLead());
		Point2D.Double localTemp2= Utils.getDirection(tracker.getTrail(), localP1);
		localTemp2 = new Point2D.Double(localTemp1.x+localTemp2.x,localTemp1.y+localTemp2.y);

		
		//neues trail berechnen
		double p = 2*(localTemp2.x*(tracker.getTrail().x-localP1.x)+localTemp2.y*(tracker.getTrail().y-localP1.y))/(localTemp2.x*localTemp2.x+localTemp2.y*localTemp2.y);
		double q = ((tracker.getTrail().x-localP1.x)*(tracker.getTrail().x-localP1.x)+(tracker.getTrail().y-localP1.y)*(tracker.getTrail().y-localP1.y)-tracker.getDistance()*tracker.getDistance())/(localTemp2.x*localTemp2.x+localTemp2.y*localTemp2.y);
		double lambda1 = -p/2.0+Math.sqrt(p*p/4.0-q);
		double lambda2 = -p/2.0-Math.sqrt(p*p/4.0-q);
		double lambda = lambda1;
		
		if (lambda > 1.0 || lambda < 0.0)
			lambda = lambda2;
		if ((lambda1 < 1.0 && lambda2 < 1.0) && (lambda1 > 0.0 && lambda2 > 0.0))
			lambda = Math.min(lambda1, lambda2);
		
		Point2D.Double back = (Double) tracker.getTrail().clone();
		back.x += lambda * localTemp2.x;
		back.y += lambda * localTemp2.y;
		
		return back;
	}

}
