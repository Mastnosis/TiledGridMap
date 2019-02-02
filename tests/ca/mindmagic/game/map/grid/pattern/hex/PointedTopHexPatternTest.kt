package ca.mindmagic.game.map.grid.pattern.hex

import ca.mindmagic.game.map.grid.Coordinate
import org.junit.Assert.assertArrayEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest


import kotlin.test.*

class PointedTopHexPatternTest {

    val pointedHexPattern = PointedTopHexPattern()

    val origin = Coordinate(0, 0)

    @Test
    fun rangeSelf() {
        assertEquals(0, pointedHexPattern.range(origin, origin))
    }

    @Test
    fun rangeGreaterThanDeltaRowDeltaCol() {
        assertEquals(3, pointedHexPattern.range(Coordinate(0, 1), Coordinate(1, -2)))
    }

    @Test
    fun rangeNeighbors() {
        assertEquals(1, pointedHexPattern.range(origin, Coordinate(0, 1)))
        assertEquals(1, pointedHexPattern.range(origin, Coordinate(-1, 0)))
        assertEquals(1, pointedHexPattern.range(origin, Coordinate(1, 0)))
        assertEquals(1, pointedHexPattern.range(origin, Coordinate(1, -1)))
        assertEquals(1, pointedHexPattern.range(origin, Coordinate(0, -1)))
        assertEquals(1, pointedHexPattern.range(origin, Coordinate(-1, -1)))
    }


    @Test
    fun rangeToNegCoordinate() {
        assertEquals(2, pointedHexPattern.range(origin, Coordinate(2, -1)))
    }

    @Test
    fun rangeToNegNegCoordinate() {
        assertEquals(1, pointedHexPattern.range(origin, Coordinate(0, -1)))
    }

    @Test
    fun rangeGivenNonOrigin() {
        val c = Coordinate(1, 1)
        assertEquals(1, pointedHexPattern.range(c, Coordinate(0, 1)))
        assertEquals(1, pointedHexPattern.range(c, Coordinate(0, 2)))
        assertEquals(1, pointedHexPattern.range(c, Coordinate(1, 2)))
        assertEquals(1, pointedHexPattern.range(c, Coordinate(2, 2)))
        assertEquals(1, pointedHexPattern.range(c, Coordinate(2, 1)))
        assertEquals(1, pointedHexPattern.range(c, Coordinate(1, 0)))
        assertEquals(3, pointedHexPattern.range(c, Coordinate(1, -2)))
        assertEquals(4, pointedHexPattern.range(c, Coordinate(2, -2)))
    }

    @Test
    fun rangeInNegQuadrant() {
        val c = Coordinate(-1, 0)
        assertEquals(1, pointedHexPattern.range(c, Coordinate(0, 0)))
        assertEquals(2, pointedHexPattern.range(c, Coordinate(0, 2)))
        assertEquals(3, pointedHexPattern.range(c, Coordinate(1, 2)))
        assertEquals(3, pointedHexPattern.range(c, Coordinate(2, 2)))
        assertEquals(3, pointedHexPattern.range(c, Coordinate(2, 1)))
        assertEquals(2, pointedHexPattern.range(c, Coordinate(1, 0)))
        assertEquals(1, pointedHexPattern.range(c, Coordinate(-1, -1)))
        assertEquals(2, pointedHexPattern.range(c, Coordinate(-2, -1)))
    }

    @Test
    fun verticesOfOrigin() {
        val vertices = pointedHexPattern.verticesOf(origin, 30.0)
        assertArrayEquals(doubleArrayOf(0.0, -30.0, 26.0, -15.0, 26.0, 15.0, 0.0, 30.0, -26.0, 15.0, -26.0, -15.0), vertices.toDoubleArray(), 0.1)
    }
}