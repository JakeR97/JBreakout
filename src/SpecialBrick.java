import java.net.URL;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class SpecialBrick extends Brick {

	private String powerUp;
	private int hitsLeft;
	
	public SpecialBrick(int x, int y, String power) {
		super(x, y);
		powerUp = power;
		hitsLeft = 1;
		if (powerUp.equals("FireBall")) {
			URL fireUrl = SpecialBrick.class.getResource("/FireBall.png");
			ImageIcon ii = new ImageIcon(fireUrl);
			this.setImage(ii.getImage());
		} else if (powerUp.equals("BigPaddle")) {
			URL bigPadUrl = SpecialBrick.class.getResource("/BigPaddle.png");
			ImageIcon ii = new ImageIcon(bigPadUrl);
			this.setImage(ii.getImage());
		} else if (powerUp.equals("SmallPaddle")) {
			URL smallPadUrl = SpecialBrick.class.getResource("/SmallPaddle.png");
			ImageIcon ii = new ImageIcon(smallPadUrl);			
			this.setImage(ii.getImage());
		} else if (powerUp.equals("SlowDown")) {
			URL slowUrl = SpecialBrick.class.getResource("/SlowDown.png");
			ImageIcon ii = new ImageIcon(slowUrl);
			this.setImage(ii.getImage());
		} else if (powerUp.equals("SpeedUp")) {
			URL fastUrl = SpecialBrick.class.getResource("/SpeedUp.png");
			ImageIcon ii = new ImageIcon(fastUrl);
			this.setImage(ii.getImage());
		} else if (powerUp.equals("MultiBall")) {
			URL multiUrl = SpecialBrick.class.getResource("/MultiBall.png");
			ImageIcon ii = new ImageIcon(multiUrl);
			this.setImage(ii.getImage());
		} else if (powerUp.equals("Hard")) {
			hitsLeft = 2;
		}
	}
	
	public SpecialBrick(int x, int y, String power, String color) {
		super(x, y, color);
		powerUp = power;
		hitsLeft = 1;
		if (powerUp.equals("FireBall")) {
			URL fireUrl = SpecialBrick.class.getResource("/FireBall.png");
			ImageIcon ii = new ImageIcon(fireUrl);
			this.setImage(ii.getImage());
		} else if (powerUp.equals("BigPaddle")) {
			URL bigPadUrl = SpecialBrick.class.getResource("/BigPaddle.png");
			ImageIcon ii = new ImageIcon(bigPadUrl);
			this.setImage(ii.getImage());
		} else if (powerUp.equals("SmallPaddle")) {
			URL smallPadUrl = SpecialBrick.class.getResource("/SmallPaddle.png");
			ImageIcon ii = new ImageIcon(smallPadUrl);			
			this.setImage(ii.getImage());
		} else if (powerUp.equals("SlowDown")) {
			URL slowUrl = SpecialBrick.class.getResource("/SlowDown.png");
			ImageIcon ii = new ImageIcon(slowUrl);
			this.setImage(ii.getImage());
		} else if (powerUp.equals("SpeedUp")) {
			URL fastUrl = SpecialBrick.class.getResource("/SpeedUp.png");
			ImageIcon ii = new ImageIcon(fastUrl);
			this.setImage(ii.getImage());
		} else if (powerUp.equals("MultiBall")) {
			URL multiUrl = SpecialBrick.class.getResource("/MultiBall.png");
			ImageIcon ii = new ImageIcon(multiUrl);
			this.setImage(ii.getImage());
		} else if (powerUp.equals("Hard")) {
			hitsLeft = 2;
		}
	}
	
	public String getPowerUp() {
		return powerUp;
	}
	
	public int getHitsLeft() {
		return hitsLeft;
	}
	
	public void hitBrick() {
		hitsLeft--;
	}
	
	

}
