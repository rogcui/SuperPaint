package superpaint;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.DefaultListCellRenderer;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.JTextField;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.GridLayout;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import java.lang.NumberFormatException;
import java.util.Scanner;
import java.io.IOException;
import java.awt.Graphics2D;

/**
 * Class used to create JFrame for drawing shapes.
 * 
 * @author Roger Cui
 * @version 1.2.1
 */ 
public class DrawFrame extends JFrame 
{
  // final int variables to eliminate magic numbers
  private final int LINE = 0; //final int to represent a line
  private final int RECTANGLE = 1; //final int to represent a rectangle
  private final int OVAL = 2; //final int to represent a oval
  private final int IMAGE = -1;

  private JLabel statusLabel; //status label to display mouse actions
  private JPanel optionPanel; //option label to display user options
  private JPanel featurePanel; //panel to display extra features
  private JFileChooser chooser;
  private JTabbedPane tabbedPane;
  
  // all drawing feature components
  private JButton undo; //undo button
  private JButton clear; //clear button 
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
  private JButton redo;
   
  // all menu related components
  private JMenuBar menuBar; 
  private JMenuItem fileMenu_New;
  private JMenuItem fileMenu_CloseTab;
  private JMenuItem fileMenu_NewWindow;
  private JMenuItem fileMenu_Open;
  private JMenuItem fileMenu_Save;
  private JMenuItem fileMenu_Exit;
  private JMenuItem fileMenu_Preferences;
  private JMenuItem fileMenu_About;
  private JMenu fileMenu;
  private JMenu editMenu;
  private JMenu helpMenu;
  private JMenuItem editMenu_Undo;
  private JMenuItem editMenu_Redo;
 
  private File preferencesFile;
  private DrawPanel currentPanel; 
  private DrawPanel[] drawPanels = new DrawPanel[10]; //create 10 panels for tabs

  private static int numOfWindowsOpen = 0;
  private int numOfTabs = 1;

  
  private Color[] arrayColor = { Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.PINK, 
                                Color.GRAY, Color.CYAN, Color.MAGENTA, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.WHITE };
  
  private String[] stringShape = { "Line", "Rectangle", "Oval" }; //string array of shape names for shapes combobox
  
  
  //constructor for DrawFrame
  public DrawFrame() {
    super ( "Super Paint!");
    preferencesFile = new File("preferences.cfg");
    numOfWindowsOpen += 1;
    
    // set the Nimbus Look and Feel in the UIManager
    try {
      UIManager.setLookAndFeel( "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel" ); // "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
    } catch (Exception e) { 
      //ignore
    }
    
    //update the user look and feel
    SwingUtilities.updateComponentTreeUI(this);
    
    chooser = new JFileChooser();
    tabbedPane = new JTabbedPane(); //Creates a tabbed pane
    tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); //Sets the tab's behaviour if they aren't able to fit in one line.
    
    //creating all menu related components
    
    menuBar = new JMenuBar();
    fileMenu = new JMenu("   File"); //creates file menu
    editMenu = new JMenu("  Edit  "); //creates edit menu
    helpMenu = new JMenu(" Help ");
    editMenu_Undo = new JMenuItem("       Undo");
    editMenu_Redo = new JMenuItem("       Redo");
    fileMenu_New = new JMenuItem("   New Tab");
    fileMenu_CloseTab = new JMenuItem("  Close Tab");
    fileMenu_NewWindow = new JMenuItem("New Window");
    fileMenu_Open = new JMenuItem(" Load Image");
    fileMenu_Save = new JMenuItem("       Save");
    fileMenu_Preferences = new JMenuItem(" Preferences");
    fileMenu_About = new JMenuItem("  About");
    fileMenu_Exit = new JMenuItem("        Exit");
    fileMenu.setPreferredSize(new Dimension( 48, 21 ) );
    
