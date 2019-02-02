package ca.mindmagic.game.map.grid

import ca.mindmagic.game.map.grid.pattern.Pattern
import java.util.stream.Collectors

open class GridMap @JvmOverloads constructor(pattern: Pattern,
                                             _width: Int, _height: Int,
                                             val wrapHorizontal: Boolean = false, val wrapVertical: Boolean = false) : Grid(pattern) {

    /*
        for wrapped maps, it is important to have even dimensions in order for edges to match up cleanly
        neighborsOf and range will possibly give undefined behavior if dimension is odd
     */
    // TODO consider forcing dimensions to be even (+ _height % 2)
    val height = if (_height < 1) 1 else _height
    val width = if (_width < 1) 1 else _width



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

    /*
        It is important to note that wrapped maps need special consideration for range calculations as there is
        more than one possible path from source to target
     */
    override fun range(source: Coordinate, target: Coordinate): Int {
        // TODO optimize for use with wrapped edges. Should at least cap iterations of loop at max(width, height)
        return super.universalRange(source, target)  // quick fix but not optimized
    }

    private fun wrappedRange(source: Coordinate, target: Coordinate): Int {
        var range = range(source, target)
        if (wrapHorizontal) {

        }
        if (wrapVertical) {

        }
        return range
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
            yOffset = height + yOffset       // it is important that height never be less than 1 or this will never exit
        }
        while (yOffset >= height) {
            yOffset -= height
        }
        return Coordinate(yOffset, c.col)
    }

    private fun wrapHorizontal(c: Coordinate): Coordinate {
        var xOffset = c.col
        while (xOffset < 0) {
            xOffset = width + xOffset       // it is important that width never be less than 1 or this will never exit
        }
        while (xOffset >= width) {
            xOffset -= width
        }
        return Coordinate(c.row, xOffset)
    }
}

