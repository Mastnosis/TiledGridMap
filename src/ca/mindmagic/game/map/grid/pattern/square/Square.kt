package ca.mindmagic.game.map.grid.pattern.square

import ca.mindmagic.game.map.grid.pattern.Shape
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
                    topLeft(half),
                    topRight(half),
                    bottomRight(half),
                    bottomLeft(half)
            )

        }

        private fun topLeft(half: Double) = Point2D(-half, -half)
        private fun topRight(half: Double) = Point2D(half, -half)
        private fun bottomRight(half: Double) = Point2D(half, half)
        private fun bottomLeft(half: Double) = Point2D(-half, half)
    }
}
