package ca.mindmagic.game.map.grid.hex

/*
  	 _   _   _
 	/ \_/ \_/ \
   \_/ \_/ \_/
   / \_/ \_/ \
   \_/ \_/ \_/
   / \_/ \_/ \

   rows and columns based on pointed orientation below or flat orientation as above

    /\  /\  /\  /\  /\  /\   Y
   /  \/  \/  \/  \/  \/  \
   |   |   |   |   |   |   |
   |X0 | 1 | 2 |   |   |   | 0
   \  /\  /\  /\  /\  /\  /
    \/  \/  \/  \/  \/  \/
     |   |   |   |   |   |
     | 0 | 1 | 2 |   |   |   1
    /\  /\  /\  /\  /\  /\
   /  \/  \/  \/  \/  \/  \
   |   |   |   |   |   |   |
   | 0 | 1 | 2 |   |   |   | 2
   \  /\  /\  /\  /\  /\  /
    \/  \/  \/  \/  \/  \/

 */

import ca.mindmagic.game.map.grid.Coordinate
import ca.mindmagic.game.map.grid.GridMap
import ca.mindmagic.game.map.grid.pattern.HexPattern

import java.util.Arrays
import java.util.function.IntBinaryOperator
import java.util.function.ToDoubleBiFunction

import javafx.geometry.Point2D

class HexMap @JvmOverloads constructor(height: Int, width: Int,
                                       wrapHeight: Boolean = false, wrapWidth: Boolean = false,
                                       val orientation: Orientation = Orientation.POINTED_TOP)
    : GridMap(HexPattern(), height, width, wrapHeight, wrapWidth) {

    override fun neighborsOf(mapLocation: Coordinate): Set<Coordinate> {
        return calcNeighborsOf(mapLocation)
    }

    override fun neighborsOf(mapRow: Int, mapCol: Int): Set<Coordinate> {
        return neighborsOf(Coordinate(mapRow, mapCol))
    }

    private fun calcNeighborsOf(mapLocation: Coordinate): Set<Coordinate> {
        return calcNeighborsOf(mapLocation.row, mapLocation.col)
    }

    private fun calcNeighborsOf(mapRow: Int, mapCol: Int): Set<Coordinate> {
        if (!contains(mapRow, mapCol)) return emptySet()
        return pattern.neighborsOf(mapToGridCoordinate(mapRow, mapCol))
                .map { gridToMapCoordinate(it) }
                .map { wrapIfNeeded(it) }
                .filter { contains(it) }
                .toSet()
    }

    override fun range(source: Coordinate, target: Coordinate): Int {
        val gridSource = orientation.gridCoordinateOf(source)
        val gridTarget = orientation.gridCoordinateOf(target)
        return 1 //grid.range(gridSource, gridTarget)
    }


    public override fun mapToGridCoordinate(row: Int, col: Int): Coordinate {
        return orientation.gridCoordinateOf(row, col)
    }

    override fun gridToMapCoordinate(xAxis: Int, yAxis: Int): Coordinate {
        return orientation.mapCoordinateOf(xAxis, yAxis)
    }

    override fun verticesOf(row: Int, col: Int): Array<Double> {
        return orientation.verticesOf(row, col)
    }

    override fun centerPointOf(row: Int, col: Int): Point2D {
        return orientation.centerOf(row, col)
    }

    override fun toString(): String {
        return "HexMap($height, $width, $orientation)"
    }

    enum class Orientation constructor(
            private val toGridRow: (Int, Int) -> Int,
            private val toGridCol: (Int, Int) -> Int,
            private val toMapRow: (Int, Int) -> Int,
            private val toMapCol: (Int, Int) -> Int,
            private val findCenterX: (Int, Int) -> Double,
            private val findCenterY: (Int, Int) -> Double,
            private val xValues: DoubleArray,
            private val yValues: DoubleArray
    ) {
        POINTED_TOP(
                { x, y -> x },
                { x, y -> y + Math.ceil(x / 2.0).toInt() },
                { x, y -> x },
                { x, y -> y - Math.ceil(x / 2.0).toInt() },
                { x, y -> y * 2.0 * Math.sqrt(0.75) + x % 2 * Math.sqrt(0.75) },
                { x, y -> 1.5 * x },
                doubleArrayOf(0.0, H, H, 0.0, -H, -H),
                doubleArrayOf(-1.0, -0.5, 0.5, 1.0, 0.5, -0.5)
        ),
        FLAT_TOP(
                { x, y -> x + Math.ceil(y / 2.0).toInt() },
                { x, y -> y },
                { x, y -> x - Math.ceil(y / 2.0).toInt() },
                { x, y -> y },
                { x, y -> 1.5 * y },
                { x, y -> 2.0 * Math.sqrt(0.75) * x + y % 2 * Math.sqrt(0.75) },
                doubleArrayOf(-0.5, 0.5, 1.0, 0.5, -0.5, -1.0),
                doubleArrayOf(-H, -H, 0.0, H, H, 0.0)
        );


        fun gridCoordinateOf(mapCoordinate: Coordinate): Coordinate {
            return gridCoordinateOf(mapCoordinate.row, mapCoordinate.col)
        }

        fun gridCoordinateOf(mapRow: Int, mapCol: Int): Coordinate {
            return Coordinate(toGridRow(mapRow, mapCol), toGridCol(mapRow, mapCol))
        }

        fun mapCoordinateOf(gridCoordinate: Coordinate): Coordinate {
            return mapCoordinateOf(gridCoordinate.row, gridCoordinate.col)
        }

        fun mapCoordinateOf(gridRow: Int, gridCol: Int): Coordinate {
            return Coordinate(
                    toMapRow(gridRow, gridCol),
                    toMapCol(gridRow, gridCol)
            )
        }

        fun centerOf(mapRow: Int, mapCol: Int): Point2D {
            val x: Double
            val y: Double
            x = findCenterX(mapRow, mapCol)
            y = findCenterY(mapRow, mapCol)
            return Point2D(x, y)
        }

        fun centerOf(coordinate: Coordinate): Point2D {
            return centerOf(coordinate.row, coordinate.col)
        }

        fun verticesOf(mapCoordinate: Coordinate): Array<Double> {
            return verticesOf(mapCoordinate.row, mapCoordinate.col)
        }

        fun verticesOf(mapRow: Int, mapCol: Int): Array<Double> {
            val center = centerOf(mapRow, mapCol)
            val xList = xValues.map { x -> x + center.x }
            val yList = yValues.map { y -> y + center.y }
            val combinedList = ArrayList<Double>()
            for (i in 0 until xValues.size) {
                combinedList.add(xList[i])
                combinedList.add(yList[i])
            }
            return combinedList.toTypedArray()
        }

        fun verticesOf(mapRow: Int, mapCol: Int, sideLength: Int): Array<Double> {
            return verticesOf(mapRow, mapCol).map { d -> d * sideLength }.toTypedArray()
        }

        fun verticesOf(location: Coordinate, sideLength: Int): Array<Double> {
            return verticesOf(location.row, location.col, sideLength)
        }
    }

    companion object {

        fun verticesOf(row: Int, col: Int, orientation: Orientation): Array<Double> {
            return orientation.verticesOf(row, col)
        }

        /**
         * Orientation - for a square map, there are two primary orientations for a hex grid; either
         * pointed top or flat top. These alter the calculation of row and column indexes as well as the
         * hexagon vertices.
         */

        val H = Math.sqrt(0.75)

    }
}
