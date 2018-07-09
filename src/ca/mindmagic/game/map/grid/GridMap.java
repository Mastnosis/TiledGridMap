package ca.mindmagic.game.map.grid;

import ca.mindmagic.game.map.grid.Coordinate;
import ca.mindmagic.game.map.grid.Grid;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.geometry.Point2D;

public abstract class GridMap {

  protected int height, width;
  protected Grid grid;
  protected boolean wrapVertical, wrapHorizontal;

  public GridMap(Grid grid, int height, int width, boolean wrapVertical, boolean wrapHorizontal) {
    this.grid = grid;
    this.height = height;
    this.width = width;
    this.wrapVertical = wrapVertical;
    this.wrapHorizontal = wrapHorizontal;
  }

  public Double[] verticesOf(Coordinate c) {
    return verticesOf(c.getRow(), c.getCol());
  }

  public abstract Double[] verticesOf(int row, int col);

  public abstract Point2D centerOf(int row, int col);

  public Point2D centerOf(Coordinate location) {
    return centerOf(location.getRow(), location.getCol());
  }

  public Set<Coordinate> neighborsOf(Coordinate location) {
    return neighborsOf(location.getRow(), location.getCol());
  }

  public abstract Set<Coordinate> neighborsOf(int row, int col);
  public abstract int range(Coordinate c1, Coordinate c2);

  @Deprecated
  public abstract Set<Coordinate> getLocations();

  public int getHeight(){
    return height;
  }

  public int getWidth(){
    return width;
  }

  public boolean locationExistsOnMap(Coordinate location){
    return locationExistsOnMap(location.getRow(), location.getCol());
  }

  public Set<Coordinate> getArea(Coordinate c, int radius){
    return getArea(c.getRow(), c.getCol(), radius);
  }

  public Set<Coordinate> getArea(int row, int col, int radius) {
    Set<Coordinate> area = grid.getArea(mapToGridCoordinate(row, col), radius).stream()
        .map(this::gridToMapCoordinate)
        .collect(Collectors.toSet());
    if (wrapHorizontal) {
      area = area.stream().map(this::wrapWidth).collect(Collectors.toSet());
    }
    if (wrapVertical) {
      area = area.stream().map(this::wrapHeight).collect(Collectors.toSet());
    }
    return area.stream().filter(this::locationExistsOnMap).collect(Collectors.toSet());
  }

  protected Coordinate mapToGridCoordinate(Coordinate c) {
    return mapToGridCoordinate(c.getRow(), c.getCol());
  }

  protected abstract Coordinate mapToGridCoordinate(int row, int col);

  protected Coordinate gridToMapCoordinate(Coordinate c) {
    return gridToMapCoordinate(c.getRow(), c.getCol());
  }

  protected abstract Coordinate gridToMapCoordinate(int xAxis, int yAxis);

  public boolean locationExistsOnMap(int row, int col){
    if (row < 0 || row > height - 1) return false;
    if (col < 0 || col > width - 1) return false;
    return true;
  }

  /**
   * The angle of a line between the center of the origin to the center of the target. This
   * can be useful for unit facing calculations
   *
   * @param origin the starting location
   * @param target the end point
   * @return the angle in degrees
   */
  public double angleOf(Coordinate origin, Coordinate target) {
    Point2D a = centerOf(origin);
    Point2D b = centerOf(target);
    return a.angle(centerOf(origin.getRow(), origin.getCol() + 1), b);
  }

  private Coordinate wrapHeight(Coordinate c) {
    int yOffset = c.getRow();
    if (yOffset < 0) {
      return new Coordinate(height + yOffset, c.getCol());
    }
    if (yOffset >= height) {
      return new Coordinate(yOffset - height, c.getCol());
    }
    return c;
  }

  private Coordinate wrapWidth(Coordinate c) {
    int xOffset = c.getCol();
    if (xOffset < 0) {
      return new Coordinate(c.getRow(), width + xOffset);
    }
    if (xOffset >= width) {
      return new Coordinate(c.getRow(), xOffset - width);
    }
    return c;
  }
}
