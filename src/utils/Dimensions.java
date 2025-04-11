package utils;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Dimensions {

    // Default screen size
    public static int WIDTH = 1920;
    public static int HEIGHT = 1080;

    static {
        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Set WIDTH and HEIGHT based on the screen size
        WIDTH = (int) screenSize.getWidth();
        HEIGHT = (int) screenSize.getHeight();
    }
}
