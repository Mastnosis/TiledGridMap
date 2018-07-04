package ca.mindmagic.game.map;

import ca.mindmagic.game.map.grid.Coordinate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javafx.geometry.Point2D;
import org.junit.Test;

import static org.junit.Assert.*;

public class PointedHexMapTest {
    private HexMap.Orientation pointedTop = HexMap.Orientation.POINTED_TOP;
    private HexMap map = new HexMap(4, 4, pointedTop);
    private double height = Math.sqrt(0.75);

    Coordinate origin = new Coordinate(0, 0);
    Coordinate positiveQuadrant = new Coordinate(5, 4);
    Coordinate negativeQuadrant = new Coordinate(-6, -1);
    Coordinate negPosQuadrant = new Coordinate(-2, 3);
    Coordinate posNegQuadrant = new Coordinate(1, -4);

    Double[] vertices = {0.0, -1.0, height, -0.5, height, 0.5, 0.0, 1.0, -height, 0.5, -height, -0.5};
    Point2D[] points = {new Point2D(0, -1), new Point2D(height, -0.5)};
    Double[] scaledVertices = {0.0, -30.0, 26.0, -15.0, 26.0, 15.0, 0.0, 30.0, -26.0, 15.0, -26.0, -15.0};


    @Test
    public void zeroZeroEqualsCoordZeroZero() {
        assertEquals(new Coordinate(0, 0), map.mapToGridCoordinate(0, 0));
    }

    @Test
    public void zeroOneEqualsCoordOneOne() {
        assertEquals(new Coordinate(0, 1), map.mapToGridCoordinate(0, 1));
    }

    @Test
    public void zeroTwoEqualsCoordOneTwo() {
        assertEquals(new Coordinate(0, 2), map.mapToGridCoordinate(0, 2));
    }

    @Test
    public void twoZeroEqualsCoordTwoZero() {
        assertEquals(new Coordinate(1, 2), map.mapToGridCoordinate(1, 1));
    }

    @Test
    public void oneOneEqualsCoordTwoOne() {
        assertEquals(new Coordinate(2, 1), map.mapToGridCoordinate(2, 0));
    }

    @Test
    public void oneZeroEqualsCoordOneOne() {
        assertEquals(new Coordinate(1, 1), map.mapToGridCoordinate(1, 0));
    }

    @Test
    public void negOneZeroEqualsCoordNegOneZero() {
        assertEquals(new Coordinate(-1, 0), map.mapToGridCoordinate(-1, 0));
    }

    @Test
    public void zeroNegOneEqualsCoordNegOneZero() {
        assertEquals(new Coordinate(0, -1), map.mapToGridCoordinate(0, -1));
    }

    @Test
    public void centerOfZeroZeroEqualsZeroZero() {
        Point2D origin = new Point2D(0, 0);
        assertEquals(origin, map.centerOf(0, 0));
    }

    @Test
    public void centerOfOneZeroEquals() {
        Point2D origin = new Point2D(height, 1.5);
        assertEquals(origin, map.centerOf(1, 0));
    }

    @Test
    public void centerOfTwoZeroEquals() {
        Point2D origin = new Point2D(0, 3);
        assertEquals(origin, map.centerOf(2, 0));
    }

    @Test
    public void centerOfZeroOneEquals() {
        Point2D origin = new Point2D(2 * height, 0);
        assertEquals(origin, map.centerOf(0, 1));
    }

    @Test
    public void centerOfZeroTwoEquals() {
        Point2D origin = new Point2D(4 * height, 0);
        assertEquals(origin, map.centerOf(0, 2));
    }

    @Test
    public void centerOfOneOneEquals() {
        Point2D origin = new Point2D(3 * height, 1.5);
        assertEquals(origin, map.centerOf(1, 1));
    }

    @Test
    public void setNoWrapNeighborsOfOrigins() {
        Set<Coordinate> neighbors = new HashSet<Coordinate>();
        neighbors.add(new Coordinate(0, 1));
        neighbors.add(new Coordinate(1, 0));
        assertEquals(neighbors, map.neighborsOf(origin));
    }

