 
/*
Various routines for working with the key arrays: board, lines and sums in one
possible implementation of the TTT3D project.
Author: Delbert Bailey
 */
import java.io.*;
public class BoardTest{
    static int[][][] board = new int[4][4][4];
    static int[] sums = new int[76];
    // Lines array provided by professor
    static final int[][][] lines = {
    		{{0,0,0},{0,0,1},{0,0,2},{0,0,3}},  //lev 0; row 0   rows in each level	line 1    
    		{{0,1,0},{0,1,1},{0,1,2},{0,1,3}},  //       row 1     					line 2 
    		{{0,2,0},{0,2,1},{0,2,2},{0,2,3}},  //       row 2     					line 3
    		{{0,3,0},{0,3,1},{0,3,2},{0,3,3}},  //       row 3     					line 4
    		{{1,0,0},{1,0,1},{1,0,2},{1,0,3}},  //lev 1; row 0     					line 5
    		{{1,1,0},{1,1,1},{1,1,2},{1,1,3}},  //       row 1     					line 6
    		{{1,2,0},{1,2,1},{1,2,2},{1,2,3}},  //       row 2     					line 7
    		{{1,3,0},{1,3,1},{1,3,2},{1,3,3}},  //       row 3     					line 8
    		{{2,0,0},{2,0,1},{2,0,2},{2,0,3}},  //lev 2; row 0     					line 9
    		{{2,1,0},{2,1,1},{2,1,2},{2,1,3}},  //       row 1     					line 10
    		{{2,2,0},{2,2,1},{2,2,2},{2,2,3}},  //       row 2       				line 11
    		{{2,3,0},{2,3,1},{2,3,2},{2,3,3}},  //       row 3     					line 12
    		{{3,0,0},{3,0,1},{3,0,2},{3,0,3}},  //lev 3; row 0     					line 13
    		{{3,1,0},{3,1,1},{3,1,2},{3,1,3}},  //       row 1 						line 14
    		{{3,2,0},{3,2,1},{3,2,2},{3,2,3}},  //       row 2       				line 15
    		{{3,3,0},{3,3,1},{3,3,2},{3,3,3}},  //       row 3           			line 16
    		{{0,0,0},{0,1,0},{0,2,0},{0,3,0}},  //lev 0; col columns in each level  line 17
    		{{0,0,1},{0,1,1},{0,2,1},{0,3,1}},  //       col 1    					line 18
    		{{0,0,2},{0,1,2},{0,2,2},{0,3,2}},  //       col 2    					line 19
    		{{0,0,3},{0,1,3},{0,2,3},{0,3,3}},  //       col 3    					line 20
    		{{1,0,0},{1,1,0},{1,2,0},{1,3,0}},  //lev 1; col 0     					line 21
    		{{1,0,1},{1,1,1},{1,2,1},{1,3,1}},  //       col 1    					line 22
    		{{1,0,2},{1,1,2},{1,2,2},{1,3,2}},  //       col 2    					line 23
    		{{1,0,3},{1,1,3},{1,2,3},{1,3,3}},  //       col 3    					line 24
    		{{2,0,0},{2,1,0},{2,2,0},{2,3,0}},  //lev 2; col 0     					line 25
    		{{2,0,1},{2,1,1},{2,2,1},{2,3,1}},  //       col 1    					line 26
    		{{2,0,2},{2,1,2},{2,2,2},{2,3,2}},  //       col 2    					line 27
    		{{2,0,3},{2,1,3},{2,2,3},{2,3,3}},  //       col 3    					line 28
    		{{3,0,0},{3,1,0},{3,2,0},{3,3,0}},  //lev 3; col 0     					line 29
    		{{3,0,1},{3,1,1},{3,2,1},{3,3,1}},  //       col 1						line 30
    		{{3,0,2},{3,1,2},{3,2,2},{3,3,2}},  //       col 2						line 31
    		{{3,0,3},{3,1,3},{3,2,3},{3,3,3}},  //       col 3						line 32
    	    {{0,0,0},{1,0,0},{2,0,0},{3,0,0}},  //cols in vert plane in front		line 33
    	    {{0,0,1},{1,0,1},{2,0,1},{3,0,1}},	//									line 34
    	    {{0,0,2},{1,0,2},{2,0,2},{3,0,2}},  //									line 35
    	    {{0,0,3},{1,0,3},{2,0,3},{3,0,3}},  //									line 36
    	    {{0,1,0},{1,1,0},{2,1,0},{3,1,0}},  //cols in vert plane one back		line 37
    	    {{0,1,1},{1,1,1},{2,1,1},{3,1,1}},	//									line 38
    	    {{0,1,2},{1,1,2},{2,1,2},{3,1,2}},	//									line 39
    	    {{0,1,3},{1,1,3},{2,1,3},{3,1,3}},	//									line 40
    	    {{0,2,0},{1,2,0},{2,2,0},{3,2,0}},  //cols in vert plane two back		line 41
    	    {{0,2,1},{1,2,1},{2,2,1},{3,2,1}},	//									line 42
    	    {{0,2,2},{1,2,2},{2,2,2},{3,2,2}},	//									line 43
    	    {{0,2,3},{1,2,3},{2,2,3},{3,2,3}},	//									line 44
    	    {{0,3,0},{1,3,0},{2,3,0},{3,3,0}},  //cols in vert plane in rear		line 45
    	    {{0,3,1},{1,3,1},{2,3,1},{3,3,1}},	//									line 46
    	    {{0,3,2},{1,3,2},{2,3,2},{3,3,2}},	//									line 47
    	    {{0,3,3},{1,3,3},{2,3,3},{3,3,3}},	//									line 48
    	    {{0,0,0},{0,1,1},{0,2,2},{0,3,3}},  //diags in lev 0					line 49
    		{{0,3,0},{0,2,1},{0,1,2},{0,0,3}},	//									line 50
    	    {{1,0,0},{1,1,1},{1,2,2},{1,3,3}},  //diags in lev 1					line 51
    	    {{1,3,0},{1,2,1},{1,1,2},{1,0,3}},	//									line 52
    	    {{2,0,0},{2,1,1},{2,2,2},{2,3,3}},  //diags in lev 2					line 53
    	    {{2,3,0},{2,2,1},{2,1,2},{2,0,3}},	//									line 54
    	    {{3,0,0},{3,1,1},{3,2,2},{3,3,3}},  //diags in lev 3					line 55
    	    {{3,3,0},{3,2,1},{3,1,2},{3,0,3}},	//									line 56
    	    {{0,0,0},{1,0,1},{2,0,2},{3,0,3}},  //diags in vert plane in front		line 57
    	    {{3,0,0},{2,0,1},{1,0,2},{0,0,3}},	//									line 58
    	    {{0,1,0},{1,1,1},{2,1,2},{3,1,3}},  //diags in vert plane one back		line 59
    	    {{3,1,0},{2,1,1},{1,1,2},{0,1,3}},	//									line 60
    	    {{0,2,0},{1,2,1},{2,2,2},{3,2,3}},  //diags in vert plane two back		line 61
    	    {{3,2,0},{2,2,1},{1,2,2},{0,2,3}},	//									line 62
    	    {{0,3,0},{1,3,1},{2,3,2},{3,3,3}},  //diags in vert plane in rear		line 63
    	    {{3,3,0},{2,3,1},{1,3,2},{0,3,3}},	//									line 64
    	    {{0,0,0},{1,1,0},{2,2,0},{3,3,0}},  //diags left slice      			line 65
    	    {{3,0,0},{2,1,0},{1,2,0},{0,3,0}}, 	//    								line 66
    	    {{0,0,1},{1,1,1},{2,2,1},{3,3,1}},  //diags slice one to right			line 67
    	    {{3,0,1},{2,1,1},{1,2,1},{0,3,1}},  //    								line 68
    	    {{0,0,2},{1,1,2},{2,2,2},{3,3,2}},  //diags slice two to right      	line 69
    	    {{3,0,2},{2,1,2},{1,2,2},{0,3,2}},  //      							line 70
    	    {{0,0,3},{1,1,3},{2,2,3},{3,3,3}},  //diags right slice      			line 71
    	    {{3,0,3},{2,1,3},{1,2,3},{0,3,3}},  //      							line 72
    	    {{0,0,0},{1,1,1},{2,2,2},{3,3,3}},  //cube vertex diags					line 73
    	    {{3,0,0},{2,1,1},{1,2,2},{0,3,3}},	//									line 74
    	    {{0,3,0},{1,2,1},{2,1,2},{3,0,3}},	//									line 75
    	    {{3,3,0},{2,2,1},{1,1,2},{0,0,3}}   //	         						line 76
    };
    //utility display game array
	static void printBoard() {
		for (int lev = 3; lev >= 0; lev--) {
			for (int row = 3; row >= 0; row--) {
				for (int col = 0; col < 4; col++) {
					System.out.printf("%1d ", board[lev][row][col]);
				}
				System.out.printf("\n");
			}
			System.out.printf("\n");
		}
	}

