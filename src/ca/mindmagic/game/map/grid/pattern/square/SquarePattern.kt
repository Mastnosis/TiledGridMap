package ca.mindmagic.game.map.grid.pattern.square

import ca.mindmagic.game.map.grid.Coordinate
import ca.mindmagic.game.map.grid.pattern.Pattern
import kotlin.math.abs

class SquarePattern : Pattern() {

    /**
     * the shortest number of adjacent hops from the source coordinate to the target.
     * Adjacent coordinates would be range one. A coordinate and itself would be range zero.
     * @param source the first coordinate
     * @param target the second coordinate
     * @return number of hops from source to target
     */
    override fun range(source: Coordinate, target: Coordinate): Int {
        val deltaX = source.col - target.col
        val deltaY = source.row - target.row
        return abs(deltaX) + abs(deltaY)
    }

    /**
     * A pair of x and y that represent the center point of the tile
     * @param location
     * @return the x and y coordinate of the center point as Double
     */
    override fun centerPointOf(location: Coordinate, sideLength: Double): Array<Double> {
        return arrayOf(location.row * sideLength, location.col * sideLength)
    }

    /**
     * Returns all locations adjacent to the given coordinate
     */
    override fun neighborsOf(location: Coordinate): Set<Coordinate> {
        return setOf(Coordinate(location.row - 1, location.col),           // top
                Coordinate(location.row, location.col + 1),           // right
                Coordinate(location.row + 1, location.col),     // bottom
                Coordinate(location.row, location.col - 1))          // left
    }

    override fun verticesOf(location: Coordinate, sideLength: Double): Array<Double> {
        val half = sideLength / 2.0
        return arrayOf(
                -half, -half,
                half, -half,
                half, half,
                -half, half
        )
    }
}