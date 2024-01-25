package ui;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {


        // create a new game window:
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Title .......");
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.setUndecorated(true);

        // resize window to preferred size
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // start
        gp.startGameThread();
    }

}
