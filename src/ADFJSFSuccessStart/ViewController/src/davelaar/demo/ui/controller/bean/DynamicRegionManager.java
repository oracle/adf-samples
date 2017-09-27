/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package davelaar.demo.ui.controller.bean;


import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.context.FacesContext;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.render.ClientEvent;


// these imports are needed in JDev 11.1.1.x
import oracle.adf.controller.internal.metadata.MetadataService;
import oracle.adf.controller.internal.metadata.TaskFlowDefinition;
import oracle.adf.controller.internal.metadata.TaskFlowInputParameter;


/**
 * Generic class that can be used to manage a set of task flows that can be displayed within
 * a dynamic region. This class keeps track of the current task flow displayed, and its
 * parameter map. The configuration of each task flow (taskflow id, name and parameter map)
 * must be done in its own managed bean, named after the task flow suffixed with "TaskFlowConfig" and
 * stored in page flow scope or normal scope. To use this class, perform the following steps:
 * <ul>
 * <li>Create a managed bean that uses this class with pageFlowScope in the (un)bounded taskflow
 * that displays the page with the dynamic region. For example:
 * </li>
 * <li>Create a taskflow binding in the page definition of the page that displays the dynamic region.
 * For example, when you named the managed bean created in the previous step "myDynamicRegionManager", the
 * task flow binding will look like this:
 * <pre>
 * &lt;taskFlow id="myRegion"
 * taskFlowName="${pageFlowScope.myDynamicRegionManager.currentTaskFlowId}"
 * parametersMap="${pageFlowScope.myDynamicRegionManager.currentParamMap}"
 * xmlns="http://xmlns.oracle.com/adf/controller/binding"/&gt;
 * </pre>
 * </li>
 * <li>Add the af:region tag to the page where the dynamic region should be displayed, and set the
 * value to the regionModel property of the taskflow binding:
 * <pre>
 * &lt;af:region id="myRegion" value="#{bindings.myRegion.regionModel}" /&gt;
 * </pre>
 * </li>
 * <li>For each taskflow you want to display in the dynamic region, create a managed bean named after
 * the taskflow suffixed with "TaskFlowConfig", for example:
 * <pre>
 * &lt;managed-bean&gt;
 * &lt;managed-bean-name&gt;DepartmentsTaskFlowConfig&lt;/managed-bean-name&gt;
 * &lt;managed-bean-class&gt;davelaar.demo.ui.controller.bean.TaskFlowConfigBean&lt;/managed-bean-class&gt;
 * &lt;managed-bean-scope&gt;pageFlow&lt;/managed-bean-scope&gt;
 * &lt;managed-property&gt;
 * &lt;property-name&gt;params&lt;/property-name&gt;
 * &lt;property-class&gt;java.util.Map&lt;/property-class&gt;
 * &lt;map-entries&gt;
 * &lt;map-entry&gt;
 * &lt;key&gt;createDepartments&lt;/key&gt;
 * &lt;value&gt;false&lt;/value&gt;
 * &lt;/map-entry&gt;
 * &lt;/map-entries&gt;
 * &lt;/managed-property&gt;
 * &lt;managed-property&gt;
 * &lt;property-name&gt;name&lt;/property-name&gt;
 * &lt;value&gt;Departments&lt;/value&gt;
 * &lt;/managed-property&gt;
 * &lt;managed-property&gt;
 * &lt;property-name&gt;taskFlowName&lt;/property-name&gt;
 * &lt;value&gt;/WEB-INF/adfc-config-Departments.xml#DepartmentsTaskFlow&lt;/value&gt;
 * &lt;/managed-property&gt;
 * &lt;/managed-bean&gt;
 *
 * </pre>
 * </li>
 * <li>To "activate" a taskflow within the dynamic region, all you need to do is calling
 * setCurrentTaskFlowName on the regionManager bean. Optionally, you can set/change the taskflow
 * parameters first by calling getParams().put (in code), on the TaskFlowConfig bean. To set the current taskflow when
 * clicking a commandLink or commandNavigationItem, you can use the setACtionListener, for example:
 * <pre>
 * &lt;af:setActionListener from="#{'myTaskFlow'}"
 * to="#{pageFlowScope.myDynamicRegionManager.currentTaskFlowName}"/&gt;
 * </pre>
 * </li>
 * </ul>
 *
 * @author Steven Davelaar
 */
