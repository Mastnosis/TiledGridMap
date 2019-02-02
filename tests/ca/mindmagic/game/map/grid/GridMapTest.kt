package ca.mindmagic.game.map.grid

import ca.mindmagic.game.map.grid.pattern.hex.PointedTopHexPattern
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GridMapTest {

    @Test
    fun minWidthTest() {
        val map = GridMap(PointedTopHexPattern(), 0, -3)
        assertEquals(1, map.width)
    }

    @Test
    fun minHeightTest() {
        val map = GridMap(PointedTopHexPattern(), 0, -3)
        assertEquals(1, map.height)
    }

    @ParameterizedTest
    @MethodSource("neighborArguments")
    fun neighborsTest(gridMap: GridMap, location: Coordinate, expectedNeighbors: Collection<Coordinate>) {
        val neighbors = gridMap.neighborsOf(location)
        assertEquals(expectedNeighbors.size, neighbors.size)
        assertTrue(neighbors.containsAll(expectedNeighbors))
    }

    @ParameterizedTest
    @MethodSource("rangeArguments")
    fun rangeTest(gridMap: GridMap, source: Coordinate, target: Coordinate, expected: Int) {
        assertEquals(expected, gridMap.range(source, target))
    }

    companion object {

        val noWrapMap = GridMap(PointedTopHexPattern(), 8, 8)
        val wrappedHorzMap = GridMap(PointedTopHexPattern(), 8, 8, true, false)
        val wrappedVertMap = GridMap(PointedTopHexPattern(), 8, 8, false, true)
        val wrappedBothMap = GridMap(PointedTopHexPattern(), 8, 8, true, true)

        @JvmStatic
        fun neighborArguments(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(noWrapMap, Coordinate(1, 1),
                            setOf(Coordinate(0, 2), Coordinate(1, 2), Coordinate(2, 2),
                                    Coordinate(2, 1), Coordinate(1, 0), Coordinate(0, 1))),
                    Arguments.of(noWrapMap, Coordinate(0, 0),
                            setOf(Coordinate(0, 1), Coordinate(1, 0))),
                    Arguments.of(noWrapMap, Coordinate(1, 0),
                            setOf(Coordinate(0, 0), Coordinate(0, 1), Coordinate(1, 1),
                                    Coordinate(2, 1), Coordinate(2, 0))),
                    Arguments.of(noWrapMap, Coordinate(2, 0),
                            setOf(Coordinate(1, 0), Coordinate(2, 1), Coordinate(3, 0))),

                    // Wrapped Horizontal tests
                    Arguments.of(wrappedHorzMap, Coordinate(0, 0),
                            setOf(Coordinate(0, 1), Coordinate(1, 0), Coordinate(1, 7),
                                    Coordinate(0, 7)))
            )
        }

        @JvmStatic
        fun rangeArguments(): Stream<Arguments> {
            val source = Coordinate(1, 2)
            val target = Coordinate(7, 7)
            return Stream.of(
                    Arguments.of(noWrapMap, source, target, 8),
                    Arguments.of(wrappedHorzMap, source, target, 6),
                    Arguments.of(wrappedVertMap, source, target, 6),
                    Arguments.of(wrappedBothMap, source, target, 4)
            )
        }
    }
}