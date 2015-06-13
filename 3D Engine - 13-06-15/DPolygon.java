import java.awt.Color;

public class DPolygon {
	Color c;
	double[] x, y, z;
	boolean draw = true, seeThrough = false;
	double[] CalcPos, newX, newY;
	PolygonObject DrawablePolygon;
	double AvgDist;
	
	public DPolygon(double[] x, double[] y,  double[] z, Color c, boolean seeThrough)
	{
		this.x = x;
		this.y = y;
		this.z = z;		
		this.c = c;
		this.seeThrough = seeThrough; 
		createPolygon();
	}
	
	void createPolygon()
	{
		DrawablePolygon = new PolygonObject(new double[x.length], new double[x.length], c, Screen.DPolygons.size(), seeThrough);
	}
	
	void updatePolygon()
	{		
		newX = new double[x.length];
		newY = new double[x.length];
		draw = true;
		for(int i=0; i<x.length; i++)
		{
			CalcPos = Calculator.CalculatePositionP(Screen.ViewFrom, Screen.ViewTo, x[i], y[i], z[i]);
			newX[i] = (DDDTutorial.ScreenSize.getWidth()/2 - Calculator.CalcFocusPos[0]) + CalcPos[0] * Screen.zoom;
			newY[i] = (DDDTutorial.ScreenSize.getHeight()/2 - Calculator.CalcFocusPos[1]) + CalcPos[1] * Screen.zoom;			
			if(Calculator.t < 0)
				draw = false;
		}
		
		calcLighting();
		
		DrawablePolygon.draw = draw;
		DrawablePolygon.updatePolygon(newX, newY);
		AvgDist = GetDist();
	}
	
	void calcLighting()
	{
		Plane lightingPlane = new Plane(this);
		double angle = Math.acos(((lightingPlane.NV.x * Screen.LightDir[0]) + 
			  (lightingPlane.NV.y * Screen.LightDir[1]) + (lightingPlane.NV.z * Screen.LightDir[2]))
			  /(Math.sqrt(Screen.LightDir[0] * Screen.LightDir[0] + Screen.LightDir[1] * Screen.LightDir[1] + Screen.LightDir[2] * Screen.LightDir[2])));
		
		DrawablePolygon.lighting = 0.2 + 1 - Math.sqrt(Math.toDegrees(angle)/180);

		if(DrawablePolygon.lighting > 1)
			DrawablePolygon.lighting = 1;
		if(DrawablePolygon.lighting < 0)
			DrawablePolygon.lighting = 0;
	}
		
	double GetDist()
	{
		double total = 0;
		for(int i=0; i<x.length; i++)
			total += GetDistanceToP(i);
		return total / x.length;
	}
	
	double GetDistanceToP(int i)
	{
		return Math.sqrt((Screen.ViewFrom[0]-x[i])*(Screen.ViewFrom[0]-x[i]) + 
						 (Screen.ViewFrom[1]-y[i])*(Screen.ViewFrom[1]-y[i]) +
						 (Screen.ViewFrom[2]-z[i])*(Screen.ViewFrom[2]-z[i]));
	}
}
