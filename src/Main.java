import java.io.IOException;

import javax.swing.SwingUtilities;

public class Main {
    // entry point
    /*
     * TODO:
     * add rotated train cards
     * update cardimages to accept with and height as it isn't constant anymore
     * 
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TTRFrame frame = null;
            try {
                frame = new TTRFrame();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
            frame.setVisible(true);
        });
    }
}
