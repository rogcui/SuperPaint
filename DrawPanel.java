package superpaint;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.JOptionPane;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Cursor;

/**
 * class controls the drawing of shapes within the JPanel
 * 
 * @author Roger Cui
 * @version 1.2.1
 */ 
public class DrawPanel extends JPanel
{
  private final int LINE = 0; //final int to represent a line
  private final int RECTANGLE = 1; //final int to represent a rectangle
  private final int OVAL = 2; //final int to represent a oval
  private final int IMAGE = -1;
  private MyShape[] shapeObjects;
  private int numOfShapes; //keeps track of how many shapes are on the screen
  private int currentShapeType; //specifies the current type of shape being drawn
  private MyShape currentShapeObject; //Object of the current shape being drawn
  private Color currentShapeColor; //color of the current shape being drawn
  private Boolean currentShapeFilled; //sets the current shape to filled if true
  private Boolean currentShapeGradient;
  private Color currentShapeGradColor1;
  private Color currentShapeGradColor2;
  private Boolean currentShapeDashed;
  private float currentShapeStrokeWidth;
  private float currentShapeDashLength;
  private JLabel statusLabel; //status label to display the actions of the user 
  private int numOfUndos;
  private Image myImage;
  private int numOfSavedImages;
  
  //contructor with a JLabel parameter for the status label
  public DrawPanel( JLabel label ) {
    statusLabel = label;
    
    //initializing default values for instance variables
    shapeObjects = new MyShape[ 1000 ];
    numOfShapes = 0; 
    numOfUndos = 0;
    numOfSavedImages = 0;
    currentShapeType = LINE;
    currentShapeObject = null;
    currentShapeColor = Color.BLACK;
    currentShapeFilled = false;
    currentShapeGradColor1 = Color.RED;
    currentShapeGradColor2 = Color.GREEN;
    currentShapeDashed = false;
    currentShapeGradient = false;
    currentShapeDashLength = 5;
    currentShapeStrokeWidth = 1;
    setBackground( Color.WHITE );
    
    //create MouseHandler object for handler
    MouseHandler handler = new MouseHandler();
    addMouseListener( handler ); //add MouseListener
    addMouseMotionListener( handler ); //add MouseMotionListener
  }
  
  //private inner class MouseHandler to handle mouse events
  private class MouseHandler extends MouseAdapter implements MouseListener {
    
    // handle event when mouse is clicked
    public void mouseClicked( MouseEvent event ) {
      statusLabel.setText( String.format( "Clicked at [%d, %d]", event.getX(), event.getY() ) );
    } 
    
    // handle event when mouse pressed
    public void mousePressed( MouseEvent event ) {
      
      if (currentShapeType == IMAGE) {
        currentShapeObject = new MyImage();
        ((MyImage)currentShapeObject ).setImage( myImage );
      }
      if (currentShapeType == LINE) {
        currentShapeObject = new MyLine(); //set currentShapeObject to Line
  
        
        }
        
        if (currentShapeType == RECTANGLE) {
          currentShapeObject = new MyRectangle(); //set currentShapeObject to Rectangle
          ( (MyBoundedShape)currentShapeObject ).setFilled( currentShapeFilled ); //check if object is filled
        }
        
        if (currentShapeType == OVAL) {
          currentShapeObject = new MyOval(); //set currentShapeObject to Oval
          ( (MyBoundedShape)currentShapeObject ).setFilled( currentShapeFilled ); //check if object is filled
          
        }
        
        currentShapeObject.setX1 ( event.getX() ); //set x1 of the currentShapeObject
        currentShapeObject.setY1 ( event.getY() ); //set y1 of the currentShapeObject
        currentShapeObject.setColor (currentShapeColor ); //set the color of the currentShapeObject
        currentShapeObject.setDashed( currentShapeDashed );
        currentShapeObject.setGradient( currentShapeGradient );
        currentShapeObject.setGradientColor1( currentShapeGradColor1 );
        currentShapeObject.setGradientColor2( currentShapeGradColor2 );
        currentShapeObject.setStrokeWidth( currentShapeStrokeWidth );
        currentShapeObject.setDashLength( currentShapeDashLength );
        
        // if max amount of shapes is drawn, clear all drawn shapes
        if (numOfShapes >= shapeObjects.length) {
          for (int i = 0; i < shapeObjects.length; i++) {
            shapeObjects[i] = null;
          }
          JOptionPane.showMessageDialog(null, "You've drawn too many shapes. The screen will now be cleared.", "Max Shapes Reached!", JOptionPane.INFORMATION_MESSAGE);
          numOfShapes = 0;
          currentShapeObject = null;
          repaint();
        }
        
    } // end method mousePresse
    
