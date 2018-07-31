import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JPanel implements ActionListener {
	
	private Paddle paddle;
	private Ball ball;
	private Timer timer;
	private ArrayList<Brick> bricks;
	private boolean gameOver, levelWon;
	private Image background;

	public Display() {
		initialize();
	}

	private void initialize() {
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);
		setDoubleBuffered(true);
		
		timer = new Timer(15, this);
		timer.start();
		
		//Initialize variables/objects
		paddle = new Paddle(400, 1000);
		ball = new Ball(400 + paddle.getWidth()/2, 985, 2);
		bricks = new ArrayList<Brick>();
		gameOver = false;
		levelWon = false;
		
		//Background
		ImageIcon ii = new ImageIcon("Images/Background.png");
		background = ii.getImage();
		
		addLevelOne();

		
	}
	
	public void addLevelOne() {
		int x = 65;
		int y = 20;
		for (int i = 1; i <= 6; i++) {
			for (int j = 1; j <= 6; j++) {
				if ((i == 2 || i == 5) && (j == 2 || j == 5)) {
					SpecialBrick brick = new SpecialBrick(250 + i*x, 300 + j*y, "FireBall");
					bricks.add(brick);
				} else {
					Brick brick = new Brick(250 + i*x, 300 + j*y);
					bricks.add(brick);
				}
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
		
		drawObjects(g2d);
		
		if (gameOver) {
			g2d.setColor(Color.RED);
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 72));
			g2d.drawString("Game Over", 350, 600);
		}
		if (levelWon) {
			g2d.setColor(Color.GREEN);
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 72));
			g2d.drawString("Level Won!", 350, 600);
		}
		
	}
	
	private void drawObjects(Graphics2D g2d) {
		try {
			g2d.drawImage(paddle.getImage(), paddle.getX(),	paddle.getY(), 
					paddle.getWidth(), paddle.getHeight(), this);
			g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(),
					ball.getWidth(), ball.getHeight(), this);
			for (Brick brick: bricks) {
				g2d.drawImage(brick.getImage(), brick.getX(), brick.getY(),
						brick.getWidth(), brick.getHeight(), this);
			}
		} catch (NullPointerException e) {
			//Suppress the output of the NPE that doesnt cause issues
		}
		
	}
	
	private void checkCollisions() throws LineUnavailableException, IOException {
		if (ball.isCollidedWith(paddle)) {
			ball.setVertDir("up");
			if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (4*paddle.getWidth()/5)) {
				ball.setHoDir("right");
				ball.setHoSpeed(20);
			}
			else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (3*paddle.getWidth()/5)) {
				ball.setHoDir("right");
				ball.setHoSpeed(10);
			}
			else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (2*paddle.getWidth()/5)) {
				ball.setHoSpeed(0);
			}
			else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (paddle.getWidth()/5)) {
				ball.setHoDir("left");
				ball.setHoSpeed(10);
			}
			else if (ball.getRect().getMaxX() > paddle.getRect().getMinX()) {
				ball.setHoDir("left");
				ball.setHoSpeed(20);
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
					java.util.Timer specTimer = new java.util.Timer("Special Timer");
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
					} else if (power.equals("SpeedUp")) {
						paddle.setPaddleSpeed(15);
						specTimer.schedule(new TimerTask() {
							public void run() {
								paddle.setPaddleSpeed(10);
							}
						}, 10000);
					} else if (power.equals("SlowDown")) {
						paddle.setPaddleSpeed(5);
						specTimer.schedule(new TimerTask() {
							public void run() {
								paddle.setPaddleSpeed(10);
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
	
	public void loseLevel() {
		ball.setVertSpeed(0);
		ball.setHoSpeed(0);
		gameOver = true;
	}
	
	public void winLevel() {
		ball.setVertSpeed(0);
		ball.setHoSpeed(0);
		levelWon = true;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		paddle.move();
		ball.move();
		if (ball.checkBounds()) {
			loseLevel();
			timer.stop();
		}
		if (bricks.isEmpty()) {
			winLevel();
			timer.stop();
		}
		try {
			checkCollisions();
		} catch (LineUnavailableException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		repaint();
	}
	
	
	// ------------- Classes ------------------------
	
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


	
	
}
