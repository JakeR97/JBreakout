import java.net.URL;

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
		URL iconUrl = Breakout.class.getResource("/Ball1.png");
		setIconImage(new ImageIcon(iconUrl).getImage());
		
		try {
			add(new Display());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Breakout(int level) {
		setTitle("Breakout");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Constants.WIDTH, Constants.HEIGHT);
		setResizable(false);
		setVisible(true);
		URL iconUrl = Breakout.class.getResource("/Ball1.png");
		setIconImage(new ImageIcon(iconUrl).getImage());
		
		try {
			add(new Display(level));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
