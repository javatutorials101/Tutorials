import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Screen extends JPanel{
	PolygonObject Poly1;
	
	public Screen()
	{
		Poly1 = new PolygonObject(new int[]{10, 200, 10}, new int[]{10, 200, 400}, Color.black);
	}
	
	public void paintComponent(Graphics g)
	{
		Poly1.drawPolygon(g);
	}
}
