package ca.mindmagic.game.map.grid.pattern

import ca.mindmagic.game.map.grid.Coordinate
import javafx.geometry.Point2D

abstract class Pattern(var size: Double = 60.0, var rotationInDegree: Double = 0.0) {

    abstract fun neighborsOf(locX: Int, locY: Int): Set<Coordinate>
    fun neighborsOf(location: Coordinate) = neighborsOf(location.row, location.col)

    abstract fun verticesAsDouble(locX: Int, locY: Int): Array<Double>

    abstract fun verticesAsPoints(locX: Int, locY: Int): Array<Point2D>

    abstract fun centerPointOf(locX: Int, locY: Int): Point2D

}