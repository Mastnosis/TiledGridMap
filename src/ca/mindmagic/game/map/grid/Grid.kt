package ca.mindmagic.game.map.grid


import ca.mindmagic.game.map.grid.pattern.Pattern


open class Grid(val pattern: Pattern) {


    /**
     * return the set of all adjacent locations to the specified location
     */
    open fun neighborsOf(location: Coordinate): Set<Coordinate> {
        return neighborsOf(location.row, location.col)
    }

    open fun neighborsOf(row: Int, col: Int): Set<Coordinate> = pattern.neighborsOf(row, col)

    /**
     * Return the set of all locations within the given radius including center location.
     * i.e. Radius 0 would return the center tile. Radius 1 would return center tile and
     * all its immediate neighbors. Radius 2 would return the center, its neighbors and their
     * neighbors etc.
     * @param center the center of the area
     * @param radius the range from the center
     * @return all the coordinates within the radius of the center
     */
    fun area(center: Coordinate, radius: Int): Set<Coordinate> {
        if (radius < 0) {
            return emptySet()
        } else if (radius == 0) {
            return setOf(center)
        } else {
            val area = area(center, radius - 1)
            return area.plus(area.flatMap { neighborsOf(it) })
        }
    }

    fun area(row: Int, col: Int, radius: Int): Set<Coordinate> {
        return area(Coordinate(row, col), radius)
    }

    /**
     * Returns all coordinates at the specified radius. Locations
     * within that radius are excluded.
     */
    fun ring(center: Coordinate, radius: Int): Set<Coordinate> {
        return area(center, radius).minus(area(center, radius - 1))
    }

    /**
     * the shortest number of adjacent hops from the source coordinate to the target,
     * not counting the first but counting the last. Adjacent coordinates would be range one.
     * A coordinate and itself would be range zero.
     * @param source the first coordinate
     * @param target the second coordinate
     * @return number of hops from source to target
     */
//    fun range(source: Coordinate, target: Coordinate): Int

    /**
     * The angle from the center of the starting location to the center of the target location.
     * This can be useful for determining unit facing.
     *
     * @param origin
     * @param target
     * @return angle in degrees
     */
//    fun angleOf(origin: Coordinate, target: Coordinate): Double {
//        val a = centerPointOf(origin)
//        val b = centerPointOf(target)
//        return a.angle(a.add(1.0, 0.0), b)
//    }

//    fun verticesOf(c: Coordinate): Array<Double> {
//        return verticesOf(c.row, c.col)
//    }

//    fun verticesOf(row: Int, col: Int): Array<Double>

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

//    fun centerPointOf(c: Coordinate): Point2D {
//        return centerPointOf(c.row, c.col)
//    }

//    fun centerPointOf(x: Int, y: Int): Point2D
}
