package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

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
		SwingUtilities.invokeLater(() -> {
			setLayout(null);
			updatePanel();
		});
	}

	public void updatePanel() {
		removeAll();
		add(bgImage);
		for (int i = 0; i < playerArray.length; i++) {
			JLabel trainLabel = new JLabel(
					String.valueOf(playerArray[i].getTrains()));
			JLabel stationsLabel = new JLabel(
					String.valueOf(playerArray[i].getStations()));
			JLabel pointsLabel = new JLabel(
					String.valueOf(playerArray[i].getPoints()));
			Font font = new Font("Arial", Font.PLAIN, Rel.W(20));

			trainLabel.setForeground(Color.BLACK); // Set text color to black
			trainLabel.setBounds(Rel.X(130), Rel.Y(i * 97 + 35), Rel.W(100), Rel.H(20));
			trainLabel.setFont(font);

			stationsLabel.setForeground(Color.BLACK);
			stationsLabel.setBounds(Rel.X(130), Rel.Y(i * 97 + 63), Rel.W(100), Rel.H(20));
			stationsLabel.setFont(font);

			pointsLabel.setForeground(Color.BLACK);
			pointsLabel.setBounds(Rel.X(15), Rel.Y(i * 97 + 73), Rel.W(100), Rel.H(20));
			pointsLabel.setFont(font);

			add(trainLabel);
			add(stationsLabel);
			setComponentZOrder(trainLabel, (i * 3));
			setComponentZOrder(stationsLabel, (i * 3) + 1);
			setComponentZOrder(pointsLabel, (i * 3) + 2);
		}
		setComponentZOrder(bgImage, 12);
		revalidate();
		repaint();
	}

}