    @Test
    public void setNoWrapNeighborsOfUpperRightCorner() {
        Set<Coordinate> neighbors = new HashSet<>();
        neighbors.add(new Coordinate(0, 2));
        neighbors.add(new Coordinate(1, 2));
        neighbors.add(new Coordinate(1, 3));
        assertEquals(neighbors, map.neighborsOf(new Coordinate(0, 3)));
    }

    @Test
    public void coordinateExistAndReturnTrue() {
        assertTrue(map.locationExistsOnMap(new Coordinate(0, 0)));
    }

    @Test
    public void coordinateDoesNotExist() {
        assertFalse(map.locationExistsOnMap(new Coordinate(-1, 0)));
    }

    @Test
    public void origin_has_two_neighbors() {
        assertEquals(2, map.neighborsOf(0, 0).size());
    }

    @Test
    public void non_edge_hex_has_six_neighbors() {
        assertEquals(6, map.neighborsOf(1, 1).size());
    }

    @Test
    public void originVertices() {
        Double[] calculated = pointedTop.verticesOf(origin);
        assertTrue(Arrays.equals(vertices, calculated));
    }

    //@Test
    //public void verticesScale() {
    //  Double[] scaled = pointedTop.verticesOf(origin.getRow(), origin.getCol(), 30);
    //  assertTrue(Arrays.equals(scaledVertices, scaled));
    //}

    @Test
    public void conversionIsSymmetric() {
        testConversionSymmetry(origin);
        testConversionSymmetry(positiveQuadrant);
        testConversionSymmetry(negativeQuadrant);
        testConversionSymmetry(posNegQuadrant);
        testConversionSymmetry(negPosQuadrant);
    }

    private void testConversionSymmetry(Coordinate mapCoordinate) {
        assertEquals(mapCoordinate, pointedTop.
                mapCoordinateOf(pointedTop.gridCoordinateOf(mapCoordinate)));
    }

    @Test
    public void horiz_wrapped_map_origin_has_four_neighbors() {
        HexMap wrappedMap = new HexMap(5, 5, false, true, pointedTop);
        Set<Coordinate> neighbors = wrappedMap.neighborsOf(0, 0);
        assertTrue(neighbors.size() == 4);
    }

    @Test
    public void horiz_wrapped_map_one_zero_has_six_neighbors() {
        HexMap wrappedMap = new HexMap(5, 5, false, true, pointedTop);
        Set<Coordinate> neighbors = wrappedMap.neighborsOf(1, 0);
        assertTrue(neighbors.size() == 6);
    }

    @Test
    public void area_around_origin_range_one_wrapped_horizontal() {
        HexMap wrappedMap = new HexMap(5, 5, false, true, pointedTop);
        Set<Coordinate> area = wrappedMap.getArea(0, 0, 1);
        assertTrue(area.size() == 5);
        assertTrue(area.contains(origin));
        assertTrue(area.contains(new Coordinate(0, 1)));
        assertTrue(area.contains(new Coordinate(1, 0)));
        assertTrue(area.contains(new Coordinate(0, 4)));
        assertTrue(area.contains(new Coordinate(1, 4)));
    }

    @Test
    public void area_around_origin_range_two_wrapped_horizontal() {
        HexMap wrappedMap = new HexMap(5, 5, false, true, pointedTop);
        Set<Coordinate> area = wrappedMap.getArea(0, 0, 2);
        assertTrue(area.size() == 12);
    }

    @Test
    public void test_angleOf() {
        double delta = 1.0;
        double opposite = 3.0;
        double adjacent = 4 * height;
        double temp = Math.atan(3 / (4 * height));
        Coordinate right = new Coordinate(0, 1);
        Coordinate bottomRight = new Coordinate(1, 0);
        Coordinate straightDown = new Coordinate(2, 0);
        Coordinate overDown = new Coordinate(1, 1);
        Coordinate overTwo = new Coordinate(2, 2);
        assertEquals(0.0, map.angleOf(origin, right), delta);
        assertEquals(60, map.angleOf(origin, bottomRight), delta);
        assertEquals(90, map.angleOf(origin, straightDown), delta);
        assertEquals(30, map.angleOf(origin, overDown), delta);
        //assertEquals(Math.toRadians(temp), map.angleOf(origin, overTwo), delta);
    }
}