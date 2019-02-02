package ca.mindmagic.game.map.grid

import ca.mindmagic.game.map.grid.pattern.hex.PointedTopHexPattern
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GridTest {

    val grid = Grid(PointedTopHexPattern())

    @Test
    fun areaNegativeRadius() {
        assertEquals(emptySet(), grid.area(Coordinate(0, 0), -1), "Area with negative range should be empty")
    }

    @Test
    fun areaRadiusZero() {
        val result = grid.area(Coordinate(0, 0), 0)
        assertEquals(1, result.size)
        assertTrue(result.contains(Coordinate(0, 0)))
    }

    @Test
    fun areaRadiusOne() {
        assertEquals(7, grid.area(Coordinate(0, 0), 1).size)

    }

    @Test
    fun areaRadiusTwo() {
        val result = grid.area(Coordinate(0, 0), 2)
        assertEquals(1 + 6 + 12, result.size, result.toString())
    }

    @Test
    fun areaTest() {
        val result = grid.area(Coordinate(1, 2), 3)
        assertEquals(1 + 6 + 12 + 18, result.size, result.toString())
        assertTrue(result.contains(Coordinate(0, 0)))
        assertTrue(result.contains(Coordinate(-2, 1)))
        assertTrue(result.contains(Coordinate(4, 1)))
        assertTrue(result.contains(Coordinate(1, 2)))
    }

    @Test
    fun ringNegativeRadius() {
        assertEquals(emptySet(), grid.ring(Coordinate(1, -2), -1))
    }

    @Test
    fun ringRadiusZero() {
        val result = grid.ring(Coordinate(1, -2), 0)
        assertEquals(1, result.size)
        assertTrue(result.contains(Coordinate(1, -2)))
    }

    @Test
    fun ringRadiusOne() {
        val result = grid.ring(Coordinate(1, 1), 1)
        assertEquals(6, result.size)
        assertTrue(result.contains(Coordinate(1, 2)))
        assertTrue(result.contains(Coordinate(0, 2)))
        assertTrue(result.contains(Coordinate(2, 1)))
        assertTrue(result.contains(Coordinate(2, 2)))
        assertTrue(result.contains(Coordinate(1, 0)))
        assertTrue(result.contains(Coordinate(0, 1)))
    }

    @Test
    fun ringRadiusTwo() {
        val result = grid.ring(Coordinate(1, -2), 2)
        assertEquals(12, result.size)
        assertTrue(result.contains(Coordinate(-1, -1)))
        assertTrue(result.contains(Coordinate(0, 0)))
        assertTrue(result.contains(Coordinate(1, 0)))
        assertTrue(result.contains(Coordinate(2, 0)))
        assertTrue(result.contains(Coordinate(3, -1)))
    }

    @ParameterizedTest
    @MethodSource("")
    fun areaSizeTest(gridMap: GridMap, center: Coordinate, radius: Int, expectedSize: Int) {
        assertEquals(expectedSize, gridMap.area(center, radius).size)
    }

    @ParameterizedTest
    @MethodSource("")
    fun areaContainsTest(gridMap: GridMap, center: Coordinate, radius: Int, expectedToContain: Coordinate) {
        assertTrue(gridMap.area(center, radius).contains(expectedToContain))
    }

    @ParameterizedTest
    @MethodSource("")
    fun areaExclusionTest(gridMap: GridMap, center: Coordinate, radius: Int, expectedToContain: Coordinate) {
        assertTrue(gridMap.area(center, radius).contains(expectedToContain))
    }

}