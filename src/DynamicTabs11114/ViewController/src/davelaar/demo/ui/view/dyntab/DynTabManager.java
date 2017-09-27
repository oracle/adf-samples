/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package davelaar.demo.ui.view.dyntab;


import davelaar.demo.ui.util.JsfUtils;
import davelaar.demo.ui.util.RichClientUtils;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
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
  private static final ADFLogger sLog =
    ADFLogger.createADFLogger(DynTabManager.class);


  private static final String TAB_NOT_CLOSEABLE_MESSAGE_KEY =
    "TAB_NOT_CLOSEABLE";
  private static final String TOO_MANY_TABS_OPEN_MESSAGE_KEY =
    "TOO_MANY_TABS_OPEN";
  private DynTabTracker tabTracker;
  private ComponentReference contentArea;
  private ComponentReference tabsNavigationPane;
  private Map tabsDisclosed = new TabsDisclosed();
  private boolean doUpdateDocumentTitle = false;
  private ComponentReference<RichPopup> tabDirtyPopup;
  private String documentTitlePrefix = "";
  private String documentId = "doc0";
  private List<String> initialTabs = new ArrayList<String>();
  private String tabToRemoveId;


  public DynTabManager()
  {
  }

  /**
   * Returns a DynTabManager instance. This can be a subclass of DynTabManager
   * because the JSF expression #{viewScope.dynTabManager} is evaluated to retrieve the instance. So,
   * the actual class defined in an ADF Taskflow through the managed bean
   * facility under key "viewScope.dynTabManager" will be returned.
   * If no context is found, we also try to find it under pageFlowScope.dynTabManager, because it might have been
   * passed as taskflow parameter.
   *
   * @return DynTabContext
   */
  public static DynTabManager getCurrentInstance()
  {
    // viewScope might not be available yet, which results in NPE when evaluating.
    // In this case, we just return null
    try
    {
      DynTabManager context =
        (DynTabManager) JsfUtils.getExpressionValue("#{viewScope.dynTabManager}");
      if (context == null)
      {
        context =
            (DynTabManager) JsfUtils.getExpressionValue("#{pageFlowScope.dynTabManager}");
      }
      return context;
    }
    catch (Exception e)
    {
      // do nothing
    }
    return null;
  }

  @PostConstruct
  /**
   * Process initially displayed tabs
   */
  public void init()
  {
    for (String tabName : initialTabs)
    {
      launchTab(tabName);
    }    
    if (initialTabs.size()>0)
    {
      // set first initial tab as selected
      setSelectedTabId("r0");
    }
  }

  public TaskFlowId getEmptyTaskFlowId()
  {
    return getTabTracker().getEmptyTaskFlowId();
  }

  public void setMainContent(String taskflowId)
    throws TabContentAreaDirtyException
  {
    setMainContent(taskflowId, null);
  }

  public void setMainContent(String taskflowId,
                             Map<String, Object> parameters)
    throws TabContentAreaDirtyException
  {

    DynTab tab = getSelectedTab();
    if (tab != null)
    {
      if (tab.isDirty())
      {
        throw new TabContentAreaDirtyException();
      }

      tab.setTitle("");
      tab.setTaskflowId(TaskFlowId.parse(taskflowId));
    }
    setTabsRendered(false);
    refreshTabsAndContentArea();
  }

  public void handleTooManyTabsOpen()
  {
    JsfUtils.getInstance().addMessage(TOO_MANY_TABS_OPEN_MESSAGE_KEY);
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

  public void addOrSelectTab(DynTab tab)
  {
    sLog.info("TaskflowId: " + tab.getTaskFlowIdString() + ", tabUniqueIdentifier: " + tab.getUniqueIdentifier() + ", params: " +
              tab.getParameters());

    DynTab existingTab = getMatchingTab(tab);

    if (existingTab != null)
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

  public void addTab(DynTab tabToAdd)
  {

    if (tabTracker.getNumActive() >= tabTracker.getMaxNumberOfTabs())
    {
      handleTooManyTabsOpen();
    }
    else
    {
      // The list of tabs doesn't get fragmented, so the first inactive tab always
      // shows as the right most tab.
      DynTab tab = getFirstInactiveTabOrThrow();
      tab.setTitle(tabToAdd.getTitle());
      tab.setActive(true);
      tab.setTaskflowId(tabToAdd.getTaskflowId());
      tab.setUniqueIdentifier(tabToAdd.getUniqueIdentifier());
      tab.setParameters(tabToAdd.getParameters());
      // add the DynTabManager instance as taskflow parameter so the 
      // taskflow itself can open new tabs, and close tabs. 
      tab.getParameters().put("dynTabManager", this);
      // add tab id so the taskflow iktself can change its label
      tab.getParameters().put("dynTabId", tab.getId());      
      tabTracker.setNumActive(tabTracker.getNumActive() + 1);
      setSelectedTabId(tab.getId());
    }
  }

  private DynTab getFirstInactiveTabOrThrow()
  {
    // We can do this, because we always make sure that all active tabs are in the
    // front of the list.
    DynTab tab = tabTracker.getTabList().get(tabTracker.getNumActive());
    if (!tab.isActive())
    {
      return tab;
    }

    throw new IllegalStateException("TabList state is corrupted!");
  }

  public void markCurrentTabDirty(boolean isDirty)
  {

    markTabDirty(getSelectedTabId(), isDirty);
  }

  public void markTabDirty(String id, boolean isDirty)
  {
    DynTab tab = getTab(id);
    if (tab.isDirty()!=isDirty)
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

  public void removeCurrentTab()
  {
    removeTab(getSelectedTabId(), false);
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

  public void removeTab(String id)
  {
    removeTab(id, false);
  }

  public boolean isTagSetDirty()
  {
    for (DynTab tab: tabTracker.getActiveTabList())
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

  public String getSelectedTabIdentifier()
  {
    DynTab tab = getSelectedTab();
    return tab != null? tab.getUniqueIdentifier(): "";
  }


  public void setTabsRendered(boolean render)
  {

    tabTracker.setTabsRendered(render);
  }

  public String getSelectedTabId()
  {
    return tabTracker.getSelectedTabId();
  }

  public boolean isTabsRendered()
  {

    return tabTracker.isTabsRendered();
  }

  public DynTab getMatchingTab(DynTab compareTab)
  {
    if (compareTab.getUniqueIdentifier() == null)
    {
      return getFirstTabWithTaskFlowId(compareTab.getTaskFlowIdString());
    }

    for (DynTab tab: tabTracker.getActiveTabList())
    {
      if (compareTab.getUniqueIdentifier().equals(tab.getUniqueIdentifier()))
      {
        return tab;
      }
    }

    return null;
  }

  public DynTab getFirstTabWithTaskFlowId(String taskflowId)
  {
    for (DynTab tab: tabTracker.getActiveTabList())
    {
      if (tab.getTaskflowId().getFullyQualifiedName().equals(taskflowId))
      {
        return tab;
      }
    }

    return null;
  }


  public void setSelectedTabId(String id)
  {
    DynTab tab = getTab(id);

    if (tab == null)
    {
      throw new IllegalArgumentException("Tab: " + id + " unknown!");
    }

    setSelectedTab(tab);
  }

  public void setSelectedTab(DynTab tab)
  {
    tabTracker.setSelectedTabId(tab.getId());
    tab.setActive(true);
    refreshTabsAndContentArea();
    updateDocumentTitle();
  }

  public void updateDocumentTitle()
  {
    if (isDoUpdateDocumentTitle())
    {
      String js =
        "AdfPage.PAGE.findComponent('" + getDocumentId() + "').setTitle('" +
        getDocumentTitle() + "')";
      RichClientUtils.getInstance().writeJavaScriptToClient(js);
    }
  }

  public String getDocumentTitle()
  {
    String tabTitle =
      getSelectedTab() != null? getSelectedTab().getTitle(): "";
    return getDocumentTitlePrefix() + tabTitle;
  }

  public Map<String, DynTab> getTabMap()
  {
    return Collections.unmodifiableMap(tabTracker.getTabMap());
  }

  public int indexOf(DynTab tab)
  {
    return tabTracker.getTabList().indexOf(tab);
  }

  public DynTab getTabAt(int index)
  {
    return tabTracker.getTabList().get(index);
  }

  public DynTab getTab(String id)
  {
    return tabTracker.getTabMap().get(id);
  }

  public List<DynTab> getActiveTabList()
  {
    return Collections.unmodifiableList(tabTracker.getActiveTabList());
  }

  public MenuModel getTabMenuModel()
  {
    List<DynTab> tabList = tabTracker.getTabList();
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
    UIComponent component = action.getComponent();
    String tabId = String.valueOf(component.getAttributes().get("tabId"));
    removeTab(tabId);
    updateDocumentTitle();
  }

  public Map getTabDisclosed()
  {
    return tabsDisclosed;
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

    // Completely clear the tab.
    tab.setTaskflowId(getEmptyTaskFlowId());
    tab.setParameters(null);
    tab.setUniqueIdentifier(null);
    tab.setTitle("");
    tab.setActive(false);
    tab.setActivated(false);

    // Reduce the numActive count.
    tabTracker.setNumActive(tabTracker.getNumActive() - 1);

    // Make sure that all active tabs are in the front of the list. This will
    // prevent fragmentation of the tablist, so this will make everything a lot
    // easier.
    List<DynTab> tabList = tabTracker.getTabList();
    int oldIndex = tabList.indexOf(tab);
    tabList.add(tabList.remove(oldIndex));

    // Determine the tab that should be should be currently open (in case the current
    // tab was closed).
    if (tab.equals(getSelectedTab()) && tabTracker.getNumActive() > 0)
    {
      int newIndex = oldIndex;
      if (newIndex >= tabTracker.getNumActive())
      {
        newIndex = tabTracker.getNumActive()-1;
      }

      setSelectedTab(tabList.get(newIndex));
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

  public DynTabTracker getTabTracker()
  {
    return tabTracker;
  }

  public void setTabTracker(DynTabTracker tabTracker)
  {
    this.tabTracker = tabTracker;
  }

  public void removeTab(ItemEvent itemEvent)
  {
    UIComponent component = itemEvent.getComponent();
    String tabId = String.valueOf(component.getAttributes().get("tabId"));
    removeTab(tabId);
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
    return tabDirtyPopup!=null? tabDirtyPopup.getComponent() : null;
  }

  public void handleDirtyTabDialog(DialogEvent event)
  {
    if (event.getOutcome() == DialogEvent.Outcome.ok ||
        event.getOutcome() == DialogEvent.Outcome.yes)
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

  public void setInitialTabs(List<String> initialTabs)
  {
    this.initialTabs = initialTabs;
  }

  public List<String> getInitialTabs()
  {
    return initialTabs;
  }

  public void setTabToRemoveId(String tabToRemoveId)
  {
    this.tabToRemoveId = tabToRemoveId;
  }

  public String getTabToRemoveId()
  {
    return tabToRemoveId;
  }

  public static final class TabContentAreaDirtyException
    extends Exception
  {

    public TabContentAreaDirtyException()
    {

    }
  }

  public static final class TabContentAreaNotReadyException
    extends RuntimeException
  {

    public TabContentAreaNotReadyException()
    {

    }
  }

  public final class TabOverflowException
    extends Exception
  {

    final DynTabManager this$0;

    public TabOverflowException()
    {

      super();
      this$0 = DynTabManager.this;
    }

    public void handleDefault()
    {
      JsfUtils.getInstance().addMessage(TOO_MANY_TABS_OPEN_MESSAGE_KEY);
    }
  }

  private class TabsDisclosed
    extends HashMap
  {
    public Object get(Object key)
    {
      if (key instanceof DynTab)
      {
        DynTab tab = (DynTab) key;
        return tab.equals(getSelectedTab());
      }
      return false;
    }
  }
}

