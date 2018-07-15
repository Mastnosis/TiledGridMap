package ca.mindmagic.game.map.grid.pattern

import java.awt.Point

class Square : Shape {

    override val vertices: Array<Point>
        get() = getVertices(sideLength)

    constructor() {}

    constructor(sideLength: Int) {
        if (sideLength > 0) {
            this.sideLength = sideLength
        }
    }

    override fun numberOfSides(): Int {
        return 4
    }

    override fun lengthOfSides(): Int {
        return sideLength
    }

    companion object {

        fun getVertices(sideLength: Int): Array<Point> {
            val vertices = arrayOfNulls<Point>(4)
            val half = sideLength / 2
            vertices[0] = Point(-half, -half)
            vertices[1] = Point(half, -half)
            vertices[2] = Point(half, half)
            vertices[3] = Point(-half, half)
            return vertices
        }
    }
}
