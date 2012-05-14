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

	public boolean equals(Location l) {
		return (x==l.x && y==l.y);
	}

	public String toString() {
		return "("+x+", "+y+")";
	}
}
