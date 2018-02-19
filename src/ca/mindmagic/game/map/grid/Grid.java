package ca.mindmagic.game.map.grid;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public interface Grid {


	/**
	 * return the set of all adjacent locations to the specified location
	 */
	Set<Coordinate> neighborsOf(Coordinate location);
	
	/**
	 * Return the set of all locations within the given radius including source location.
	 * i.e. Radius 0 would return the source tile. Radius 1 would return source tile and
	 * all its immediate neighbors. Radius 2 would return the source, its neighbors and their
	 * neighbors etc.
   * @param source the center location
   * @param radius the range from the center
   * @return all the coordinates within the radius of the center
	 */
	default Set<Coordinate> getArea(Coordinate source, int radius){
		Set<Coordinate> results = new HashSet<Coordinate>();
		if(radius < 0){
			// do nothing
		} else if (radius == 0){
			results.add(source);
		} else {
			Set<Coordinate> intermediate = getArea(source, radius -1);
      results.addAll(intermediate);
      for (Coordinate cd : intermediate) {
        results.addAll(neighborsOf(cd));
      }
		}
		return results;
	}

	default Set<Coordinate> getArea(int row, int col, int radius){
    return getArea(new Coordinate(row, col), radius);
  }

	/**
	 * Returns all coordinates at the specified radius. Locations
	 * within that radius are excluded.
	 */
	default Set<Coordinate> ring(Coordinate c, int radius){
		Set<Coordinate> set = getArea(c, radius);
		set.removeAll(getArea(c, radius -1));
		return set;
	}

	/**
	 * the shortest number of adjacent hops from the first coordinate to the second,
	 * not counting the first but counting the last. Adjacent coordinates would be range one.
	 * A coordinate and itself would be range zero.
	 * @param c1 the first coordinate
	 * @param c2 the second coordinate
	 * @return number of hops from c1 to c2
	 */
	int range(Coordinate c1, Coordinate c2);
	
	default Point[] getVertices(Coordinate c){
		return getVertices(c.getRow(), c.getCol());
	}

	Point[] getVertices(int row, int col);

	//default Point[] getVertices(int row, int col){
	//	Point[] vertices = new Point[getShape().numberOfSides()];
	//	Point centerPoint = getCenterPoint(row, col);
	//	int index = 0;
	//	for (Point p: getShape().getVertices()) {
	//		vertices[index] = new Point(centerPoint.x + p.x, centerPoint.y + p.y);
	//		index++;
	//	}
	//	return vertices;
	//}

	default Point getCenterPoint(Coordinate c){
		return getCenterPoint(c.getRow(), c.getCol());
	}

	Point getCenterPoint(int x, int y);
}
