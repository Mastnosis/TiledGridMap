package ca.mindmagic.game.map.grid.pattern

import ca.mindmagic.game.map.grid.Coordinate
import ca.mindmagic.game.map.grid.hex.coord
import javafx.geometry.Point2D

class HexPattern(size: Double = 60.0, rotationInDegree: Double = 0.0) : Pattern(size, rotationInDegree) {
    override fun centerPointOf(locX: Int, locY: Int): Point2D {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun verticesAsPoints(locX: Int, locY: Int): Array<Point2D> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun verticesAsDouble(locX: Int, locY: Int): Array<Double> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun neighborsOf(row: Int, col: Int): Set<Coordinate> {
        return setOf(
                Coordinate(row - 1, col),
                Coordinate(row, col + 1),
                Coordinate(row + 1, col + 1),
                Coordinate(row + 1, col),
                Coordinate(row, col - 1),
                Coordinate(row - 1, col - 1)
        )
    }

}