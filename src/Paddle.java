import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * This is the paddle class which is a simple in game
 * object that only interacts with the ball and sides.
 * @author reardj3
 * @version 1.0.2 (August 9th, 2018)
 */

@SuppressWarnings("serial")
public class Paddle extends Sprite {
	private int dx;
	private int paddleSpeed;
	
	/** The constructor creates a new paddle at specified location */
	public Paddle(int x, int y) {
		URL paddleUrl = Paddle.class.getResource("paddle.png");
		ImageIcon ii = new ImageIcon(paddleUrl);
		this.setImage(ii.getImage());
		
		setX(x);
		setY(y);
		dx = 0;
		paddleSpeed = 10;
		this.setVisible(true);
		
		
		
		this.setHeight(this.getImage().getHeight(null));
		this.setWidth(this.getImage().getWidth(null));
	}
	
	/** Returns the current speed of the paddle */
	public int getDX() {
		return dx;
	}
	
	/** Changes the speed (pixels per tick) of the paddle */
	public void setPaddleSpeed(int newSpeed) {
		paddleSpeed = newSpeed;
	}
	
	/** Handles the display of movement of the paddle */
	public void move() {
		if (dx > 0 && this.getRect().getMaxX() < Constants.WIDTH - 20) {
			this.setX(this.getX() + dx);
		}
		else if (dx < 0 && this.getRect().getMinX() > 20) {
			this.setX(this.getX() + dx);
		}
	}
	
	/** Moves the paddle to the default position */
	public void reset() {
		this.setX(300);
	}
	
	/** Handles the movement of the paddle when akey is
	 * pressed 
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			dx = -(paddleSpeed);
		}
		if (key == KeyEvent.VK_RIGHT) {
			dx = paddleSpeed;
		}
	}
	
	/** Handles the movement of the paddle when akey is
	 * released 
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
	}
}
