/*
 * Copyright 2012 David Pearson. BSD License.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

/**
 * The actual gameplay view, and associated AI methods.
 *
 * @author David
 */
public class Panel extends JPanel {
	private Graphics graph;
	private BufferedImage image;

	private int[][] board; 
	private Polygon[][] squares;

	private final Color[] colors={Color.GRAY, Color.WHITE, Color.BLACK};

	private int turn=1;
	private AI ai;

	/**
	 * Default constructor for the view.
	 */
	public Panel() {
		board=new int[7][7];

		squares=new Polygon[7][7];

		ai=new DecentAI();

		image=new BufferedImage(400, 500, BufferedImage.TYPE_INT_RGB);
		graph=image.getGraphics();
		graph.setColor(new Color(210, 180, 140));
		graph.fillRect(0, 0, 400, 500);

		Location loc=ai.getPlayLocation(board, new Location(-1, -1));
		drawBoard();
		playAt(loc.x, loc.y);

		addMouseListener(new Mouse());
	}

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

		repaint();
	}

	/**
	 * Paints the view.
	 * @param  g  the Graphics instance to paint to.
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}

	private Polygon drawHex(double rad, double centerX, double centerY) {
		Polygon p=new Polygon();

		double arc=(Math.PI*2)/6;

		for (int i=0; i<=6; i++) {
			p.addPoint((int)Math.round(centerX+rad*Math.cos(arc*i)), (int)Math.round(centerY+rad*Math.sin(arc*i)));
		}

		return p;
	}

	private Color getColor(int x, int y) {
		return colors[board[y][x]];
	}

	private boolean look(int player, int x, int y, int prevX, int prevY) {
		boolean connects=false;

		if ((player==1 && y==6) || (player==2 && x==6)) {
			return true;
		}

		if (x>0) {
			connects=connects || ((x-1!=prevX || y!=prevY) && board[y][x-1]==player && look(player, x-1, y, x, y));
		}

		if (x<6) {
			connects=connects || ((x+1!=prevX || y!=prevY) && board[y][x+1]==player && look(player, x+1, y, x, y));
		}

		if (y>0) {
			connects=connects || ((x!=prevX || y-1!=prevY) && board[y-1][x]==player && look(player, x, y-1, x, y));
		}

		if (y<6) {
			connects=connects || ((x!=prevX || y+1!=prevY) && board[y+1][x]==player && look(player, x, y+1, x, y));
		}

		if (x>0 && y>0) {
			connects=connects || ((x-1!=prevX || y-1!=prevY) && board[y-1][x-1]==player && look(player, x-1, y-1, x, y));
		}

		if (x<6 && y<6) {
			connects=connects || ((x+1!=prevX || y+1!=prevY) && board[y+1][x+1]==player && look(player, x+1, y+1, x, y));
		}

		return connects;
	}

	private boolean checkWin(int player) {
		if (player==1) {
			boolean foundTop=false;
			boolean foundBottom=false;

			for (int x=0; x<board[0].length; x++) {
				if (board[0][x]==player) {
					foundTop=true;
				}
			}

			for (int x=0; x<board[6].length; x++) {
				if (board[6][x]==player) {
					foundBottom=true;
				}
			}

			if (!foundTop || !foundBottom) {
				return false;
			}

			for (int x=0; x<board[0].length; x++) {
				if (board[0][x]==player) {
					return look(player, x, 0, -1, -1);
				}
			}
		} else if (player==2) {
			boolean foundLeft=false;
			boolean foundRight=false;

			for (int y=0; y<board.length; y++) {
				if (board[y][0]==player) {
					foundLeft=true;
				}
			}

			for (int y=0; y<board.length; y++) {
				if (board[y][6]==player) {
					foundRight=true;
				}
			}

			if (!foundLeft || !foundRight) {
				return false;
			}

			for (int y=0; y<board.length; y++) {
				if (board[y][0]==player) {
					return look(player, 0, y, -1, -1);
				}
			}
		}

		return false;
	}

	private void playAt(int x, int y) {
		if (!isLegalPlay(x, y)) {
			System.out.println("Not valid!");
			return;
		}

		board[y][x]=turn;
		drawBoard();

		if (checkWin(turn)) {
			System.out.println("Player "+turn+" wins!");
			return;
		}

		if (turn==2) {
			turn--;

			Location loc=ai.getPlayLocation(board, new Location(x, y));
			drawBoard();
			playAt(loc.x, loc.y);
		} else {
			turn++;
			drawBoard();
		}
	}

	private boolean isLegalPlay(int x, int y) {
		return board[y][x]==0;
	}

	private class Mouse extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			int eX=e.getX();
			int eY=e.getY();

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
