package ca.mindmagic.game.map.grid;

import java.util.HashSet;
import java.util.Set;

import javafx.geometry.Point2D;

public class SquareGrid implements Grid {

	int sideLength;

	protected boolean diagonalIsAdjacent = false;


	public SquareGrid(int sideLength){
		this.sideLength = sideLength;
	}

	public Double[] verticesOf(Coordinate c) {
		return verticesOf(c.getRow(), c.getCol(), sideLength);
	}

	@Override
	public Double[] verticesOf(int row, int col) {
		return verticesOf(row, col, sideLength);
	}

	@Override
	public Point2D centerPointOf(int x, int y) {
		return null;
	}  // TODO needs implementing

	public static Double[] verticesOf(int row, int col, int sideLength) {
		Point2D topLeft = topLeft(row, col, sideLength);
		Point2D topRight = topRight(row, col, sideLength);
		Point2D bottomLeft = bottomLeft(row, col, sideLength);
		Point2D bottomRight = bottomRight(row, col, sideLength);
		Double[] vertices = {topLeft.getX(), topLeft.getY(), topRight.getX(), topRight.getY(),
				bottomLeft.getX(), bottomLeft.getY(), bottomRight.getX(), bottomRight.getX()};
		return vertices;
	}

	public int getSideLength(){
		return sideLength;
	}

	public static Point2D centerPointOf(Coordinate c, int sideLength) {
		Point2D p = topLeft(c.getRow(), c.getCol(), sideLength);
		int halfOfSide = sideLength/2;
		return new Point2D(p.getX() + halfOfSide, p.getY() + halfOfSide);
	}

	@Override
	public Set<Coordinate> neighborsOf(Coordinate c) {
		return neighborsOf(c.getRow(), c.getCol());
	}

	@Override
	public Set<Coordinate> neighborsOf(int row, int col) {
		Set<Coordinate> neighbors = new HashSet<>();
		neighbors.add(new Coordinate(row + 1, col));
		neighbors.add(new Coordinate(row, col + 1));
		neighbors.add(new Coordinate(row - 1, col));
		neighbors.add(new Coordinate(row, col - 1));
		if (diagonalIsAdjacent){
			neighbors.add(new Coordinate(row + 1, col + 1));
			neighbors.add(new Coordinate(row - 1, col + 1));
			neighbors.add(new Coordinate(row + 1, col - 1));
			neighbors.add(new Coordinate(row - 1, col - 1));
		}
		return neighbors;
	}

	@Override
	public int range(Coordinate c1, Coordinate c2) {
		int range = 0;
		range += Math.abs(c1.getCol() - c2.getCol());
		range += Math.abs(c1.getRow() - c2.getRow());
		return range;
	}

	private static Point2D bottomRight(int row, int col, int sideLength) {
		return new Point2D((row + 1) * sideLength, (col + 1) * sideLength);
  }

	private static Point2D bottomLeft(int row, int col, int sideLength) {
		return new Point2D(row * sideLength, (col + 1) * sideLength);
  }

	private static Point2D topRight(int row, int col, int sideLength) {
		return new Point2D((row + 1) * sideLength, col * sideLength);
  }

	private static Point2D topLeft(int row, int col, int sideLength) {
		return new Point2D(row * sideLength, col * sideLength);
  }

}
