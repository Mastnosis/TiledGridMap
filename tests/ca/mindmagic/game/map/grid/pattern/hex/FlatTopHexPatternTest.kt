package ca.mindmagic.game.map.grid.pattern.hex

import ca.mindmagic.game.map.grid.Coordinate
import org.junit.Test

import org.junit.Assert.*

class FlatTopHexPatternTest {

    val pattern = FlatTopHexPattern()

    val origin = Coordinate(0, 0)

    @Test
    fun range() {
        assertEquals(0, pattern.range(origin, origin))

        assertEquals(1, pattern.range(origin, Coordinate(-1, 0)))
        assertEquals(1, pattern.range(origin, Coordinate(-1, 1)))
        assertEquals(1, pattern.range(origin, Coordinate(0, 1)))
        assertEquals(1, pattern.range(origin, Coordinate(1, 0)))
        assertEquals(1, pattern.range(origin, Coordinate(0, -1)))
        assertEquals(1, pattern.range(origin, Coordinate(-1, -1)))

        assertEquals(2, pattern.range(origin, Coordinate(1, 1)))

        assertEquals(4, pattern.range(Coordinate(1, 1), Coordinate(-2, -1)))
        assertEquals(2, pattern.range(Coordinate(-2, -1), Coordinate(-2, 1)))
    }
}