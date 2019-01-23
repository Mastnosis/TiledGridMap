package ca.mindmagic.game.map.grid.pattern.hex;


import ca.mindmagic.game.map.grid.Coordinate;
import ca.mindmagic.game.map.grid.Grid;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@RunWith(Parameterized.class)
public class NeighborsTest {

//    @Parameterized.Parameters(name = "{index}.{0} neighbors of {1}")
//    public static Collection data() {
//        return Arrays.asList(hexGrid_test_origin(),
//                hexMap_noWrap_origin(),
//                hexMap_noWrap_upper_right_corner(),
//                hexMap_noWrap_size_zero(),
//                hexMap_negative_dimensions(),
//                hexMap_noWrap_bottom_right_corner_even_rows(),
//                hexMap_noWrap_bottom_right_corner_odd_rows(),
//                hexMap_does_not_contain_location()
//        );
//    }
//
//    @Parameterized.Parameter
//    public Grid grid;
//
//    @Parameterized.Parameter(1)
//    public Coordinate target;
//
//    @Parameterized.Parameter(2)
//    public Collection<Coordinate> expectedResult;
//
//    @Test
//    public void verifyListOfNeighbors() {
//        Set<Coordinate> neighbors = grid.neighborsOf(target);
//        for (Coordinate neighbor : expectedResult
//        ) {
//            assertTrue("Does not contain expected neighbor: " + neighbor.toString(), neighbors.contains(neighbor));
//        }
//    }
//
//    @Test
//    public void verifyNumberOfNeighbors() {
//        assertEquals(expectedResult.size(), grid.neighborsOf(target).size());
//    }
//
//    ///////////////////////////////////////////////////////////////////////////////////////////////
//
//    private static Object[] hexGrid_test_origin() {
//        return new Object[]{new HexGrid(), coord(0, 0), Arrays.asList(
//                coord(0, -1),
//                coord(1, 0),
//                coord(1, 1),
//                coord(0, 1),
//                coord(-1, 0),
//                coord(-1, -1)
//        )};
//    }
//
//    private static Object[] hexMap_noWrap_origin() {
//        return new Object[]{new HexMap(10, 10), coord(0, 0), Arrays.asList(
//                coord(0, 1),
//                coord(1, 0)
//        )};
//    }
//
//    private static Object[] hexMap_noWrap_upper_right_corner() {
//        return new Object[]{new HexMap(10, 10), coord(0, 9), Arrays.asList(
//                coord(0, 8),
//                coord(1, 8),
//                coord(1, 9)
//        )};
//    }
//
//    private static Object[] hexMap_noWrap_bottom_right_corner_even_rows() {
//        return new Object[]{new HexMap(10, 10), coord(9, 9), Arrays.asList(
//                coord(8, 9),
//                coord(9, 8)
//        )};
//    }
//
//    private static Object[] hexMap_noWrap_bottom_right_corner_odd_rows() {
//        return new Object[]{new HexMap(9, 9), coord(8, 8), Arrays.asList(
//                coord(7, 8),
//                coord(7, 7),
//                coord(8, 7)
//        )};
//    }
//
//    private static Object[] hexMap_noWrap_size_zero() {
//        return new Object[]{new HexMap(0, 0), coord(0, 0), Arrays.asList()};
//    }
//
//    private static Object[] hexMap_negative_dimensions() {
//        return new Object[]{new HexMap(-10, -1), coord(0, 0), Arrays.asList()};
//    }
//
//    private static Object[] hexMap_does_not_contain_location() {
//        return new Object[]{new HexMap(10, 10), coord(-1, -13), Arrays.asList()};
//    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private static Coordinate coord(int row, int col) {
        return new Coordinate(row, col);
    }
}