    // handle event when mouse released after dragging
    public void mouseReleased( MouseEvent event ) {
      
      if (currentShapeObject != null) {
      statusLabel.setText( String.format( "Released at [%d, %d]", event.getX(), event.getY()) );
      currentShapeObject.setX2( event.getX() ); //set x2 of the currentShapeObject
      currentShapeObject.setY2( event.getY() ); //set y2 of the currentShapeObject
      
      //if x1,y1 and x2,y2 are equal, a shape is not drawn
      if ( currentShapeObject.getX1() != currentShapeObject.getX2() && currentShapeObject.getY1() != currentShapeObject.getY2()) {
        shapeObjects[ numOfShapes ] = currentShapeObject; //add the current object to shapeObjects array
        currentShapeObject = null; //set currentShapeObject to null for the next shape
        numOfShapes += 1; //increment numOfShapes by 1
        numOfUndos = 0; //set undos to 0 since new shape was drawn
        repaint(); //redraw DrawPanel
      }
      
      }
    } // end method mouseReleased
    
    // handle event when mouse enters area
    public void mouseEntered( MouseEvent event ) {
      statusLabel.setText( String.format( "Mouse entered at [%d, %d]", event.getX(), event.getY() ) );
      Cursor crosshairCursor = new Cursor( Cursor.CROSSHAIR_CURSOR );
      setCursor( crosshairCursor );
    } // end method mouseEntered
    
    // handle event when mouse exits area
    public void mouseExited( MouseEvent event ) {
      statusLabel.setText( "Mouse outside JPanel" );
      setBackground( Color.WHITE );
    } // end method mouseExited
    
    // MouseMotionListener event handlers
    // handle event when user drags mouse with button pressed
    public void mouseDragged( MouseEvent event ) {
      if (currentShapeObject != null) {
      currentShapeObject.setX2( event.getX() ); //set temporary x2 of the currentShapeObject
      currentShapeObject.setY2( event.getY() ); //set temporary y2 of the currentShapeObject

      repaint();
      
      statusLabel.setText( String.format( "Dragged at [%d, %d]", event.getX(), event.getY() ) );
      }
    } // end method mouseDragged
    
    // handle event when user moves mouse
    public void mouseMoved( MouseEvent event ) {
      statusLabel.setText( String.format( "Moved at [%d, %d]", event.getX(), event.getY() ) );
    } // end method mouseMoved
    
  }// end private inner class MouseHandler 
  
  // paintComponent method to draw the shapes
  public void paintComponent( Graphics g ) {
    super.paintComponent( g ); 
    
    // draw all objects in shapeObjects array
    for (int i = 0; i < numOfShapes; i++) { //for the number of items in array shapes
      shapeObjects[i].draw( g );
    }
    // draw the currentShapeObject if it is not null
    if (currentShapeObject != null) {
      currentShapeObject.draw( g );
    }
  } // end method paintComponent
  
  // mutator method for currentShapeType
  public void setCurrentShapeType ( int newType ) {
    currentShapeType = newType;
  }
  
  // mutator method for currentShapeColor
  public void setCurrentShapeColor ( Color newColor ) {
    currentShapeColor = newColor;
  }
  
  // mutator method for currentShapeFilled
  public void setCurrentShapeFilled ( Boolean newFilled ) {
    currentShapeFilled = newFilled;
  }
  
  // mutator method for currentShapeGradient
  public void setCurrentShapeGradient ( Boolean newGradient ) {
    currentShapeGradient = newGradient;
  }
  
  // mutator method for currentShapeGradColor1
  public void setCurrentShapeGradColor1 ( Color newGradColor1 ) {
    currentShapeGradColor1 = newGradColor1;
  }
  
  // mutator method for currentShapeGradColor2
  public void setCurrentShapeGradColor2 ( Color newGradColor2 ) {
    currentShapeGradColor2 = newGradColor2;
  }
  
  // mutator method for currentShapeDashed
  public void setCurrentShapeDashed ( Boolean newDashed ) {
    currentShapeDashed = newDashed;
  }
  
  // mutator method for currentShapeStrokeWidth
  public void setStrokeWidth ( float newStrokeWidth ) {
    currentShapeStrokeWidth = newStrokeWidth;
  }
  
  // mutator method for currentShapeDashLength
  public void setDashLength ( float newDashLength ) {
    currentShapeDashLength = newDashLength;
  }
  
  // mutator method for myImage
  public void setImage( Image newImage ) {
    myImage = newImage;
  }
  
  // clears the last shape drawn
  public void clearLastShape() {
    
    //if at least 1 shape is drawn,
    if ( numOfShapes > 0 ) {
      numOfShapes -= 1;
      numOfUndos += 1;
    }
    repaint();
  }
  
  // redraws the last shape if clearLastShape was used
  public void redrawLastShape() {
    
    // if at least 1 undo is used
    if ( numOfUndos > 0 ) {
      numOfShapes += 1;
      numOfUndos -= 1;
    }
    repaint();
  }

  // clears every shape drawn
  public void clearDrawing() {
    numOfShapes = 0; 
    repaint();
  }
  
}// end class