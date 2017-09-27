/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package davelaar.demo.ui.view.dyntab;


import davelaar.demo.ui.util.JsfUtils;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.controller.internal.binding.DCTaskFlowBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;

import oracle.binding.BindingContainer;


public class DynTab
  implements Serializable
{

  private static final ADFLogger sLog =
    ADFLogger.createADFLogger(DynTab.class);

  private static final String PAGE_TEMPLATE_BINDING_KEY =
    "dynTabsTemplateBinding";
  private String id = null;
  private String name;
  private boolean isActive;
  private boolean activated = false;
  private boolean isDirty;
  private boolean isSelected;
  private String title;
  private String uniqueIdentifier;
  private TaskFlowId taskflowId;
  private String taskFlowIdString;
  private Map parameters = new HashMap();
  private boolean isCloseable = true;
  private boolean parameterValuesResolved = false;

  public DynTab()
  {
  }

  public DynTab(String id, TaskFlowId taskFlowId)
  {

    isActive = false;
    isDirty = false;
    this.id = id;
    taskflowId = taskFlowId;
  }

  /**
   * Looks up the dyn tab bean for given tab name.
   * This bean is named after the tab, suffixed with "DynTab" and placed in
   * request scope
   * @param tabName
   */
  public static DynTab getInstance(String tabName)
  {
    String beanName = tabName + "DynTab";
    String expr = "#{" + beanName + "}";
    DynTab tab =
      (DynTab) JsfUtils.getExpressionValue(expr);
    if (tab == null)
    {
      sLog.severe("Could not find DynTab bean " + beanName + "!");
    }
    tab.setName(tabName);
    return tab;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getTitle()
  {

    return title;
  }

  public void setActive(boolean rendered)
  {

    isActive = rendered;

    if (!isActive)
    {
      setDirty(false);
    }
  }

  public boolean isActive()
  {

    return isActive;
  }

  public void setDirty(boolean isDirty)
  {

    this.isDirty = isDirty;
  }

  public boolean isDirty()
  {

    return isDirty;
  }

  public void setCloseable(boolean isCloseable)
  {

    this.isCloseable = isCloseable;
  }

  public boolean isCloseable()
  {
    return this.isCloseable;
  }

  public void setTaskflowId(TaskFlowId id)
  {

    taskflowId = id;
  }

  public TaskFlowId getTaskflowId()
  {

    return taskflowId;
  }

  public void setParameters(Map parameters)
  {

    this.parameters = parameters;
  }

  public Map getParameters()
  {
    Map newParams = new HashMap();
    if (!parameterValuesResolved)
    {
      parameterValuesResolved = true;
      Iterator keys = parameters.keySet().iterator();
      while (keys.hasNext())
      {
        Object key = keys.next();
        Object value = parameters.get(key);
        if (value instanceof String && ((String) value).startsWith("#{"))
        {
          value = JsfUtils.getExpressionValue((String) value);
        }
        newParams.put(key, value);
      }
      parameters = newParams;
    }
    return parameters;
  }

  public List getChildren()
  {

    return Collections.emptyList();
  }

  public void setUniqueIdentifier(String uniqueIdentifier)
  {

    this.uniqueIdentifier = uniqueIdentifier;
  }

  public String getUniqueIdentifier()
  {

    return uniqueIdentifier;
  }

  public void setTaskFlowIdString(String taskflowIdString)
  {
    this.taskFlowIdString = taskflowIdString;
    if (taskflowIdString != null)
    {
      setTaskflowId(TaskFlowId.parse(taskflowIdString));
    }
  }

  public String getTaskFlowIdString()
  {
    return taskFlowIdString;
  }

  public void setSelected(boolean isSelected)
  {
    this.isSelected = isSelected;
  }

  public boolean isSelected()
  {
    return isSelected;
  }

  public String getBindingKey()
  {
    return getId();
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getId()
  {
    return id;
  }

  public void setActivated(boolean activated)
  {
    if (activated)
    {
      if (!this.activated)
      {
        sLog.info("Activating tab with id {0} (first time).", getId());
      }
      else
      {
        sLog.info("Tab with id {0} has already been activated.", getId());
      }
    }
  }

  public boolean isActivated()
  {
    return activated;
  }
  
  public DCTaskFlowBinding getBinding()
  {

    BindingContainer bc =
      BindingContext.getCurrent().getCurrentBindingsEntry();
    if (bc == null)
    {
      return null;
    }
    BindingContainer dynTabsBindings =
      (BindingContainer) bc.get(PAGE_TEMPLATE_BINDING_KEY);
    if (dynTabsBindings == null)
    {
      // use current bc directly
      dynTabsBindings = bc;
    }
    DCTaskFlowBinding binding = null;
    binding =
        (DCTaskFlowBinding) dynTabsBindings.get(getBindingKey()); // NOTRANS
    return binding;
  }


  public void setName(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

}
