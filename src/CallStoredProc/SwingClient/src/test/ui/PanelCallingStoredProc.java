/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.ui;
import java.awt.*;
import javax.swing.*;
import oracle.jbo.uicli.jui.*;
import oracle.jbo.uicli.controls.*;
import oracle.jbo.uicli.binding.*;
import oracle.jdeveloper.layout.*;
import oracle.adf.model.*;
import oracle.adf.model.binding.*;
import java.util.ArrayList;
import java.awt.Dimension;
import javax.swing.JTextField;
import java.awt.Rectangle;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import test.model.common.TestModule;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;

public class PanelCallingStoredProc extends JPanel implements JUPanel  {
// Panel binding definition used by design time
  private JUPanelBinding panelBinding = new JUPanelBinding("PanelCallingStoredProcUIModel");
  private JTextField message = new JTextField();
  private JButton jButton1 = new JButton();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel jLabel1 = new JLabel();

  /**
   * 
   *  The default constructor for panel
   */
  public PanelCallingStoredProc() {
  }

  /**
   * 
   *  the JbInit method
   */
  public void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);
    this.setSize(new Dimension(254, 81));
    jButton1.setText("Call Proc");
    jButton1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jButton1_actionPerformed(e);
        }
      });
    jLabel1.setText("Enter a message:");
    this.add(jButton1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.add(message, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  }

  public static void main(String [] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch(Exception exemp) {
      exemp.printStackTrace();
    }
    PanelCallingStoredProc panel = new PanelCallingStoredProc();
    panel.setBindingContext(JUTestFrame.startTestFrame("DataBindings.cpx", "null", panel, panel.getPanelBinding(), new Dimension(200, 150)));
    panel.revalidate();
  }

  /**
   * 
   *  JUPanel implementation
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

  private void jButton1_actionPerformed(ActionEvent e) {
    TestModule tm = (TestModule)panelBinding.getDataControl().getDataProvider();
    tm.callStoredProcedure(message.getText());
  }
}
