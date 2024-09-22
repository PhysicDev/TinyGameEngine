package tge.object;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BackgroundPanel extends JPanel {
    protected BufferedImage backgroundImage;

    // Constructor that takes a BufferedImage
    public BackgroundPanel(BufferedImage image) {
        this.backgroundImage = image;
    }

    // Constructor that takes a file path to an image
    public BackgroundPanel(String filePath) {
        try {
            this.backgroundImage = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Override the paintComponent method to draw the image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}