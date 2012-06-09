/* Copyright 2012 David Pearson.
 * BSD License.
 */

import java.util.ArrayList;

/**
 * Various utility functions that have no better home
 * 	in a language like Java.
 *
 * @author David Pearson
 */
public class Utils {
	/**
	 * Checks if an ArrayList contains the given element.
	 *
	 * @param visited The ArrayList of Locations to check
	 * @param l The Location to check for
	 *
	 * @returns the existance of the Location in the ArrayList
	 */
	public static boolean ALContains(ArrayList<Location> visited, Location l) {
		for (int i=0; i<visited.size(); i++) {
			if (visited.get(i).equals(l)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Performs a deep copy of an ArrayList of Locations.
	 *
	 * @param one The ArrayList of Locations to copy
	 *
	 * @return A copy of the given ArrayList
	 */
	public static ArrayList<Location> ALCopy(ArrayList<Location> one) {
		ArrayList<Location> two=new ArrayList<Location>();

		for (int i=0; i<one.size(); i++) {
			two.add(new Location(one.get(i).x, one.get(i).y));
		}

		return two;
	}
}