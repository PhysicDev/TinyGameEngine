package tge;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BasicListener implements MouseListener{

	protected int releasedX;
	protected int releasedY;
	protected int pressedX;
	protected int pressedY;
	protected boolean[] mouseButton=new boolean[] {false,false,false,false,false,false,false};
	
	public int getRX(){
		return releasedX;}
	public int getRY(){
		return releasedY;}
	
	public int getPX() {
		return pressedX;}
	public int getPY() {
		return pressedY;}
	

	public boolean isPressed(int mb) {
		return mouseButton[mb];}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressedX=e.getX();
		pressedY=e.getY();
		mouseButton[e.getButton()]=true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		releasedX=e.getX();
		releasedY=e.getY();
		mouseButton[e.getButton()]=false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}
