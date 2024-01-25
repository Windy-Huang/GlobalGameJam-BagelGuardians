package catCharacter;

import ui.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MouseHandler implements MouseListener {

    public boolean direction = false;
    private Sound s = new Sound();

    // EFFECTS: slap the cat based on the location of the mouse
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("clicked");
        s.play(0);
        direction = !direction;
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

