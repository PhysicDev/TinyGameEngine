package tge.tileset;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

import tge.Utilities;

public class SmartTileset<E> implements GridRenderer<E> {

	private ArrayList<BufferedImage[]> tilesets=new ArrayList<BufferedImage[]>();
	
	private E borderValue=null;
	
	private Function<E,Integer> testFunction;
	
	public void setMapper(Function<E,Integer> testFun) {
		testFunction=testFun;
	}
	
	public int testMapper(E value) {
		return testFunction.apply(value);
	}
	
	public SmartTileset(SmartTileset<E> dgr) {
		tilesets=new ArrayList<BufferedImage[]>(dgr.tilesets);
		borderValue=dgr.borderValue;
		testFunction=dgr.testFunction;
	}
	
	public SmartTileset(String tilsetPath, int tileSize) throws IOException{
        tilesets.add(Utilities.loadTileset(tilsetPath, tileSize,tileSize));
        if(tilesets.get(0).length<16)
        	throw new IOException("error while loading tileset, expecting at least 16 tiles, got "+tilesets.get(0).length);
    }
	
	public SmartTileset(String[] tilsetPath, int tileSize) throws IOException{
		for(String path:tilsetPath) {
	        tilesets.add(Utilities.loadTileset(path, tileSize,tileSize));
	        if(tilesets.get(0).length<16)
	        	throw new IOException("error while loading tileset, expecting at least 16 tiles, got "+tilesets.get(0).length);
		}
    }

	@Override
	public void draw(Grid<E> grid, Graphics g) {//method naive pour l'instant
		int pos=0;
		for(int y=0;y<grid.sizeY;y++) {
			for(int x=0;x<grid.sizeX;x++) {
				int texID=testFunction.apply(grid.get(x,y));
				if(texID!=0) {
					int id=(testFunction.apply((y!=0)?grid.get(x,y-1):borderValue)!=0?1:0)+
						   (testFunction.apply((x!=grid.sizeX-1)?grid.get(x+1,y):borderValue)!=0?2:0)+
						   (testFunction.apply((x!=0)?grid.get(x-1,y):borderValue)!=0?4:0)+
						   (testFunction.apply((y!=grid.sizeY-1)?grid.get(x,y+1):borderValue)!=0?8:0);
					
					g.drawImage(tilesets.get(texID-1)[id],(int)(x*grid.getScale()+grid.getPosX())
							,(int)(y*grid.getScale()+grid.getPosY())
							,(int)((x+1)*grid.getScale()+grid.getPosX())-(int)(x*grid.getScale()+grid.getPosX())
							,(int)((y+1)*grid.getScale()+grid.getPosY())-(int)(y*grid.getScale()+grid.getPosY()),null);
				}
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

	
	
}
