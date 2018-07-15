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

import java.util.Arrays
import java.util.HashMap
import java.util.function.IntBinaryOperator
import java.util.function.ToDoubleBiFunction
import java.util.stream.Collectors

import javafx.geometry.Point2D

class HexMap(height: Int, width: Int, wrapHeight: Boolean, wrapWidth: Boolean, val orientation: Orientation) : GridMap(HexGrid(), height, width, wrapHeight, wrapWidth) {

    internal var locations: MutableMap<Coordinate, Set<Coordinate>>

    @JvmOverloads constructor(height: Int, width: Int, orientation: Orientation = Orientation.POINTED_TOP) : this(height, width, false, false, orientation) {}

    init {
        this.width = width
        this.height = height

        locations = HashMap()
        for (i in 0 until height) {
            for (j in 0 until width) {
                val c = Coordinate(i, j)
                locations[c] = calcNeighborsOf(c)
            }
        }

    }

    override fun neighborsOf(mapLocation: Coordinate): Set<Coordinate> {
        return calcNeighborsOf(mapLocation)
    }

    override fun neighborsOf(mapRow: Int, mapCol: Int): Set<Coordinate> {
        return neighborsOf(Coordinate(mapRow, mapCol))
    }

    @Deprecated("")
    private fun calcNeighborsOf(mapLocation: Coordinate): Set<Coordinate> {
        return calcNeighborsOf(mapLocation.row, mapLocation.col)
    }

    @Deprecated("")
    private fun calcNeighborsOf(mapRow: Int, mapCol: Int): Set<Coordinate> {
        val neighbors = grid.neighborsOf(mapToGridCoordinate(Coordinate(mapRow, mapCol)))
                .stream()
                .map<Coordinate>(Function<Coordinate, Coordinate> { this.orientation.mapCoordinateOf(it) })
                .filter(Predicate<Coordinate> { this.locationExistsOnMap(it) })
                .collect<Set<Coordinate>, Any>(Collectors.toSet())
        if (wrapHorizontal && orientation == Orientation.POINTED_TOP) {
            if (mapCol == 0) {
                for (gc in grid.neighborsOf(orientation.gridCoordinateOf(mapRow, mapCol))) {
                    val (row, col) = gridToMapCoordinate(gc)
                    if (col == -1) {
                        val wrapped = Coordinate(row, width - 1)
                        if (locationExistsOnMap(wrapped)) {
                            neighbors.add(wrapped)
                        }
                    }
                    if (col == width) {
                        val wrapped = Coordinate(row, 0)
                        if (locationExistsOnMap(wrapped)) {
                            neighbors.add(wrapped)
                        }
                    }
                }
            }
        }
        return neighbors
    }

    override fun range(source: Coordinate, target: Coordinate): Int {
        val gridSource = orientation.gridCoordinateOf(source)
        val gridTarget = orientation.gridCoordinateOf(target)
        return grid.range(gridSource, gridTarget)
    }

    override fun getLocations(): Set<Coordinate> {
        return locations.keys
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

    override fun centerOf(row: Int, col: Int): Point2D {
        return orientation.centerOf(row, col)
    }

    enum class Orientation private constructor(
            internal var toGridRow: IntBinaryOperator,
            internal var toGridCol: IntBinaryOperator,
            internal var toMapRow: IntBinaryOperator,
            internal var toMapCol: IntBinaryOperator,
            internal var findCenterX: ToDoubleBiFunction<Int, Int>,
            internal var findCenterY: ToDoubleBiFunction<Int, Int>,
            internal var xValues: DoubleArray,
            internal var yValues: DoubleArray
    ) {
        POINTED_TOP(
                { x, y -> x },
                { x, y -> y + Math.ceil(x / 2.0).toInt() },
                { x, y -> x },
                { x, y -> y - Math.ceil(x / 2.0).toInt() },
                { x, y -> y!!.toDouble() * 2.0 * Math.sqrt(0.75) + x!! % 2 * Math.sqrt(0.75) },
                { x, y -> 1.5 * x!! },
                doubleArrayOf(0.0, H, H, 0.0, -H, -H),
                doubleArrayOf(-1.0, -0.5, 0.5, 1.0, 0.5, -0.5)
        ),
        FLAT_TOP(
                { x, y -> x + Math.ceil(y / 2.0).toInt() },
                { x, y -> y },
                { x, y -> x - Math.ceil(y / 2.0).toInt() },
                { x, y -> y },
                { x, y -> 1.5 * y!! },
                { x, y -> 2.0 * Math.sqrt(0.75) * x!!.toDouble() + y!! % 2 * Math.sqrt(0.75) },
                doubleArrayOf(-0.5, 0.5, 1.0, 0.5, -0.5, -1.0),
                doubleArrayOf(-H, -H, 0.0, H, H, 0.0)
        );

        internal fun gridCoordinateOf(mapCoordinate: Coordinate): Coordinate {
            return gridCoordinateOf(mapCoordinate.row, mapCoordinate.col)
        }

        internal fun gridCoordinateOf(mapRow: Int, mapCol: Int): Coordinate {
            return Coordinate(toGridRow.applyAsInt(mapRow, mapCol), toGridCol.applyAsInt(mapRow, mapCol))
        }

        internal fun mapCoordinateOf(gridCoordinate: Coordinate): Coordinate {
            return mapCoordinateOf(gridCoordinate.row, gridCoordinate.col)
        }

        internal fun mapCoordinateOf(gridRow: Int, gridCol: Int): Coordinate {
            return Coordinate(
                    toMapRow.applyAsInt(gridRow, gridCol),
                    toMapCol.applyAsInt(gridRow, gridCol)
            )
        }

        internal fun centerOf(mapRow: Int, mapCol: Int): Point2D {
            val x: Double
            val y: Double
            x = findCenterX.applyAsDouble(mapRow, mapCol)
            y = findCenterY.applyAsDouble(mapRow, mapCol)
            return Point2D(x, y)
        }

        internal fun centerOf(coordinate: Coordinate): Point2D {
            return centerOf(coordinate.row, coordinate.col)
        }

        internal fun verticesOf(mapCoordinate: Coordinate): Array<Double> {
            return verticesOf(mapCoordinate.row, mapCoordinate.col)
        }

        internal fun verticesOf(mapRow: Int, mapCol: Int): Array<Double> {
            val center = centerOf(mapRow, mapCol)
            val xValues = Arrays.stream(this.xValues).map { x -> x + center.x }.toArray()
            val yValues = Arrays.stream(this.yValues).map { y -> y + center.y }.toArray()
            val points = 6
            val vertices = arrayOfNulls<Double>(points * 2)
            for (i in 0 until points) {
                val index = i * 2
                vertices[index] = xValues[i]
                vertices[index + 1] = yValues[i]
            }
            return vertices
        }

        fun verticesOf(mapRow: Int, mapCol: Int, sideLength: Int): Array<Double> {
            return Arrays.stream(verticesOf(mapRow, mapCol)).map { d -> d!! * sideLength }.toArray(Double[]::new  /* Currently unsupported in Kotlin */)
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

        private val H = Math.sqrt(0.75)
    }
}