    //adding all menu related components
    fileMenu.add( fileMenu_New );
    fileMenu.add( fileMenu_CloseTab );
    fileMenu.add( fileMenu_NewWindow );
    fileMenu.add( fileMenu_Open );
    fileMenu.add( fileMenu_Save );
    fileMenu.add( fileMenu_Exit );
    editMenu.add( editMenu_Undo );
    editMenu.add( editMenu_Redo );
    editMenu.add( fileMenu_Preferences );
    helpMenu.add( fileMenu_About );
    menuBar.add( fileMenu );
    menuBar.add( editMenu );
    menuBar.add( helpMenu );
    setJMenuBar (menuBar);
    
    optionPanel = new JPanel(); //create the option panel
    add( optionPanel, BorderLayout.NORTH ); //add the option panel to the top
    
    statusLabel = new JLabel("Waiting for user..."); //create the status label
    add (statusLabel, BorderLayout.SOUTH ); //add the status label to the bottom

    
    drawPanels[0] = new DrawPanel( statusLabel ); //create panel object, passing in a reference to statusLabel
    currentPanel = drawPanels[0];
    currentPanel.setBackground( Color.WHITE ); //set the background color to white
    
    featurePanel = new JPanel( new GridLayout(15, 2) ); //create the feature panel
    add( featurePanel, BorderLayout.WEST ); //add the feature panel
    featurePanel.setPreferredSize( new Dimension(50, 0) );
    featurePanel.setBackground( Color.GRAY );
    
    //getting different button icons and resizing them to fit
    ImageIcon undoIcon = new ImageIcon( "Icons/UndoIcon.png" );
    Image img = undoIcon.getImage() ;  
    Image newimg = img.getScaledInstance( 30, 30, java.awt.Image.SCALE_SMOOTH ) ;
    undoIcon = new ImageIcon( newimg );
    
    ImageIcon redoIcon = new ImageIcon( "Icons/RedoIcon.png" );
    img = redoIcon.getImage() ;  
    newimg = img.getScaledInstance( 30, 30, java.awt.Image.SCALE_SMOOTH ) ;
    redoIcon = new ImageIcon( newimg );
    
    ImageIcon pencilIcon = new ImageIcon( "Icons/PencilIcon.png" );
    img = pencilIcon.getImage() ;  
    newimg = img.getScaledInstance( 30, 30, java.awt.Image.SCALE_SMOOTH ) ;
    pencilIcon = new ImageIcon( newimg );
    
    ImageIcon rectangleIcon = new ImageIcon( "Icons/RectangleIcon.jpg" );
    img = rectangleIcon.getImage() ;  
    newimg = img.getScaledInstance( 30, 30, java.awt.Image.SCALE_SMOOTH ) ;
    rectangleIcon = new ImageIcon( newimg );
    
    ImageIcon ovalIcon = new ImageIcon( "Icons/OvalIcon.jpg" );
    img = ovalIcon.getImage() ;  
    newimg = img.getScaledInstance( 30, 30, java.awt.Image.SCALE_SMOOTH ) ;
    ovalIcon = new ImageIcon( newimg );
    
    //creating buttons with ImageIcons
    undo = new JButton( undoIcon ); 
    undo.setPreferredSize( new Dimension(35, 35) );
    redo = new JButton( redoIcon );
    redo.setPreferredSize( new Dimension(35, 35) );
    button_Pencil = new JButton( pencilIcon );
    button_Rectangle = new JButton( rectangleIcon );
    button_Oval = new JButton( ovalIcon );
    
    
    clear = new JButton( "Clear" );
    color = new JComboBox( arrayColor );
    color.setPreferredSize( new Dimension( 50, 25 ) );
    shapes = new JComboBox( stringShape );
    dashed = new JCheckBox( "Dashed" );
    filled = new JCheckBox( "Filled" );
    dashLength = new JComboBox( 
                               new String[] {"1.0", "2.0", "4.0", "6.0", "8.0", "9.0", "10.0", "11.0", "12.0", "14.0", 
                                                 "16.0", "18.0","20.0", "22.0", "24.0", "26.0", "28.0", "32.0", "36.0", "48.0", "72.0"} );
    dashLength.setPreferredSize( new Dimension(55,20) );
    dashLength.setEditable( true );
    strokeWidth = new JComboBox( new String[] {"1", "2", "4", "6", "8", "12", "16", "20", "26", "32"} );
    strokeWidth.setPreferredSize( new Dimension(55, 20) );
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
    
