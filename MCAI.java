import java.util.ArrayList;

public class MCAI extends AI {
	private int aiplayer=1;
	private int minLen=49;
	private Location lastPlayed;

	public MCAI() {}
	public MCAI(int player) {
		aiplayer=player;
	}

	public int getPlayerCode() {
		return aiplayer;
	}

	private int calcN(int[][] board, int player, Location l, ArrayList<Location> visited, int count) {
		if (count<minLen && ((player==1 && l.y==6) || (player==2 && l.x==6))) {
			minLen=count;

			return count;
		} else if ((player==1 && l.y==6) || (player==2 && l.x==6)) {
			return count;
		}

		if (player==1 && count+(6-l.y)>=minLen) {
			return 999;
		} else if (player==2 && count+(6-l.x)>=minLen) {
			return 999;
		}

		ArrayList<Location> adj=l.getAdjacentLocations();
		ArrayList<Bridge> bridges=l.getBridges();

		ArrayList<Location> v=Utils.ALCopy(visited);

		v.add(new Location(l.x, l.y));

		int min=999;

		for (int i=0; i<bridges.size(); i++) {
			Bridge b=bridges.get(i);

			boolean canUseBridge=board[b.mids.get(0).y][b.mids.get(0).x]==0 && board[b.mids.get(1).y][b.mids.get(1).x]==0;
			if (canUseBridge && !Utils.ALContains(v, b.l1) && (board[b.l1.y][b.l1.x]==player || board[b.l1.y][b.l1.x]==0) /*&& (board[b.l2.y][b.l2.x]==player || board[b.l2.y][b.l2.x]==0)*/) {
				int val=calcN(board, player, b.l1, v, count);

				if (val<min) {
					min=val;
				}
			}
		}

		for (int i=0; i<adj.size(); i++) {
			Location loc=adj.get(i);

			if (!Utils.ALContains(v, loc) && (board[loc.y][loc.x]==player || board[loc.y][loc.x]==0)) {
				int val=999;

				if (board[loc.y][loc.x]==player) {
					val=calcN(board, player, loc, v, count);
				} else {
					val=calcN(board, player, loc, v, count+1);
				}

				if (val<min) {
					min=val;
				}
			}
		}

		return min;
	}

	private double calcVal(int[][] board) {
		int opp=1;
		if (aiplayer==1) {
			opp=2;
		}

		minLen=49;
		double maxno=999;
		double minnp=999;

		for (int i=0; i<board.length; i++) {
			if (board[i][0]!=opp) {
				int initCountP=1;
				int initCountO=1;
				if (board[i][0]==aiplayer) {
					initCountP=0;
				}
				if (board[i][0]==opp) {
					initCountO=0;
				}

				Location pLoc, oLoc;
				if (aiplayer==1) {
					pLoc=new Location(i, 0);
					oLoc=new Location(0, i);
				} else {
					pLoc=new Location(0, i);
					oLoc=new Location(i, 0);
				}

				double no=(double)calcN(board, opp, oLoc, new ArrayList<Location>(), initCountO);
				minLen=49;

				double np=(double)calcN(board, aiplayer, pLoc, new ArrayList<Location>(), initCountP);
				minLen=49;

				if (no<maxno) {
					maxno=no;
				}

				if (np<minnp) {
					minnp=np;
				}
			}
		}

		return maxno;
	}

	private boolean playRandomGame(int[][] board) {
		int[][] b=Board.BoardCopy(board);

		int currPlayer=1;
		if (aiplayer==1) {
			currPlayer=2;
		}

		while (Board.hasEmpty(b)) {
			int x=(int)(Math.random()*board.length);
			int y=(int)(Math.random()*board.length);

			while (b[y][x]!=0) {
				x=(int)(Math.random()*board.length);
				y=(int)(Math.random()*board.length);
			}

			b[y][x]=currPlayer;

			if (currPlayer==2) {
				currPlayer--;
			} else {
				currPlayer++;
			}
		}

		return calcVal(b)>Math.pow(board.length, 2);
	}

	private double calcWinPercent(int[][] board) {
		int winCount=0;
		for (int i=0; i<100; i++) {
			if (playRandomGame(board)) {
				winCount++;
			}
		}

		return ((double)winCount)/100.0;
	}

	private ArrayList<TreeNode> buildTree(TreeNode parent, int[][] board, int depth) {
		ArrayList<TreeNode> nodes=new ArrayList<TreeNode>();

		for (int y=0; y<board.length; y++) {
			for (int x=0; x<board[y].length; x++) {
				if (board[y][x]==0) {
					TreeNode node=new TreeNode();
					node.parent=parent;
					node.board=Board.BoardCopy(board);
					node.board[y][x]=aiplayer;
					node.playLocation=new Location(x, y);

					if (depth>1) {
						buildTree(node, node.board, depth-1);
					}
					nodes.add(node);
				}
			}
		}

		if (parent!=null) {
			parent.children=nodes;
		}

		return nodes;
	}

	public Location getPlayLocation(int[][] board, Location last, int depth) {
		long t=System.currentTimeMillis();

		// If a bridge has had a piece played in it, play so as to hold it.
		lastPlayed=last;
		ArrayList<TreeNode> tree=buildTree(null, board, 1);

		double best=-9999;
		Location bestLoc=tree.get(0).playLocation;
		for (int i=0; i<tree.size(); i++) {
			double score=calcWinPercent(tree.get(i).board);

			if (score>best) {
				best=score;
				bestLoc=tree.get(i).playLocation;
			}
		}
		System.out.println(System.currentTimeMillis()-t);
		return bestLoc;
	}
}