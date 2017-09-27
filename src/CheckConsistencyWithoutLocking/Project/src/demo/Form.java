/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.*;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.event.*;

import oracle.adf.model.*;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.*;
import oracle.adf.model.generic.*;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.common.DefLocaleContext;
import oracle.jbo.common.JBOClass;
import oracle.jbo.uicli.*;
import oracle.jbo.uicli.binding.*;
import oracle.jbo.uicli.controls.*;
import oracle.jbo.uicli.jui.*;
import oracle.jbo.uicli.mom.JUMetaObjectManager;
public class Form extends JFrame {
  private BorderLayout borderLayout1 = new BorderLayout();
  private PanelDeptView1Helper panelDeptView1Helper1 = 
    new PanelDeptView1Helper();

  /**The default constructor for form
   */
  public Form() {
    addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent e) {int action = _popupTransactionDialog();
if (action!=JOptionPane.CLOSED_OPTION){ panelBinding.releaseDataControl(); statusBar.release();System.exit(0);} } });
  }
  private GridLayout gridLayout = new GridLayout();
  private BorderLayout borderLayout = new BorderLayout();
  private JUPanelBinding panelBinding = new JUPanelBinding("FormPageDef");
  private JUStatusBar statusBar = new JUStatusBar();
  private JPanel topPanel = new JPanel();
  private JPanel dataPanel = new JPanel();
  private JMenuBar menubarFrame = new JMenuBar();
  private JUNavigationBar hiddenNavBar = new JUNavigationBar() { protected void _updateButtonStates() { super._updateButtonStates(); menuItemsUpdate(); }};
  private JMenu menuFile = new JMenu();
  private JMenuItem itemFileExit = new JMenuItem();
  private JMenu menuDatabase = new JMenu();
  private JMenuItem itemDatabaseFirst = new JMenuItem();
  private JMenuItem itemDatabasePrevious = new JMenuItem();
  private JMenuItem itemDatabaseNext = new JMenuItem();
  private JMenuItem itemDatabaseLast = new JMenuItem();
  private JMenuItem itemDatabaseInsert = new JMenuItem();
  private JMenuItem itemDatabaseDelete = new JMenuItem();
  private JMenuItem itemDatabaseCommit = new JMenuItem();
  private JMenuItem itemDatabaseRollback = new JMenuItem();
  private JMenuItem itemFindMode = new JMenuItem();
  private JMenuItem itemExecute = new JMenuItem();
  private JMenu menuHelp = new JMenu();
  private JMenuItem itemHelpAbout = new JMenuItem();
  private String aboutMessage = "about message";
  private String aboutTitle = "about title ";
  private void registerProjectGlobalVariables(BindingContext bindCtx) {
    JUUtil.registerNavigationBarInterface(panelBinding, bindCtx);
  }
  private void unRegisterProjectGlobalVariables(BindingContext bindCtx) {
    JUUtil.unRegisterNavigationBarInterface(panelBinding, bindCtx);
  }

  /**the JbInit method
   */
  public void jbInit() throws Exception {
    panelDeptView1Helper1.bindNestedContainer(panelBinding.findNestedPanelBinding("PanelDeptView1HelperPageDef"));
    dataPanel.setLayout(borderLayout1);
    this.getContentPane().setLayout(gridLayout);
    topPanel.setLayout(borderLayout);
    this.getContentPane().add(topPanel);
    this.setSize(new Dimension(450, 218));
    dataPanel.add(panelDeptView1Helper1, BorderLayout.CENTER);
    topPanel.add(dataPanel, BorderLayout.CENTER);
    topPanel.add(statusBar, BorderLayout.SOUTH);
    setJMenuBar(menubarFrame);
    itemFileExit.setText("Exit");
    itemFileExit.setMnemonic('X');
    itemFileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 
                                                       Event.ALT_MASK, false));
    itemFileExit.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            file_exit_action(e);
          }
        });
    menuFile.add(itemFileExit);
    menuFile.setText("File");
    menuFile.setMnemonic('F');
    menubarFrame.add(menuFile);
    menuDatabase.setText("Database");
    menuDatabase.setMnemonic('D');
    itemDatabaseFirst.setText("First");
    itemDatabaseFirst.setMnemonic('F');
    itemDatabaseFirst.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 
                                                            Event.ALT_MASK, 
                                                            false));
    itemDatabaseFirst.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            first_action(e);
          }
        });
    menuDatabase.add(itemDatabaseFirst);
    itemDatabasePrevious.setText("Previous");
    itemDatabasePrevious.setMnemonic('P');
    itemDatabasePrevious.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 
                                                               Event.ALT_MASK, 
                                                               false));
    itemDatabasePrevious.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            previous_action(e);
          }
        });
    menuDatabase.add(itemDatabasePrevious);
    itemDatabaseNext.setText("Next");
    itemDatabaseNext.setMnemonic('N');
    itemDatabaseNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 
                                                           Event.ALT_MASK, 
                                                           false));
    itemDatabaseNext.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            next_action(e);
          }
        });
    menuDatabase.add(itemDatabaseNext);
    itemDatabaseLast.setText("Last");
    itemDatabaseLast.setMnemonic('L');
    itemDatabaseLast.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_END, 
                                                           Event.ALT_MASK, 
                                                           false));
    itemDatabaseLast.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            last_action(e);
          }
        });
    menuDatabase.add(itemDatabaseLast);
    menuDatabase.addSeparator();
    itemDatabaseInsert.setText("Insert");
    itemDatabaseInsert.setMnemonic('I');
    itemDatabaseInsert.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 
                                                             Event.ALT_MASK, 
                                                             false));
    itemDatabaseInsert.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            insert_action(e);
          }
        });
    menuDatabase.add(itemDatabaseInsert);
    itemDatabaseDelete.setText("Delete");
    itemDatabaseDelete.setMnemonic('D');
    itemDatabaseDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 
                                                             Event.ALT_MASK, 
                                                             false));
    itemDatabaseDelete.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            delete_action(e);
          }
        });
    menuDatabase.add(itemDatabaseDelete);
    menuDatabase.addSeparator();
    itemDatabaseCommit.setText("Commit");
    itemDatabaseCommit.setMnemonic('C');
    itemDatabaseCommit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 
                                                             Event.ALT_MASK, 
                                                             false));
    itemDatabaseCommit.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            commit_action(e);
          }
        });
    menuDatabase.add(itemDatabaseCommit);
    itemDatabaseRollback.setText("Rollback");
    itemDatabaseRollback.setMnemonic('R');
    itemDatabaseRollback.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 
                                                               Event.ALT_MASK, 
                                                               false));
    itemDatabaseRollback.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            rollback_action(e);
          }
        });
    menuDatabase.add(itemDatabaseRollback);
    menuDatabase.addSeparator();
    itemFindMode.setText("Query Mode");
    itemFindMode.setMnemonic('Q');
    itemFindMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 
                                                       Event.ALT_MASK, false));
    itemFindMode.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            find_action(e);
          }
        });
    menuDatabase.add(itemFindMode);
    itemExecute.setText("Execute Query");
    itemExecute.setMnemonic('E');
    itemExecute.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, 
                                                      Event.ALT_MASK, false));
    itemExecute.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            execute_action(e);
          }
        });
    menuDatabase.add(itemExecute);
    menubarFrame.add(menuDatabase);
    menuHelp.setText("Help");
    menuHelp.setMnemonic('H');
    itemHelpAbout.setText("About");
    itemHelpAbout.setMnemonic('A');
    itemHelpAbout.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            help_about_action(e);
          }
        });
    menuHelp.add(itemHelpAbout);
    menubarFrame.add(menuHelp);
    hiddenNavBar.setModel(JUNavigationBar.createPanelBinding(panelBinding, hiddenNavBar));
    statusBar.setModel(JUStatusBar.createPanelBinding(panelBinding, statusBar));
  }
  private void first_action(ActionEvent e) {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_FIRST);
  }
  private void previous_action(ActionEvent e) {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_PREV);
  }
  private void next_action(ActionEvent e) {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_NEXT);
  }
  private void last_action(ActionEvent e) {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_LAST);
  }
  private void insert_action(ActionEvent e) {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_INSERT);
  }
  private void delete_action(ActionEvent e) {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_DELETE);
  }
  private void commit_action(ActionEvent e) {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_COMMIT);
  }
  private void rollback_action(ActionEvent e) {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_ROLLBACK);
  }
  private void find_action(ActionEvent e) {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_FIND);
  }
  private void execute_action(ActionEvent e) {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_EXECUTE);
  }
  private void file_exit_action(ActionEvent e) {
    int action = _popupTransactionDialog();
    if (action != JOptionPane.CLOSED_OPTION) {
      System.exit(0);
    }
  }
  private void help_about_action(ActionEvent e) {
    JOptionPane.showMessageDialog(this, aboutMessage, aboutTitle,                          JOptionPane.INFORMATION_MESSAGE);
  }
  private void menuItemsUpdate() {
    itemDatabaseFirst.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_FIRST));
    itemDatabasePrevious.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_PREV));
    itemDatabaseLast.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_LAST));
    itemDatabaseNext.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_NEXT));
    itemDatabaseInsert.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_INSERT));
    itemDatabaseDelete.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_DELETE));
    itemDatabaseCommit.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_COMMIT));
    itemDatabaseRollback.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_ROLLBACK));
    itemFindMode.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_FIND));
    itemExecute.setEnabled(hiddenNavBar.isButtonActive(JUNavigationBar.BUTTON_EXECUTE));
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
    try {
      JUMetaObjectManager.setErrorHandler(new JUErrorHandlerDlg());
      JUMetaObjectManager mgr = JUMetaObjectManager.getJUMom();
      mgr.setJClientDefFactory(null);
      BindingContext ctx = new BindingContext();
      ctx.put(DataControlFactory.APP_PARAM_ENV_INFO, new JUEnvInfoProvider());
      ctx.setLocaleContext(new DefLocaleContext(null));
      HashMap map = new HashMap(4);
      map.put(DataControlFactory.APP_PARAMS_BINDING_CONTEXT, ctx);
      mgr.loadCpx("demo.DataBindings.cpx" , map);
      final Form frame = new Form();
      frame.setBindingContext(ctx);
      frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = frame.getSize();
      if (frameSize.height > screenSize.height) {
        frameSize.height = screenSize.height;
      }
      if (frameSize.width > screenSize.width) {
        frameSize.width = screenSize.width;
      }
      frame.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
      frame.setVisible(true);
    } catch (Exception ex) {
      JUMetaObjectManager.reportException(null, ex, true);
      System.exit(1);
    }
  }
  private int _popupTransactionDialog() {
    if (panelBinding == null || panelBinding.getPanel() == null) {
      return JOptionPane.NO_OPTION;
    }
    if (panelBinding.isTransactionDirty()) {
      JButton commitButton = new JButton("Commit");
      commitButton.setMnemonic('C');
      JButton rollBackButton = new JButton("Rollback");
      rollBackButton.setMnemonic('R');
      Object [] options = {commitButton , rollBackButton};
      final JOptionPane optionPane = 
        new JOptionPane("How do you want to close the transaction?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options, options[0]);
      commitButton.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
 optionPane.setValue(new Integer(JOptionPane.YES_OPTION));
}
});
      rollBackButton.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
 optionPane.setValue(new Integer(JOptionPane.NO_OPTION));
}
});
      optionPane.createDialog(Form.this, "Transaction open").setVisible(true);
      int action = JOptionPane.CLOSED_OPTION;
      Object  obj = optionPane.getValue();
      if (obj!=null) {
        action = ((Integer)optionPane.getValue()).intValue();
      }
      switch (action) {
      case JOptionPane.NO_OPTION:
        hiddenNavBar.doAction(JUNavigationBar.BUTTON_ROLLBACK);
        break;
      case JOptionPane.CLOSED_OPTION:
        break;
      case JOptionPane.YES_OPTION:
      default:
        hiddenNavBar.doAction(JUNavigationBar.BUTTON_COMMIT);
        break;
      }
      return action;
    }
    return JOptionPane.NO_OPTION;
  }
  public JUPanelBinding getPanelBinding() {
    return panelBinding;
  }
  public void setPanelBinding(JUPanelBinding binding) {
    if (binding.getPanel() == null) {
      binding.setPanel(topPanel);
    }
    if (panelBinding == null || panelBinding.getPanel() == null) {
      try {
        panelBinding = binding;
        jbInit();
      } catch (Exception ex) {
        panelBinding.reportException(ex);
      }
    }
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
}
