package superpaint;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.BasicStroke;

/**
 * MyOval class that inherits from MyBoundedShape
 * 
 * @author Roger Cui
 * @version 1.2.1
 */ 
public class MyOval extends MyBoundedShape {
  
  // constructor with no input values
  public MyOval() {
    super();
  }
  
  // constructor with input values
  public MyOval( int x1, int y1, int x2, int y2, Color color, Boolean filled, Boolean gradient, Color gradientColor1, Color gradientColor2, Boolean dashed ) {
    super( x1, y1, x2, y2, color, filled, gradient, gradientColor1, gradientColor2, dashed );
  } 
  
  // draws the oval dependant on different options that the user selects
  public void draw( Graphics g ) {
    
    Graphics2D g2d = ( Graphics2D ) g; // cast g to Graphics2D
    
    // set dashed settings
    if (getDashed() == true )
      g2d.setStroke( new BasicStroke(getStrokeWidth(), BasicStroke.CAP_SQUARE,
         BasicStroke.JOIN_ROUND, 50, new float[]{ getDashLength() }, 50 ) ); 
    
    // set stroke settings
    else 
      g2d.setStroke( new BasicStroke(getStrokeWidth(), BasicStroke.CAP_SQUARE,
         BasicStroke.JOIN_ROUND, 50) );
    
    // user chooses filled and no gradient
    if (getFilled() == true && getGradient() == false) {
      g.setColor( getColor() );
      g.fillOval( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight() );
    }
    
    // user chooses filled and gradient
    else if (getFilled() == true && getGradient() == true) {
      g2d.setPaint( new GradientPaint( getX1(), getY1(), getGradientColor1(), getX2(), getY2(), 
                                      getGradientColor2()) );
      g2d.fill( new Ellipse2D.Double( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight()) );
    }
    
    // user chooses no fill and no gradient
    else if (getFilled() == false && getGradient() == false) {
      g.setColor( getColor() );
      g.drawOval(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight() );
    }    
    
    // user chooses no fill and gradient
    else if (getFilled() == false && getGradient() == true) {
      g2d.setPaint( new GradientPaint( getX1(), getY1(), getGradientColor1(), getX2(), getY2(), 
                                      getGradientColor2()) );
      g2d.draw( new Ellipse2D.Double( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight()) );
    }
    
  } // end method draw
}
