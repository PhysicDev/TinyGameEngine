package tge;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public final class Utilities {
	//chat gpt
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

	//chat gpt
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
    //chat gpt
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
