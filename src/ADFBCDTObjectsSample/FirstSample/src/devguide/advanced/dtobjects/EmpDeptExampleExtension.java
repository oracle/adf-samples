/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package devguide.advanced.dtobjects;
import oracle.ide.IdeMainWindow;
import oracle.ide.Ide;

import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import oracle.ide.Addin;
import oracle.ide.Context;
import oracle.ide.compiler.Compiler;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.controller.MenuManager;
import oracle.ide.log.LogManager;
import oracle.ide.model.Project;
import oracle.ide.model.Workspace;
import oracle.ide.navigator.NavigatorManager;
import oracle.ide.wizard.Wizard;
import oracle.jbo.dt.objects.JboAppModule;
import oracle.jbo.dt.objects.JboApplication;
import oracle.jbo.dt.objects.JboAssociation;
import oracle.jbo.dt.objects.JboAssociationEnd;
import oracle.jbo.dt.objects.JboAttribute;
import oracle.jbo.dt.objects.JboAttributeList;
import oracle.jbo.dt.objects.JboBaseObject;
import oracle.jbo.dt.objects.JboEntity;
import oracle.jbo.dt.objects.JboEntityAttr;
import oracle.jbo.dt.objects.JboPackage;
import oracle.jbo.dt.objects.JboView;
import oracle.jbo.dt.objects.JboViewLink;
import oracle.jbo.dt.objects.JboViewLinkEnd;
import oracle.jbo.dt.objects.JboViewLinkUsage;
import oracle.jbo.dt.objects.JboViewReference;
import oracle.jbo.dt.ui.main.DtuUtil;
public final class EmpDeptExampleExtension extends Wizard implements Addin, 
                                                                     Controller {
  public void initialize() {
    {
      Controller ctrlr = this;
      JMenuItem mi = 
        doCreateMenuItem(ctrlr, "Create Emp/Dept Objects...", new Integer('I'), 
                         "16x16_Generic.gif");
      JMenu mainMenu = MenuManager.getJMenu(IdeMainWindow.MENU_TOOLS);
      mainMenu.add(mi);
    }
    { // Create the Context Menu Item and add it to the Navigator Menu
      Controller ctrlr2 = this;
      JMenuItem rclickMi = 
        doCreateMenuItem(ctrlr2, "Create Emp/Dept Objects...", 
                         new Integer('I'), "16x16_Generic.gif");
      ctxMenuListener ctxMenuLstnr = new ctxMenuListener(rclickMi);
      // By setting the NodeTypeClass to a particular type, then menu will only appear on those types!
      final Class matchingNodeType = null;
      NavigatorManager.getWorkspaceNavigatorManager().addContextMenuListener(ctxMenuLstnr, 
                                                                             matchingNodeType);
    }
  }
  private static Icon largeIcon;
  public Icon getIcon() {
    if (largeIcon == null)
      largeIcon = 
          new ImageIcon(this.getClass().getResource("16x16_Generic.gif"));
    return largeIcon;
  }
  public String getShortLabel() {
    return "Create Emp/Dept Objects Extension";
  }
  public boolean invoke(Context context) {
    performTask();
    return true;
  }
  private void performTask() {
    CreateEmpDeptObjectsTask task = new CreateEmpDeptObjectsTask();
    task.performTask();    
  }
  public boolean isAvailable(Context context) {
    return true;
  }
  public boolean handleEvent(IdeAction action, Context context) {
    int cmdId = action.getCommandId();
    if (cmdId == SAMPLE_ONE_MENU_COMMAND_ID) {
      // Invocation: Display message dialog
      performTask();
      return true;
    }
    return false;
  }
  public boolean update(IdeAction action, Context context) {
    int cmdId = action.getCommandId();
    if (cmdId == SAMPLE_ONE_MENU_COMMAND_ID) {
      if (isMenuAvailable(context)) {
        action.setEnabled(true);
        return true;
      }
    }
    return false;
  }
  private static final class ctxMenuListener implements ContextMenuListener {
    private JMenuItem menuItem;
    ctxMenuListener(JMenuItem ctxMenuItem) {
      this.menuItem = ctxMenuItem;
    }
    public void menuWillShow(ContextMenu popup) {
      Context context = (popup == null) ? null : popup.getContext();
      if (context == null)
        return;
      if (isMenuAvailable(context)) {
        popup.add(this.menuItem);
      }
      return;
    }
    public void menuWillHide(ContextMenu popup) {
      // Do any necessary clean up
    }
    public boolean handleDefaultAction(Context context) {
      return false; // No, this is not the default action!
    }
  }
  public static final int SAMPLE_ONE_MENU_COMMAND_ID = 
    Ide.createCmdID("MyMenu.SAMPLE_ONE_MENU_COMMAND");
  private static JMenuItem doCreateMenuItem(Controller ctrlr, String menuLabel, 
                                            Integer mnemonic, 
                                            String iconName) {
    Icon icon = 
      new ImageIcon(EmpDeptExampleExtension.class.getResource(iconName));
    String category = null;
    String cmdClass = null;
    IdeAction actionTM = // int cmdId,
      //    String cmdClass extends oracle.ide.addin.AbstractCommand,
      //    String name,
      //    Category
      //    Integer mnemonic,
      //    Icon icon,
      //    Object data,
      //    boolean enabled
      IdeAction.get(SAMPLE_ONE_MENU_COMMAND_ID, cmdClass, menuLabel, category, 
                    mnemonic, icon, null, true);
    actionTM.addController(ctrlr);
    JMenuItem menuItem = Ide.getMenubar().createMenuItem(actionTM);
    return menuItem;
  }
  public static boolean isMenuAvailable(Context context) {
    return true;
  }

}
