package model;

import ui.GamePanel;
import ui.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Cat {
    public static final int WAIT = 175;
    public static final int NO_REACTION = 2750;
    public final int TARGET = 15;

    private GamePanel gp;
    private MouseHandler mh;
    private Sound s = new Sound();
    private BufferedImage left, right, regular, smirky, cry, img, background;
    private long startReactionTime = 0;
    private long noReactionTime = 0;
    public Boolean transition = false;
    public Boolean over = false;
    private int hit = 0;
    private int x=230,y=240;

    public Cat(GamePanel gp, MouseHandler mh){
        this.gp = gp;
        this.mh = mh;
        getImage();
    }

    // EFFECTS: imports the image
    private void getImage() {
        try {
            left = ImageIO.read(getClass().getResourceAsStream("/character/left.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/character/right.png"));
            regular = ImageIO.read(getClass().getResourceAsStream("/character/regular.png"));
            smirky = ImageIO.read(getClass().getResourceAsStream("/character/smirky.png"));
            cry = ImageIO.read(getClass().getResourceAsStream("/character/cry.png"));
            background = ImageIO.read(getClass().getResourceAsStream("/image/background.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        img = regular;
    }

    // EFFECTS: return true if the coordinate is in valid hitting range
    public int checkValid(int x, int y) {
        int SIZE = gp.PIXEL_SIZE;
        int delta = SIZE/3;

        int width1 = this.x;
        int width2 = this.x + delta;
        int width4 = (this.x + SIZE);
        int width3 = width4 - delta;

        int height1 = this.y + delta;
        int height2 = (this.y + SIZE) - delta;

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
    private boolean between(int val, int lo, int hi){
        return (lo <= val && val <= hi);
    }

    private void slapped(BufferedImage i) {
        startReactionTime = System.currentTimeMillis();
        noReactionTime = 0;
        img = i;
        hit++;
        checkHit();
    }

    // EFFECTS: update the cat to the corresponding reaction
    public void updateCatReaction() {
        if (startReactionTime == 0){
            if (mh.getLeftDirection()){
                slapped(left);
                mh.setLeftDirection(false);
            } else if (mh.getRightDirection()) {
                slapped(right);
                mh.setRightDirection(false);
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
            mh.setLeftDirection(false);
            mh.setRightDirection(false);
        }
    }

    // EFFECT: update the cat reaction
    public void drawCat(Graphics g) {
//      change to 2D
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(background, 0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, null);
        g2d.drawImage(img, x, y, gp.PIXEL_SIZE, gp.PIXEL_SIZE, null);
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

    private void checkHit() {
        if (hit >= TARGET) {
            img = cry;
        }
    }

    // GETTER:
    public long getStartReactionTime() {
        return startReactionTime;
    }

    public long getNoReactionTime() {
        return noReactionTime;
    }

    public int getHit() {
        return hit;
    }

    public BufferedImage getSmirky() {
        return smirky;
    }

    public BufferedImage getCry() {
        return cry;
    }

    public BufferedImage getImg() {
        return img;
    }

}
