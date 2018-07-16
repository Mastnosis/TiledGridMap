package ca.mindmagic.game.map.grid

import javafx.geometry.Point2D

abstract class GridMap(protected var grid: Grid,
                       protected val height: Int, protected val width: Int,
                       protected var wrapVertical: Boolean, protected var wrapHorizontal: Boolean) {

    @get:Deprecated("")
    abstract val locations: Set<Coordinate>


    fun verticesOf(c: Coordinate): Array<Double> {
        return verticesOf(c.row, c.col)
    }

    abstract fun verticesOf(row: Int, col: Int): Array<Double>

    abstract fun centerOf(row: Int, col: Int): Point2D

    fun centerOf(location: Coordinate): Point2D {
        return centerOf(location.row, location.col)
    }

    open fun neighborsOf(location: Coordinate): Set<Coordinate> {
        return neighborsOf(location.row, location.col)
    }

    abstract fun neighborsOf(row: Int, col: Int): Set<Coordinate>

    abstract fun range(c1: Coordinate, c2: Coordinate): Int

    fun locationExistsOnMap(location: Coordinate): Boolean {
        return locationExistsOnMap(location.row, location.col)
    }

    fun getArea(c: Coordinate, radius: Int): Set<Coordinate> {
        return getArea(c.row, c.col, radius)
    }

    fun getArea(row: Int, col: Int, radius: Int): Set<Coordinate> {
        val area = grid.area(mapToGridCoordinate(row, col), radius)
                .map { this.gridToMapCoordinate(it) }
                .filter { locationExistsOnMap(it) }
                .toSet()
//        if (wrapHorizontal) {
//            area = area.stream().map<Coordinate>(Function<Coordinate, Coordinate> { this.wrapWidth(it) }).collect<Set<Coordinate>, Any>(Collectors.toSet())
//        }
//        if (wrapVertical) {
//            area = area.stream().map<Coordinate>(Function<Coordinate, Coordinate> { this.wrapHeight(it) }).collect<Set<Coordinate>, Any>(Collectors.toSet())
//        }
        return area
    }

    protected fun mapToGridCoordinate(c: Coordinate): Coordinate {
        return mapToGridCoordinate(c.row, c.col)
    }

    protected abstract fun mapToGridCoordinate(row: Int, col: Int): Coordinate

    protected fun gridToMapCoordinate(c: Coordinate): Coordinate {
        return gridToMapCoordinate(c.row, c.col)
    }

    protected abstract fun gridToMapCoordinate(xAxis: Int, yAxis: Int): Coordinate

    @SuppressWarnings
    protected fun locationExistsOnMap(row: Int, col: Int): Boolean {
        if (row < 0 || row > height - 1) return false
        if (col < 0 || col > width - 1) return false
        return true
    }

    /**
     * The angle of a line between the center of the origin to the center of the target. This
     * can be useful for unit facing calculations
     *
     * @param origin the starting location
     * @param target the end point
     * @return the angle in degrees
     */
    fun angleOf(origin: Coordinate, target: Coordinate): Double {
        val a = centerOf(origin)
        val b = centerOf(target)
        return a.angle(centerOf(origin.row, origin.col + 1), b)
    }

    private fun wrapHeight(c: Coordinate): Coordinate {
        val yOffset = c.row
        if (yOffset < 0) {
            return Coordinate(height + yOffset, c.col)
        }
        return if (yOffset >= height) {
            Coordinate(yOffset - height, c.col)
        } else c
    }

    private fun wrapWidth(c: Coordinate): Coordinate {
        val xOffset = c.col
        if (xOffset < 0) {
            return Coordinate(c.row, width + xOffset)
        }
        return if (xOffset >= width) {
            Coordinate(c.row, xOffset - width)
        } else c
    }

    fun ring(center: Coordinate, radius: Int): Set<Coordinate> {
        val area = getArea(center, radius)
        val innerRegion = getArea(center, radius - 1)
        return area.minus(innerRegion)
    }
}
