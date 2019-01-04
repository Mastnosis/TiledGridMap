package ca.mindmagic.game.map.grid

import ca.mindmagic.game.map.grid.pattern.Pattern


abstract class GridMap @JvmOverloads constructor(val pattern: Pattern,
                                                 val height: Int, val width: Int,
                                                 val wrapVertical: Boolean = false, val wrapHorizontal: Boolean = false) : Grid {


    fun contains(location: Coordinate): Boolean {
        return contains(location.row, location.col)
    }

    fun contains(row: Int, col: Int): Boolean {
        if (row < 0 || row >= height) return false
        if (col < 0 || col >= width) return false
        return true
    }

    override fun area(center: Coordinate, radius: Int): Set<Coordinate> {
        if (!contains(center)) return emptySet()
        return super.area(center, radius)
    }

    protected fun mapToGridCoordinate(c: Coordinate): Coordinate {
        return mapToGridCoordinate(c.row, c.col)
    }

    protected abstract fun mapToGridCoordinate(row: Int, col: Int): Coordinate

    protected fun gridToMapCoordinate(c: Coordinate): Coordinate {
        return gridToMapCoordinate(c.row, c.col)
    }

    protected abstract fun gridToMapCoordinate(xAxis: Int, yAxis: Int): Coordinate

    protected fun wrapIfNeeded(c: Coordinate): Coordinate {
        var result = c
        if (wrapVertical) result = wrapHeight(result)
        if (wrapHorizontal) result = wrapWidth(result)
        return result
    }

    protected fun wrapHeight(c: Coordinate): Coordinate {
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
