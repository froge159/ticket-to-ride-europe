package panels;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.Player;
import utils.ColorEnum;
import java.awt.GridLayout;

public class PlayerPanel extends JPanel {
    
    private Player[] playerArray;
    private JLabel bgImage;

    public PlayerPanel(Player[] pa) {
        playerArray = pa;

        
    }
}


