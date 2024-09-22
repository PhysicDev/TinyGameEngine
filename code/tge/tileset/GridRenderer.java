package tge.tileset;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.function.Function;

public interface GridRenderer<E> {
	
	 public static <T, R> Function<T, R> toFunction(HashMap<T, R> map) {
	        // Return a function that applies the hashmap's get method
	        return key -> map.get(key);   
	 }
	public void draw(Grid<E> grid,Graphics g);
}
