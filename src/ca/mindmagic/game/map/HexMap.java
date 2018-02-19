package ca.mindmagic.game.map;

/**
 * 	 _   _   _
 *	/ \_/ \_/ \
 *  \_/ \_/ \_/
 *  / \_/ \_/ \
 *  \_/ \_/ \_/
 *  / \_/ \_/ \
 *
 *  rows and columns based on pointed orientation below or flat orientation as above
 *
 *   /\  /\  /\  /\  /\  /\   Y
 *  /  \/  \/  \/  \/  \/  \
 *  |   |   |   |   |   |   |
 *  |X0 | 1 | 2 |   |   |   | 0
 *  \  /\  /\  /\  /\  /\  /
 *   \/  \/  \/  \/  \/  \/
 *    |   |   |   |   |   |
 *    | 0 | 1 | 2 |   |   |   1
 *   /\  /\  /\  /\  /\  /\
 *  /  \/  \/  \/  \/  \/  \
 *  |   |   |   |   |   |   |
 *  | 0 | 1 | 2 |   |   |   | 2
 *  \  /\  /\  /\  /\  /\  /
 *   \/  \/  \/  \/  \/  \/
 *
 */

import ca.mindmagic.game.map.grid.Coordinate;
import ca.mindmagic.game.map.grid.Grid;
import ca.mindmagic.game.map.grid.HexGrid;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.IntBinaryOperator;
import java.util.function.ToDoubleBiFunction;
import java.util.stream.Collectors;

public class HexMap extends GridMap{

  private static final double H = Math.sqrt(0.75);

  final Orientation orientation;



  protected final boolean wrapWidth;
  protected final boolean wrapHeight;
  Map<Coordinate, Set<Coordinate>> locations;

  public HexMap(int height, int width) {
    this(height, width, Orientation.POINTED_TOP);
  }

  public HexMap(int height, int width, Orientation orientation){
    this(height, width, false, false, orientation);
  }

