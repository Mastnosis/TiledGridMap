package ca.mindmagic.game.map.grid;

import ca.mindmagic.game.map.grid.pattern.Shape;
import ca.mindmagic.game.map.grid.pattern.Square;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class SquareGrid implements Grid {

	int sideLength;

	protected boolean diagonalIsAdjacent = false;


	public SquareGrid(int sideLength){
		this.sideLength = sideLength;
	}

	public Point[] getVertices(Coordinate c){
		return getVertices(c, sideLength);
	}

	@Override public Point[] getVertices(int row, int col) {
		return new Point[0];
	}

	@Override public Point getCenterPoint(int x, int y) {
		return null;
	}

	public static Point[] getVertices(Coordinate c, int sideLength) {
		Point[] points = new Point[4];
		points[0] = topLeft(c, sideLength);
		points[1] = topRight(c, sideLength);
		points[2] = bottomLeft(c, sideLength);
		points[3] = bottomRight(c, sideLength);
		
		return points;
	}

	public int getSideLength(){
		return sideLength;
	}

	public static Point getCenterPoint(Coordinate c, int sideLength) {
		Point p = topLeft(c, sideLength);
		int halfOfSide = sideLength/2;
		return new Point(p.x+halfOfSide, p.y + halfOfSide);
	}

	@Override
	public Set<Coordinate> neighborsOf(Coordinate c) {
		Set<Coordinate> neighbors = new HashSet<>();
		neighbors.add(new Coordinate(c.getRow()+1, c.getCol()));
		neighbors.add(new Coordinate(c.getRow(), c.getCol()+1));
		neighbors.add(new Coordinate(c.getRow()-1, c.getCol()));
		neighbors.add(new Coordinate(c.getRow(), c.getCol()-1));
		if (diagonalIsAdjacent){
			neighbors.add(new Coordinate(c.getRow()+1, c.getCol()+1));
			neighbors.add(new Coordinate(c.getRow()-1, c.getCol()+1));
			neighbors.add(new Coordinate(c.getRow()+1, c.getCol()-1));
			neighbors.add(new Coordinate(c.getRow()-1, c.getCol()-1));
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

  private static Point bottomRight(Coordinate c, int sideLength) {
    return new Point((c.getRow()+1)*sideLength, (c.getCol()+1)*sideLength);
  }

  private static Point bottomLeft(Coordinate c, int sideLength) {
    return new Point(c.getRow() *sideLength, (c.getCol()+1)*sideLength);
  }

  private static Point topRight(Coordinate c, int sideLength) {
    return new Point((c.getRow()+1)*sideLength, c.getCol()*sideLength);
  }

  private static Point topLeft(Coordinate c, int sideLength) {
    return new Point(c.getCol()*sideLength, c.getRow() *sideLength);
  }

}
