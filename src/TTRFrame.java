import javax.swing.JFrame;


public class TTRFrame extends JFrame {

    public static int WIDTH = 1600;
    public static int HEIGHT = 1000;

    public TTRFrame() {
        setTitle("Ticket To Ride: Europe");
        setSize(1600, 1000);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        

    }


    
}
