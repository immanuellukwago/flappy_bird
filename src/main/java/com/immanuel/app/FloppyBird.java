package com.immanuel.app;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;
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

    // score
    double score = 0.0;

    // game over
    boolean isGameOver = false;

    // collision logic
    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

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

    // Pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image pipe;

        Pipe(Image image) {
            this.pipe = image;
        }
    }

    // bird logic ----------------------------
    Bird bird;
    List<Pipe> pipes;
    double velocityLeft = -4.0;
    double velocityUp = 0;
    double gravity = 0.5;

    // timer
    // game
    Timer gameLoop;
    // pipes
    Timer pipesTimer;

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
        pipes = new LinkedList<Pipe>();

        // pipes
        pipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePipe();
            }
        });
        pipesTimer.start();

        // timer for frames
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    // method to create a pipe and add it to the panel
    public void generatePipe() {
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * pipeHeight / 2);
        int openingSpace = pipeHeight / 4;
        Pipe topPipe = new Pipe(topPipeImage);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImage);
        bottomPipe.y = topPipe.y + topPipe.height + openingSpace + 100;
        pipes.add(bottomPipe);
    }

    // drawing all the components on the screen ------------------
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, null);
        g.drawImage(birdImage, bird.x, bird.y, bird.width, bird.height, null);
        // drawing the pipes logic
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe1 = pipes.get(i);
            g.drawImage(topPipeImage, pipe1.x, pipe1.y, pipe1.width, pipe1.height, null);
        }
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe2 = pipes.get(i);
            g.drawImage(bottomPipeImage, pipe2.x, pipe2.y, pipe2.width, pipe2.height, null);
        }

        // score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (isGameOver) {
            g.drawString("Game Over!! " + String.valueOf((int) score), 20, 45);
        } else {
            g.drawString("Score: " + String.valueOf((int) score), 20, 35);
        }
    }

    // move method --------------------------
    public void move() {
        bird.y = Math.max(bird.y, 0);
        velocityUp += gravity;
        bird.y += velocityUp;

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityLeft;
            if (pipe.x == -pipe.width) {
                pipes.remove(i);
            }
            if ((bird.x + bird.width) > (pipe.x + pipe.width)) {
                score += 0.5; // because they are two pipes -> 1 - score
                // score = score / 20;
            }
            if (collision(bird, pipe)) {
                isGameOver = true;
            }
        }
        if (bird.y >= boardHeight) {
            isGameOver = true;
        }
    }

    // listens to the actions that should be performed ---------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (isGameOver) {
            gameLoop.stop();
            pipesTimer.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityUp = -9;
            if (isGameOver) {
                bird.y = birdY;
                velocityUp = 0;
                pipes.clear();
                score = 0;
                isGameOver = false;
                gameLoop.start();
                pipesTimer.start();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
