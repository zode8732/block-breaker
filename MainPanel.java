import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class MainPanel extends JPanel {
   
    private static final int FRAME = 600;
    private static final int PANEL_HEIGHT = (int)(FRAME * 1.334);
    private static final Color BACKGROUND = Color.BLACK;

    private BufferedImage myImage;
    private Graphics myBuffer;
    private Timer t;

    Paddle paddle = new Paddle();
    Ball ball = new Ball(300, 720);
    Block[][] blocks = new Block[5][7];

    private int score = 0;
    private int lives = 3;
    private boolean gameOver = false;
    private boolean gameWon = false;

    private int bigTimer = 0;
    private int slowTimer = 0;
   
    public MainPanel() {
       /** initiates a tall panel for user */
       setPreferredSize(new Dimension(FRAME, PANEL_HEIGHT));
        setFocusable(true);

        myImage = new BufferedImage(FRAME, PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        myBuffer = myImage.getGraphics();

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
       /** draws components on panel */
        super.paintComponent(g);

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
        /** check if left, right, and/or up keys are pressed */
        public void keyPressed(KeyEvent e) {
            if (gameOver || gameWon) {
                return;
            }
            
            if(e.getKeyCode() == KeyEvent.VK_LEFT)
                paddle.moveLeft(); /** if left, move left */
           
            if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                paddle.moveRight(); /** if right, move right */
           
            if(e.getKeyCode() == KeyEvent.VK_UP)
                paddle.shoot(ball); /** if up, shoot */
        }
    }
    
    private class Listener implements ActionListener {
       public void actionPerformed(ActionEvent e){
           /** runs the game cycle for as long as needed */
           if (!gameOver && !gameWon) {
                if (ball.isStuckToPaddle()) {
                    ball.stickToPaddle(paddle);
                }

               /** moves ball within panel */
                ball.move(FRAME, PANEL_HEIGHT);
                PaddleCollision.collidePaddle(paddle, ball);
                ballTouchesBlock();

                if (ball.isOutOfBounds(PANEL_HEIGHT)) {
                    lives--;
                    ball.resetBall();
                    ball.stickToPaddle(paddle);

                    /** checks if user has lives */
                    if (lives <= 0) {
                        gameOver = true;
                    }
                }

                updatePowerUpTimers();
                gameWon = allBlocksBroken();
            }
           
            repaint();
       }
    }
   
    public boolean ballTouchesBlock() {
        /** checks whether ball hits the block */
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                Block block = blocks[i][j];
                /** checks for all the blocks unbroken */
                if (!block.isBroken() && PaddleCollision.collideBlock(block, ball)) {
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
        /** chooses which method to call */
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
        /** makes sure power ups don't last forever */
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
        /** checks for unbroken blocks to continue game 
         * @return boolean of if all blocks are broken or not
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

    public void laser(int column) {
        /** destroys entire column of blocks */
        for (int i = 0; i < blocks.length; i++) {
            if (!blocks[i][column].isBroken()) {
                blocks[i][column].forceBreak();
                score += 10;
            }
        }
    }
}
