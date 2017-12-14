package ca.mindmagic.game.map.grid;

import ca.mindmagic.game.map.grid.shape.Hexagon;
import ca.mindmagic.game.map.grid.shape.Shape;
import java.util.LinkedHashSet;
import java.util.Set;


public class HexGrid extends Grid{

	public enum Direction{
		A(-1,0), B(0,1), C(1,1), D(1,0), E(0,-1), F(-1,-1);

		private final int row, col;

		Direction(int row, int col){
			this.row = row;
			this.col = col;
		}
		Koordinate move(Koordinate start){
			return new Koordinate(start.row + row, start.column + col);
		}

	}

	Shape hex;

	//static double sinL = Math.sin(Math.toRadians(60));

	public HexGrid(){
		hex = new Hexagon();
	}

	public HexGrid(int hexSize){
		hex = new Hexagon(hexSize);
	}

	@Override public Shape getShape() {
		return hex;
	}

	@Override
	public Set<Koordinate> neighborsOf(Koordinate hex) {
		Set<Koordinate> neighbors = new LinkedHashSet<>();
		neighbors.add(new Koordinate(hex.row-1,hex.column));
		neighbors.add(new Koordinate(hex.row, hex.column+1));
		neighbors.add(new Koordinate(hex.row+1, hex.column+1));
		neighbors.add(new Koordinate(hex.row+1, hex.column));
		neighbors.add(new Koordinate(hex.row, hex.column-1));
		neighbors.add(new Koordinate(hex.row-1, hex.column-1));
		return neighbors;
	}


	@Override
	public int range(Koordinate source, Koordinate target) {
		return range(source.row, source.column, target.row, target.column);
	}


	private static int range(int row1, int col1, int row2, int col2){
		int deltaRow = row1 - row2;
		int deltaCol = col1 - col2;
		return Math.max(Math.max(Math.abs(deltaRow), Math.abs(deltaCol)), Math.abs(deltaRow - deltaCol));
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
	//	return (int)(c.column + Math.ceil(c.row/2.0));
	//}

	//@Override
	//public Point getCenterPoint(int row, int col) {
	//	return getCenterPoint(row, col, hex.lengthOfSides());
	//}
  //
	//public static Point getCenterPoint(int row, int col, int size){
	//	int hexWidth = 2*(int)(size*Hex.HEIGHT);
	//	int oddRowOffset = row%2 * hexWidth/2;
	//	int centerPointX = col * hexWidth + oddRowOffset;
	//	int centerPointY = (int)(row * 1.5 * size);
	//	return new Point(centerPointX, centerPointY);
	//}

}
