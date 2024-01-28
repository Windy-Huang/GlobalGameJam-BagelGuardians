package catCharacter;

import ui.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Cat {
    public static final int WAIT = 175;

    public GamePanel gp;
    public MouseHandler mh;
    public BufferedImage left, right, regular, img;
    public long startReactionTime = 0;
    public int x=300,y=300;

    public Cat(GamePanel gp, MouseHandler mh){
        this.gp = gp;
        this.mh = mh;
        getImage();
    }

    // EFFECTS: imports the image
    public void getImage() {
        try {
            left = ImageIO.read(getClass().getResourceAsStream("/left.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/right.png"));
            regular = ImageIO.read(getClass().getResourceAsStream("/regular.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        img = regular;
    }

    // EFFECTS: return true if the coordinate is in valid hitting range
    public int checkValid(int x, int y) {
        int SIZE = gp.PIXEL_SIZE;

        int deltaWidth = SIZE/3;
        int width1 = this.x;
        int width2 = this.x + deltaWidth;
        int width4 = (this.x + SIZE);
        int width3 = width4 - deltaWidth;

        int deltaHeight = SIZE/3;
        int height1 = this.y + deltaHeight;
        int height2 = (this.y + SIZE) - deltaHeight;

        Boolean validXLeft = between(x, width1, width2);
        Boolean validXRight = between(x, width3, width4);
        Boolean validY = between(y, height1, height2);

        if (validXLeft && validY){
            return 1;
        } else if (validXRight && validY) {
            return 2;
        } else {
            return 3;
        }
    }

    // EFFECTS: helper
    public boolean between(int val, int lo, int hi){
        return (lo <= val && val <= hi);
    }

    // EFFECTS: draw the corresponding cat
    public void updateCatReaction() {
        if (startReactionTime == 0){
            if (mh.leftDirection){
                startReactionTime = System.currentTimeMillis();
                img = left;
                mh.leftDirection = false;
            } else if (mh.rightDirection) {
                startReactionTime = System.currentTimeMillis();
                img = right;
                mh.rightDirection = false;
            }
        } else {
            if ((System.currentTimeMillis() - startReactionTime) >= WAIT){
                startReactionTime = 0;
                img = regular;
            }
            mh.leftDirection = false;
            mh.rightDirection = false;
        }
    }
    // EFFECT: update the cat reaction
    public void drawCat(Graphics g) {
//      change to 2D
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(img, x, y, gp.getPixelSize(), gp.getPixelSize(), null);
    }

}
