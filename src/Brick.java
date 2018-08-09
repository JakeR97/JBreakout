import java.net.URL;

import javax.swing.ImageIcon;

/** The brick class, for the general brick,
 * not the special bricks 
 * @author reardj3
 * @version 1.0.2 (August 9th, 2018)
 */
@SuppressWarnings("serial")
public class Brick extends Sprite {
	
	private String color;

	/** The default constructor creates a brick at specified location
	 * and assigns it a color based on its y value
	 * @param x is x pos
	 * @param y is y pos
	 */
	public Brick(int x, int y) {
		setX(x);
		setY(y);
		
		this.setVisible(true);
		if (y < 115) {
			color = "Purple";
		} else if (y < 230) {
			color = "Blue";
		} else if (y < 345) {
			color = "Cyan";
		} else if (y < 460) {
			color = "Green";
		} else if (y < 575) {
			color = "Yellow";
		} else if (y < 690) {
			color = "Orange";
		} else {
			color = "Red";
		}
		
		try {
			URL brickUrl = Brick.class.getResource("/" + color + "Brick.png");
			ImageIcon ii = new ImageIcon(brickUrl);
			this.setImage(ii.getImage());
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println(color);
		}
		
		this.setHeight(this.getImage().getHeight(null));
		this.setWidth(this.getImage().getWidth(null));
	}
	
	/** The specialized construtor is the same as the default,
	 * except the color is specified as opposed to being 
	 * determined by the y position
	 * @param x is x pos
	 * @param y is y po
	 * @param col is color to set brick
	 */
	public Brick(int x, int y, String col) {
		setX(x);
		setY(y);
		color = col;
		try {
			URL brickUrl = Brick.class.getResource("/" + color + "Brick.png");
			ImageIcon ii = new ImageIcon(brickUrl);
			this.setImage(ii.getImage());
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println(color);
		}
		
		this.setHeight(this.getImage().getHeight(null));
		this.setWidth(this.getImage().getWidth(null));
	}
	
	/**@return the color of the brick */
	public String getColor() {
		return color;
	}
	
}
