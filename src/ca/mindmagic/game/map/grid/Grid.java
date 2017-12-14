package ca.mindmagic.game.map.grid;

import ca.mindmagic.game.map.grid.shape.Shape;
import java.util.HashSet;
import java.util.Set;

public abstract class Grid {

	//static final int DEFAULT_SIZE = 40;

	public abstract Shape getShape();

	/*
	 * return the set of all adjacent locations to the specified location
	 */
	public abstract Set<Koordinate> neighborsOf(Koordinate location);
	
	/**
	 * Return the set of all locations within the given radius including source location.
	 * i.e. Radius 0 would return the source tile. Radius 1 would return source tile and
	 * all its immediate neighbors. Radius 2 would return the source, its neighbors and their neighbors
	 */
	public Set<Koordinate> neighborsRadius(Koordinate location, int radius){ // TODO consider switching from sets to arrays
		Set<Koordinate> results = new HashSet<Koordinate>();
		if(radius < 0){
			// do nothing
		} else if (radius == 0){
			results.add(location);
		} else {
			Set<Koordinate> intermediate = neighborsRadius(location, radius -1);
			for (Koordinate cd : intermediate) {
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
	public Set<Koordinate> ring(Koordinate c, int radius){
//		Set<Koordinate> s = neighborsRadius(c, radius);
//		s.removeAll(neighborsRadius(c, radius-1));
		
		Set<Koordinate> s = new HashSet<Koordinate>();
		if (radius < 0){
			// do nothing
		} else if (radius == 0){
			s.add(c);
		} else {
			s.addAll(ring(c, radius -1));
		}
		return s;
	}
	
	public abstract int range(Koordinate c1, Koordinate c2);
	
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
