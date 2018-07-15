package ca.mindmagic.game.map.grid.pattern

import javafx.geometry.Point2D

abstract class Shape @JvmOverloads constructor(var sideLength: Double = DEFAULT) {


    abstract fun getVertices(): Array<Point2D>

    abstract fun numberOfSides(): Int

    open fun lengthOfSides(): Double {
        return sideLength
    }

    companion object {
        private val DEFAULT = 40.0
    }
}
