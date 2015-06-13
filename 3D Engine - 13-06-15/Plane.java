public class Plane {
	Vector V1, V2, NV;
	double[] P = new double[3];
	public Plane(DPolygon DP)
	{
		P[0] = DP.x[0]; 
		P[1] = DP.y[0]; 
		P[2] = DP.z[0]; 
		
		V1 = new Vector(DP.x[1] - DP.x[0], 
		        		DP.y[1] - DP.y[0], 
		        		DP.z[1] - DP.z[0]);

		V2 = new Vector(DP.x[2] - DP.x[0], 
		        		DP.y[2] - DP.y[0], 
		        		DP.z[2] - DP.z[0]);
		
		NV = V1.CrossProduct(V2);
	}
	
	public Plane(Vector VE1, Vector VE2, double[] Z)
	{
		P = Z;
		
		V1 = VE1;
		
		V2 = VE2;
		
		NV = V1.CrossProduct(V2);
	}
}
