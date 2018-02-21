/**
 * Assignment #4.
 * This program is a game of Tic-Tac-Toe
 * on a 4x4x4 three dimensional board layout
 * 
 * Authors: Anthony Ho (ankho@ucsc.edu) 
 * 
 * citations: used lines array from professor's BoardTest.java
 *              referenced BoardTest's findLineSum() and compLineSum()
 *                  for my makeLineSums() and findLineSum() 
 */ 
import java.util.*;
import java.io.*;
public class TTT3Dp {
    public static Random rng = new Random();
    public static boolean playerWon = false;
    public static boolean compWon = false;
    public static boolean draw = false;
    public static int[] sums = new int[76];
    public static int board[][][] = new int[4][4][4]; //lev row col
    // Lines array provided by professor
    static final int[][][] lines = {
        {{0,0,0},{0,0,1},{0,0,2},{0,0,3}},  //lev 0; row 0   rows in each level line 0    
        {{0,1,0},{0,1,1},{0,1,2},{0,1,3}},  //       row 1                      line 1 
        {{0,2,0},{0,2,1},{0,2,2},{0,2,3}},  //       row 2                      line 2
        {{0,3,0},{0,3,1},{0,3,2},{0,3,3}},  //       row 3                      line 3
        {{1,0,0},{1,0,1},{1,0,2},{1,0,3}},  //lev 1; row 0                      line 4
        {{1,1,0},{1,1,1},{1,1,2},{1,1,3}},  //       row 1                      line 5
        {{1,2,0},{1,2,1},{1,2,2},{1,2,3}},  //       row 2                      line 6
        {{1,3,0},{1,3,1},{1,3,2},{1,3,3}},  //       row 3                      line 7
        {{2,0,0},{2,0,1},{2,0,2},{2,0,3}},  //lev 2; row 0                      line 8
        {{2,1,0},{2,1,1},{2,1,2},{2,1,3}},  //       row 1                      line 9
        {{2,2,0},{2,2,1},{2,2,2},{2,2,3}},  //       row 2                      line 10
        {{2,3,0},{2,3,1},{2,3,2},{2,3,3}},  //       row 3                      line 11
        {{3,0,0},{3,0,1},{3,0,2},{3,0,3}},  //lev 3; row 0                      line 12
        {{3,1,0},{3,1,1},{3,1,2},{3,1,3}},  //       row 1                      line 13
        {{3,2,0},{3,2,1},{3,2,2},{3,2,3}},  //       row 2                      line 14
        {{3,3,0},{3,3,1},{3,3,2},{3,3,3}},  //       row 3                      line 15
        {{0,0,0},{0,1,0},{0,2,0},{0,3,0}},  //lev 0; col columns in each level  line 16
        {{0,0,1},{0,1,1},{0,2,1},{0,3,1}},  //       col 1                      line 17
        {{0,0,2},{0,1,2},{0,2,2},{0,3,2}},  //       col 2                      line 18
        {{0,0,3},{0,1,3},{0,2,3},{0,3,3}},  //       col 3                      line 19
        {{1,0,0},{1,1,0},{1,2,0},{1,3,0}},  //lev 1; col 0                      line 20
        {{1,0,1},{1,1,1},{1,2,1},{1,3,1}},  //       col 1                      line 21
        {{1,0,2},{1,1,2},{1,2,2},{1,3,2}},  //       col 2                      line 22
        {{1,0,3},{1,1,3},{1,2,3},{1,3,3}},  //       col 3                      line 23
        {{2,0,0},{2,1,0},{2,2,0},{2,3,0}},  //lev 2; col 0                      line 24
        {{2,0,1},{2,1,1},{2,2,1},{2,3,1}},  //       col 1                      line 25
        {{2,0,2},{2,1,2},{2,2,2},{2,3,2}},  //       col 2                      line 26
        {{2,0,3},{2,1,3},{2,2,3},{2,3,3}},  //       col 3                      line 27
        {{3,0,0},{3,1,0},{3,2,0},{3,3,0}},  //lev 3; col 0                      line 28
        {{3,0,1},{3,1,1},{3,2,1},{3,3,1}},  //       col 1                      line 29
        {{3,0,2},{3,1,2},{3,2,2},{3,3,2}},  //       col 2                      line 30
        {{3,0,3},{3,1,3},{3,2,3},{3,3,3}},  //       col 3                      line 31
        {{0,0,0},{1,0,0},{2,0,0},{3,0,0}},  //cols in vert plane in front       line 32
        {{0,0,1},{1,0,1},{2,0,1},{3,0,1}},  //                                  line 33
        {{0,0,2},{1,0,2},{2,0,2},{3,0,2}},  //                                  line 34
        {{0,0,3},{1,0,3},{2,0,3},{3,0,3}},  //                                  line 35
        {{0,1,0},{1,1,0},{2,1,0},{3,1,0}},  //cols in vert plane one back       line 36
        {{0,1,1},{1,1,1},{2,1,1},{3,1,1}},  //                                  line 37
        {{0,1,2},{1,1,2},{2,1,2},{3,1,2}},  //                                  line 38
        {{0,1,3},{1,1,3},{2,1,3},{3,1,3}},  //                                  line 39
        {{0,2,0},{1,2,0},{2,2,0},{3,2,0}},  //cols in vert plane two back       line 40
        {{0,2,1},{1,2,1},{2,2,1},{3,2,1}},  //                                  line 41
        {{0,2,2},{1,2,2},{2,2,2},{3,2,2}},  //                                  line 42
        {{0,2,3},{1,2,3},{2,2,3},{3,2,3}},  //                                  line 43
        {{0,3,0},{1,3,0},{2,3,0},{3,3,0}},  //cols in vert plane in rear        line 44
        {{0,3,1},{1,3,1},{2,3,1},{3,3,1}},  //                                  line 45
        {{0,3,2},{1,3,2},{2,3,2},{3,3,2}},  //                                  line 46
        {{0,3,3},{1,3,3},{2,3,3},{3,3,3}},  //                                  line 47
        {{0,0,0},{0,1,1},{0,2,2},{0,3,3}},  //diags in lev 0                    line 48
        {{0,3,0},{0,2,1},{0,1,2},{0,0,3}},  //                                  line 49
        {{1,0,0},{1,1,1},{1,2,2},{1,3,3}},  //diags in lev 1                    line 50
        {{1,3,0},{1,2,1},{1,1,2},{1,0,3}},  //                                  line 51
        {{2,0,0},{2,1,1},{2,2,2},{2,3,3}},  //diags in lev 2                    line 52
        {{2,3,0},{2,2,1},{2,1,2},{2,0,3}},  //                                  line 53
        {{3,0,0},{3,1,1},{3,2,2},{3,3,3}},  //diags in lev 3                    line 54
        {{3,3,0},{3,2,1},{3,1,2},{3,0,3}},  //                                  line 55
        {{0,0,0},{1,0,1},{2,0,2},{3,0,3}},  //diags in vert plane in front      line 56
        {{3,0,0},{2,0,1},{1,0,2},{0,0,3}},  //                                  line 57
        {{0,1,0},{1,1,1},{2,1,2},{3,1,3}},  //diags in vert plane one back      line 58
        {{3,1,0},{2,1,1},{1,1,2},{0,1,3}},  //                                  line 59
        {{0,2,0},{1,2,1},{2,2,2},{3,2,3}},  //diags in vert plane two back      line 60
        {{3,2,0},{2,2,1},{1,2,2},{0,2,3}},  //                                  line 61
        {{0,3,0},{1,3,1},{2,3,2},{3,3,3}},  //diags in vert plane in rear       line 62
        {{3,3,0},{2,3,1},{1,3,2},{0,3,3}},  //                                  line 63
        {{0,0,0},{1,1,0},{2,2,0},{3,3,0}},  //diags left slice                  line 64
        {{3,0,0},{2,1,0},{1,2,0},{0,3,0}},  //                                  line 65
        {{0,0,1},{1,1,1},{2,2,1},{3,3,1}},  //diags slice one to right          line 66
        {{3,0,1},{2,1,1},{1,2,1},{0,3,1}},  //                                  line 67
        {{0,0,2},{1,1,2},{2,2,2},{3,3,2}},  //diags slice two to right          line 68
        {{3,0,2},{2,1,2},{1,2,2},{0,3,2}},  //                                  line 69
        {{0,0,3},{1,1,3},{2,2,3},{3,3,3}},  //diags right slice                 line 70
        {{3,0,3},{2,1,3},{1,2,3},{0,3,3}},  //                                  line 71
        {{0,0,0},{1,1,1},{2,2,2},{3,3,3}},  //cube vertex diags                 line 72
        {{3,0,0},{2,1,1},{1,2,2},{0,3,3}},  //                                  line 73
        {{0,3,0},{1,2,1},{2,1,2},{3,0,3}},  //                                  line 74
        {{3,3,0},{2,2,1},{1,1,2},{0,0,3}}   //                                  line 75
    };
    /**
     * Method to check if a cell is empty
     * @param i level for cell
     * @param j for cell
     * @param k column for cell
     * @return True/False cell is empty
     */
    public static boolean isEmpty(int i, int j, int k) {
        return board[i][j][k] == 0;
    }
    /**
     * Method to generate the computer's random move on a non-dead line
     */
    public static void makeRandomMove() {
        boolean markMade = false;
        while (!markMade) {
            int i = rng.nextInt(sums.length);
            int numCompMoves = 0;
            int numPlayerMoves = 0;
            int numCellsOccupied = 0;
            for (int a = 0; a < lines[i][0].length; a++) {
                if (board[lines[i][a][0]]
                            [lines[i][a][1]]
                                [lines[i][a][2]] == 1) {
                    numPlayerMoves++;
                    numCellsOccupied++;
                } else if (board[lines[i][a][0]]
                                    [lines[i][a][1]]
                                        [lines[i][a][2]] == 5) {
                    numCompMoves++;
                    numCellsOccupied++;
                }
            }
            if (numCellsOccupied < 4 && 
                    (numCompMoves <= 0 || numPlayerMoves <= 0)) {
                while (!markMade) {
                    int j = rng.nextInt(4);
                    if (board[lines[i][j][0]]
                                [lines[i][j][1]]
                                    [lines[i][j][2]] == 0) {
                        board[lines[i][j][0]]
                                [lines[i][j][1]]
                                    [lines[i][j][2]] = 1;
                        markMade = true;
                    }
                }
            }
        }
    }
    /**
     * Method to mark the player's desired cell
     * @param i level for desired cell
     * @param j row for desired cell
     * @param k column for desired cell
     */
    public static void markPlayerMove(int i, int j, int k) {
        board[i][j][k] = 5;
    }
    /**
     * Method to Print the current board state
     */
    public static void printBoard() {
        int currLev = 4;
        for (int i = 3; i >= 0; i--) {
            currLev--;
            int currRow = 4;
            for (int j = 3; j >= 0; j--) {
                for (int b = currRow; b > 0; b--) {
                    System.out.print(" ");
                }
                currRow--;
                System.out.print(currLev);
                System.out.print(currRow + " ");

                for (int k = 0; k < board[0][0].length; k++) {
                    if (board[i][j][k] == 0) {
                        System.out.print(" _");
                    } else if (board[i][j][k] == 1) {
                        System.out.print(" O");
                    } else if (board[i][j][k] == 5) {
                        System.out.print(" X");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println("   " + 0 + " " + 1 + " " + 2 + " " + 3);
    }
    /**
     * Method to fill sums array with current sum for the lines
     */
    public static void makeLineSums() {
        for (int i = 0; i < sums.length; i++) {
            sums[i] = 0;
            for (int j = 0; j < 4; j++) {
                sums[i] += board[lines[i][j][0]]
                                    [lines[i][j][1]]
                                        [lines[i][j][2]];
            }
        }
    }
    /**
     * Method to find a line with a certain sum
     * @param sum desired sum
     * @return number for line containing the desired sum
     */
    public static int findLineSum(int sum) {
        for (int i = 0; i < sums.length; i++) {
            if (sums[i] == sum)
                return i;
        }
        return -1;
    }
    /**
     * Method to check if the player has won
     */
    public static void checkPlayerWin() {
        for (int i = 0; i < sums.length; i++) {
            if (sums[i] == 20) {
                playerWon = true;
            }
        }
    }
    /**
     * Method to check if the computer has won
     */
    public static void checkCompWin() {
        for (int i = 0; i < sums.length; i++) {
            if (sums[i] == 4) {
                compWon = true;
            }
        }
    }
    /**
     * Method to check if the game has reached a draw
     */
    public static void checkForDraw() {
        int numEmpty = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                for (int k = 0; k < board[0][0].length; k++) {
                    if (board[i][j][k] == 0) {
                        numEmpty++;
                    }
                }
            }
        }
        if (numEmpty == 0) {
            draw = true;
        }
    }
    /**
     * Method to check if the player can win the next turn
     * @return true/false if player can win next turn
     */
    public static boolean playerCanWin() {
        for (int i = 0; i < sums.length; i++) {
            if (sums[i] == 15) {
                return true;
            }
        }
        return false;
    }
    /**
     * Method to block a player if they're about to win
     */
    public static void blockPlayer() {
        boolean markMade = false;
        int i = 0;
        while (!markMade) {
            int currentInt = board[lines[findLineSum(15)][i][0]]
                                    [lines[findLineSum(15)][i][1]]
                                        [lines[findLineSum(15)][i][2]];
            if (currentInt == 0) {
                board[lines[findLineSum(15)][i][0]
                        ][lines[findLineSum(15)][i][1]]
                            [lines[findLineSum(15)][i][2]] = 1;

                markMade = true;
            }
            i++;
        }
    }
    /**
     * Method to check if the computer can win next turn
     * @return true/false if the computer can win next turn
     */
    public static boolean compCanWin() {
        for (int i = 0; i < sums.length; i++) {
            if (sums[i] == 3) {
                return true;
            }
        }
        return false;
    }
    /**
     * Method to mark the final cell for the computer to win
     */
    public static void markWinningCell() {
        boolean markMade = false;
        int i = 0;
        while (!markMade) {
            int currentInt = board[lines[findLineSum(3)][i][0]]
                                    [lines[findLineSum(3)][i][1]]
                                        [lines[findLineSum(3)][i][2]];
            if (currentInt == 0) {
                board[lines[findLineSum(3)][i][0]]
                        [lines[findLineSum(3)][i][1]]
                            [lines[findLineSum(3)][i][2]] = 1;

                markMade = true;
            }
            i++;
        }
    }
    /**
     * Method to check if a player is trying to create a fork situation
     * @return true/false if player is creating a fork
     */
    public static boolean findPlayerForks() {
        for (int i = 0; i < 76; i++) {
            for (int j = 1; j < 76; j++) {
                if (i != j) {
                    if (isAdjacent(i, j)) {
                        if (sums[i] == 10 && sums[j] == 10) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    /**
     * Method to check if computer has a possible fork
     * @return True/False if there is a possible fork
     */
    public static boolean findPossibleFork() {
        for (int i = 0; i < 76; i++) {
            for (int j = 1; j < 76; j++) {
                if (i != j) {
                    if (isAdjacent(i, j)) {
                        if (sums[i] == 2 && sums[j] == 2) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    /**
     * Method to block a player's fork situation
     */
    public static void blockFork() {
        boolean markMade = false;
        while (!markMade) {
            for (int i = 0; i < 76; i++) {
                for (int j = 1; j < 76; j++) {
                    if (i != j) {
                        if (isAdjacent(i, j)) {
                            if (sums[i] == 10 && sums[j] == 10) {
                                for (int a = 0; a < 4; a++) {
                                    if (board[lines[i][a][0]]
                                            [lines[i][a][1]]
                                                [lines[i][a][2]] == 0) {
                                        board[lines[i][a][0]]
                                                [lines[i][a][1]]
                                                    [lines[i][a][2]] = 1;
                                        markMade = true;
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * Method to fill a fork for the computer
     */
    public static void fillFork() {
        boolean markMade = false;
        while (!markMade) {
            for (int i = 0; i < 76; i++) {
                for (int j = 1; j < 76; j++) {
                    if (i != j) {
                        if (isAdjacent(i, j)) {
                            if (sums[i] == 2 && sums[j] == 2) {
                                for (int a = 0; a < 4; a++) {
                                    if (board[lines[i][a][0]]
                                            [lines[i][a][1]]
                                                [lines[i][a][2]] == 0) {
                                        board[lines[i][a][0]]
                                                [lines[i][a][1]]
                                                    [lines[i][a][2]] = 1;
                                        markMade = true;
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * Method to check if two lines are adjacent
     * @param l1 First line number
     * @param l2 Second line number
     * @return True/False if the two lines are adjacent
     */
    public static boolean isAdjacent(int l1, int l2) {
        String[] l1Coordinates = new String[4];
        String[] l2Coordinates = new String[4];
        for (int i = 0; i < l1Coordinates.length; i++) {
            String temp = "";
            temp += lines[l1][i][0];
            temp += lines[l1][i][1];
            temp += lines[l1][i][2];
            l1Coordinates[i] = temp;
        }

        for (int i = 0; i < l2Coordinates.length; i++) {
            String temp = "";
            temp += lines[l2][i][0];
            temp += lines[l2][i][1];
            temp += lines[l2][i][2];
            l2Coordinates[i] = temp;
        }

        for (int i = 0; i < l1Coordinates.length; i++) {
            for (int j = 0; j < l2Coordinates.length; j++) {
                if (l1Coordinates[i].equals(l2Coordinates[j])) {
                    return true;
                }
            }
        }
        return false;
    }
    /** Main Method for TTT3D */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        Scanner in2 = new Scanner(new FileInputStream(args[0]));
        boolean gameOver = false;
        int size = in2.nextInt();
        for(int i = 0; i < size ; i++) {
            board[in2.nextInt()][in2.nextInt()][in2.nextInt()] = in2.nextInt();
            makeLineSums();
        }
        while (!gameOver) {
            boolean playerTurnMade = false;
            printBoard();
            while (!playerTurnMade) {
                System.out.println("Type your move as one three digit number(lrc)");
                int userInput = in.nextInt();
                int i = userInput / 100;
                int j = (userInput / 10) % 10;
                int k = userInput % 10;
                if (isEmpty(i, j, k)) {
                    markPlayerMove(i, j, k);
                    makeLineSums();
                    playerTurnMade = true;
                } else {
                    System.out.println("Sorry that cell is occupied");
                }
            }
            checkPlayerWin();
            checkForDraw();
            if (playerWon||draw) {
                gameOver = true;
            } else {
                if (compCanWin()) {
                    markWinningCell();
                } else if (playerCanWin()) {
                    blockPlayer();
                } else if (findPossibleFork()) {
                    fillFork();
                } else if (findPlayerForks()) {
                    blockFork();
                } else {
                    makeRandomMove();
                }
                makeLineSums();
                checkCompWin();
                checkForDraw();
            }
            if (compWon || draw) {
                gameOver = true;
            }
        }
        if (playerWon) {
            printBoard();
            System.out.println("Congraulations you won!");
        } else if (compWon) {
            printBoard();
            System.out.println("Computer wins again!");
        } else if (draw) {
            printBoard();
            System.out.println("Game is a draw");
        }
    }
}
