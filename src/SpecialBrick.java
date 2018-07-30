import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class SpecialBrick extends Brick {

	private String powerUp;
	
	public SpecialBrick(int x, int y, String power) {
		super(x, y);
		powerUp = power;
		if (powerUp.equals("FireBall")) {
			ImageIcon ii = new ImageIcon("FireBall.jpg");
			this.setImage(ii.getImage());
		} else if (powerUp.equals("BigPaddle")) {
			ImageIcon ii = new ImageIcon("BigPaddle.jpg");
			this.setImage(ii.getImage());
		} else if (powerUp.equals("SmallPaddle")) {
			ImageIcon ii = new ImageIcon("SmallPaddle.jpg");
			this.setImage(ii.getImage());
		}
	}
	
	public String getPowerUp() {
		return powerUp;
	}
	
	

}
