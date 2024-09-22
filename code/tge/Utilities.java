package tge;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public final class Utilities {
	//chat gpt
	/**
     * Replace colors in img based on the palette and an integer shift value.
     *
     * @param img BufferedImage - The source image where colors will be replaced
     * @param palette BufferedImage - The palette image used to map the colors
     * @param shiftRow int - The row in the palette to shift the colors to
     */
    public static void replaceColors(BufferedImage img, BufferedImage palette, int shiftRow) {
        int width = img.getWidth();
        int height = img.getHeight();

        // Ensure the palette has enough rows
        if (shiftRow>=palette.getHeight()) 
            throw new IllegalArgumentException("The palette doesn't have enough rows for the given shift.");
        
        if(shiftRow<0) 
            throw new IllegalArgumentException("The variable shiftRow cannot be negative");

        // Iterate through each pixel of img
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get the current pixel color
                int currentColor = img.getRGB(x, y);

                // Iterate through the first row of the palette
                for (int paletteX = 0; paletteX < palette.getWidth(); paletteX++) {
                    int paletteColor = palette.getRGB(paletteX, 0);

                    // If the color matches, replace it with the color from the shifted row
                    if (currentColor == paletteColor) {
                        int replacementColor = palette.getRGB(paletteX, shiftRow); // shiftRow-1 as index starts from 0
                        img.setRGB(x, y, replacementColor);
                        break; // Stop checking after the match
                    }
                }
            }
        }
    }
	
	
	
	public static void FillImage(Color c,BufferedImage bi) {
		int rgb=c.getRGB();
        for (int y = 0; y < bi.getHeight(); y++) {
            for (int x = 0; x < bi.getWidth(); x++) {
                int rgba = bi.getRGB(x, y);
                int alpha = (rgba >> 24) & 0xFF;
                int newColor = (alpha << 24) | (rgb & 0x00FFFFFF);
                bi.setRGB(x, y, newColor);
            }
        }
	}
	
	 public static BufferedImage tileImage(BufferedImage image, int width, int height) {
	        // Create a new BufferedImage with the desired width and height
	        BufferedImage tiledImage = new BufferedImage(width, height, image.getType());

	        // Get the Graphics2D context from the new image
	        Graphics2D g2d = tiledImage.createGraphics();

	        // Tile the input image across the entire area
	        for (int y = 0; y < height; y += image.getHeight()) {
	            for (int x = 0; x < width; x += image.getWidth()) {
	                g2d.drawImage(image, x, y, null);
	            }
	        }

	        // Dispose of the Graphics2D context to release system resources
	        g2d.dispose();

	        // Return the tiled image
	        return tiledImage;
	    }
	
	public static BufferedImage[] loadTileset(String tilsetPath, int tileSizeX,int tileSizeY) throws IOException {
        // Load the image from the file path
        BufferedImage image = ImageIO.read(new File(tilsetPath));
        
        // Get image dimensions
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        
        // Calculate number of tiles in each dimension
        int tilesX = imageWidth / tileSizeX;
        int tilesY = imageHeight / tileSizeY;
        
        // Create an array to store the tiles
        BufferedImage[] tileset = new BufferedImage[tilesX * tilesY];
        
        // Loop through the image and extract tiles
        int tileIndex = 0;
        for (int y = 0; y < tilesY; y++) {
            for (int x = 0; x < tilesX; x++) {
                // Extract the tile as a subimage
                BufferedImage tile = image.getSubimage(x * tileSizeX, y * tileSizeY, tileSizeX, tileSizeY);
                tileset[tileIndex++] = tile;
            }
        }
        return tileset;
    }
	
	//chat gpt
	public static BufferedImage cloneBufferedImage(BufferedImage original) {
        // Create a new BufferedImage with the same properties as the original
        BufferedImage clone = new BufferedImage(
            original.getWidth(),
            original.getHeight(),
            original.getType()
        );

        // Create a Graphics2D object to draw the original image onto the new one
        Graphics2D g2d = clone.createGraphics();
        g2d.drawImage(original, 0, 0, null);
        g2d.dispose(); // Clean up

        return clone;
    }
	
	public static void addHighlight(JButton button) {
        Icon originalIcon = button.getIcon();

        if (originalIcon instanceof ImageIcon) {
            ImageIcon originalImageIcon = (ImageIcon) originalIcon;
            Image originalImage = originalImageIcon.getImage();

            // Create a brighter version of the icon
            Image brightenedImage = brightenImage(originalImage, 1.2f); // 1.2f for 20% brighter
            ImageIcon brightenedImageIcon = new ImageIcon(brightenedImage);

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setIcon(brightenedImageIcon);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setIcon(originalImageIcon);
                }
            });
        }
    }

    private static Image brightenImage(Image originalImage, float factor) {
        BufferedImage bufferedImage = new BufferedImage(
                originalImage.getWidth(null),
                originalImage.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        RescaleOp rescaleOp = new RescaleOp(factor, 0, null);
        rescaleOp.filter(bufferedImage, bufferedImage);

        return bufferedImage;
    }
}
