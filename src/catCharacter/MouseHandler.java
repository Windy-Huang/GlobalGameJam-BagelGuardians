package catCharacter;

import ui.GamePanel;
import ui.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MouseHandler implements MouseListener {

    public boolean direction = false;
    public Sound s = new Sound();
    public GamePanel gp;
    public Cat c;

    public MouseHandler(GamePanel gp) {
        this.gp = gp;
        c = new Cat(gp, this);
    }

    // EFFECTS: slap the cat based on the location of the mouse
    @Override
    public void mouseClicked(MouseEvent e) {
        if (c.checkValid(e.getX(), e.getY())) {
            System.out.println(e.getY());
            s.play(0);
            direction = !direction;
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
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}

