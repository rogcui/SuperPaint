package superpaint;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

/**
 * MyLine class that inherits from MyShape
 * 
 * @author Roger Cui
 * @version 1.2.1
 */ 
public class MyLine extends MyShape {
  
  // constructor with no input values
  public MyLine() {
  }
  
  // constructor with input values
  public MyLine( int x1, int y1, int x2, int y2, Color color, Boolean gradient, Color gradientColor1, Color gradientColor2, Boolean dashed ) {
    super( x1, y1, x1, x2, color, gradient, gradientColor1, gradientColor2, dashed );
  } // end MyLine constructor
  
  // draws the line
  public void draw( Graphics g ) {
    
    Graphics2D g2d = ( Graphics2D ) g; // cast g to Graphics2D
    
    // set dashed and stroke settings
    if (getDashed() == true )
      g2d.setStroke( new BasicStroke(getStrokeWidth(), BasicStroke.CAP_SQUARE,
                                     BasicStroke.JOIN_ROUND, 50, new float[]{ getDashLength() }, 50 ) ); 
    
    // only set stroke settings
    else 
      g2d.setStroke( new BasicStroke(getStrokeWidth(), BasicStroke.CAP_SQUARE,
                                     BasicStroke.JOIN_ROUND, 50) );
    
    // user chooses gradient 
    if ( getGradient() == true ) {
      g2d.setPaint( new GradientPaint( getX1(), getY1(), getGradientColor1(), getX2(), getY2(), 
                                      getGradientColor2(), true ) );  
      g2d.drawLine( getX1(), getY1(), getX2(), getY2() );
    }
    
    // user chooses no gradient
    else {
      g2d.setColor( getColor() );
      g2d.drawLine( getX1(), getY1(), getX2(), getY2() );
    }
    
  } // end method draw
} // end class MyLine
