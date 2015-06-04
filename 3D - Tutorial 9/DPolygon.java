import java.awt.Color;
import java.awt.Polygon;


public class DPolygon {
	Color c;
	double[] x, y, z;
	
	public DPolygon(double[] x, double[] y, double[] z, Color c)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.c = c;
		createPolygon();
	}
	
	void createPolygon()
	{
		double[] newX = new double[x.length];
		double	[] newY = new double[x.length];
		
		for(int i = 0; i < x.length; i++)
		{
			newX[i] = 200 * Calculator.CalculatePositionX(Screen.ViewFrom, Screen.ViewTo, x[i], y[i], z[i]);
			newY[i] = 200 * Calculator.CalculatePositionY(Screen.ViewFrom, Screen.ViewTo, x[i], y[i], z[i]);
		}
		
 		Screen.DrawablePolygons[Screen.NumberOfPolygons] = new PolygonObject(newX, newY, c);
	}
}
