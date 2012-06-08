import java.util.ArrayList;

public class Location {
	public int x;
	public int y;

	public Location(int x1, int y1) {
		x=x1;
		y=y1;
	}

	public ArrayList<Location> getAdjacentLocations() {
		ArrayList<Location> l=new ArrayList<Location>();

		if (x>0) {
			l.add(new Location(x-1, y));
		}

		if (x<6) {
			l.add(new Location(x+1, y));
		}

		if (y>0) {
			l.add(new Location(x, y-1));
		}

		if (y<6) {
			l.add(new Location(x, y+1));
		}

		if (x>0 && y>0) {
			l.add(new Location(x-1, y-1));
		}

		if (x<6 && y<6) {
			l.add(new Location(x+1, y+1));
		}

		return l;
	}

	public ArrayList<Bridge> getBridges() {
		ArrayList<Bridge> bridges=new ArrayList<Bridge>();

		if (x+2<7 && y+1<7 && x-2>0 && y-1>0) {
			Bridge b=new Bridge();
			b.l1=new Location(x+2, y+1);
			b.l2=new Location(x-2, y-1);

			ArrayList<Location> midLocs=new ArrayList<Location>();
			midLocs.add(new Location(x+1, y));
			midLocs.add(new Location(x+1, y+1));
			midLocs.add(new Location(x-1, y));
			midLocs.add(new Location(x-1, y-1));

			b.mids=midLocs;

			b.dir=2;

			bridges.add(b);
		}

		if (x+1<7 && y-1>0 && x-1>0 && y+1<7) {
			Bridge b=new Bridge();
			b.l1=new Location(x+1, y-1);
			b.l2=new Location(x-1, y+1);

			ArrayList<Location> midLocs=new ArrayList<Location>();
			midLocs.add(new Location(x+1, y));
			midLocs.add(new Location(x, y-1));
			midLocs.add(new Location(x-1, y));
			midLocs.add(new Location(x, y+1));

			b.mids=midLocs;

			b.dir=1;

			bridges.add(b);
		}

		return bridges;
	}

	public int distanceFrom(Location l) {
		return (int)Math.sqrt(Math.pow(l.x-x, 2)+Math.pow(l.y-y, 2));
	}

	public boolean equals(Location l) {
		return (x==l.x && y==l.y);
	}

	public String toString() {
		return "("+x+", "+y+")";
	}
}
