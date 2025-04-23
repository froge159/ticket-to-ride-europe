package panels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.NormalPathCard;
import models.TrainCard;
import utils.PNGEnum;
import utils.Rel;

public class DrawPanel extends JPanel {

    private JButton deckButton, ticketButton;
    private JButton[] faceUpButtons;

    private Stack<TrainCard> trainDeck, discard;
    private TrainCard[] faceUpDeck;
    private Stack<NormalPathCard> pathCards;

    public DrawPanel(Stack<TrainCard> trainCards, Stack<NormalPathCard> pathCards) {
        this.trainDeck = trainCards;
        this.discard = new Stack<>();
        this.faceUpDeck = new TrainCard[5];
        this.pathCards = pathCards;
        faceUpButtons = new JButton[5];

        setSize(Rel.W(720), Rel.H(800));
        setOpaque(false);
        //setBorder(new javax.swing.border.LineBorder(Color.YELLOW, Rel.W(3), true));

        
        setLayout(null);
        updatePanel();
    }

    public void updatePanel() {
        SwingUtilities.invokeLater(() -> {
            removeAll();
        });
        
        ImageIcon deckPNG = PNGEnum.TRAINBACK.getImage();
        ImageIcon deckPNGBW = PNGEnum.TRAINBACKBW.getImage();
        deckButton = new JButton();
        deckButton.setIcon(trainDeck.size() < 1 ? deckPNGBW : deckPNG);
        deckButton.setBounds(Rel.X(80), Rel.Y(5), Rel.W(200), Rel.H(125));
        if (trainDeck.size() < 1) {
            deckButton.setEnabled(false);
        } else {
            deckButton.setEnabled(true);
        }
        
        // TESTING
        refillFaceUpDeck();
        // TESITNG

        int y = 133;
        for (int i = 0; i < 5; i++) {
            final int index = i; 
            if (faceUpDeck[i] == null) {
                y += 130;
                continue;
            }
            JButton btn = new JButton();
            btn.setBounds(Rel.X(80), Rel.Y(y), Rel.W(200), Rel.H(125));
            btn.setIcon(faceUpDeck[index].getScaledFront(Rel.W(200), Rel.H(125)));
            faceUpButtons[index] = btn;
            y += 130;
            SwingUtilities.invokeLater(() -> {
                add(faceUpButtons[index]);
            });
        }

        ticketButton = new JButton();
        ImageIcon ticketPNG = PNGEnum.TICKETBACK.getImage();
        ImageIcon ticketPNGBW = PNGEnum.TICKETBACKBW.getImage();
        ticketButton.setIcon(pathCards.size() < 1 ? ticketPNGBW : ticketPNG);
        ticketButton.setBounds(Rel.X(309), Rel.Y(523), Rel.W(200), Rel.H(125));
        if (pathCards.size() < 1) {
            ticketButton.setEnabled(false);
        } else {
            ticketButton.setEnabled(true);
        }

        SwingUtilities.invokeLater(() -> {
            add(deckButton);
            add(ticketButton);
            revalidate();
            repaint();
        });
    }

    public void refillFaceUpDeck() {
        for (int i = 0; i < 5; i++) {
            if (trainDeck.size() > 0 && faceUpDeck[i] == null) {
                faceUpDeck[i] = trainDeck.pop();
            }
        }
    }

    public JButton getDeckButton() {
        return deckButton;
    }
    public JButton getTicketButton() {
        return ticketButton;
    }
    public JButton[] getFaceUpButtons() {
        return faceUpButtons;
    }

    public void showDrawnCard(ImageIcon icon) {
        deckButton.setIcon(icon);
    }

    public void setDrawTrainCardsEnabled(boolean enabled) {
        deckButton.setEnabled(enabled);
        for (int i = 0; i < faceUpButtons.length; i++) {
            if (faceUpButtons[i] != null) {
                faceUpButtons[i].setEnabled(enabled);
            }
        }
    }

    public void setTicketButtonEnabled(boolean enabled) {
        ticketButton.setEnabled(enabled);
    }

}
