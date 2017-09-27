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
import oracle.adf.share.logging.ADFLogger;


public class DynTab
  implements Serializable
{

  private static final ADFLogger sLog =
    ADFLogger.createADFLogger(DynTab.class);

  private String id;
  private String name;
  private boolean isDirty;
  private String title;
  private Object uniqueIdentifier;
  private TaskFlowId taskFlowId;
  private String taskFlowIdString;
  private Map parameters = new HashMap();
  private String parameterMapExpression;
  private boolean isCloseable = true;
  private boolean parameterValuesResolved = false;

  public DynTab()
  {
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
      // try viewScope, must be used when tab is initially displayed
      expr = "#{viewScope." + beanName + "}";
      tab =
        (DynTab) JsfUtils.getExpressionValue(expr);
    }
    if (tab == null)
    {
      sLog.severe("Could not find DynTab bean " + beanName + "!");
    }
    tab.setName(tabName);
    return tab;
  }

  public DynTab(String id, TaskFlowId taskFlowId)
  {
    isDirty = false;
    this.id = id;
    this.taskFlowId = taskFlowId;
  }

  public void setTitle(String title)
  {
    this.title = title;
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

  public void setTaskFlowId(TaskFlowId id)
  {

    taskFlowId = id;
  }

  public TaskFlowId getTaskFlowId()
  {

    return taskFlowId;
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

  public void setUniqueIdentifier(Object uniqueIdentifier)
  {

    this.uniqueIdentifier = uniqueIdentifier;
  }

  public Object getUniqueIdentifier()
  {

    return uniqueIdentifier;
  }

  public void setTaskFlowIdString(String taskflowIdString)
  {
    this.taskFlowIdString = taskflowIdString;
    if (taskflowIdString != null)
    {
      setTaskFlowId(TaskFlowId.parse(taskflowIdString));
    }
  }

  public String getTaskFlowIdString()
  {
    return taskFlowIdString;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getId()
  {
    return id;
  }

  public void setParameterMapExpression(String parameterMapExpression)
  {
    this.parameterMapExpression = parameterMapExpression;
  }

  public String getParameterMapExpression()
  {
    if (getName()==null)
    {
      throw new IllegalStateException("Cannot derive parameter map expression, name property is NOT set");
    }
    return "#{"+getName()+"DynTab.parameters}";
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public String getTitle()
  {
    return title;
  }
}
