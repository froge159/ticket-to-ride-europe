package panels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import utils.ImageEnum;
import utils.Rel;


public class RulesPanel extends JPanel {

    private JButton returnButton;
    private BufferedImage rulesBG;
    private JPanel originPanel;

    public RulesPanel() {
        returnButton = new JButton("Return");
        returnButton.setBounds(Rel.X(820), Rel.Y(900), Rel.W(240), Rel.H(30));
        rulesBG = ImageEnum.RULESBG.getImage();
        SwingUtilities.invokeLater(() -> {
            setLayout(null);
            add(returnButton);
        });
    }

    public void setOriginPanel(JPanel p) {
        originPanel = p;
    }

    public JPanel getOriginPanel() {
        return originPanel;
    }

    public JButton getReturnButton() {
        return returnButton;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rulesBG != null) {
            g.drawImage(rulesBG, 0, 0, this);
        }
    }
}
