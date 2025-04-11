import java.io.IOException;

import javax.swing.SwingUtilities;

public class Main {
    // entry point
    /*
     *  Engines: handles game logic, updates JPanels too, bulk of code will be here
     *  Controllers: handle events, calls Engine methods
     *  Panels: initializes game components and paints
     *  Panels <- Controllers <- Engine
     *
     *  modifying components outside actionListener requires invokeLater()
     *  use layout managers - if not possible add coordinate ratios
     *  use graphics2d for map
     *  never use paint - always use paintComponent()
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
