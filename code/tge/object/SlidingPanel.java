package tge.object;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class SlidingPanel extends BackgroundPanel {

	public static final int NOMOVE=4;
	public static final int UP=1;
	public static final int DOWN=7;
	public static final int RIGHT=2;
	public static final int LEFT=0;
	
	private int size;
	private int direction;
	private double speed=1.5;
	private double posX=0;
	private double posY=0;
	
	
	
	public void setSpeed(double speed) {
		this.speed=speed;
	}

	public SlidingPanel(String filepath,int size,int direction) {
		super(filepath);
		this.size=size;
		this.direction=direction;
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	}

	
	// Override the paintComponent method to draw the image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
        	for(int i=(int) posX;i< this.getWidth();i+=size)
            	for(int j=(int) posY;j<this.getHeight();j+=size)
            		g.drawImage(backgroundImage, i,j, size,size, this);
        }
        
        //update position;
        posX+=speed*(direction%3-1);
        posY+=speed*(direction/3-1);
        posX=(posX+size)%size-size;
        posY=(posY+size)%size-size;
    }
}
