/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
import com.jgoodies.forms.layout.*;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.table.*;
import javax.swing.text.*;

import oracle.adf.model.*;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.*;
import oracle.jbo.ApplicationModule;
import oracle.jbo.uicli.binding.*;
import oracle.jbo.uicli.controls.*;
import oracle.jbo.uicli.jui.*;

import oracle.jdeveloper.layout.*;
public class PanelDeptView1Helper extends JPanel implements JUPanel {
  private JCheckBox jCheckBox1 = new JCheckBox();

  /**The default constructor for panel
   */
  public PanelDeptView1Helper() {
    this.setLayout(borderLayout);
  }

  /**the JbInit method
   */
  public void jbInit() throws Exception {
    dataPanel.setLayout(panelLayout);
    dataPanel.setMinimumSize(new Dimension(100,100));
    mDeptno.setToolTipText((panelBinding.findCtrlValueBinding("DeptView1Deptno")).getTooltip());
    mDeptno.setColumns(panelBinding.findCtrlValueBinding("DeptView1Deptno").getDisplayWidth());
    dataPanel.add(labelmDeptno, 
                  new CellConstraints(2, 2, 1, 1, CellConstraints.DEFAULT, 
                                      CellConstraints.FILL));
    labelmDeptno.setLabelFor(mDeptno);
    dataPanel.add(mDeptno, 
                  new CellConstraints(4, 2, 1, 1, CellConstraints.DEFAULT, 
                                      CellConstraints.FILL));
    mDeptno.setColumns(15);
    labelmDeptno.setText(panelBinding.findCtrlValueBinding("DeptView1Deptno").getLabel());
    mDname.setToolTipText((panelBinding.findCtrlValueBinding("DeptView1Dname")).getTooltip());
    mDname.setColumns(panelBinding.findCtrlValueBinding("DeptView1Dname").getDisplayWidth());
    dataPanel.add(labelmDname, 
                  new CellConstraints(2, 4, 1, 1, CellConstraints.DEFAULT, 
                                      CellConstraints.FILL));
    labelmDname.setLabelFor(mDname);
    dataPanel.add(mDname, 
                  new CellConstraints(4, 4, 1, 1, CellConstraints.DEFAULT, 
                                      CellConstraints.FILL));
    mDname.setColumns(15);
    labelmDname.setText(panelBinding.findCtrlValueBinding("DeptView1Dname").getLabel());
    mLoc.setToolTipText((panelBinding.findCtrlValueBinding("DeptView1Loc")).getTooltip());
    mLoc.setColumns(panelBinding.findCtrlValueBinding("DeptView1Loc").getDisplayWidth());
    dataPanel.add(labelmLoc, 
                  new CellConstraints(2, 6, 1, 1, CellConstraints.DEFAULT, 
                                      CellConstraints.FILL));dataPanel.add(mLoc,
                  new CellConstraints(4, 6, 1, 1, CellConstraints.DEFAULT,
                                      CellConstraints.FILL));dataPanel.add(jCheckBox1,
                  new CellConstraints(4, 7, 1, 1, CellConstraints.DEFAULT,
                                      CellConstraints.DEFAULT));
    labelmLoc.setLabelFor(mLoc);
    mLoc.setColumns(15);
    jCheckBox1.setText("Selected?");
    jCheckBox1.setToolTipText((panelBinding.findCtrlValueBinding("DeptView1Selected")).getTooltip());
    labelmLoc.setText(panelBinding.findCtrlValueBinding("DeptView1Loc").getLabel());
    navBar.setModel(JUNavigationBar.createViewBinding(panelBinding, navBar, "", 
                                                      null, 
                                                      "DeptView1Iterator"));
    add(navBar, BorderLayout.NORTH);
    add(dataPanel, BorderLayout.CENTER);
    mDeptno.setDocument((Document)panelBinding.bindUIControl("DeptView1Deptno",mDeptno));
    mDname.setDocument((Document)panelBinding.bindUIControl("DeptView1Dname",mDname));
    mLoc.setDocument((Document)panelBinding.bindUIControl("DeptView1Loc",mLoc));
    jCheckBox1.setModel((ButtonModel)panelBinding.bindUIControl("DeptView1Selected",jCheckBox1));
  }
  private JUPanelBinding panelBinding = new JUPanelBinding("PanelDeptView1HelperPageDef");
  private JPanel dataPanel = new JPanel();
  private BorderLayout borderLayout = new BorderLayout();
  private JUNavigationBar navBar = new JUNavigationBar();
  private FormLayout panelLayout = new FormLayout("f:3dlu:n, r:d:g, f:5dlu:n, f:d:g, f:5dlu:n", "c:3dlu:n, c:d:n, c:3dlu:n, c:d:n, c:3dlu:n, c:d:n, c:max(d;15dlu):n, c:3dlu:n, c:max(d;15dlu):n");
  private JLabel labelmDeptno = new JLabel();
  private JTextField mDeptno = new JTextField();
  private JLabel labelmDname = new JLabel();
  private JTextField mDname = new JTextField();
  private JLabel labelmLoc = new JLabel();
  private JTextField mLoc = new JTextField();
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
    PanelDeptView1Helper panel = new PanelDeptView1Helper();
    panel.setBindingContext(JUTestFrame.startTestFrame("test.view.DataBindings.cpx", "null", panel, panel.getPanelBinding(), new Dimension(400,300)));
    panel.revalidate();
  }

  /**ADF Swing Panel implementation
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
