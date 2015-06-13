import java.awt.Color;

public class Prism {
	double x, y, z, width, length, height, rotation = Math.PI*0.75;
	double[] RotAdd = new double[6];
	Color c;
	double x1, x2, x3, x4, x5, x6, y1, y2, y3, y4, y5, y6;
	DPolygon[] Polys = new DPolygon[5];
	double[] angle;
	
	public Prism(double x, double y, double z, double width, double length, double height, Color c)
	{
		Polys[0] = new DPolygon(new double[]{x, x+width, x+width, x}, new double[]{y, y, y+length, y+length},  new double[]{z, z, z, z}, c, false);
		Screen.DPolygons.add(Polys[0]);
		Polys[1] = new DPolygon(new double[]{x, x, x+width, x+width}, new double[]{y, y, y, y},  new double[]{z, z+height, z+height, z}, c, false);
		Screen.DPolygons.add(Polys[1]);
		Polys[2] = new DPolygon(new double[]{x+width, x+width, x+width}, new double[]{y, y, y+length},  new double[]{z, z+height, z+height}, c, false);
		Screen.DPolygons.add(Polys[2]);
		Polys[3] = new DPolygon(new double[]{x, x, x+width, x+width}, new double[]{y+length, y+length, y+length, y+length},  new double[]{z, z+height, z+height, z}, c, false);
		Screen.DPolygons.add(Polys[3]);
		Polys[4] = new DPolygon(new double[]{x, x, x}, new double[]{y, y, y+length},  new double[]{z, z+height, z+height}, c, false);
		Screen.DPolygons.add(Polys[4]);
		
		this.c = c;
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.length = length;
		this.height = height;
		
		setRotAdd();
		updatePoly();
	}
	
	void setRotAdd()
	{
		angle = new double[6];
		
		double xdif = - width + 0.00001;
		double ydif = - length + 0.00001;
		
		angle[0] = Math.atan(ydif/xdif);
		
		if(xdif<0)
			angle[0] += Math.PI;
		
/////////
		xdif = width + 0.00001;
		ydif = - length + 0.00001;
		
		angle[1] = Math.atan(ydif/xdif);
		
		if(xdif<0)
			angle[1] += Math.PI;
/////////
		xdif = width + 0.00001;
		ydif = length + 0.00001;
		
		angle[2] = Math.atan(ydif/xdif);
		
		if(xdif<0)
			angle[2] += Math.PI;
		
/////////
		xdif = - width + 0.00001;
		ydif = length + 0.00001;
		
		angle[3] = Math.atan(ydif/xdif);
		
		if(xdif<0)
			angle[3] += Math.PI;		
		
/////////
		xdif = - width + 0.00001;
		ydif = 0.00001;
		
		angle[4] = Math.atan(ydif/xdif);
		
		if(xdif<0)
			angle[4] += Math.PI;		

/////////
		xdif = + width + 0.00001;
		ydif = 0.00001;
		
		angle[5] = Math.atan(ydif/xdif);
		
		if(xdif<0)
			angle[5] += Math.PI;		
		
		RotAdd[0] = angle[0] + 0.25 * Math.PI;
		RotAdd[1] =	angle[1] + 0.25 * Math.PI;
		RotAdd[2] = angle[2] + 0.25 * Math.PI;
		RotAdd[3] = angle[3] + 0.25 * Math.PI;
		RotAdd[4] = angle[4] + 0.25 * Math.PI;
		RotAdd[5] = angle[5] + 0.25 * Math.PI;
	}
	
	void UpdateDirection(double toX, double toY)
	{
		double xdif = toX - (x + width/2) + 0.00001;
		double ydif = toY - (y + length/2) + 0.00001;
		
		double anglet = Math.atan(ydif/xdif) + 0.75 * Math.PI;

		if(xdif<0)
			anglet += Math.PI;

		rotation = anglet;
		updatePoly();		
	}

	void updatePoly()
	{
		for(int i = 0; i < 5; i++)
		{
			Screen.DPolygons.add(Polys[i]);
			Screen.DPolygons.remove(Polys[i]);
		}
		
		double radius = Math.sqrt(width*width + length*length);
		double innerRadius = Math.sqrt(width*width);
		
			   x1 = x+width*0.5+radius*0.5*Math.cos(rotation + RotAdd[0]);
			   x2 = x+width*0.5+radius*0.5*Math.cos(rotation + RotAdd[1]);
			   x3 = x+width*0.5+radius*0.5*Math.cos(rotation + RotAdd[2]);
			   x4 = x+width*0.5+radius*0.5*Math.cos(rotation + RotAdd[3]);
			   x5 = x+width*0.5+innerRadius*0.5*Math.cos(rotation + RotAdd[4]);
			   x6 = x+width*0.5+innerRadius*0.5*Math.cos(rotation + RotAdd[5]);
			   
			   y1 = y+length*0.5+radius*0.5*Math.sin(rotation + RotAdd[0]);
			   y2 = y+length*0.5+radius*0.5*Math.sin(rotation + RotAdd[1]);
			   y3 = y+length*0.5+radius*0.5*Math.sin(rotation + RotAdd[2]);
			   y4 = y+length*0.5+radius*0.5*Math.sin(rotation + RotAdd[3]);
			   y5 = y+length*0.5+innerRadius*0.5*Math.sin(rotation + RotAdd[4]);
			   y6 = y+length*0.5+innerRadius*0.5*Math.sin(rotation + RotAdd[5]);
   
		Polys[0].x = new double[]{x1, x2, x3, x4};
		Polys[0].y = new double[]{y1, y2, y3, y4};
		Polys[0].z = new double[]{z, z, z, z};
			   
		Polys[1].x = new double[]{x1, x5, x6, x2};
		Polys[1].y = new double[]{y1, y5, y6, y2};
		Polys[1].z = new double[]{z, z+height, z+height, z};

		Polys[2].x = new double[]{x3, x2, x6};
		Polys[2].y = new double[]{y3, y2, y6};
		Polys[2].z = new double[]{z, z, z+height};

		Polys[3].x = new double[]{x3, x6, x5, x4};
		Polys[3].y = new double[]{y3, y6, y5, y4};
		Polys[3].z = new double[]{z, z+height, z+height, z};

		Polys[4].x = new double[]{x1, x4, x5};
		Polys[4].y = new double[]{y1, y4, y5};
		Polys[4].z = new double[]{z, z, z+height};
		
	}

	void removePrism()
	{
		for(int i = 0; i < 5; i ++)
			Screen.DPolygons.remove(Polys[i]);
		Screen.Prisms.remove(this);
	}
}
