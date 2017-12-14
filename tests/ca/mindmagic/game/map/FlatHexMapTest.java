package ca.mindmagic.game.map;

import ca.mindmagic.game.map.grid.Koordinate;
import java.util.Arrays;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlatHexMapTest {
  private HexMap map = new HexMap(4,4, HexMap.Orientation.FLAT_TOP);
  private HexMap.Orientation orientation = map.getOrientation();
  double height = Math.sqrt(0.75);

  Double[] vertices = {-0.5, -height, 0.5, -height, 1.0, 0.0, 0.5, height, -0.5, height, -1.0, 0.0};

  Koordinate origin = new Koordinate(0,0);
  Koordinate positiveQuadrant = new Koordinate(5,4);
  Koordinate negativeQuadrant = new Koordinate(6,-1);
  Koordinate negPosQuadrant = new Koordinate(-2,3);
  Koordinate posNegQuadrant = new Koordinate(1,-4);

  @Test
  public void zeroZeroEqualsCoordZeroZero() throws Exception {
    assertEquals(new Koordinate(0,0), gridCoordinateOf(0,0));
    //assertEquals(new Koordinate(0,0), map.coordinateOf(0,0));
  }

  @Test
  public void zeroOneEqualsCoordOneOne() throws Exception {
    assertEquals(new Koordinate(1, 1), gridCoordinateOf(0,1));
  }

  @Test
  public void zeroTwoEqualsCoordOneTwo() throws Exception {
    assertEquals(new Koordinate(1,2), gridCoordinateOf(0,2));
  }

  @Test
  public void twoZeroEqualsCoordTwoZero() throws Exception {
    assertEquals(new Koordinate(2,0), gridCoordinateOf(2,0));
  }
  @Test
  public void oneOneEqualsCoordTwoOne() throws Exception {
    assertEquals(new Koordinate(2,1), gridCoordinateOf(1,1));
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
    double expectedX = 0;
    double expectedY = 2.0*height;
    assertEquals(expectedX, map.centerOf(1,0)[0], 0.0);
    assertEquals(expectedY, map.centerOf(1,0)[1], 0.0);
  }

  @Test
  public void centerOfTwoZeroEquals() throws Exception {
    double expectedX = 0;
    double expectedY = 4.0*height;
    assertEquals(expectedX, map.centerOf(2,0)[0], 0.0);
    assertEquals(expectedY, map.centerOf(2,0)[1], 0.0);
  }

  @Test
  public void centerOfZeroOneEquals() throws Exception {
    double expectedX = 1.5;
    double expectedY = height;
    assertEquals(expectedX, map.centerOf(0,1)[0], 0.0);
    assertEquals(expectedY, map.centerOf(0,1)[1], 0.0);
  }

  @Test
  public void centerOfZeroTwoEquals() throws Exception {
    double expectedX = 3.0;
    double expectedY = 0.0;
    assertEquals(expectedX, map.centerOf(0,2)[0], 0.0);
    assertEquals(expectedY, map.centerOf(0,2)[1], 0.0);
  }

  @Test
  public void conversionIsSymmetric() throws Exception {
    testConversionSymmetry(origin);
    testConversionSymmetry(positiveQuadrant);
    testConversionSymmetry(negativeQuadrant);
    testConversionSymmetry(posNegQuadrant);
    testConversionSymmetry(negPosQuadrant);
  }

  @Test
  public void originVertices() throws Exception {
    assertTrue(Arrays.equals(vertices, orientation.verticesOf(origin)));
  }

  @Test
  public void origin_has_two_neighbors() throws Exception {
    assertEquals(2, map.neighborsOf(0,0).size());
  }

  @Test
  public void non_edge_hex_has_six_neighbors() throws Exception {
    assertEquals(6, map.neighborsOf(1,1).size());
  }

  private void testConversionSymmetry(Koordinate mapCoordinate){
    assertEquals(mapCoordinate, orientation.
        mapCoordinateOf(orientation.gridCoordinateOf(mapCoordinate)));
  }

  private Koordinate gridCoordinateOf(int mapRow, int mapCol){
    return orientation.gridCoordinateOf(new Koordinate(mapRow, mapCol));
  }
}