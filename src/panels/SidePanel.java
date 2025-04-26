package panels;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.LongPathCard;
import models.NormalPathCard;
import models.TrainCard;
import utils.Rel;

public class SidePanel extends JPanel{
    private JButton confirm, cancel;
    private JLabel label, bottom;
    private String state;
    private ArrayList<LongPathCard> longCards;
    private ArrayList<NormalPathCard> pathCards;
    private ArrayList<TrainCard> deck, discard;
    private int turn;

    public SidePanel(ArrayList<LongPathCard> lng, ArrayList<NormalPathCard> path, ArrayList<TrainCard> deck, ArrayList<TrainCard> discard, int t) {
        this.discard = discard;
        label = new JLabel();
        bottom = new JLabel();
        longCards = lng;
        pathCards = path;
        this.deck = deck;
        turn = t;

        setLayout(null);
        setSize(Rel.W(500), Rel.H(1080));
        setBorder(new javax.swing.border.LineBorder(Color.YELLOW, Rel.W(3), true));
        setBackground(Color.YELLOW); // Transparent background
        setOpaque(true);
        setVisible(true);
        changeInfo("begin");
    }

    public void updatePanel(){

        SwingUtilities.invokeLater(() -> {
            removeAll();
        });
        
        confirm = new JButton("Confirm");
        cancel = new JButton("Cancel");

        confirm.setFont(new Font("Arial", Font.PLAIN, Rel.W(15)));
        cancel.setFont(new Font("Arial", Font.PLAIN, Rel.W(15)));

        confirm.setBounds(Rel.X(290), Rel.Y(850), Rel.W(120), Rel.H(50));
        cancel.setBounds(Rel.X(90), Rel.Y(850), Rel.W(120), Rel.H(50));

        int y = 200;

        for (int i = 0; i < 3; i++) {
            JButton btn = new JButton();
            ImageIcon icon = new ImageIcon();
            btn.setBounds(Rel.X(150), Rel.Y(y), Rel.W(200), Rel.H(125));
            
            if(state.equals("begin") || state.equals("path")){
                //System.out.println(pathCards.getLast());
                icon = pathCards.getLast().getFront();
                pathCards.add(0, pathCards.remove(pathCards.size() - 1));
            }else if(state.equals("mountain")){
                icon = deck.get(deck.size() - 1).getScaledFront(Rel.W(200), Rel.H(125));
                deck.add(0, deck.remove(deck.size() - 1));
            }
            btn.setIcon(icon);
            btn.setBounds(Rel.X(150), Rel.Y(y), Rel.W(200), Rel.H(125));
            y += 150;
           // System.out.println(btn.getBounds());
            SwingUtilities.invokeLater(() -> {
                add(btn);
                //setComponentZOrder(btn, 0); 
            });
        }

        if(state.equals("begin")){
            JButton btn = new JButton();
            btn.setIcon(longCards.getLast().getFront());
            btn.setBounds(Rel.X(150), Rel.Y(y), Rel.W(200), Rel.H(125));
            longCards.add(0, longCards.remove(longCards.size() - 1));
            SwingUtilities.invokeLater(() -> {
                add(btn);
                //setComponentZOrder(btn, 0); 
            });
        }

        SwingUtilities.invokeLater(() -> {
            add(confirm);
            add(cancel);
            add(label);
            add(bottom);
            revalidate();
            repaint();
        });
    }

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
        System.out.println("added label: " + label.getText());
        System.out.println("Label bounds: " + label.getBounds());
        updatePanel();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(44, 25, 17)); // Set the background color to yellow
    
    }
}
