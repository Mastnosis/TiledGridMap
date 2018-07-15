package ca.mindmagic.game.map.grid.hex;


import ca.mindmagic.game.map.grid.Coordinate;
import ca.mindmagic.game.map.grid.Grid;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class NeighborsTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
//                {10, 10, coord(0,0)}
        });
    }

    public Grid grid;
    public Coordinate coord;
    public Collection<Coordinate> expectedResult;

    public NeighborsTest(Grid gridToTest, Coordinate center, Collection<Coordinate> expectedNeighbors) {

    }
}