public class DynamicRegionManager
  implements Serializable
{
  private static final ADFLogger sLog = ADFLogger.createADFLogger(DynamicRegionManager.class);
  private static final String EMPTY_TASK_FLOW_ID = "";
  private static final String TASK_FLOW_NAME = "taskFlowName";

  private String currentTaskFlowId = EMPTY_TASK_FLOW_ID;
  private String currentTaskFlowName = null;
  private boolean enableDeepLinkingFromRequestParams = true;

  /**
   * Map[TaskFlowName -> TaskFlowConfig bean]
   */
  Map<String, TaskFlowConfigBean> taskFlowMap =
    new HashMap<String, TaskFlowConfigBean>();

  protected void taskFlowNotFound()
  {
    currentTaskFlowName = null;
    currentTaskFlowId = EMPTY_TASK_FLOW_ID;
    sLog.severe("setCurrentTaskFlowName: Unknown TaskFlowName!");
  }

  public void setCurrentTaskFlowName(String taskFlowName)
  {
    sLog.info("Task flow name: "+taskFlowName);
    String previousTaskFlowName = getCurrentTaskFlowName(); 
    String previousTaskFlowId = getCurrentTaskFlowId(); 
    if (!taskFlowMap.containsKey(taskFlowName))
    {
      initTaskFlow(taskFlowName);
    }

    currentTaskFlowName = taskFlowName;

    if (taskFlowMap.get(taskFlowName) == null)
    {
      taskFlowNotFound();
      return;
    }

    if (!taskFlowName.equals(previousTaskFlowName)
        && getCurrentTaskFlowId().equals(previousTaskFlowId))
    {
      // we need to explicitly do this because otherwise the 
      // taskflow is not reloaded because the id stays the same
      getTaskFlowConfig(taskFlowName).setParamsChanged(true);      
    }

    currentTaskFlowId = taskFlowMap.get(taskFlowName).getTaskFlowId();

    if (currentTaskFlowId == null)
    {
      taskFlowNotFound();
      return;
    }
  }

  protected void initTaskFlow(String taskFlowName)
  {
    TaskFlowConfigBean configBean = TaskFlowConfigBean.getInstance(taskFlowName);
    if (configBean != null)
    {
      registerTaskFlow(taskFlowName, configBean);

      if (configBean.getName() == null ||
          configBean.getName().length() == 0)
      {
        // For LOV TaskFlow config beans, the name is by default left empty.
        // So set it to the name that it should be in here.
        configBean.setName(taskFlowName);
      }
    }
  }

  public String getCurrentTaskFlowName()
  {
    return currentTaskFlowName;
  }

  public void setCurrentTaskFlowId(String taskFlowId)
  {
    this.currentTaskFlowId = taskFlowId;
    currentTaskFlowName = null;
  }

  public String getCurrentTaskFlowId()
  {
    return currentTaskFlowId;
  }

  public void registerTaskFlow(String name, TaskFlowConfigBean configBean)
  {
    taskFlowMap.put(name, configBean);
  }

  public Map<String, Object> getCurrentParamMap()
  {
    TaskFlowConfigBean config = getTaskFlowConfig(currentTaskFlowName);
    if (config == null)
    {
      return null;
    }
    Map<String, Object> map = config.getParams();
    sLog.info("Content current param map " + currentTaskFlowName + ": " +
               map);
    return map;
  }

  public boolean isCurrentParamMapChanged()
  {
    TaskFlowConfigBean config = getTaskFlowConfig(currentTaskFlowName);
    if (config == null)
    {
      return false;
    }
    boolean changed = config.isParamsChanged();
    sLog.info("Content current param map " + currentTaskFlowName +
               " changed: " + changed);
    return changed;
  }

  public TaskFlowConfigBean getTaskFlowConfig(String taskFlowName)
  {
    return taskFlowMap.get(taskFlowName);
  }

  public TaskFlowConfigBean getCurrentTaskFlowConfig()
  {
    return getTaskFlowConfig(getCurrentTaskFlowName());
  }

  public String getCurrentTaskFlowLabel()
  {
    return getCurrentTaskFlowConfig().getLabel();
  }

  public void swapEmptyTaskFlow()
  {
    currentTaskFlowName = null;
    currentTaskFlowId = EMPTY_TASK_FLOW_ID;
  }

  /**
   * This method can be used by the LOV window to indicate that the LOV popup
   * is closed. The current task flow will be switched to an empty task flow,
   * causing the current task flow to abandon and release its resources.
   * @param event
   */
  public void swapEmptyTaskFlow(ClientEvent event)
  {
    swapEmptyTaskFlow();

    // if event delivery set to immediate=true, short-circuit to renderResponse.
    // Forcing an empty taskflow releases the bindings and view port.
    //    FacesContext context = FacesContext.getCurrentInstance();
    //    context.renderResponse();

    String popupId = (String) event.getParameters().get("popupId");
    sLog.info("Swapping Empty Taskflow on popupClosed for " + popupId);
  }

  public TaskFlowDefinition getCurrentTaskFlowDefinition()
  {
    return getTaskFlowDefinition(getCurrentTaskFlowId());
  }

  public TaskFlowDefinition getTaskFlowDefinition(String taskFlowIdStr)
  {
    MetadataService service = MetadataService.getInstance();
    TaskFlowId taskFlowId = TaskFlowId.parse(taskFlowIdStr);
    TaskFlowDefinition taskFlowDefinition =
      service.getTaskFlowDefinition(taskFlowId);
    return taskFlowDefinition;
  }

  public void swapTaskFlow(String taskFlowName)
  {
    swapTaskFlow(taskFlowName, null);
  }

  /**
   * Swap the current task flow to the taskFlowName passed in as argument.
   * @param taskFlowName
   * @param paramList key-value pairs of param names and values. The param name is checked against
   * the task flow definition of the new task flow. If no parameter definition with such a name
   * exists, the parameter is simply ignored. This allows you to pass in a view-scoped ContextMap
   * that is used for communication between task flows within regions.
   */
  public void swapTaskFlow(String taskFlowName,
                           Map<String, Object> paramList)
  {
    sLog.info("Swapping taskflow to " + taskFlowName);
    setCurrentTaskFlowName(taskFlowName);
    if (paramList != null)
    {
      TaskFlowDefinition taskFlowDefinition =
        getCurrentTaskFlowDefinition();
      Map<String, TaskFlowInputParameter> paramDefs =
        taskFlowDefinition.getInputParameters();
      // check whether the param is actually defined as param name!
      for (String paramName: paramList.keySet())
      {
        if (paramDefs.containsKey(paramName))
        {
          getCurrentParamMap().put(paramName, paramList.get(paramName));
        }
      }
    }
    // init params changed to null, otherwise the new taskflow will be loaded twice
    getTaskFlowConfig(taskFlowName).setParamsChanged(false);      
  }


  public void setEnableDeepLinkingFromRequestParams(boolean enableDeepLinkingFromRequestParams)
  {
    this.enableDeepLinkingFromRequestParams =
        enableDeepLinkingFromRequestParams;
  }

  public boolean isEnableDeepLinkingFromRequestParams()
  {
    return enableDeepLinkingFromRequestParams;
  }

  /**
   * If the managed property "enableDeepLinkingFromRequestParams" is true (default false),
   * and there is a request parameter named "taskFlowName", set that as the current task flow, and
   * put other request parameters in the taskFlow parameter map (except parameters whose name starts with "_afr" or "_adf").
   * This allows deeplinking by specifying a URL
   * like .../faces/UIShell?taskFlowName=Employees&rowKeyValueEmployees=130
   */
  @PostConstruct
  public void deepLinkFromRequestParams()
  {
    // By using the PostConstruct annotation, this method is called after setting the managed properties of the bean.
    if (isEnableDeepLinkingFromRequestParams())
    {
      Map<String, String> deepLinkParameterMap =
        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
      // Check if there was a request param named jhsTaskFlowName
      String taskFlowName =
        deepLinkParameterMap.get(TASK_FLOW_NAME);
      if (taskFlowName != null)
      {
        // set that task flow as the current taskflow
        setCurrentTaskFlowName(taskFlowName);
        sLog.info("Deeplinking to taskflow " + taskFlowName);
        Map<String, Object> taskFlowParameterMap = getCurrentParamMap();
        if (taskFlowParameterMap == null) {
            // probably an invalid taskFlowName is given
            // do nothing
        }
        else {
            // assume that all other params in the request parameters are task flow parameters
            // except the ones that start with "_afr" like _afrLoop, _afrWindow, etc
            for (String deepLinkParamName :
                 deepLinkParameterMap.keySet()) {
                if (!deepLinkParamName.equals(TASK_FLOW_NAME) &&
                    !deepLinkParamName.startsWith("_afr") && !deepLinkParamName.startsWith("_adf")) {
                    taskFlowParameterMap.put(deepLinkParamName,
                                             deepLinkParameterMap.get(deepLinkParamName));
                }
            }
            sLog.info("Content current param map of " +
                       taskFlowName + "TaskFlow: " +
                       taskFlowParameterMap);
        }
      }
    }
  }

}
