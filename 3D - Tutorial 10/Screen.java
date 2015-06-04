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
	
	public Screen()
	{
		addKeyListener(this);
		setFocusable(true);
		DPolygons[0] = new DPolygon(new double[]{0, 2, 2, 0}, new double[]{0, 0, 2, 2},  new double[]{0, 0, 0, 0}, Color.black);
		DPolygons[1] = new DPolygon(new double[]{0, 2, 2, 0}, new double[]{0, 0, 2, 2},  new double[]{3, 3, 3, 3	}, Color.black);
	}
	
	public void paintComponent(Graphics g)
	{
		g.clearRect(0, 0, 2000, 1200);
		g.drawString(System.currentTimeMillis() + "", 20, 20);

		for(int i = 0; i < NumberOf3DPolygons; i++)
			DPolygons[i].updatePolygon();

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
