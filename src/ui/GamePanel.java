package ui;

import model.Cat;
import model.MouseHandler;
import model.Textbox;

import javax.swing.*;
import java.awt.*;

// the class contains all functionality of JPanel class
public class GamePanel extends JPanel implements Runnable {

    // How big the character will appear on screen
    private final int ORIGINAL_PIXEL_SIZE = 64;
    private final int SCALE = 4;
    public final int PIXEL_SIZE = ORIGINAL_PIXEL_SIZE * SCALE;

    // Screen Settings
    public final int SCREEN_WIDTH = 800;
    public final int SCREEN_HEIGHT = 600;
    private final int FRAME_PER_SEC = 60;

    // create game state
    // todo create k handler to move through opening state
    public int gameState;
    public final int PLAY = 1;
    public final int OPENING = 2; // move by pressing entre
    public final int CLOSING = 3; // turn gameThread = null to exis


    // create a game clock that updates characters
    public Textbox textbox = new Textbox();
    MouseHandler m = new MouseHandler(this);
    Cat cat = new Cat(this, m);
    Sound s = new Sound();
    Thread gameThread;

    // EFFECTS: create an object with the intended width and height
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.lightGray);
        // all drawing will be done in an offscreen painting buffer
        // improve game rendering
        this.setDoubleBuffered(true);
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
        double drawInterval = 1000 / FRAME_PER_SEC;
        double delta = 0;
        long lastTime = System.currentTimeMillis();
        long currentTime;
        s.playBackgroundMusic(1);
        gameState = PLAY;

        while (gameThread != null) {
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

    // EFFECTS: update the game during play
    public void playUpdate() {
        if (!(textbox.isClicked)) {
            this.textbox.move();
            this.textbox.handleBoundary();
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            cat.updateCatReaction();
            this.setCursor(m.customize());

            if (cat.over == true) {
                try {
                    Thread.sleep((long) 5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                gameState = CLOSING;
            } else if (cat.transition == true) {
                try {
                    Thread.sleep((long) 2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                cat.restart();
                textbox.isClicked = false;
            }
        }
    }

    public void playDraw(Graphics g) {
        if (!(textbox.isClicked)) {
            textbox.drawTextBox(g);
        } else {
            cat.drawCat(g);
        }
    }

    public void gameClosing() {
    }

    // EFFECTS: updates world information in updates 30 times per second
    public void update() {
        if (gameState == PLAY) {
            playUpdate();
        }
    }

    public void paintComponent(Graphics g) {
        // calling it with the parent class - JPanel
        super.paintComponent(g);

        if (gameState == PLAY) {
            playDraw(g);
        }

    }

    // Getters:

    public int getPixelSize() {
        return PIXEL_SIZE;
    }

}