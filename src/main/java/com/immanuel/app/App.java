package com.immanuel.app;

import javax.swing.JFrame;

/**
 * Floppy Bird
 *
 */
public class App {
    public static void main(String[] args) {
        int frameWidth = 360;
        int frameHeight = 640;

        FloppyBird floppyBird = new FloppyBird();

        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(floppyBird);
        frame.pack();
        floppyBird.requestFocus(true);
        frame.setVisible(true);
    }
}
