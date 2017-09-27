/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
import java.awt.*;
import java.awt.BorderLayout;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.JPanel;

import oracle.adf.model.*;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.*;

import oracle.jbo.uicli.binding.*;
import oracle.jbo.uicli.controls.*;
import oracle.jbo.uicli.controls.JULabel;
import oracle.jbo.uicli.jui.*;

import oracle.jdeveloper.layout.*;
public class Panel2 extends JPanel implements JUPanel {
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private EmployeePanel employeePanel1 = new EmployeePanel();
  private JULabel jULabel1 = new JULabel();

  /**The default constructor for panel
   */
  public Panel2() {
  }

  /**the JbInit method
   */
  public void jbInit() throws Exception {
    employeePanel1.bindNestedContainer(panelBinding.findNestedPanelBinding("EmployeePanelPageDef"));
    this.setLayout(borderLayout1);
    jPanel1.setBackground(new Color(222, 181, 255));
    jPanel1.setPreferredSize(new Dimension(30, 30));
    jULabel1.setText("This panel reuses EmployeePanel with TestDataControl2.AllEmployees");
    jPanel1.add(jULabel1, null);
    this.add(jPanel1, BorderLayout.NORTH);
    this.add(employeePanel1, BorderLayout.CENTER);
  }
  private JUPanelBinding panelBinding = new JUPanelBinding("Panel2PageDef");
  public static void main(String [] args) {
    try {
      UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
    } catch (ClassNotFoundException cnfe) {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception exemp) {
        exemp.printStackTrace();
      }
    } catch (Exception exemp) {
      exemp.printStackTrace();
    }
    Panel2 panel = new Panel2();
    panel.setBindingContext(JUTestFrame.startTestFrame("test.view.DataBindings.cpx", "null", panel, panel.getPanelBinding(), new Dimension(400,300)));
    panel.revalidate();
  }

  /**JUPanel implementation
   */
  public JUPanelBinding getPanelBinding() {
    return panelBinding;
  }
  public void bindNestedContainer(JUPanelBinding ctr) {
    if (panelBinding.getPanel() == null) {
      ctr.setPanel(this);
      panelBinding.release(DCDataControl.REL_VIEW_REFS);
      panelBinding = ctr;
      registerProjectGlobalVariables(panelBinding.getBindingContext());
      try {
        jbInit();
      } catch (Exception ex) {
        ex.printStackTrace();
        ctr.reportException(ex);
      }
    }
  }
  private void registerProjectGlobalVariables(BindingContext bindCtx) {
    JUUtil.registerNavigationBarInterface(panelBinding, bindCtx);
  }
  private void unRegisterProjectGlobalVariables(BindingContext bindCtx) {
    JUUtil.unRegisterNavigationBarInterface(panelBinding, bindCtx);
  }
  public void setBindingContext(BindingContext bindCtx) {
    if (panelBinding.getPanel() == null) {
      panelBinding = panelBinding.setup(bindCtx, this);
      registerProjectGlobalVariables(bindCtx);
      panelBinding.refreshControl();
      try {
        jbInit();
        panelBinding.refreshControl();
      } catch (Exception ex) {
        panelBinding.reportException(ex);
      }
    }
  }
}
