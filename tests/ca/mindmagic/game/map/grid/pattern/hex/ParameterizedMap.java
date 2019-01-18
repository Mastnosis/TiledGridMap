package ca.mindmagic.game.map.grid.pattern.hex;

import ca.mindmagic.game.map.grid.Coordinate;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ParameterizedMap {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {10, 10, coord(0, 0)}
        });
    }

    public ParameterizedMap(int height, int width) {
    }

    private static Coordinate coord(int row, int col) {
        return new Coordinate(row, col);
    }
}
