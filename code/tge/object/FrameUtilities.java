package tge.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class FrameUtilities {
	
	public static Font ButtonFont;

	public static JButton simpleButton(String string,Color c) {
		JButton output=new JButton(string);
		output.setPreferredSize(new Dimension(300,50));
		output.setSize(300,50);
		output.setMinimumSize(new Dimension(300,50));
		output.setFont(ButtonFont.deriveFont(35f));
		//output.setForeground(c);
		output.setBackground(new Color(255,255,255,150));
		output.setBorder(BorderFactory.createLineBorder(c, 3));
		output.setContentAreaFilled(true);
        output.setFocusPainted(false);  
		
		output.addMouseListener((MouseListener) new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent me) {
        		output.setBorder(BorderFactory.createLineBorder(c, 5));
        		output.setBackground(new Color(255,255,255,230));
            	
            }
            @Override
            public void mouseExited(MouseEvent me) {
        		output.setBorder(BorderFactory.createLineBorder(c, 3));
        		output.setBackground(new Color(255,255,255,150));
            	
            }
        });
		return output;
	}
}
