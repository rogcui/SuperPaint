package superpaint;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.ListCellRenderer;
import javax.swing.event.EventListenerList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import javax.swing.border.CompoundBorder;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.JLabel;

class ColorComboBoxEditor implements ComboBoxEditor {
  final protected JButton editor;

  transient protected EventListenerList listenerList = new EventListenerList();

  public ColorComboBoxEditor(Color initialColor) {
    editor = new JButton("");
    editor.setBackground(initialColor);
    ActionListener actionListener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Color currentBackground = editor.getBackground();
        Color color = JColorChooser.showDialog(editor, "Color Chooser",
            currentBackground);
        if ((color != null) && (currentBackground != color)) {
          editor.setBackground(color);
          fireActionEvent(color);
        }
      }
    };
    editor.addActionListener(actionListener);
  }

  public void addActionListener(ActionListener l) {
    listenerList.add(ActionListener.class, l);
  }

  public Component getEditorComponent() {
    return editor;
  }

  public Object getItem() {
    return editor.getBackground();
  }

  public void removeActionListener(ActionListener l) {
    listenerList.remove(ActionListener.class, l);
  }

  public void selectAll() {
    // ignore
  }

  public void setItem(Object newValue) {
    if (newValue instanceof Color) {
      Color color = (Color) newValue;
      editor.setBackground(color);
    } else {
      // Try to decode
      try {
        Color color = Color.decode(newValue.toString());
        editor.setBackground(color);
      } catch (NumberFormatException e) {
        // ignore - value unchanged
      }
    }
  }

  protected void fireActionEvent(Color color) {
    Object listeners[] = listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == ActionListener.class) {
        ActionEvent actionEvent = new ActionEvent(editor,
            ActionEvent.ACTION_PERFORMED, color.toString());
        ((ActionListener) listeners[i + 1])
            .actionPerformed(actionEvent);
      }
    }
  }
}