import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {

	private int x;
	private int y;
	private int height;
	private int width;
	private Image image;
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setImage(Image i) {
		image = i;
	}
	
	public Image getImage() {
		return image;
	}
	
	Rectangle getRect() {
		return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}
	
}