  public HexMap(int height, int width, boolean wrapWidth, boolean wrapHeight, Orientation orientation){
    super(height, width, new HexGrid());
    this.orientation = orientation;
    this.width = width;
    this.height = height;
    this.wrapWidth = wrapWidth;
    this.wrapHeight = wrapHeight;

    locations = new HashMap<>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Coordinate c = new Coordinate(i, j);
        locations.put(c, calcNeighborsOf(c));
      }
    }

  }

  public Set<Coordinate> neighborsOf(Coordinate mapLocation){
    if(locations.containsKey(mapLocation)){
      return locations.get(mapLocation);
    }
    return Collections.emptySet();
  }

  public Set<Coordinate> neighborsOf(int mapRow, int mapCol){
    return neighborsOf(new Coordinate(mapRow, mapCol));
  }

  @Deprecated
  private Set<Coordinate> calcNeighborsOf(Coordinate mapLocation){
    return calcNeighborsOf(mapLocation.getRow(), mapLocation.getCol());
  }

  @Deprecated
  private Set<Coordinate> calcNeighborsOf(int mapRow, int mapCol){
    Set<Coordinate> neighbors = grid.neighborsOf(orientation.gridCoordinateOf(mapRow, mapCol));
    return neighbors.stream()
        .map(this.orientation::mapCoordinateOf)
        .filter(this::ifMapContains)
        .collect(Collectors.toSet());
  }

  public Orientation getOrientation(){
    return orientation;
  }

  public int range(Coordinate source, Coordinate target){
    Coordinate gridSource = orientation.gridCoordinateOf(source);
    Coordinate gridTarget = orientation.gridCoordinateOf(target);
    return grid.range(gridSource, gridTarget);
  }

  @Override public Set<Coordinate> getLocations(){
    return locations.keySet();
  }

  public Coordinate coordinateOf(int row, int col){
    return orientation.gridCoordinateOf(row, col);
  }

  public boolean ifMapContains(Coordinate c){
    return ifMapContains(c.getRow(), c.getCol());
  }

  public boolean ifMapContains(int row, int col){
    if(row < 0 || row >= height){
      return false;
    }
    if(col < 0 || col >= width){
      return false;
    }
    return true;
}

  public Double[] verticesOf(Coordinate c){
    return orientation.verticesOf(c);
  }

  public Double[] verticesOf(int row, int col){
    return verticesOf(row, col, orientation);
  }

  public static Double[] verticesOf(int row, int col, Orientation orientation){
    return orientation.verticesOf(row, col);
  }

  public Double[] centerOf(Coordinate coordinate){
    return centerOf(coordinate.getRow(), coordinate.getCol());
  }

  public Double[] centerOf(int row, int col){
    return orientation.centerOf(row, col);
  }

  public enum Orientation{
    POINTED_TOP(
        (x,y) -> x,
        (x,y) -> y+(int)Math.ceil(x/2.0),
        (x,y) -> x,
        (x,y) -> y-(int)Math.ceil(x/2.0),
        (x,y) -> y*2*Math.sqrt(0.75) + x%2*Math.sqrt(0.75),
        (x,y) -> 1.5*x,
        new double[]{0.0, H, H, 0, -H, -H},
        new double[]{-1, -0.5, 0.5, 1, 0.5, -0.5}
    ),
    FLAT_TOP(
        (x,y) -> x+(int)Math.ceil(y/2.0),
        (x,y) -> y,
        (x,y) -> x-(int)Math.ceil(y/2.0),
        (x,y) -> y,
        (x,y) -> 1.5*y,
        (x,y) -> 2*Math.sqrt(0.75)*x + y%2*Math.sqrt(0.75),
        new double[]{-0.5, 0.5, 1, 0.5, -0.5, -1},
        new double[]{-H, -H, 0, H, H, 0}
    );

    IntBinaryOperator toGridRow;
    IntBinaryOperator toGridCol;

    IntBinaryOperator toMapRow;
    IntBinaryOperator toMapCol;

    ToDoubleBiFunction<Integer,Integer> findCenterX;
    ToDoubleBiFunction<Integer,Integer> findCenterY;

    double[] xValues;
    double[] yValues;

    Orientation(
        IntBinaryOperator toGridRow,
        IntBinaryOperator toGridCol,
        IntBinaryOperator toMapRow,
        IntBinaryOperator toMapCol,
        ToDoubleBiFunction<Integer,Integer> centerXOperator,
        ToDoubleBiFunction<Integer,Integer> centerYOperator,
        double[] xOfPoints,
        double[] yOfPoints
    ){
      this.toGridRow = toGridRow;
      this.toGridCol = toGridCol;
      this.toMapRow = toMapRow;
      this.toMapCol = toMapCol;
      findCenterX = centerXOperator;
      findCenterY = centerYOperator;
      this.xValues = xOfPoints;
      this.yValues = yOfPoints;
    }

    Coordinate gridCoordinateOf(Coordinate mapCoordinate){
      return gridCoordinateOf(mapCoordinate.getRow(), mapCoordinate.getCol());
    }

    Coordinate gridCoordinateOf(int mapRow, int mapCol){
      return new Coordinate(toGridRow.applyAsInt(mapRow,mapCol), toGridCol.applyAsInt(mapRow,mapCol));
    }

    Coordinate mapCoordinateOf(Coordinate gridCoordinate){
      return mapCoordinateOf(gridCoordinate.getRow(), gridCoordinate.getCol());
    }

    Coordinate mapCoordinateOf(int gridRow, int gridCol){
      return new Coordinate(
          toMapRow.applyAsInt(gridRow, gridCol),
          toMapCol.applyAsInt(gridRow, gridCol)
      );
    }

    Double[] centerOf(int mapRow, int mapCol){
      double x,y;
      x = findCenterX.applyAsDouble(mapRow,mapCol);
      y = findCenterY.applyAsDouble(mapRow,mapCol);
      return new Double[]{x,y};
    }

    Double[] centerOf(Coordinate coordinate){
      return centerOf(coordinate.getRow(), coordinate.getCol());
    }

    Double[] verticesOf(Coordinate mapCoordinate){
      return verticesOf(mapCoordinate.getRow(), mapCoordinate.getCol());
    }

    Double[] verticesOf(int mapRow, int mapCol){
      double[] xValues = Arrays.stream(this.xValues).map(x -> x+centerOf(mapRow, mapCol)[0]).toArray();
      double[] yValues = Arrays.stream(this.yValues).map(y -> y+centerOf(mapRow, mapCol)[1]).toArray();
      Double[] vertices = new Double[12];
      for (int i = 0; i < 6; i++){
        vertices[i*2] = xValues[i];
        vertices[i*2+1] = yValues[i];
      }
      return vertices;
    }

    public Double[] verticesOf(int mapRow, int mapCol, int sideLength){
      return Arrays.stream(verticesOf(mapRow, mapCol)).map(x -> Math.rint(x*sideLength)).toArray(Double[]::new);
    }

    public Double[] verticesOf(Coordinate location, int sideLength){
      return verticesOf(location.getRow(), location.getCol(), sideLength);
    }

  }

}
