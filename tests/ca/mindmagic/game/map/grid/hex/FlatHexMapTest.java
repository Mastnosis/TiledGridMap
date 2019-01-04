package ca.mindmagic.game.map.grid.hex;

import ca.mindmagic.game.map.grid.Coordinate;
import java.util.Arrays;
import java.util.Set;

import ca.mindmagic.game.map.grid.hex.HexMap;
import javafx.geometry.Point2D;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlatHexMapTest {
    private HexMap map = new HexMap(4, 4, false, false, HexMap.Orientation.FLAT_TOP);
  private HexMap.Orientation orientation = map.getOrientation();
  double height = Math.sqrt(0.75);

  Double[] vertices = {-0.5, -height, 0.5, -height, 1.0, 0.0, 0.5, height, -0.5, height, -1.0, 0.0};
  Point2D[] points = {new Point2D(-0.5, -height), new Point2D(0.5, -height),
          new Point2D(1.0, 0), new Point2D(0.5, height), new Point2D(-0.5, height),
          new Point2D(-1, 0)};

  Coordinate origin = new Coordinate(0,0);
  Coordinate positiveQuadrant = new Coordinate(5,4);
  Coordinate negativeQuadrant = new Coordinate(6,-1);
  Coordinate negPosQuadrant = new Coordinate(-2,3);
  Coordinate posNegQuadrant = new Coordinate(1,-4);

  @Test
  public void zeroZeroEqualsCoordZeroZero() {
    assertEquals(new Coordinate(0,0), gridCoordinateOf(0,0));
    //assertEquals(new Koordinate(0,0), map.coordinateOf(0,0));
  }

  @Test
  public void zeroOneEqualsCoordOneOne() {
    assertEquals(new Coordinate(1, 1), gridCoordinateOf(0,1));
  }

  @Test
  public void zeroTwoEqualsCoordOneTwo() {
    assertEquals(new Coordinate(1,2), gridCoordinateOf(0,2));
  }

  @Test
  public void twoZeroEqualsCoordTwoZero() {
    assertEquals(new Coordinate(2,0), gridCoordinateOf(2,0));
  }
  @Test
  public void oneOneEqualsCoordTwoOne() {
    assertEquals(new Coordinate(2,1), gridCoordinateOf(1,1));
  }

  @Test
  public void centerOfZeroZeroEqualsZeroZero() {
    double expectedX = 0.0;
    double expectedY = 0.0;
    Point2D expected = new Point2D(expectedX, expectedY);
      assertEquals(expected, map.centerPointOf(0, 0));
  }

  @Test
  public void centerOfOneZeroEquals() {
    double expectedX = 0;
    double expectedY = 2.0*height;
    Point2D expected = new Point2D(expectedX, expectedY);
      assertEquals(expected, map.centerPointOf(1, 0));
  }

  @Test
  public void centerOfTwoZeroEquals() {
    double expectedX = 0;
    double expectedY = 4.0*height;
    Point2D expected = new Point2D(expectedX, expectedY);
      assertEquals(expected, map.centerPointOf(2, 0));
  }

  @Test
  public void centerOfZeroOneEquals() {
    double expectedX = 1.5;
    double expectedY = height;
    Point2D expected = new Point2D(expectedX, expectedY);
      assertEquals(expected, map.centerPointOf(0, 1));
  }

  @Test
  public void centerOfZeroTwoEquals() {
    double expectedX = 3.0;
    double expectedY = 0.0;
    Point2D expected = new Point2D(expectedX, expectedY);
      assertEquals(expected, map.centerPointOf(0, 2));
  }

  @Test
  public void conversionIsSymmetric() {
    testConversionSymmetry(origin);
    testConversionSymmetry(positiveQuadrant);
    testConversionSymmetry(negativeQuadrant);
    testConversionSymmetry(posNegQuadrant);
    testConversionSymmetry(negPosQuadrant);
  }

  @Test
  public void originVertices() {
    assertTrue(Arrays.equals(vertices, orientation.verticesOf(origin)));
    //assertEquals(points, orientation.verticesOf(origin));
  }

  @Test
  public void origin_has_two_neighbors() {
    assertEquals(2, map.neighborsOf(0,0).size());
  }

  @Test
  public void non_edge_hex_has_six_neighbors() {
    assertEquals(6, map.neighborsOf(1,1).size());
  }

  @Test public void getArea_returns_correct_hexes(){
      Set<Coordinate> area = map.area(1, 1, 1);
    assertTrue("top", area.contains(new Coordinate(0,1)));
    assertTrue("leftTop", area.contains(new Coordinate(1,0)));
    assertTrue("leftBottom", area.contains(new Coordinate(2,0)));
    assertTrue("center", area.contains(new Coordinate(1,1)));
    assertTrue("rightTop", area.contains(new Coordinate(1,2)));
    assertTrue("rightBottom", area.contains(new Coordinate(2,2)));
    assertTrue("Bottom", area.contains(new Coordinate(2,1)));
  }

  private void testConversionSymmetry(Coordinate mapCoordinate){
    assertEquals(mapCoordinate, orientation.
        mapCoordinateOf(orientation.gridCoordinateOf(mapCoordinate)));
  }

  private Coordinate gridCoordinateOf(int mapRow, int mapCol){
    return orientation.gridCoordinateOf(new Coordinate(mapRow, mapCol));
  }
}