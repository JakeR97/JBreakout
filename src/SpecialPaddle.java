import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class SpecialPaddle extends Paddle {

	public SpecialPaddle(int x, int y, String spec) {
		super(x, y);
		if (spec.equals("Short")) {
			ImageIcon ii = new ImageIcon("Images/ShortenedPaddle.png");
			this.setImage(ii.getImage());
		} else if (spec.equals("Long")) {
			ImageIcon ii = new ImageIcon("Images/ExtendedPaddle.png");
			this.setImage(ii.getImage());
		}
		this.setWidth(this.getImage().getWidth(null));
		this.setHeight(this.getImage().getHeight(null));
	}

}
