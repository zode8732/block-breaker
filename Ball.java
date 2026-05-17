import java.awt.*;

public class Ball {

	private int myX;
    private int myY;
    private int startX;
    private int startY;
    private int myDiameter = 16;
    private Color myColor = Color.WHITE;
	
    private double dx = 0;
    private double dy = 0;
    private boolean stuckToPaddle = true;

    public Ball(int x, int y) {
        // instantiates ball object at the x and y values necessary
		myX = x;
        myY = y;
        startX = x;
        startY = y;
    }
    
    public void move(double rightEdge, double bottomEdge) {
        // moves the ball
		if (stuckToPaddle) {
            return;
        }
		myX += dx;
        myY += dy;
        bounceWall(rightEdge);
    }

    public void draw(Graphics myBuffer) {
        myBuffer.setColor(myColor);
        myBuffer.fillOval(myX, myY, myDiameter, myDiameter);
    }
    
    public void setdx(double x) {
        // changes horizontal speed
	  dx = x;
    }
    
    public void setdy(double y) {
        // changes vertical speed
		dy = y;
    }

    public void setX(int x) {
        myX = x;
    }

    public void setY(int x) {
        myY = x;
    }

    public void setStuckToPaddle(boolean x) {
        stuckToPaddle = x;
    }
    
    public void bouncePaddle() {
        // if ball is touching the paddle, make it bounce. called for each iteration
		dy = -Math.abs(dy);
    }
	
	public void bouncePaddle(Paddle paddle) {
        // if ball is touching the paddle, make it bounce. called for each iteration
		double paddleCenter = paddle.getX() + paddle.getWidth() / 2.0;
        double ballCenter = getCenterX();
        double hitPosition = (ballCenter - paddleCenter) / (paddle.getWidth() / 2.0);

        dx = hitPosition * 5;
        dy = -Math.abs(dy);

        myY = paddle.getY() - myDiameter - 1;
    }
    
    public void bounceWall() {
        // if ball touches wall, bounces off wall
		bounceWall(600);
    }

	public void bounceWall(double rightEdge) {
		// if ball touches wall, bounces off wall
        if (myX <= 0) {
            myX = 0;
            dx = Math.abs(dx);
        }

        if (myX + myDiameter >= rightEdge) {
            myX = (int)rightEdge - myDiameter;
            dx = -Math.abs(dx);
        }

        if (myY <= 0) {
            myY = 0;
            dy = Math.abs(dy);
        }
    }
    
    public void bounceBlock() {
        // if hits block, bounce off block
		dy *= -1;
    }

	// self explanatory get methods

    public double getdx() {
        return dx;
    }
    
    public double getdy() {
        return dy;
    }

	public int getX() {
        return myX;
    }

    public int getY() {
        return myY;
    }

    public int getDiameter() {
        return myDiameter;
    }

    public double getCenterX() {
        return myX + myDiameter / 2.0;
    }

    public double getCenterY() {
        return myY + myDiameter / 2.0;
    }

    public Rectangle getBounds() {
        return new Rectangle(myX, myY, myDiameter, myDiameter);
    }

    public boolean isOutOfBounds(double bottomEdge) {
        return myY > bottomEdge;
    }

    public boolean isStuckToPaddle() {
        return stuckToPaddle;
    }

	public void stickToPaddle(Paddle paddle) {
		// sends to top left of paddle
        myX = paddle.getX() + paddle.getWidth() / 2 - myDiameter / 2;
        myY = paddle.getY() - myDiameter - 2;
    }
    
    public void resetBall() {
        // resets ball position
		myX = startX;
        myY = startY;
        dx = 0;
        dy = 0;
        stuckToPaddle = true;
    }
}
