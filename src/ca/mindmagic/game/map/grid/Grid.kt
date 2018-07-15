package ca.mindmagic.game.map.grid


import javafx.geometry.Point2D

import java.util.HashSet


interface Grid {


    /**
     * return the set of all adjacent locations to the specified location
     */
    fun neighborsOf(location: Coordinate): Set<Coordinate>

    fun neighborsOf(row: Int, col: Int): Set<Coordinate>

    /**
     * Return the set of all locations within the given radius including center location.
     * i.e. Radius 0 would return the center tile. Radius 1 would return center tile and
     * all its immediate neighbors. Radius 2 would return the center, its neighbors and their
     * neighbors etc.
     * @param center the center of the area
     * @param radius the range from the center
     * @return all the coordinates within the radius of the center
     */
    fun getArea(center: Coordinate, radius: Int): MutableSet<Coordinate> {
        val results = HashSet<Coordinate>()
        if (radius < 0) {
            // do nothing
        } else if (radius == 0) {
            results.add(center)
        } else {
            val intermediate = getArea(center, radius - 1)
            results.addAll(intermediate)
            for (cd in intermediate) {
                results.addAll(neighborsOf(cd))
            }
        }
        return results
    }

    fun getArea(row: Int, col: Int, radius: Int): Set<Coordinate> {
        return getArea(Coordinate(row, col), radius)
    }

    /**
     * Returns all coordinates at the specified radius. Locations
     * within that radius are excluded.
     */
    fun ring(c: Coordinate, radius: Int): Set<Coordinate> {
        val set = getArea(c, radius)
        set.removeAll(getArea(c, radius - 1))
        return set
    }

    /**
     * the shortest number of adjacent hops from the first coordinate to the second,
     * not counting the first but counting the last. Adjacent coordinates would be range one.
     * A coordinate and itself would be range zero.
     * @param c1 the first coordinate
     * @param c2 the second coordinate
     * @return number of hops from c1 to c2
     */
    fun range(c1: Coordinate, c2: Coordinate): Int

    /**
     * The angle from the center of the starting location to the center of the target location.
     * This can be useful for determining unit facing.
     *
     * @param origin
     * @param target
     * @return angle in degrees
     */
    fun angleOf(origin: Coordinate, target: Coordinate): Double {
        val a = centerPointOf(origin)
        val b = centerPointOf(target)
        return a.angle(centerPointOf(origin.row, origin.col + 1), b)
    }

    open fun verticesOf(c: Coordinate): Array<Double> {
        return verticesOf(c.row, c.col)
    }

    fun verticesOf(row: Int, col: Int): Array<Double>

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

    fun centerPointOf(c: Coordinate): Point2D {
        return centerPointOf(c.row, c.col)
    }

    fun centerPointOf(x: Int, y: Int): Point2D
}
