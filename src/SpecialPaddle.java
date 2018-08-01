import java.net.URL;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class SpecialPaddle extends Paddle {

	public SpecialPaddle(int x, int y, String spec) {
		super(x, y);
		if (spec.equals("Short")) {
			URL smallUrl = SpecialBrick.class.getResource("/ShortenedPaddle.png");
			ImageIcon ii = new ImageIcon(smallUrl);		
			this.setImage(ii.getImage());
		} else if (spec.equals("Long")) {
			URL bigUrl = SpecialBrick.class.getResource("/ExtendedPaddle.png");
			ImageIcon ii = new ImageIcon(bigUrl);
			this.setImage(ii.getImage());
		}
		this.setWidth(this.getImage().getWidth(null));
		this.setHeight(this.getImage().getHeight(null));
	}

}
