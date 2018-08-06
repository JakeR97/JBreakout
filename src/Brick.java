import java.net.URL;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Brick extends Sprite {
	
	private String color;

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
	
	public String getColor() {
		return color;
	}
	
}
