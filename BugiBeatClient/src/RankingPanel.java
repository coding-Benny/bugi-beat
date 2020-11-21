
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class RankingPanel extends JPanel {
	private Image rankingPanel_bg= new ImageIcon(Main.class.getResource("/images/rangking-bg.png")).getImage();
	public void paint(Graphics g) {
		g.drawImage(rankingPanel_bg, 0, 0, null);
	}
	public RankingPanel() {
		setSize(450, 335);
		setOpaque(true);
		setLayout(null);
	}
}