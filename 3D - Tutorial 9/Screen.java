import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Screen extends JPanel{
	double SleepTime = 1000/30, lastRefresh = 0;
	static double[] ViewFrom = new double[] {10, 10, 10};
	static double[] ViewTo = new double[] {5, 0, 0};
	static int NumberOfPolygons = 0;
	static PolygonObject[] DrawablePolygons = new PolygonObject[100];
	
	DPolygon DPoly1 = new DPolygon(new double[]{2, 4, 2}, new double[]{2, 4, 6},  new double[]{5, 5, 5}, Color.black);
	public Screen()
	{

	}
	
	public void paintComponent(Graphics g)
	{
		g.clearRect(0, 0, 2000, 1200);
		g.drawString(System.currentTimeMillis() + "", 20, 20);
		for(int i = 0; i < NumberOfPolygons; i++)
			DrawablePolygons[i].drawPolygon(g);
		SleepAndRefresh();
	}
	
	void SleepAndRefresh()
	{
		while(true)
		{
			if(System.currentTimeMillis() - lastRefresh > SleepTime)
			{
				lastRefresh = System.currentTimeMillis();
				repaint();
				break;
			}
			else
			{
				try 
				{
					Thread.sleep((long)(System.currentTimeMillis() - lastRefresh));
				} 
				catch (Exception e) 
				{

				}
			}
		}
	}
}
