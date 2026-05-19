public class PaddleCollision {
    
    private static double nearestX; // used to approximate what point of the paddle  
    private static double nearestY;  // a ball collided with
    
    public static void collideBlock(Block block, Ball ball)
   {
        // see if the ball hit the paddle!
        if(block.touchesBall(ball))
        {  
            // back the ball up until it is just outside the paddle
            while(block.touchesBall(ball))
            {
                ball.setX(ball.getX() - (int)(ball.getdx()/10.0));
                ball.setY(ball.getY() - (int)(ball.getdy()/10.0));
            }
           
            // find the point on the edge of the paddle closest to the ball
            findImpactPoint(block, ball);
           
            double ux=nearestX-ball.getX();
            double uy=nearestY-ball.getY();
            double ur=Math.sqrt(ux*ux+uy*uy);
            ux/=ur;
            uy/=ur;
            int dx=(int)ball.getdx();
            int dy=(int)ball.getdy();
            double dot_1= ux*dx+uy*dy;
            double dot_2=-uy*dx+ux*dy;
            dot_1*=-1; // this is the actual "bounce"
            double[] d = new double[2];
            d[0]=dot_1*ux-dot_2*uy;      //vector math
            d[1]=dot_1*uy+dot_2*ux;      //vector math
            dx=(int)Math.round(d[0]);
            dy=(int)Math.round(d[1]);
            ball.setdx(dx);
            ball.setdy(dy);
        }
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

        if (block.getBounds().intersects(ball.getBounds())) {
            findImpactPoint(block, ball);
            ball.bounceBlock();
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
}
