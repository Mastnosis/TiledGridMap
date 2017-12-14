package ca.mindmagic.game.map.grid.shape;

import java.awt.Point;

public class Square extends Shape {

  public Square(){
  }

  public Square(int sideLength){
    if(sideLength > 0){
      this.sideLength = sideLength;
    }
  }

  @Override public int numberOfSides() {
    return 4;
  }

  @Override public int lengthOfSides() {
    return sideLength;
  }

  @Override public Point[] getVertices() {
    return getVertices(sideLength);
  }

  public static Point[] getVertices(int sideLength){
    Point[] vertices = new Point[4];
    int half = sideLength/2;
    vertices[0] = new Point(-half, -half);
    vertices[1] = new Point(half, -half);
    vertices[2] = new Point(half, half);
    vertices[3] = new Point(-half, half);
    return vertices;
  }
}
