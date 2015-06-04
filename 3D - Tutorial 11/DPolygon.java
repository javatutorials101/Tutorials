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
		double total = 0;
		double[] newX = new double[x.length];
		double	[] newY = new double[x.length];
		
		for(int i = 0; i < x.length; i++)
		{
			newX[i] = 500 + 50 * Calculator.CalculatePositionX(Screen.ViewFrom, Screen.ViewTo, x[i], y[i], z[i]);
			newY[i] = 500 + 50 * Calculator.CalculatePositionY(Screen.ViewFrom, Screen.ViewTo, x[i], y[i], z[i]);
			total += Calculator.t;
		}
		
		poly = Screen.NumberOfPolygons;
 		Screen.DrawablePolygons[Screen.NumberOfPolygons] = new PolygonObject(newX, newY, c);
 		Screen.DrawablePolygons[poly].AvgDist = total / x.length;
	}
	
	void updatePolygon()
	{
		double[] newX = new double[x.length];
		double	[] newY = new double[x.length];
		
		for(int i = 0; i < x.length; i++)
		{
			newX[i] = 500 + 50 * Calculator.CalculatePositionX(Screen.ViewFrom, Screen.ViewTo, x[i], y[i], z[i]);
			newY[i] = 500 + 50 * Calculator.CalculatePositionY(Screen.ViewFrom, Screen.ViewTo, x[i], y[i], z[i]);
		}
		
 		Screen.DrawablePolygons[poly] = new PolygonObject(newX, newY, c);
 		Screen.NumberOfPolygons --;
	}
}
