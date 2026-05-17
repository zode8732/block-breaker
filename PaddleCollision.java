public class PaddleCollision {
    public static void collidePaddle(Paddle paddle, Ball ball) {
        if (paddle.touchesBall(ball)) {
            findImpactPoint(paddle, ball);
            ball.bouncePaddle(paddle);
        }
    }

    public static boolean collideBlock(Block block, Ball ball) {
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

    private static void findImpactPoint(Paddle paddle, Ball ball) {
        
    }

    private static void findImpactPoint(Block block, Ball ball) {
        
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}