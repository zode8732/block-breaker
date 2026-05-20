import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.concurrent.TimeUnit;
import java.awt.geom.*;

public class MainPanel extends JPanel {
   
    private static final int FRAME = 600;
    private static final int PANEL_HEIGHT = (int)(FRAME * 1.334);
    private static final Color BACKGROUND = Color.BLACK;

    private BufferedImage myImage;
    private Graphics2D myBuffer;
    private Timer t;

    Paddle paddle = new Paddle();
    Ball ball = new Ball(300, 720);
    Block[][] blocks = new Block[5][7];

    private int score = 0;
    private int lives = 3;
    private boolean gameOver = false;
    private boolean gameWon = false;
    
    private boolean movingRight, movingLeft = false;

    private int bigTimer = 0;
    private int slowTimer = 0;
   
    public MainPanel() {
       /** 
          * initiates a tall panel for user
          */
       setPreferredSize(new Dimension(FRAME, PANEL_HEIGHT));
        setFocusable(true);

        myImage = new BufferedImage(FRAME, PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        myBuffer = (Graphics2D) myImage.createGraphics();

        buildBlocks();

        addKeyListener(new Key());
        t = new Timer(10, new Listener());
        t.start();
    }

    private void buildBlocks() {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                int powerUpType = 1;

                double d = Math.random() * 11;
                
                if (d > 6) {
                    powerUpType = 2;
                } if (d > 7) {
                    powerUpType = 3;
                } if (d > 8) {
                    powerUpType = 4;
                } if (d > 10) {
                    powerUpType = 5;
                }

                blocks[i][j] = new Block(i, j, powerUpType);
            }
        }
    }

   public void paintComponent(Graphics g) {
       /** 
          *draws components on panel
          */
        super.paintComponent(g);

        myBuffer = myImage.createGraphics();

        myBuffer.setColor(BACKGROUND);
        myBuffer.fillRect(0, 0, FRAME, PANEL_HEIGHT);

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                blocks[i][j].draw(myBuffer);
            }
        }

        paddle.draw(myBuffer);
        ball.draw(myBuffer);

        myBuffer.setColor(Color.WHITE);
        myBuffer.setFont(new Font("SansSerif", Font.BOLD, 18));
        myBuffer.drawString("Score: " + score, 20, PANEL_HEIGHT - 45);
        myBuffer.drawString("Lives: " + lives, 20, PANEL_HEIGHT - 20);

        if (ball.isStuckToPaddle() && !gameOver && !gameWon) {
            myBuffer.drawString("Press UP to launch", 210, PANEL_HEIGHT - 20);
        }

        if (gameOver) {
            myBuffer.setFont(new Font("SansSerif", Font.BOLD, 42));
            myBuffer.drawString("Game Over", 180, 420);
        }

        if (gameWon) {
            myBuffer.setFont(new Font("SansSerif", Font.BOLD, 42));
            myBuffer.drawString("You Win!", 210, 420);
        }

        g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
    }
   
    private class Key extends KeyAdapter {
        /** 
       *check if left, right, and/or up keys are pressed
       */
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT) 
                    setMovingLeft(true);
                if (key == KeyEvent.VK_RIGHT) 
                    setMovingRight(true);
                if (key == KeyEvent.VK_UP) 
                    paddle.shoot(ball);
            }

            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT) 
                    setMovingLeft(false);
                if (key == KeyEvent.VK_RIGHT) 
                    setMovingRight(false);
            }
    }

    
    
    private class Listener implements ActionListener {
       public void actionPerformed(ActionEvent e){
           /** 
              *runs the game cycle for as long as needed
              */
           if (!gameOver && !gameWon) {
                if (ball.isStuckToPaddle()) {
                    ball.stickToPaddle(paddle);
                }

               // moves ball within panel
                ball.move(FRAME, PANEL_HEIGHT);
                PaddleCollision.collidePaddle(paddle, ball);
                ballTouchesBlock();

                if (ball.isOutOfBounds(PANEL_HEIGHT)) {
                    lives--;
                    ball.resetBall();
                    ball.stickToPaddle(paddle);

                    // checks if user has lives
                    if (lives <= 0) {
                        gameOver = true;
                    }
                }

                updatePowerUpTimers();
                gameWon = allBlocksBroken();
            }
           
            updateIt(paddle);
            repaint();
       }
    }
   
    public boolean ballTouchesBlock() {
        /** 
           *checks whether ball hits the block
           */
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                Block block = blocks[i][j];
                // checks for all the blocks unbroken
                if (!block.isBroken() && PaddleCollision.ifCollideBlock(block, ball)) {
                    int blockType = block.brokenBlock();

                    if (blockType != 0) {
                        score += 10;
                        applyPowerUp(blockType, j);
                    }

                    return true;
                }
            }
        }
        return false;
    }

    private void applyPowerUp(int blockType, int column) {
        /** 
           *chooses which method to call
           */
        if (blockType == 3) {
            laser(column);
        }
        else if (blockType == 4) {
            paddle.makeBig();
            bigTimer = 500;
        }
        else if (blockType == 5) {
            paddle.slowDown();
            slowTimer = 500;
        }
    }

    private void updatePowerUpTimers() {
        /** 
           *makes sure power ups don't last forever
           */
        if (bigTimer > 0) {
            bigTimer--;
            if (bigTimer == 0) {
                paddle.resetWidth();
            }
        }

        if (slowTimer > 0) {
            slowTimer--;
            if (slowTimer == 0) {
                paddle.resetSpeed();
            }
        }
    }

    private boolean allBlocksBroken() {
        /** 
           *checks for unbroken blocks to continue game
           */
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (!blocks[i][j].isBroken()) {
                    return false;
                }
            }
        }
        return true;
    }

    private double distance(double x1, double x2, double y1, double y2) {
        /** 
           *calculates distance using pythagorean theorem
           */
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public void laser(int column) {
        /** 
           *destroys entire column of blocks
           */
        for (int i = 0; i < blocks.length; i++) {
            if (!blocks[i][column].isBroken()) {
                blocks[i][column].forceBreak();
                score += 10;
            }
        }
    }
    
    public void setMovingLeft(boolean b) { 
        movingLeft = b; 
    }
    public void setMovingRight(boolean b) { 
        movingRight = b; 
    }
    
    public void updateIt(Paddle paddle) {
        if (movingLeft == true) 
            paddle.moveLeft();
        if (movingRight == true) 
            paddle.moveRight();
    }


}
