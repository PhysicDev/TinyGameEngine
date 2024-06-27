package tge;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public abstract class Sim extends JPanel{
	
	public static final String GAME_SCREEN="game";
	
	protected HashMap<String,JPanel> menus=new HashMap<String,JPanel>();
	
	public void addMenu(JPanel menu,String id){
		if(id.equalsIgnoreCase(GAME_SCREEN))
			throw new IllegalArgumentException("illegal id : already used for the game_screen");
		menus.put(id, menu);
	}

	protected JFrame sim;
	private JPanel mainPanel;
	private CardLayout screenManager;
	
	protected static int FrameLimiterGraphic=60;
	protected static int FrameLimiterLogic=250;
	
	public int FrameGraph() {return FrameLimiterGraphic;}
	public int FrameLogic() {return FrameLimiterLogic;}
	
	protected static long Gframes=0;
	protected static long Lframes=0;
	
	public boolean paused=false;
	
	protected int mouseX;
	protected int mouseY;
	
	
	public int getMX() {
		return mouseX;}
	public int getMY() {
		return mouseY;}
	
	
	private Point mousePosition() {
		Point pos=MouseInfo.getPointerInfo().getLocation().getLocation();
		SwingUtilities.convertPointFromScreen(pos, this);
		return pos;
	}
	
	public void StartLoop() {
		GameLoop gameloop=new GameLoop();
		gameloop.parent=this;
		gameloop.start();
		
		while(true) {
			long time=System.currentTimeMillis();
			if(!paused)
				GraphicLoop();
			try {
				long waitTime=(long) (1000d/(double)FrameLimiterGraphic-System.currentTimeMillis()+time);
				if(waitTime>0)
					Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public JFrame setupFrame() {
		sim=new JFrame();
		sim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sim.setSize(800,600);
		return setupFrame(sim);
	}
	
	public JFrame setupFrame(JFrame frame) {
		sim=frame;
		sim.add(this);
		screenManager = new CardLayout();
		mainPanel = new JPanel(screenManager);
		
		mainPanel.add(this,GAME_SCREEN);
		for(String key:menus.keySet())
			mainPanel.add(menus.get(key),key);
		
        sim.add(mainPanel, BorderLayout.CENTER);
        
        screenManager.show(mainPanel, GAME_SCREEN);
		return sim;
	}
	
	public void loadMenu(String menu) {
		paused=menu!=GAME_SCREEN;
		screenManager.show(mainPanel, menu);
	}
	
	
	public class GameLoop extends Thread {
		 long timeNano=System.nanoTime();
		 Sim parent;
		 public void run() {
			  while(true) {
				long time=System.currentTimeMillis();
				if(!parent.paused)
					loop();
				try {
					long waitTime=(long) (1000d/(double)FrameLimiterLogic-System.currentTimeMillis()+time);
					if(waitTime>0)
						Thread.sleep(waitTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			  }
		 }
		
		public int loop() {
			return (parent.LogicLoop());
		}
	}
	
	public int LogicLoop() {
		Lframes++;
		return 1;//success
	}
	
	public int GraphicLoop(){
		Gframes++;
		Point pos=mousePosition();
		mouseX=pos.x;
		mouseY=pos.y;
		sim.repaint();
		return 1;//success
	}

	public static int getFrameLimiterGraphic() {
		return FrameLimiterGraphic;
	}

	public static void setFrameLimiterGraphic(int frameLimiterGraphic) {
		FrameLimiterGraphic = frameLimiterGraphic;
	}

	public static int getFrameLimiterLogic() {
		return FrameLimiterLogic;
	}

	public static void setFrameLimiterLogic(int frameLimiterLogic) {
		FrameLimiterLogic = frameLimiterLogic;
	}
	
	protected void frameDebug(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,0,(int) (Lframes%this.getWidth()),5);
		g.fillRect(0,this.getHeight()-5,(int) (Gframes%this.getWidth()),5);
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	}
}
