package ca.mindmagic.game.map;

import ca.mindmagic.game.map.grid.Coordinate;
import ca.mindmagic.game.map.grid.Grid;
import java.util.Set;

public abstract class GridMap {

  protected int height, width;
  protected Grid grid;

  public GridMap(int height, int width, Grid grid){
    this.height = height;
    this.width = width;
    this.grid = grid;
  }

  public abstract Coordinate coordinateOf(int row, int col);
  public abstract Double[] verticesOf(int row, int col);
  public abstract Double[] centerOf(int row, int col);
  public abstract Double[] centerOf(Coordinate target);
  public abstract Set<Coordinate> neighborsOf(Coordinate target);
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

  public Set<Coordinate> getArea(int row, int col, int radius){
    Set<Coordinate> set = grid.getArea(row, col, radius);

    return set;
  }

  public boolean locationExistsOnMap(int row, int col){
    if(row < 0 || row > height) return false;
    if(col < 0 || col > width) return false;
    return true;
  }
}
