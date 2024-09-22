package tge.tileset;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Function;

import javax.imageio.ImageIO;

import tge.Utilities;

public class TilesetRenderer<E> implements GridRenderer<E> {
	
	
	 
	protected BufferedImage[] tileset;
	
	protected Function<E,Integer> textureMapping;
	
	public int getTiles() {
		return tileset.length;
	}
	
	public void loadTileset(String tilsetPath, int tileSize) throws IOException {
        tileset=Utilities.loadTileset(tilsetPath, tileSize,tileSize);
    }
	
	@Override
	public void draw(Grid<E> grid, Graphics g) {
		int pos=0;
		int size=tileset.length;
		for(int y=0;y<grid.sizeY;y++) {
			for(int x=0;x<grid.sizeX;x++) {
				int id=textureMapping.apply(grid.data.get(pos));//;tm.get(grid.data.get(pos));
				id=id>=size?0:id;
				g.drawImage(tileset[id], (int)(x*grid.getScale()+grid.getPosX())
						,(int)(y*grid.getScale()+grid.getPosY())
						,(int)((x+1)*grid.getScale()+grid.getPosX())-(int)(x*grid.getScale()+grid.getPosX())
						,(int)((y+1)*grid.getScale()+grid.getPosY())-(int)(y*grid.getScale()+grid.getPosY()),null);
				pos++;
			}
		}
	}

	public Function<E,Integer> getMapper() {
		return textureMapping;
	}

	public void setMapper(Function<E,Integer> textureMapping) {
		this.textureMapping = textureMapping;
	}
}
