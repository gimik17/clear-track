

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class CalculationMethodSchneider implements CalculationMethod {

	@Override
	public Double getNewTrailPoint(Double incrementLead, Trackable tracker) {
		Point2D.Double localP1=new Point2D.Double(tracker.getLead().x+incrementLead.x,tracker.getLead().y+incrementLead.y);
		
		//Streckenhalbierende berechnen
		Point2D.Double localTemp1 = new Point2D.Double(tracker.getLead().x+0.5*(localP1.x-tracker.getLead().x),tracker.getLead().y+0.5*(localP1.y-tracker.getLead().y));
				
		//neues lead berechnen
		double t1x = localP1.x-tracker.getTrail().x;
		double t2x = localTemp1.x-tracker.getTrail().x;
		double t1y = localP1.y-tracker.getTrail().y;
		double t2y = localTemp1.y-tracker.getTrail().y;
		
		double a = t2x*t2x+t2y*t2y;
		double b = 2*(-t1x*t2x-t1y*t2y);
		double c = t1x*t1x+t1y*t1y-tracker.getDistance()*tracker.getDistance();
		
		double p = b/a;
		double q = c/a;
		double lambda1 = -p/2.0+Math.sqrt(p*p/4.0-q);
		double lambda2 = -p/2.0-Math.sqrt(p*p/4.0-q);
		double lambda = lambda1;
		
		if (lambda > 1.0 || lambda < 0.0)
			lambda = lambda2;
		if ((lambda1 < 1.0 && lambda2 < 1.0) && (lambda1 > 0.0 && lambda2 > 0.0))
			lambda = Math.min(lambda1, lambda2);
				
		Point2D.Double back = new Point2D.Double(tracker.getTrail().x, tracker.getTrail().y);
		back.x += lambda * (localTemp1.x-tracker.getTrail().x);
		back.y += lambda * (localTemp1.y-tracker.getTrail().y);
			
		return back;
	}

}
