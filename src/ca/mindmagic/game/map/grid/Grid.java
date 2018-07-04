package ca.mindmagic.game.map.grid;

import java.util.HashSet;
import java.util.Set;

import javafx.geometry.Point2D;

public interface Grid {


	/**
	 * return the set of all adjacent locations to the specified location
	 */
	Set<Coordinate> neighborsOf(Coordinate location);

	Set<Coordinate> neighborsOf(int row, int col);

	/**
	 * Return the set of all locations within the given radius including center location.
	 * i.e. Radius 0 would return the center tile. Radius 1 would return center tile and
	 * all its immediate neighbors. Radius 2 would return the center, its neighbors and their
	 * neighbors etc.
	 * @param center the center of the area
   * @param radius the range from the center
   * @return all the coordinates within the radius of the center
	 */
	default Set<Coordinate> getArea(Coordinate center, int radius) {
		Set<Coordinate> results = new HashSet<>();
		if(radius < 0){
			// do nothing
		} else if (radius == 0){
			results.add(center);
		} else {
			Set<Coordinate> intermediate = getArea(center, radius - 1);
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

	/**
	 * The angle from the center of the starting location to the center of the target location.
	 * This can be useful for determining unit facing.
	 *
	 * @param origin
	 * @param target
	 * @return angle in degrees
	 */
	default double angleOf(Coordinate origin, Coordinate target) {
		Point2D a = centerPointOf(origin);
		Point2D b = centerPointOf(target);
		return a.angle(centerPointOf(origin.getRow(), origin.getCol() + 1), b);
	}

	default Double[] verticesOf(Coordinate c) {
		return verticesOf(c.getRow(), c.getCol());
	}

	Double[] verticesOf(int row, int col);

	//default Point[] verticesOf(int row, int col){
	//	Point[] vertices = new Point[getShape().numberOfSides()];
	//	Point centerPoint = getCenterPoint(row, col);
	//	int index = 0;
	//	for (Point p: getShape().verticesOf()) {
	//		vertices[index] = new Point(centerPoint.x + p.x, centerPoint.y + p.y);
	//		index++;
	//	}
	//	return vertices;
	//}

	default Point2D centerPointOf(Coordinate c) {
		return centerPointOf(c.getRow(), c.getCol());
	}

	Point2D centerPointOf(int x, int y);
}
