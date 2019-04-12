package ca.mindmagic.game.map.grid.pattern

import ca.mindmagic.game.map.grid.Coordinate

abstract class Pattern {

    /**
     * Returns all locations adjacent to the given coordinate
     */
    abstract fun neighborsOf(location: Coordinate): Set<Coordinate>

    /**
     * The points that define the corners of the given location
     * @param location
     * @return array of doubles in the form of {x1, y1, x2, y2...}
     */
    abstract fun verticesOf(location: Coordinate, sideLength: Double = 60.0): Array<Double>

    /**
     * A pair of x and y that represent the center point of the tile
     * @param location
     * @return the x and y coordinate of the center point as Double
     */
    abstract fun centerPointOf(location: Coordinate, sideLength: Double = 60.0): Array<Double>

    /**
     * the shortest number of adjacent hops from the source coordinate to the target.
     * Adjacent coordinates would be range one. A coordinate and itself would be range zero.
     * @param source the first coordinate
     * @param target the second coordinate
     * @return number of hops from source to target
     */
    abstract fun range(source: Coordinate, target: Coordinate): Int

}