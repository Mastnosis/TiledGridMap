package ca.mindmagic.game.map.grid;

import ca.mindmagic.game.map.grid.shape.Shape;
import ca.mindmagic.game.map.grid.shape.Square;
import java.awt.Point;
import java.util.Set;

public class SquareGrid extends Grid {

	int sideLength;
	Shape shape = new Square();
	
	protected boolean diagonalIsAdjacent = false;
  //
	//public SquareGrid(){
	//	this(DEFAULT_SIZE);
	//}

	public SquareGrid(int sideLength){
		this.sideLength = sideLength;
	}

	public Point[] getVertices(Koordinate c){
		return getVertices(c, sideLength);
	}
  //
	//@Override public Point[] getVertices(int x, int y) {
	//	return new Point[0];
	//}

	public static Point[] getVertices(Koordinate c, int sideLength) {
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

	private static Point bottomRight(Koordinate c, int sidelength) {
		return new Point(c.row+1*sidelength, c.column+1*sidelength);
	}

	private static Point bottomLeft(Koordinate c, int sidelength) {
		return new Point(c.row*sidelength, c.column+1*sidelength);
	}

	private static Point topRight(Koordinate c, int sidelength) {
		return new Point(c.row+1*sidelength, c.column*sidelength);
	}

	private static Point topLeft(Koordinate c, int sidelength) {
		return new Point(c.column*sidelength, c.row*sidelength);
	}

	public static Point getCenterPoint(Koordinate c, int sidelength) {
		Point p = topLeft(c, sidelength);
		int halfOfSide = sidelength/2;
		return new Point(p.x+halfOfSide, p.y + halfOfSide);
	}

	@Override public Shape getShape() {
		return null;
	}

	@Override
	public Set<Koordinate> neighborsOf(Koordinate c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int range(Koordinate c1, Koordinate c2) {
		int range = 0;
		range += Math.abs(c1.column - c2.column);
		range += Math.abs(c1.row - c2.row);
		return range;
	}

	//@Override
	//public Point getCenterPoint(Koordinate c) {
	//	return getCenterPoint(c, sideLength);
	//}
  //
	//@Override public Point getCenterPoint(int x, int y) {
	//	return null;
	//}
}
