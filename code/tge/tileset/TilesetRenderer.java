package tge.tileset;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Function;

import javax.imageio.ImageIO;

import tge.Utilities;

public class TilesetRenderer<E> extends GridRenderer<E> {
	
	
	 
	protected BufferedImage[] tileset;
	
	protected Function<E,Integer> textureMapping;
	
	public int getTiles() {
		return tileset.length;
	}
	
	public void loadTileset(String tilsetPath, int tileSize) throws IOException {
        tileset=Utilities.loadTileset(tilsetPath, tileSize,tileSize,-1);
    }
	
	@Override
	public void draw(Grid<E> grid, Graphics g) {
		int size=tileset.length;
		
		int LX=(int)Math.max(0,Math.floor((super.bound_lowX-grid.getPosX())/grid.getScale()));
		int LY=(int)Math.max(0,Math.floor((super.bound_lowY-grid.getPosY())/grid.getScale()));
		

		int HX=(int)Math.min(grid.sizeX,Math.ceil((super.bound_highX-grid.getPosX())/grid.getScale()));
		int HY=(int)Math.min(grid.sizeY,Math.ceil((super.bound_highY-grid.getPosY())/grid.getScale()));
		
		int pos=LX+LY*grid.sizeX;
		int offset=grid.sizeX-HX+LX;
		
		for(int y=LY;y<HY;y++) {
			for(int x=LX;x<HX;x++) {
				int id=textureMapping.apply(grid.data.get(pos));//;tm.get(grid.data.get(pos));
				id=id>=size?0:id;
				g.drawImage(tileset[id], (int)(x*grid.getScale()+grid.getPosX())
						,(int)(y*grid.getScale()+grid.getPosY())
						,(int)((x+1)*grid.getScale()+grid.getPosX())-(int)(x*grid.getScale()+grid.getPosX())
						,(int)((y+1)*grid.getScale()+grid.getPosY())-(int)(y*grid.getScale()+grid.getPosY()),null);
				pos++;
			}
			pos+=offset;
		}
	}

	public Function<E,Integer> getMapper() {
		return textureMapping;
	}

	public void setMapper(Function<E,Integer> textureMapping) {
		this.textureMapping = textureMapping;
	}
}
