package panels;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import utils.PNGEnum;
import utils.Rel;

public class ButtonPanel extends JPanel { // panel for Station and Info buttons

    private JButton stationButton, infoButton;

    public ButtonPanel() {

        ImageIcon stationPNG = PNGEnum.STATIONBUTTON.getImage();
        stationButton = new JButton();
        stationButton.setIcon(stationPNG);
        stationButton.setBounds(Rel.X(27), Rel.Y(15), stationPNG.getIconWidth(), stationPNG.getIconHeight());
        stationButton.setOpaque(false);
        stationButton.setContentAreaFilled(false);
        stationButton.setBorderPainted(false);
        stationButton.setFocusPainted(false);

        ImageIcon infoPNG = PNGEnum.INFOICON.getImage();
        infoButton = new JButton();
        infoButton.setIcon(infoPNG);
        infoButton.setBounds(Rel.X(120), Rel.Y(535), infoPNG.getIconWidth(), infoPNG.getIconHeight());
        infoButton.setOpaque(false);
        infoButton.setContentAreaFilled(false);
        infoButton.setBorderPainted(false);
        infoButton.setFocusPainted(false);


        
        setOpaque(false);
        //setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK));
        //SwingUtilities.invokeLater(() -> {
            setLayout(null);
            setSize(new Dimension(200, 800));
            add(stationButton);
            add(infoButton);
        //});
    }

    public JButton getStationButton() { return stationButton; }

    public JButton getInfoButton() { return infoButton; }

    public void setEnabled(boolean enabled) {
        stationButton.setEnabled(enabled);
    }
}