	// utility display sums array
	static void printSums() {
		for (int i = 0; i < sums.length; i++) {
			System.out.println("line " + i + "is " + sums[i]);
		}
	}

	// is a cell at specified adr empty
	static boolean isEmpty(int[] celAdr) {
		if (board[celAdr[0]][celAdr[1]][celAdr[2]] == 0) {
			return true;
		} else {
			return false;
		}
	}

	// place a move on the board by cell adr array
	static void move(int[] celAdr, int val) {
		move(celAdr[0], celAdr[1], celAdr[2], val);
	}

	// place a move on the board explicit coordinates
	static void move(int lev, int row, int col, int val) {
		board[lev][row][col] = val;
	}

	// clear board to a value
	static void setAll(int val) {
		for (int lev = 0; lev < 4; lev++) {
			for (int row = 0; row < 4; row++) {
				for (int col = 0; col < 4; col++) {
					move(lev, row, col, val);
				}
			}
		}
	}

	// are two cell addresses the same
	static boolean isEqual(int[] a, int[] b) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}

	// fine empty cell in a line
	static int[] findEmptyCel(int[][] line) {
		for (int i = 0; i < 4; i++) {
			if (isEmpty(line[i]))
				return line[i];
		}
		return null;
	}

	// find common empty cell to two lines
	static int[] findComMtCel(int[][] line1, int[][] line2) {
		for (int i = 0; i < line1.length; i++) {
			for (int j = 0; j < line1.length; j++) {
				if (isEqual(line1[i], line2[j]) && isEmpty(line1[i]) && isEmpty(line2[j])) {
					return line1[i];
				}
			}
		}
		return null;
	}

	static void compLineSums() {
		for (int i = 0; i < sums.length; i++) {
			sums[i] = 0;
			for (int j = 0; j < 4; j++) {
				sums[i] += board[lines[i][j][0]][lines[i][j][1]][lines[i][j][2]];
			}
		}
	}

	// find line with a specific sum
	static int findLineSum(int sum) {
		for (int i = 0; i < sums.length; i++) {
			if (sums[i] == sum)
				return i; // line i has the sum
		}
		return -1; // no such sum found
	}

	// main routine for testing
	public static void main(String[] args) {

		// testing simple display of board
		printBoard();

		for (int line = 0; line < 76; line++) {
			setAll(0);
			for (int cell = 0; cell < 4; cell++) {
				move(lines[line][cell], 1);
			}
			System.out.println("line number " + line);
			printBoard();
		}
	}

}
