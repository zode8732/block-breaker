import java.awt.Color;
import java.awt.*;

public class Ball {

    private double dx = 0;
    private double dy = 0;

    public Ball(int x, int y) {
        // instantiates ball object at the x and y values necessary
    }
    
    public void move(double rightEdge, double bottomEdge) {
        // moves the ball
    }
    
    public void setdx(double x) {
        // changes horizontal speed
	  dx = x;
    }
    
    public void setdy(double y) {
        // changes vertical speed
	 dy = y;
    }
    
    public void bouncePaddle() {
        // if ball is touching the paddle, make it bounce. called for each iteration
    }
    
    public void bounceWall() {
        // if ball touches wall, bounces off wall
    }
    
    public void bounceBlock() {
        // if hits block, bounce off block
    }
    
    public double getdx() {
        return dx;
    }
    
    public double getdy() {
        return dy;
    }
    
    public void resetBall() {
        // resets ball position
    }
}
