import java.util.ArrayList;

public class Utils {
	public static boolean ALContains(ArrayList<Location> visited, Location l) {
		for (int i=0; i<visited.size(); i++) {
			if (visited.get(i).equals(l)) {
				return true;
			}
		}

		return false;
	}

	public static ArrayList<Location> ALCopy(ArrayList<Location> one) {
		ArrayList<Location> two=new ArrayList<Location>();

		for (int i=0; i<one.size(); i++) {
			two.add(new Location(one.get(i).x, one.get(i).y));
		}

		return two;
	}
}