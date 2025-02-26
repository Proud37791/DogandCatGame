package game.component;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import game.logic.Player;

public class GamePanel extends JPanel {
    private Dog dog;
    private Cat cat;
    private BufferedImage dogSpriteSheet;
    private BufferedImage catSpriteSheet;
    private BufferedImage[] dogFrames;
    private BufferedImage[] catFrames;
    private int currentFrame = 0;
    private int frameDelay = 10; // Delay between frames
    private int frameCount = 0;

    public GamePanel() {
        dog = new Dog("Dog", 100, 100);
        cat = new Cat("Cat", 600, 100);

        // Load sprite sheets
        try {
            dogSpriteSheet = ImageIO.read(new File("resources/dog_sprite.png"));
            catSpriteSheet = ImageIO.read(new File("resources/cat_sprite.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Extract frames from sprite sheets
        dogFrames = extractFrames(dogSpriteSheet, 4, 2); // Assuming 4 columns and 2 rows
        catFrames = extractFrames(catSpriteSheet, 4, 2); // Assuming 4 columns and 2 rows

        // Setup initial game state, load images, etc.
        Timer timer = new Timer(100, e -> {
            frameCount++;
            if (frameCount >= frameDelay) {
                currentFrame = (currentFrame + 1) % dogFrames.length;
                frameCount = 0;
                repaint();
            }
        });
        timer.start();
    }

    private BufferedImage[] extractFrames(BufferedImage spriteSheet, int cols, int rows) {
        int frameWidth = 512 / cols;  // 128 pixels per frame
        int frameHeight = 256 / rows; // 128 pixels per frame
        BufferedImage[] frames = new BufferedImage[cols * rows];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                frames[row * cols + col] = spriteSheet.getSubimage(
                    col * frameWidth, row * frameHeight, frameWidth, frameHeight);
            }
        }
        return frames;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw animated dog and cat
        if (dogFrames != null && catFrames != null) {
            g.drawImage(dogFrames[currentFrame], dog.getX(), dog.getY(), this);
            g.drawImage(catFrames[currentFrame], cat.getX(), cat.getY(), this);
        }

        // Draw health bars
        g.setColor(Color.RED);
        g.drawString(dog.getName() + " Health: " + dog.getHealth(), dog.getX(), dog.getY() - 10);
        g.drawString(cat.getName() + " Health: " + cat.getHealth(), cat.getX(), cat.getY() - 10);
    }
}