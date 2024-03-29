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
    private int gameState;
    private final int PLAY = 1;
    private final int OPENING = 2; // move by pressing entre
    private final int CLOSING = 3; // turn gameThread = null to exist
    private final int TRANSITION = 4;

    // create necessary components
    public Textbox textbox = new Textbox(this);
    public MouseHandler m = new MouseHandler(this);
    public KeyHandler key = new KeyHandler(this);
    public Cat cat = new Cat(this, m);
    private Sound s = new Sound();
    private Image i = new Image();
    private Thread gameThread;

    // the index constant of image in array
    private int index = 0;
    private int OPENING_END_INDEX = 20;
    private int CLOSING_START_INDEX = 26;
    private int CLOSING_END_INDEX = 33;
    private int TRANSITION_START = 12;
    private int TRANSITION_END = 25;

    // EFFECTS: create an object with the intended width and height
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.lightGray);
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
    private void playUpdate() {
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
                index = CLOSING_START_INDEX;
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            } else if (cat.transition == true) {
                try {
                    Thread.sleep((long) 2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                cat.restart();
                textbox.isClicked = false;
                gameState = TRANSITION;
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

    private void openUpdate() {
        key.active = true;
        if (index == OPENING_END_INDEX){
            gameState = PLAY;
            key.active = false;
        }
    }

    private void closeUpdate() {
        key.active = true;
        if (index == CLOSING_END_INDEX){
            key.active = false;
            gameThread = null;
            index--;
            s.stopMusic();
        }
    }

    private void transitionUpdate() {
        key.active = true;
        if (index == TRANSITION_END){
            gameState = OPENING;
            index = TRANSITION_START;
            key.active = false;
        }
    }

    private void playDraw(Graphics g) {
        if (!(textbox.isClicked)) {
            textbox.drawTextBox(g);
        } else {
            cat.drawCat(g);
        }
    }

    private void draw(Graphics g) {
        g.drawImage(i.imagelist.get(index),0,0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
    }

    // EFFECTS: updates world information in updates 30 times per second
    private void update() {
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
        } else {
            draw(g);
        }
    }

    // Setters:
    public void incrementIndex() {
        index++;
    }

}