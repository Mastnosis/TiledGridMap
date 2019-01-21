package ca.mindmagic.game.map.grid.pattern.hex

import ca.mindmagic.game.map.grid.pattern.Shape
import javafx.geometry.Point2D

class Hexagon(sideLength: Double) : Shape(sideLength) {

    override fun numberOfSides(): Int {
        return 6
    }

    override fun getVertices() = getVertices(sideLength)

    companion object {

        // HEIGHT = shortest distance between the center and any of the hex sides
        // where sideLength is equal to one.
        val HEIGHT = Math.sqrt(0.75)

        fun getVertices(sideLength: Double): Array<Point2D> {
            return arrayOf(
                    topPoint(sideLength),
                    upperRightPoint(sideLength),
                    lowerRightPoint(sideLength),
                    bottomPoint(sideLength),
                    lowerLeftPoint(sideLength),
                    upperLeftPoint(sideLength))
        }

        internal fun upperRightPoint(size: Double): Point2D {
            val x = (HEIGHT * size)
            val y = -size / 2.0
            return Point2D(x, y)
        }

        internal fun lowerRightPoint(size: Double): Point2D {
            val x = (HEIGHT * size)
            val y = size / 2.0
            return Point2D(x, y)
        }

        internal fun bottomPoint(size: Double): Point2D { // done
            val x = 0.0
            val y = size
            return Point2D(x, y)
        }

        internal fun lowerLeftPoint(size: Double): Point2D {
            val x = -(HEIGHT * size)
            val y = size / 2.0
            return Point2D(x, y)
        }

        internal fun upperLeftPoint(size: Double): Point2D {
            val x = -(HEIGHT * size)
            val y = -size / 2.0
            return Point2D(x, y)
        }

        internal fun topPoint(size: Double): Point2D { // done
            val x = 0.0
            val y = -size
            return Point2D(x, y)
        }
    }


}