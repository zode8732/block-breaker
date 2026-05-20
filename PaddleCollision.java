import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.*;
import java.awt.Rectangle;

public class PaddleCollision {
    
    private static double nearestX; // used to approximate what point of the paddle  
    private static double nearestY;  // a ball collided with
    
    static Rectangle2D lineTop, lineLeft, lineRight, lineBottom;
    
    static double tempX, tempY;
    
    public static void collideBlock(Block block, Ball ball)
   {
        // see if the ball hit the paddle!
            /*
            double ballCenterY = ball.getY() + (ball.getDiameter() / 2.0);
            double blockBottom = block.getY() + block.getHeight();
            double blockTop = block.getY();
            double blockLeft = block.getX();
            double blockRight = block.getX() + block.getWidth();

            // Hit Bottom (Ball moving UP)
            if (ball.getdy() > 0 && (ball.getY() + ball.getDiameter()) > blockTop) {
                ball.setdy(-1 * Math.abs(ball.getdy())); // Force dy negative (up)
                ball.setY(blockTop - ball.getDiameter());  // Snap to top edge
            } else if (ball.getdx() < 0 && ball.getX() < blockRight && ball.getX() > blockLeft) {
                ball.setdx(Math.abs(ball.getdx())); // Force dx positive (right)
                ball.setX(blockRight);             // Snap to right edge
            } else if (ball.getdx() > 0 && (ball.getX() + ball.getDiameter()) > blockLeft) {
                ball.setdx(-1 * Math.abs(ball.getdx())); // Force dx negative (left)
                ball.setX(blockLeft - ball.getDiameter());  // Snap to left edge
            } else if (ball.getdy() < 0 && ball.getY() < blockBottom && ball.getY() > blockTop) {
                ball.setdy(Math.abs(ball.getdy())); // Force dy positive (down)
                ball.setY(blockBottom);             // Snap to bottom edge
            }
            */
            
            
            
            // set coordinates to push ball to a bit
            tempX = ball.getX()-ball.getdx();
            tempY = ball.getY()-ball.getdy();
            
            // establish lines of a block 
            makeLines(block);
            
            // based on where ball hit block, reverse dx or dy
            if(topIntersects(ball) && ball.getdy() > 0){ 
                ball.setdy(-1 * Math.abs(ball.getdy()));
            } else if(bottomIntersects(ball) && ball.getdy() < 0){ 
                ball.setdy(Math.abs(ball.getdy()));
            } else if(leftIntersects(ball) && ball.getdx() > 0){ 
                ball.setdx(-1 * Math.abs(ball.getdx()));
            } else if(rightIntersects(ball) && ball.getdx() < 0) { 
                ball.setdx(Math.abs(ball.getdx()));
            }
            
            // push ball so that it doesn't cruise through blocks
            ball.setX(tempX);
            ball.setY(tempY);
            
        
    }
    public static void collidePaddle(Paddle paddle, Ball ball) {
        if (paddle.touchesBall(ball)) {
            findImpactPointPaddle(paddle, ball);
            ball.bouncePaddle(paddle);
        }
    }

    public static boolean ifCollideBlock(Block block, Ball ball) {
        if (block.isBroken()) {
            return false;
        }
        
        Area areaA = new Area(block.getBounds());
        Area areaB = new Area(ball.getBounds());
        areaA.intersect(areaB);

        if (!areaA.isEmpty()) {
            collideBlock(block, ball);
            return true;
        }

        return false;
    }
    
    private static void updateIfCloser(int x, int y, Ball b)
    {
        if(distance(x, y, b.getX(), b.getY()) < distance(nearestX, nearestY, b.getX(), b.getY()))
        {
            nearestX = x;
            nearestY = y;
      }
    }

    private static void findImpactPoint(Block block, Ball ball)
    {
        // first assume the nw corner is closest
        nearestX = block.getX();  
        nearestY = block.getY();
   
        // now work around the edge of the paddle looking for a closer point
        for (int x = block.getX(); x <= block.getX() + block.getWidth(); x++)  // top & bottom edges
        {
            updateIfCloser(x, block.getY(), ball);
            updateIfCloser(x, block.getY() + block.getHeight(), ball);
        }
        for (int y = block.getY(); y <= block.getY() + block.getHeight(); y++)  // right & left edges
        {
            updateIfCloser(block.getX(), y, ball);
            updateIfCloser(block.getX() + block.getWidth(), y, ball);
        }
    }
    
    private static void findImpactPointPaddle(Paddle block, Ball ball)
    {
        // first assume the nw corner is closest
        nearestX = block.getX();  
        nearestY = block.getY();
   
        // now work around the edge of the paddle looking for a closer point
        for (int x = block.getX(); x <= block.getX() + block.getWidth(); x++)  // top & bottom edges
        {
            updateIfCloser(x, block.getY(), ball);
            updateIfCloser(x, block.getY() + block.getHeight(), ball);
        }
        for (int y = block.getY(); y <= block.getY() + block.getHeight(); y++)  // right & left edges
        {
            updateIfCloser(block.getX(), y, ball);
            updateIfCloser(block.getX() + block.getWidth(), y, ball);
        }
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
    
    private static void makeLines(Block block) {
        lineTop = new Rectangle2D.Double(block.getX()+1, block.getY()-2, block.getWidth()-2, 3);
        lineLeft = new Rectangle2D.Double(block.getX()-2, block.getY(), 3, block.getHeight());
        lineRight = new Rectangle2D.Double(block.getX()+block.getWidth()-1, block.getY(), 3, block.getHeight());
        lineBottom = new Rectangle2D.Double(block.getX()+1, block.getY()+block.getHeight()-1, block.getWidth()-2, 3);
    }
    
    private static boolean topIntersects(Ball ball) {
        Area areaA = new Area(lineTop.getBounds());
        Area areaB = new Area(ball.getBounds());
        areaA.intersect(areaB);
        return !areaA.isEmpty();
    }
    
    private static boolean leftIntersects(Ball ball) {
        Area areaA = new Area(lineLeft.getBounds());
        Area areaB = new Area(ball.getBounds());
        areaA.intersect(areaB);
        return !areaA.isEmpty();
    }
    
    private static boolean rightIntersects(Ball ball) {
        Area areaA = new Area(lineRight.getBounds());
        Area areaB = new Area(ball.getBounds());
        areaA.intersect(areaB);
        return !areaA.isEmpty();
    }
    
    private static boolean bottomIntersects(Ball ball) {
        Area areaA = new Area(lineBottom.getBounds());
        Area areaB = new Area(ball.getBounds());
        areaA.intersect(areaB);
        return !areaA.isEmpty();
    }
}
