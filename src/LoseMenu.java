import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LoseMenu extends JFrame implements ActionListener {

	private JButton retryLevel, mainMenu;
	private SoundEffect music;
	private JPanel loseMenu;
	private int level;
	private JLabel backgr;
	
	public LoseMenu(int currentLevel, JLabel backg) {
		backgr = backg;
		level = currentLevel;
		setTitle("Breakout");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Constants.WIDTH, Constants.HEIGHT);
		setResizable(false);
		setVisible(true);
		URL ballUrl = Menu.class.getResource("/Ball1.png");
		setIconImage(Constants.ICON);
		
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
	
	private void addMenu() {
		loseMenu = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				backgr.getIcon().paintIcon(this, g, 0, 0);
				g.setColor(Color.BLACK);
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 72));
				g.drawString("Game Over", 145, 180);
				g.setColor(Color.RED);
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 72));
				g.drawString("Game Over", 150, 175);
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainMenu) {
			this.dispose();
			music.stop();
			new Menu();
		} else if (e.getSource() == retryLevel) {
			this.dispose();
			music.stop();
			new Breakout(level);
		}
	}

}
