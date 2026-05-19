import javax.swing.JFrame;

public class driver
{
    public static void main(String[] args)
    {
        /** Title of project and calls MainPanel */
        JFrame frame = new JFrame("Unit 5 Final Project");
        frame.setSize(600, 900);
        frame.setLocation(0, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainPanel p = new MainPanel();
        frame.setContentPane(p);
        p.requestFocus();
        frame.setVisible(true);
    }
}
