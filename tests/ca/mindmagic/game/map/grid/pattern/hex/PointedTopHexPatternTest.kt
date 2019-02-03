package ca.mindmagic.game.map.grid.pattern.hex

import ca.mindmagic.game.map.grid.Coordinate
import org.junit.Assert.assertArrayEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest


import kotlin.test.*

class PointedTopHexPatternTest {

    val pointedHexPattern = PointedTopHexPattern()

    val origin = Coordinate(0, 0)


//    @ParameterizedTest
//    fun range(c1: Coordinate, c2: Coordinate, expectedRange: Int, description: String){
//
//    }

    @Test
    fun rangeSelf() {
        assertEquals(0, pointedHexPattern.range(origin, origin))
    }

    @Test
    fun rangeGreaterThanDeltaRowDeltaCol() {
        assertEquals(3, pointedHexPattern.range(Coordinate(0, 1), Coordinate(1, -2)))
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

    @Test
    fun neighborsOfOrigin() {
        val expected = setOf(Coordinate(0, 1), Coordinate(1, 0),
                Coordinate(1, -1), Coordinate(0, -1), Coordinate(-1, -1), Coordinate(-1, 0))
        assertEquals(expected, pointedHexPattern.neighborsOf(Coordinate(0, 0)))
    }

    @Test
    fun neighborsOfNegativeQuadrant() {
        val expected = setOf(Coordinate(0, 0), Coordinate(0, -1),
                Coordinate(-1, -2), Coordinate(-2, -1), Coordinate(-2, 0), Coordinate(-1, 0))
        assertEquals(expected, pointedHexPattern.neighborsOf(Coordinate(-1, -1)))
    }

    @Test
    fun neighborsOfOddRow() {
        val expected = setOf(Coordinate(0, 0), Coordinate(0, 1),
                Coordinate(1, 1), Coordinate(2, 1), Coordinate(2, 0), Coordinate(1, -1))
        assertEquals(expected, pointedHexPattern.neighborsOf(Coordinate(1, 0)))
    }

    @Test
    fun neighborsOfEvenRow() {
        val expected = setOf(Coordinate(-1, 1), Coordinate(0, 2),
                Coordinate(1, 1), Coordinate(1, 0), Coordinate(0, 0), Coordinate(-1, 0))
        assertEquals(expected, pointedHexPattern.neighborsOf(Coordinate(0, 1)))
    }

    @Test
    fun neighborsOfEvenRow2() {
        val result = pointedHexPattern.neighborsOf(Coordinate(2, 1))
        assertEquals(6, result.size)
        assertTrue(result.contains(Coordinate(3, 1)))
        assertTrue(result.contains(Coordinate(2, 2)))
        assertTrue(result.contains(Coordinate(1, 1)))
        assertTrue(result.contains(Coordinate(1, 0)))
        assertTrue(result.contains(Coordinate(3, 0)))
        assertTrue(result.contains(Coordinate(2, 0)))
    }

}