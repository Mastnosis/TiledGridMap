package ca.mindmagic.game.map.grid

import ca.mindmagic.game.map.grid.pattern.Pattern


open class GridMap @JvmOverloads constructor(pattern: Pattern,
                                             protected val width: Int, protected val height: Int,
                                             val wrapHorizontal: Boolean = false, val wrapVertical: Boolean = false) : Grid(pattern) {

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
        if (wrapVertical) result = wrapHeight(result)
        if (wrapHorizontal) result = wrapWidth(result)
        return result
    }

    private fun wrapHeight(c: Coordinate): Coordinate {
        val yOffset = c.row
        if (yOffset < 0) {
            return Coordinate(height + yOffset, c.col)
        }
        if (yOffset >= height) {
            return Coordinate(yOffset - height, c.col)
        } else {
            return c
        }
    }

    protected fun wrapWidth(c: Coordinate): Coordinate {
        val xOffset = c.col
        if (xOffset < 0) {
            return Coordinate(c.row, width + xOffset)
        }
        if (xOffset >= width) {
            return Coordinate(c.row, xOffset - width)
        } else {
            return c
        }
    }
}
