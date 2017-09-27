/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.*;
import java.awt.FlowLayout;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Document;

import oracle.adf.model.*;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.*;

import oracle.jbo.uicli.binding.*;
import oracle.jbo.uicli.binding.JUIteratorBinding;
import oracle.jbo.uicli.controls.*;
import oracle.jbo.uicli.controls.JULabel;
import oracle.jbo.uicli.controls.JUNavigationBar;
import oracle.jbo.uicli.jui.*;
import oracle.jbo.uicli.jui.JULabelBinding;

import oracle.jdeveloper.layout.*;
public class Panel3 extends JPanel implements JUPanel {
  private FormLayout formLayout1 = 
    new FormLayout("f:3dlu:n, f:max(d;20dlu):g, f:3dlu:n, f:max(d;20dlu):g, f:3dlu:n, f:max(d;20dlu):n, f:3dlu:n, f:max(d;20dlu):g, f:3dlu:n", "c:3dlu:n, c:max(d;15dlu):n, c:3dlu:n, c:max(d;15dlu):n, c:3dlu:n, c:max(d;15dlu):n, c:3dlu:n, c:max(d;15dlu):n, c:3dlu:n, c:max(d;15dlu):g, c:3dlu:n, c:max(d;15dlu):n, c:3dlu:n");
  private JUNavigationBar jUNavigationBar1 = new JUNavigationBar();
  private JTextField jTextField1 = new JTextField();
  private JULabel jULabel1 = new JULabel();
  private JTextField jTextField2 = new JTextField();
  private JULabel jULabel2 = new JULabel();
  private JUNavigationBar jUNavigationBar2 = new JUNavigationBar();
  private EmployeePanel employeePanel1 = new EmployeePanel();
  private JPanel jPanel1 = new JPanel();
  private JULabel jULabel3 = new JULabel();

  /**The default constructor for panel
   */
  public Panel3() {
  }

  /**the JbInit method
   */
  public void jbInit() throws Exception {
    employeePanel1.bindNestedContainer(panelBinding.findNestedPanelBinding("EmployeePanelPageDef"));
    this.setLayout(formLayout1);
    this.setSize(new Dimension(561, 300));
    this.add(jUNavigationBar1, 
             new CellConstraints(2, 4, 7, 1, CellConstraints.DEFAULT, 
                                 CellConstraints.DEFAULT));
    this.add(jTextField1, 
             new CellConstraints(4, 6, 1, 1, CellConstraints.DEFAULT, 
                                 CellConstraints.DEFAULT));this.add(jULabel1, 
             new CellConstraints(2, 6, 1, 1, CellConstraints.DEFAULT, CellConstraints.DEFAULT));this.add(jTextField2, 
             new CellConstraints(8, 6, 1, 1, CellConstraints.DEFAULT, 
                                 CellConstraints.DEFAULT));this.add(jULabel2, 
             new CellConstraints(6, 6, 1, 1, CellConstraints.DEFAULT, CellConstraints.DEFAULT));this.add(jUNavigationBar2, 
             new CellConstraints(2, 8, 7, 1, CellConstraints.DEFAULT, 
                                 CellConstraints.DEFAULT));this.add(employeePanel1, 
             new CellConstraints(2, 10, 8, 3, CellConstraints.DEFAULT, 
                                 CellConstraints.DEFAULT));jPanel1.add(jULabel3, null);
    this.add(jPanel1, 
             new CellConstraints(2, 2, 7, 1, CellConstraints.DEFAULT, CellConstraints.DEFAULT));
    jUNavigationBar1.setModel(JUNavigationBar.createViewBinding(panelBinding, jUNavigationBar1, "Departments", null, "DepartmentsIterator"));jTextField1.setToolTipText((panelBinding.findCtrlValueBinding("DepartmentsDeptno")).getTooltip());
    jTextField1.setColumns((panelBinding.findCtrlValueBinding("DepartmentsDeptno")).getDisplayWidth());
    jULabel1.setText(panelBinding.findCtrlValueBinding("DepartmentsDeptno_1").getLabel());
    jTextField2.setToolTipText((panelBinding.findCtrlValueBinding("DepartmentsDname")).getTooltip());
    jTextField2.setColumns((panelBinding.findCtrlValueBinding("DepartmentsDname")).getDisplayWidth());
    jULabel2.setText(panelBinding.findCtrlValueBinding("DepartmentsDname_1").getLabel());
    jUNavigationBar2.setModel(JUNavigationBar.createViewBinding(panelBinding, jUNavigationBar2, "EmployeesInDepartment", null, "EmployeesInDepartmentIterator"));jPanel1.setPreferredSize(new Dimension(30, 30));
    jPanel1.setBackground(new Color(230, 230, 0));
    jULabel3.setText("This panel reuses EmployeePanel for the detail VO instance TestModule2DataControl.EmployeesInDepartment");
    jTextField1.setDocument((Document)panelBinding.bindUIControl("DepartmentsDeptno",jTextField1));
    jTextField2.setDocument((Document)panelBinding.bindUIControl("DepartmentsDname",jTextField2));
  }
  private JUPanelBinding panelBinding = new JUPanelBinding("Panel3PageDef");
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
    Panel3 panel = new Panel3();
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
