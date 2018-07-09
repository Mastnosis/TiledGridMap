package ca.mindmagic.game.map.grid.hex;

import ca.mindmagic.game.map.grid.Coordinate;
import ca.mindmagic.game.map.grid.Grid;
import ca.mindmagic.game.map.grid.pattern.Hexagon;
import java.util.LinkedHashSet;
import java.util.Set;

import javafx.geometry.Point2D;

public class HexGrid implements Grid {

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
		return neighborsOf(hex.getRow(), hex.getCol());
	}

	@Override
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

	@Override
	public Double[] verticesOf(int row, int col) {
		return verticesOf(row, col, sideLength);
	}

	public static Double[] verticesOf(int row, int col, int sideLength) {
		Double[] vertices = new Double[12];
		// TODO requires implementation
		return vertices;
  }

	@Override
	public Point2D centerPointOf(int x, int y) {
		return centerPointOf(x, y, sideLength);
  }


	public static Point2D centerPointOf(int row, int col, int sidelength) {
		int hexWidth = 2 * (int) (sidelength * Hexagon.HEIGHT);
    int oddRowOffset = row%2 * hexWidth/2;
    int centerPointX = col * hexWidth + oddRowOffset;
		int centerPointY = (int) (row * 1.5 * sidelength);
		return new Point2D(centerPointX, centerPointY);
  }

  private static int range(int row1, int col1, int row2, int col2){
		int deltaRow = row1 - row2;
		int deltaCol = col1 - col2;
		return Math.max(Math.max(Math.abs(deltaRow), Math.abs(deltaCol)), Math.abs(deltaRow - deltaCol));
	}

}
