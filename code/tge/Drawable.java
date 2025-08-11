package tge;

import java.awt.Graphics;
import java.util.Comparator;

public interface Drawable {
	public void draw(Graphics g);
	public double Zindex();
	

	public class DrawComparator implements Comparator<Drawable> {
	    @Override
	    public int compare(Drawable o1, Drawable o2) {
	        return Double.compare(o1.Zindex(), o2.Zindex());
	    }
	}
}
