package ca.mindmagic.game.map.grid.shape;

import java.awt.Point;

public class Triangle extends Shape {

  public Triangle(){
    super();
  }

  public Triangle(int sideLength){
    super(sideLength);
  }

  @Override public int numberOfSides() {
    return 3;
  }

  @Override public Point[] getVertices() {
    return getVertices(sideLength);
  }

  public static Point[] getVertices(int sidelength){
    Point[] vertices = new Point[3];
    vertices[0] = new Point();  // TODO create the appropriate points
    vertices[1] = new Point();
    vertices[2] = new Point();
    return vertices;
  }
}
