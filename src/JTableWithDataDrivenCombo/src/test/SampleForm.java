/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import oracle.jbo.RowIterator;
import oracle.jbo.Row;
import oracle.jbo.uicli.mom.JUMetaObjectManager;
import oracle.jbo.uicli.binding.*;
import oracle.jbo.uicli.controls.*;
import oracle.jbo.uicli.*;
import oracle.jbo.uicli.jui.*;
import java.awt.BorderLayout;
import oracle.jbo.uicli.controls.dataPanel;
import java.awt.Dimension;

public class SampleForm extends JFrame 
{
  // form layout
  private GridLayout gridLayout = new GridLayout();
  private BorderLayout borderLayout = new BorderLayout();

  // The form's top panel

  private JPanel topPanel = new JPanel();


  private JPanel dataPanel = new JPanel();

  // panel definition used in design time

  private JUPanelBinding panelBinding = new JUPanelBinding("JTableWithDataDrivenCombo.TestModule", null);

// Navigation bar

// the statusbar
  private JUStatusBar statusBar = new JUStatusBar();
// menu definitions
  private JMenuBar menubarFrame = new JMenuBar();
  private JUNavigationBar hiddenNavBar = new JUNavigationBar()
  {
    protected void _updateButtonStates()
    {
      super._updateButtonStates();
      menuItemsUpdate();
    }
  };


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
  private BorderLayout borderLayout1 = new BorderLayout();
  private PanelEmployees dataPanel1 = new PanelEmployees();


  /**
   * 
   * The default constructor for form
   */
  public SampleForm()
  {
    addWindowListener(new WindowAdapter()
      {
        public void windowClosing(WindowEvent e)
        {
          _popupTransactionDialog();
          JUApplication juApp = panelBinding.getApplication();
          if (juApp != null)
          {
            juApp.release();
          }
          System.exit(0);
        }
      });
  }

  public SampleForm(JUPanelBinding panelBinding)
  {
    this();
    setPanelBinding(panelBinding);
  }

