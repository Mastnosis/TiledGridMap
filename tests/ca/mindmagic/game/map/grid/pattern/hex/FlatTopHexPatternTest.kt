package ca.mindmagic.game.map.grid.pattern.hex

import ca.mindmagic.game.map.grid.Coordinate
import org.junit.jupiter.api.Test

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class FlatTopHexPatternTest {


    @ParameterizedTest
    @MethodSource("rangeArguments")
    fun rangeTest(source: Coordinate, destination: Coordinate, expected: Int, description: String) {
        val pattern = FlatTopHexPattern()
        assertEquals(expected, pattern.range(source, destination), "Failed: $description")
    }

    @ParameterizedTest
    @MethodSource("rangeArguments")
    fun rangeReciprocal(loc1: Coordinate, loc2: Coordinate) {
        val pattern = FlatTopHexPattern()
        assertEquals(pattern.range(loc1, loc2), pattern.range(loc2, loc1))
    }

    @Test
    fun rangeGreaterThanDeltaRowDeltaCol() {
        assertEquals(3, FlatTopHexPattern().range(Coordinate(1, 1), Coordinate(-1, -1)))
    }

    @Test
    fun neighborsOfOrigin() {
        val expected = setOf(Coordinate(-1, -1), Coordinate(-1, 0), Coordinate(-1, 1),
                Coordinate(0, 1), Coordinate(1, 0), Coordinate(0, -1))
        assertEquals(expected, FlatTopHexPattern().neighborsOf(Coordinate(0, 0)))
    }

    @Test
    fun neighborsOfEvenCol() {
        val expected = setOf(Coordinate(0, 2), Coordinate(0, 3), Coordinate(1, 3),
                Coordinate(2, 2), Coordinate(1, 1), Coordinate(0, 1))
        assertEquals(expected, FlatTopHexPattern().neighborsOf(Coordinate(1, 2)))
    }

    @Test
    fun neighborsOfOddCol() {
        val expected = setOf(Coordinate(1, 1), Coordinate(2, 2), Coordinate(3, 2),
                Coordinate(3, 1), Coordinate(3, 0), Coordinate(2, 0))
        assertEquals(expected, FlatTopHexPattern().neighborsOf(Coordinate(2, 1)))
    }

    @Test
    fun neighborsOfNegQuadrant() {
        val expected = setOf(Coordinate(-2, -1), Coordinate(-1, 0), Coordinate(0, 0),
                Coordinate(0, -1), Coordinate(0, -2), Coordinate(-1, -2))
        assertEquals(expected, FlatTopHexPattern().neighborsOf(Coordinate(-1, -1)))
    }

    companion object {
        @JvmStatic
        fun rangeArguments(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(Coordinate(0, 0), Coordinate(0, 0), 0, "range to self"),

                    Arguments.of(Coordinate(0, 0), Coordinate(-1, 0), 1, "range to top neighbor"),
                    Arguments.of(Coordinate(0, 0), Coordinate(-1, 1), 1, "range to topright neighbor"),
                    Arguments.of(Coordinate(0, 0), Coordinate(0, 1), 1, "range to bottomright neighbor"),
                    Arguments.of(Coordinate(0, 0), Coordinate(1, 0), 1, "range to bottom neighbor"),
                    Arguments.of(Coordinate(0, 0), Coordinate(0, -1), 1, "range to bottomleft neighbor"),
                    Arguments.of(Coordinate(0, 0), Coordinate(-1, -1), 1, "range to topleft neighbor")

                    , Arguments.of(Coordinate(0, 0), Coordinate(1, 1), 2, "range (0,0) to (1,1)")

                    , Arguments.of(Coordinate(1, 1), Coordinate(-2, -1), 4, "range (0,0) to (1,1)")
                    , Arguments.of(Coordinate(-2, -1), Coordinate(-2, 1), 2, "range (0,0) to (1,1)")

            )
        }
    }
}