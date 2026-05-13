import java.awt.*;
import java.awt.Color;

public class Paddle {
   
    private int myY;
    private Color myColor = Color.RED;
    private int myWidth;
    private int myHeight;
    private int xSpeed = 3;    

    public Paddle(int y, Color color, int width, int height) { // Makes the paddle
        myY = y;
        myColor = color;
        myWidth = width;
        myHeight = height;
    }
   
    public void moveLeft() {
        // decreases the x value of the panel
    }
   
    public void moveRight() {
        // increases x value of the panel
    }
   
    public void shoot() {
        // when the ball is on the paddle, sets the ball's dx and dy to a value
    }
   
    public void draw(Graphics myBuffer) {
        // draws paddle
    }
   
    public boolean touchesBall(Ball ball) {
 // checks if paddle is touching ball to change ball direction
        return true;
    }

    public void slowDown() {
   // changes paddle xSpeed temporarily
    }

    public void makeBig() {
        // paddle with is modified temporarily
    }
   
    private double distance(double x1, double x2, double y1, double y2) {
        return 0;
        // calculates distance using pythagorean theorem
    }
}
