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
import ca.mindmagic.game.map.grid.HexGrid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.IntBinaryOperator;
import java.util.function.ToDoubleBiFunction;
import java.util.stream.Collectors;

import javafx.geometry.Point2D;

public class HexMap extends GridMap{

  final Orientation orientation;

  Map<Coordinate, Set<Coordinate>> locations;

  public HexMap(int height, int width) {
    this(height, width, Orientation.POINTED_TOP);
  }

  public HexMap(int height, int width, Orientation orientation){
    this(height, width, false, false, orientation);
  }

  public HexMap(int height, int width, boolean wrapHeight, boolean wrapWidth, Orientation orientation) {
    super(new HexGrid(), height, width, wrapHeight, wrapWidth);
    this.orientation = orientation;
    this.width = width;
    this.height = height;

    locations = new HashMap<>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Coordinate c = new Coordinate(i, j);
        locations.put(c, calcNeighborsOf(c));
      }
    }

  }

  public Set<Coordinate> neighborsOf(Coordinate mapLocation){
    return calcNeighborsOf(mapLocation);
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
    Set<Coordinate> neighbors = grid.neighborsOf(mapToGridCoordinate(new Coordinate(mapRow, mapCol)))
        .stream()
        .map(this.orientation::mapCoordinateOf)
        .filter(this::locationExistsOnMap)
        .collect(Collectors.toSet());
    if (wrapHorizontal && orientation == Orientation.POINTED_TOP) {
      if (mapCol == 0) {
        for (Coordinate gc : grid.neighborsOf(orientation.gridCoordinateOf(mapRow, mapCol))) {
          Coordinate mc = gridToMapCoordinate(gc);
          if(mc.getCol() == -1){
            Coordinate wrapped = new Coordinate(mc.getRow(), width -1);
            if (locationExistsOnMap(wrapped)) {
              neighbors.add(wrapped);
            }
          }
          if(mc.getCol() == width){
            Coordinate wrapped = new Coordinate(mc.getRow(), 0);
            if (locationExistsOnMap(wrapped)) {
              neighbors.add(wrapped);
            }
          }
        }
      }
    }
    return neighbors;
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

  @Override
  protected Coordinate mapToGridCoordinate(int row, int col) {
    return orientation.gridCoordinateOf(row, col);
  }

  @Override
  protected Coordinate gridToMapCoordinate(int xAxis, int yAxis) {
    return orientation.mapCoordinateOf(xAxis, yAxis);
  }

  @Override
  public Double[] verticesOf(int row, int col){
    return orientation.verticesOf(row, col);
  }

  public static Double[] verticesOf(int row, int col, Orientation orientation){
    return orientation.verticesOf(row, col);
  }

  @Override
  public Point2D centerOf(int row, int col) {
    return orientation.centerOf(row, col);
  }

  /**
   * Orientation - for a square map, there are two primary orientations for a hex grid; either
   * pointed top or flat top. These alter the calculation of row and column indexes as well as the
   * hexagon vertices.
   */

  private static final double H = Math.sqrt(0.75);

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

    Point2D centerOf(int mapRow, int mapCol) {
      double x,y;
      x = findCenterX.applyAsDouble(mapRow,mapCol);
      y = findCenterY.applyAsDouble(mapRow,mapCol);
      return new Point2D(x, y);
    }

    Point2D centerOf(Coordinate coordinate) {
      return centerOf(coordinate.getRow(), coordinate.getCol());
    }

    Double[] verticesOf(Coordinate mapCoordinate){
      return verticesOf(mapCoordinate.getRow(), mapCoordinate.getCol());
    }

    Double[] verticesOf(int mapRow, int mapCol){
      Point2D center = centerOf(mapRow, mapCol);
      double[] xValues = Arrays.stream(this.xValues).map(x -> x + center.getX()).toArray();
      double[] yValues = Arrays.stream(this.yValues).map(y -> y + center.getY()).toArray();
      int points = 6;
      Double[] vertices = new Double[points * 2];
      for (int i = 0; i < points; i++) {
        int index = i * 2;
        vertices[index] = xValues[i];
        vertices[index + 1] = yValues[i];
      }
      return vertices;
    }

    public Double[] verticesOf(int mapRow, int mapCol, int sideLength){
      return Arrays.stream(verticesOf(mapRow, mapCol)).map(d -> (d * sideLength)).toArray(Double[]::new);
    }

    public Double[] verticesOf(Coordinate location, int sideLength){
      return verticesOf(location.getRow(), location.getCol(), sideLength);
    }

  }
}
