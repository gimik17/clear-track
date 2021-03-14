

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class CalculationMethodHauska implements CalculationMethod {

	@Override
	public Double getNewTrailPoint(Double incrementLead, Trackable tracker) {
		Point2D.Double localP1=new Point2D.Double(tracker.getLead().x+incrementLead.x,tracker.getLead().y+incrementLead.y);
		
		//neues trail berechnen
		Point2D.Double localTemp1= Utils.getDistance(tracker.getTrail(), tracker.getLead());
		double p = 2*(localTemp1.x*(tracker.getTrail().x-localP1.x)+localTemp1.y*(tracker.getTrail().y-localP1.y))/(localTemp1.x*localTemp1.x+localTemp1.y*localTemp1.y);
		double q = ((tracker.getTrail().x-localP1.x)*(tracker.getTrail().x-localP1.x)+(tracker.getTrail().y-localP1.y)*(tracker.getTrail().y-localP1.y)-tracker.getDistance()*tracker.getDistance())/(localTemp1.x*localTemp1.x+localTemp1.y*localTemp1.y);
		double lambda1 = -p/2.0+Math.sqrt(p*p/4.0-q);
		double lambda2 = -p/2.0-Math.sqrt(p*p/4.0-q);
		double lambda = lambda1;
		
		if (lambda > 1.0 || lambda < 0.0)
			lambda = lambda2;
		if ((lambda1 < 1.0 && lambda2 < 1.0) && (lambda1 > 0.0 && lambda2 > 0.0))
			lambda = Math.min(lambda1, lambda2);
				
		Point2D.Double back = new Point2D.Double(tracker.getTrail().x, tracker.getTrail().y);
		back.x += lambda * localTemp1.x;
		back.y += lambda * localTemp1.y;
			
		return back;
	}

}
