import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Screen extends JPanel{
	double[] ViewFrom = new double[] {10, 10, 10};
	double[] ViewTo = new double[] {0, 0, 0};
	
	DPolygon DPoly1 = new DPolygon(new int[]{2, 4, 2}, new int[]{2, 4, 6},  new int[]{5, 5, 5}, Color.black);
	public Screen()
	{

	}
	
	public void paintComponent(Graphics g)
	{
	
	}
}
