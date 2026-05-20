import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.*;

public class Block {
   
    private int blockType = 0;
    private int blockX = 0;
    private int blockY = 0;
    private int hitsLeft = 1;

    private static final int BLOCK_WIDTH = 60;
    private static final int BLOCK_HEIGHT = 25;
    private static final int START_X = 45;
    private static final int START_Y = 60;
    private static final int GAP = 10;
   
    public Block(int i, int j, int powerUpType) {
        // creates block with power up and location
        blockType = powerUpType;
        blockX = START_X + j * (BLOCK_WIDTH + GAP);
        blockY = START_Y + i * (BLOCK_HEIGHT + GAP);
        if (blockType == 2) {
            hitsLeft = 2;
        }
        else {
            hitsLeft = 1;
        }
    }
    
    public boolean touchesBall(Ball ball) {
        // checks if paddle is touching ball to change ball direction
        Area areaA = new Area(getBounds());
        Area areaB = new Area(ball.getBounds());
        areaA.intersect(areaB);
        return !areaA.isEmpty() && ball.getdy() > 0;

        // return getBounds().intersects(ball.getBounds());
    }
   
    public void draw(Graphics myBuffer) {
        if (blockType == 0) {
            return;
        }

        if (blockType == 1) {
            myBuffer.setColor(Color.BLUE);
        }
        else if (blockType == 2) {
            myBuffer.setColor(Color.GRAY);
        }
        else if (blockType == 3) {
            myBuffer.setColor(Color.ORANGE);
        }
        else if (blockType == 4) {
            myBuffer.setColor(Color.GREEN);
        }
        else if (blockType == 5) {
            myBuffer.setColor(Color.MAGENTA);
        }
      
        myBuffer.fillRect(blockX, blockY, BLOCK_WIDTH, BLOCK_HEIGHT);
        myBuffer.setColor(Color.WHITE);
        myBuffer.drawRect(blockX, blockY, BLOCK_WIDTH, BLOCK_HEIGHT);
      if (blockType == 2) {
        myBuffer.drawString("2", blockX + BLOCK_WIDTH / 2 - 4, blockY + 17);
      }
    }

    public void draw(int i, int j) {
        blockX = START_X + j * (BLOCK_WIDTH + GAP);
        blockY = START_Y + i * (BLOCK_HEIGHT + GAP);
    }
    
    public int brokenBlock() {
        // makes sure to get rid of block
        if (blockType == 0) {
            return 0;
        }

        hitsLeft--;

        if (hitsLeft <= 0) {
            int oldBlockType = blockType;
            blockType = 0;
            return oldBlockType;
        }

        return 0;
    }

    public void forceBreak() {
        // breaks block without fail
        blockType = 0;
        hitsLeft = 0;
    }

    // get methods
    
    public boolean isBroken() {
        return blockType == 0;
    }

    public int getBlockType() {
        return blockType;
    }

    public int getX() {
        return blockX;
    }

    public int getY() {
        return blockY;
    }

    public int getWidth() {
        return BLOCK_WIDTH;
    }

    public int getHeight() {
        return BLOCK_HEIGHT;
    }

    public Rectangle getBounds() {
        return new Rectangle(blockX, blockY, BLOCK_WIDTH, BLOCK_HEIGHT);
    }
}
