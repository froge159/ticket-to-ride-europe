package panels;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

 
import models.NormalPathCard;
import models.PathCard;
import models.Player;
import utils.Rel;

public class TicketPanel extends JPanel {

    private JButton confirm;
    private JLabel infoLabel, bottom;
    private NormalPathCard[] ticketArray;
    private JButton[] ticketButtons;
    private boolean[] clicked;
    private ArrayList<NormalPathCard> normalPaths;
    private Player player;

    public TicketPanel(ArrayList<NormalPathCard> normalPath) {
        this.normalPaths = normalPath;
        ticketArray = new NormalPathCard[3];
        ticketButtons = new JButton[3];
        clicked = new boolean[3];

        setLayout(null);
        setSize(Rel.W(500), Rel.H(1080));
        //setBorder(new javax.swing.border.LineBorder(Color.YELLOW, Rel.W(3), true));
        setBackground(Color.YELLOW);
        setOpaque(true);
        setupPanel();
    }

    public void setupPanel(){ 
        
        confirm = new JButton("Confirm");
        confirm.setFont(new Font("Arial", Font.PLAIN, Rel.W(15)));
        confirm.setBounds(Rel.X(190), Rel.Y(750), Rel.W(120), Rel.H(50));

        infoLabel = new JLabel("Select at least 1 ticket");
        bottom = new JLabel();
        infoLabel.setFont(new Font("Arial", Font.PLAIN, Rel.W(30)));
        bottom.setFont(new Font("Arial", Font.PLAIN, Rel.W(30)));
        infoLabel.setForeground(Color.WHITE);
        bottom.setForeground(Color.WHITE);
        infoLabel.setBounds(Rel.X(100), Rel.Y(100), Rel.W(400), Rel.H(50));
        bottom.setBounds(Rel.X(202), Rel.Y(950), Rel.W(400), Rel.H(50));

        // y starts at 200, increments by 150
        // x is at 150
        int y = 200;
        clicked = new boolean[3];
        
        for (int i = 0; i < 3; i++) {
            final int ind = i; 
            NormalPathCard card = normalPaths.remove(normalPaths.size() - 1);
            ticketButtons[ind] = new JButton();
            ticketButtons[ind].setIcon(card.getFront());
            ticketButtons[ind].setBounds(Rel.X(150), Rel.Y(y), Rel.W(200), Rel.H(125));
            ticketArray[ind] = card;
            SwingUtilities.invokeLater(() -> {
                add(ticketButtons[ind]);
            });
            y += 150;
        }

        SwingUtilities.invokeLater(() -> {
            add(confirm);
            add(infoLabel);
            add(bottom);
            revalidate();
            repaint();
        });
    }

    /*
    public void changeInfo(String state){
        this.state = state;
        if(state.equals("path")){
            label.setText("Select at least one ticket");
            bottom.setText("Player " + (turn + 1));
        }else if(state.equals("begin")){
            label.setText("Select at least two tickets");
            bottom.setText("Player " + (turn + 1));
        }else if(state.equals("mountain")){
            label.setText("Drawing for mountain route");
            bottom.setText("Pay +X [color] cards to claim route");
        }

        
        label.setFont(new Font("Arial", Font.PLAIN, Rel.W(30)));
        bottom.setFont(new Font("Arial", Font.PLAIN, Rel.W(30)));
        label.setForeground(Color.WHITE);
        bottom.setForeground(Color.WHITE);
        label.setBounds(Rel.X(250) - Rel.W(6) * label.getText().length(), Rel.Y(100), 400, Rel.H(50));
        bottom.setBounds(Rel.X(250) - Rel.W(6) * bottom.getText().length(), Rel.Y(950), 400, Rel.H(50));
        //label.setBorder(new javax.swing.border.LineBorder(Color.YELLOW, Rel.W(3), true));
        //System.out.println("added label: " + label.getText());
        //System.out.println("Label bounds: " + label.getBounds());
        updatePanel();
    }*/

     

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(44, 25, 17)); // Set the background color to yellow
    }

    public void updateForNextPlayer(Player player) {
        this.player = player;
        
        int y = 200;
        clicked = new boolean[3];
        bottom.setText("Player " + (player.getNumber() + 1));
        
        for (int i = 0; i < 3; i++) {
            final int ind = i; 
            NormalPathCard card = normalPaths.remove(normalPaths.size() - 1);
            ticketButtons[ind].setIcon(card.getFront());
            ticketButtons[ind].setBorder(null);
            ticketArray[ind] = card;
            y += 150;
        }
    }

    public JButton getConfirmButton() {
        return confirm;
    }

    public NormalPathCard[] getTicketArray() {
        return ticketArray;
    }

    public JButton[] getTicketButtons() {
        return ticketButtons;
    }

    public ArrayList<NormalPathCard> getNormalPaths() {
        return normalPaths;
    }

    public Player getPlayer() {
        return player;
    }

    public JLabel getInfoLabel() {
        return infoLabel;
    }

    public JLabel getBottomLabel() {
        return bottom;
    }

    public boolean[] getClickedArray() {
        return clicked;
    }
}
