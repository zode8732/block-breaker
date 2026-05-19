import java.awt.*;

public class Paddle {
   
    private int myX = 250;
    private int myY = 745;
    private Color myColor = Color.RED;
    private int myWidth = 100;
    private int myHeight = 25;
    private int xSpeed = 7;

    private int normalWidth = 100;
    private int normalSpeed = 7;
    private int rightEdge = 600;

    public Paddle() {
       
    }

    public Paddle(int y, Color color, int width, int height) { // Makes the paddle
       myY = y;
       myColor = color;
       myWidth = width;
       myHeight = height;
       normalWidth = width;
    }
   
    public void moveLeft() {
        // decreases the x value of the panel
       myX -= xSpeed;
       if (myX < 0) {
          myX = 0;
       }
    }
   
    public void moveRight() {
        // increases x value of the panel
        myX += xSpeed;
        if (myX + myWidth > rightEdge) {
            myX = rightEdge - myWidth;
        }
    }

    public void shoot() {
       
    }
   
    public void shoot(Ball ball) {
        // when the ball is on the paddle, sets the ball's dx and dy to a value
        if (ball.isStuckToPaddle()) {
            ball.setStuckToPaddle(false);
            ball.setdx(3);
            ball.setdy(-4);
        }
    }
   
    public void draw(Graphics myBuffer) {
        // draws paddle
        myBuffer.setColor(myColor);
        myBuffer.fillRect(myX, myY, myWidth, myHeight);
    }
   
    public boolean touchesBall(Ball ball) {
        // checks if paddle is touching ball to change ball direction
        return getBounds().intersects(ball.getBounds()) && ball.getdy() > 0;
    }

    // these two methods change speed for a power up
    
    public void slowDown() {
        // changes paddle xSpeed temporarily
        xSpeed = 3;
    }

    public void resetSpeed() {
        xSpeed = normalSpeed;
    }

    public void makeBig() {
        // paddle with is modified temporarily
        myWidth = 150;
        if (myX + myWidth > rightEdge) {
            myX = rightEdge - myWidth;
        }
    }

    public void resetWidth() {
        myWidth = normalWidth;
    }

    public int getX() {
        return myX;
    }

    public int getY() {
        return myY;
    }

    public int getWidth() {
        return myWidth;
    }

    public int getHeight() {
        return myHeight;
    }

    public Rectangle getBounds() {
        return new Rectangle(myX, myY, myWidth, myHeight);
    }
    
    private double distance(double x1, double x2, double y1, double y2) {
        // calculates distance using pythagorean theorem
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
