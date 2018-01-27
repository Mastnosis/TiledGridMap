package ca.mindmagic.game.map.grid;

import org.junit.Test;

import static org.junit.Assert.*;

public class HexGridTest {

  private HexGrid grid = new HexGrid(30);

  @Test
  public void hexIsRangeZeroFromSelf() throws Exception {
    Coordinate c1 = new Coordinate(3,4);
    assertTrue(grid.range(c1,c1) == 0);
    Coordinate c2 = new Coordinate(0,0);
    assertTrue(grid.range(c2,c2) == 0);
    Coordinate c3 = new Coordinate(-1,-1);
    assertTrue(grid.range(c3,c3) == 0);
    Coordinate c4 = new Coordinate(-4,5);
    assertTrue(grid.range(c4,c4) == 0);
  }

  @Test
  public void originIsRangeOneFromAllNeighbors() throws Exception {
    Coordinate origin = new Coordinate(0,0);
    Coordinate topLeft = HexGrid.Direction.A.move(origin);
    //Koordinate topLeft = new Koordinate(-1,0);
    Coordinate topRight = new Coordinate(0,1);
    Coordinate left = new Coordinate(1,1);
    Coordinate right = new Coordinate(1,0);
    Coordinate bottomLeft = new Coordinate(0,-1);
    Coordinate bottomRight = new Coordinate(-1,-1);
    assertTrue("Top Left is not adjacent", grid.range(origin, topLeft) == 1);
    assertTrue("Top Right is not adjacent", grid.range(origin, topRight) == 1);
    assertTrue("Left is not adjacent", grid.range(origin, left) == 1);
    assertTrue("Right is not adjacent", grid.range(origin, right) == 1);
    assertTrue("Bottom Left is not adjacent", grid.range(origin, bottomLeft) == 1);
    assertTrue("Bottom Right is not adjacent", grid.range(origin, bottomRight) == 1);
  }
  @Test
  public void oneOneIsRangeOneFromAllNeighbors() throws Exception {
    Coordinate origin = new Coordinate(1,1);
    Coordinate topLeft = new Coordinate(0,1);
    Coordinate topRight = new Coordinate(1,2);
    Coordinate left = new Coordinate(2,2);
    Coordinate right = new Coordinate(2,1);
    Coordinate bottomLeft = new Coordinate(1,0);
    Coordinate bottomRight = new Coordinate(0,0);
    assertTrue("Top Left is not adjacent", grid.range(origin, topLeft) == 1);
    assertTrue("Top Right is not adjacent", grid.range(origin, topRight) == 1);
    assertTrue("Left is not adjacent", grid.range(origin, left) == 1);
    assertTrue("Right is not adjacent", grid.range(origin, right) == 1);
    assertTrue("Bottom Left is not adjacent", grid.range(origin, bottomLeft) == 1);
    assertTrue("Bottom Right is not adjacent", grid.range(origin, bottomRight) == 1);
  }

  //@Test
  //public void adjacentHexesShareVertices() throws Exception {
  //  Koordinate source = new Koordinate(3, 4);
  //  Koordinate adjacent = new Koordinate(3, 3);
  //  Point p1 = grid.getVertices(source)[5];
  //  Point p2 = grid.getVertices(adjacent)[1];
  //  assertEquals(p1, p2);
  //}

  @Test
  public void neighborsRadiusOneRangeOne() throws Exception {
    Coordinate center = new Coordinate(0,0);
  }

  @Test public void numberOfNeighborsEqualsSix() throws Exception {
    assertEquals(6, grid.neighborsOf(new Coordinate(0,0)).size());
  }

  @Test public void getNeighborsReturnsRangeOne(){
    Coordinate center = new Coordinate(0,0);
    grid.neighborsOf(center).forEach(e -> assertRange(center, e, 1));
  }

  private void assertRange(Coordinate c1, Coordinate c2, int trueRange){
    int calculatedRange = grid.range(c1, c2);
    assertEquals("Range Value", trueRange, calculatedRange);
  }
}