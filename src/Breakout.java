import javax.swing.JFrame;

/** This is the JFram in which the Display JPanels live
 * 
 * @author reardj3
 * @version 1.0.2 (August 9th, 2018)
 */

@SuppressWarnings("serial")
public class Breakout extends JFrame {
	
	/** The default constructor launches a new default 
	 * display JPanel
	 */
	public Breakout() {
		setTitle("Breakout");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Constants.WIDTH, Constants.HEIGHT);
		setResizable(false);
		setVisible(true);
		setIconImage(Constants.ICON);
		
		try {
			add(new Display());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** The level specific constructor launches a new 
	 * level specific Display JPanel
	 * @param level
	 */
	public Breakout(int level) {
		setTitle("Breakout");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Constants.WIDTH, Constants.HEIGHT);
		setResizable(false);
		setVisible(true);
		setIconImage(Constants.ICON);
		
		try {
			add(new Display(level));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
