package panels;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import utils.PNGEnum;
import utils.Rel;

public class ButtonPanel extends JPanel { // panel for Station and Info buttons

    private JButton stationButton, infoButton;

    public ButtonPanel() {

        ImageIcon stationPNG = PNGEnum.STATIONBUTTON.getImage();
        stationButton = new JButton();
        stationButton.setIcon(stationPNG);
        stationButton.setBounds(Rel.X(30), 0, stationPNG.getIconWidth(), stationPNG.getIconHeight());
        stationButton.setOpaque(false);
        stationButton.setContentAreaFilled(false);
        stationButton.setBorderPainted(false);
        stationButton.setFocusPainted(false);

        ImageIcon infoPNG = PNGEnum.INFOICON.getImage();
        infoButton = new JButton();
        infoButton.setIcon(infoPNG);
        infoButton.setBounds(Rel.X(120), Rel.Y(380), infoPNG.getIconWidth(), infoPNG.getIconHeight());
        infoButton.setOpaque(false);
        infoButton.setContentAreaFilled(false);
        infoButton.setBorderPainted(false);
        infoButton.setFocusPainted(false);

        setSize(new Dimension(200, 450));
        setOpaque(false);
        //setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK));
        SwingUtilities.invokeLater(() -> {
            setLayout(null);
            add(stationButton);
            add(infoButton);
        });
    }

    public JButton getStationButton() { return stationButton; }

    public JButton getInfoButton() { return infoButton; }
}
