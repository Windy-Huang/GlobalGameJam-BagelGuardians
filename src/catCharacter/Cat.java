package catCharacter;

import ui.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Cat {

    private GamePanel gp;
    private MouseHandler mh;
    private BufferedImage left, right, regular;


    public Cat(GamePanel gp, MouseHandler mh){
        this.gp = gp;
        this.mh = mh;
        getImage();
    }

    public void getImage() {
        try {
            left = ImageIO.read(getClass().getResourceAsStream("/left.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/right.png"));
            regular = ImageIO.read(getClass().getResourceAsStream("/regular.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
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
        g2d.drawImage(img, 100, 100, gp.getPixelSize(), gp.getPixelSize(), null);
    }
}
