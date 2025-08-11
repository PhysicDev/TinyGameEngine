package tge.tileset;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.Predicate;

import tge.Utilities;

public class DualGridRenderer<E> extends GridRenderer<E> {

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
		int size=tileset.length;

		int LX=(int)Math.max(-1,Math.floor((super.bound_lowX-grid.getPosX())/grid.getScale())-1);
		int LY=(int)Math.max(-1,Math.floor((super.bound_lowY-grid.getPosY())/grid.getScale())-1);
		

		int HX=(int)Math.min(grid.sizeX,Math.ceil((super.bound_highX-grid.getPosX())/grid.getScale()));
		int HY=(int)Math.min(grid.sizeY,Math.ceil((super.bound_highY-grid.getPosY())/grid.getScale()));
		
		for(int y=LY;y<HY;y++) {
			for(int x=LX;x<HX;x++) {
				
				int id=(testFunction.test((x>=0&&y>=0)?grid.get(x,y):borderValue)?1:0)+
					   (testFunction.test((x<grid.sizeX-1 && y>=0)?grid.get(x+1,y):borderValue)?2:0)+
					   (testFunction.test((y<grid.sizeY-1&&x>=0)?grid.get(x,y+1):borderValue)?4:0)+
					   (testFunction.test((y<grid.sizeY-1&&x<grid.sizeX-1)?grid.get(x+1,y+1):borderValue)?8:0);
				
				id=id>=size?0:id;
				g.drawImage(tileset[id],
						(int)((x+OffX)*grid.getScale()+grid.getPosX()),
						(int)((y+OffY)*grid.getScale()+grid.getPosY()+OffY),
						(int)((x+1+OffX)*grid.getScale()+grid.getPosX())	 -(int)((x+OffX)*grid.getScale()+grid.getPosX()),
						(int)((y+1+OffY)*grid.getScale()+grid.getPosY()+OffY)-(int)((y+OffY)*grid.getScale()+grid.getPosY()+OffY),null);
			}
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
