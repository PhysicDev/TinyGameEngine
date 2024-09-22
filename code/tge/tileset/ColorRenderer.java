package tge.tileset;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.function.Function;

public class ColorRenderer<E> implements GridRenderer<E> {


	public static final Color NULL_COLOR=Color.PINK;
	protected Function<E,Color> textureMapping;
	
	@Override
	public void draw(Grid<E> grid, Graphics g) {
		int pos=0;
		for(int y=0;y<grid.sizeY;y++) {
			for(int x=0;x<grid.sizeX;x++) {
				Color c=textureMapping.apply(grid.data.get(pos));
				//System.out.println(grid.data.get(pos));
				c=c==null?NULL_COLOR:c;
				if(g.getColor()!=c)
					g.setColor(c);
				
				g.fillRect((int)(x*grid.getScale()+grid.getPosX()),(int)(y*grid.getScale()+grid.getPosY()),(int)grid.getScale(),(int)grid.getScale());

				pos++;
			}
		}
	}
	

	public Function<E,Color> getMapper() {
		return textureMapping;
	}

	public void setMapper(Function<E,Color> textureMapping) {
		this.textureMapping = textureMapping;
	}
}
