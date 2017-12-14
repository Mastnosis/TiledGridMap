package ca.mindmagic.game.map.grid;

import org.junit.Test;

import static org.junit.Assert.*;

public class HexGridTest {

  private HexGrid grid = new HexGrid(30);

  @Test
  public void hexIsRangeZeroFromSelf() throws Exception {
    Koordinate c1 = new Koordinate(3,4);
    assertTrue(grid.range(c1,c1) == 0);
    Koordinate c2 = new Koordinate(0,0);
    assertTrue(grid.range(c2,c2) == 0);
    Koordinate c3 = new Koordinate(-1,-1);
    assertTrue(grid.range(c3,c3) == 0);
    Koordinate c4 = new Koordinate(-4,5);
    assertTrue(grid.range(c4,c4) == 0);
  }

  @Test
  public void originIsRangeOneFromAllNeighbors() throws Exception {
    Koordinate origin = new Koordinate(0,0);
    Koordinate topLeft = HexGrid.Direction.A.move(origin);
    //Koordinate topLeft = new Koordinate(-1,0);
    Koordinate topRight = new Koordinate(0,1);
    Koordinate left = new Koordinate(1,1);
    Koordinate right = new Koordinate(1,0);
    Koordinate bottomLeft = new Koordinate(0,-1);
    Koordinate bottomRight = new Koordinate(-1,-1);
    assertTrue("Top Left is not adjacent", grid.range(origin, topLeft) == 1);
    assertTrue("Top Right is not adjacent", grid.range(origin, topRight) == 1);
    assertTrue("Left is not adjacent", grid.range(origin, left) == 1);
    assertTrue("Right is not adjacent", grid.range(origin, right) == 1);
    assertTrue("Bottom Left is not adjacent", grid.range(origin, bottomLeft) == 1);
    assertTrue("Bottom Right is not adjacent", grid.range(origin, bottomRight) == 1);
  }
  @Test
  public void oneOneIsRangeOneFromAllNeighbors() throws Exception {
    Koordinate origin = new Koordinate(1,1);
    Koordinate topLeft = new Koordinate(0,1);
    Koordinate topRight = new Koordinate(1,2);
    Koordinate left = new Koordinate(2,2);
    Koordinate right = new Koordinate(2,1);
    Koordinate bottomLeft = new Koordinate(1,0);
    Koordinate bottomRight = new Koordinate(0,0);
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
    Koordinate center = new Koordinate(0,0);
  }

  @Test public void numberOfNeighborsEqualsSix() throws Exception {
    assertEquals(6, grid.neighborsOf(new Koordinate(0,0)).size());
  }

  @Test public void getNeighborsReturnsRangeOne(){
    Koordinate center = new Koordinate(0,0);
    grid.neighborsOf(center).forEach(e -> assertRange(center, e, 1));
  }

  private void assertRange(Koordinate c1, Koordinate c2, int trueRange){
    int calculatedRange = grid.range(c1, c2);
    assertEquals("Range Value", trueRange, calculatedRange);
  }
}