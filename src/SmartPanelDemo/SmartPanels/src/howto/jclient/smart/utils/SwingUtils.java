/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package howto.jclient.smart.utils;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
public class SwingUtils  {
  public static void setupDefaultFont(String fontName, int size) {
    FontUIResource defaultFont = new FontUIResource(fontName,FontUIResource.PLAIN,size);
    UIManager.put("Button.font",defaultFont);
    UIManager.put("CheckBox.font",defaultFont);
    UIManager.put("CheckBoxMenuItem.acceleratorFont",defaultFont);
    UIManager.put("CheckBoxMenuItem.font",defaultFont);
    UIManager.put("ColorChooser.font",defaultFont);
    UIManager.put("ComboBox.font",defaultFont);
    UIManager.put("EditorPane.font",defaultFont);
    UIManager.put("InternalFrame.titleFont",defaultFont);
    UIManager.put("Label.font",defaultFont);
    UIManager.put("List.font",defaultFont);
    UIManager.put("Menu.acceleratorFont",defaultFont);
    UIManager.put("Menu.font",defaultFont);
    UIManager.put("MenuBar.font",defaultFont);
    UIManager.put("MenuItem.acceleratorFont",defaultFont);
    UIManager.put("MenuItem.font",defaultFont);
    UIManager.put("OptionPane.font",defaultFont);
    UIManager.put("Panel.font",defaultFont);
    UIManager.put("PasswordField.font",defaultFont);
    UIManager.put("PopupMenu.font",defaultFont);
    UIManager.put("ProgressBar.font",defaultFont);
    UIManager.put("RadioButton.font",defaultFont);
    UIManager.put("RadioButtonMenuItem.acceleratorFont",defaultFont);
    UIManager.put("RadioButtonMenuItem.font",defaultFont);
    UIManager.put("ScrollPane.font",defaultFont);
    UIManager.put("TabbedPane.font",defaultFont);
    UIManager.put("Table.font",defaultFont);
    UIManager.put("TableHeader.font",defaultFont);
    UIManager.put("TextArea.font",defaultFont);
    UIManager.put("TextField.font",defaultFont);
    UIManager.put("TextPane.font",defaultFont);
    UIManager.put("TitledBorder.font",defaultFont);
    UIManager.put("ToggleButton.font",defaultFont);
    UIManager.put("ToolBar.font",defaultFont);
    UIManager.put("ToolTip.font",defaultFont);
    UIManager.put("Tree.font",defaultFont);
    UIManager.put("Viewport.font",defaultFont);    
  }
}
