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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Display extends JPanel implements ActionListener {
	
	private Paddle paddle;
	private Ball ball;
	private Timer timer;
	private ArrayList<Brick> bricks;
	private boolean gameOver, levelWon;
	private Image background;
	private JLabel count3, count2, count1;
	private int currentLevel;

	public Display() throws InterruptedException {
		initialize();
	}

	private void initialize() throws InterruptedException {
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);
		setDoubleBuffered(true);		
		
		//Initialize variables/objects
		paddle = new Paddle(Constants.PAD_X_START, Constants.PAD_Y_START);
		ball = new Ball(Constants.BALL_X_START, Constants.BALL_Y_START, 2);
		bricks = new ArrayList<Brick>();
		gameOver = false;
		levelWon = false;
		count3 = new JLabel("3");
		count3.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 72));
		count3.setForeground(Color.WHITE);
		count2 = new JLabel("2");
		count2.setForeground(Color.WHITE);
		count2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 72));
		count1 = new JLabel("2");
		count1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 72));
		count1.setForeground(Color.WHITE);
		
		//Background
		ImageIcon ii = new ImageIcon("Images/Background.png");
		background = ii.getImage();
		
		currentLevel = 2;
		addLevel(currentLevel);	
		
		timer = new Timer(10, this);
		timer.setInitialDelay(1500);
		timer.start();
		
	}
	
	public void addLevel(int level) {
		switch (level) {
			case 1: addLevelOne();
					break;
			case 2: addLevelTwo();
					break;
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
			if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (10*paddle.getWidth()/11)) {
				ball.setHoDir("right");
				ball.setHoSpeed(10);
				ball.setVertSpeed(1);				
			}
			else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (9*paddle.getWidth()/11)) {
				ball.setHoDir("right");
				ball.setHoSpeed(8);
				ball.setVertSpeed(3);
			}
			else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (8*paddle.getWidth()/11)) {
				ball.setHoDir("right");
				ball.setHoSpeed(6);
				ball.setVertSpeed(5);
			}
			else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (7*paddle.getWidth()/11)) {
				ball.setHoDir("right");
				ball.setHoSpeed(4);
				ball.setVertSpeed(7);
			}
			else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (6*paddle.getWidth()/11)) {
				ball.setHoDir("right");
				ball.setHoSpeed(2);
				ball.setVertSpeed(9);
			}
			else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (5*paddle.getWidth()/11)) {
				ball.setHoSpeed(0);
				ball.setVertSpeed(10);
			}
			else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (4*paddle.getWidth()/11)) {
				ball.setHoDir("left");
				ball.setHoSpeed(2);
				ball.setVertSpeed(9);
			}
			else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (3*paddle.getWidth()/11)) {
				ball.setHoDir("left");
				ball.setHoSpeed(4);
				ball.setVertSpeed(7);
			}
			else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (2*paddle.getWidth()/11)) {
				ball.setHoDir("left");
				ball.setHoSpeed(6);
				ball.setVertSpeed(5);
			}
			else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (paddle.getWidth()/11)) {
				ball.setHoDir("left");
				ball.setHoSpeed(8);
				ball.setVertSpeed(3);
			}
			else if (ball.getRect().getMaxX() > paddle.getRect().getMinX()) {
				ball.setHoDir("left");
				ball.setHoSpeed(10);
				ball.setVertSpeed(1);
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
		paddle.setPaddleSpeed(0);
		levelWon = true;
		addLevel(currentLevel);
		paddle.setX(Constants.PAD_X_START);
		paddle.setY(Constants.PAD_Y_START);
		ball.setX(Constants.BALL_X_START);
		ball.setY(Constants.BALL_Y_START);
		try {
			timer.wait(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
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
	
	// ------------- Levels -------------------------
	
	private void addLevelOne() {
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
	
	private void addLevelTwo() {
		int x = 65;
		int y = 20;
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++) {
				if (j == 5 && (i == 3 || i == 8)) {
					SpecialBrick brick = new SpecialBrick(100 + i*x, 100 + j*y, "BigPaddle");
					bricks.add(brick);
				}
				else if((i == 5 || i == 6) && j == 5) {
					SpecialBrick brick = new SpecialBrick(100 + i*x, 100 + j*y, "SlowDown");
					bricks.add(brick);
				}
				else if (j == 6 && (i == 3 || i == 8)) {
					SpecialBrick brick = new SpecialBrick(100 + i*x, 100 + j*y, "SmallPaddle");
					bricks.add(brick);
				}
				else if((i == 5 || i == 6) && j ==6) {
					SpecialBrick brick = new SpecialBrick(100 + i*x, 100 + j*y, "SpeedUp");
					bricks.add(brick);
				}
				else if (i == 1 || i == 3 || i == 5 || i == 6 || i == 8 || i == 10 || j == 1 || j == 10) {
					Brick brick = new Brick(100 + i*x, 100 + j*y);
					bricks.add(brick);
				}
			}
		}
	}


	
	
}
