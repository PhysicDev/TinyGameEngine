package tge.tileset;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.Predicate;

import tge.Utilities;

public class DualGridRenderer<E> implements GridRenderer<E> {

	private BufferedImage[] tileset;
	
	private double OffX=0.5;
	private double OffY=0.5;
	
	private E borderValue=null;
	
	private Predicate<E> testFunction;
	
	public void setPredicate(Predicate<E> testFun) {
		testFunction=testFun;
	}
	
	public boolean testPredicate(E value) {
		return testFunction.test(value);
	}
	
	public DualGridRenderer(DualGridRenderer<E> dgr) {
		tileset=dgr.tileset;
		OffX=dgr.OffX;
		OffY=dgr.OffY;
		borderValue=dgr.borderValue;
		testFunction=dgr.testFunction;
	}
	
	public DualGridRenderer(String tilsetPath, int tileSize) throws IOException{
        tileset=Utilities.loadTileset(tilsetPath, tileSize,tileSize);
        if(tileset.length<16)
        	throw new IOException("error while loading tileset, expecting at least 16 tiles, got "+tileset.length);
    }

	@Override
	public void draw(Grid<E> grid, Graphics g) {//method naive pour l'instant
		int pos=0;
		int size=tileset.length;
		for(int y=-1;y<grid.sizeY;y++) {
			for(int x=-1;x<grid.sizeX;x++) {
				
				int id=(testFunction.test((x>=0&&y>=0)?grid.get(x,y):borderValue)?1:0)+
					   (testFunction.test((x<grid.sizeX-1 && y>=0)?grid.get(x+1,y):borderValue)?2:0)+
					   (testFunction.test((y<grid.sizeY-1&&x>=0)?grid.get(x,y+1):borderValue)?4:0)+
					   (testFunction.test((y<grid.sizeY-1&&x<grid.sizeX-1)?grid.get(x+1,y+1):borderValue)?8:0);
				
				id=id>=size?0:id;
				g.drawImage(tileset[id], (int)((x+OffX)*grid.getScale()+grid.getPosX()),(int)((y+OffY)*grid.getScale()+grid.getPosY()+OffY),(int)grid.getScale(),(int)grid.getScale(),null);
				pos++;
			}
			pos++;
		}
	}

	public E getBorderValue() {
		return borderValue;
	}

	public void setBorderValue(E borderValue) {
		this.borderValue = borderValue;
	}

	public double getOffX() {
		return OffX;
	}

	public void setOffX(double offX) {
		OffX = offX;
	}

	public double getOffY() {
		return OffY;
	}

	public void setOffY(double offY) {
		OffY = offY;
	}

	
	
}
