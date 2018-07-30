import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Ball extends Sprite {
	
	private String vertDir;
	private String hoDir;
	private int vertSpeed;
	private int hoSpeed;

	public Ball(int x, int y) {
		setX(x);
		setY(y);
		setVisible(true);
		vertDir = "up";
		hoDir = "left";
		vertSpeed = 1;
		hoSpeed = 1;
		
		ImageIcon ii = new ImageIcon("Ball1.jpg");
		this.setImage(ii.getImage());
		
		this.setHeight(this.getImage().getHeight(null));
		this.setWidth(this.getImage().getWidth(null));		
	}
	
	public void setVertDir (String str) {
		vertDir = str;
	}
	
	public void setHoDir(String str) {
		hoDir = str;
	}
	
	public String getHoDir() {
		return hoDir;
	}
	
	public void setHoSpeed(int speed) {
		hoSpeed = speed;
	}
	
	public int getHoSpeed() {
		return hoSpeed;
	}
	
	public void setVertSpeed(int speed) {
		vertSpeed = speed;
	}
	
	public int getVertSpeed() {
		return vertSpeed;
	}
	
	public void move() {
		if (vertDir.equals("up")) {
			this.setY(this.getY() - vertSpeed);
		} else if (vertDir.equals("down")) {
			this.setY(this.getY() + vertSpeed);
		}	
		if (hoDir.equals("left")) {
			this.setX(this.getX() - hoSpeed);
		} else if (hoDir.equals("right")) {
			this.setX(this.getX() + hoSpeed);
		}
	}
	
	public boolean checkBounds() {
		if (this.getY() < 50) {
			vertDir = "down";
		} else if (this.getY() > Constants.HEIGHT - 10) {
			return true;
		}
		if (this.getX() < 10) {
			this.setHoDir("right");
		} else if (this.getX() > Constants.HEIGHT - 110) {
			this.setHoDir("left");
		}
		return false;
	}
	
	
	
	
}
