package tge.tileset;

import java.awt.Graphics;
import java.io.IOException;

import tge.Utilities;

public class AdvancedTilesetRenderer<E> extends TilesetRenderer<E> {
	private int tileX,tileY;
	private int offX=0,offY=0;
	private int scale;
	

	public void loadTileset(String tilsetPath, int tileSizeX,int tileSizeY) throws IOException {

	    tileset=Utilities.loadTileset(tilsetPath, tileSizeX,tileSizeY);
	    tileX=tileSizeX;
	    tileY=tileSizeY;
	    scale=Math.min(tileSizeX, tileSizeY);
	}


	public int getScale() {
		return scale;
	}


	public void setScale(int scale) {
		this.scale = scale;
	}
	
	@Override
	public void draw(Grid<E> grid, Graphics g) {
		int pos=0;
		int size=tileset.length;
		double ratio=grid.getScale()/scale;
		double scaleX=(double)tileX*ratio;
		double scaleY=(double)tileY*ratio;
		int offsetX=(int) (offX*ratio);
		int offsetY=(int) (offY*ratio);
		for(int y=0;y<grid.sizeY;y++) {
			for(int x=0;x<grid.sizeX;x++) {
				int id=textureMapping.apply(grid.data.get(pos));
				id=id>=size?0:id;
				g.drawImage(tileset[id], (int)(x*grid.getScale()+grid.getPosX()+offsetX),
						(int)(y*grid.getScale()+grid.getPosY()+offsetY),
						(int)(x*grid.getScale()+grid.getPosX()+offsetX+scaleX) - (int)(x*grid.getScale()+grid.getPosX()+offsetX),
						(int)(y*grid.getScale()+grid.getPosY()+offsetY+scaleY) - (int)(y*grid.getScale()+grid.getPosY()+offsetY),null);
				pos++;
			}
		}
	}


	public int getOffY() {
		return offY;
	}


	public void setOffY(int offY) {
		this.offY = offY;
	}


	public int getOffX() {
		return offX;
	}


	public void setOffX(int offX) {
		this.offX = offX;
	}
}
