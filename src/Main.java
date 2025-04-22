import java.io.IOException;

import javax.swing.SwingUtilities;

public class Main {
    // entry point
    /*
     * TODO:
     * add button hover effects
     * style buttons
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TTRFrame frame = null;
            try {
                frame = new TTRFrame();
            } catch (IOException e) {
                e.printStackTrace();
            } 
            frame.setVisible(true);
        });
    }
}
