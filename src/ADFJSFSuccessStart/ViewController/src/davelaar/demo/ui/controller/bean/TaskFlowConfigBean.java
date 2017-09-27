/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package davelaar.demo.ui.controller.bean;


import davelaar.demo.ui.util.JsfUtils;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import oracle.adf.share.logging.ADFLogger;


/**
 * Class that holds all info required to display a task flow in a dynamic
 * region, or in a dynamic tab.
 *
 * @author Steven Davelaar
 */
public class TaskFlowConfigBean implements Serializable
{
  private static final ADFLogger sLog = ADFLogger.createADFLogger(TaskFlowConfigBean.class);
  Map<String, Object> params = new ParamMap();
  String name;
  String label;
  String tabUniqueIdentifier;;
  String taskFlowId;
  boolean paramsChanged = false;
  List<String> permissionList;

  /**
   * Looks up the taskflow config bean for given taskflow name.
   * This bean is named after the taskflow, suffixed with "TaskFlowConfig" and placed in
   * pageFlowScope
   * @param taskFlowName
   */
  public static TaskFlowConfigBean getInstance(String taskFlowName)
  {
    String beanName = taskFlowName + "TaskFlowConfig";
    String expr = "#{pageFlowScope." + beanName + "}";
    TaskFlowConfigBean configBean =
      (TaskFlowConfigBean) JsfUtils.getExpressionValue(expr);
    if (configBean == null)
    {
      expr = "#{pageFlowScope.parentContext." + beanName + "}";
      configBean = (TaskFlowConfigBean) JsfUtils.getExpressionValue(expr);
    }
    if (configBean == null)
    {
      // try standard scopes, flex region def bean is in app scope....
      expr = "#{" + beanName + "}";
      configBean = (TaskFlowConfigBean) JsfUtils.getExpressionValue(expr);
    }
    if (configBean == null)
    {
      sLog.severe("Could not find taskflow config bean " + beanName + "!");
    }
    else if (!taskFlowName.equals(configBean.getName()))
    {
      sLog.warning("initTaskFlow: Value of #{" + beanName +
                ".name} does not match bean name!");
    }
    return configBean;
  }

  public void setParams(Map<String, Object> params)
  {
    params.clear();
    params.putAll(params);
  }

  @PostConstruct 
  public void postConstruct()
  {
    // By using the PostConstruct annotation, this method is called after setting the 
    // managed properties of the bean. We need to reset here
    // paramsChanged to false, otherwise, the first time we start a task flow it will be
    // refreshed twice
    setParamsChanged(false);     
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Map<String, Object> getParams()
  {
    return params;
  }

  public void setTaskFlowId(String taskFlowId)
  {
    this.taskFlowId = taskFlowId;
  }

  public String getName()
  {
    return name;
  }

  public String getTaskFlowId()
  {
    return taskFlowId;
  }

  public void setParamsChanged(boolean paramsChanged)
  {
    this.paramsChanged = paramsChanged;
  }

  /**
   * This method returns true when the parameter maps has changed since the previous
   * invocation of this method.
   * @return
   */
  public boolean isParamsChanged()
  {
   if (paramsChanged)
   {
     paramsChanged = false;
     return true;
   }
   return false;
  }

  /**
   * Set the label that is used when a dynamic tab is created for this taskflow.
   * @param label
   */
  public void setLabel(String label)
  {
    this.label = label;
  }

  /**
   * Return the label that is used when a dynamic tab is created for this taskflow.
   * If no label is set, the name of the taskflow is returned.
   * @return
   */
  public String getLabel()
  {
    if (label==null)
    {
      return getName();
    }
    return label;
  }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }

    public List<String> getPermissionList() {
        return permissionList;
    }

  public void setTabUniqueIdentifier(String tabUniqueIdentifier)
  {
    this.tabUniqueIdentifier = tabUniqueIdentifier;
  }

  public String getTabUniqueIdentifier()
  {
    return tabUniqueIdentifier;
  }

  class ParamMap extends HashMap implements Serializable
  {
    @Override
    public Object put(Object key, Object value)
    {
      setParamsChanged(true);
      return super.put(key, value);
    }

    @Override
    public void putAll(Map m)
    {
      setParamsChanged(true);
      super.putAll(m);
    }

    @Override
    public Object remove(Object key)
    {
      setParamsChanged(true);
      return super.remove(key);
    }

    @Override
    public void clear()
    {
      setParamsChanged(true);
      super.clear();
    }

  }
}
