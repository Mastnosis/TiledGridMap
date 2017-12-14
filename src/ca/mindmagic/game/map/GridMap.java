package ca.mindmagic.game.map;

import ca.mindmagic.game.map.grid.Koordinate;
import java.util.Set;

public interface GridMap {

  int height();
  int width();
  Koordinate coordinateOf(int row, int col);
  Double[] verticesOf(int row, int col);
  Double[] centerOf(int row, int col);
  Double[] centerOf(Koordinate target);
  Set<Koordinate> neighborsOf(Koordinate target);
  Set<Koordinate> neighborsOf(int row, int col);
  int range(Koordinate c1, Koordinate c2);
  Set<Koordinate> getLocations();

  default boolean ifLocationExistsOnMap(Koordinate location){
    return ifLocationExistsOnMap(location.row, location.column);
  }

  default boolean ifLocationExistsOnMap(int row, int col){
    if(row < 0 || row > height()) return false;
    if(col < 0 || col > width()) return false;
    return true;
  }


}
