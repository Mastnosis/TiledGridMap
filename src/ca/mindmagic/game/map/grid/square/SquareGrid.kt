package ca.mindmagic.game.map.grid.square

import java.util.HashSet

import ca.mindmagic.game.map.grid.Coordinate
import ca.mindmagic.game.map.grid.Grid
import javafx.geometry.Point2D

class SquareGrid(sideLength: Int) : Grid {

    var sideLength: Int = 0
        internal set

    protected var diagonalIsAdjacent = false


    init {
        this.sideLength = sideLength
    }

    override fun verticesOf(c: Coordinate): Array<Double> {
        return verticesOf(c.row, c.col, sideLength)
    }

    override fun verticesOf(row: Int, col: Int): Array<Double> {
        return verticesOf(row, col, sideLength)
    }

    override fun centerPointOf(x: Int, y: Int): Point2D {
        return Point2D(0.0, 0.0)
    }  // TODO needs implementing

    override fun neighborsOf(c: Coordinate): Set<Coordinate> {
        return neighborsOf(c.row, c.col)
    }

    override fun neighborsOf(row: Int, col: Int): Set<Coordinate> {
        val neighbors = HashSet<Coordinate>()
        neighbors.add(Coordinate(row + 1, col))
        neighbors.add(Coordinate(row, col + 1))
        neighbors.add(Coordinate(row - 1, col))
        neighbors.add(Coordinate(row, col - 1))
        if (diagonalIsAdjacent) {
            neighbors.add(Coordinate(row + 1, col + 1))
            neighbors.add(Coordinate(row - 1, col + 1))
            neighbors.add(Coordinate(row + 1, col - 1))
            neighbors.add(Coordinate(row - 1, col - 1))
        }
        return neighbors
    }

    override fun range(c1: Coordinate, c2: Coordinate): Int {
        var range = 0
        range += Math.abs(c1.col - c2.col)
        range += Math.abs(c1.row - c2.row)
        return range
    }

    companion object {

        fun verticesOf(row: Int, col: Int, sideLength: Int): Array<Double> {
            val topLeft = topLeft(row, col, sideLength)
            val topRight = topRight(row, col, sideLength)
            val bottomLeft = bottomLeft(row, col, sideLength)
            val bottomRight = bottomRight(row, col, sideLength)
            return arrayOf(topLeft.x, topLeft.y, topRight.x, topRight.y, bottomLeft.x, bottomLeft.y, bottomRight.x, bottomRight.x)
        }

        fun centerPointOf(c: Coordinate, sideLength: Int): Point2D {
            val p = topLeft(c.row, c.col, sideLength)
            val halfOfSide = sideLength / 2
            return Point2D(p.x + halfOfSide, p.y + halfOfSide)
        }

        private fun bottomRight(row: Int, col: Int, sideLength: Int): Point2D {
            return Point2D(((row + 1) * sideLength).toDouble(), ((col + 1) * sideLength).toDouble())
        }

        private fun bottomLeft(row: Int, col: Int, sideLength: Int): Point2D {
            return Point2D((row * sideLength).toDouble(), ((col + 1) * sideLength).toDouble())
        }

        private fun topRight(row: Int, col: Int, sideLength: Int): Point2D {
            return Point2D(((row + 1) * sideLength).toDouble(), (col * sideLength).toDouble())
        }

        private fun topLeft(row: Int, col: Int, sideLength: Int): Point2D {
            return Point2D((row * sideLength).toDouble(), (col * sideLength).toDouble())
        }
    }

}
