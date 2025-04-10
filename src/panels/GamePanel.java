package panels;

import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.Player;
import utils.ColorEnum;

public class GamePanel extends JPanel {

    private Player[] playerArray;

    
    public GamePanel() {
        playerArray = new Player[4];
        playerArray[0] = new Player("red");
        playerArray[1] = new Player("yellow");
        playerArray[2] = new Player("green");
        playerArray[3] = new Player("blue");
        
        SwingUtilities.invokeLater(() -> {
            add(new PlayerPanel(playerArray));
            revalidate();
        });
        
    }




    public Player[] getPlayers() {
        return playerArray;
    }


    
}
