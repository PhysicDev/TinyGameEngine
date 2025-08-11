package tge.tileset;

import java.awt.Color;
import java.awt.Graphics;
import java.util.function.Function;

public class ColorRenderer<E> extends GridRenderer<E> {


	public static final Color NULL_COLOR=Color.PINK;
	protected Function<E,Color> textureMapping;
	
	@Override
	public void draw(Grid<E> grid, Graphics g) {

		int LX=(int)Math.max(0,Math.floor((super.bound_lowX-grid.getPosX())/grid.getScale()));
		int LY=(int)Math.max(0,Math.floor((super.bound_lowY-grid.getPosY())/grid.getScale()));
		
		int HX=(int)Math.min(grid.sizeX,Math.ceil((super.bound_highX-grid.getPosX())/grid.getScale()));
		int HY=(int)Math.min(grid.sizeY,Math.ceil((super.bound_highY-grid.getPosY())/grid.getScale()));
		
		
		int pos=LX+LY*grid.sizeX;
		int offset=grid.sizeX-HX+LX;
		
		for(int y=LY;y<HY;y++) {
			for(int x=LX;x<HX;x++) {
				Color c=textureMapping.apply(grid.data.get(pos));
				//System.out.println(grid.data.get(pos));
				c=c==null?NULL_COLOR:c;
				if(g.getColor()!=c)
					g.setColor(c);
				
				g.fillRect((int)(x*grid.getScale()+grid.getPosX()),(int)(y*grid.getScale()+grid.getPosY()),(int)grid.getScale(),(int)grid.getScale());

				pos++;
			}
			pos+=offset;
		}
	}
	

	public Function<E,Color> getMapper() {
		return textureMapping;
	}

	public void setMapper(Function<E,Color> textureMapping) {
		this.textureMapping = textureMapping;
	}
}
