package superpaint;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is called when the preferences menu is clicked.
 * 
 * @author Roger Cui
 * @version 1.2.1
 * 
 */
public class PreferencesWindow extends JFrame
{
  //Create the instance variables.
  private PrintWriter writer;
  private File preferencesFile;
  private JButton save;
  private JButton defaultSettings;
  private JPanel panel;
  private JComboBox color; //combobox for different colors
  private JComboBox shapes; //combox for different shapes
  private JComboBox gradientColor1;
  private JComboBox gradientColor2;
  private JCheckBox filled; //checkbox for filled shapes
  private JCheckBox gradient; //checkbox for gradients
  private JCheckBox dashed; //checkbox for dashed lines
  private JLabel widthLabel;
  private JButton button_Rectangle;
  private JButton button_Pencil;
  private JButton button_Oval;
  private JComboBox strokeWidth;
  private JComboBox dashLength;
  private Color[] arrayColor = { Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.PINK, 
    Color.GRAY, Color.CYAN, Color.MAGENTA, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.WHITE };
  
  private String[] stringShape = { "Line", "Rectangle", "Oval" }; //string array of shape names for shapes combobox
  
  /**
   * This is the constructor for the preferences window.
   */
  public PreferencesWindow() {
    super( "Preferences" );
    preferencesFile = new File( "preferences.cfg" );
    panel = new JPanel();
    save = new JButton( "Save" );
    defaultSettings = new JButton( "Return to Default Settings" );
    
    // create all options that are availible in the regular program
    color = new JComboBox( arrayColor );
    color.setPreferredSize( new Dimension( 50, 25 ) );
    shapes = new JComboBox( stringShape );
    dashed = new JCheckBox( "Dashed" );
    filled = new JCheckBox( "Filled" );
    dashLength = new JComboBox( 
                               new String[] {"1", "2", "4", "6", "8", "9", "10", "11", "12", "14", "16", "18","20", "22", "24", "26", "28", "32", "36", "48", "72"} );
    dashLength.setPreferredSize( new Dimension(47,20) );
    dashLength.setEditable( true );
    strokeWidth = new JComboBox( new String[] {"1", "2", "4", "6", "8", "12", "16", "20", "26", "32"} );
    strokeWidth.setPreferredSize( new Dimension(47, 20) );
    strokeWidth.setEditable( true );
    gradient = new JCheckBox( "Gradient" );
    gradientColor1 = new JComboBox( arrayColor );
    gradientColor1.setSelectedItem( Color.RED );
    gradientColor2 = new JComboBox( arrayColor );
    gradientColor2.setSelectedItem( Color.GREEN );
    color.setRenderer( new ColorComboRenderer() );
    color.setEditor( new ColorComboBoxEditor( (Color) color.getSelectedItem() ) );
    color.setEditable(true);
    gradientColor1.setRenderer( new ColorComboRenderer() );
    gradientColor1.setEditor( new ColorComboBoxEditor( (Color) color.getSelectedItem() ) );
    gradientColor1.setEditable(true);
    gradientColor2.setRenderer( new ColorComboRenderer() );
    gradientColor2.setEditor( new ColorComboBoxEditor( (Color) color.getSelectedItem() ) );
    gradientColor2.setEditable(true);
    
    // add all the different options
    panel.add( shapes );
    panel.add( color );
    panel.add( new JLabel("Stroke Width:") );
    panel.add( strokeWidth );
    panel.add( dashed );
    panel.add( new JLabel("Dash Length:") );
    panel.add( dashLength );
    panel.add( gradient );
    panel.add( gradientColor1 );
    panel.add( gradientColor2 );
    panel.add( filled );
    
    // add the save button, defaultSettings button, and panel
    add( defaultSettings, BorderLayout.NORTH );
    add( panel, BorderLayout.CENTER );
    add( save, BorderLayout.SOUTH );
    
    // add a handler for the buttons
    ButtonHandler buttonHandler = new ButtonHandler();
    save.addActionListener( buttonHandler );
    defaultSettings.addActionListener( buttonHandler );
  }
  
  // private inner class to handle button events
  private class ButtonHandler implements ActionListener {
    
    public void actionPerformed( ActionEvent event ) {
      
      // if the save button is clicked
      if ( event.getSource() == save ) {
        float width = 0;
        float length = 0;
        
        // test if the stroke width and dash length are valid positive integers
        try {
          width = Float.parseFloat((String)strokeWidth.getSelectedItem());
          length = Float.parseFloat((String)dashLength.getSelectedItem());
          
          if ( width < 0 || width > 999) 
            JOptionPane.showMessageDialog( null, "Stroke width is negative or too high. Please try again.", "SuperPaint", JOptionPane.ERROR_MESSAGE );
          
          else if ( length < 0 || length > 999 )
            JOptionPane.showMessageDialog( null, "Dash Length is negative or too high. Please try again.", "SuperPaint", JOptionPane.ERROR_MESSAGE );
          
          // if stroke width and dash length are valid positive integers, save the selected preferences into the preferencesFile
          else {
            strokeWidth.setSelectedItem( "" + width ); // set the valid stroke width
            dashLength.setSelectedItem( "" + length ); //set the valid dash length
            try {
              writer = new PrintWriter( new FileWriter( preferencesFile ) ); 
              writer.println( ((Color)color.getSelectedItem()).getRGB() );
              writer.println( shapes.getSelectedIndex() );
              writer.println( strokeWidth.getSelectedItem() );
              writer.println( dashed.isSelected() );
              writer.println( dashLength.getSelectedItem() );
              writer.println( gradient.isSelected() );
              writer.println( ((Color) gradientColor1.getSelectedItem()).getRGB() );
              writer.println( ((Color) gradientColor2.getSelectedItem()).getRGB() );
              writer.println( filled.isSelected() );
              writer.close();
              JOptionPane.showMessageDialog( null, "Preferences now saved. Restart SuperPaint for changes to take effect.", "SuperPaint", JOptionPane.INFORMATION_MESSAGE );
            }
            
            catch ( IOException e ) {
               JOptionPane.showMessageDialog( null, "Error!", "SuperPaint", JOptionPane.ERROR_MESSAGE );
            }
          }
        }
        
        // if the user did not enter a valid integer value for the stroke width and/or dash length
        catch (NumberFormatException e) {
          JOptionPane.showMessageDialog( null, "Stroke width and/or dash length is not a valid number. Please try again.", "SuperPaint", JOptionPane.ERROR_MESSAGE );
        }
        
      }
      
      // if the user wants the default settings, delete the preferences file
      else if ( event.getSource() == defaultSettings ) {
        preferencesFile.delete();
        JOptionPane.showMessageDialog( null, "Default Settings Initiated. Please restart to program for changes to take effect.", "SuperPaint", JOptionPane.INFORMATION_MESSAGE );
      }
    
    }
  }// end private inner class
  
}// end class


