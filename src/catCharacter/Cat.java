package catCharacter;

import ui.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Cat {

    public GamePanel gp;
    public MouseHandler mh;
    public BufferedImage left, right, regular;
    public int x=0,y=0;

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
    }

    // EFFECTS: return true if the coordinate is in valid hitting range
    public boolean checkValid(int x, int y) {
        int SIZE = gp.PIXEL_SIZE;

        int deltaWidth = (this.x + SIZE)/3;
        int width1 = this.x;
        int width2 = this.x + deltaWidth;
        int width4 = (this.x + SIZE);
        int width3 = width4 - deltaWidth;

        int deltaHeight = (this.y + SIZE)/3;
        int height1 = this.y + deltaHeight;
        int height2 = (this.y + SIZE) - deltaHeight;

        Boolean validX = between(x, width1, width2) || between(x, width3, width4);
        Boolean validY = between(y, height1, height2);

        return (validX && validY);
    }

    public boolean between(int val, int lo, int hi){
        return (lo <= val && val <= hi);
    }

    public void drawCat(Graphics g) {
//        change to 2D
        Graphics2D g2d = (Graphics2D)g;
//        g2d.setColor(Color.BLUE);
//        g2d.fillRect(100,100, gp.getPixelSize(), gp.getPixelSize());
//        g2d.dispose();
        BufferedImage img = null;
        if (mh.direction){
            img = left;
        } else {
            img = right;
        }
        g2d.drawImage(img, x, y, gp.getPixelSize(), gp.getPixelSize(), null);
    }

    // the current x coordinate - width of the image / 2 will return the center of the image
    // divides the cat into 3 section horizontally, valid if in outer 2
    // 4 section vertically, the inner 2 valid
    // if the mouse is in
}
