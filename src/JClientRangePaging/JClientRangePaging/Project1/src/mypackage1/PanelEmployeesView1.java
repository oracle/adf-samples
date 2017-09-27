/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package mypackage1;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import oracle.jbo.uicli.jui.*;
import oracle.jbo.uicli.controls.*;
import oracle.jbo.uicli.binding.*;
import oracle.adf.model.*;
import oracle.adf.model.binding.*;
import java.util.ArrayList;

import oracle.jdeveloper.layout.*;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.ButtonModel;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class PanelEmployeesView1 extends JPanel implements JUPanel 
{
  // Panel binding definition used by design time
  private JUPanelBinding panelBinding = new JUPanelBinding("PanelEmployeesView1UIModel");

// Panel containing the data entry fields

  private JPanel dataPanel = new JPanel();
  private BorderLayout borderLayout = new BorderLayout();

// The navigation bar

  private JUNavigationBar navBar = new JUNavigationBar();

// Layout used by panel


// Fields for attribute:  EmployeesView1

  private JTable tableEmployeesView1 = new JTable();
  private JScrollPane scroller = new JScrollPane();
  private JPanel jPanel1 = new JPanel();
  private JButton jButton1 = new JButton();
  private JButton jButton2 = new JButton();
  private BorderLayout borderLayout1 = new BorderLayout();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();


  /**
   * 
   *  The default constructor for panel
   */
  public PanelEmployeesView1()
  {
  }

  /**
   * 
   *  the JbInit method
   */
  public void jbInit() throws Exception
  {
    // Layout definition for this panel
    dataPanel.setLayout(borderLayout1);
    tableEmployeesView1.setPreferredScrollableViewportSize(new Dimension(550, 100));
    tableEmployeesView1.setDoubleBuffered(true);
    scroller.setPreferredSize(new Dimension(550, 100));
    scroller.setDoubleBuffered(true);
   // dataPanel.setMinimumSize(new Dimension(100, 100));
    this.setLayout(borderLayout);
    // Code support for view object:  EmployeesView1
    jPanel1.setMinimumSize(new Dimension(10, 40));
    jPanel1.setPreferredSize(new Dimension(10, 50));
    jPanel1.setLayout(gridBagLayout1);
    jButton1.setText("jButton1");
    jButton2.setText("jButton2");
    scroller.getViewport().add(tableEmployeesView1, null);
    // Bind the navigation bar
    dataPanel.add(scroller, BorderLayout.CENTER);
    navBar.setModel(JUNavigationBar.createViewBinding(panelBinding, navBar, "EmployeesView1", null, "EmployeesView1Iterator"));
    // Layout the datapanel and the navigation bar
    add(navBar, BorderLayout.SOUTH);
    add(dataPanel, BorderLayout.CENTER);
    jPanel1.add(jButton2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jButton1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jPanel1, BorderLayout.NORTH);
    jButton1.setModel((ButtonModel)panelBinding.bindUIControl("NextSet", jButton1));
    jButton1.setText("Next Set");
    jButton2.setModel((ButtonModel)panelBinding.bindUIControl("PreviousSet", jButton2));
    jButton2.setText("Previous Set");
    tableEmployeesView1.setModel((TableModel)panelBinding.bindUIControl("EmployeesView1", tableEmployeesView1));


  }

  public static void main(String [] args)
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception exemp)
    {
      exemp.printStackTrace();
    }

    PanelEmployeesView1 panel = new PanelEmployeesView1();
    panel.setBindingContext(JUTestFrame.startTestFrame("DataBindings.cpx", "AppModuleDataControl", panel, panel.getPanelBinding(), new Dimension(400, 300)));
    panel.revalidate();
  }

  /**
   * 
   *  JClientPanel implementation
   */
  public JUPanelBinding getPanelBinding()
  {
    return panelBinding;
  }

  private void unRegisterProjectGlobalVariables(BindingContext bindCtx)
  {
    JUUtil.unRegisterNavigationBarInterface(panelBinding, bindCtx);
  }

  private void registerProjectGlobalVariables(BindingContext bindCtx)
  {
    JUUtil.registerNavigationBarInterface(panelBinding, bindCtx);
  }

  public void setBindingContext(BindingContext bindCtx)
  {
    if (panelBinding.getPanel() == null)
    {
      panelBinding = panelBinding.setup(bindCtx, this);
      registerProjectGlobalVariables(bindCtx);
      panelBinding.refreshControl();
      try
      {
        jbInit();
        panelBinding.refreshControl();
      }
      catch(Exception ex)
      {
        panelBinding.reportException(ex);
      }

    }
  }
}
