package ca.mindmagic.game.map.grid;

import ca.mindmagic.game.map.grid.pattern.Hexagon;
import java.awt.Point;
import java.util.LinkedHashSet;
import java.util.Set;


public class HexGrid implements Grid{

  private final int sideLength;

  public enum Direction{
		A(-1,0), B(0,1), C(1,1), D(1,0), E(0,-1), F(-1,-1);

		private final int row, col;

		Direction(int row, int col){
			this.row = row;
			this.col = col;
		}
		Coordinate move(Coordinate start){
			return new Coordinate(start.getRow() + row, start.getCol() + col);
		}

	}

	public HexGrid(){
    this(60);
  }

	public HexGrid(int sideLength){
	  this.sideLength = sideLength;
  }

	@Override
	public Set<Coordinate> neighborsOf(Coordinate hex) {
		Set<Coordinate> neighbors = new LinkedHashSet<>();
		neighbors.add(new Coordinate(hex.getRow() -1,hex.getCol()));
		neighbors.add(new Coordinate(hex.getRow(), hex.getCol()+1));
		neighbors.add(new Coordinate(hex.getRow() +1, hex.getCol()+1));
		neighbors.add(new Coordinate(hex.getRow() +1, hex.getCol()));
		neighbors.add(new Coordinate(hex.getRow(), hex.getCol()-1));
		neighbors.add(new Coordinate(hex.getRow() -1, hex.getCol()-1));
		return neighbors;
	}

	public Set<Coordinate> neighborsOf(int row, int col){
		Set<Coordinate> neighbors = new LinkedHashSet<>();
		neighbors.add(new Coordinate(row -1,col));
		neighbors.add(new Coordinate(row, col+1));
		neighbors.add(new Coordinate(row +1, col+1));
		neighbors.add(new Coordinate(row +1, col));
		neighbors.add(new Coordinate(row, col-1));
		neighbors.add(new Coordinate(row -1, col-1));
		return neighbors;
	}


	@Override
	public int range(Coordinate source, Coordinate target) {
		return range(source.getRow(), source.getCol(), target.getRow(), target.getCol());
	}

  @Override public Point[] getVertices(int row, int col) {
    return new Point[0];
  }

  @Override public Point getCenterPoint(int x, int y) {
    return getCenterPoint(x, y, sideLength);
  }

  /**
   * Convert from a square column format to an axial column format
   * 0 1 2		0 1 2
   *  0 1	2		 1 2 3
   * 0 1 2		1 2 3
   *
   * @return column number converted to axial value
   */
  //private int convertColToAxial(Koordinate c){
  //	return (int)(c.getCol() + Math.ceil(c.row/2.0));
  //}

  //@Override
  //public Point getCenterPoint(int row, int col) {
  //	return getCenterPoint(row, col, hex.lengthOfSides());
  //}
  //
  public static Point getCenterPoint(int row, int col, int size){
    int hexWidth = 2*(int)(size* Hexagon.HEIGHT);
    int oddRowOffset = row%2 * hexWidth/2;
    int centerPointX = col * hexWidth + oddRowOffset;
    int centerPointY = (int)(row * 1.5 * size);
    return new Point(centerPointX, centerPointY);
  }

  private static int range(int row1, int col1, int row2, int col2){
		int deltaRow = row1 - row2;
		int deltaCol = col1 - col2;
		return Math.max(Math.max(Math.abs(deltaRow), Math.abs(deltaCol)), Math.abs(deltaRow - deltaCol));
	}

}