  /**
   * 
   * the JbInit method
   */
  public void jbInit() throws Exception
  {
    // form layout

    dataPanel.setLayout(borderLayout1);
    this.getContentPane().setLayout(gridLayout);
    this.setTitle("Sample Form With Data-Driven Poplist in Table");
    topPanel.setLayout(borderLayout);

    this.getContentPane().add(topPanel);


    this.setSize(new Dimension(538, 227));



    dataPanel.add(dataPanel1, BorderLayout.CENTER);
    topPanel.add(dataPanel, BorderLayout.CENTER);

    topPanel.add(statusBar, BorderLayout.SOUTH);

    setJMenuBar(menubarFrame);
    itemFileExit.setText("Exit");
    itemFileExit.setMnemonic('X');
    itemFileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.ALT_MASK, false));

    itemFileExit.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
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
    itemDatabaseFirst.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_HOME, Event.ALT_MASK, false));
    itemDatabaseFirst.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          first_action(e);
        }
      });

    menuDatabase.add(itemDatabaseFirst);
    itemDatabasePrevious.setText("Previous");
    itemDatabasePrevious.setMnemonic('P');
    itemDatabasePrevious.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, Event.ALT_MASK, false));
    itemDatabasePrevious.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          previous_action(e);
        }
      });

    menuDatabase.add(itemDatabasePrevious);
    itemDatabaseNext.setText("Next");
    itemDatabaseNext.setMnemonic('N');
    itemDatabaseNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, Event.ALT_MASK, false));
    itemDatabaseNext.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          next_action(e);
        }
      });

    menuDatabase.add(itemDatabaseNext);
    itemDatabaseLast.setText("Last");
    itemDatabaseLast.setMnemonic('L');
    itemDatabaseLast.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_END, Event.ALT_MASK, false));
    itemDatabaseLast.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          last_action(e);
        }
      });

    menuDatabase.add(itemDatabaseLast);

    menuDatabase.addSeparator();
    itemDatabaseInsert.setText("Insert");
    itemDatabaseInsert.setMnemonic('I');
    itemDatabaseInsert.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, Event.ALT_MASK, false));
    itemDatabaseInsert.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          insert_action(e);
        }
      });

    menuDatabase.add(itemDatabaseInsert);
    itemDatabaseDelete.setText("Delete");
    itemDatabaseDelete.setMnemonic('D');
    itemDatabaseDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, Event.ALT_MASK, false));
    itemDatabaseDelete.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          delete_action(e);
        }
      });

    menuDatabase.add(itemDatabaseDelete);

    menuDatabase.addSeparator();
    itemDatabaseCommit.setText("Commit");
    itemDatabaseCommit.setMnemonic('C');
    itemDatabaseCommit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.ALT_MASK, false));
    itemDatabaseCommit.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          commit_action(e);
        }
      });

    menuDatabase.add(itemDatabaseCommit);
    itemDatabaseRollback.setText("Rollback");
    itemDatabaseRollback.setMnemonic('R');
    itemDatabaseRollback.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.ALT_MASK, false));
    itemDatabaseRollback.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rollback_action(e);
        }
      });

    menuDatabase.add(itemDatabaseRollback);

    menuDatabase.addSeparator();
    itemFindMode.setText("Query Mode");
    itemFindMode.setMnemonic('Q');
    itemFindMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.ALT_MASK, false));
    itemFindMode.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          find_action(e);
        }
      });

    menuDatabase.add(itemFindMode);
    itemExecute.setText("Execute Query");
    itemExecute.setMnemonic('E');
    itemExecute.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.ALT_MASK, false));
    itemExecute.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          execute_action(e);
        }
      });

    menuDatabase.add(itemExecute);

    menubarFrame.add(menuDatabase);
    menuHelp.setText("Help");

    menuHelp.setMnemonic('H');
    itemHelpAbout.setText("About");
    itemHelpAbout.setMnemonic('A');
    itemHelpAbout.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          help_about_action(e);
        }
      });

    menuHelp.add(itemHelpAbout);


    menubarFrame.add(menuHelp);
    hiddenNavBar.setModel(JUNavigationBar.createPanelBinding(panelBinding, hiddenNavBar));
    statusBar.setModel(JUStatusBar.createPanelBinding(panelBinding, statusBar));
    dataPanel1.setPanelBinding(panelBinding);
  }

  private void first_action(ActionEvent e)
  {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_FIRST);
  }

  private void previous_action(ActionEvent e)
  {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_PREV);
  }

  private void next_action(ActionEvent e)
  {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_NEXT);
  }

  private void last_action(ActionEvent e)
  {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_LAST);
  }

  private void insert_action(ActionEvent e)
  {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_INSERT);
  }

  private void delete_action(ActionEvent e)
  {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_DELETE);
  }

  private void commit_action(ActionEvent e)
  {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_COMMIT);
  }

  private void rollback_action(ActionEvent e)
  {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_ROLLBACK);
  }

  private void find_action(ActionEvent e)
  {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_FIND);
  }

  private void execute_action(ActionEvent e)
  {
    hiddenNavBar.doAction(JUNavigationBar.BUTTON_EXECUTE);
  }

  private void file_exit_action(ActionEvent e)
  {
    _popupTransactionDialog();
    System.exit(0);
  }

  private void help_about_action(ActionEvent e)
  {
    JOptionPane.showMessageDialog(this, aboutMessage, aboutTitle, JOptionPane.INFORMATION_MESSAGE);
  }

  private void menuItemsUpdate()
  {
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

  public static void main(String [] args)
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      SwingUtils.setupDefaultFont("Lucida Sans Unicode",14);
    }
    catch(Exception exemp)
    {
      exemp.printStackTrace();
    }



    try
    {
      // bootstrap application
      JUMetaObjectManager.setBaseErrorHandler(new JUErrorHandlerDlg());
      JUApplication app = JUMetaObjectManager.createApplicationObject("JTableWithDataDrivenCombo.TestModule", null, new JUEnvInfoProvider());
      JUPanelBinding panelBinding = new JUPanelBinding("JTableWithDataDrivenCombo.TestModule", null);
      panelBinding.setApplication(app);
      SampleForm frame = new SampleForm(panelBinding);
      frame.setSize(600,300);
      panelBinding.execute();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = frame.getSize();

      // run this form

      if (frameSize.height > screenSize.height)
      {
        frameSize.height = screenSize.height;
      }
      if (frameSize.width > screenSize.width)
      {
        frameSize.width = screenSize.width;
      }
      frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      frame.setVisible(true);
    }
    catch(Exception ex)
    {
      JUMetaObjectManager.reportException(null, ex);
      System.exit(1);
    }

  }

  private void _popupTransactionDialog()
  {
    if (panelBinding == null || panelBinding.getPanel() == null)
    {
      return;
    }
    if (panelBinding.getApplicationModule().getTransaction().isDirty())
    {
      Object [] options = {"Commit", "Rollback"};

      int action = JOptionPane.showOptionDialog(SampleForm.this, "How do you want to close the transaction?", "Transaction open", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

      switch (action)
        {
        case JOptionPane.NO_OPTION:
        case JOptionPane.CLOSED_OPTION:
          hiddenNavBar.doAction(JUNavigationBar.BUTTON_ROLLBACK);
          break;
        case JOptionPane.YES_OPTION:
        default:
          hiddenNavBar.doAction(JUNavigationBar.BUTTON_COMMIT);
          break;
        }
    }
  }

  public JUPanelBinding getPanelBinding()
  {
    return panelBinding;
  }

  public void setPanelBinding(JUPanelBinding binding)
  {
    if (binding.getPanel() == null)
    {
      binding.setPanel(topPanel);
    }

    if (panelBinding == null || panelBinding.getPanel() == null)
    {
      try
      {
        panelBinding = binding;
        jbInit();
      }
      catch(Exception ex)
      {
        panelBinding.reportException(ex);
      }

    }
  }
}
