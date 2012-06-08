public class Board {
	public static int[][] BoardCopy(int[][] board) {
		int[][] nBoard=new int[board.length][board.length];

		for (int y=0; y<board.length; y++) {
			for (int x=0; x<board[y].length; x++) {
				nBoard[y][x]=board[y][x];
			}
		}

		return nBoard;
	}

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