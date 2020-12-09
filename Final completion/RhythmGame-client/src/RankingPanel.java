
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class RankingPanel extends JPanel {
	private Image rankingPanel_bg= new ImageIcon(Main.class.getResource("/images/rangking-bg.png")).getImage();
	private JScrollPane rankingScrollPane = new JScrollPane();
	private JList<String> rankList = new JList<String>();
	public static DefaultListModel<String> rank = new DefaultListModel<String>();
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(rankingPanel_bg, 0, 0, null);
	}
	
	public RankingPanel() {
		setSize(450, 335);
		setLayout(null);
		setOpaque(false);
		
		rankingScrollPane.setBounds(40, 85, 360, 230);
		rankingScrollPane.setBorder(null);
		rankingScrollPane.setOpaque(false);
		rankingScrollPane.setViewportView(rankList);
		rankingScrollPane.getViewport().setOpaque(false);
		rankingScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		rankingScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		rankList.setOpaque(false);
		rankList.setFixedCellHeight(30);
		rankList.setFont(new Font("산돌수필B", Font.BOLD, 25));
		rankList.setForeground(Color.WHITE);
		rankList.setModel(rank);
		rankList.ensureIndexIsVisible(rank.getSize());
		
		DefaultListCellRenderer renderer = new DefaultListCellRenderer();
		renderer.setOpaque(false);
		renderer.setForeground(Color.WHITE);
		rankList.setSelectionForeground(Color.GRAY);
		rankList.setCellRenderer(renderer);
		
		add(rankingScrollPane);
		setVisible(true);
	}
}