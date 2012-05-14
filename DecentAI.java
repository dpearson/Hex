import java.util.ArrayList;

public class DecentAI extends AI {
	private int minLen=999;

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

	private int calcN(int[][] board, int player, Location l, ArrayList<Location> visited) {
		int numV=visited.size();

		if (numV<minLen && ((player==1 && numV>0 && visited.get(numV-1).y==6) || (player==2 && numV>0 && visited.get(numV-1).x==6))) {
			minLen=visited.size()+1;
			return 0;
		}

		ArrayList<Location> adj=l.getAdjacentLocations();
		ArrayList<Integer> n=new ArrayList<Integer>();

		ArrayList<Location> v=new ArrayList<Location>();
		for (int i=0; i<visited.size(); i++) {
			v.add(new Location(visited.get(i).x, visited.get(i).y));
		}
		v.add(new Location(l.x, l.y));

		if (player==1) {
			if (v.size()+(6-l.y)>minLen) {
				return 999;
			}
		} else if (player==2) {
			if (v.size()+(6-l.x)>minLen) {
				return 999;
			}
		}

		for (int i=0; i<adj.size(); i++) {
			Location loc=adj.get(i);

			if (!ALContains(v, loc) && (board[loc.y][loc.x]==0 || board[loc.y][loc.x]==player)) {
				int val=calcN(board, player, loc, v);

				if (numV==0 && board[l.y][l.x]==player) {
					val-=1;
				}

				if (board[loc.y][loc.x]!=player) {
					val+=1;
				}
				n.add(val);
			}
		}

		if (n.size()==0) {
			return 999;
		}

		int min=n.get(0);

		for (int i=1; i<n.size(); i++) {
			int curr=n.get(i);

			if (curr<min) {
				min=curr;
			}
		}

		return min;
	}

	public Location getPlayLocation(int[][] board, Location last) {
		Location minLoc=new Location((int)(Math.random()*7), (int)(Math.random()*7));
		double min=((double)calcN(board, 1, minLoc, new ArrayList<Location>()));
		minLen=999;
		min/=((double)calcN(board, 2, minLoc, new ArrayList<Location>()));

		while (board[minLoc.y][minLoc.x]!=0) {
			minLoc=new Location((int)(Math.random()*7), (int)(Math.random()*7));
			min=((double)calcN(board, 1, minLoc, new ArrayList<Location>()));
			minLen=999;
			min/=((double)calcN(board, 2, minLoc, new ArrayList<Location>()));
		}

		for (int y=0; y<board.length; y++) {
			for (int x=0; x<board[y].length; x++) {
				if (board[y][x]==0) {
					board[y][x]=1;
					Location loc=new Location(x, y);

					//double minnp=(double)calcN(board, 1, new Location(0, 0), new ArrayList<Location>());
					minLen=999;
					double maxno=(double)calcN(board, 2, new Location(0, 0), new ArrayList<Location>());
					minLen=999;

					/*for (int i=0; i<board.length; i++) {
						double np=(double)calcN(board, 1, new Location(i, 0), new ArrayList<Location>());

						if (np<minnp) {
							minnp=np;
						}
					}*/

					minLen=999;

					for (int i=0; i<board.length; i++) {
						double no=(double)calcN(board, 2, new Location(0, i), new ArrayList<Location>());

						if (no<maxno) {
							maxno=no;
						}
					}

					minLen=999;

					//double nr=minnp/maxno;

					if ((x-1==last.x && y==last.y) || (x+1==last.x && y==last.y)) {
						maxno+=0.2;
					}
					if (maxno>min) {
						min=maxno;
						minLoc=loc;
					}

					board[y][x]=0;
				}
			}
		}

		System.out.println(min);
		return minLoc;
	}
}
