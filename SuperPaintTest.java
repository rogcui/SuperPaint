package superpaint;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Test class for the SuperPaint application.
 * 
 * @author Roger Cui
 * @version 1.2.1
 * 
 */
public class SuperPaintTest{
  public static void main( String []args )
  {
    // create the frame in the middle of the screen
    DrawFrame frame = new DrawFrame();
    frame.setSize( 950, 700 );
    frame.setLocationRelativeTo( null ); //centers the JFrame
    frame.setVisible( true );
  }
}
