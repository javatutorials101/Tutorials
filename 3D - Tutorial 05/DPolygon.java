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
		Screen.DrawablePolygons[Screen.NumberOfPolygons] = new PolygonObject(x, y, c);
	}
}
