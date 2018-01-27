package ca.mindmagic.game.map.grid.pattern;

import java.awt.Point;

public abstract class Shape {

  private static final int DEFAULT = 40;

  protected int sideLength;

  public Shape(){
    this(DEFAULT);
  }

  public Shape(int sideLength){
    if(sideLength > 0){
      this.sideLength = sideLength;
    }
    else {
      this.sideLength = DEFAULT;
    }
  }

  public abstract int numberOfSides();

  public int lengthOfSides(){
    return sideLength;
  }

  public abstract Point[] getVertices();
}
