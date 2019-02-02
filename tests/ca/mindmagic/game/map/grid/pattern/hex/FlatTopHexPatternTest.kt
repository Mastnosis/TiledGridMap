package ca.mindmagic.game.map.grid.pattern.hex

import ca.mindmagic.game.map.grid.Coordinate

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class FlatTopHexPatternTest {


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

    @ParameterizedTest
    @MethodSource("rangeArguments")
    fun rangeTest(source: Coordinate, destination: Coordinate, expected: Int, description: String) {
        val pattern = FlatTopHexPattern()
        assertEquals(expected, pattern.range(source, destination), "Failed: $description")
    }

}