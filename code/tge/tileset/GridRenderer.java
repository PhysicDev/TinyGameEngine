package tge.tileset;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.function.Function;

import javax.swing.JComponent;

public abstract class GridRenderer<E> {
	
	protected int bound_lowX=0,bound_lowY=0,bound_highX=Integer.MAX_VALUE,bound_highY=Integer.MAX_VALUE;
	
	public void setBound(JComponent c) {
		bound_lowX=0;
		bound_lowY=0;
		bound_highX=c.getWidth();
		bound_highY=c.getHeight();
	}
	
	public void setBound(int lx,int ly,int hx,int hy) {
		bound_lowX=lx;
		bound_lowY=ly;
		bound_highX=hx;
		bound_highY=hy;
	}
	
	public static <T, R> Function<T, R> toFunction(HashMap<T, R> map) {
	    // Return a function that applies the hashmap's get method
	    return key -> map.get(key);   
	}
	public abstract void draw(Grid<E> grid,Graphics g);
}
