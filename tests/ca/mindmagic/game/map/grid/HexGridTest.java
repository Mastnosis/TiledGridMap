package ca.mindmagic.game.map.grid;

import org.junit.Test;

import static org.junit.Assert.*;

public class HexGridTest {

  private HexGrid grid = new HexGrid(30);

  private Coordinate origin = new Coordinate(0, 0);
  private Coordinate positiveQuadrant = new Coordinate(5, 4);
  private Coordinate negativeQuadrant = new Coordinate(6, -1);
  private Coordinate negPosQuadrant = new Coordinate(-2, 3);
  private Coordinate posNegQuadrant = new Coordinate(1, -4);

  @Test
  public void hexIsRangeZeroFromSelf() {
    Coordinate c1 = new Coordinate(3,4);
    assertEquals(0, grid.range(c1, c1));
    Coordinate c2 = new Coordinate(0,0);
    assertEquals(0, grid.range(c2, c2));
    Coordinate c3 = new Coordinate(-1,-1);
    assertEquals(0, grid.range(c3, c3));
    Coordinate c4 = new Coordinate(-4,5);
    assertEquals(0, grid.range(c4, c4));
  }

  @Test
  public void originIsRangeOneFromAllNeighbors() {
    Coordinate origin = new Coordinate(0,0);
    Coordinate topLeft = HexGrid.Direction.A.move(origin);
    //Koordinate topLeft = new Koordinate(-1,0);
    Coordinate topRight = new Coordinate(0,1);
    Coordinate left = new Coordinate(1,1);
    Coordinate right = new Coordinate(1,0);
    Coordinate bottomLeft = new Coordinate(0,-1);
    Coordinate bottomRight = new Coordinate(-1,-1);
    assertEquals("Top Left is not adjacent", 1, grid.range(origin, topLeft));
    assertEquals("Top Right is not adjacent", 1, grid.range(origin, topRight));
    assertEquals("Left is not adjacent", 1, grid.range(origin, left));
    assertEquals("Right is not adjacent", 1, grid.range(origin, right));
    assertEquals("Bottom Left is not adjacent", 1, grid.range(origin, bottomLeft));
    assertEquals("Bottom Right is not adjacent", 1, grid.range(origin, bottomRight));
  }

  @Test
  public void oneOneIsRangeOneFromAllNeighbors() {
    Coordinate origin = new Coordinate(1,1);
    Coordinate topLeft = new Coordinate(0,1);
    Coordinate topRight = new Coordinate(1,2);
    Coordinate left = new Coordinate(2,2);
    Coordinate right = new Coordinate(2,1);
    Coordinate bottomLeft = new Coordinate(1,0);
    Coordinate bottomRight = new Coordinate(0,0);
    assertEquals("Top Left is not adjacent", 1, grid.range(origin, topLeft));
    assertEquals("Top Right is not adjacent", 1, grid.range(origin, topRight));
    assertEquals("Left is not adjacent", 1, grid.range(origin, left));
    assertEquals("Right is not adjacent", 1, grid.range(origin, right));
    assertEquals("Bottom Left is not adjacent", 1, grid.range(origin, bottomLeft));
    assertEquals("Bottom Right is not adjacent", 1, grid.range(origin, bottomRight));
  }

  //@Test
  //public void adjacentHexesShareVertices() {
  //  Koordinate source = new Koordinate(3, 4);
  //  Koordinate adjacent = new Koordinate(3, 3);
  //  Point p1 = grid.verticesOf(source)[5];
  //  Point p2 = grid.verticesOf(adjacent)[1];
  //  assertEquals(p1, p2);
  //}

  @Test
  public void neighborsRadiusOneRangeOne() {
    Coordinate center = new Coordinate(0,0);
  }

  @Test
  public void numberOfNeighborsEqualsSix() {
    assertEquals(6, grid.neighborsOf(new Coordinate(0,0)).size());
  }

  @Test public void getNeighborsReturnsRangeOne(){
    Coordinate center = new Coordinate(0,0);
    grid.neighborsOf(center).forEach(e -> assertRange(center, e, 1));
  }

  @Test public void number_of_neighbors_equal_to_six(){
    assertEquals(6, grid.neighborsOf(0,0).size());
  }

  @Test public void number_of_neighbors_radius_neg_equal_to_zero(){
    assertEquals(0, grid.getArea(origin, -1).size());
    assertEquals(0, grid.getArea(positiveQuadrant, -1).size());
    assertEquals(0, grid.getArea(negativeQuadrant, -1).size());
    assertEquals(0, grid.getArea(negPosQuadrant, -1).size());
  }

  @Test public void number_of_neighbors_radius_0_equal_to_one(){
    assertEquals(1, grid.getArea(new Coordinate(0,0), 0).size());
    assertEquals(1, grid.getArea(new Coordinate(0,0), 0).size());
    assertEquals(1, grid.getArea(new Coordinate(0,0), 0).size());
    assertEquals(1, grid.getArea(new Coordinate(0,0), 0).size());
    assertEquals(1, grid.getArea(new Coordinate(0,0), 0).size());
  }
  @Test public void number_of_neighbors_radius_1_equal_to_seven(){
    assertEquals(7, grid.getArea(new Coordinate(0,0), 1).size());
    assertEquals(7, grid.getArea(new Coordinate(0,0), 1).size());
    assertEquals(7, grid.getArea(new Coordinate(0,0), 1).size());
    assertEquals(7, grid.getArea(new Coordinate(0,0), 1).size());
    assertEquals(7, grid.getArea(new Coordinate(0,0), 1).size());
  }
  @Test public void number_of_neighbors_radius_2_equal_to_13(){
    assertEquals(19, grid.getArea(new Coordinate(0,0), 2).size());
  }

  @Test public void number_of_ring_neighbors_radius_zero_equals_one(){
    assertEquals(1, grid.ring(origin, 0).size());
  }

  @Test public void number_of_ring_neighbors_radius_one_equals_six(){
    assertEquals(6, grid.ring(origin, 1).size());
  }

  @Test public void number_of_ring_neighbors_radius_two_equals_12(){
    assertEquals(12, grid.ring(origin, 2).size());
  }

  /////////////////////////////////////////////////////////////////////////////////
  private void assertRange(Coordinate c1, Coordinate c2, int trueRange){
    int calculatedRange = grid.range(c1, c2);
    assertEquals("Range Value", trueRange, calculatedRange);
  }
}