    //adding all the options
    optionPanel.add( undo );
    optionPanel.add( redo );
    optionPanel.add( clear );
    optionPanel.add( color );
    optionPanel.add( shapes );
    optionPanel.add( new JLabel("Stroke Width:") );
    optionPanel.add( strokeWidth );
    optionPanel.add( dashed );
    optionPanel.add( new JLabel("Dash Length:") );
    optionPanel.add( dashLength );
    optionPanel.add( gradient );
    optionPanel.add( gradientColor1 );
    optionPanel.add( gradientColor2 );
    optionPanel.add( filled );
    
    //adding extra features
    featurePanel.add( button_Pencil );
    featurePanel.add( button_Rectangle );
    featurePanel.add( button_Oval );
    
    add( tabbedPane, BorderLayout.CENTER );
    
    //some features are not availible when program first opens
    filled.setEnabled( false );
    gradientColor1.setEnabled( false );
    gradientColor2.setEnabled( false );
    dashLength.setEnabled( false );
    
    tabbedPane.addTab( "Untitled " + ( tabbedPane.getTabCount() + 1 ), currentPanel); 
    
    //add event handling for buttons
    ButtonHandler buttonHandler = new ButtonHandler();
    undo.addActionListener( buttonHandler );
    redo.addActionListener( buttonHandler );
    clear.addActionListener( buttonHandler );
    button_Pencil.addActionListener( buttonHandler );
    button_Rectangle.addActionListener( buttonHandler );
    button_Oval.addActionListener( buttonHandler );
    
    //add event handling for checkbox
    CheckBoxHandler checkBoxHandler = new CheckBoxHandler();
    filled.addItemListener( checkBoxHandler );
    gradient.addItemListener( checkBoxHandler );
    dashed.addItemListener( checkBoxHandler );
    
    //add event handling for comboboxes
    ComboBoxHandler comboBoxHandler = new ComboBoxHandler();
    color.addItemListener( comboBoxHandler );
    shapes.addItemListener( comboBoxHandler );
    gradientColor1.addItemListener( comboBoxHandler );
    gradientColor2.addItemListener( comboBoxHandler );
    strokeWidth.addItemListener( comboBoxHandler );
    dashLength.addItemListener( comboBoxHandler );
    
    //add event handling for menu items
    MenuItemHandler menuItemHandler = new MenuItemHandler();
    fileMenu_New.addActionListener( menuItemHandler );
    fileMenu_CloseTab.addActionListener( menuItemHandler );
    fileMenu_NewWindow.addActionListener( menuItemHandler );
    fileMenu_Open.addActionListener( menuItemHandler );
    fileMenu_Save.addActionListener( menuItemHandler );
    fileMenu_Preferences.addActionListener( menuItemHandler );
    fileMenu_About.addActionListener( menuItemHandler );
    fileMenu_Exit.addActionListener( menuItemHandler );
    editMenu_Undo.addActionListener( menuItemHandler );
    editMenu_Redo.addActionListener( menuItemHandler );
    
    //add event handling for tabbed panes
    TabbedPaneHandler tabbedPaneHandler = new TabbedPaneHandler();
    tabbedPane.addChangeListener( tabbedPaneHandler );
    
    //add event handling for windows
    WindowHandler handler = new WindowHandler();
    addWindowListener( handler );
    
