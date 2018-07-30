import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class BreakoutGame extends JFrame implements ActionListener {
	
	private Paddle paddle;
	private Ball ball;
	private Timer timer;
	private ArrayList<Brick> bricks;
	
	public static void main(String[] arg) {
		new BreakoutGame();
	}	
	
	public BreakoutGame() {
		this.setTitle("Breakout");
		this.setVisible(true);
		this.setSize(Constants.WIDTH, Constants.HEIGHT);
		this.setResizable(false);
		this.addKeyListener(new MyKeyAdapter());
		
		this.setContentPane(new BackgroundPanel());
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 15);
		
		paddle = new Paddle(400, 1000);
		ball = new Ball(400 + paddle.getWidth()/2, 985);
		bricks = new ArrayList<Brick>();
		
		addLevelOne();

		revalidate();
		repaint();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void addLevelOne() {
		int x = 65;
		int y = 20;
		for (int i = 1; i <= 13; i++) {
			for (int j = 1; j <= 5; j++) {
				if ((i == 2 || i == 12) && (j == 2 || j == 4)) {
					SpecialBrick brick = new SpecialBrick(i*x, 100 + j*y, "FireBall");
					bricks.add(brick);
				} else {
					Brick brick = new Brick(i*x, 100 + j*y);
					bricks.add(brick);
				}
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g); 
		
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
		
		drawObjects(g2d);
		
	}

	private void drawObjects(Graphics2D g2d) {
		g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), 
				paddle.getWidth(), paddle.getHeight(), this);
		g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(),
				ball.getWidth(), ball.getHeight(), this);
		for (Brick brick: bricks) {
			g2d.drawImage(brick.getImage(), brick.getX(), brick.getY(),
					brick.getWidth(), brick.getHeight(), this);
		}
		
	}
	
	private void checkCollisions() {
		if (ball.isCollidedWith(paddle)) {
			ball.setVertDir("up");
			if (ball.getRect().getMaxX() < paddle.getRect().getMinX() + (paddle.getWidth()/3)) {
				if (ball.getHoSpeed() == 0) {
					ball.setHoSpeed(5);
					ball.setHoDir("left");
			    } else if (ball.getHoDir().equals("left")) {
					ball.setHoSpeed(ball.getHoSpeed() + 1);
				} else if (ball.getHoDir().equals("right")) {
					ball.setHoSpeed(0);
				} 
			}
			if (ball.getRect().getMinX() > paddle.getRect().getMaxX() - (paddle.getWidth()/3)) {
				if (ball.getHoSpeed() == 0) {
					ball.setHoSpeed(5);
					ball.setHoDir("right");
			    } else if (ball.getHoDir().equals("right")) {
					ball.setHoSpeed(ball.getHoSpeed() + 1);
				} else if (ball.getHoDir().equals("left")) {
					ball.setHoSpeed(0);
				} 
			}
		}
		Brick brickToRemove = null;
		for (Brick brick: bricks) {
			if (ball.isCollidedWith(brick)) {
				brickToRemove = brick;
				if (!ball.getSpecial().equals("FireBall")) {
					if (Math.abs(ball.getRect().getMinY() - brick.getRect().getMaxY()) <= 5) {
						ball.setVertDir("down");
					}
					if (Math.abs(ball.getRect().getMaxY() - brick.getRect().getMinY()) <= 10) {
						ball.setVertDir("up");
					}
					if (Math.abs(ball.getRect().getMinX() - brick.getRect().getMaxX()) <= 5) {
						ball.setHoDir("right");
					}
					if (Math.abs(ball.getRect().getMaxX() - brick.getRect().getMinX()) <= 5) {
						ball.setHoDir("left");
					}
				}
				if (brick.getClass() == SpecialBrick.class) {
					String power = ((SpecialBrick) brick).getPowerUp();
					Timer specTimer = new Timer("Special Timer");
					int x = paddle.getX();
					int y = paddle.getY();
					//Big Paddle brick
					if (power.equals("BigPaddle")) {
						paddle = new SpecialPaddle(x, y, "Long");
						specTimer.schedule(new TimerTask() {
							public void run() {
								paddle = new Paddle(paddle.getX(), paddle.getY());
							}
						}, 10000);
					//Small paddle brick
					} else if (power.equals("SmallPaddle")) {
						paddle = new SpecialPaddle(x, y, "Short");	
						specTimer.schedule(new TimerTask() {
							public void run() {
								paddle = new Paddle(paddle.getX(), paddle.getY());
							}
						}, 10000);
					//Fireball brick
					} else if (power.equals("FireBall")) {
						ball.setSpecial("FireBall");
						specTimer.schedule(new TimerTask() {
							public void run() {
								ball.setSpecial("");
							}
						}, 10000);
					}					
				}
			}
		}
		if (brickToRemove != null) {
			bricks.remove(brickToRemove);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		paddle.move();
		ball.move();
		ball.checkBounds();
		checkCollisions();
		repaint();
	}
	
	public void endGame() {
		ball.setVertSpeed(0);
		ball.setHoSpeed(0);
		System.out.println("Game Over");
	}
	
// -------------------  CLASSES ------------------------------------------------- // 	
	
	private class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			paddle.keyPressed(e);
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			paddle.keyReleased(e);
		}
	}
	
	private class ScheduleTask extends TimerTask {

		@Override
		public void run() {
			paddle.move();
			ball.move();
			if (ball.checkBounds()) {
				endGame();
				timer.cancel();
			}
			checkCollisions();
			repaint();			
		}
		
	}
	
	
}


