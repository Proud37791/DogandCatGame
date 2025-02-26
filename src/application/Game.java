package application;

import javax.swing.*;
import game.component.GamePanel;

public class Game extends JFrame {
    public Game() {
        setTitle("Dog vs Cat Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            game.setVisible(true);
        });
    }
}