    try {
    getPreferences();
    }
    catch( IOException e ) {
      
    }
  }
  
  // inner class for button event handling
  private class ButtonHandler implements ActionListener {
    // handle button event
    public void actionPerformed( ActionEvent event ) {
      
      // if undo button is clicked, call clearLastShape()
      if (event.getSource() == undo) 
        currentPanel.clearLastShape(); 
      
      else if (event.getSource() == redo) 
        currentPanel.redrawLastShape();
                 
      // if clear button is clicked, call clearDrawing()
      else if (event.getSource() == clear)
        currentPanel.clearDrawing();
      
      else if (event.getSource() == button_Pencil) {
        currentPanel.setCurrentShapeType( LINE ); //sets selected shape to draw
        shapes.setSelectedIndex( LINE );
      }
      
      else if (event.getSource() == button_Rectangle) {
        currentPanel.setCurrentShapeType( RECTANGLE ); //sets selected shape to draw
        shapes.setSelectedIndex( RECTANGLE );
      }
      
      else if (event.getSource() == button_Oval) {
        currentPanel.setCurrentShapeType( OVAL ); //sets selected shape to draw
        shapes.setSelectedIndex( OVAL );
      }
      
      
    } // end method actionPerformed
  } // end private inner class ButtonHandler
  
  // private inner class for ItemListener event handling
  private class CheckBoxHandler implements ItemListener  {
    
    public void itemStateChanged( ItemEvent event ) {
      
      //set currentShapeFilled to true if checkbox is checked
      if (event.getSource() == filled) 
        currentPanel.setCurrentShapeFilled( filled.isSelected() );
      
      //enable gradient options if gradient checkbox is checked
      else if (event.getSource() == gradient) {
        gradientColor1.setEnabled( gradient.isSelected() );
        gradientColor2.setEnabled( gradient.isSelected() );
        color.setEnabled( !gradient.isSelected() );
        currentPanel.setCurrentShapeGradient( gradient.isSelected() );
      }
      
      //enable dashed options if gradient checkbox is checked
      else if (event.getSource() == dashed) {
        currentPanel.setCurrentShapeDashed( dashed.isSelected() );
        dashLength.setEnabled( dashed.isSelected() );
      }
      
    }// end private inner class CheckBoxHandler
  }
  
  // private inner class for JComboBox event handling
  private class ComboBoxHandler implements ItemListener {
    
    public void itemStateChanged( ItemEvent event) {
      
      // if color combo box is changed
      if (event.getSource() == color) 
        currentPanel.setCurrentShapeColor( (Color)color.getSelectedItem() ); //sets selected color
      
      // if shapes combo box is changed
      else if (event.getSource() == shapes) {
        currentPanel.setCurrentShapeType(shapes.getSelectedIndex()); //sets selected shape to draw
        
        // if the selected shape is a line, disable filled option
        if ( shapes.getSelectedIndex() == LINE )  
          filled.setEnabled( false );
        
        // every other shape can be filled
        else 
          filled.setEnabled( true );
        
      }
      
      // if gradient color1 combo box is changed
      else if (event.getSource() == gradientColor1) {
        currentPanel.setCurrentShapeGradColor1( (Color)gradientColor1.getSelectedItem() ); //sets selected color
      }
      
      // if gradient color2 combo box is changed
      else if (event.getSource() == gradientColor2) {
        currentPanel.setCurrentShapeGradColor2( (Color)gradientColor2.getSelectedItem() ); //sets selected color
      }
      
      // if dash length is changed, exception handle the input and also sets the dash length
      else if (event.getSource() == dashLength) {
        float length;
        try {
          length = Float.parseFloat((String)dashLength.getSelectedItem());
          
          if ( length < 1 || length > 999 ) {
            dashLength.setSelectedIndex( 0 );
          }
          else
            currentPanel.setDashLength( length );
        }
        catch (NumberFormatException e) {
          dashLength.setSelectedIndex( 0 );
        }
      }
      
      // if stroke width is changed, exception handle the input and also sets the dash length
      else if (event.getSource() == strokeWidth) {
        float width;
        try {
          width = Float.parseFloat((String)strokeWidth.getSelectedItem());
          
          if ( width < 0 || width > 999) 
            strokeWidth.setSelectedIndex( 0 );
          
          else
            currentPanel.setStrokeWidth( width );
        }
        catch (NumberFormatException e) {
          strokeWidth.setSelectedIndex( 0 );
        }
        
      }
    }
    
  }// end private inner class ComboBoxHandler 
  
  
  //private inner class for MenuItem event handling
  private class MenuItemHandler implements ActionListener { 
     //show a message dialog for which item is clicked
    public void actionPerformed (ActionEvent event) {
      if (event.getSource() == fileMenu_New) {   
        int tabNum = tabbedPane.getTabCount();
        if ( tabNum < 10 ) {
          drawPanels[tabNum] = new DrawPanel(statusLabel);
          tabbedPane.add( "Untitled " + (numOfTabs + 1), drawPanels[tabNum] );
          tabbedPane.setSelectedComponent( drawPanels[tabNum] );
          numOfTabs += 1;
        }
      }
      
      else if (event.getSource() == fileMenu_CloseTab) {
        if ( tabbedPane.getTabCount() > 1 ) {
          int tabNum = tabbedPane.getTabCount();
          tabbedPane.remove( tabbedPane.getSelectedComponent() );
        }
      }
      
      else if (event.getSource() == fileMenu_NewWindow) {
        DrawFrame newWindow = new DrawFrame();
        newWindow.setSize( 950, 700 );
        newWindow.setLocationRelativeTo( null ); //centers the JFrame
        newWindow.setVisible( true );
      }
      
      else if (event.getSource() == fileMenu_Open) {
        chooser.addChoosableFileFilter( new ImageFilter() );
        int returnVal = chooser.showOpenDialog(currentPanel);
        try {
          Image image = (Image) ImageIO.read(new File( chooser.getSelectedFile().getPath() ) );
          currentPanel.setImage( image );
          currentPanel.setCurrentShapeType( IMAGE );
          JOptionPane.showMessageDialog( null, "Image successfully loaded!\n You can exit image drawing mode by selecting one of the icons on the left!",
                                        "SuperPaint!", JOptionPane.INFORMATION_MESSAGE );
        }
        catch(Exception e) {
          
        }
        
      }
      
      else  if (event.getSource() == fileMenu_Save) {
        String fileName = "";
        Dimension size = currentPanel.getSize();
        BufferedImage saveImage = (BufferedImage)currentPanel.createImage(size.width, size.height);
        Graphics2D g2 = saveImage.createGraphics();
        currentPanel.paint( g2 );
        try {
          fileName = JOptionPane.showInputDialog( null, "Create a filename for your image. You do not have to type the extension.", "SuperPaint", JOptionPane.QUESTION_MESSAGE );
          if ( fileName != null ) {
            ImageIO.write(saveImage, "jpg", new File( "images/" + fileName + ".jpg"));
            tabbedPane.setTitleAt( tabbedPane.getSelectedIndex(), fileName + ".jpg" );
            JOptionPane.showMessageDialog( null, "Image successfully saved. Go to the images folder to view your saved drawing!", "SuperPaint!", JOptionPane.INFORMATION_MESSAGE );
          }
        }
        catch (IOException e){
          e.printStackTrace();
        }
      }
      
      
      else if (event.getSource() == fileMenu_Preferences) {
        PreferencesWindow prefWindow = new PreferencesWindow();
        prefWindow.setSize( 500, 160 );
        prefWindow.setLocationRelativeTo( null ); //centers the JFrame
        prefWindow.setVisible( true );
      }
      
      else if (event.getSource() == fileMenu_About) {
        String message = String.format("%10s\n%s\n%s", "Thank You for Testing SuperPaint!", "Version: 1.2.1 OMEGA", "Created By: Roger Cui");
        JOptionPane.showMessageDialog( null, message, "About", JOptionPane.INFORMATION_MESSAGE);
      }
      
      else if (event.getSource() == fileMenu_Exit) {
        int choice = JOptionPane.showConfirmDialog( null, "All windows will close. Are you sure?", "SuperPaint!", JOptionPane.YES_NO_OPTION );
        if (choice == JOptionPane.YES_OPTION)
          System.exit(0);
      }
      
      else if (event.getSource() == editMenu_Undo) {
        currentPanel.clearLastShape();
      }
      
      else if (event.getSource() == editMenu_Redo) {
        currentPanel.redrawLastShape(); 
      }
    }
  } //end class
  
  private class TabbedPaneHandler implements ChangeListener {
    
    public void stateChanged(ChangeEvent e) {
      currentPanel = (DrawPanel) tabbedPane.getSelectedComponent();
      currentPanel.setCurrentShapeColor(arrayColor[color.getSelectedIndex()]); //sets selected color
      currentPanel.setCurrentShapeType(shapes.getSelectedIndex()); //sets selected shape to draw
      currentPanel.setCurrentShapeFilled( filled.isSelected() ); //set currentShapeFilled to true if checkbox is checked
      currentPanel.setCurrentShapeGradient( gradient.isSelected() );
      currentPanel.setCurrentShapeGradColor1( (Color)gradientColor1.getSelectedItem() );
      currentPanel.setCurrentShapeGradColor2( (Color)gradientColor2.getSelectedItem() );
      currentPanel.setCurrentShapeDashed( dashed.isSelected() );
      currentPanel.setDashLength( Float.parseFloat((String)dashLength.getSelectedItem()) );
      currentPanel.setStrokeWidth( Float.parseFloat((String)strokeWidth.getSelectedItem()) );
      tabbedPane.setSelectedComponent( currentPanel );
    }
  }
  
  private class WindowHandler implements WindowListener {
    
    public void windowClosing( WindowEvent e ) {
      if (numOfWindowsOpen > 1) {
        numOfWindowsOpen -= 1;
        dispose();
      }
      
      else if (numOfWindowsOpen == 1) {
        System.exit( 0 );
      }
    }
    
    public void windowOpened(WindowEvent e) {
    }
    public void windowClosed(WindowEvent e) {
    }
    public void windowIconified(WindowEvent e) {
    }
    public void windowDeiconified(WindowEvent e) {
    }
    public void windowActivated(WindowEvent e) {
    }
    public void windowDeactivated(WindowEvent e) {
    }
  }
  
  private void getPreferences() throws IOException {
    Scanner scanner = new Scanner( preferencesFile );
    if ( scanner.hasNextLine() ) {
      //Try to read the preferences from the file.
        //Create the scanner.
        currentPanel.setCurrentShapeColor( new Color( Integer.parseInt( scanner.nextLine() ) ) );
        currentPanel.setCurrentShapeType( Integer.parseInt( scanner.nextLine() ) );
        currentPanel.setStrokeWidth( Float.parseFloat( scanner.nextLine() ) );
        currentPanel.setCurrentShapeDashed( Boolean.parseBoolean( scanner.nextLine() ) );
        currentPanel.setDashLength( Float.parseFloat( scanner.nextLine() ) );
        currentPanel.setCurrentShapeGradient( Boolean.parseBoolean( scanner.nextLine() ) );
        currentPanel.setCurrentShapeGradColor1( new Color( Integer.parseInt( scanner.nextLine() ) ) );
        currentPanel.setCurrentShapeGradColor2( new Color( Integer.parseInt( scanner.nextLine() ) ) );
        currentPanel.setCurrentShapeFilled( Boolean.parseBoolean( scanner.nextLine() ) );
        scanner = new Scanner( preferencesFile );
        color.setSelectedItem( new Color( Integer.parseInt( scanner.nextLine() ) ) );
        shapes.setSelectedIndex( Integer.parseInt( scanner.nextLine() ) );
        strokeWidth.setSelectedItem( scanner.nextLine() );
        dashed.setSelected( Boolean.parseBoolean( scanner.nextLine() ) );
        dashLength.setSelectedItem( scanner.nextLine() );
        gradient.setSelected( Boolean.parseBoolean( scanner.nextLine() ) );
        gradientColor1.setSelectedItem( new Color( Integer.parseInt( scanner.nextLine() ) ) );
        gradientColor2.setSelectedItem( new Color( Integer.parseInt( scanner.nextLine() ) ) );
        filled.setSelected( Boolean.parseBoolean( scanner.nextLine() ) );
 
    }
  }
     
    }// end DrawFrame class

  
  
  