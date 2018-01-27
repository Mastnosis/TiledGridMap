package ca.mindmagic.game.map.grid.pattern;

import java.awt.Point;
import java.util.Arrays;

public class Hexagon extends Shape{

  // HEIGHT = shortest distance between the center and any of the hex sides
  // where sideLength is equal to one.
  public static final float HEIGHT = (float)Math.sqrt(0.75);

  public Hexagon(){
    super();
  }

  public Hexagon(int sideLength){
    super(sideLength);
  }

  @Override public int numberOfSides() {
    return 6;
  }

  @Override public int lengthOfSides() {
    return sideLength;
  }

  @Override public Point[] getVertices() {
    return getVertices(sideLength);
  }

  public static Point[] getVertices(int sideLength) {
    Point[] vertices = new Point[6];
    vertices[0] = topPoint(sideLength);
    vertices[1] = upperRightPoint(sideLength);
    vertices[2] = lowerRightPoint(sideLength);
    vertices[3] = bottomPoint(sideLength);
    vertices[4] = lowerLeftPoint(sideLength);
    vertices[5] = upperLeftPoint(sideLength);
    return vertices;
  }

  public Double[] getVerticesAsDoubleArray(double scalar){
    Double[] vertices = new Double[12];
    return vertices;
  }

  private static Double[] scale(Double[] values, double scalar){
    return Arrays.stream(values).map(x -> x*scalar)
        .toArray(Double[]::new);
  }

  private static Point upperRightPoint(int size) {
    int x, y;
    x = (int)(HEIGHT * size);
    y = -size/2;
    return new Point(x,y);
  }

  private static Point lowerRightPoint(int size) {
    int x, y;
    x = (int)(HEIGHT * size);
    y = size/2;
    return new Point(x,y);
  }

  private static Point bottomPoint(int size) { // done
    int x, y;
    x = 0;
    y = size;
    return new Point(x,y);
  }

  private static  Point lowerLeftPoint(int size) {
    int x, y;
    x = -(int)(HEIGHT * size);
    y = size/2;
    return new Point(x,y);
  }

  private static Point upperLeftPoint(int size) {
    int x, y;
    x = -(int)(HEIGHT * size);
    y = -size/2;
    return new Point(x,y);
  }

  private static Point topPoint(int size) { // done
    int x, y;
    x = 0;
    y = -size;
    return new Point(x,y);
  }

  public String toString(){
    return getVertices().toString();
  }


}
