import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The Display class is the class that handles the playing of the game
 * and all of the levels themselves. This is the most important class
 * to the game.
 * 
 * @author reardj3
 * @version 1.0.2 (August 9th, 2018)
 *
 */

@SuppressWarnings("serial")
public class Display extends JPanel implements ActionListener {
	
	private Paddle paddle;
	private Ball ball;
	private ArrayList<Ball> balls;
	private Timer timer;
	private ArrayList<Brick> bricks;
	private boolean levelWon;
	private Image background;
	private int currentLevel;
	private boolean paused;
	private SoundEffect ballBrick, ballPaddle, music, fire, special;
	private int scale;

	/** This constructor is used in the general case where
	 * a specific level is not given, therefore the game
	 * loads level one.
	 * @throws InterruptedException
	 */
	public Display() throws InterruptedException {
		currentLevel = 1;
		initialize();
	}

	/** This constructor is used in the general case where
	 * a specific level is given
	 * @param level is the level to load in
	 * @throws InterruptedException
	 */
	public Display(int level) throws InterruptedException {
		currentLevel = level;
		initialize();
	}

	/** This method sets up all required elements of the screen, including a paddle,
	 * a ball, and the level itself. The method also initalizes several variables
	 * such as the levelWon boolean.
	 * @throws InterruptedException
	 */
	private void initialize() throws InterruptedException {
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);
		setDoubleBuffered(true);        
		
		// Make the cursor invisible when playing a level
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		java.awt.Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
		setCursor(blankCursor);
		
		//Set scale
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		scale = (int) screenSize.getHeight()/1080;
		
		//Initialize variables/objects
		paddle = new Paddle(Constants.PAD_X_START, Constants.PAD_Y_START);
		ball = new Ball(Constants.BALL_X_START, Constants.BALL_Y_START);
		balls = new ArrayList<Ball>();
		balls.add(ball);
		bricks = new ArrayList<Brick>();
		levelWon = false;
		
		//Initialize Sound effects
		InputStream brickStream = Display.class.getResourceAsStream("/BallBrick.wav");
		InputStream paddleStream = Display.class.getResourceAsStream("/BallPaddle.wav");
		InputStream musicStream = Display.class.getResourceAsStream("/LevelMusic.wav");
		InputStream fireStream = Display.class.getResourceAsStream("/Fire.wav");
		InputStream specialStream = Display.class.getResourceAsStream("/Special.wav");
		ballBrick = new SoundEffect(brickStream);
		ballPaddle = new SoundEffect(paddleStream);
		music = new SoundEffect(musicStream);
		fire = new SoundEffect(fireStream);
		special = new SoundEffect(specialStream);
		
		//Set Background image
		URL backUrl = Display.class.getResource("/Background.png");
		ImageIcon ii = new ImageIcon(backUrl);
		background = ii.getImage();
		addLevel(currentLevel);	
		
