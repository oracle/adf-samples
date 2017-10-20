/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.client;
import java.awt.*;
import javax.swing.*;
import oracle.jbo.uicli.jui.*;
import oracle.jbo.uicli.controls.*;
import oracle.jbo.uicli.binding.*;
import oracle.jdeveloper.layout.*;
import oracle.adf.model.*;
import oracle.adf.model.binding.*;
import java.util.ArrayList;
import javax.swing.JTextField;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import test.model.beans.Customer;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Panel1 extends JPanel implements JUPanel  {
// Panel binding definition used by design time
  private JUPanelBinding panelBinding = new JUPanelBinding("Panel1UIModel");
  private JTextField jTextField1 = new JTextField();
  private JButton jButton1 = new JButton();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable jTable1 = new JTable();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();

  /**
   * 
   *  The default constructor for panel
   */
  public Panel1() {
  }

  /**
   * 
   *  the JbInit method
   */
  public void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);
    jTextField1.setText("1");
    jButton1.setText("Go");
    jButton1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jButton1_actionPerformed(e);
        }
      });
    jScrollPane1.getViewport().add(jTable1, null);
    this.add(jScrollPane1, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jButton1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jTextField1, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    jTable1.setModel((TableModel)panelBinding.bindUIControl("findAllOrdersByCustomer1", jTable1));
  }

  public static void main(String [] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch(Exception exemp) {
      exemp.printStackTrace();
    }
    Panel1 panel = new Panel1();
    panel.setBindingContext(JUTestFrame.startTestFrame("DataBindings.cpx", "null", panel, panel.getPanelBinding(), new Dimension(400, 300)));
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

  private JUCtrlActionBinding findActionBinding(String s) {
    return (JUCtrlActionBinding)getPanelBinding().findControlBinding(s);
  }
  private Object invokeActionBinding(String s,ArrayList params) {
    JUCtrlActionBinding action = findActionBinding(s);
    action.setParams(params);
    action.invoke();
    return action.getResult();
  }
  private Object invokeActionBinding(String s,Object param) {
    JUCtrlActionBinding action = findActionBinding(s);
    ArrayList params = new ArrayList(1);
    params.add(param);
    action.setParams(params);
    action.invoke();
    return action.getResult();
  }
  private void executeMethodIterator(String iterName,String actionName,Object param) {
    JUCtrlActionBinding action = findActionBinding(actionName);
    ArrayList params = new ArrayList(1);
    params.add(param);
    action.setParams(params);
    getPanelBinding().findIteratorBinding(iterName).executeQuery();
  }  
  private void jButton1_actionPerformed(ActionEvent e) {
    Customer c = new Customer();
    c.setId(Integer.parseInt(jTextField1.getText()));
    executeMethodIterator("findAllOrdersByCustomerIter","findAllOrdersByCustomer",c);
  }
}
