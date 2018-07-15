package ca.mindmagic.game.map.grid.pattern

import javafx.geometry.Point2D

class Square(sideLength: Double) : Shape(sideLength) {



    override fun numberOfSides(): Int {
        return 4
    }

    override fun getVertices(): Array<Point2D> {
        return getVertices(sideLength)
    }

    companion object {

        fun getVertices(sideLength: Double): Array<Point2D> {
            val half = sideLength / 2.0
            return arrayOf(
                    Point2D(-half, -half),
                    Point2D(half, -half),
                    Point2D(half, half),
                    Point2D(-half, half))

        }
    }
}
