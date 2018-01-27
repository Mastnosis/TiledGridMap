package ca.mindmagic.game.map.grid;

import ca.mindmagic.game.map.grid.pattern.Shape;
import ca.mindmagic.game.map.grid.pattern.Square;
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

	public Point[] getVertices(Coordinate c){
		return getVertices(c, sideLength);
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

	private static Point bottomRight(Coordinate c, int sidelength) {
		return new Point(c.getRow() +1*sidelength, c.getCol()+1*sidelength);
	}

	private static Point bottomLeft(Coordinate c, int sidelength) {
		return new Point(c.getRow() *sidelength, c.getCol()+1*sidelength);
	}

	private static Point topRight(Coordinate c, int sidelength) {
		return new Point(c.getRow() +1*sidelength, c.getCol()*sidelength);
	}

	private static Point topLeft(Coordinate c, int sidelength) {
		return new Point(c.getCol()*sidelength, c.getRow() *sidelength);
	}

	public static Point getCenterPoint(Coordinate c, int sidelength) {
		Point p = topLeft(c, sidelength);
		int halfOfSide = sidelength/2;
		return new Point(p.x+halfOfSide, p.y + halfOfSide);
	}

	@Override public Shape getShape() {
		return null;
	}

	@Override
	public Set<Coordinate> neighborsOf(Coordinate c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int range(Coordinate c1, Coordinate c2) {
		int range = 0;
		range += Math.abs(c1.getCol() - c2.getCol());
		range += Math.abs(c1.getRow() - c2.getRow());
		return range;
	}

}
