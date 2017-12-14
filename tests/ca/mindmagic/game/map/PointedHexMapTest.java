package ca.mindmagic.game.map;

import ca.mindmagic.game.map.grid.Koordinate;
import java.util.Arrays;
import org.junit.Test;

import static org.junit.Assert.*;

public class PointedHexMapTest {
  private HexMap map = new HexMap(4,4, HexMap.Orientation.POINTED_TOP);
  private HexMap.Orientation orientation = map.getOrientation();
  double height = Math.sqrt(0.75);

  Koordinate origin = new Koordinate(0,0);
  Koordinate positiveQuadrant = new Koordinate(5,4);
  Koordinate negativeQuadrant = new Koordinate(6,-1);
  Koordinate negPosQuadrant = new Koordinate(-2,3);
  Koordinate posNegQuadrant = new Koordinate(1,-4);

  Double[] vertices = {0.0, 1.0, height, -0.5, height, 0.5, 0.0, 1.0, -height, 0.5, -height, -0.5};
  Double[] scaledVertices = {0.0, 30.0, 26.0, -15.0, 26.0, 15.0, 0.0, 30.0, -26.0, 15.0, -26.0, -15.0};

  @Test
  public void zeroZeroEqualsCoordZeroZero() throws Exception {
    assertEquals(new Koordinate(0,0), map.coordinateOf(0,0));
  }

  @Test
  public void zeroOneEqualsCoordOneOne() throws Exception {
    assertEquals(new Koordinate(0, 1), map.coordinateOf(0,1));
  }

  @Test
  public void zeroTwoEqualsCoordOneTwo() throws Exception {
    assertEquals(new Koordinate(0,2), map.coordinateOf(0,2));
  }

  @Test
  public void twoZeroEqualsCoordTwoZero() throws Exception {
    assertEquals(new Koordinate(1,2), map.coordinateOf(1,1));
  }

  @Test
  public void oneOneEqualsCoordTwoOne() throws Exception {
    assertEquals(new Koordinate(2,1), map.coordinateOf(2,0));
  }

  @Test
  public void oneZeroEqualsCoordOneOne() throws Exception {
    assertEquals(new Koordinate(1,1), map.coordinateOf(1,0));
  }

  @Test
  public void negOneZeroEqualsCoordNegOneZero() throws Exception {
    assertEquals(new Koordinate(-1,0), map.coordinateOf(-1,0));
  }
 @Test
  public void zeroNegOneEqualsCoordNegOneZero() throws Exception {
    assertEquals(new Koordinate(0,-1), map.coordinateOf(0,-1));
  }

  @Test
  public void centerOfZeroZeroEqualsZeroZero() throws Exception {
    double expectedX = 0.0;
    double expectedY = 0.0;
    assertEquals(expectedX, map.centerOf(0,0)[0], 0.0);
    assertEquals(expectedY, map.centerOf(0,0)[1], 0.0);
  }

  @Test
  public void centerOfOneZeroEquals() throws Exception {
    double expectedX = height;
    double expectedY = 1.5;
    assertEquals(expectedX, map.centerOf(1,0)[0], 0.0);
    assertEquals(expectedY, map.centerOf(1,0)[1], 0.0);
  }

  @Test
  public void centerOfTwoZeroEquals() throws Exception {
    double expectedX = 0;
    double expectedY = 3.0;
    assertEquals(expectedX, map.centerOf(2,0)[0], 0.0);
    assertEquals(expectedY, map.centerOf(2,0)[1], 0.0);
  }

  @Test
  public void centerOfZeroOneEquals() throws Exception {
    double expectedX = 2*height;
    double expectedY = 0;
    assertEquals(expectedX, map.centerOf(0,1)[0], 0.0);
    assertEquals(expectedY, map.centerOf(0,1)[1], 0.0);
  }

  @Test
  public void centerOfZeroTwoEquals() throws Exception {
    double expectedX = 4*height;
    double expectedY = 0.0;
    assertEquals(expectedX, map.centerOf(0,2)[0], 0.0);
    assertEquals(expectedY, map.centerOf(0,2)[1], 0.0);
  }

  @Test
  public void centerOfOneOneEquals() throws Exception {
    double expectedX = 3*height;
    double expectedY = 1.5;
    assertEquals(expectedX, map.centerOf(1,1)[0], 0.0);
    assertEquals(expectedY, map.centerOf(1,1)[1], 0.0);
  }

  @Test
  public void coordinateExistAndReturnTrue() throws Exception {
    assertTrue(map.ifMapContains(new Koordinate(0,0)));
  }

  @Test
  public void coordinateDoesNotExist() throws Exception {
    assertFalse(map.ifMapContains(new Koordinate(-1,0)));
  }

  @Test
  public void origin_has_two_neighbors() throws Exception {
    assertEquals(2, map.neighborsOf(0,0).size());
  }

  @Test
  public void non_edge_hex_has_six_neighbors() throws Exception {
    assertEquals(6, map.neighborsOf(1,1).size());
  }

  @Test
  public void originVertices() throws Exception {
    assertTrue(Arrays.equals(vertices, orientation.verticesOf(origin)));
  }

  @Test
  public void verticesScale() throws Exception {
    Double[] scaled = orientation.verticesOf(origin.row, origin.column, 30);
    System.out.println(Arrays.toString(scaled));
    assertTrue(Arrays.equals(scaledVertices, scaled));
  }

  @Test
  public void conversionIsSymmetric() throws Exception {
    testConversionSymmetry(origin);
    testConversionSymmetry(positiveQuadrant);
    testConversionSymmetry(negativeQuadrant);
    testConversionSymmetry(posNegQuadrant);
    testConversionSymmetry(negPosQuadrant);
  }

  private void testConversionSymmetry(Koordinate mapCoordinate){
    assertEquals(mapCoordinate, orientation.
        mapCoordinateOf(orientation.gridCoordinateOf(mapCoordinate)));
  }
}