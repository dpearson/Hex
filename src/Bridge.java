/* Copyright 2012 David Pearson.
 * BSD License.
 */

import java.util.ArrayList;

/**
 * A representation of a bridge on a Hex board.
 *
 * @author David Pearson
 */
public class Bridge {
	public Location l1;
	public Location l2;
	public ArrayList<Location> mids;
	int dir;
}