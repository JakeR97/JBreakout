import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class is the main menu of the game,
 * allowing a player to launch levels.
 * 
 * @author reardj3
 * @version 1.0.2 (August 9th, 2018)
 */

@SuppressWarnings("serial")
public class Menu extends JFrame implements ActionListener {
	
	private JButton quit, levels, back, start;
	private ArrayList<JButton> levelList;
	private JPanel menu, levelMenu;
	private SoundEffect music;
	
	/** The constructor for this class creates a main menu
	 * for the "home page" of the game, as well as setting 
	 * the background and music.
	 */
	public Menu() {
		setTitle("Breakout");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();     
        setSize((int)(screenSize.height * 0.66), screenSize.height);
		setResizable(false);
		setVisible(true);
		InputStream musicStream = Menu.class.getResourceAsStream("/MainMenuMusic.wav");
		setIconImage(Constants.ICON);
		music = new SoundEffect(musicStream);
		addMenu();
	}
	
	/** This method is responsible for adding the 
	 * actual menu components to the JFrame such as the 
	 * buttons, the background images, etc.
	 */
	private void addMenu() {
		URL backUrl = Menu.class.getResource("/Background.png");
		ImageIcon ii = new ImageIcon(backUrl);
		Image background = ii.getImage();
		menu = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				double scale = screenSize.getHeight()/1080;
				Graphics2D g2d = (Graphics2D) g;
				g2d.scale(scale, scale);
				g2d.drawImage(background, 0, 0, null);
			}
		};
		menu.setLayout(new FlowLayout());
		JPanel subPanel = new JPanel(new GridLayout(20, 2, 10, 10));
		subPanel.setBackground(Constants.CLEAR);
		
		start = new JButton("New Game");
		start.setFont(Constants.MENU_FONT);
		start.addActionListener(this);
		quit = new JButton("Quit");
		quit.setFont(Constants.MENU_FONT);
		quit.addActionListener(this);
		levels = new JButton("Levels");
		levels.setFont(Constants.MENU_FONT);
		levels.addActionListener(this);
		
		for (int i = 0; i < 7; i++) {
			subPanel.add(new JLabel());
		}
		subPanel.add(start);
		subPanel.add(levels);
		subPanel.add(quit);
		menu.add(subPanel);
		
		this.add(menu);
		music.loop();
	}

	/** This method adds the level-sub menu allowing a player
	 * to jump right in to any level he/she chooses
	 */
	private void addLevelMenu() {
		levelList = new ArrayList<JButton>();
		initLevels();
		this.remove(menu);
		URL backUrl = Menu.class.getResource("/Background.png");
		ImageIcon ii = new ImageIcon(backUrl);
		Image background = ii.getImage();
		levelMenu = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                double scale = screenSize.getHeight()/1080;
                Graphics2D g2d = (Graphics2D) g;
                g2d.scale(scale, scale);
                g2d.drawImage(background, 0, 0, null);
			}
		};
		levelMenu.setLayout(new FlowLayout());
		JPanel subPanel = new JPanel(new GridLayout(20, 2, 10, 10));
		subPanel.setBackground(Constants.CLEAR);
		
		subPanel.add(new JLabel());
		subPanel.add(new JLabel());
		for (JButton level: levelList) {
			subPanel.add(level);
		}
		subPanel.add(new JLabel());
		subPanel.add(back);
		
		levelMenu.add(subPanel);
		this.add(levelMenu);
	}

	/** This method is responsible for initializing
	 * all of the buttons for the level sub-menu
	 */
	private void initLevels() {
		JButton level1 = new JButton("Level 1");
		JButton level2 = new JButton("Level 2");
		JButton level3 = new JButton("Level 3");
		JButton level4 = new JButton("Level 4");
		JButton level5 = new JButton("Level 5");
		JButton level6 = new JButton("Level 6");
		JButton level7 = new JButton("Level 7");
		JButton level8 = new JButton("Level 8");
		JButton level9 = new JButton("Level 9");
		JButton level10 = new JButton("Level 10");
		back = new JButton("Back");
		back.setFont(Constants.MENU_FONT);
		back.addActionListener(this);
		levelList.add(level1);
		levelList.add(level2);
		levelList.add(level3);
		levelList.add(level4);
		levelList.add(level5);
		levelList.add(level6);
		levelList.add(level7);
		levelList.add(level8);
		levelList.add(level9);
		levelList.add(level10);
		for (JButton level: levelList) {
			level.setFont(Constants.MENU_FONT);
			level.addActionListener(this);
		}
		
	}

	
	/** This method handles all of the button 
	 * presses within the menus
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == quit) {
			System.exit(0);
		} else if (e.getSource() == levels) {
			addLevelMenu();
		} else if (e.getSource() == back) {
			remove(levelMenu);
			addMenu();
		} else if (e.getSource() == start) {
			this.dispose();
			music.stop();
			new Breakout(this.getX(), this.getY());
		} else if (e.getSource().getClass() == JButton.class) {
			this.dispose();
			music.stop();
			new Breakout(levelList.indexOf(e.getSource()) + 1, this.getX(), this.getY());
		}
		revalidate();
		repaint();
	}
	

}
