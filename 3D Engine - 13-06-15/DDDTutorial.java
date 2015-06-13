import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class DDDTutorial 
{	
	static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static JTextField TF;
	
	public static void main(String[] args)
	{
		JFrame F = new JFrame();
		Screen ScreenObject = new Screen();
		F.add(ScreenObject);
		F.setUndecorated(true);
	    F.setSize(ScreenSize);
		F.setVisible(true);
		F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
