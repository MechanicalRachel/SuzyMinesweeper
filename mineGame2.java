import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;

public class mineGame2 {
    public static boolean gameDone = false;
    public static int n = 10;
    public static int mineCounter = n * 2;
    public static int gameCounter = (n * n) - mineCounter;
    public static String result = "none";


    public static String[][] zeroChecker(boolean[][] mines, int coord1, int coord2, String[][] board, int gameCounter) {
        if (coord1 != 0 && coord2 != 0) {
            if (board[coord1-1][coord2-1].equals("?")) {
                gameCounter -= 1;
            }
            board[coord1-1][coord2-1] = tileChecker(mines, coord1-1, coord2-1, board);
        }
        if (coord1 != 0) {
            if (board[coord1-1][coord2].equals("?")) {
                gameCounter -= 1;
            }
            board[coord1-1][coord2] = tileChecker(mines, coord1-1, coord2, board);
        }
        if (coord2 != (n-1) && coord1 != 0) {
            if (board[coord1-1][coord2+1].equals("?")) {
                gameCounter -= 1;
            }
            board[coord1-1][coord2+1] = tileChecker(mines, coord1-1, coord2+1, board);
        }
        if (coord2 != 0) {
            if (board[coord1][coord2-1].equals("?")) {
                gameCounter -= 1;
            }
            board[coord1][coord2-1] = tileChecker(mines, coord1, coord2-1, board);
        }
        if (coord2 != (n-1)) {
            if (board[coord1][coord2+1].equals("?")) {
                gameCounter -= 1;
            }
            board[coord1][coord2+1] = tileChecker(mines, coord1, coord2+1, board);
        }
        if (coord2 != 0 && coord1 != (n-1)) {
            if (board[coord1+1][coord2-1].equals("?")) {
                gameCounter -= 1;
            }
            board[coord1+1][coord2-1] = tileChecker(mines, coord1+1, coord2-1, board);
        }
        if (coord1 != (n-1)) {
            if (board[coord1+1][coord2].equals("?")) {
                gameCounter -= 1;
            }
            board[coord1+1][coord2] = tileChecker(mines, coord1+1, coord2, board);
        }
        if (coord1 != (n-1) && coord2 != (n-1)) {
            if (board[coord1+1][coord2+1].equals("?")) {
                gameCounter -= 1;
            }
            board[coord1+1][coord2+1] = tileChecker(mines, coord1+1, coord2+1, board);
        }
        return board;
    }

    public static boolean getGameDone() {
        return gameDone;
    }

    public static String getResults() {
        return result;
    }

    public static boolean[][] generateMines(int n) {
        Random r = new Random();
        boolean[][] mines = new boolean[n][n];
        int dim1;
        int dim2;

        // initialize all values on the board to false
        for (int i = 0; i < n; ++i) {
            Arrays.fill(mines[i], Boolean.FALSE);
        }
        for (int i = 0; i < mineCounter; ++i) {
            dim1 = r.nextInt(n);
            dim2 = r.nextInt(n);
            if (mines[dim1][dim2]) { // if the spot is already a mine, skip it and try again
                --i;
                continue;
            }
            else {
                mines[dim1][dim2] = true;
            }
        }
        return mines;
    }

    public static String[][] generateBoard(int n) {
        String[][] board = new String[n][n];
        for (int i = 0; i < n; ++i) {
            Arrays.fill(board[i], "?");
        }
        return board;
    }

    public static String[][] shoot(String[][] board, boolean[][] mines, int coord1, int coord2, int gameCounter) {
        // if shot hits a mine, lose game
        if (mines[coord1][coord2]) {
            gameDone = true;
            result = "L";
        }
        // if shot doesn't hit mine, check for surrounding mines
        else {
            //System.out.println(gameCounter);
            if (gameCounter == 1) {
                gameDone = true;
                result = "W";
            }
            board[coord1][coord2] = tileChecker(mines, coord1, coord2, board);
            if (board[coord1][coord2].equals("0")) {
                // TODO: reveal the nearby zeroes
                board = zeroChecker(mines, coord1, coord2, board, gameCounter);
            }
        }
        return board;   
    }

    public static String[][] flag(String[][] board, int coord1, int coord2) {
        if (board[coord1][coord2].equals("F")) {
            board[coord1][coord2] = "?";
        }
        else {
            board[coord1][coord2] = "F";
        }
        return board;
    }

    public static String tileChecker(boolean[][] mines, int coord1, int coord2, String[][] board) {
        int count = 0;
        for (int r = -1; r < 2; ++r) {
            for (int c = -1; c < 2; ++c) {
                // don't let row index go out of bounds
                if ((coord1 == 0 && r == -1) || (coord1 == mines.length-1 && r == 1)) {
                    continue;
                }
                // don't let column index go out of bounds
                if (coord2 == 0 && c == -1 || (coord2 == mines.length-1 && c == 1)) {
                    continue;
                }
                // don't include center tile in check
                if (r == 0 && c == 0) {
                    continue;
                }
                // count mines, if any
                else if (mines[coord1+r][coord2+c]) {
                    count += 1;
                }
            }
        }
        return Integer.toString(count);
    }
    
    public static void main(String[] args) {
        boolean[][] mines = new boolean[n][n];
        String[][] board = new String[n][n];
        Scanner scnr = new Scanner(System.in);
        int coord1 = 0;
        int coord2 = 0;
        String command;

        // generate mine board
        mines = generateMines(n);

        // fill in the main game board
        board = generateBoard(n);

        //print board
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {

                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }

        while (!gameDone) {
            System.out.println("Enter a command and coordinates.");
            command = scnr.next(); // capture command
            if (command.equals("shoot")) { 
                coord1 = scnr.nextInt(); //capture row coordinate
                coord2 = scnr.nextInt(); //capture column coordinate
                board = shoot(board, mines, coord1, coord2, gameCounter);
                if (gameDone) {
                    System.out.println("you lost");
                }
                //print board and count remainding tiles
                for (int i = 0; i < n; ++i) {
                    for (int j = 0; j < n; ++j) {
                        System.out.print(board[i][j] + " ");
                        boolean test = board[i][j].equals("?");
                    }
                    System.out.println();
                }
                System.out.println(gameCounter);
            }

            // flag command
            else if (command.equals("flag")) {
                coord1 = scnr.nextInt();
                coord2 = scnr.nextInt();
                board = flag(board, coord1, coord2);
                //print board
                for (int i = 0; i < n; ++i) {
                    for (int j = 0; j < n; ++j) {
                        System.out.print(board[i][j] + " ");
                    }
                    System.out.println();
                }
            }

            // if all the non-mine tiles are revealed, player wins
            if (gameCounter == 1) {
                gameDone = true;
                System.out.println("You win! :)");
                result = "W";
            }

        }
    }

}
