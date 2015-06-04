
public class Vector {
	double x = 0, y = 0, z = 0;
	public Vector(double x, double y, double z)
	{
		double length = Math.sqrt(x * x + y * y + z * z);
		if(length>0)
		{
			this.x = x/length;
			this.y = y/length;
			this.z = z/length;
		}
	}
	
	Vector CrossProduct(Vector V)
	{
		Vector CrossVector = new Vector(
				y * V.z - z * V.y,
				z * V.x - x * V.z,
				x * V.y - y * V.x);
		return CrossVector;
	}
}
