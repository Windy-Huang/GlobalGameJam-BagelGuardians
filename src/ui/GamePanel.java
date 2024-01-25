package ui;

import catCharacter.Cat;
import catCharacter.MouseHandler;

import javax.swing.*;
import java.awt.*;

// the class contains all functionality of JPanel class
public class GamePanel extends JPanel implements Runnable{

    // How big the character will appear on screen
    private final int ORIGINAL_PIXEL_SIZE = 32;
    private final int SCALE = 5;
    private final int PIXEL_SIZE = ORIGINAL_PIXEL_SIZE * SCALE;

    // Screen Settings
    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 600;
    private final int FRAME_PER_SEC = 30;

    // create a game clock that updates characters
// testing
    MouseHandler m = new MouseHandler();
    Cat cat = new Cat(this, m);
    Thread gameThread;

    // EFFECTS: create an object with the intended width and height
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.lightGray);
        // all drawing will be done in an offscreen painting buffer
        // improve game rendering
        this.setDoubleBuffered(true);
// testing
        this.addMouseListener(m);
        this.setFocusable(true);
    }

    // EFFECTS: initiate the game clock
    public void startGameThread() {
        // pass GamePanel object into the thread
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000/FRAME_PER_SEC;
        double delta = 0;
        long lastTime = System.currentTimeMillis();
        long currentTime;


        while(gameThread != null) {
            currentTime = System.currentTimeMillis();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta = 0;
            }
        }
    }

    // EFFECTS: updates world information in updates 30 times per second
    public void update() {
// testing
        this.setCursor(m.customize());
    }

    public void paintComponent(Graphics g) {
        // calling it with the parent class - JPanel
        super.paintComponent(g);

// testing stuff
        cat.drawCat(g);
    }

    // Getters:

    public int getPixelSize() {return PIXEL_SIZE;}
}