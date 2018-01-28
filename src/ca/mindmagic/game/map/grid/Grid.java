package ca.mindmagic.game.map.grid;

import ca.mindmagic.game.map.grid.pattern.Shape;
import java.util.HashSet;
import java.util.Set;

public interface Grid {


	Shape getShape();

	/**
	 * return the set of all adjacent locations to the specified location
	 */
	Set<Coordinate> neighborsOf(Coordinate location);
	
	/**
	 * Return the set of all locations within the given radius including source location.
	 * i.e. Radius 0 would return the source tile. Radius 1 would return source tile and
	 * all its immediate neighbors. Radius 2 would return the source, its neighbors and their neighbors
	 */
	default Set<Coordinate> neighborsRadius(Coordinate location, int radius){
		Set<Coordinate> results = new HashSet<Coordinate>();
		if(radius < 0){
			// do nothing
		} else if (radius == 0){
			results.add(location);
		} else {
			Set<Coordinate> intermediate = neighborsRadius(location, radius -1);
			for (Coordinate cd : intermediate) {
				results.addAll(neighborsOf(cd));
			}
			results.addAll(intermediate);
		}
		return results;
	}

	/**
	 * Returns all coordinates at the specified radius. Locations
	 * within that radius are excluded.
	 */
	default Set<Coordinate> ring(Coordinate c, int radius){
		
		Set<Coordinate> s = new HashSet<>();
		if (radius < 0){
			// do nothing
		} else if (radius == 0){
			s.add(c);
		} else {
			s.addAll(ring(c, radius -1));
		}
		return s;
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
	
	//public Point[] getVertices(Koordinate c){
	//	return getVertices(c.getRow(), c.getColumn());
	//}
  //
	//public Point[] getVertices(int row, int col){
	//	Point[] vertices = new Point[getShape().numberOfSides()];
	//	Point centerPoint = getCenterPoint(row, col);
	//	int index = 0;
	//	for (Point p: getShape().getVertices()) {
	//		vertices[index] = new Point(centerPoint.x + p.x, centerPoint.y + p.y);
	//		index++;
	//	}
	//	return vertices;
	//}
	//
	//public Point getCenterPoint(Koordinate c){
	//	return getCenterPoint(c.row, c.column);
	//}
  //
  //
	//public abstract Point getCenterPoint(int x, int y);
}
