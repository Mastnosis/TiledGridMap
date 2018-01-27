package ca.mindmagic.game.map;

import ca.mindmagic.game.map.grid.Coordinate;
import java.util.Set;

public interface GridMap {

  int height();
  int width();
  Coordinate coordinateOf(int row, int col);
  Double[] verticesOf(int row, int col);
  Double[] centerOf(int row, int col);
  Double[] centerOf(Coordinate target);
  Set<Coordinate> neighborsOf(Coordinate target);
  Set<Coordinate> neighborsOf(int row, int col);
  int range(Coordinate c1, Coordinate c2);
  Set<Coordinate> getLocations();

  default boolean ifLocationExistsOnMap(Coordinate location){
    return ifLocationExistsOnMap(location.getRow(), location.getCol());
  }

  default boolean ifLocationExistsOnMap(int row, int col){
    if(row < 0 || row > height()) return false;
    if(col < 0 || col > width()) return false;
    return true;
  }


}
