public class RandomAI extends AI {
	public Location getPlayLocation(int[][] board) {
		Location loc=new Location((int)(Math.random()*7), (int)(Math.random()*7));

		while (board[loc.y][loc.x]!=0) {
			loc=new Location((int)(Math.random()*7), (int)(Math.random()*7));
		}

		return loc;
	}
}