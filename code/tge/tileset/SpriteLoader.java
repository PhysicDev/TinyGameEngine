package tge.tileset;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import tge.Utilities;

public class SpriteLoader {
	protected BufferedImage[] frames;
	public SpriteLoader(String tilsetPath, int tileWidth,int tileHeight) throws IOException {
       this(tilsetPath,tileWidth,tileHeight,-1);
    }
	public SpriteLoader(String tilsetPath, int tileWidth,int tileHeight,int f) throws IOException {
        frames=Utilities.loadTileset(tilsetPath, tileWidth,tileHeight,f);
    }

	private float frameDuration=0.1f;
	public void setFrameDuration(float fd) {frameDuration=fd;}
	
	public BufferedImage getFrame(float time){
		return frames[(int)(time/frameDuration)];
	}
	
	public void drawFrame(float time,int x,int y,int width,int height,Graphics g) {
		g.drawImage(frames[(int)(time%frameDuration)], x, y, width,height,null);
	}
	
	public float animationLength() {
		return frameDuration*frames.length;
	}
}
