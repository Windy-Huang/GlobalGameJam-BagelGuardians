package model;

import ui.GamePanel;
import ui.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MouseHandler implements MouseListener {

    public boolean leftDirection;
    public boolean rightDirection;
    public Sound s;
    public GamePanel gp;
    public Cat c;
    public Textbox t;

    public MouseHandler(GamePanel gp) {
        this.leftDirection = false;
        this.rightDirection = false;
        s = new Sound();
        this.gp = gp;
        c = new Cat(gp, this);
        t = gp.textbox;
    }


    // EFFECTS: slap the cat based on the location of the mouse
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("(" + e.getX() + ", " + e.getY() + ")");
        System.out.println("(" + this.t.x + ", " + this.t.y + ")");
        System.out.println(t.isClicked);

        if (((e.getX() < (this.t.x + (this.t.width/2))) && (e.getX() > (this.t.x-(this.t.width/2))))
                && ((e.getY() < (this.t.y + (this.t.height/2)) && (e.getY() > (this.t.y-(this.t.width/2)))))) {
            t.isClicked = true;
        } else if (c.checkValid(e.getX(), e.getY()) == 1) {
            System.out.println("clicked left");
            s.playSoundEffect(0);
            leftDirection = true;
        } else if (c.checkValid(e.getX(), e.getY()) == 2) {
            System.out.println("clicked right");
            s.playSoundEffect(0);
            rightDirection = true;
        }

    }

    // failed
    public Cursor customize() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/hand.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        Point point = new Point(0, 0);
        return tk.createCustomCursor(img,point,"custom");
    }

    // not of interest
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}

