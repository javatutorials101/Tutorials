import java.awt.Toolkit;
import javax.swing.JFrame;

public class DDDTutorial extends JFrame{
	static JFrame F = new DDDTutorial();
	Screen ScreenObject = new Screen();
	
	public DDDTutorial()
	{
		add(ScreenObject);
		setUndecorated(true);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		
	}
}
