package ca.mindmagic.game.map.grid.pattern.hex

import ca.mindmagic.game.map.grid.Coordinate
import ca.mindmagic.game.map.grid.pattern.Pattern
import javafx.geometry.Point2D

class HexPattern : Pattern() {
    /**
     * Returns all locations adjacent to the given coordinate
     */
    override fun neighborsOf(location: Coordinate): Set<Coordinate> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * The points that define the corners of the given location
     * @param location
     * @return array of doubles in the form of {x1, y1, x2, y2...}
     */
    override fun verticesOf(location: Coordinate, sideLength: Double): Array<Double> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * A pair of x and y that represent the center point of the tile
     * @param location
     * @return the x and y coordinate of the center point as Double
     */
    override fun centerPointOf(location: Coordinate, sideLength: Double): Array<Double> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * the shortest number of adjacent hops from the source coordinate to the target.
     * Adjacent coordinates would be range one. A coordinate and itself would be range zero.
     * @param source the first coordinate
     * @param target the second coordinate
     * @return number of hops from source to target
     */
    override fun range(source: Coordinate, target: Coordinate): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


//    override fun neighborsOf(row: Int, col: Int): Set<Coordinate> {
//        return setOf(
//                Coordinate(row - 1, col),
//                Coordinate(row, col + 1),
//                Coordinate(row + 1, col + 1),
//                Coordinate(row + 1, col),
//                Coordinate(row, col - 1),
//                Coordinate(row - 1, col - 1)
//        )
//    }

}