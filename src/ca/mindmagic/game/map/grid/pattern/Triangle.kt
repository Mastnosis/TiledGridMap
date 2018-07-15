package ca.mindmagic.game.map.grid.pattern

import javafx.geometry.Point2D

class Triangle(sidelength: Double) : Shape(sidelength) {



    override fun numberOfSides(): Int {
        return 3
    }

    override fun getVertices(): Array<Point2D> {
        return getVertices(sideLength)
    }

    companion object {

        fun getVertices(sidelength: Double): Array<Point2D> {
            return arrayOf(
                    Point2D(0.0, 0.0),
                    Point2D(0.0, 0.0),
                    Point2D(0.0, 0.0)
            )
        }
    }
}
