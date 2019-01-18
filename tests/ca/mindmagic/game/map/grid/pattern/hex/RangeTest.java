package ca.mindmagic.game.map.grid.pattern.hex;


import ca.mindmagic.game.map.grid.Coordinate;
import ca.mindmagic.game.map.grid.Grid;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.*;

import java.util.Arrays;
import java.util.Collection;

public class RangeTest {

    @Parameters(name = "{index}.{0} neighbors of {1}")
    public static Collection data() {
        return Arrays.asList(
        );
    }

    @Parameter
    public Grid grid;

    @Parameter(1)
    public Coordinate c1;

    @Parameter(2)
    public Coordinate c2;

    @Parameter(3)
    public int expectedRange;

    @Test
    public void confirmRange() {
        assertEquals(expectedRange, grid.range(c1, c2));
    }

    @Test
    public void reciprocal() {
        assertEquals(expectedRange, grid.range(c2, c1));
    }
}
