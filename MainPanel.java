import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.concurrent.TimeUnit;

public class MainPanel extends JPanel {
   
    private static final int FRAME = 600;
    private static final Color BACKGROUND = Color.BLACK;
    private BufferedImage myImage;
    private Graphics myBuffer;
   
    Paddle paddle = new Paddle(0, Color.RED, 10, 10);
    Block[][] blocks = new Block[5][7];
   
    public MainPanel() {
// initiates a tall panel for user
    }
   
    public void paintComponent(Graphics g){
      // draws components on panel
    }
   
    private class Key extends KeyAdapter {
        // check if left, right, and/or up keys are pressed
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_LEFT)
                paddle.moveLeft(); // if left, move left
           
            if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                paddle.moveRight(); // if right, move right
           
            if(e.getKeyCode() == KeyEvent.VK_UP)
                paddle.shoot(); // if up, shoot
        }
    }
    private class Listener implements ActionListener {
       public void actionPerformed(ActionEvent e){
      // runs code
  }
    }
   
    public boolean ballTouchesBlock(){ // checks whether ball hits the block
   return true;
    }

    private double distance(double x1, double x2, double y1, double y2) {
        // calculates distance using pythagorean theorem
        return 0;
    }

    public void laser() { // destroys entire column of blocks

    }
}
