package superpaint;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.DefaultListCellRenderer;

import javax.swing.JLabel;

  public class ColorComboRenderer extends JPanel implements ListCellRenderer {
    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
    Color obj;
    // width doesn't matter as combobox will size
    private final static Dimension preferredSize = new Dimension(0, 20);
    public ColorComboRenderer() {
    super();

    setBorder(new CompoundBorder(
                                 new MatteBorder(1, 2, 1, 2, Color.WHITE), new LineBorder(
                                                                                            Color.black)));
  }
  
    public Component getListCellRendererComponent(JList list, Object value,
        int index, boolean isSelected, boolean cellHasFocus) {
      JLabel renderer = (JLabel) defaultRenderer
          .getListCellRendererComponent(list, value, index,
              isSelected, cellHasFocus);
      if (value instanceof Color) {
        renderer.setBackground((Color) value);
        obj = (Color) value;
      }
      renderer.setPreferredSize(preferredSize);
      return this;
     
    }
    
    public void paint(Graphics g) {
    setBackground(obj);
    super.paint(g);
  }
    
  }
