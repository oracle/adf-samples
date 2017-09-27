/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package davelaar.demo.ui.view.dyntab;


import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import oracle.adf.controller.TaskFlowId;


public class DynTabTracker  implements Serializable
{

  private String initialTabsExpression;
  private boolean renderTabs = true;
  private String emptyTaskFlowDocument= "/common/config/Blank.xml#blank";


    /**
     * The id of the tab that is currently selected / open.
     */
    private String selectedTabId = null;

    /**
     * The number of bindings in the pagedef en the number of region tabs in the
     * DynamicTab declarative component. This is the absolute maximum number of
     * tabs that can be open at the same time (a technical restraint), while
     * <code>{@link #maxNumberOfTabs}</code> is a functional restraint.
     */
  private int numberOfTabsDefined = 15;

    /**
     * This is the maximum number of tabs that is allowed by the application
     * developer. This cannot exceed 15, unless you change the number of predefined regions
     * and associated taskflow bindings 
     */
    private int maxNumberOfTabs = 15;

    /**
     * The list of tabs that defines the order of the tabs on the screen. All
     * tabs are in this list at all times.
     * <ul>
     * <li><code>tabList[0..numActive)</code> contains the active (used) tabs
     * <li><code>tabList[numActive..n)</code> contains the inactive (unused) tabs, where n =
     * the size of tabList.
     * </ul>
     */
    private final List<DynTab> tabList = new ArrayList<DynTab>();

    /**
     * The <code>tabMap</code> contains all tabs based on their <code>id</code>.
     */
    private final Map<String, DynTab> tabMap = new HashMap<String, DynTab>();

    /**
     * The number of tabs that are currently used (visible on the screen). This is
     * always equal to the number of tabs in <code>tabList</code> that are active
     * (<code>tab.isActive()</code>).
     */
    private int numActive = 0;
  
  public DynTabTracker()
  {
  }

  @PostConstruct
  public void init()
  {
    // Create "placeholder"tabs    
    for (int i = 0; i < getNumberOfTabsDefined(); i++)
    {
        DynTab tab = new DynTab(createId(i), getEmptyTaskFlowId());
        tabList.add(tab);
        tabMap.put(tab.getId(), tab);
    }    
  }

    protected String createId(int n)
    {
      return "r" + n;
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
     * Returns the list of tabs that are currently active on screen. This list is
     * modifiable and can be used to change the order of the tabs on the screen. This
     * list is backed by <code>{@link #getTabList}</code>, so make sure not to modify
     * both structurally at the same time.
     *
     * @return the list of tabs that are currently active on screen
     * @see List#subList
     */
    public List<DynTab> getActiveTabList()
    {
      return getTabList().subList(0, getNumActive());
    }


  void setTabsRendered(boolean rendered)
  {

    renderTabs = rendered;
  }

  boolean isTabsRendered()
  {

    return renderTabs;
  }

    /**
     * Sets the maximum number of tabs that the user is allowed to open.
     * <p>
     * This can never be more than <code>{@link #maxNumberOfTabsDefined}</code>.
     *
     * @param maxNumberOfTabs the new maximum number of tabs that the user is allowed to open
     */
  public void setMaxNumberOfTabs(int maxNumberOfTabs)
  {
      if (maxNumberOfTabs > getNumberOfTabsDefined())
      {
        throw new IllegalArgumentException("maxNumberOfTabs may never exceed " +
                                           getNumberOfTabsDefined());
      }
    this.maxNumberOfTabs = maxNumberOfTabs;
  }

  public int getMaxNumberOfTabs()
  {
    return maxNumberOfTabs;
  }

  public void setInitialTabsExpression(String initialTabsExpression)
  {
    this.initialTabsExpression = initialTabsExpression;
  }

  public String getInitialTabsExpression()
  {
    return initialTabsExpression;
  }

  public void setNumberOfTabsDefined(int numberOfTabsDefined)
  {
    this.numberOfTabsDefined = numberOfTabsDefined;
  }

  public int getNumberOfTabsDefined()
  {
    return numberOfTabsDefined;
  }

  public void setEmptyTaskFlowDocument(String emptyTaskFlowDocument)
  {
    this.emptyTaskFlowDocument = emptyTaskFlowDocument;
  }

  public String getEmptyTaskFlowDocument()
  {
    return emptyTaskFlowDocument;
  }

  public TaskFlowId getEmptyTaskFlowId()
  {
    return TaskFlowId.parse(getEmptyTaskFlowDocument());
  }

    public void setSelectedTabId(String selectedTabId) {
        this.selectedTabId = selectedTabId;
    }

    public String getSelectedTabId() {
        return selectedTabId;
    }

    /**
     * Sets the number of tabs that are currently active. Make sure that this number
     * is always equal to the number of tabs in <code>{@link #getTabList}</code> that
     * are active (<code>tab.isActive()</code>).
     *
     * @param numActive the new number of tabs that is currently active
     */
    public void setNumActive(int numActive)
    {
      this.numActive = numActive;
    }

    /**
     * Returns the number of tabs that are currently active. This is
     * always equal to the number of tabs in <code>{@link #getTabList}</code> that
     * are active (<code>tab.isActive()</code>).
     *
     * @return the number of tabs that are currently active
     */
    public int getNumActive()
    {
      return numActive;
    }

}
