import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class SpecialBrick extends Brick {

	private String powerUp;
	
	public SpecialBrick(int x, int y, String power) {
		super(x, y);
		powerUp = power;
		if (powerUp.equals("FireBall")) {
			ImageIcon ii = new ImageIcon("FireBall.png");
			this.setImage(ii.getImage());
		} else if (powerUp.equals("BigPaddle")) {
			ImageIcon ii = new ImageIcon("BigPaddle.png");
			this.setImage(ii.getImage());
		} else if (powerUp.equals("SmallPaddle")) {
			ImageIcon ii = new ImageIcon("SmallPaddle.png");
			this.setImage(ii.getImage());
		}
	}
	
	public String getPowerUp() {
		return powerUp;
	}
	
	

}
