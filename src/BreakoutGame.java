import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class BreakoutGame extends JFrame implements ActionListener {
	
	private Paddle paddle;
	private Ball ball;
	private Timer timer;
	
	public static void main(String[] arg) {
		new BreakoutGame();
	}	
	
	public BreakoutGame() {
		this.setTitle("Breakout");
		this.setVisible(true);
		this.setSize(Constants.WIDTH, Constants.HEIGHT);
		this.setResizable(false);
		this.addKeyListener(new MyKeyAdapter());
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 3);
		
		paddle = new Paddle(400, 1000);
		ball = new Ball(400 + paddle.getWidth()/2, 985);
		Brick brick = new Brick(100, 400);

		revalidate();
		repaint();
		
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
	}
	
	private void checkCollisions() {
		if (ball.isCollidedWith(paddle)) {
			ball.setVertDir("up");
			if (ball.getHoSpeed() == 0 && paddle.getDX() == 1) {
				ball.setHoDir("right");
			} else if (ball.getHoSpeed() == 0 && paddle.getDX() == -1) {
				ball.setHoDir("left");
			}
			if (ball.getHoDir().equals("left")) {
				ball.setHoSpeed(Math.abs(ball.getHoSpeed() - paddle.getDX()));	
			} else {
				ball.setHoSpeed(Math.abs(ball.getHoSpeed() + paddle.getDX()));	
			}
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


