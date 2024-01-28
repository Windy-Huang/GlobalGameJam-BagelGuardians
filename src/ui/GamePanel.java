package ui;

import model.Cat;
import model.KeyHandler;
import model.MouseHandler;
import model.Textbox;

import javax.swing.*;
import java.awt.*;

// the class contains all functionality of JPanel class
public class GamePanel extends JPanel implements Runnable {

    // How big the character will appear on screen
    private final int ORIGINAL_PIXEL_SIZE = 64;
    private final int SCALE = 5;
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
    public final int CLOSING = 3; // turn gameThread = null to exist
    public final int TRANSITION = 4;


    // create a game clock that updates characters
    public Textbox textbox = new Textbox();
    MouseHandler m = new MouseHandler(this);
    KeyHandler key = new KeyHandler(this);
    Cat cat = new Cat(this, m);
    Sound s = new Sound();
    Image i = new Image();
    public Thread gameThread;

    // the index of image in array
    public int index = 0;
    public int OPENING_END_INDEX = 20;
    public int CLOSING_END_INDEX = 20;
    public int TRANSITION_START = 12;
    public int TRANSITION_END = 22;

    // EFFECTS: create an object with the intended width and height
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.lightGray);
        // all drawing will be done in an offscreen painting buffer
        // improve game rendering
        this.setDoubleBuffered(true);
        this.addMouseListener(m);
        this.addKeyListener(key);
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
        gameState = OPENING;

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
                index = TRANSITION_START;
                gameState = TRANSITION;
            }
        }
    }

    public void openUpdate() {
        key.active = true;
        if (index == OPENING_END_INDEX){
            gameState = PLAY;
            key.active = false;
        }
    }

    public void closeUpdate() {
        key.active = true;
        if (index == CLOSING_END_INDEX){
            key.active = false;
            gameThread = null;
            index--;
            s.stopMusic();
        }
    }

    public void transitionUpdate() {
        key.active = true;
        index = OPENING_END_INDEX;
        if (index == TRANSITION_END){
            gameState = OPENING;
            index = TRANSITION_START;
            key.active = false;
        }
    }

    public void playDraw(Graphics g) {
        if (!(textbox.isClicked)) {
            textbox.drawTextBox(g);
        } else {
            cat.drawCat(g);
        }
    }

    public void openDraw(Graphics g) {
        g.drawImage(i.imagelist.get(index),0,0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
    }

    public void closeDraw(Graphics g) {
        g.drawImage(i.imagelist.get(index),0,0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
    }

    // EFFECTS: updates world information in updates 30 times per second
    public void update() {
        if (gameState == PLAY) {
            playUpdate();
        } else if (gameState == OPENING) {
            openUpdate();
        } else if (gameState == CLOSING) {
            closeUpdate();
        } else if (gameState == TRANSITION) {
            transitionUpdate();
        }
    }

    public void paintComponent(Graphics g) {
        // calling it with the parent class - JPanel
        super.paintComponent(g);

        if (gameState == PLAY) {
            playDraw(g);
        } else if (gameState == OPENING) {
            openDraw(g);
        } else if (gameState == CLOSING) {
            closeDraw(g);
        }
    }

    // Getters:

    public int getPixelSize() {
        return PIXEL_SIZE;
    }

}