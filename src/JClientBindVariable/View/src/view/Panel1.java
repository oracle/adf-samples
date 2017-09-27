/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view;
import java.awt.*;
import javax.swing.*;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.jui.*;
import oracle.jbo.uicli.controls.*;
import oracle.jbo.uicli.binding.*;
import oracle.jbo.uicli.jui.JUActionBindingAdapter;
import oracle.jdeveloper.layout.*;
import oracle.adf.model.*;
import oracle.adf.model.binding.*;
import java.util.ArrayList;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonModel;
import java.awt.Dimension;

public class Panel1 extends JPanel implements JUPanel 
{
// Panel binding definition used by design time
  private JUPanelBinding panelBinding = new JUPanelBinding("Panel1UIModel");
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTextField jTextField1 = new JTextField();
  private JTable jTable1 = new JTable();
  private JLabel jLabel1 = new JLabel();
  private JButton jButton2 = new JButton();

  /**
   * 
   *  The default constructor for panel
   */
  public Panel1()
  {
  }

  /**
   * 
   *  the JbInit method
   */
  public void jbInit() throws Exception
  {
    this.setLayout(gridBagLayout1);
    jTextField1.setSize(new Dimension(100, 19));
    jLabel1.setText("Department Name Match:");
    jButton2.setText("jButton2");
    jScrollPane1.getViewport().add(jTable1, null);
    this.add(jScrollPane1, new GridBagConstraints(0, 1, 3, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jTextField1, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jButton2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jTable1.setModel((TableModel)panelBinding.bindUIControl("DeptView1", jTable1));
    jButton2.setModel((ButtonModel)panelBinding.bindUIControl("Execute", jButton2));
    jButton2.setText("Go!");
    /*
     * ADDED CODE #2
     * 
     * Add an action binding listener to the "Go!" button which is
     * declaratively bound to the "Execute" action binding, which is
     * in-turn set to use the built-in Execute operation to cause the
     * view object to execute it's query.
     */
     ((JUActionBinding)panelBinding.findControlBinding("Execute")).addActionBindingListener(
       new JUActionBindingAdapter() 
       {
         public void beforeActionPerformed(JUActionBindingEvent ev)
         {
            // any before action-binding-executed logic goes here.            
            ViewObject vo = panelBinding.findIterBinding("DeptView1Iterator").getViewObject();
            vo.setMaxFetchSize(-1);
            vo.setWhereClauseParam(0,jTextField1.getText());
         }
         public void afterActionPerformed(JUActionBindingEvent ev)
         {
            // any after action-binding-executed logic goes here.
         }         
       }
     );
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

    Panel1 panel = new Panel1();
    panel.setBindingContext(JUTestFrame.startTestFrame("DataBindings.cpx", "null", panel, panel.getPanelBinding(), new Dimension(500, 200)));
    panel.revalidate();
  }

  /**
   * 
   *  JUPanel implementation
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
      /*
       * ADDED CODE #1
       * Initially set max fetch size to 0 to stop any query from happening.
       * It's important you do this just before the refreshControl() call
       * which is responsible for refreshing the iterators in the binding
       * container if they haven't been yet.
       */
      panelBinding.findIterBinding("DeptView1Iterator").getViewObject().setMaxFetchSize(0);
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
