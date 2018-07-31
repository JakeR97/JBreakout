import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Breakout extends JFrame {
	
	public Breakout() throws InterruptedException {
		setTitle("Breakout");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Constants.WIDTH, Constants.HEIGHT);
		setResizable(false);
		setVisible(true);
		add(new Display());
		
		setIconImage(new ImageIcon("Images/Ball1.png").getImage());
		
		
	}
	
	public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                
                Breakout game = null;
				try {
					game = new Breakout();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                game.setVisible(true);                
            }
        });
    }
	
}
