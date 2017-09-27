package test.rules;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Customizer;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import oracle.jbo.common.JboTypeMap;
import oracle.jbo.domain.Date;
import oracle.jbo.dt.objects.JboAttribute;
import oracle.jbo.dt.objects.JboBaseObject;
import oracle.jbo.dt.objects.JboDatabaseAttr;
import oracle.jbo.dt.objects.JboEntity;
import oracle.jbo.dt.ui.main.dlg.DtuWizardPanelDialog;
/**
 * Example of a custom validator editor panel
 *
 * The ADF BC design time finds this class by introspecting
 * the custom validator bean (DateRangeRule) and finding the
 * bean descriptor. The BeanDescriptor associates this Customizer
 * class with the DateRangeRule bean. The ADF BC design time
 * validation panel of the Entity Object editor then uses this
 * custom editor instead of the default one.
 * 
 * We package this into the DateRangeValidationRuleDT.jar
 * for use at design time only.
 */
public class DateRangeRuleCustomizer extends JPanel implements Customizer {
  private PropertyChangeSupport propertyChangeSupport;
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JComboBox startingNameCombo = new JComboBox();
  private DateRangeRule ruleBeanBeingEdited = null;
  private JComboBox endingNameCombo = new JComboBox();

  public DateRangeRuleCustomizer() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Swing fires this method when our customizer panel is
   * added to its container (which at ADF BC design time is
   * the validation rule editor dialog.
   */
  public void addNotify() {
    super.addNotify();
    Vector attrNames = getDateAttributeNames();
    if (attrNames.size() == 0) {
      attrNames.add("<No Date Attributes>");
      startingNameCombo.setEnabled(false);
      endingNameCombo.setEnabled(false);
    }
    else {
      startingNameCombo.setEnabled(true);
      endingNameCombo.setEnabled(true);
    }
    startingNameCombo.setModel(new DefaultComboBoxModel(attrNames));
    endingNameCombo.setModel(new DefaultComboBoxModel(attrNames));
    String curStartAttrName = ruleBeanBeingEdited.getBeginDateAttrName();
    String curEndingAttrName = ruleBeanBeingEdited.getEndDateAttrName();
    if (curStartAttrName == null) {
      setBeginDateAttrToSelectedItem();
    }
    else {
      startingNameCombo.setSelectedItem(curStartAttrName);
    }
    if (curEndingAttrName == null) {
      setEndDateAttrToSelectedItem();
    }
    else {
      endingNameCombo.setSelectedItem(curEndingAttrName);
    }
  }
  /**
   * Return a vector of Date attribute names
   *
   * @return vector of date attribute names
   */
  private Vector getDateAttributeNames() {
    Component parentObj = getParent();
    Vector attrNames = new Vector();
    while (parentObj != null) {
      if (parentObj instanceof DtuWizardPanelDialog) {
        DtuWizardPanelDialog dlg = (DtuWizardPanelDialog) parentObj;
        JboBaseObject ownerBase = (JboBaseObject) dlg.getObject();
        if (ownerBase instanceof JboEntity) {
          JboEntity ownerEntity = (JboEntity) ownerBase;
          JboAttribute[] attrs = ownerEntity.getAttributes();
          for (int i = 0; i < attrs.length; i++) {
            if (isDateAttribute(attrs[i])) {
              attrNames.add(attrs[i].getName());
            }
          }
          return attrNames;
        }
        break;
      }
      parentObj = parentObj.getParent();
    }
    return attrNames;
  }
  //==vvv== java.beans.Customizer Interface Implementation ==vvv==
  /**
   *
   * @param bean
   */
  public void setObject(Object bean) {
    propertyChangeSupport = new PropertyChangeSupport(bean);
    if ((ruleBeanBeingEdited == null) || (bean != ruleBeanBeingEdited)) {
      initCustomizer((DateRangeRule) bean);
    }
  }
  /**
   *
   * @param listener
   */
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    if (propertyChangeSupport != null) {
      propertyChangeSupport.addPropertyChangeListener(listener);
    }
  }
  /**
   *
   * @param listener
   */
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    if (propertyChangeSupport != null) {
      propertyChangeSupport.removePropertyChangeListener(listener);
    }
  }
  //==^^^== java.beans.Customizer Interface Implementation ==^^^==
  /**
   * Helper method to return whether or not an entity object
   * attribute at design time is a date attribute.
   *
   * @return true if the attribute is a date attribute
   * @param attr design time attribute object to evaluate
   */
  private boolean isDateAttribute(JboAttribute attr) {
    int sqltype = -1;
    if (attr instanceof JboDatabaseAttr) {
      sqltype = ((JboDatabaseAttr) attr).getSQLTypeId();
    }
    else {
      sqltype = JboTypeMap.javaTypeToSQLTypeId(attr.getJavaTypeFullName());
    }
    return JboTypeMap.isDateType(sqltype);
  }
  /**
   * Initializes the Swing components in the customizer panel
   *
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);
    this.setBorder(BorderFactory.createTitledBorder(
        "Date Range Attribute Names"));
    this.setSize(new Dimension(248, 124));
    jLabel1.setText("Starting Date Attribute:");
    jLabel2.setText("Ending Date Attribute:");
    startingNameCombo.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          startingNameCombo_actionPerformed(e);
        }
      });
    endingNameCombo.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          endingNameCombo_actionPerformed(e);
        }
      });
    this.add(jLabel1,
      new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
        GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jLabel2,
      new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
        GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.add(startingNameCombo,
      new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    this.add(endingNameCombo,
      new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
  }
  private void initCustomizer(DateRangeRule bean) {
    ruleBeanBeingEdited = (DateRangeRule) bean;
  }
  private void setBeginDateAttrToSelectedItem() {
    String oldValue = ruleBeanBeingEdited.getBeginDateAttrName();
    String newValue = (String) startingNameCombo.getModel().getSelectedItem();
    ruleBeanBeingEdited.setBeginDateAttrName(newValue);
    propertyChangeSupport.firePropertyChange("beginDateAttrName", oldValue,
      newValue);
  }
  private void setEndDateAttrToSelectedItem() {
    String oldValue = ruleBeanBeingEdited.getEndDateAttrName();
    String newValue = (String) endingNameCombo.getModel().getSelectedItem();
    ruleBeanBeingEdited.setEndDateAttrName(newValue);
    propertyChangeSupport.firePropertyChange("endDateAttrName", oldValue,
      newValue);
  }
  private void startingNameCombo_actionPerformed(ActionEvent e) {
    setBeginDateAttrToSelectedItem();
  }
  private void endingNameCombo_actionPerformed(ActionEvent e) {
    setEndDateAttrToSelectedItem();
  }
}
