import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class BackgroundPanel extends JComponent {
	private Image image;
	public BackgroundPanel() {
		ImageIcon ii = new ImageIcon("src/Images/Background.png");
		image = ii.getImage();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}

}
