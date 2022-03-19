import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ClassLoader;

public class GamePanel extends JPanel {
    int n = 10; // length of one side of board
    Minesweeper frame; // frame callback
    public int coord1; // x coordinate
    public int coord2; // y coordinate
    // Game defaults
    boolean[][] mines; // mine board
    String[][] board; // normal board
    String mineCount; // number of mines
    boolean gameDone = false; // whether the game has finished or not
    int gameCounter = (n * n) - (n * 2); // number of non-mine spaces left

    public GamePanel(Minesweeper mf) {
        //Icon ic = new ImageIcon("flower.png"); // button picture
        Icon ic = new ImageIcon(getClass().getResource("/resources/images/flower.png"));
        frame = mf;
        frame.getModel(); 

        mines = frame.getModel().generateMines(n); // generate mine board
        board = frame.getModel().generateBoard(n); // generate normal board

        setLayout(new GridLayout(n,n)); // gridlayout for buttons
        for (int i = 1; i <= n * n; ++i) {
            JButton toggle = new JButton(ic);
            toggle = new JButton("", ic);
            toggle.addActionListener(new ButtonHandler()); // left click event handler
            add(toggle);
            toggle.addMouseListener(new MouseHandler()); // right click event handler
            add(toggle);
        }
    }
    
    // event handler for a button getting left clicked
    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            coord1 = ((JButton)e.getSource()).getX();
            coord2 = ((JButton)e.getSource()).getY();
            gameCounter -= 1; // one less button to be clicked
            // call game to shoot normal board at coordinates
            board = frame.getModel().shoot(board, mines, coord2/45, coord1/45, gameCounter);
            // call game to see if the game ended
            gameDone = frame.getModel().getGameDone();
            // if the game did not end, change the picture of the button
            if (!gameDone) {
                // check for surrounding mines
                mineCount = frame.getModel().tileChecker(mines, coord2/45, coord1/45, board);
                if (board[coord2/45][coord1/45].equals("0")) {
                    board = frame.getModel().zeroChecker(mines, coord1/45, coord2/45, board, gameCounter);
                }
                Icon ic = new ImageIcon(getClass().getResource("/resources/images/flower.png")); // default icon

                // the following are the images that the button could switch to
                if(mineCount.equals("0")) {
                    ic = new ImageIcon(getClass().getResource("/resources/images/flower0.png"));
                }
                if(mineCount.equals("1")) {
                    ic = new ImageIcon(getClass().getResource("/resources/images/flower1.png"));
                }
                if(mineCount.equals("2")) {
                    ic = new ImageIcon(getClass().getResource("/resources/images/flower2.png"));
                }
                if(mineCount.equals("3")) {
                    ic = new ImageIcon(getClass().getResource("/resources/images/flower3.png"));
                }
                if(mineCount.equals("4")) {
                    ic = new ImageIcon(getClass().getResource("/resources/images/flower4.png"));
                }
                if(mineCount.equals("5")) {
                    ic = new ImageIcon(getClass().getResource("/resources/images/flower5.png"));
                }
                if(mineCount.equals("6")) {
                    ic = new ImageIcon(getClass().getResource("/resources/images/flower6.png"));
                }
                if(mineCount.equals("7")) {
                    ic = new ImageIcon(getClass().getResource("/resources/images/flower7.png"));
                }
                if(mineCount.equals("8")) {
                    ic = new ImageIcon(getClass().getResource("/resources/images/flower8.png"));
                }

                // set icon of the button that was clicked on
                ((JButton)e.getSource()).setIcon(ic);
            }
            // if the game has ended, get the results and print lose or win
            else if (frame.getModel().getResults().equals("L")) {
                //lose
                Icon ic = new ImageIcon(getClass().getResource("/resources/images/lose.png"));
                removeAll(); // clear board
                setLayout(new BorderLayout());
                JButton lose = new JButton("", ic);
                lose.addActionListener(new ExitHandler()); // exit event handler
                lose.setBounds(100, 100, 300, 300);
                add(lose, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
            else if (frame.getModel().getResults().equals("W")) {
                //win
                Icon ic = new ImageIcon(getClass().getResource("/resources/images/win.png"));
                removeAll(); // clear board
                setLayout(new BorderLayout());
                JButton win = new JButton("", ic);
                win.addActionListener(new ExitHandler()); // exit event handler
                win.setBounds(100, 100, 300, 300);
                add(win, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        }
    }

    // event handler for a button being left-clicked
    public class MouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            // if mouse click is right click
            if (SwingUtilities.isRightMouseButton(e)) {
                // if the button is already flagged, remove flag
                if (board[coord2/45][coord1/45].equals("F")) {
                    board = frame.getModel().flag(board, coord2/45, coord1/45);
                    Icon ic = new ImageIcon(getClass().getResource("/resources/images/flower.png"));
                    ((JButton)e.getSource()).setIcon(ic);
                }
                // if button is not flagged, add flag
                else {
                    board = frame.getModel().flag(board, coord2/45, coord1/45);
                    Icon ic = new ImageIcon(getClass().getResource("/resources/images/flowerF.png"));
                    ((JButton)e.getSource()).setIcon(ic);
                }
            }
        }
    }

    private class ExitHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // exit program when game concludes
            System.exit(0);
        }
    }
    
}