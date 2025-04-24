package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import models.Player;
import utils.PNGEnum;
import utils.Rel;

public class PlayerPanel extends JPanel  {

	private Player[] playerArray;
	private JLabel bgImage;

	public PlayerPanel(Player[] pa) {
		playerArray = pa;
		bgImage = new JLabel(PNGEnum.PLAYERPANELIMG.getImage());
		bgImage.setSize(
				PNGEnum.PLAYERPANELIMG.getWidth(),
				PNGEnum.PLAYERPANELIMG.getHeight());
		
		setSize(
				new Dimension(
						PNGEnum.PLAYERPANELIMG.getWidth(),
						PNGEnum.PLAYERPANELIMG.getHeight()));
		
		setOpaque(false);
		setLayout(null);
		updatePanel();
	}

	public void updatePanel() {
		SwingUtilities.invokeLater(() -> {
			removeAll();
		});
		for (int i = 0; i < playerArray.length; i++) {
			final int index = i; // Create a final copy of i
			JLabel trainLabel = new JLabel(
					String.valueOf(playerArray[index].getTrains()));
			JLabel stationsLabel = new JLabel(
					String.valueOf(playerArray[index].getStations()));
			JLabel pointsLabel = new JLabel(
					String.valueOf(playerArray[index].getPoints()));
			Font font = new Font("Arial", Font.PLAIN, Rel.W(20));

			trainLabel.setForeground(Color.BLACK); // Set text color to black
			trainLabel.setBounds(Rel.X(130), Rel.Y(index * 97 + 35), Rel.W(100), Rel.H(20));
			trainLabel.setFont(font);

			stationsLabel.setForeground(Color.BLACK);
			stationsLabel.setBounds(Rel.X(130), Rel.Y(index * 97 + 63), Rel.W(100), Rel.H(20));
			stationsLabel.setFont(font);

			pointsLabel.setForeground(Color.BLACK);
			pointsLabel.setBounds(Rel.X(15), Rel.Y(index * 97 + 73), Rel.W(100), Rel.H(20));
			pointsLabel.setFont(font);

			SwingUtilities.invokeLater(() -> {
				add(trainLabel);
				add(stationsLabel);
				setComponentZOrder(trainLabel, (index * 3));
				setComponentZOrder(stationsLabel, (index * 3) + 1);
				setComponentZOrder(pointsLabel, (index * 3) + 2);
			});
		}
		SwingUtilities.invokeLater(() -> {
			add(bgImage);
			setComponentZOrder(bgImage, 12);
			revalidate();
			repaint();
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.YELLOW);
		g.drawRect(0, 0, getWidth(), getHeight() / 4 + Rel.H(10));
	}

	public Player[] getPlayerArray() {
		return playerArray;
	}
	public JLabel getBgImage() {
		return bgImage;
	}
}
