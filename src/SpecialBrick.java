import java.net.URL;

import javax.swing.ImageIcon;

/** This is the special brick class, 
 * which takes care of the bricks containing
 * power ups
 * @author reardj3
 *  @version 1.0.2 (August 9th, 2018)
 *
 */
@SuppressWarnings("serial")
public class SpecialBrick extends Brick {

	private String powerUp;
	private int hitsLeft;
	
	/** This constructor creates a new special brick with a 
	 * power up inside
	 * @param x is x pos
	 * @param y is y pos
	 * @param power is the power up of the brick
	 */
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
	
	/** This constructor acts the same as above, but also specifies
	 * a color to set the brick
	 * @param x is x pos
	 * @param y is y pos
	 * @param power is the special power of the brick
	 * @param color is the specified color of the brick
	 */
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
	
	/**@return Returns the power up of the special brick */
	public String getPowerUp() {
		return powerUp;
	}
	
	/** @return Returns the number of hits left before the brick is destroyed */
	public int getHitsLeft() {
		return hitsLeft;
	}
	
	/** Reduces number of hits left before the brick is destroyed */
	public void hitBrick() {
		hitsLeft--;
	}
	
	

}
