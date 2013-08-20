package superpaint;
import java.io.File;
import javax.swing.filechooser.FileFilter;
/**
 * A file filter that only shows images to be selected.
 * 
 * @author Roger Cui
 * @version 1.2.1
 */ 
public class ImageFilter extends FileFilter {
  
  //Accept all directories and all gif, jpg, tiff, or png files.
  public boolean accept( File f ) {
    
    // if the selected file is a directory, go inside the directory
    if ( f.isDirectory() == true ) 
      return true;
    
    String extension = getExtension(f); // get the extension of the file
    
    // if the extension is valid
    if (extension != null) {
      
      // check if the extension is an image extension
      if (extension.equals("jpeg") ||
          extension.equals("tif") ||
          extension.equals("gif") ||
          extension.equals("jpeg") ||
          extension.equals("jpg") ||
          extension.equals("png")) {
        return true;  
      }
      
      // if not an image extension
      else 
        return false;
    }
    
    return false;
  }
  
  // gets the extension of the file 
  private String getExtension(File f) {
    String ext = null;
    String s = f.getName();
    int i = s.lastIndexOf('.');
    
    if (i > 0 &&  i < s.length() - 1) 
      ext = s.substring(i+1).toLowerCase();
   
    return ext; // return the extension of the file
  }
  
  // A description of the filter
  public String getDescription() {
    return "Just Images";
  }
  
}// end class