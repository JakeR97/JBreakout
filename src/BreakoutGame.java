import javax.swing.JFrame;

@SuppressWarnings("serial")
public class BreakoutGame extends JFrame {
	
	public static void main(String[] arg) {
		new BreakoutGame();
	}	
	
	public BreakoutGame() {
		this.setTitle("Breakout");
		this.setVisible(true);
		this.setLocation(690, 100);
		this.setSize(600, 800);
	}
	
}
