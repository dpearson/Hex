import java.util.ArrayList;

public class BestAI extends AI {
	private int aiplayer=1;
	private int minLen=49;
	private Location lastPlayed;

	public BestAI() {}
	public BestAI(int player) {
		aiplayer=player;
	}

	public int getPlayerCode() {
		return aiplayer;
	}

	private boolean ALContains(ArrayList<Location> visited, Location l) {
		for (int i=0; i<visited.size(); i++) {
			if (visited.get(i).equals(l)) {
				return true;
			}
		}

		return false;
	}

	private ArrayList<Location> ALCopy(ArrayList<Location> one) {
		ArrayList<Location> two=new ArrayList<Location>();

		for (int i=0; i<one.size(); i++) {
			two.add(new Location(one.get(i).x, one.get(i).y));
		}

		return two;
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

		ArrayList<Location> v=ALCopy(visited);

		v.add(new Location(l.x, l.y));

		int min=999;

		for (int i=0; i<bridges.size(); i++) {
			Bridge b=bridges.get(i);

			boolean canUseBridge=board[b.mids.get(0).y][b.mids.get(0).x]==0 && board[b.mids.get(1).y][b.mids.get(1).x]==0;
			if (canUseBridge && !ALContains(v, b.l1) && (board[b.l1.y][b.l1.x]==player || board[b.l1.y][b.l1.x]==0) /*&& (board[b.l2.y][b.l2.x]==player || board[b.l2.y][b.l2.x]==0)*/) {
				int val=calcN(board, player, b.l1, v, count);

				if (val<min) {
					min=val;
				}
			}
		}

		for (int i=0; i<adj.size(); i++) {
			Location loc=adj.get(i);

			if (!ALContains(v, loc) && (board[loc.y][loc.x]==player || board[loc.y][loc.x]==0)) {
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

	private double calcVal(int[][] board, Location loc) {
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

		if ((aiplayer==1 && ((loc.x-1==lastPlayed.x && loc.y==lastPlayed.y) || (loc.x+1==lastPlayed.x && loc.y==lastPlayed.y))) || (aiplayer==2 && ((loc.x==lastPlayed.x && loc.y-1==lastPlayed.y) || (loc.x==lastPlayed.x && loc.y+1==lastPlayed.y)))) {
			maxno+=0.2;
			minnp-=0.2;
		}

		return maxno;
	}

	int[][] BoardCopy(int[][] board) {
		int[][] nBoard=new int[board.length][board.length];

		for (int y=0; y<board.length; y++) {
			for (int x=0; x<board[y].length; x++) {
				nBoard[y][x]=board[y][x];
			}
		}

		return nBoard;
	}

	private ArrayList<TreeNode> buildTree(TreeNode parent, int[][] board, int depth) {
		ArrayList<TreeNode> nodes=new ArrayList<TreeNode>();

		for (int y=0; y<board.length; y++) {
			for (int x=0; x<board[y].length; x++) {
				if (board[y][x]==0) {
					TreeNode node=new TreeNode();
					node.parent=parent;
					node.board=BoardCopy(board);
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

	private ArrayList<TreeNode> sortTree(ArrayList<TreeNode> old, Location around) {
		ArrayList<TreeNode> nodes=new ArrayList<TreeNode>();

		for (int i=0; i<old.size(); i++) {
			int j=0;
			TreeNode node=old.get(i);
			//while (j<nodes.size() && nodes.get(j).playLocation.distanceFrom(around)<node.playLocation.distanceFrom(around)) {
			//	j++;
			//}

			nodes.add((int)(Math.random()*nodes.size()), node);
		}

		return nodes;
	}

	private double negascout(TreeNode node, int depth, double alpha, double beta) {
		if (node.children==null || depth==0) {
			return node.score;
		}

		double b=beta;
		double score=0.0;
		for (int i=0; i<node.children.size(); i++) {
			score=-negascout(node.children.get(i), depth-1, -b, -alpha);
			if (alpha<score && score<beta) {
				score=-negascout(node.children.get(i), depth-1, -beta, -alpha);
			}

			score=Math.max(alpha, score);
			if (alpha>=beta) {
				return alpha;
			}

			b=alpha+1;
		}

		return alpha;
	}

	private double negamax(TreeNode node, int depth, double alpha, double beta, int mult) {
		if (node.children==null || depth==0) {
			return calcVal(node.board, node.playLocation)*mult;
		}

		for (int i=0; i<node.children.size(); i++) {
			double val=-1*negamax(node.children.get(i), depth-1, beta*-1, alpha*-1, mult*-1);
			if (val>=beta) {
				return val;
			}
			if (val>=alpha) {
				alpha=val;
			}
		}

		return alpha;
	}

	public Location getPlayLocation(int[][] board, Location last, int depth) {
		long t=System.currentTimeMillis();
		lastPlayed=last;
		ArrayList<TreeNode> tree=buildTree(null, board, 3);
		tree=sortTree(tree, last);

		double best=-9999;
		Location bestLoc=tree.get(0).playLocation;
		for (int i=0; i<tree.size(); i++) {
			double score=negamax(tree.get(i), 3, -9999, 9999, 1);

			if (score>best) {
				best=score;
				bestLoc=tree.get(i).playLocation;
			}
		}
		System.out.println(System.currentTimeMillis()-t);
		return bestLoc;
	}
}