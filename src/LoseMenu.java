import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
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
	
	public LoseMenu(int currentLevel) {
		level = currentLevel;
		setTitle("Breakout");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Constants.WIDTH, Constants.HEIGHT);
		setResizable(false);
		setVisible(true);
		URL ballUrl = Menu.class.getResource("/Ball1.png");
		setIconImage(new ImageIcon(ballUrl).getImage());
		
		retryLevel = new JButton("Retry Level");
		retryLevel.setFont(Constants.MENU_FONT);
		retryLevel.addActionListener(this);
		mainMenu = new JButton("Main Menu");
		mainMenu.setFont(Constants.MENU_FONT);
		mainMenu.addActionListener(this);
		
		InputStream musicStream = LoseMenu.class.getResourceAsStream("/GameOver.wav");
		music = new SoundEffect(musicStream);
		addMenu();
	}
	
	private void addMenu() {
		URL backUrl = Menu.class.getResource("/Background.png");
		ImageIcon ii = new ImageIcon(backUrl);
		Image background = ii.getImage();
		loseMenu = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(background, 0, 0, null);
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
