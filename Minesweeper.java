import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Minesweeper extends JFrame {
    mineGame2 game;
    GamePanel panel;

    public Minesweeper() {
        // Set up the frame
        setTitle("Minesweeper");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false); // No resizing
        game = new mineGame2();

        // Call the panel
        panel = new GamePanel(this);
        add(panel);
        setVisible(true);
    }

    public mineGame2 getModel() {
        return game;
    }
    public static void main(String[] args) {
        new Minesweeper();
    }
}