/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import oracle.jbo.uicli.binding.*;
import oracle.jbo.uicli.controls.*;
import oracle.jbo.uicli.jui.*;
import oracle.jdeveloper.layout.*;
public class PanelEmployees extends JPanel implements JClientPanel {
  // Panel binding definition used by design time
  private JUPanelBinding panelBinding = new JUPanelBinding("JTableWithDataDrivenCombo.TestModule",
      null);

  // Panel containing the data entry fields
  private JPanel dataPanel = new JPanel();
  private BorderLayout borderLayout = new BorderLayout();

  // Layout used by panel
  private GridLayout gridLayout = new GridLayout();

  // Fields for attribute:  Employees
  //  private JTable tableEmployees = new JTable();
  private JTable tableEmployees = /**
     * This in an on-the-fly subclass of JTable that overrides
     * two of its methods 'editingStopped', and 'xxx'
     */
    new JTable() {
      /**
       * Override to avoid redundant setValueAt, as Combobox binding will
       * set the value on selection rightaway.
       */
      public void editingStopped(javax.swing.event.ChangeEvent e) {
        /*
         * Take in the new value
         */
        TableCellEditor editor = getCellEditor();
        if (editor instanceof DefaultCellEditor) {
          Component cellEditor = ((DefaultCellEditor) editor).getComponent();
          if (cellEditor == jComboBox1) {
            removeEditor();
            return;
          }
          super.editingStopped(e);
        }
      }
      /*
       * Swing JTable calls this method to get the editor for a cell.
       * If the editor is the installed combobox, set selected item in the
       * combobox to the value shown in that cell and return the combobox.
       */
      public Component prepareEditor(TableCellEditor editor, int row, int col) {
        if (editor instanceof DefaultCellEditor) {
          Component cellEditor = ((DefaultCellEditor) editor).getComponent();
          if (cellEditor == jComboBox1) {
            this.setRowSelectionInterval(row, row);
            String locCity = (String) getValueAt(row, col);
            if (jComboBox1Binding == null) {
              jComboBox1Binding = (JUComboBoxBinding) panelBinding.getControlBinding(jComboBox1);
            }
            /*
             * Find matching Row with location id equals to what's in this row
             * in a future release there'd be a method in JUComboBoxBinding that
             * matches an entry based on input value (rather than display value).
             */

            // jComboBox1.setSelectedIndex(jComboBox1Binding.findListIndex(locCity));
          }
        }
        return super.prepareEditor(editor, row, col);
      }
    }; /* End of "new JTable() {}" */
  private JComboBox jComboBox1 = new JComboBox();
  private JUComboBoxBinding jComboBox1Binding = null;
  private JScrollPane scroller = new JScrollPane();

  /**
   *
   * The default constructor for panel
   */
  public PanelEmployees() {}
  /**
   *
   * Constructor that takes a shared panel binding
   */
  public PanelEmployees(JUPanelBinding binding) {
    setPanelBinding(binding);
  }

  /**
   *
   * the JbInit method
   */
  public void jbInit() throws Exception {
    // Layout definition for this panel
    dataPanel.setLayout(gridLayout);
    dataPanel.setMinimumSize(new Dimension(100, 100));
    this.setLayout(borderLayout);
    // Code support for view object:  Employees
    tableEmployees.setModel(JUTableBinding.createAttributeListBinding(
        panelBinding, tableEmployees, "Employees", null, "EmployeesIter",
        new String[] { "Empno", "Ename", "Sal", "Dname", "Loc" }));
    scroller.getViewport().add(tableEmployees, null);
    // Layout the datapanel and the navigation bar
    dataPanel.add(scroller);
    add(dataPanel, BorderLayout.CENTER);
    /*
     * Bind the combobox to the Departments view object instance.
     */
    jComboBox1.setModel(JUComboBoxBinding.createLovBinding(panelBinding,
        jComboBox1, "Employees", null, "EmployeesIter",
        new String[] { "WorksInDeptno" }, "Departments",
        new String[] { "Deptno" }, new String[] { "Dname" }, null, null));
    jComboBox1.setEditable(false);
    //Add this line to set the cell editor for the table
    tableEmployees.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(
        jComboBox1));
  }
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception exemp) {
      exemp.printStackTrace();
    }
    PanelEmployees panel = new PanelEmployees();
    JUTestFrame.testJClientPanel(panel, panel.getPanelBinding(),
      new Dimension(600, 400));
  }
  /**
   *
   * JClientPanel implementation
   */
  public JUPanelBinding getPanelBinding() {
    return panelBinding;
  }
  public void setPanelBinding(JUPanelBinding binding) {
    if (binding.getPanel() == null) {
      binding.setPanel(this);
    }
    if ((panelBinding == null) || (panelBinding.getPanel() == null)) {
      try {
        panelBinding = binding;
        jbInit();
      }
      catch (Exception ex) {
        panelBinding.reportException(ex);
      }
    }
  }
}
