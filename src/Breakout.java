import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Breakout extends JFrame {
	
	public Breakout() {
		setTitle("Breakout");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Constants.WIDTH, Constants.HEIGHT);
		setResizable(false);
		setVisible(true);
		try {
			add(new Display());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		setIconImage(new ImageIcon("Images/Ball1.png").getImage());
		
		
	}
	
	public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                
                Breakout game = null;
				game = new Breakout();
                game.setVisible(true);                
            }
        });
	}
	
}
