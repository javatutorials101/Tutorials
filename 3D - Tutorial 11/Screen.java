import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Screen extends JPanel implements KeyListener{
	double SleepTime = 1000/30, lastRefresh = 0;
	static double[] ViewFrom = new double[] {10, 10, 10};
	static double[] ViewTo = new double[] {5, 0, 0};
	static int NumberOfPolygons = 0, NumberOf3DPolygons = 0;
	static PolygonObject[] DrawablePolygons = new PolygonObject[100];
	static DPolygon[] DPolygons = new DPolygon[100];
	int[] NewOrder;
	
	public Screen()
	{
		addKeyListener(this);
		setFocusable(true);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0, 2, 2, 0}, new double[]{0, 0, 2, 2},  new double[]{0, 0, 0, 0}, Color.gray);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0, 2, 2, 0}, new double[]{0, 0, 2, 2},  new double[]{3, 3, 3, 3}, Color.gray);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0, 2, 2, 0}, new double[]{0, 0, 0, 0},  new double[]{0, 0, 3, 3}, Color.gray);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0, 2, 2, 0}, new double[]{2, 2, 2, 2},  new double[]{0, 0, 3, 3}, Color.gray);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0, 0, 0, 0}, new double[]{0, 2, 2, 0},  new double[]{0, 0, 3, 3}, Color.gray);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{2, 2, 2, 2}, new double[]{0, 2, 2, 0},  new double[]{0, 0, 3, 3}, Color.gray);
	}
	
	public void paintComponent(Graphics g)
	{
		g.clearRect(0, 0, 2000, 1200);
		g.drawString(System.currentTimeMillis() + "", 20, 20);

		for(int i = 0; i < NumberOf3DPolygons; i++)
			DPolygons[i].updatePolygon();
		
		setOrder();

		for(int i = 0; i < NumberOfPolygons; i++)
			DrawablePolygons[NewOrder[i]].drawPolygon(g);
		SleepAndRefresh();
	}
	
	void setOrder()
	{
		double[] k = new double[NumberOfPolygons];
		NewOrder = new int[NumberOfPolygons];
		
		for(int i = 0; i < NumberOfPolygons; i++)
		{
			k[i] = DrawablePolygons[i].AvgDist;	
			NewOrder[i] = i;
		}
		
	    double temp;
	    int tempr;	    
		for (int a = 0; a < k.length-1; a++)
			for (int b = 0; b < k.length-1; b++)
				if(k[b] < k[b + 1])
				{
					temp = k[b];
					tempr = NewOrder[b];
					NewOrder[b] = NewOrder[b + 1];
					k[b] = k[b + 1];
					   
					NewOrder[b + 1] = tempr;
					k[b + 1] = temp;
				}
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

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			ViewFrom[0] --;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			ViewFrom[0] ++;
		if(e.getKeyCode() == KeyEvent.VK_UP)
			ViewFrom[1] --;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			ViewFrom[0] ++;
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
