package superpaint;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Image;
/**
 * MyImage class that inherits from MyBoundedShape.
 * 
 * @author Roger Cui
 * @version 1.2.1
 */ 
public class MyImage extends MyBoundedShape {
  
  Image myImage;
  
  // default constructor
  public MyImage() {
  }
  
  // draws the image 
  public void draw( Graphics g ) {
    g.drawImage( myImage, getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight(), null ); 
  }
  
  // accessor method for image
  public Image getImage( Image newImage ) {
    return myImage;
  }
  
  // mutator method for image
  public void setImage( Image newImage ) {
    myImage = newImage;
  }
}

  