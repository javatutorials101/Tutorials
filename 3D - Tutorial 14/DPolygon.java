import java.awt.Color;
import java.awt.Polygon;


public class DPolygon {
	Color c;
	double[] x, y, z;
	int poly = 0;
	
	public DPolygon(double[] x, double[] y, double[] z, Color c)
	{
		Screen.NumberOf3DPolygons++;
		this.x = x;
		this.y = y;
		this.z = z;
		this.c = c;
		createPolygon();
	}
	
	void createPolygon()
	{
		poly = Screen.NumberOfPolygons;
 		Screen.DrawablePolygons[Screen.NumberOfPolygons] = new PolygonObject(new double[]{}, new double[]{}, c);
 		updatePolygon();
	}
	
	void updatePolygon()
	{
		double dx = - 50 * Calculator.CalculatePositionX(Screen.ViewFrom, Screen.ViewTo, Screen.ViewTo[0], Screen.ViewTo[1], Screen.ViewTo[2]);
		double dy = - 50 * Calculator.CalculatePositionY(Screen.ViewFrom, Screen.ViewTo, Screen.ViewTo[0], Screen.ViewTo[1], Screen.ViewTo[2]);
		double[] newX = new double[x.length];
		double[] newY = new double[x.length];
		
		for(int i = 0; i < x.length; i++)
		{
			newX[i] = dx + DDDTutorial.ScreenSize.getWidth()/2 + 50 * Calculator.CalculatePositionX(Screen.ViewFrom, Screen.ViewTo, x[i], y[i], z[i]);
			newY[i] = dy + DDDTutorial.ScreenSize.getHeight()/2 + 50 * Calculator.CalculatePositionY(Screen.ViewFrom, Screen.ViewTo, x[i], y[i], z[i]);
		}
		
 		Screen.DrawablePolygons[poly] = new PolygonObject(newX, newY, c);
 		Screen.DrawablePolygons[poly].AvgDist = GetDist();
 		Screen.NumberOfPolygons --;
	}
	
	double GetDist()
	{
			double total = 0;
			for(int i = 0; i < x.length; i++)
				total += GetDistanceToP(i);
			return total / x.length;
	}
	
	double GetDistanceToP(int i)
	{
		
		return Math.sqrt(
				(Screen.ViewFrom[0] - x[i])*(Screen.ViewFrom[0] - x[i]) +
				(Screen.ViewFrom[1] - y[i])*(Screen.ViewFrom[1] - y[i]) +
				(Screen.ViewFrom[2] - z[i])*(Screen.ViewFrom[2] - z[i]));
	}
}
