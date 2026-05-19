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
        /**
         * methods not relevant for purpose of project
         */
    }

    private static void findImpactPoint(Block block, Ball ball) {
        /**
         * methods not relevant for purpose of project
         */
    }
}