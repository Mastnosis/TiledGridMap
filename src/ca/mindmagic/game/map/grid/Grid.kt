package ca.mindmagic.game.map.grid


import ca.mindmagic.game.map.grid.pattern.Pattern


open class Grid @JvmOverloads constructor(val pattern: Pattern, var size: Double = 60.0) {


    /**
     * return the set of all adjacent locations to the specified location
     */
    open fun neighborsOf(location: Coordinate): Set<Coordinate> = pattern.neighborsOf(location)

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
        return when {
            radius < 0 -> emptySet()
            radius == 0 -> setOf(center)
            else -> {
                val area = area(center, radius - 1)
                area.plus(area.flatMap { neighborsOf(it) })
            }
        }
    }

    /**
     * Returns all coordinates at the specified radius. Locations
     * within that radius are excluded. Radius 0 will return center
     * Radius 1 will return only neighbors of center etc.
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
    open fun range(source: Coordinate, target: Coordinate): Int {
        return pattern.range(source, target)
    }

    /*
        The below algorithm has the advantage that it works to calculate range with any pattern. No need to create a new algorithm for each.
        The disadvantage is that it has terrible performance.
     */
    internal fun universalRange(source: Coordinate, target: Coordinate): Int {
        var range: Int = 0
        while (!ring(source, range).contains(target)) {
            range++
        }
        return range
    }

    /**
     * The angle from the center of the starting location to the center of the target location.
     * This can be useful for determining unit facing.
     *
     * @param source
     * @param target
     * @return angle in degrees
     */
    fun angleOf(source: Coordinate, target: Coordinate): Double {
        val a = pattern.centerPointOf(source)
        val b = pattern.centerPointOf(target)
        return 0.0
    }

    /**
     * Provide the vertices for the given location
     *
     * @param location
     * @return array of points (javafx.geometry.Point2D)
     */
    fun verticesOf(location: Coordinate) = pattern.verticesOf(location, size)

    /**
     * Provide the vertices for the given location
     *
     * @param location
     * @return the point that defines the center of the given coordinate (javafx.geometry.Point2D)
     */
    fun centerOf(location: Coordinate) = pattern.centerPointOf(location, size)

}
