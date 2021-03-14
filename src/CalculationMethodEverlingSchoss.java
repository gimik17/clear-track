

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class CalculationMethodEverlingSchoss implements CalculationMethod {

	@Override
	public Double getNewTrailPoint(Double incrementLead, Trackable tracker) {
		Point2D.Double localP1=new Point2D.Double(tracker.getLead().x+incrementLead.x,tracker.getLead().y+incrementLead.y);
		
		//allgemeine Werte
		double h = Math.sqrt(incrementLead.x*incrementLead.x+incrementLead.y*incrementLead.y);
		double xs = incrementLead.x/h;
		double ys = incrementLead.y/h;
		
		//Werte für Runge-Kutta-Verfahren
		double k1 =getf(tracker.getDistance(),tracker.getLead().x,tracker.getLead().y,xs,ys,tracker.getTrail().x,tracker.getTrail().y)*h;
		double l1 =getg(tracker.getDistance(),tracker.getLead().x,tracker.getLead().y,xs,ys,tracker.getTrail().x,tracker.getTrail().y)*h;
		double k2 =getf(tracker.getDistance(),tracker.getLead().x+incrementLead.x*0.5,tracker.getLead().y+incrementLead.y*0.5,xs,ys,tracker.getTrail().x+k1*0.5,tracker.getTrail().y+l1*0.5)*h;
		double l2 =getg(tracker.getDistance(),tracker.getLead().x+incrementLead.x*0.5,tracker.getLead().y+incrementLead.y*0.5,xs,ys,tracker.getTrail().x+k1*0.5,tracker.getTrail().y+l1*0.5)*h;
		double k3 =getf(tracker.getDistance(),tracker.getLead().x+incrementLead.x*0.5,tracker.getLead().y+incrementLead.y*0.5,xs,ys,tracker.getTrail().x+k2*0.5,tracker.getTrail().y+l2*0.5)*h;
		double l3 =getg(tracker.getDistance(),tracker.getLead().x+incrementLead.x*0.5,tracker.getLead().y+incrementLead.y*0.5,xs,ys,tracker.getTrail().x+k2*0.5,tracker.getTrail().y+l2*0.5)*h;
		double k4 =getf(tracker.getDistance(),localP1.x,localP1.y,xs,ys,tracker.getTrail().x+k3,tracker.getTrail().y+l3)*h;
		double l4 =getg(tracker.getDistance(),localP1.x,localP1.y,xs,ys,tracker.getTrail().x+k3,tracker.getTrail().y+l3)*h;
		
		//neues trail berechnen		
		Point2D.Double back = new Point2D.Double(tracker.getTrail().x, tracker.getTrail().y);
		back.x += 1.0/6.0*(k1+2.0*k2+2.0*k3+k4);
		back.y += 1.0/6.0*(l1+2.0*l2+2.0*l3+l4);
		
		return back;
	}
	private double getf(double a, double x, double y, double xs, double ys, double u, double v)
	{return (x-u)/(a*a)*((x-u)*xs+(y-v)*ys);}
	
	private double getg(double a, double x, double y, double xs, double ys, double u, double v)
	{return (y-v)/(a*a)*((x-u)*xs+(y-v)*ys);}
}
