import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This is the menu that pops up after a player loses
 * a level, allowing them to return to the main menu
 * or retry the level
 * 
 * @author reardj3
 *@version 1.0.2 (August 9th, 2018)
 */
@SuppressWarnings("serial")
public class LoseMenu extends JFrame implements ActionListener {

	private JButton retryLevel, mainMenu;
	private SoundEffect music;
	private JPanel loseMenu;
	private int level;
	private JLabel backgr;
	
	/** The constructor directly initializes all of the buttons
	 * and background image
	 * @param currentLevel is the level that was lost
	 * @param backg is the image of the lost level
	 */
	public LoseMenu(int currentLevel, JLabel backg, int x, int y) {
		backgr = backg;
		level = currentLevel;
		setTitle("Breakout");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();     
        setSize((int)(screenSize.height * 0.66), screenSize.height);
		setResizable(false);
		setVisible(true);
		setIconImage(Constants.ICON);
		setLocation(x, y);
		
		retryLevel = new JButton("Retry Level");
		retryLevel.setFont(Constants.MENU_FONT);
		retryLevel.addActionListener(this);
		mainMenu = new JButton("Main Menu");
		mainMenu.setFont(Constants.MENU_FONT);
		mainMenu.addActionListener(this);
		
		InputStream musicStream = LoseMenu.class.getResourceAsStream("/LoseMusic.wav");
		music = new SoundEffect(musicStream);
		addMenu();
	}
	
	/** This method adds the menu to the screen,
	 * including the Game Over text at the top
	 */
	private void addMenu() {
		loseMenu = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
			    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                double scale = screenSize.getHeight()/1080;
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				backgr.getIcon().paintIcon(this, g, 0, 0);
				g2d.scale(scale, scale);
				g2d.setColor(Color.BLACK);
				g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 72));
				g2d.drawString("Game Over", 145, 180);
				g2d.setColor(Color.RED);
				g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 72));
				g2d.drawString("Game Over", 150, 175);
			}
		};
		loseMenu.setLayout(new FlowLayout());
		JPanel subPanel = new JPanel(new GridLayout(20, 2, 10, 10));
		subPanel.setBackground(Constants.CLEAR);
		loseMenu.add(subPanel);
		
		for (int i = 0; i < 6; i++) {
			subPanel.add(new JLabel());
		}
		subPanel.add(retryLevel);
		subPanel.add(new JLabel());
		subPanel.add(mainMenu);
		
		this.add(loseMenu);
		music.loop();
		this.paint(this.getGraphics());
	}
	
	/** This method handles button presses within the menu */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainMenu) {
			this.dispose();
			music.stop();
			new Menu();
		} else if (e.getSource() == retryLevel) {
			this.dispose();
			music.stop();
			new Breakout(level, this.getX(), this.getY());
		}
	}

}
