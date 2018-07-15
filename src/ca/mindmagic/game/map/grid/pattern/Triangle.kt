package ca.mindmagic.game.map.grid.pattern

import java.awt.Point

class Triangle : Shape {

    override val vertices: Array<Point>
        get() = getVertices(sideLength)

    constructor() : super() {}

    constructor(sideLength: Int) : super(sideLength) {}

    override fun numberOfSides(): Int {
        return 3
    }

    companion object {

        fun getVertices(sidelength: Int): Array<Point> {
            val vertices = arrayOfNulls<Point>(3)
            vertices[0] = Point()  // TODO create the appropriate points
            vertices[1] = Point()
            vertices[2] = Point()
            return vertices
        }
    }
}
