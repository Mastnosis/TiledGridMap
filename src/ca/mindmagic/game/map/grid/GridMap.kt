package ca.mindmagic.game.map.grid

import ca.mindmagic.game.map.grid.pattern.Pattern
import java.util.stream.Collectors

open class GridMap @JvmOverloads constructor(pattern: Pattern,
                                             _width: Int, _height: Int,
                                             val wrapHorizontal: Boolean = false, val wrapVertical: Boolean = false) : Grid(pattern) {

    val width = if (_width < 1) 1 else _width
    val height = if (_height < 1) 1 else _height


    override fun neighborsOf(location: Coordinate): Set<Coordinate> {
        return pattern.neighborsOf(location).stream()
                .map { c -> wrapIfNeeded(c) }
                .filter { c -> contains(c) }
                .collect(Collectors.toSet())
    }

    fun contains(location: Coordinate): Boolean {
        return contains(location.row, location.col)
    }

    fun contains(row: Int, col: Int): Boolean {
        if (row < 0 || row >= height) return false
        if (col < 0 || col >= width) return false
        return true
    }

    private fun wrapIfNeeded(c: Coordinate): Coordinate {
        var result = c
        if (wrapVertical) result = wrapVertical(result)
        if (wrapHorizontal) result = wrapHorizontal(result)
        return result
    }

    private fun wrapVertical(c: Coordinate): Coordinate {
        var yOffset = c.row
        while (yOffset < 0) {
            yOffset = height - yOffset       // it is important that height never be less than 1 or this will never exit
        }
        while (yOffset >= height) {
            yOffset -= height
        }
        return Coordinate(yOffset, c.col)
    }

    private fun wrapHorizontal(c: Coordinate): Coordinate {
        var xOffset = c.col
        while (xOffset < 0) {
            xOffset = width - xOffset       // it is important that width never be less than 1 or this will never exit
        }
        while (xOffset >= width) {
            xOffset -= width
        }
        return Coordinate(c.row, xOffset)
    }
}

