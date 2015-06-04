import java.awt.Graphics;
import javax.swing.JPanel;

public class Screen extends JPanel{
	public void paintComponent(Graphics g)
	{
		g.fillOval(10, 10, 500, 500);
	}
}
