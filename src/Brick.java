import java.net.URL;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Brick extends Sprite {

	public Brick(int x, int y) {
		setX(x);
		setY(y);
		
		this.setVisible(true);
		
		URL brickUrl = Brick.class.getResource("/Brick.png");
		ImageIcon ii = new ImageIcon(brickUrl);
		this.setImage(ii.getImage());
		
		this.setHeight(this.getImage().getHeight(null));
		this.setWidth(this.getImage().getWidth(null));
	}
	
}