		//Begin the game by repeatedly updating the frame with a timer
		timer = new Timer(10, this);
		timer.setInitialDelay(1500);
		timer.start();
		
	}
	
	/** This is the method responsible for adding the levels.
	 * Given a level to add, the method calls the applicable 
	 * add level method, and re-loops the music
	 * @param level is the level to add
	 */
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
					break;
			case 5: addLevelFive();
					music.stop();
					music.loop();
					break;
			case 6: addLevelSix();
					music.stop();
					music.loop();
					break;
			default:
					addLevelOne();
					break;
		}
		
	}
	
	/** This method is responsible for the graphics of the game.
	 * Each time the frame is updated by the game timer, this method
	 * repaints all objects on the screen. 
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED);
        
		drawObjects(g2d);
		
		if (levelWon) {
			g2d.setColor(Color.GREEN);
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 72));
			g2d.drawString("Level Won!", 150, 575);
		}
		
	}
	
	/** This method is used to capture the current image of the screen.
	 * It is used in order to display the image later on in the 
	 * "level lost" screen
	 * @param comp is the component to capture (usually "this")
	 * @return the BufferedImage screenshot
	 */
	private BufferedImage getScreenshot(Component comp) {
		BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		comp.paint(bi.getGraphics());
		return bi;
	}
	
	
	/** This method is responsible for drawing all on screen objects */
	private void drawObjects(Graphics2D g2d) {
		try {
		  //Set graphics
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        g2d.scale(screenSize.getHeight()/1080, screenSize.getHeight()/1080);
		    g2d.drawImage(background, 0, 0, this);
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
	
	/** This method handles the game physics and checks what should be done at each update.
	 * It checks for ball collisions with bricks and with the paddle, and handles the events.
	 */
	private void checkCollisions() {
		//Ball and Paddle collision
		Ball balltoAdd = null;
		for (Ball ball: balls) {
			if (ball.isCollidedWith(paddle)) {
				ballPaddle.play();
				ball.setVertDir("up");
				
				if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (20*paddle.getWidth()/21)) {
					ball.setHoDir("right");
					ball.setHoSpeed(10);
					ball.setVertSpeed(1);				
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (19*paddle.getWidth()/21)) {
					ball.setHoDir("right");
					ball.setHoSpeed(9);
					ball.setVertSpeed(2);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (18*paddle.getWidth()/21)) {
					ball.setHoDir("right");
					ball.setHoSpeed(8);
					ball.setVertSpeed(3);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (17*paddle.getWidth()/21)) {
					ball.setHoDir("right");
					ball.setHoSpeed(7);
					ball.setVertSpeed(4);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (16*paddle.getWidth()/21)) {
					ball.setHoDir("right");
					ball.setHoSpeed(6);
					ball.setVertSpeed(5);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (15*paddle.getWidth()/21)) {
					ball.setHoDir("right");
					ball.setHoSpeed(5);
					ball.setVertSpeed(6);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (14*paddle.getWidth()/21)) {
					ball.setHoDir("right");
					ball.setHoSpeed(4);
					ball.setVertSpeed(7);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (13*paddle.getWidth()/21)) {
					ball.setHoDir("right");
					ball.setHoSpeed(3);
					ball.setVertSpeed(8);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (12*paddle.getWidth()/21)) {
					ball.setHoDir("right");
					ball.setHoSpeed(2);
					ball.setVertSpeed(9);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (11*paddle.getWidth()/21)) {
					ball.setHoDir("right");
					ball.setHoSpeed(1);
					ball.setVertSpeed(10);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (10*paddle.getWidth()/21)) {
					ball.setHoSpeed(0);
					ball.setVertSpeed(11);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (9*paddle.getWidth()/21)) {
					ball.setHoDir("left");
					ball.setHoSpeed(1);
					ball.setVertSpeed(10);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (8*paddle.getWidth()/11)) {
					ball.setHoDir("left");
					ball.setHoSpeed(2);
					ball.setVertSpeed(9);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (7*paddle.getWidth()/21)) {
					ball.setHoDir("left");
					ball.setHoSpeed(3);
					ball.setVertSpeed(8);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (6*paddle.getWidth()/21)) {
					ball.setHoDir("left");
					ball.setHoSpeed(4);
					ball.setVertSpeed(7);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (5*paddle.getWidth()/21)) {
					ball.setHoDir("left");
					ball.setHoSpeed(5);
					ball.setVertSpeed(6);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (4*paddle.getWidth()/21)) {
					ball.setHoDir("left");
					ball.setHoSpeed(6);
					ball.setVertSpeed(5);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (3*paddle.getWidth()/21)) {
					ball.setHoDir("left");
					ball.setHoSpeed(7);
					ball.setVertSpeed(4);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (2*paddle.getWidth()/21)) {
					ball.setHoDir("left");
					ball.setHoSpeed(8);
					ball.setVertSpeed(3);
				}
				else if (ball.getRect().getMinX() > paddle.getRect().getMinX() + (paddle.getWidth()/21)) {
					ball.setHoDir("left");
					ball.setHoSpeed(9);
					ball.setVertSpeed(2);
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
						if (Math.abs(ball.getRect().getMinY() - brick.getRect().getMaxY()) <= (10 * scale)) {
							ball.setVertDir("down");
						}
						if (Math.abs(ball.getRect().getMaxY() - brick.getRect().getMinY()) <= (10 * scale)) {
							ball.setVertDir("up");
						}
						if (Math.abs(ball.getRect().getMinX() - brick.getRect().getMaxX()) <= (10 * scale)) {
							ball.setHoDir("right");
						}
						if (Math.abs(ball.getRect().getMaxX() - brick.getRect().getMinX()) <= (10 * scale)) {
							ball.setHoDir("left");
						}
					}
					if (brick.getClass() == SpecialBrick.class) {
						String power = ((SpecialBrick) brick).getPowerUp();
						java.util.Timer specTimer = null;
						if (power != "Hard") {
							special.play();
							specTimer = new java.util.Timer("Special Timer");
						}
						int x = paddle.getX();
						int y = paddle.getY();
						//Big Paddle brick
						if (power.equals("BigPaddle")) {
							paddle = new SpecialPaddle(x - 55, y, "Long");
							specTimer.schedule(new TimerTask() {
								public void run() {
									paddle = new Paddle(paddle.getX() + 55, paddle.getY());
								}
							}, 10000);
						//Small paddle brick
						} else if (power.equals("SmallPaddle")) {
							paddle = new SpecialPaddle(x + 37 , y, "Short");	
							specTimer.schedule(new TimerTask() {
								public void run() {
									paddle = new Paddle(paddle.getX() - 37, paddle.getY());
								}
							}, 10000);
						//Fireball brick
						} else if (power.equals("FireBall")) {
							fire.loop();
							ball.setSpecial("FireBall");
							specTimer.schedule(new TimerTask() {
								public void run() {
									ball.setSpecial("");
									fire.stop();
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
							Random rand = new Random();
							newBall.setVertSpeed(rand.nextInt(9) + 1);
							balltoAdd = newBall;
						} else if (power.equals("Hard")) {
							if (((SpecialBrick) brick).getHitsLeft() == 2) {
								((SpecialBrick) brick).hitBrick();
								URL crackedURL = SpecialBrick.class.getResource("/Cracked" + brick.getColor() + "Brick.png");
								ImageIcon ii = new ImageIcon(crackedURL);
								brick.setImage(ii.getImage());
								brickToRemove = null;
							}
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
	
	
	/** This method takes a screenshot of the lost level and 
	 * creates a new lose menu for the user.
	 */
	public void loseLevel() {
		ball.setVertSpeed(0);
		ball.setHoSpeed(0);
		JLabel capture = new JLabel();
		capture.setIcon(new ImageIcon(getScreenshot(this)));
        int x = this.getParent().getParent().getParent().getParent().getX();
        int y = this.getParent().getParent().getParent().getParent().getY();
		((Breakout) this.getParent().getParent().getParent().getParent()).dispose();
		music.stop();
		new LoseMenu(currentLevel, capture, x, y);
		
	}
	
	/** This method advances the game to the next level after displaying 
	 * the message "Level Won" to the player
	 */
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
	
	/** This is the method that gets called at every "tick" of the timer.
	 * It checks if the level is won, moves the ball(s), and calls
	 * the check collisions method
	 */
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
	
	/** This class is used to accept input from the left and right
	 * keys and make the paddle move accordingly. Additionally this
	 * class also makes both the "P" and "Esc" keys pause buttons
	 * @author reardj3
	 *
	 */
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
	
	
	/** The following methods are all used to create the levels.
	 * Each metod corresponds to one level and i created by placing 
	 * bricks in a 9x40 grid using nested for loops
	 */	
	
	
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
				if ((i == 3 && j == 5) || (i == 5 && j == 21)) {
					SpecialBrick brick = new SpecialBrick(50 + i*x, 100 + j*y, "MultiBall");
					bricks.add(brick);
				}
				else if ((i == 3 && j == 21) || (i == 5 && j == 5)) {
					SpecialBrick brick = new SpecialBrick(50 + i*x, 100 + j*y,  "FireBall");
					bricks.add(brick);
				}
				else if (j <= 2 || j >= 24 || i == 1 || i == 7 || (i == 3 && j >= 5) || (i == 5 && j <= 21)) {
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
	
	private void addLevelFive() {
		int x = 65;
		int y = 20;
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 27; j++) {
				if ((i == 1 || i == 9) && j == 27) {
					SpecialBrick brick = new SpecialBrick(x*i, y*j, "SmallPaddle");
					bricks.add(brick);
				} else if ((i == 1 || i == 9) && (j > 18)) {
					SpecialBrick brick = new SpecialBrick(x*i, y*j, "Hard", "Purple");
					bricks.add(brick);
				} else if ((i == 2 || i == 8) && (j < 22 && j > 8)) {
					SpecialBrick brick = new SpecialBrick(x*i, y*j, "Hard", "Purple");
					bricks.add(brick);
				} else if ((i == 3 || i == 7) && (j == 5 || j == 14)) {
					SpecialBrick brick = new SpecialBrick(x*i, y*j, "Hard", "Orange");
					bricks.add(brick);
				} else if ((i == 3 || i == 7) && (j < 19 && j > 4)) {
					SpecialBrick brick = new SpecialBrick(x*i, y*j, "Hard", "Purple");
					bricks.add(brick);
				} else if ((i == 4 || i == 6) && j == 8) {
					SpecialBrick brick = new SpecialBrick(x*i, y*j, "MultiBall");
					bricks.add(brick);
				} else if ((i == 4 || i == 6) && j == 25) {
					SpecialBrick brick = new SpecialBrick(x*i, y*j, "SmallPaddle");
					bricks.add(brick);
				} else if ((i == 4 || i == 6) && (j == 6 || j == 15)) {
					SpecialBrick brick = new SpecialBrick(x*i, y*j, "Hard", "Orange");
					bricks.add(brick);
				} else if ((i == 4 || i == 6) && (j < 26 && j > 2)) {
					SpecialBrick brick = new SpecialBrick(x*i, y*j, "Hard", "Purple");
					bricks.add(brick);
				} else if (i == 5 && j == 15) {
					SpecialBrick brick = new SpecialBrick(x*i, y*j, "Hard", "Orange");
					bricks.add(brick);
				} else if (i == 5 && (j < 19 && j > 2)) {
					SpecialBrick brick = new SpecialBrick(x*i, y*j, "Hard", "Purple");
					bricks.add(brick);
				}
			}
		}
		
	}
	
	private void addLevelSix() {
		int x = 65;
		int y = 20;
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 31; j++) {
				if ((j == 18 || j == 19) && i == 5) {
					SpecialBrick brick = new SpecialBrick(x*i, y*j, "MultiBall");
					bricks.add(brick);
				} else if (((j+2)%8 == 0) || ((j+1)%8 == 0)) {
					SpecialBrick brick = new SpecialBrick(x*i, y*j, "Hard");
					bricks.add(brick);
				} else if ((j == 10 || j == 11 || j == 26 || j == 27) && 
						(i != 3 && i != 7)) {
					Brick brick = new Brick(x*i, y*j);
					bricks.add(brick);
				} else if ((j == 18 || j == 19) && 
						(i != 1 && i != 5 && i != 9)) {
					Brick brick = new Brick(x*i, y*j);
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
