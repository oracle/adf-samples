/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import howto.jclient.smart.SmartPanel;
import java.awt.Dimension;
import java.util.Locale;
import javax.swing.UIManager;
import howto.jclient.smart.utils.SwingUtils;
import oracle.jbo.uicli.controls.JUTestFrame;
import oracle.jbo.uicli.jui.JUPanelBinding;
import java.awt.event.*;
import oracle.sql.*;
import oracle.jbo.domain.Number;
public class EmpPanel extends SmartPanel  {

  private JUPanelBinding panelBinding = new JUPanelBinding("TestProject.HRModule",null);
  
  public EmpPanel() {
    //super("test/EmpPanel.xml");
    initializeDesignTimePanelBinding(panelBinding);
  }

  public static void main(String[] args) throws Throwable {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      SwingUtils.setupDefaultFont("Tahoma",20);
      // Uncomment this line to try in Italian instead.
      // Locale.setDefault(new Locale("it","it","euro"));
      Locale.setDefault(Locale.US);
    }
    catch(Exception exemp) {
      exemp.printStackTrace();
    }

    EmpPanel panel = new EmpPanel();
    JUTestFrame.testJClientPanel(panel, panel.getPanelBinding(), new Dimension(750, 250));
    
  } 

  public void mymethod(ActionEvent e) {
    System.out.println("####");
  }  
}
