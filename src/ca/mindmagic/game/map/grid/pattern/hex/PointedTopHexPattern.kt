package ca.mindmagic.game.map.grid.pattern.hex

import ca.mindmagic.game.map.grid.Coordinate
import ca.mindmagic.game.map.grid.pattern.Pattern
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.max

/*
   rows and columns based on pointed orientation below

    /\  /\  /\  /\  /\  /\   Y
   /  \/  \/  \/  \/  \/  \
   |   |   |   |   |   |   |
   |X0 | 1 | 2 |   |   |   | 0
   \  /\  /\  /\  /\  /\  /
    \/  \/  \/  \/  \/  \/
     |   |   |   |   |   |
     | 0 | 1 | 2 |   |   |   1
    /\  /\  /\  /\  /\  /\
   /  \/  \/  \/  \/  \/  \
   |   |   |   |   |   |   |
   | 0 | 1 | 2 |   |   |   | 2
   \  /\  /\  /\  /\  /\  /
    \/  \/  \/  \/  \/  \/

 */

class PointedTopHexPattern : Pattern() {
    /**
     * Returns all locations adjacent to the given coordinate
     */
    override fun neighborsOf(location: Coordinate): Set<Coordinate> {
        return setOf(
                Coordinate(location.row, location.col + 1),           // right
                Coordinate(location.row + 1, location.col),          // bottom right
                Coordinate(location.row + 1, location.col - 1),  // bottom left
                Coordinate(location.row, location.col - 1),           // left
                Coordinate(location.row - 1, location.col - 1),  // upper left
                Coordinate(location.row - 1, location.col))          // upper right
    }

    /**
     * The points that define the corners of the given location
     * @param location
     * @return array of doubles in the form of {x1, y1, x2, y2...}
     */
    override fun verticesOf(location: Coordinate, sideLength: Double): Array<Double> {
        val halfWidth = sideLength * Math.sqrt(0.75)
        val centerPoint = centerPointOf(location, sideLength)
        val x = centerPoint[0]
        val y = centerPoint[1]
        return arrayOf(
                x, y - sideLength,                      // top
                x + halfWidth, y - sideLength / 2,      // top right
                x + halfWidth, y + sideLength / 2,      // bottom right
                x, y + sideLength,                      // bottom
                x - halfWidth, y + sideLength / 2,      // bottom left
                x - halfWidth, y - sideLength / 2)      // top left
    }

    /**
     * A pair of x and y that represent the center point of the tile
     * @param location
     * @param sideLength the length of one side of the tile
     * @return the x and y coordinate of the center point as Double
     */
    override fun centerPointOf(location: Coordinate, sideLength: Double): Array<Double> {
        val width = sideLength * Math.sqrt(0.75) * 2
        val x = (location.col * width) + (width / 2 * (location.col % 2))
        val y = location.row * sideLength * 1.5
        return arrayOf(x, y)
    }

    /**
     * the shortest number of adjacent hops from the source coordinate to the target.
     * Adjacent coordinates would be range one. A coordinate and itself would be range zero.
     * @param source the first coordinate
     * @param target the second coordinate
     * @return number of hops from source to target
     */
    override fun range(source: Coordinate, target: Coordinate): Int {
        val deltaX = source.x() - target.x()
        val deltaY = source.y() - target.y()
        return max(max(abs(deltaX), abs(deltaY)), abs(deltaX - deltaY))
    }

    /*
        Helper functions for conversion to axial coordinates which makes for cleaner range calculations
     */
    fun Coordinate.x() = col + ceil(row / 2.0).toInt()

    fun Coordinate.y() = row

}

