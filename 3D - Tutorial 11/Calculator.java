
public class Calculator {
	static double DrawX = 0, DrawY = 0, t;
	static double CalculatePositionX(double[] ViewFrom, double[] ViewTo, double x, double y, double z)
	{
		setStuff(ViewFrom, ViewTo, x, y, z);
		return DrawX;
	}

	static double CalculatePositionY(double[] ViewFrom, double[] ViewTo, double x, double y, double z)
	{
		setStuff(ViewFrom, ViewTo, x, y, z);
		return DrawY;		
	}
	
	static void setStuff(double[] ViewFrom, double[] ViewTo, double x, double y, double z)
	{
		Vector ViewVector = new Vector(ViewTo[0] - ViewFrom[0], ViewTo[1] - ViewFrom[1], ViewTo[2] - ViewFrom[2]);
		Vector DirectionVector = new Vector(1, 1, 1);
		Vector PlaneVector1 = ViewVector.CrossProduct(DirectionVector);
		Vector PlaneVector2 = ViewVector.CrossProduct(PlaneVector1);	
		
		Vector ViewToPoint = new Vector(x - ViewFrom[0], y - ViewFrom[1], z - ViewFrom[2]);
		
			t = (ViewVector.x * ViewTo[0] + ViewVector.y*ViewTo[1] + ViewVector.z*ViewTo[2]
			   	-  (ViewVector.x * ViewFrom[0] + ViewVector.y*ViewFrom[1] + ViewVector.z*ViewFrom[2]))
				/  (ViewVector.x * ViewToPoint.x + ViewVector.y*ViewToPoint.y + ViewVector.z*ViewToPoint.z);
		
		x = ViewFrom[0] + ViewToPoint.x * t;
		y = ViewFrom[1] + ViewToPoint.y * t;
		z = ViewFrom[2] + ViewToPoint.z * t;
		
		if(t > 0)
		{
			DrawX = PlaneVector2.x * x + PlaneVector2.y * y + PlaneVector2.z * z;
			DrawY = PlaneVector1.x * x + PlaneVector1.y * y + PlaneVector1.z * z;
		}
	}
}
