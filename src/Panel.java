/* Copyright 2012 David Pearson.
 * BSD License
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

/**
 * The actual gameplay view, and associated AI methods.
 *
 * @author David Pearson
 */
public class Panel extends JPanel {
	private Graphics graph;
	private BufferedImage image;

	private int[][] board;
	private Polygon[][] squares;

	private final Color[] colors={Color.GRAY, Color.WHITE, Color.BLACK};

	private int turn=1;
	private int colour=1;
	private AI ai;

	/**
	 * Default constructor for the view.
	 */
	public Panel() {
		board=new int[7][7];

		squares=new Polygon[7][7];

		String[] opts={"Black", "White"};
		colour=JOptionPane.showOptionDialog(null, "Which colour would you like to play as?", "Choose Colour", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, opts, opts[0]);

		int opp=1;
		if (colour!=JOptionPane.CLOSED_OPTION) {
			opp=colour+1;
		} else {
			colour=2;
		}

		ai=new MCAI(opp);

		Object[] options={25, 50, 75, 100, 150};
		Integer level=(Integer)JOptionPane.showInputDialog(null, "Choose a difficulty level:", "Choose Difficulty", JOptionPane.PLAIN_MESSAGE, null, options, 75);

		if (level!=null) {
			((MCAI)ai).diffLevel=level.intValue();
		}

		image=new BufferedImage(400, 500, BufferedImage.TYPE_INT_RGB);
		graph=image.getGraphics();
		graph.setColor(new Color(210, 180, 140));
		graph.fillRect(0, 0, 400, 500);

		drawBoard();

		if (turn==ai.getPlayerCode()) {
			Location loc=ai.getPlayLocation(board, new Location(-1, -1));
			playAt(loc.x, loc.y);
		}

		addMouseListener(new Mouse());
	}

	/**
	 * Redraws the board as represented internally and forces a repaint
	 * 	to occur immediately.
	 */
	private void drawBoard() {
		graph.setColor(new Color(210, 180, 140));
		graph.fillRect(0, 0, 400, 500);

		for (int y=0; y<7; y++) {
			for (int x=0; x<7; x++) {
				graph.setColor(getColor(x, y));
				squares[y][x]=drawHex(25.0, x*42+70, y*47+178-(23*x));
				graph.fillPolygon(squares[y][x]);
			}
		}

		paintImmediately(getBounds());
	}
	
	/**
	 * Paints the view.
	 * @param  g  the Graphics instance to paint to.
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}

	/**
	 * Creates a regular hexagon for drawing.
	 *
	 * @param rad The side length of the hexagon
	 * @param centerX The x coordinate of the center point
	 * @param centerY The y coordinate of the center point
	 *
	 * @return A hexagon-shaped Polygon
	 */
	private Polygon drawHex(double rad, double centerX, double centerY) {
		Polygon p=new Polygon();

		double arc=(Math.PI*2)/6;

		for (int i=0; i<=6; i++) {
			p.addPoint((int)Math.round(centerX+rad*Math.cos(arc*i)), (int)Math.round(centerY+rad*Math.sin(arc*i)));
		}

		return p;
	}

	/**
	 * Gets the color of the hexagon at a given point.
	 *
	 * @param x The x coordinate of the hexagon
	 * @param y The y coordinate of the hexagon
	 *
	 * @return The color of the hexagon at (x, y)
	 */
	private Color getColor(int x, int y) {
		return colors[board[y][x]];
	}

	/**
	 * Places a piece for the current player at a given location on the board.
	 *
	 * @param x The x coordinate of the play location
	 * @param y The y coordinate of the play location
	 */
	private void playAt(int x, int y) {
		if (!isLegalPlay(x, y)) {
			JOptionPane.showMessageDialog(null, "You can't make that move!", "Invalid move!", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		board[y][x]=turn;
		drawBoard();

		if (((MCAI)ai).calcVal(board)>Math.pow(board.length, 2)) {
			JOptionPane.showMessageDialog(null, "The computer won. You didn't.", "Victory!", JOptionPane.PLAIN_MESSAGE);
			turn=-1;
			return;
		} else if (new MCAI(colour).calcVal(board)>Math.pow(board.length, 2)) {
			JOptionPane.showMessageDialog(null, "You won.", "Victory!", JOptionPane.PLAIN_MESSAGE);
			turn=-1;
			return;
		}

		if (turn==2) {
			turn--;
			drawBoard();
		} else {
			turn++;
			drawBoard();
		}

		if (turn==ai.getPlayerCode()) {
			Location loc=ai.getPlayLocation(board, new Location(x, y));
			playAt(loc.x, loc.y);
		}
	}

	/**
	 * Checks if a player can legally play at the given location.
	 *
	 * @param x The x coordinate of the play location
	 * @param y The y coordinate of the play location
	 *
	 * @return true if the location is empty
	 */
	private boolean isLegalPlay(int x, int y) {
		return board[y][x]==0;
	}

	/**
	 * Handles mouse events in the panel.
	 */
	private class Mouse extends MouseAdapter {
		/**
		 * Handles a left click event and makes a play if necessary.
		 *
		 * @param e A MouseEvent to process.
		 */
		public void mousePressed(MouseEvent e) {
			int eX=e.getX();
			int eY=e.getY();

			if (turn==ai.getPlayerCode() && turn>0) {
				return;
			}

			for (int y=0; y<squares.length; y++) {
				for (int x=0; x<squares[y].length; x++) {
					if (squares[y][x].contains(eX, eY)) {
						playAt(x, y);
					}
				}
			}
		}
	}
}