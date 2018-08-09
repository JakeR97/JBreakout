import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;

/** This interface holds all of the values
 * that are used throughout the program
 * 
 * @author reardj3
 * @version 1.0.2 (August 9th, 2018)
 *
 */

public interface Constants {

	public static final int WIDTH = 715;
	public static final int HEIGHT = 1080;
	public static final int PAD_X_START = 250;
	public static final int PAD_Y_START = 1000;
	public static final int BALL_X_START = 325;
	public static final int BALL_Y_START = 980;	

	public static final Font MENU_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 36);
	public static final Color CLEAR = new Color(0, 0, 0, 0);	
	public static final Image ICON = new ImageIcon(Breakout.class.getResource("/Icon.png")).getImage();
}
