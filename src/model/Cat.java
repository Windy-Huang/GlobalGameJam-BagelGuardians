package model;

import ui.GamePanel;
import ui.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Cat {
    public static final int WAIT = 175;
    public static final int NO_REACTION = 3000;
    public final int TARGET = 5;

    public GamePanel gp;
    public MouseHandler mh;
    public Sound s = new Sound();
    public BufferedImage left, right, regular, smirky, cry, img;
    public long startReactionTime = 0;
    public long noReactionTime = 0;
    public Boolean transition = false;
    public Boolean over = false;
    public int hit = 0;
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
            smirky = ImageIO.read(getClass().getResourceAsStream("/smirky.png"));
            cry = ImageIO.read(getClass().getResourceAsStream("/cry.png"));
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

    // EFFECTS: update the cat to the corresponding reaction
    public void updateCatReaction() {
        if (startReactionTime == 0){
            if (mh.leftDirection){
                startReactionTime = System.currentTimeMillis();
                noReactionTime = 0;
                img = left;
                hit++;
                mh.leftDirection = false;
                checkHit();
            } else if (mh.rightDirection) {
                startReactionTime = System.currentTimeMillis();
                noReactionTime = 0;
                img = right;
                hit++;
                mh.rightDirection = false;
                checkHit();
            } else if (noReactionTime != 0) {
                if ((System.currentTimeMillis() - noReactionTime) >= NO_REACTION) {
                    img = smirky;
                }
            } else {
                noReactionTime = System.currentTimeMillis();
            }
        } else {
            if ((System.currentTimeMillis() - startReactionTime) >= WAIT) {
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
        g2d.drawString(Integer.toString(hit), 400, 100);
        if (img == smirky) {
            s.playSoundEffect(2);
            transition = true;
        } else if (img == cry) {
            s.playSoundEffect(3);
            over = true;

        }
    }

    public void restart() {
        startReactionTime = 0;
        noReactionTime = 0;
        transition = false;
        img = regular;
        hit = 0;
    }

    public void checkHit() {
        if (hit >= TARGET) {
            img = cry;
        }
    }
}
