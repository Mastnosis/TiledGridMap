package ca.mindmagic.game.map.grid.hex

import ca.mindmagic.game.map.grid.Coordinate
import ca.mindmagic.game.map.grid.Grid
import ca.mindmagic.game.map.grid.pattern.Hexagon
import java.util.LinkedHashSet

import javafx.geometry.Point2D

class HexGrid @JvmOverloads constructor(var sideLength: Double = 60.0) : Grid {

    enum class Direction constructor(private val row: Int, private val col: Int) {
        A(-1, 0), B(0, 1), C(1, 1), D(1, 0), E(0, -1), F(-1, -1);

        fun move(start: Coordinate): Coordinate {
            return Coordinate(start.row + row, start.col + col)
        }

    }


    fun neighborsOf(row: Int, col: Int): Set<Coordinate> {
        return setOf(
                coord(row - 1, col),
                coord(row, col + 1),
                coord(row + 1, col + 1),
                coord(row + 1, col),
                coord(row, col - 1),
                coord(row - 1, col - 1)
        )
    }

    override fun range(source: Coordinate, target: Coordinate): Int {
        return range(source.row, source.col, target.row, target.col)
    }

    override fun verticesOf(row: Int, col: Int): Array<Double> {
        return verticesOf(row, col, sideLength)
    }

    override fun centerPointOf(x: Int, y: Int): Point2D {
        return centerPointOf(x, y, sideLength)
    }

    override fun toString(): String {
        return "HexGrid($sideLength)"
    }

    companion object {

        fun verticesOf(row: Int, col: Int, sideLength: Double): Array<Double> {
// TODO requires implementation
            return emptyArray()
        }


        fun centerPointOf(row: Int, col: Int, sidelength: Double): Point2D {
            val hexWidth = 2 * (sidelength * Hexagon.HEIGHT).toInt()
            val oddRowOffset = row % 2 * hexWidth / 2
            val centerPointX = col * hexWidth + oddRowOffset
            val centerPointY = (row.toDouble() * 1.5 * sidelength).toInt()
            return Point2D(centerPointX.toDouble(), centerPointY.toDouble())
        }

        private fun range(row1: Int, col1: Int, row2: Int, col2: Int): Int {
            val deltaRow = row1 - row2
            val deltaCol = col1 - col2
            return Math.max(Math.max(Math.abs(deltaRow), Math.abs(deltaCol)), Math.abs(deltaRow - deltaCol))
        }
    }

}

fun coord(row: Int, col: Int) = Coordinate(row, col)

