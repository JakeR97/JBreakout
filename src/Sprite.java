import java.awt.Component;
import java.awt.Image;
import java.awt.Rectangle;

@SuppressWarnings("serial")
public class Sprite extends Component {

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
	
	public void setHeight(int h) {
		height = h;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setWidth(int w) {
		width = w;
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
