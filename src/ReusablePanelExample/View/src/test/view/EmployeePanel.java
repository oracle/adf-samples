/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
import com.jgoodies.forms.layout.*;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

import oracle.adf.model.*;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.*;

import oracle.jbo.uicli.binding.*;
import oracle.jbo.uicli.controls.*;
import oracle.jbo.uicli.jui.*;

import oracle.jdeveloper.layout.*;
public class EmployeePanel extends JPanel implements JUPanel {
  /**The default constructor for panel
   */
  public EmployeePanel() {
  }
  private JUPanelBinding panelBinding = new JUPanelBinding("EmployeePanelPageDef");
  private JPanel dataPanel = new JPanel();
  private BorderLayout borderLayout = new BorderLayout();
  private FormLayout panelLayout = 
    new FormLayout("3dlu,d:g,3dlu,d:g,3dlu,d:g,3dlu,d:g,3dlu" , "3dlu,d,3dlu,d,3dlu,d,3dlu,d,3dlu");
  private JLabel labelEmpno = new JLabel();
  private JTextField mEmpno = new JTextField();
  private JLabel labelEname = new JLabel();
  private JTextField mEname = new JTextField();
  private JLabel labelJob = new JLabel();
  private JTextField mJob = new JTextField();
  private JLabel labelMgr = new JLabel();
  private JTextField mMgr = new JTextField();
  private JLabel labelHiredate = new JLabel();
  private JTextField mHiredate = new JTextField();
  private JLabel labelSal = new JLabel();
  private JTextField mSal = new JTextField();
  private JLabel labelComm = new JLabel();
  private JTextField mComm = new JTextField();
  private JLabel labelDeptno = new JLabel();
  private JTextField mDeptno = new JTextField();

  /**the JbInit method
   */
  public void jbInit() throws Exception {
    dataPanel.setLayout(panelLayout);
    dataPanel.setMinimumSize(new Dimension(100,100));
    this.setLayout(borderLayout);
    this.setBorder(BorderFactory.createTitledBorder("Reusable Employee Panel"));
    this.setSize(new Dimension(400, 142));
    dataPanel.add(labelEmpno, new CellConstraints("2, 2,1,1,default,"+CellConstraints.FILL));
    dataPanel.add(mEmpno, new CellConstraints("4, 2,1,1,default,"+CellConstraints.FILL));
    labelEmpno.setLabelFor(mEmpno);
    mEmpno.setColumns(15);
    labelEmpno.setText("<html><B>"+panelBinding.findCtrlValueBinding("Empno").getLabel()+"</B></html>");
    mEmpno.setToolTipText(panelBinding.findCtrlValueBinding("Empno").getTooltip());
    dataPanel.add(labelEname, new CellConstraints("6, 2,1,1,default,"+CellConstraints.FILL));
    dataPanel.add(mEname, new CellConstraints("8, 2,1,1,default,"+CellConstraints.FILL));
    labelEname.setLabelFor(mEname);
    mEname.setColumns(15);
    labelEname.setText(panelBinding.findCtrlValueBinding("Ename").getLabel());
    mEname.setToolTipText(panelBinding.findCtrlValueBinding("Ename").getTooltip());
    dataPanel.add(labelJob, new CellConstraints("2, 4,1,1,default,"+CellConstraints.FILL));
    dataPanel.add(mJob, new CellConstraints("4, 4,1,1,default,"+CellConstraints.FILL));
    labelJob.setLabelFor(mJob);
    mJob.setColumns(15);
    labelJob.setText(panelBinding.findCtrlValueBinding("Job").getLabel());
    mJob.setToolTipText(panelBinding.findCtrlValueBinding("Job").getTooltip());
    dataPanel.add(labelMgr, new CellConstraints("6, 4,1,1,default,"+CellConstraints.FILL));
    dataPanel.add(mMgr, new CellConstraints("8, 4,1,1,default,"+CellConstraints.FILL));
    labelMgr.setLabelFor(mMgr);
    mMgr.setColumns(15);
    labelMgr.setText(panelBinding.findCtrlValueBinding("Mgr").getLabel());
    mMgr.setToolTipText(panelBinding.findCtrlValueBinding("Mgr").getTooltip());
    dataPanel.add(labelHiredate, new CellConstraints("2, 6,1,1,default,"+CellConstraints.FILL));
    dataPanel.add(mHiredate, new CellConstraints("4, 6,1,1,default,"+CellConstraints.FILL));
    labelHiredate.setLabelFor(mHiredate);
    mHiredate.setColumns(15);
    labelHiredate.setText(panelBinding.findCtrlValueBinding("Hiredate").getLabel());
    mHiredate.setToolTipText(panelBinding.findCtrlValueBinding("Hiredate").getTooltip());
    dataPanel.add(labelSal, new CellConstraints("6, 6,1,1,default,"+CellConstraints.FILL));
    dataPanel.add(mSal, new CellConstraints("8, 6,1,1,default,"+CellConstraints.FILL));
    labelSal.setLabelFor(mSal);
    mSal.setColumns(15);
    labelSal.setText(panelBinding.findCtrlValueBinding("Sal").getLabel());
    mSal.setToolTipText(panelBinding.findCtrlValueBinding("Sal").getTooltip());
    dataPanel.add(labelComm, new CellConstraints("2, 8,1,1,default,"+CellConstraints.FILL));
    dataPanel.add(mComm, new CellConstraints("4, 8,1,1,default,"+CellConstraints.FILL));
    labelComm.setLabelFor(mComm);
    mComm.setColumns(15);
    labelComm.setText(panelBinding.findCtrlValueBinding("Comm").getLabel());
    mComm.setToolTipText(panelBinding.findCtrlValueBinding("Comm").getTooltip());
    dataPanel.add(labelDeptno, new CellConstraints("6, 8,1,1,default,"+CellConstraints.FILL));
    dataPanel.add(mDeptno, new CellConstraints("8, 8,1,1,default,"+CellConstraints.FILL));
    labelDeptno.setLabelFor(mDeptno);
    mDeptno.setColumns(15);
    labelDeptno.setText(panelBinding.findCtrlValueBinding("Deptno").getLabel());
    mDeptno.setToolTipText(panelBinding.findCtrlValueBinding("Deptno").getTooltip());
    add(dataPanel, BorderLayout.CENTER);
    mEmpno.setDocument((Document)panelBinding.bindUIControl("Empno",mEmpno));
    mEname.setDocument((Document)panelBinding.bindUIControl("Ename",mEname));
    mJob.setDocument((Document)panelBinding.bindUIControl("Job",mJob));
    mMgr.setDocument((Document)panelBinding.bindUIControl("Mgr",mMgr));
    mHiredate.setDocument((Document)panelBinding.bindUIControl("Hiredate",mHiredate));
    mSal.setDocument((Document)panelBinding.bindUIControl("Sal",mSal));
    mComm.setDocument((Document)panelBinding.bindUIControl("Comm",mComm));
    mDeptno.setDocument((Document)panelBinding.bindUIControl("Deptno",mDeptno));
  }
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
    EmployeePanel panel = new EmployeePanel();
    panel.setBindingContext(JUTestFrame.startTestFrame("test.view.DataBindings.cpx", "TestModule1DataControl", panel, panel.getPanelBinding(), new Dimension(400,300)));
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
