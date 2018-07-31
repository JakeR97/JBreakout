import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Breakout extends JFrame {
	
	public Breakout() {
		add(new Display());
		setTitle("Breakout");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Constants.WIDTH, Constants.HEIGHT);
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                
                Breakout game = new Breakout();
                game.setVisible(true);                
            }
        });
    }
	
}
