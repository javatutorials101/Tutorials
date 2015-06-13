import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Screen extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
	
	//ArrayList of all the 3D polygons - each 3D polygon has a 2D 'PolygonObject' inside called 'DrawablePolygon'
	static ArrayList<DPolygon> DPolygons = new ArrayList<DPolygon>();
	
	static ArrayList<Cube> Cubes = new ArrayList<Cube>();
	static ArrayList<Prism> Prisms = new ArrayList<Prism>();
	static ArrayList<Pyramid> Pyramids = new ArrayList<Pyramid>();
	
	//The polygon that the mouse is currently over
	static PolygonObject PolygonOver = null;

	//Used for keeping mouse in center
	Robot r;

	static double[] ViewFrom = new double[] { 15, 5, 10},	
					ViewTo = new double[] {0, 0, 0},
					LightDir = new double[] {1, 1, 1};

	
	//The smaller the zoom the more zoomed out you are and visa versa, although altering too far from 1000 will make it look pretty weird
	static double zoom = 1000, MinZoom = 500, MaxZoom = 2500, MouseX = 0, MouseY = 0, MovementSpeed = 0.5;
	
	//FPS is a bit primitive, you can set the MaxFPS as high as u want
	double drawFPS = 0, MaxFPS = 1000, SleepTime = 1000.0/MaxFPS, LastRefresh = 0, StartTime = System.currentTimeMillis(), LastFPSCheck = 0, Checks = 0;
	//VertLook goes from 0.999 to -0.999, minus being looking down and + looking up, HorLook takes any number and goes round in radians
	//aimSight changes the size of the center-cross. The lower HorRotSpeed or VertRotSpeed, the faster the camera will rotate in those directions
	double VertLook = -0.9, HorLook = 0, aimSight = 4, HorRotSpeed = 900, VertRotSpeed = 2200, SunPos = 0;

	//will hold the order that the polygons in the ArrayList DPolygon should be drawn meaning DPolygon.get(NewOrder[0]) gets drawn first
	int[] NewOrder;

	static boolean OutLines = true;
	boolean[] Keys = new boolean[4];
	
	long repaintTime = 0;
	
	public Screen()
	{		
		this.addKeyListener(this);
		setFocusable(true);		
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		
		invisibleMouse();	
		new GenerateTerrain();
		Cubes.add(new Cube(0, -5, 0, 2, 2, 2, Color.red));
		Prisms.add(new Prism(6, -5, 0, 2, 2, 2, Color.green));
		Pyramids.add(new Pyramid(12, -5, 0, 2, 2, 2, Color.blue));
		Cubes.add(new Cube(18, -5, 0, 2, 2, 2, Color.red));
		Cubes.add(new Cube(20, -5, 0, 2, 2, 2, Color.red));
		Cubes.add(new Cube(22, -5, 0, 2, 2, 2, Color.red));
		Cubes.add(new Cube(20, -5, 2, 2, 2, 2, Color.red));
		Prisms.add(new Prism(18, -5, 2, 2, 2, 2, Color.green));
		Prisms.add(new Prism(22, -5, 2, 2, 2, 2, Color.green));
		Pyramids.add(new Pyramid(20, -5, 4, 2, 2, 2, Color.blue));
	}	
	
	public void paintComponent(Graphics g)
	{
		//Clear screen and draw background color
		g.setColor(new Color(140, 180, 180));
		g.fillRect(0, 0, (int)DDDTutorial.ScreenSize.getWidth(), (int)DDDTutorial.ScreenSize.getHeight());

		CameraMovement();
		
		//Calculated all that is general for this camera position
		Calculator.SetPrederterminedInfo();

		ControlSunAndLight();

		//Updates each polygon for this camera position
		for(int i = 0; i < DPolygons.size(); i++)
			DPolygons.get(i).updatePolygon();
		
		//rotate and update shape examples
		Cubes.get(0).rotation+=.01;
		Cubes.get(0).updatePoly();

		Prisms.get(0).rotation+=.01;
		Prisms.get(0).updatePoly();
		
		Pyramids.get(0).rotation+=.01;
		Pyramids.get(0).updatePoly();

		//Set drawing order so closest polygons gets drawn last
		setOrder();
			
		//Set the polygon that the mouse is currently over
		setPolygonOver();
			
		//draw polygons in the Order that is set by the 'setOrder' function
		for(int i = 0; i < NewOrder.length; i++)
			DPolygons.get(NewOrder[i]).DrawablePolygon.drawPolygon(g);
			
		//draw the cross in the center of the screen
		drawMouseAim(g);			
		
		//FPS display
		g.drawString("FPS: " + (int)drawFPS + " (Benchmark)", 40, 40);
		
//		repaintTime = System.currentTimeMillis() - repaintTime; 
//		System.out.println(repaintTime);
		SleepAndRefresh();
	}
	
	void setOrder()
	{
		double[] k = new double[DPolygons.size()];
		NewOrder = new int[DPolygons.size()];
		
		for(int i=0; i<DPolygons.size(); i++)
		{
			k[i] = DPolygons.get(i).AvgDist;
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
		
	void invisibleMouse()
	{
		 Toolkit toolkit = Toolkit.getDefaultToolkit();
		 BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT); 
		 Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0,0), "InvisibleCursor");        
		 setCursor(invisibleCursor);
	}
	
	void drawMouseAim(Graphics g)
	{
		g.setColor(Color.black);
		g.drawLine((int)(DDDTutorial.ScreenSize.getWidth()/2 - aimSight), (int)(DDDTutorial.ScreenSize.getHeight()/2), (int)(DDDTutorial.ScreenSize.getWidth()/2 + aimSight), (int)(DDDTutorial.ScreenSize.getHeight()/2));
		g.drawLine((int)(DDDTutorial.ScreenSize.getWidth()/2), (int)(DDDTutorial.ScreenSize.getHeight()/2 - aimSight), (int)(DDDTutorial.ScreenSize.getWidth()/2), (int)(DDDTutorial.ScreenSize.getHeight()/2 + aimSight));			
	}

	void SleepAndRefresh()
	{
		long timeSLU = (long) (System.currentTimeMillis() - LastRefresh); 

		Checks ++;			
		if(Checks >= 15)
		{
			drawFPS = Checks/((System.currentTimeMillis() - LastFPSCheck)/1000.0);
			LastFPSCheck = System.currentTimeMillis();
			Checks = 0;
		}
		
		if(timeSLU < 1000.0/MaxFPS)
		{
			try {
				Thread.sleep((long) (1000.0/MaxFPS - timeSLU));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
				
		LastRefresh = System.currentTimeMillis();
		
		repaint();
	}
	
	void ControlSunAndLight()
	{
		SunPos += 0.005;		
		double mapSize = GenerateTerrain.mapSize * GenerateTerrain.Size;
		LightDir[0] = mapSize/2 - (mapSize/2 + Math.cos(SunPos) * mapSize * 10);
		LightDir[1] = mapSize/2 - (mapSize/2 + Math.sin(SunPos) * mapSize * 10);
		LightDir[2] = -200;
	}
	
	void CameraMovement()
	{
		Vector ViewVector = new Vector(ViewTo[0] - ViewFrom[0], ViewTo[1] - ViewFrom[1], ViewTo[2] - ViewFrom[2]);
		double xMove = 0, yMove = 0, zMove = 0;
		Vector VerticalVector = new Vector (0, 0, 1);
		Vector SideViewVector = ViewVector.CrossProduct(VerticalVector);
		
		if(Keys[0])
		{
			xMove += ViewVector.x ;
			yMove += ViewVector.y ;
			zMove += ViewVector.z ;
		}

		if(Keys[2])
		{
			xMove -= ViewVector.x ;
			yMove -= ViewVector.y ;
			zMove -= ViewVector.z ;
		}
			
		if(Keys[1])
		{
			xMove += SideViewVector.x ;
			yMove += SideViewVector.y ;
			zMove += SideViewVector.z ;
		}

		if(Keys[3])
		{
			xMove -= SideViewVector.x ;
			yMove -= SideViewVector.y ;
			zMove -= SideViewVector.z ;
		}
		
		Vector MoveVector = new Vector(xMove, yMove, zMove);
		MoveTo(ViewFrom[0] + MoveVector.x * MovementSpeed, ViewFrom[1] + MoveVector.y * MovementSpeed, ViewFrom[2] + MoveVector.z * MovementSpeed);
	}

	void MoveTo(double x, double y, double z)
	{
		ViewFrom[0] = x;
		ViewFrom[1] = y;
		ViewFrom[2] = z;
		updateView();
	}

	void setPolygonOver()
	{
		PolygonOver = null;
		for(int i = NewOrder.length-1; i >= 0; i--)
			if(DPolygons.get(NewOrder[i]).DrawablePolygon.MouseOver() && DPolygons.get(NewOrder[i]).draw 
					&& DPolygons.get(NewOrder[i]).DrawablePolygon.visible)
			{
				PolygonOver = DPolygons.get(NewOrder[i]).DrawablePolygon;
				break;
			}
	}

	void MouseMovement(double NewMouseX, double NewMouseY)
	{		
			double difX = (NewMouseX - DDDTutorial.ScreenSize.getWidth()/2);
			double difY = (NewMouseY - DDDTutorial.ScreenSize.getHeight()/2);
			difY *= 6 - Math.abs(VertLook) * 5;
			VertLook -= difY  / VertRotSpeed;
			HorLook += difX / HorRotSpeed;
	
			if(VertLook>0.999)
				VertLook = 0.999;
	
			if(VertLook<-0.999)
				VertLook = -0.999;
			
			updateView();
	}
	
	void updateView()
	{
		double r = Math.sqrt(1 - (VertLook * VertLook));
		ViewTo[0] = ViewFrom[0] + r * Math.cos(HorLook);
		ViewTo[1] = ViewFrom[1] + r * Math.sin(HorLook);		
		ViewTo[2] = ViewFrom[2] + VertLook;
	}
	
	void CenterMouse() 
	{
			try {
				r = new Robot();
				r.mouseMove((int)DDDTutorial.ScreenSize.getWidth()/2, (int)DDDTutorial.ScreenSize.getHeight()/2);
			} catch (AWTException e) {
				e.printStackTrace();
			}
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W)
			Keys[0] = true;
		if(e.getKeyCode() == KeyEvent.VK_A)
			Keys[1] = true;
		if(e.getKeyCode() == KeyEvent.VK_S)
			Keys[2] = true;
		if(e.getKeyCode() == KeyEvent.VK_D)
			Keys[3] = true;
		if(e.getKeyCode() == KeyEvent.VK_O)
			OutLines = !OutLines;
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W)
			Keys[0] = false;
		if(e.getKeyCode() == KeyEvent.VK_A)
			Keys[1] = false;
		if(e.getKeyCode() == KeyEvent.VK_S)
			Keys[2] = false;
		if(e.getKeyCode() == KeyEvent.VK_D)
			Keys[3] = false;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void mouseDragged(MouseEvent arg0) {
		MouseMovement(arg0.getX(), arg0.getY());
		MouseX = arg0.getX();
		MouseY = arg0.getY();
		CenterMouse();
	}
	
	public void mouseMoved(MouseEvent arg0) {
		MouseMovement(arg0.getX(), arg0.getY());
		MouseX = arg0.getX();
		MouseY = arg0.getY();
		CenterMouse();
	}
	
	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON1)
			if(PolygonOver != null)
				PolygonOver.seeThrough = false;

		if(arg0.getButton() == MouseEvent.BUTTON3)
			if(PolygonOver != null)
				PolygonOver.seeThrough = true;
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void mouseWheelMoved(MouseWheelEvent arg0) {
		if(arg0.getUnitsToScroll()>0)
		{
			if(zoom > MinZoom)
				zoom -= 25 * arg0.getUnitsToScroll();
		}
		else
		{
			if(zoom < MaxZoom)
				zoom -= 25 * arg0.getUnitsToScroll();
		}	
	}
}
