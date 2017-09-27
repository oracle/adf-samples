/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package davelaar.demo.ui.view.dyntab;

import davelaar.demo.ui.util.JsfUtils;
import davelaar.demo.ui.util.RichClientUtils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;

import javax.faces.event.ActionListener;

import javax.faces.event.PhaseId;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.controller.binding.TaskFlowBindingAttributes;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.ItemEvent;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.model.ChildPropertyMenuModel;
import org.apache.myfaces.trinidad.model.MenuModel;
import org.apache.myfaces.trinidad.util.ComponentReference;


public class DynTabManager
  implements Serializable
{
  private static final ADFLogger sLog = ADFLogger.createADFLogger(DynTabManager.class);

  private List<TaskFlowBindingAttributes> taskFlowBindingAttrsList = new ArrayList<TaskFlowBindingAttributes>();

  private static final String TAB_NOT_CLOSEABLE_MESSAGE_KEY = "TAB_NOT_CLOSEABLE";
  private static final String TOO_MANY_TABS_OPEN_MESSAGE_KEY = "TOO_MANY_TABS_OPEN";
  private ComponentReference contentArea;
  private ComponentReference tabsNavigationPane;
  private boolean doUpdateDocumentTitle = true;
  private ComponentReference<RichPopup> tabDirtyPopup;
  private String documentTitlePrefix = "";
  private String documentId = "doc0";
  private String tabToRemoveId;

  private List<String> initialTabs = new ArrayList<String>();
  private int lastUsedIndexForId = 0;
  
  private boolean checkTabExists = true;

  /**
   * The id of the tab that is currently selected / open.
   */
  private String selectedTabId = null;


  /**
   * This is the maximum number of tabs that is allowed by the application
   * developer.
   */
  private int maxNumberOfTabs = 3;

  /**
   * The list of tabs that defines the order of the tabs on the screen.
   */
  private final List<DynTab> tabList = new ArrayList<DynTab>();

  /**
   * The <code>tabMap</code> contains all tabs based on their <code>id</code>.
   */
  private final Map<String, DynTab> tabMap = new HashMap<String, DynTab>();
  private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

  /**
   * Returns a DynTabManager instance. We first lookup the instance using
   * the JSF expression #{viewScope.dynTabManager}.If no instance is found, we also try to
   * find it under pageFlowScope.dynTabManager, because it might have been
   * passed as taskflow parameter.
   *
   * @return dynTabManager
   */
  public static DynTabManager getCurrentInstance()
  {
    String expression = "#{viewScope.dynTabManager}";
    try
    {
      DynTabManager context = (DynTabManager) JsfUtils.getExpressionValue(expression);
      if (context == null)
      {
        expression = "#{pageFlowScope.dynTabManager}";
        context = (DynTabManager) JsfUtils.getExpressionValue(expression);
      }
      return context;
    }
    catch (ClassCastException e)
    {
      throw new IllegalArgumentException("Expression "+expression+" does not return instance of DynTabManager");
    }
  }

  @PostConstruct
  public void init()
  {
    boolean oldValue = isDoUpdateDocumentTitle();
    // switch off update of document title during init, causes NPE
    // because extended render kit not yet available
    setDoUpdateDocumentTitle(false);    
    for (String tabName: initialTabs)
    {
      launchTab(tabName);
    }
    if (initialTabs.size()>0)
    {
      setSelectedTabId(getTabAt(0).getId());
    }
    setDoUpdateDocumentTitle(oldValue);
  }

  /**
   * This method is called to open a new dynamic tab, or select the existing tab for the same uniqueIdentifier when
   * checkTabExists property is true. 
   * This method receives a tabName argument, this value is suffixed with "DynTab" to lookup a managed bean in request
   * scope of type DynTab. For example, if the tabName passed in is "Jobs", there should be a managed bean named
   * "JobsDynTab" defined. This managed bean holds the information needed to launch the tab: taskFlowId, parameters,
   * tab title, whether the tab is closeable, etc.
   */
  public void launchTab(String tabName)
  {
    DynTab tab = DynTab.getInstance(tabName);   
    addOrSelectTab(tab);
  }

  /** 
   * This method can be used to launch a tab from a command component using the actionListener property.
   * To be able to use this method, the command component must have an attribute nanmed "tabName" with the value
   * set to the name of the tab. This method then calls launchTab(String tabName) with the value of this attribute
   * 
   * @see launchTab(String tabName)
   */
  public void launchTab(ActionEvent event)
  {
    String tabName = (String) event.getComponent().getAttributes().get("tabName");
    launchTab(tabName);
  }

  protected String createId()
  {
    lastUsedIndexForId ++;
    return "t" + lastUsedIndexForId;
  }

  /**
   * Returns the list of tabs that defines the order of the tabs on the screen.
   * All tabs are included in this list at all times.
   *
   * @return the list of tabs that defines the order of the tabs on the screen
   */
  public List<DynTab> getTabList()
  {

    return tabList;
  }

  /**
   * Returns the map containing all tabs based on their <code>id</code>s.
   *
   * @return the map containing all tabs based on their ids
   */
  public Map<String, DynTab> getTabMap()
  {
    return tabMap;
  }

  /**
   * Sets the maximum number of tabs that the user is allowed to open.
   * <p>
   *
   * @param maxNumberOfTabs the new maximum number of tabs that the user is allowed to open
   */
  public void setMaxNumberOfTabs(int maxNumberOfTabs)
  {
    this.maxNumberOfTabs = maxNumberOfTabs;
  }

  public int getMaxNumberOfTabs()
  {
    return maxNumberOfTabs;
  }

  public void setInitialTabs(List<String> initialTabs)
  {
    this.initialTabs = initialTabs;
  }

  public List<String> getInitialTabs()
  {
    return initialTabs;
  }

  public void setSelectedTabId(String id)
  {
    DynTab tab = getTab(id);

    if (tab == null)
    {
      throw new IllegalArgumentException("Tab: " + id + " unknown!");
    }
    this.selectedTabId = id;
    updateDocumentTitle();
    refreshTabsAndContentArea();
  }

  public String getSelectedTabId()
  {
    return selectedTabId;
  }

  public void addOrSelectTab(DynTab tab)
  {
    sLog.info("TaskflowId: " + tab.getTaskFlowIdString() + ", tabUniqueIdentifier: " + tab.getUniqueIdentifier() + ", params: " +
              tab.getParameters());

    DynTab existingTab = getMatchingTab(tab);

    if (existingTab != null && isCheckTabExists())
    {
      sLog.info("Matching existing tab found, select it");
      setSelectedTabId(existingTab.getId());
    }
    else
    {
      sLog.info("No matching existing tab found, add new tab");
      addTab(tab);
    }
  }


  public void addTab(DynTab tab)
  {
    if (tabList.size() >= getMaxNumberOfTabs())
    {
      handleTooManyTabsOpen();
    }
    else
    {
      // Assign unique id that we use to link the DynTab with the 
      // corresponding Taskflow
      tab.setId(createId()); 
      // add the DynTabManager instance as taskflow parameter so the 
      // taskflow itself can open new tabs, and close tabs. 
      tab.getParameters().put("dynTabManager", this);
      // Add the dynTabid so taskflow itself can change label
      tab.getParameters().put("dynTabId", tab.getId());
      // add the new tab to the tab list and map
      tabList.add(tab);
      tabMap.put(tab.getId(), tab);
      setSelectedTabId(tab.getId());
      TaskFlowBindingAttributes tfAttr = new TaskFlowBindingAttributes();
      tfAttr.setId(tab.getId());
      tfAttr.setTaskFlowId(tab.getTaskFlowId());
      tfAttr.setRefreshCondition("ifNeeded");
      tfAttr.setParametersMap(tab.getParameterMapExpression());
      taskFlowBindingAttrsList.add(tfAttr);
      refreshTabsAndContentArea();
    }
  }

  public void markCurrentTabDirty(boolean isDirty)
  {

    markTabDirty(getSelectedTabId(), isDirty);
  }

  public void markTabDirty(String id, boolean isDirty)
  {
    DynTab tab = getTab(id);
    if (tab.isDirty() != isDirty)
    {
      tab.setDirty(isDirty);
      refreshTabsNavigationPane();
    }
  }

  public void changeTabLabel(String tabId, String label)
  {
    DynTab tab = getTab(tabId);
    tab.setTitle(label);
    refreshTabsNavigationPane();
    updateDocumentTitle();
  }

  public void removeCurrentTab(boolean ignorePendingChanges)
  {
    removeTab(getSelectedTabId(), ignorePendingChanges);
  }

    /**
     * Close the current tab, returns a String so it can be used in action property of command component.
     * If the current tab has pending changes, these changes are ignored
     * @return
     */
    public String removeCurrentTabForce()
    {
      removeTab(getSelectedTabId(), true);
      return null;
    }

  /**
   * Close the current tab, returns a String so it can be used in action property of command component 
   * If the current tab has pending changes, an alert is shown before the tab is closed
   * @return
   */
  public String removeCurrentTab()
  {
    removeTab(getSelectedTabId(), false);
    return null;
  }

  public void removeTab(String id)
  {
    removeTab(id, false);
  }

  public boolean isTagSetDirty()
  {
    for (DynTab tab: tabList)
    {
      if (tab.isDirty())
      {
        return true;
      }
    }

    return false;
  }

  public boolean isCurrentTabDirty()
  {
    DynTab tab = getSelectedTab();
    return tab != null? tab.isDirty(): false;
  }

  public DynTab getSelectedTab()
  {
    String selectedTabId = getSelectedTabId();
    if (selectedTabId != null)
    {
      return getTab(selectedTabId);
    }
    return null;
  }

  /**
   * Check whether there the same tab is already open. This method returns true when
   * there is a tab with the same unqiue identifier, or when not set, the same taskFlowId.
   * @param tab
   * @return
   */
  public DynTab getMatchingTab(DynTab tab)
  {
    if (tab.getUniqueIdentifier() == null)
    {
      return getFirstTabWithTaskFlowId(tab.getTaskFlowIdString());
    }

    for (DynTab curtab: this.tabList)
    {
      if (tab.getUniqueIdentifier().equals(curtab.getUniqueIdentifier()))
      {
        return curtab;
      }
    }
    return null;
  }

  public DynTab getFirstTabWithTaskFlowId(String taskflowId)
  {
    for (DynTab tab: this.tabList)
    {
      if (tab.getTaskFlowId().getFullyQualifiedName().equals(taskflowId))
      {
        return tab;
      }
    }

    return null;
  }

  public void updateDocumentTitle()
  {
    if (isDoUpdateDocumentTitle())
    {
      String js = "AdfPage.PAGE.findComponent('" + getDocumentId() + "').setTitle('" + getDocumentTitle() + "')";
      RichClientUtils.getInstance().writeJavaScriptToClient(js);
    }
  }

  public String getDocumentTitle()
  {
    String tabTitle = getSelectedTab() != null? getSelectedTab().getTitle(): "";
    return getDocumentTitlePrefix() + tabTitle;
  }


  public int indexOf(DynTab tab)
  {
    return getTabList().indexOf(tab);
  }

  public DynTab getTabAt(int index)
  {
    return getTabList().get(index);
  }

  public DynTab getTab(String id)
  {
    return getTabMap().get(id);
  }

  public MenuModel getTabMenuModel()
  {
    List<DynTab> tabList = getTabList();
    return new ChildPropertyMenuModel(tabList, "children",
                                      Collections.singletonList(tabList.indexOf(getSelectedTab())));

  }

  public void setTabsNavigationPane(UIComponent tabsNavigationPane)
  {

    this.tabsNavigationPane = ComponentReference.newUIComponentReference(tabsNavigationPane);
  }

  public UIComponent getTabsNavigationPane()
  {    
    return tabsNavigationPane!=null ? tabsNavigationPane.getComponent() : null;
  }

  public void setContentArea(UIComponent contentArea)
  {

    this.contentArea = ComponentReference.newUIComponentReference(contentArea);
  }

  public UIComponent getContentArea()
  {

    return contentArea!=null ? contentArea.getComponent() : null;
  }

  public void tabActivatedEvent(ActionEvent action)
  {
    UIComponent component = action.getComponent();
    String tabId = String.valueOf(component.getAttributes().get("tabId"));
    setSelectedTabId(tabId);
  }


  public void tabRemovedEvent(ActionEvent action)
  {
    removeCurrentTab();
    updateDocumentTitle();
  }

  public void tabActivatedEvent(DisclosureEvent action)
  {
    UIComponent component = action.getComponent();
    String tabId = String.valueOf(component.getAttributes().get("tabId"));
    setSelectedTabId(tabId);
  }

  protected void removeTab(String id, boolean force)
  {
    DynTab tab = getTab(id);
    if (tab == null)
    {
      throw new IllegalArgumentException("Tab: " + id + " unknown!");
    }

    removeTab(tab, force);
  }

  protected void removeTab(DynTab tab, boolean force)
  {
    if (!tab.isCloseable() && !force)
    {
      JsfUtils.getInstance().addMessage(TAB_NOT_CLOSEABLE_MESSAGE_KEY);
      return;
    }

    if (tab.isDirty() && !force)
    {
      setTabToRemoveId(tab.getId());
      RichClientUtils.getInstance().showPopup(getTabDirtyPopup(), null);
      return;
    }

    boolean isSelectedTab = tab.equals(getSelectedTab());
    // get tab index
    int tabIndex = tabList.lastIndexOf(tab);

    // remove the tab from the list, map and taskflow list
    tabList.remove(tabIndex);
    taskFlowBindingAttrsList.remove(tabIndex);
    tabMap.remove(tab.getId());
        
    // Determine the new tab that should be should be currently open (in case the current
    // tab was closed).
    if (isSelectedTab && getTabList().size() > 0)
    {
      int newIndex = tabIndex;
      if (newIndex >= tabList.size())
      {
        newIndex = tabList.size()-1;
      }
      setSelectedTabId(tabList.get(newIndex).getId());
    }
    refreshTabsAndContentArea();
  }

  protected void refreshTabsNavigationPane()
  {
    sLog.info("Adding tabs navigation pane as partial target");
    addPartialTarget(getTabsNavigationPane());
  }

  protected void refreshTabsAndContentArea()
  {
    refreshTabsNavigationPane();
    refreshContentArea();
  }

  protected void refreshContentArea()
  {
    sLog.info("Adding tabs content area as partial target");
    addPartialTarget(getContentArea());      
  }

  protected void addPartialTarget(UIComponent component)
  {
    if (component != null)
    {
      AdfFacesContext.getCurrentInstance().addPartialTarget(component);
    }
  }

  public void removeTab(ItemEvent itemEvent)
  {
    UIComponent component = itemEvent.getComponent();
    String tabId = String.valueOf(component.getAttributes().get("tabId"));
    removeTab(tabId);
  }

  public void selectTab(DisclosureEvent event)
  {
    UIComponent component = event.getComponent();
    String tabId = String.valueOf(component.getAttributes().get("tabId"));
    setSelectedTabId(tabId);
  }

  public void setDoUpdateDocumentTitle(boolean doUpdateDocumentTitle)
  {
    this.doUpdateDocumentTitle = doUpdateDocumentTitle;
  }

  public boolean isDoUpdateDocumentTitle()
  {
    return doUpdateDocumentTitle;
  }

  public void setTabDirtyPopup(RichPopup tabDirtyPopup)
  {
    this.tabDirtyPopup = ComponentReference.newUIComponentReference(tabDirtyPopup);
  }

  public RichPopup getTabDirtyPopup()
  {
    return tabDirtyPopup!=null ? tabDirtyPopup.getComponent() : null;
  }

  public void handleDirtyTabDialog(DialogEvent event)
  {
    if (event.getOutcome() == DialogEvent.Outcome.ok || event.getOutcome() == DialogEvent.Outcome.yes)
    {
      if (getTabToRemoveId()!=null)
      {
        DynTab tab = getTab(getTabToRemoveId());
        setTabToRemoveId(null);
        removeTab(tab, true);  
      }
      else
      {
        removeCurrentTab(true);        
      }
    }
  }

  public void setDocumentTitlePrefix(String documentTitlePrefix)
  {
    this.documentTitlePrefix = documentTitlePrefix;
  }

  public String getDocumentTitlePrefix()
  {
    return documentTitlePrefix;
  }

  public void setDocumentId(String documentId)
  {
    this.documentId = documentId;
  }

  public String getDocumentId()
  {
    return documentId;
  }

  public List<TaskFlowBindingAttributes> getTaskFlowList()
  {
    return taskFlowBindingAttrsList;
  }

  public void handleTooManyTabsOpen()
  {
    JsfUtils.getInstance().addMessage(TOO_MANY_TABS_OPEN_MESSAGE_KEY);
  }

  public void setCheckTabExists(boolean checkTabExists)
  {
    this.checkTabExists = checkTabExists;
  }

  public boolean isCheckTabExists()
  {
    return checkTabExists;
  }

  public void setTabToRemoveId(String tabToRemoveId)
  {
    String oldTabToRemoveId = this.tabToRemoveId;
    this.tabToRemoveId = tabToRemoveId;
    propertyChangeSupport.firePropertyChange("tabToRemoveId", oldTabToRemoveId, tabToRemoveId);
  }

  public void addPropertyChangeListener(PropertyChangeListener l)
  {
    propertyChangeSupport.addPropertyChangeListener(l);
  }

  public void removePropertyChangeListener(PropertyChangeListener l)
  {
    propertyChangeSupport.removePropertyChangeListener(l);
  }

  public String getTabToRemoveId()
  {
    return tabToRemoveId;
  }
}

