/* Copyright 2012 David Pearson.
 * BSD License
 */

/**
 * Provides various methods for manipulating a board.
 *
 * @author David Pearson
 */
public class Board {
	/**
	 * Performs a deep copy of a board supplied as an argument and
	 * 	returns the copy.
	 *
	 * @param board The board to copy
	 *
	 * @return The copied board
	 */
	public static int[][] BoardCopy(int[][] board) {
		int[][] nBoard=new int[board.length][board.length];

		for (int y=0; y<board.length; y++) {
			for (int x=0; x<board[y].length; x++) {
				nBoard[y][x]=board[y][x];
			}
		}

		return nBoard;
	}

	/**
	 * Checks a board for empty spaces.
	 *
	 * @param board The board to check
	 *
	 * @return The existance of an empty space
	 */
	public static boolean hasEmpty(int[][] board) {
		for (int y=0; y<board.length; y++) {
			for (int x=0; x<board[y].length; x++) {
				if (board[y][x]==0) {
					return true;
				}
			}
		}

		return false;
	}
}