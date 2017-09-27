/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
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
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.ButtonModel;
import oracle.jbo.uicli.controls.JULabel;
import oracle.jbo.uicli.jui.JULabelBinding;
import java.awt.Font;
import java.awt.Dimension;

public class PanelNonDatabaseBlockView extends JPanel implements JUPanel  {
  // Panel binding definition used by design time
  private JUPanelBinding panelBinding = new JUPanelBinding("PanelNonDatabaseBlockViewUIModel");

// Panel containing the data entry fields

  private JPanel dataPanel = new JPanel();
  private BorderLayout borderLayout = new BorderLayout();

// Layout used by panel

  private GridBagLayout panelLayout = new GridBagLayout();

// Fields for attribute:  BeginDate

  private JLabel labelBeginDate = new JLabel();
  private JTextField mBeginDate = new JTextField();

// Fields for attribute:  EndDate

  private JLabel labelEndDate = new JLabel();
  private JTextField mEndDate = new JTextField();

// Fields for attribute:  AnotherValue

  private JLabel labelAnotherValue = new JLabel();
  private JTextField mAnotherValue = new JTextField();
  private JButton jButton1 = new JButton();
  private JULabel jULabel1 = new JULabel();
  private JButton jButton2 = new JButton();
  private JULabel jULabel2 = new JULabel();


  /**
   * 
   *  The default constructor for panel
   */
  public PanelNonDatabaseBlockView() {
  }

  /**
   * 
   *  the JbInit method
   */
  public void jbInit() throws Exception {
    // Layout definition for this panel
    dataPanel.setLayout(panelLayout);
    dataPanel.setMinimumSize(new Dimension(100, 100));
    this.setLayout(borderLayout);
    mBeginDate.setDocument((Document)panelBinding.bindUIControl("BeginDate", mBeginDate));
    jButton1.setText("jButton1");
    jULabel1.setFont(new Font("Tahoma", 1, 11));
    jULabel1.setPreferredSize(new Dimension(10, 20));
    jButton2.setText("jButton2");
    jULabel2.setText("jULabel2");
    dataPanel.add(labelBeginDate, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 1, 1));
    dataPanel.add(mBeginDate, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 1, 1));
    labelBeginDate.setLabelFor(mBeginDate);
    mBeginDate.setColumns(15);
    labelBeginDate.setText(panelBinding.findCtrlValueBinding("BeginDate").getLabel());
    mBeginDate.setToolTipText(panelBinding.findCtrlValueBinding("BeginDate").getTooltip());
    mEndDate.setDocument((Document)panelBinding.bindUIControl("EndDate", mEndDate));
    dataPanel.add(labelEndDate, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 1, 1));
    dataPanel.add(mEndDate, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 1, 1));
    labelEndDate.setLabelFor(mEndDate);
    mEndDate.setColumns(15);
    labelEndDate.setText(panelBinding.findCtrlValueBinding("EndDate").getLabel());
    mEndDate.setToolTipText(panelBinding.findCtrlValueBinding("EndDate").getTooltip());
    mAnotherValue.setDocument((Document)panelBinding.bindUIControl("AnotherValue", mAnotherValue));
    dataPanel.add(labelAnotherValue, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 1, 1));
    dataPanel.add(mAnotherValue, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 1, 1));
    dataPanel.add(jButton1, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    dataPanel.add(jULabel1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    dataPanel.add(jButton2, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    dataPanel.add(jULabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    labelAnotherValue.setLabelFor(mAnotherValue);
    mAnotherValue.setColumns(15);
    labelAnotherValue.setText(panelBinding.findCtrlValueBinding("AnotherValue").getLabel());

    // Layout the datapanel and the navigation bar
    mAnotherValue.setToolTipText(panelBinding.findCtrlValueBinding("AnotherValue").getTooltip());
    add(dataPanel, BorderLayout.CENTER);
    jButton1.setModel((ButtonModel)panelBinding.bindUIControl("registerBeginEndDateAndOtherValue", jButton1));
    jButton1.setText("registerBeginEndDateAndOtherValue");
    jULabel1.setModel((JULabelBinding)panelBinding.bindUIControl("ResultString", jULabel1));
    jButton2.setModel((ButtonModel)panelBinding.bindUIControl("registerBeginEndDateAndOtherValueWithResult", jButton2));
    jButton2.setText("registerBeginEndDateAndOtherValueWithResult");
    jULabel2.setText(panelBinding.findCtrlValueBinding("ResultString1").getLabel());


  }

  public static void main(String [] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch(Exception exemp) {
      exemp.printStackTrace();
    }
    PanelNonDatabaseBlockView panel = new PanelNonDatabaseBlockView();
    panel.setBindingContext(JUTestFrame.startTestFrame("DataBindings.cpx", "ExampleModuleDataControl", panel, panel.getPanelBinding(), new Dimension(400, 300)));
    panel.revalidate();
  }

  /**
   * 
   *  JClientPanel implementation
   */
  public JUPanelBinding getPanelBinding() {
    return panelBinding;
  }

  private void unRegisterProjectGlobalVariables(BindingContext bindCtx) {
    JUUtil.unRegisterNavigationBarInterface(panelBinding, bindCtx);
  }

  private void registerProjectGlobalVariables(BindingContext bindCtx) {
    JUUtil.registerNavigationBarInterface(panelBinding, bindCtx);
  }

  public void setBindingContext(BindingContext bindCtx) {
    if (panelBinding.getPanel() == null) {
      panelBinding = panelBinding.setup(bindCtx, this);
      registerProjectGlobalVariables(bindCtx);
      panelBinding.refreshControl();
      try {
        jbInit();
        panelBinding.refreshControl();
      } catch(Exception ex) {
        panelBinding.reportException(ex);
      }
    }
  }
}
