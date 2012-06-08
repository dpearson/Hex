public abstract class AI {
	public abstract int getPlayerCode();
	public abstract Location getPlayLocation(int[][] board, Location lastPlay, int depth);
}
