import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * The class for the ablls of the game 
 * @author reardj3
 * @version 1.0.2 (August 9th, 2018)
 */
@SuppressWarnings("serial")
public class Ball extends Sprite {
	
	private String vertDir;
	private String hoDir;
	private int vertSpeed;
	private int hoSpeed;
	private String spec;

	/** Creates a new ball at specified location
	 * and sets its image to be a random image from
	 * the group of ball images
	 * @param x is x position
	 * @param y is y position
	 */
	public Ball(int x, int y) {
		Random rand = new Random();
		int ball = rand.nextInt(4) + 1;
		setX(x);
		setY(y);
		setVisible(true);
		vertDir = "up";
		hoDir = "left";
		vertSpeed = 10;
		hoSpeed = 2;
		spec = "";
		
		URL ballUrl = Ball.class.getResource("/Ball" + ball + ".png");
		ImageIcon ii = new ImageIcon(ballUrl);
		this.setImage(ii.getImage());
		
		this.setHeight(this.getImage().getHeight(null));
		this.setWidth(this.getImage().getWidth(null));		
	}
	
	/** Sets the ball to travel up or down */
	public void setVertDir (String str) {
		vertDir = str;
	}
	
	/** Sets the ball to travel left or right */
	public void setHoDir(String str) {
		hoDir = str;
	}
	
	/**@return the horizontal direction of the ball */
	public String getHoDir() {
		return hoDir;
	}
	
	/** Sets the speed (distance traveled per tick) of the ball
	 * in the horizontal direction
	 * @param speed - horizontal pixels to move per tick
	 */
	public void setHoSpeed(int speed) {
		hoSpeed = speed;
	}
	
	/** @return the horizontal speed of ball */
	public int getHoSpeed() {
		return hoSpeed;
	}
	
	/** Sets the speed (distance traveled per tick) of the ball
	 * in the vertical direction
	 * @param speed - vertical pixels to move per tick
	 */
	public void setVertSpeed(int speed) {
		vertSpeed = speed;
	}
	
	/** @return the vertical speed of the ball */
	public int getVertSpeed() {
		return vertSpeed;
	}
	
	/** Sets the special property of the ball, if there is any
	 * e.g. fireball
	 * @param special property of the ball
	 */
	public void setSpecial(String special) {
		spec = special;
	}
	
	/**@return the special property of the ball */
	public String getSpecial() {
		return spec;
	}
	
	/** This method is responsible for the balls movement */
	public void move() {
		if (vertDir.equals("up")) {
			this.setY(this.getY() - vertSpeed);
		} else if (vertDir.equals("down")) {
			this.setY(this.getY() + vertSpeed);
		}	
		if (hoDir.equals("left")) {
			this.setX(this.getX() - hoSpeed);
		} else if (hoDir.equals("right")) {
			this.setX(this.getX() + hoSpeed);
		}
	}
	
	/** This method is for a ball being out of bounds,
	 * as well as for bouncing balls off the sides and top
	 * of the frame
	 * @return true if the ball is below the paddle
	 */
	public boolean checkBounds() {
		if (this.getY() < 10) {
			vertDir = "down";
		} else if (this.getY() > Constants.HEIGHT - 10) {
			return true;
		}
		if (this.getX() < 10) {
			this.setHoDir("right");
		} else if (this.getX() > Constants.WIDTH - (10 + getWidth())) {
			this.setHoDir("left");
		}
		return false;
	}
	
	
	
	
}
