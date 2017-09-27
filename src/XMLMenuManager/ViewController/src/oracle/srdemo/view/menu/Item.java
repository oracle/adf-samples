/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/XMLMenuManager/ViewController/src/oracle/srdemo/view/menu/Item.java,v 1.2 2005/12/01 15:34:07 steve Exp $
package oracle.srdemo.view.menu;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
/**
 * Represents a menu item.
 */
public class Item {
  private static final String GLOBAL = "global";
  private static final String DEFAULT = "default";
  private static final String COMMA = ",";
  private boolean global = false;
  private String labelKey;
  private String icon;
  private String outcome;
  private List<String> roles;
  private boolean displayIfUnauthorized = false;
  private String viewId;
  private String label;
  private List<Item> items = null;

  /**
   * Add a submenu to this menu item.
   * @param i Item to add as a submenu
   */
  public void addItem(Item i) {
    if (items == null) {
      items = new ArrayList<Item>(3);
    }
    items.add(i);
  }

  /**
   * container property indicates if this item has submenus.
   * @return true if this item is a container of other submenus.
   */
  public boolean isContainer() {
    return items != null && items.size() > 0;
  }

  /**
   * global property indicates whether the item is a global item.
   * @param global true if the item is a global item.
   */
  public void setGlobal(boolean global) {
    this.global = global;
  }

  /**
   * global property indicates whether the item is a global item.
   * @return true if the item is a global item
   */
  public boolean isGlobal() {
    return global;
  }

  /**
   * type property is the ADF Faces menu type for this item.
   * @return ADF Faces menu type for this item ("global" or "default")
   */
  public String getType() {
    return global ? GLOBAL : DEFAULT;
  }

  /**
   * labelKey property is the resource bundle key for item label.
   * @param labelKey resource bundle key for item label
   */
  public void setLabelKey(String labelKey) {
    this.labelKey = labelKey;
  }

  /**
   * labelKey property is the resource bundle key for this item label.
   * @return resource bundle key for this item's label
   */
  public String getLabelKey() {
    return labelKey;
  }

  /**
   * outcome property is the JSF navigation outcome for this item.
   * @param outcome JSF navigation outcome for this item
   */
  public void setOutcome(String outcome) {
    this.outcome = outcome;
  }

  /**
   * outcome property is the JSF navigation outcome for this item.
   * @return JSF navigation outcome for this item
   */
  public String getOutcome() {
    return outcome;
  }

  /**
   * viewId property is the JSF page name corresponding to this item.
   * @param viewId JSF page name corresponding to this item
   */
  public void setViewId(String viewId) {
    this.viewId = viewId;
  }

  /**
   * viewId property is the JSF page name corresponding to this item.
   * @return JSF page name corresponding to this item
   */
  public String getViewId() {
    return viewId;
  }

  /**
   * label property is the non-translateable label for this item.
   * @param label non-translateable label for this item
   *
   * To use a translatable label, set the labelKey property instead.
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * label property is the non-translateable label for this item.
   * @return non-translateable label for this item
   */
  public String getLabel() {
    return label;
  }

  /**
   * List of submenus for this item if any.
   * @return list of submenus for this item if any
   */
  public List<Item> getItems() {
    return items != null ? items : new ArrayList<Item>(0);
  }

  /**
   * Overrides toString() to print useful debugging output for a menu item.
   * @return debugging output for a menu item
   */
  public String toString() {
    return "(" + "label=" + label + COMMA + "labelKey=" + labelKey + 
      COMMA + "outcome=" + outcome + COMMA + "global=" + global + COMMA + 
      "viewId=" + viewId + COMMA + "roles=" + roles + COMMA + ")";
  }

  /**
   * Security roles required to use this menu item.
   * @param roles comma-separated list of role names
   */
  public void setRoles(String roles) {
    if (roles != null) {
      this.roles = new ArrayList<String>();
      if (roles.indexOf(',') >= 0) {
        StringTokenizer s = new StringTokenizer(roles, COMMA);
        while (s.hasMoreTokens()) {
          this.roles.add(s.nextToken());
        }
      } else {
        this.roles.add(roles);
      }
    }
  }

  /**
   * displayIfUnauthorized property controls whether item will appear anyway.
   * (as display-only) if the user is not in the list of roles)
   * @param display true if you want the menu item to display anyway
   */
  public void setDisplayIfUnauthorized(boolean display) {
    this.displayIfUnauthorized = display;
  }

  /**
   * rendered property indicates whether the item should be rendered.
   * @return true if the item should be displayed
   */
  public boolean isRendered() {
    return isUserAuthorized() || displayIfUnauthorized;
  }

  /**
   * readOnly property indicates whether the item is disabled.
   * @return true if the item is disabled
   */
  public boolean isDisabled() {
    return !isUserAuthorized();
  }

  /**
   * icon property is the path to the icon if the menu item needs one.
   * @param icon path to the icon
   */
  public void setIcon(String icon) {
    this.icon = icon;
  }

  /**
   * icon property is the path to the icon if the menu item needs one.
   * @return icon path if any
   */
  public String getIcon() {
    return icon;
  }
  private boolean requiresAuthorization() {
    return roles != null && roles.size() > 0;
  }
  private ExternalContext getExternalContext() {
    return FacesContext.getCurrentInstance().getExternalContext();
  }
  private boolean isSecurityEnabled() {
    return getExternalContext().getAuthType() != null;
  }
  private boolean isUserAuthorized() {
    if (!isSecurityEnabled() || !requiresAuthorization()) {
      return true;
    }
    for (String roleName: roles) {
      if (getExternalContext().isUserInRole(roleName)) {
        return true;
      }
    }
    return false;
  }
}
