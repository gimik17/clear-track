

import java.awt.geom.Point2D;
import java.io.Serializable;

public interface CalculationMethod extends Serializable{
	
	public abstract  Point2D.Double getNewTrailPoint(Point2D.Double incrementLead, Trackable tracker);
	

}
