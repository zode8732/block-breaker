public class PaddleCollision
{
   private static double nearestX; // used to approximate what point of the paddle  
   private static double nearestY;  // a ball collided with
   
   public static void collide(Paddle paddle, Ball ball)
   {
        // see if the ball hit the paddle!
        if(paddle.inPaddle(ball))
        {  
            // back the ball up until it is just outside the paddle
            while(paddle.inPaddle(ball))
            {
                ball.setX(ball.getX() - ball.getdx()/10.0);
                ball.setY(ball.getY() - ball.getdy()/10.0);
            }
           
            // find the point on the edge of the paddle closest to the ball
            findImpactPoint(paddle, ball);
           
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
   
    private static void findImpactPoint(Paddle paddle, Ball ball)
    {
        // first assume the nw corner is closest
        nearestX = paddle.getX();  
        nearestY = paddle.getY();
   
        // now work around the edge of the paddle looking for a closer point
        for (int x = paddle.getX(); x <= paddle.getX() + paddle.getWidth(); x++)  // top & bottom edges
        {
            updateIfCloser(x, paddle.getY(), ball);
            updateIfCloser(x, paddle.getY() + paddle.getHeight(), ball);
        }
        for (int y = paddle.getY(); y <= paddle.getY() + paddle.getHeight(); y++)  // right & left edges
        {
            updateIfCloser(paddle.getX(), y, ball);
            updateIfCloser(paddle.getX() + paddle.getWidth(), y, ball);
        }
    }
   
    // makes (x,y) the nearest point if it is closer to the ball
    private static void updateIfCloser(int x, int y, Ball b)
    {
        if(distance(x, y, b.getX(), b.getY()) < distance(nearestX, nearestY, b.getX(), b.getY()))
        {
            nearestX = x;
            nearestY = y;
      }
    }
   
    // returns distance between (x1, y1) and (x2, y2)
    private static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
