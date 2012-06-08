import java.util.ArrayList;

public class DecentAI extends AI {
	private int minLen=49;
	private ArrayList<Location> minPath;
	private int aiplayer=1;

	public DecentAI() {}
	public DecentAI(int player) {
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

			ArrayList<Location> v=ALCopy(visited);

			v.add(new Location(l.x, l.y));

			minPath=v;

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

	public Location getPlayLocation(int[][] board, Location last, int depth) {
		int opp=1;
		if (aiplayer==1) {
			opp=2;
		}

		int numPieces=0;
		for (int y=0; y<board.length; y++) {
			for (int x=0; x<board[y].length; x++) {
				if (board[y][x]!=0) {
					numPieces++;
				}
			}
		}

		if (numPieces==0) {
			return new Location((int)(Math.random()*7), (int)(Math.random()*7));
		}

		minLen=49;

		Location minLoc=new Location(0, 0);
		double min=-1;

		for (int y=0; y<board.length; y++) {
			for (int x=0; x<board[y].length; x++) {
				if (board[y][x]==0) {
					board[y][x]=aiplayer;
					Location loc=new Location(x, y);

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

					minLen=49;

					if ((aiplayer==1 && ((x-1==last.x && y==last.y) || (x+1==last.x && y==last.y))) || (aiplayer==2 && ((x==last.x && y-1==last.y) || (x==last.x && y+1==last.y)))) {
						maxno+=0.2;
						minnp-=0.2;
					}

					/*ArrayList<Bridge> bridges=last.getBridges();

					for (int i=0; i<bridges.size(); i++) {
						Bridge b=bridges.get(i);
						

						boolean canUseBridge=board[b.mids.get(0).y][b.mids.get(0).x]==0 && board[b.mids.get(1).y][b.mids.get(1).x]==0;

						if (canUseBridge && x==b.l1.x && y==b.l2.y && b.dir==opp) {
							maxno+=1;
						}
					}*/

					double nr=maxno/minnp;
					if (maxno>min) {
						min=maxno;
						minLoc=loc;
					}

					board[y][x]=0;
				}
			}
		}

		/*if (depth<1) {
			System.out.println("Done with run "+(depth+1));
			long t=System.currentTimeMillis();
			board[minLoc.y][minLoc.x]=aiplayer;
			Location playLoc=new DecentAI(opp).getPlayLocation(board, minLoc, depth+1);
			board[minLoc.y][minLoc.x]=0;

			System.out.println(System.currentTimeMillis()-t);

			return playLoc;
		}*/

		return minLoc;
	}
}