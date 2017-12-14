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

import ca.mindmagic.game.map.grid.Koordinate;
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

public class HexMap implements GridMap{

  private static final double H = Math.sqrt(0.75);

  final Orientation orientation;

  static Grid grid = new HexGrid();

  protected final int height;
  protected final int width;
  protected final boolean wrapWidth;
  protected final boolean wrapHeight;
  Map<Koordinate, Set<Koordinate>> locations;

  public HexMap(int width, int height) {
    this(width, height, Orientation.POINTED_TOP);
  }

  public HexMap(int width, int height, Orientation orientation){
    this(width, height, false, false, orientation);
  }

  public HexMap(int width, int height, boolean wrapWidth, boolean wrapHeight, Orientation orientation){
    this.orientation = orientation;
    this.width = width;
    this.height = height;
    this.wrapWidth = wrapWidth;
    this.wrapHeight = wrapHeight;

    locations = new HashMap<>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Koordinate c = new Koordinate(i, j);
        locations.put(c, calcNeighborsOf(c));
      }
    }

  }

  public Set<Koordinate> neighborsOf(Koordinate mapLocation){
    if(locations.containsKey(mapLocation)){
      return locations.get(mapLocation);
    }
    return Collections.emptySet();
  }

  public Set<Koordinate> neighborsOf(int mapRow, int mapCol){
    return neighborsOf(new Koordinate(mapRow, mapCol));
  }

  private Set<Koordinate> calcNeighborsOf(Koordinate mapLocation){
    return calcNeighborsOf(mapLocation.getRow(), mapLocation.getCol());
  }

  private Set<Koordinate> calcNeighborsOf(int mapRow, int mapCol){
    Set<Koordinate> neighbors = grid.neighborsOf(orientation.gridCoordinateOf(mapRow, mapCol));
    return neighbors.stream()
        .map(this.orientation::mapCoordinateOf)
        .filter(this::ifMapContains)
        .collect(Collectors.toSet());
  }

  public Orientation getOrientation(){
    return orientation;
  }

  public int range(Koordinate source, Koordinate target){
    Koordinate gridSource = orientation.gridCoordinateOf(source);
    Koordinate gridTarget = orientation.gridCoordinateOf(target);
    return grid.range(gridSource, gridTarget);
  }

  @Override public int height() {
    return height;
  }

  @Override public int width() {
    return width;
  }

  @Override public Set<Koordinate> getLocations(){
    return locations.keySet();
  }

  public Koordinate coordinateOf(int row, int col){
    return orientation.gridCoordinateOf(row, col);
  }

  public boolean ifMapContains(Koordinate c){
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

  public Double[] verticesOf(Koordinate c){
    return orientation.verticesOf(c);
  }

  public Double[] verticesOf(int row, int col){
    return verticesOf(row, col, orientation);
  }

  public static Double[] verticesOf(int row, int col, Orientation orientation){
    return orientation.verticesOf(row, col);
  }

  public Double[] centerOf(Koordinate coordinate){
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

    Koordinate gridCoordinateOf(Koordinate mapCoordinate){
      return gridCoordinateOf(mapCoordinate.getRow(), mapCoordinate.getCol());
    }

    Koordinate gridCoordinateOf(int mapRow, int mapCol){
      return new Koordinate(toGridRow.applyAsInt(mapRow,mapCol), toGridCol.applyAsInt(mapRow,mapCol));
    }

    Koordinate mapCoordinateOf(Koordinate gridCoordinate){
      return mapCoordinateOf(gridCoordinate.getRow(), gridCoordinate.getCol());
    }

    Koordinate mapCoordinateOf(int gridRow, int gridCol){
      return new Koordinate(
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

    Double[] centerOf(Koordinate coordinate){
      return centerOf(coordinate.getRow(), coordinate.getCol());
    }

    Double[] verticesOf(Koordinate mapCoordinate){
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

    public Double[] verticesOf(Koordinate location, int sideLength){
      return verticesOf(location.getRow(), location.getCol(), sideLength);
    }

  }

}
