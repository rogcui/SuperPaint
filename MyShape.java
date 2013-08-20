package superpaint;
import java.awt.Color;
import java.awt.Graphics;

/**
 * MyShape class that controls all the shapes or images drawn.
 * 
 * @author Roger Cui
 * @version 1.2.1
 */ 
public abstract class MyShape {
  
  private int x1; // first x co-ordinate
  private int x2; // second x co-ordinate
  private int y1; // first y co-ordinate
  private int y2; // second y co-ordinate
  private Color color; // color of the shape
  private Boolean gradient;
  private Boolean dashed;
  private float strokeWidth;
  private Color gradientColor1;
  private Color gradientColor2;
  private float dashLength;
  
  // constructor with no input values
  public MyShape() {
    x1 = 0;
    x2 = 0;
    y1 = 0; 
    y2 = 0;
    color = Color.BLACK;
    dashed = false;
    gradient = false;
    gradientColor1 = Color.RED;
    gradientColor2 = Color.GREEN;
    dashLength = 1;
    strokeWidth = 1;
  }
  
  // constructor with input values
  public MyShape( int x1, int y1, int x2, int y2, Color color, Boolean gradient, Color gradientColor1, Color gradientColor2, Boolean dashed ) {
    this.x1 = x1;
    this.x2 = x2;
    this.y1 = y1;
    this.y2 = y2;
    this.color = color;
    this.gradient = gradient;
    this.dashed = dashed;
  }
  
  // method that returns the first x co-ordinate
  public int getX1() {
    return x1;
  }
  
  // method that returns the second x co-ordinate
  public int getX2() {
    return x2;
  }
  
  // method that returns the first y co-ordinate
  public int getY1() {
    return y1;
  }
  
  // method that returns the second y co-ordinate
  public int getY2() {
    return y2;
  }
  
  // method that sets a new value for x1
  public void setX1(int newValue) {
    x1 = newValue;
  }
  
  // method that sets a new value for x2
  public void setX2(int newValue) {
    x2 = newValue;
  }
  
  // method that sets a new value for y1
  public void setY1(int newValue) {
    y1 = newValue;
  }
  
  // method that sets a new value for y2
  public void setY2(int newValue) {
    y2 = newValue;
  }
  
  // method that returns the color of the shape
  public Color getColor() {
    return color;
  }
  
  // mutator method for color
  public void setColor( Color newColor ) {
    color = newColor;
  }
  
  // accessor method for gradient
  public Boolean getGradient() {
    return gradient;
  }
  
  // mutator method for gradient
  public void setGradient( Boolean newGradient) {
    gradient = newGradient;
  }
  
  // accessor method for dashed
  public Boolean getDashed() {
    return dashed;
  }
  
  // mutator method for dashed
  public void setDashed( Boolean newDashed ) {
    dashed = newDashed;
  }
  
  // accessor method for strokeWidth
  public float getStrokeWidth() {
    return strokeWidth;
  }
  
  // mutator method for strokeWidth
  public void setStrokeWidth( float newStrokeWidth ) {
    strokeWidth = newStrokeWidth;
  }
  
  // accessor method for gradientColor1
  public Color getGradientColor1() {
    return gradientColor1;
  }
  
  // mutator method for gradientcolor1
  public void setGradientColor1( Color newGradientColor1 ) {
    gradientColor1 = newGradientColor1;
  }
  
  // accessor method for gradientColor2
  public Color getGradientColor2() {
    return gradientColor2;
  }
  
  // mutator method for gradientcolor2
  public void setGradientColor2( Color newGradientColor2) {
    gradientColor2 = newGradientColor2;
  }
  
  // accessor method for dashLength
  public float getDashLength() {
    return dashLength;
  }
  
  // mutator method for dashLength
  public void setDashLength( float newDashLength ) {
    dashLength = newDashLength;
  }
  
  // abstract method which subclasses must provide a body for
  public abstract void draw( Graphics g);
  
}