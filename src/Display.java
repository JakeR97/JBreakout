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
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Display extends JPanel implements ActionListener {
	
	private Paddle paddle;
	private Ball ball;
	private ArrayList<Ball> balls;
	private Timer timer;
	private ArrayList<Brick> bricks;
	private boolean gameOver, levelWon;
	private Image background;
	private JLabel count3, count2, count1;
	private int currentLevel;
	private boolean paused;
	private SoundEffect ballBrick, ballPaddle, music;

	public Display() throws InterruptedException {
		currentLevel = 1;
		initialize();
	}

	public Display(int level) throws InterruptedException {
		currentLevel = level;
		initialize();
	}

	private void initialize() throws InterruptedException {
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);
		setDoubleBuffered(true);		
		
		//Initialize variables/objects
		paddle = new Paddle(Constants.PAD_X_START, Constants.PAD_Y_START);
		ball = new Ball(Constants.BALL_X_START, Constants.BALL_Y_START);
		balls = new ArrayList<Ball>();
		balls.add(ball);
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
		
		//Sound effects
		InputStream brickStream = Display.class.getResourceAsStream("/BallBrick.wav");
		InputStream paddleStream = Display.class.getResourceAsStream("/BallPaddle.wav");
		InputStream musicStream = Display.class.getResourceAsStream("/LevelMusicCalm.wav");
		ballBrick = new SoundEffect(brickStream);
		ballPaddle = new SoundEffect(paddleStream);
		music = new SoundEffect(musicStream);
		
		//Background
		URL backUrl = Display.class.getResource("/Background.png");
		ImageIcon ii = new ImageIcon(backUrl);
		background = ii.getImage();
		
		addLevel(currentLevel);	
		
		timer = new Timer(10, this);
		timer.setInitialDelay(1500);
		timer.start();
		
	}
	
	public void addLevel(int level) {
		switch (level) {
			case 1: addLevelOne();
					music.loop();
					break;
			case 2: addLevelTwo();
					music.stop();
					music.loop();
					break;
			case 3: addLevelThree();
					music.stop();
					music.loop();
					break;
			case 4: addLevelFour();
					music.stop();
					music.loop();
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
			g2d.drawString("Game Over", 150, 575);
		}
		if (levelWon) {
			g2d.setColor(Color.GREEN);
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 72));
			g2d.drawString("Level Won!", 150, 575);
		}
		
	}
	
	private void drawObjects(Graphics2D g2d) {
		try {
			g2d.drawImage(paddle.getImage(), paddle.getX(),	paddle.getY(), 
					paddle.getWidth(), paddle.getHeight(), this);
			for (Ball ball: balls) {
				g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(),
						ball.getWidth(), ball.getHeight(), this);
			}
			for (Brick brick: bricks) {
				g2d.drawImage(brick.getImage(), brick.getX(), brick.getY(),
						brick.getWidth(), brick.getHeight(), this);
			}
		} catch (NullPointerException e) {
			//Suppress the output of the NPE that doesnt cause issues
		}
		
	}
	
	private void checkCollisions() {
		//Ball and Paddle collision
		Ball balltoAdd = null;
		for (Ball ball: balls) {
			if (ball.isCollidedWith(paddle)) {
				ballPaddle.play();
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
			
			//Ball and brick collision
			Brick brickToRemove = null;
			for (Brick brick: bricks) {
				if (ball.isCollidedWith(brick)) {
					ballBrick.play();
					brickToRemove = brick;
					if (!ball.getSpecial().equals("FireBall")) {
						if (Math.abs(ball.getRect().getMinY() - brick.getRect().getMaxY()) <= 10) {
							ball.setVertDir("down");
						}
						if (Math.abs(ball.getRect().getMaxY() - brick.getRect().getMinY()) <= 10) {
							ball.setVertDir("up");
						}
						if (Math.abs(ball.getRect().getMinX() - brick.getRect().getMaxX()) <= 10) {
							ball.setHoDir("right");
						}
						if (Math.abs(ball.getRect().getMaxX() - brick.getRect().getMinX()) <= 10) {
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
						//Fast paddle brick
						} else if (power.equals("SpeedUp")) {
							paddle.setPaddleSpeed(18);
							specTimer.schedule(new TimerTask() {
								public void run() {
									paddle.setPaddleSpeed(12);
								}
							}, 10000);
						//Slow paddle brick
						} else if (power.equals("SlowDown")) {
							paddle.setPaddleSpeed(6);
							specTimer.schedule(new TimerTask() {
								public void run() {
									paddle.setPaddleSpeed(12);
								}
							}, 10000);
						//Multiball brick
						} else if (power.equals("MultiBall")) {
							Ball newBall = new Ball(ball.getX(), ball.getY());
							newBall.setVertSpeed(ball.getVertSpeed() - 5);
							balltoAdd = newBall;
						}
					}
				}
			}
			if (brickToRemove != null) {
				bricks.remove(brickToRemove);
			}
		}
		if (balltoAdd != null) {
			balls.add(balltoAdd);
		}
	}
	
	public void loseLevel() {
		ball.setVertSpeed(0);
		ball.setHoSpeed(0);
		gameOver = true;
	}
	
	public void winLevel() {
		levelWon = true;
		balls.clear();
		ball = new Ball(Constants.BALL_X_START, Constants.BALL_Y_START);
		balls.add(ball);
		currentLevel++;
		addLevel(currentLevel);
		paddle.setX(Constants.PAD_X_START);
		paddle.setY(Constants.PAD_Y_START);
		timer.stop();
		timer.setInitialDelay(2000);
		timer.start();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (levelWon) {
			levelWon = false;
		}
		Ball ballToRemove = null;
		paddle.move();
		for (Ball ball: balls) {
			ball.move();
			if (ball.checkBounds()) {
				ballToRemove = ball;
			}
		}
		balls.remove(ballToRemove);
		if (balls.isEmpty()) {
			loseLevel();
			timer.stop();
		}
		if (bricks.isEmpty()) {
			winLevel();
		}
		checkCollisions();
		repaint();
	}
	
	
	// ------------- Classes ------------------------
	
	private class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 27 /*ESC*/ || e.getKeyCode() == 80 /*P*/) {
				if (paused) {
					timer.setInitialDelay(0);
					timer.start();
					paused = false;
				} else {
					timer.stop();
					paused = true;
				}
			}
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
					SpecialBrick brick = new SpecialBrick(100 + i*x, 300 + j*y, "FireBall");
					bricks.add(brick);
				} else {
					Brick brick = new Brick(100 + i*x, 300 + j*y);
					bricks.add(brick);
					brick.getWidth();
					brick.getHeight();
				}
			}
		}
	}
	
	private void addLevelTwo() {
		int x = 65;
		int y = 20;
		for (int i = 0; i <= 9; i++) {
			for (int j = 1; j <= 10; j++) {
				if (j == 5 && (i == 2 || i == 7)) {
					SpecialBrick brick = new SpecialBrick(25 + i*x, 100 + j*y, "BigPaddle");
					bricks.add(brick);
				}
				else if((i == 4 || i == 5) && j == 5) {
					SpecialBrick brick = new SpecialBrick(25 + i*x, 100 + j*y, "MultiBall");
					bricks.add(brick);
				}
				else if (j == 6 && (i == 2 || i == 7)) {
					SpecialBrick brick = new SpecialBrick(25 + i*x, 100 + j*y, "SmallPaddle");
					bricks.add(brick);
				}
				else if((i == 4 || i == 5) && j ==6) {
					SpecialBrick brick = new SpecialBrick(25 + i*x, 100 + j*y, "SpeedUp");
					bricks.add(brick);
				}
				else if (i == 0 || i == 2 || i == 4 || i == 5 || i == 7 || i == 9 || j == 1 || j == 10) {
					Brick brick = new Brick(25 + i*x, 100 + j*y);
					bricks.add(brick);
				}
			}
		}
	}
	
	private void addLevelThree() {
		int x = 65;
		int y = 20;
		for (int i = 1; i <= 7; i++) {
			for (int j = 1; j <= 25; j++) {
				if (j <= 2 || j >= 24 || i == 1 || i == 7 || (i == 3 && j >= 5) || (i == 5 && j <= 21)) {
					Brick brick = new Brick (50 + i*x, 100 + j*y);
					bricks.add(brick);
				} 
			}
		}
	}
	
	private void addLevelFour() {
		int x = 65;
		int y = 20;
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 40; j++) {
				if (j == 37 && (i == 2 || i == 8)) {
					SpecialBrick brick = new SpecialBrick(i*x, j*y, "BigPaddle");
					bricks.add(brick);
				}
				else if (j == 35 && i == 5) {
					SpecialBrick brick = new SpecialBrick(i*x, j*y, "SpeedUp");
					bricks.add(brick);
				}
				else if (j == 28 && (i == 4 || i == 6)) {
					SpecialBrick brick = new SpecialBrick(i*x, j*y, "SlowDown");
					bricks.add(brick);
				}
				else if (j == 13 && (i == 4 || i == 6)) {
					SpecialBrick brick = new SpecialBrick(i*x, j*y, "SmallPaddle");
					bricks.add(brick);
				}
				else if (j == 20 && (i == 2 || i == 8)) {
					SpecialBrick brick = new SpecialBrick(i*x, j*y, "FireBall");
					bricks.add(brick);
				}
				else if ((j == 6 && i ==5) || (j == 4 && (i == 2 || i == 8))) {
					SpecialBrick brick = new SpecialBrick(i*x, j*y, "MultiBall");
					bricks.add(brick);
				}
				else if ((i == 1 || i == 9) || ((i == 2 || i == 8) && (j < 38 && j > 3)) ||
						((i == 3 || i == 7) && (j < 35 && j > 6)) ||
						((i == 4 || i == 6) && ((j < 32 && j > 9) || (j <= 3 || j >= 38))) ||
						(i == 5 && (j <= 6 || j >= 35))) {
					Brick brick = new Brick(i*x, j*y);
					bricks.add(brick);
				}
			}
		}
	}
	
	
	
	@SuppressWarnings("unused")
	private void addLoss() {
		int x = 65;
		int y = 20;
		for (int i = 1; i <= 11; i++) {
			for (int j = 1; j <= 25; j++) {
				if ((i == 1 && j >= 16) || (i == 2 && j <= 10) || (i == 3 && j >= 16) || 
						(i == 7 && (j <= 10 || j >= 16)) || (i == 9 && (j <= 10 || j >= 24)) || (i > 9 && j >= 24)) {
					Brick brick = new Brick(10 + i*x, 100 + j*y);
					bricks.add(brick);
				}
			}
		}
	}


	
	
}
