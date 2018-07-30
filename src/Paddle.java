import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Paddle extends Sprite {
	private int dx;
	private int paddleSpeed;
	
	
	public Paddle(int x, int y) {
		setX(x);
		setY(y);
		dx = 0;
		paddleSpeed = 10;
		this.setVisible(true);
		
		ImageIcon ii = new ImageIcon("paddle.png");
		this.setImage(ii.getImage());
		
		this.setHeight(this.getImage().getHeight(null));
		this.setWidth(this.getImage().getWidth(null));
	}
	
	public int getDX() {
		return dx;
	}
	
	public void setPaddleSpeed(int newSpeed) {
		paddleSpeed = newSpeed;
	}
	
	public void move() {
		if (dx > 0 && this.getRect().getMaxX() < Constants.WIDTH - 20) {
			this.setX(this.getX() + dx);
		}
		else if (dx < 0 && this.getRect().getMinX() > 20) {
			this.setX(this.getX() + dx);
		}
	}
	
	public void reset() {
		this.setX(300);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			dx = -(paddleSpeed);
		}
		if (key == KeyEvent.VK_RIGHT) {
			dx = paddleSpeed;
		}
	}
	
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
