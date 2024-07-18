package com.immanuel.app;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FloppyBird extends JPanel implements ActionListener, KeyListener {

    int boardWidth = 360;
    int boardHeight = 640;

    // images for the game
    Image backgroundImage;
    Image topPipeImage;
    Image bottomPipeImage;
    Image birdImage;

    // deal with the bird
    // position
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    // size
    int birdWidth = 34;
    int birdHeight = 24;

    // making the bird object
    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image bird;

        Bird(Image image) {
            this.bird = image;
        }
    }

    // the pipes
    // topPipe
    int topX = boardWidth;
    int topY = 0;

    int topWidth = boardWidth / 6;
    int topHeight = birdHeight / 3;

    class TopPipe {
        int x = topX;
        int y = topY;

        int width = topWidth;
        int height = topHeight;

        Image topPipe;

        TopPipe(Image image) {
            this.topPipe = image;
        }
    }

    // bird logic ----------------------------
    Bird bird;
    int velocityUp = 0;
    int gravity = 1;
    // timer
    Timer gameLoop;

    // constructor -------------------------------
    FloppyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        // deal with keyListener
        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();

        // load the images
        backgroundImage = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImage = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        // bird make
        bird = new Bird(birdImage);

        // timer for frames
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    // drawing all the components on the screen ------------------
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, null);
        g.drawImage(birdImage, bird.x, bird.y, bird.width, bird.height, null);
    }

    // move method --------------------------
    public void move() {
        bird.y = Math.max(bird.y, 0);
        velocityUp += gravity;
        bird.y += velocityUp;
    }

    // listens to the actions that should be performed ---------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityUp = -12;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
