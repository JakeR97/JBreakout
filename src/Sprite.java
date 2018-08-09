import java.awt.Component;
import java.awt.Image;
import java.awt.Rectangle;

/** the sprite class is the parent class of all in game objects
 * (paddle, ball, brick)
 * @author reardj3
 * @version 1.0.2 (August 9th, 2018)
 */
@SuppressWarnings("serial")
public class Sprite extends Component {

	private int x;
	private int y;
	private int height;
	private int width;
	private Image image;
	
	/** Sets the x position */
	public void setX(int x) {
		this.x = x;
	}
	
	/** @return Returns the x position */
	public int getX() {
		return x;
	}
	
	/** Sets the y position */
	public void setY(int y) {
		this.y = y;
	}
	
	/** @return Returns the y position */
	public int getY() {
		return y;
	}
	
	/** Sets the height of the sprite */
	public void setHeight(int h) {
		height = h;
	}
	
	/**@return Returns the height of the sprite */
	public int getHeight() {
		return height;
	}
	
	/**Sets the width of the sprite */
	public void setWidth(int w) {
		width = w;
	}
	
	/**@return Returns the width of the sprite */
	public int getWidth() {
		return width;
	}
	
	/** Sets the image of the sprite */
	public void setImage(Image i) {
		image = i;
	}
	
	/**@return the image of the sprite */
	public Image getImage() {
		return image;
	}
	
	/**Returns the rectangle of space occupied by the sprite */
	public Rectangle getRect() {
		return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}
	
	/** @return Returns true if collided with another Sprite */
	public boolean isCollidedWith(Sprite sprite) {
		if (this.getRect().intersects(sprite.getRect())) {
			return true;
		} else {
			return false;
		}
	}
	
}
