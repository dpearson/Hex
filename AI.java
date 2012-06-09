/* Copyright 2012 David Pearson.
 * BSD License
 */

/**
 * An interface for AI players, to be implmented by various subclasses.
 *
 * @author David Pearson
 */
public abstract class AI {
	public abstract int getPlayerCode();
	public abstract Location getPlayLocation(int[][] board, Location lastPlay, int depth);
}
