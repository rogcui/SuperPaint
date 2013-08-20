package superpaint;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Abstract MyBoundedSHape class that inherits from MyShape
 * 
 * @author Roger Cui
 * @version 1.2.1
 */ 
abstract class MyBoundedShape extends MyShape {
  
  private Boolean filled; //instance variable for the fill status of a shape
  
  // constructor without input values
  public MyBoundedShape() {
    filled = false;
  }
  
  // constructor with input values
  public MyBoundedShape( int x1, int y1, int x2, int y2, Color color, Boolean filled, Boolean gradient, Color gradientColor1, Color gradientColor2, Boolean dashed ) {
    super(x1, y1, x2, y2, color, gradient, gradientColor1, gradientColor2, dashed);
    this.filled = filled;
  } // end MyLine constructor
  
  // method to determine if the shape is filled or not
  public Boolean getFilled() {
    return filled;
  }
  
  // method to set a new Boolean value for filled
  public void setFilled(Boolean filled) {
    this.filled = filled;
  }
  
  // determines the upper left x co-ordinate
  public int getUpperLeftX() {
    if (getX1() > getX2()) 
      return getX2();
    else 
      return getX1();
  }
  
  // determines the upper left y co-ordinate
  public int getUpperLeftY() {
    if (getY1() > getY2()) 
      return getY2();
    else 
      return getY1();
  }
  
  // determines the width of a bounded shape
  public int getWidth() {
    return Math.abs(getX2() - getX1());
  }
  
  // determines the height of a bounded shape
  public int getHeight() {
    return Math.abs(getY2() - getY1());
  }
  
  // abstract method which subclasses must provide a method body for
  public abstract void draw( Graphics g);
  
  
}
  
