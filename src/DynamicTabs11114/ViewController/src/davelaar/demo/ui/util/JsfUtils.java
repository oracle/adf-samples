/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package davelaar.demo.ui.util;


import com.sun.faces.util.MessageFactory;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.adf.controller.ControllerContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;
import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.component.UIXCollection;
import org.apache.myfaces.trinidad.component.UIXCommand;
import org.apache.myfaces.trinidad.component.UIXEditableValue;
import org.apache.myfaces.trinidad.component.UIXForm;
import org.apache.myfaces.trinidad.component.UIXSubform;


/**
 * Set of convenience methods when working with JSF
 * @author Steven Davelaar
 */
public class JsfUtils
{
  private static final ADFLogger logger = ADFLogger.createADFLogger(JsfUtils.class);
  private static final String CREATE_MODES_KEY = "createModes";

  public JsfUtils()
  {
  }

  private static JsfUtils instance;

  private static final String APPLICATION_FACTORY_KEY =
    "javax.faces.application.ApplicationFactory";

  public void addMessage(String messageKey)
  {
    addMessage(messageKey, null);
  }

  public void addMessage(String messageKey, Object[] arguments)
  {
    addMessage(messageKey, arguments, FacesMessage.SEVERITY_INFO);
  }

  public void addWarning(String messageKey)
  {
    addWarning(messageKey, null);
  }

  public void addWarning(String messageKey, Object[] arguments)
  {
    addMessage(messageKey, arguments, FacesMessage.SEVERITY_WARN);
  }

  public void addError(String messageKey)
  {
    addError(messageKey, (Object[]) null);
  }

  public void addError(String componentId, String messageKey)
  {
    addMessage(componentId, messageKey, null, FacesMessage.SEVERITY_ERROR);
  }

  public void addError(String componentId, String messageKey,
                       Object[] arguments)
  {
    addMessage(componentId, messageKey, arguments,
               FacesMessage.SEVERITY_ERROR);
  }

  /**
   * This adds a Faces Message of type error with the message text as specfied
   * @param messageText
   */
  public void addFormattedError(String messageText)
  {
    addFormattedMessage(null, messageText, FacesMessage.SEVERITY_ERROR);
  }

  /**
   * This adds a Faces Message of type error with the message text as specfied
   * @param messageText
   */
  public void addFormattedError(String componentId, String messageText)
  {
    addFormattedMessage(componentId, messageText,
                        FacesMessage.SEVERITY_ERROR);
  }

  /**
   * This adds a Faces Message of type warning with the message text as specfied
   * @param messageText
   */
  public void addFormattedWarning(String messageText)
  {
    addFormattedMessage(null, messageText, FacesMessage.SEVERITY_WARN);
  }

  /**
   * This adds a Faces Message of type info with the message text as specfied
   * @param messageText
   */
  public void addFormattedInfo(String messageText)
  {
    addFormattedMessage(null, messageText, FacesMessage.SEVERITY_INFO);
  }

  /**
   * This adds a Faces Message with the message text as specfied
   * @param componentId
   * @param messageText
   * @param severity
   */
  public void addFormattedMessage(String componentId, String messageText,
                                  FacesMessage.Severity severity)
  {
    FacesMessage message = new FacesMessage(messageText);
    message.setSeverity(severity);
    FacesContext.getCurrentInstance().addMessage(componentId, message);
  }

  /**
   * Returns true when maximum severity is error
   * @return
   */
  public boolean isHasErrors()
  {
    FacesMessage.Severity severity =
      FacesContext.getCurrentInstance().getMaximumSeverity();
    return severity == FacesMessage.SEVERITY_ERROR ||
      severity == FacesMessage.SEVERITY_FATAL;
  }


  /**
   * Returns true when maximum severity is warning
   * @return
   */
  public boolean isHasWarnings()
  {
    FacesMessage.Severity severity =
      FacesContext.getCurrentInstance().getMaximumSeverity();
    return severity == FacesMessage.SEVERITY_WARN;
  }

  /**
   * Returns true when maximum severity is info
   * @return
   */
  public boolean isHasInfo()
  {
    FacesMessage.Severity severity =
      FacesContext.getCurrentInstance().getMaximumSeverity();
    return severity == FacesMessage.SEVERITY_INFO;
  }

  public void addError(String messageKey, Object[] arguments)
  {
    addMessage(messageKey, arguments, FacesMessage.SEVERITY_ERROR);
  }

  public void addFatalError(String messageKey)
  {
    addFatalError(messageKey, null);
  }

  public void addFatalError(String messageKey, Object[] arguments)
  {
    addMessage(messageKey, arguments, FacesMessage.SEVERITY_FATAL);
  }

  public void addMessage(String messageKey, Object[] arguments,
                         FacesMessage.Severity severity)
  {
    addMessage(null, messageKey, arguments, severity);
  }

  public void addMessage(String componentId, String messageKey,
                         Object[] arguments,
                         FacesMessage.Severity severity)
  {
    FacesMessage message = MessageFactory.getMessage(messageKey, arguments);
    message.setSeverity(severity);
    FacesContext.getCurrentInstance().addMessage(componentId, message);
  }

  public Locale getLocale()
  {
    return FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
  }

  /**
   * Defined as static because it is used in getInstance()
   * @return
   */
  public static FacesContext getFacesContext()
  {
    return FacesContext.getCurrentInstance();
  }

  /**
   * Defined as static because it is used in getInstance()
   * @return
   */
  public static Application getApplication()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    if (context != null)
    {
      return FacesContext.getCurrentInstance().getApplication();
    }
    else
    {
      ApplicationFactory afactory =
        (ApplicationFactory) FactoryFinder.getFactory(APPLICATION_FACTORY_KEY);
      return afactory.getApplication();
    }
  }

  /**
   * Evaluates JSF EL expression and returns the value. If the expression
   * does not start with "#{" it is assumed to be a literal value, and
   * the value returned will be the same as passed in.
   * <p>
   * Defined as static because it is used in getInstance()
   * @param jsfExpression
   * @return
   */
  public static Object getExpressionValue(String jsfExpression)
  {
    // when specifying EL expression in managed bean as "literal" value
    // so t can be evaluated later, the # is replaced with $, quite strange
    if (jsfExpression == null)
    {
      return jsfExpression;
    }
    if (jsfExpression.startsWith("${"))
    {
      jsfExpression = "#{" + jsfExpression.substring(2);
    }
    if (!jsfExpression.startsWith("#{"))
    {
      if (jsfExpression.equalsIgnoreCase("true"))
      {
        return Boolean.TRUE;
      }
      else if (jsfExpression.equalsIgnoreCase("false"))
      {
        return Boolean.FALSE;
      }
      // there can be literal text preceding the expression...
      else if (jsfExpression.indexOf("#{")<0)
      {
        return jsfExpression;
      }
    }
    ValueExpression ve =  getApplication().getExpressionFactory().createValueExpression(getFacesContext().getELContext(),jsfExpression,Object.class);
    return ve.getValue(getFacesContext().getELContext());
  }

  /**
   * Returns a JsfUtils instance. This can be a subclass of JsfUtils
   * because the JSF expression #{jsfUtils} is evaluated to retrieve the instance. So,
   * the actual class defined in faces-config through the managed bean
   * facility under key "jsfUtils" will be returned. If this expression does not return
   * a value, or the value is not an instance of JsfUtils, then an
   * instance of JsfUtils will be returned.
   *
   * @return
   */
  public static JsfUtils getInstance()
  {
    if (instance == null)
    {
      Object value = getExpressionValue("#{jsfUtils}");
      if (value instanceof JsfUtils)
      {
        instance = (JsfUtils) value;
      }
      else
      {
        instance = new JsfUtils();
      }
    }
    return instance;
  }

  /**
   * <p>Return the {@link UIComponent} (if any) with the specified
   * <code>id</code>, searching recursively starting at the specified
   * <code>base</code>, and examining the base component itself, followed
   * by examining all the base component's facets and children.
   * Unlike findComponent method of {@link UIComponentBase}, which
   * skips recursive scan each time it finds a {@link NamingContainer},
   * this method examines all components, regardless of their namespace
   * (assuming IDs are unique).
   *
   * @param base Base {@link UIComponent} from which to search
   * @param id Component identifier to be matched
   */
  public static UIComponent findComponent(UIComponent base, String id)
  {

    // Is the "base" component itself the match we are looking for?
    if (id.equals(base.getId()))
    {
      return base;
    }
    // check for direct child
    UIComponent result = base.findComponent(id);
    if (result!=null)
    {
      return result;
    }

    // Search through our facets and children
    UIComponent kid = null;
    Iterator kids = base.getFacetsAndChildren();
    while (kids.hasNext() && (result == null))
    {
      kid = (UIComponent) kids.next();
      if (id.equals(kid.getId()))
      {
        result = kid;
        break;
      }
      result = findComponent(kid, id);
      if (result != null)
      {
        break;
      }
    }
    return result;
  }

  //  public boolean componentTreeHasPendingChanges()
  //  {
  //    UIComponent component = JsfUtils.findComponentInRoot("dataForm");
  //    if (component != null)
  //    {
  //      slogger.fine("Executing componentTreeHasPendingChanges of dataForm");
  //      return childHasPendingChanges(component);
  //    }
  //    return false;
  //  }


  public void resetComponentTree()
  {
    UIComponent component = JsfUtils.findComponentInRoot("dataForm");
    if (component != null)
    {
      logger.fine("Executing resetComponentTree of dataForm");
      resetComponentTree(component);
    }
  }

  public void resetComponentTree(UIComponent component)
  {
    UIComponent form = getContainingForm(component);
    resetChildren(form);
  }

  public UIComponent getContainingForm(UIComponent component)
  {
    UIComponent previous = component;
    for (UIComponent parent = component.getParent(); parent != null;
         parent = parent.getParent())
    {
      if ((parent instanceof UIForm) || (parent instanceof UIXForm) ||
          (parent instanceof UIXSubform))
        return parent;
      previous = parent;
    }

    return previous;
  }

  public void setImmediateItemsValid(UIComponent comp)
  {
    UIComponent kid;
    for (Iterator kids = comp.getFacetsAndChildren(); kids.hasNext();
         resetChildren(kid))
    {
      kid = (UIComponent) kids.next();
      if (kid instanceof EditableValueHolder)
      {
        EditableValueHolder item = (EditableValueHolder) kid;
        if (item.isImmediate())
        {
          item.setValid(true);
        }
      }
    }
  }


  public void resetChildren(UIComponent comp)
  {
    UIComponent kid;
    for (Iterator kids = comp.getFacetsAndChildren(); kids.hasNext();
         resetChildren(kid))
    {
      kid = (UIComponent) kids.next();
      if (kid instanceof UIXEditableValue)
      {
        ((UIXEditableValue) kid).resetValue();
        continue;
      }
      if (kid instanceof EditableValueHolder)
      {
        resetEditableValueHolder((EditableValueHolder) kid);
        continue;
      }
      if (kid instanceof UIXCollection)
        ((UIXCollection) kid).resetStampState();
    }

  }

  //  public boolean childHasPendingChanges(UIComponent comp)
  //  {
  //    boolean changes = false;
  //    UIComponent kid;
  //    for (Iterator kids = comp.getFacetsAndChildren(); kids.hasNext();)
  //    {
  //      kid = (UIComponent) kids.next();
  //      if (kid instanceof EditableValueHolder)
  //      {
  //        if (hasPendingChanges(((EditableValueHolder) kid)))
  //         {
  //           changes = true;
  //           break;
  //         }
  //      }
  //      // recursive call
  //      changes = childHasPendingChanges(kid);
  //    }
  //    return changes;
  //  }
  //
  //  public boolean hasPendingChanges(EditableValueHolder evh)
  //  {
  //    return (
  //    (evh.getLocalValue()==null && evh.getValue()!=null)
  //    || (evh.getLocalValue()!=null && evh.getValue()==null)
  //    || (evh.getLocalValue()!=evh.getValue())
  //            );
  //  }

  public void resetEditableValueHolder(EditableValueHolder evh)
  {
    evh.setValue(null);
    evh.setSubmittedValue(null);
    evh.setLocalValueSet(false);
    evh.setValid(true);
  }

  public void resetComponent(UIComponent comp)
  {
    if (comp instanceof EditableValueHolder)
    {
      resetEditableValueHolder((EditableValueHolder) comp);
    }
  }


  public static UIViewRoot getViewRoot()
  {
    if (getFacesContext() != null)
    {
      return getFacesContext().getViewRoot();
    }
    return null;
  }

  /**
   * Convenience method for setting Session variables.
   * @param key object key
   * @param object value to store
   */
  public static void storeOnSession(String key, Object object)
  {
    FacesContext ctx = FacesContext.getCurrentInstance();
    Map session = ctx.getExternalContext().getSessionMap();
    session.put(key, object);
  }

  /**
   * Convenience method for getting Session variables.
   * @param key object key
   */
  public static Object getFromSession(String key)
  {
    FacesContext ctx = FacesContext.getCurrentInstance();
    Map session = ctx.getExternalContext().getSessionMap();
    return session.get(key);
  }

  /**
   * Convenience method for setting Request attributes.
   * @param key object key
   * @param object value to store
   */
  public static void storeOnRequest(String key, Object object)
  {
    FacesContext ctx = FacesContext.getCurrentInstance();
    Map request = ctx.getExternalContext().getRequestMap();
    request.put(key, object);
  }

  /**
   * Convenience method for getting Request attributes.
   * @param key object key
   */
  public static Object getFromRequest(String key)
  {
    FacesContext ctx = FacesContext.getCurrentInstance();
    Map request = ctx.getExternalContext().getRequestMap();
    return request.get(key);
  }

  /**
   * Get Http request from external context. Return null if request is not a HttpServletRequest
   * @return
   */
  public static HttpServletRequest getHTTPServletRequest()
  {
    Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
    if (request instanceof HttpServletRequest)
    {
      return (HttpServletRequest)request;
    }
    return null;
  }

  /**
   * Get Http response from external context. Return null if response is not a HttpServletResponse
   * @return
   */
  public static HttpServletResponse getHttpServletResponse()
  {
    Object response = FacesContext.getCurrentInstance().getExternalContext().getResponse();
    if (response instanceof HttpServletResponse)
    {
      return (HttpServletResponse)response;
    }
    return null;
  }


  public boolean isPPRRequest()
  {
    return "true".equals(getFacesContext().getExternalContext().getRequestParameterMap().get("partial"));
  }

  /**
   * Does request contain parameter event with value equal to
   * the eventName argument?
   * @param eventName
   * @return
   */
  public boolean requestContainsEvent(String eventName)
  {
    return eventName.equals(getFacesContext().getExternalContext().getRequestParameterMap().get("event"));
  }

  /**
   * Does request contain parameter event with value equal to
   * the eventName argument, and a parameter named source with
   * value equal to source argument.
   * @param eventName
   * @return
   */
  public boolean requestContainsEventWithSource(String eventName,
                                                String source)
  {
    if (source == null)
    {
      return requestContainsEvent(eventName);
    }
    return requestContainsEvent(eventName) &&
      source.equals(getFacesContext().getExternalContext().getRequestParameterMap().get("source"));
  }


  public boolean isPostBack()
  {
    Boolean pb =
      (Boolean) getExpressionValue("#{adfFacesContext.postback}");
    if (pb != null)
    {
      return pb.booleanValue();
    }
    return false;
  }

  /**
   * Reverses boolean expression by adding ! in front of the expression
   * @param expression
   * @return
   */
  public static String reverseBooleanExpression(String expression)
  {
    if (expression == null)
    {
      return null;
    }
    String newExpr = "!(" + stripBracketsExpression(expression) + ")";
    return addBracketsExpression(newExpr);
  }

  public static String concatWithAndExpression(String expression1,
                                               String expression2)
  {
    return concatWithExpression(expression1, expression2, "and");
  }

  public static String concatWithOrExpression(String expression1,
                                              String expression2)
  {
    return concatWithExpression(expression1, expression2, "or");
  }

  public static String concatWithExpression(String expression1,
                                            String expression2,
                                            String operator)
  {
    if (expression1 == null && expression2 == null)
    {
      return null;
    }
    else if (expression1 == null)
    {
      return expression2;
    }
    else if (expression2 == null)
    {
      return expression1;
    }
    String newExpr =
      "(" + stripBracketsExpression(expression1) + ") " + operator + " (" +
      stripBracketsExpression(expression2) + ")";
    return addBracketsExpression(newExpr);
  }

  /**
   * Removes "#{" at start of expression, and "}" at end of expression.
   * @param expression
   * @return
   */
  public static String stripBracketsExpression(String expression)
  {
    if (expression == null)
    {
      return null;
    }
    expression = expression.replaceAll("\$\{", "");
    expression = expression.replaceAll("#\{", "");
    expression = expression.replaceAll("\}", "");
    return expression.trim();
  }

  public static String stripBracketsAndQuotesExpression(String expression)
  {
    return stripBracketsExpression(stripQuotesExpression(expression));
  }

  public static String stripQuotesExpression(String expression)
  {
    if (expression == null)
    {
      return null;
    }
    expression = expression.replaceAll("'", "");
    return expression.trim();
  }

  /**
   * Adds "#{" at start of expression, and "}" at end of expression.
   * @param expression
   * @return
   */
  public static String addBracketsExpression(String expression)
  {
    if (expression == null)
    {
      return null;
    }
    return "#{" + expression.trim() + "}";
  }

  public static String addBracketsAndQuotesExpression(String expression)
  {
    return addBracketsExpression(addQuotesExpression(expression));
  }

  public static String addQuotesExpression(String expression)
  {
    if (expression == null)
    {
      return null;
    }
    return "'" + expression.trim() + "'";
  }

  public static void redirect(String redirectUrl)
  {
    try
    {
      ExternalContext ext =
        FacesContext.getCurrentInstance().getExternalContext();
      ext.redirect(redirectUrl);
    }
    catch (IOException e)
    {
      logger.severe("Error redirecting to " + redirectUrl + ": " +
                 e.getMessage());
    }
  }

  public static void redirectToSelf() {
      FacesContext fctx = FacesContext.getCurrentInstance();
      ExternalContext ectx = fctx.getExternalContext();
      String viewId = fctx.getViewRoot().getViewId();
      ControllerContext controllerCtx = null;
      controllerCtx = ControllerContext.getInstance();
      String activityURL = controllerCtx.getGlobalViewActivityURL(viewId);
      try {
          ectx.redirect(activityURL);
          fctx.responseComplete();
      } catch (IOException e) {
          //Can't redirect
          logger.severe("Error redirecting to self (" + activityURL + "): " +
                     e.getMessage());
          fctx.renderResponse();
      }
  }

  public boolean isValidExpression(String expression)
  {
    boolean valid = true;
    try
    {
      getExpressionValue(expression);
    }
    catch (Exception ex)
    {
      valid = false;
    }
    return valid;
  }

  public PhaseId getLastPhaseId()
  {
    return (PhaseId) getFromRequest("oracle.adfinternal.view.faces.context.LAST_PHASE_ID");
  }

  public PhaseId getCurrentPhaseId()
  {
    return (PhaseId) getFromRequest("oracle.adfinternal.view.faces.context.CURRENT_PHASE_ID");
  }


  /**
   * Return the pageFlowScoped map that holds the createModes for a form page
   * @return
   */
  public Map getCreateModesMap()
  {
    Map createModes =
      (Map) AdfFacesContext.getCurrentInstance().getPageFlowScope().get(CREATE_MODES_KEY);
    if (createModes == null)
    {
      createModes = new HashMap();
      AdfFacesContext.getCurrentInstance().getPageFlowScope().put(CREATE_MODES_KEY,
                                                                  createModes);
    }
    return createModes;
  }

  /**
   * Programmatic invocation of a method that an EL evaluates
   * to. The method must not take any parameters.
   *
   * @param methodExpression EL of the method to invoke
   * @return Object that the method returns
   */
  public static Object invokeELMethod(String methodExpression)
  {
    return invokeELMethod(methodExpression, new Class[0], new Object[0]);
  }

  /**
   * Programmatic invocation of a method that an EL evaluates to.
   *
   * @param methodExpression EL of the method to invoke
   * @param paramTypes Array of Class defining the types of the
   * parameters
   * @param params Array of Object defining the values of the
   * parametrs
   * @return Object that the method returns
   */
  public static Object invokeELMethod(String methodExpression, Class[] paramTypes,
                                Object[] params)
  {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();
    MethodExpression exp = getMethodExpression(methodExpression, null,paramTypes);
    return exp.invoke(elContext, params);
  }

  public static MethodExpression getMethodExpression(String methodExpression, Class returnType, Class[] paramTypes)
  {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();
    ExpressionFactory expressionFactory =
      facesContext.getApplication().getExpressionFactory();
    MethodExpression exp =
      expressionFactory.createMethodExpression(elContext, methodExpression, returnType,
                                               paramTypes);
    return exp;
  }

  public static UIComponent findComponentInRoot(String id)
  {
    UIComponent ret = null;

    FacesContext context = FacesContext.getCurrentInstance();
    if (context != null)
    {
      UIComponent root = context.getViewRoot();
      ret = findComponent(root, id);
    }

    return ret;
  }

  public static UIComponent findComponentMatchingClientId(String clientCompId) 
  {
    FacesContext context = FacesContext.getCurrentInstance();
    UIComponent root = context.getViewRoot();
    return findComponentMatchingClientId(root, clientCompId);
  }

  /**
   * Method to parse the active component clientId to identify the UIComponent
   * instance. 
   * Code based on sample from Frank Nimphius on ADF Code Corner
   *
   * @param startComp The top-level component where we start finding. 
   * @param clientCompId clientId or ClientLocatorId. A clientId contains all the
   *        naming comntainers between the document root and the UI component. A
   *        client locator also contains a row indes if the component is part of a
   *        table rendering
   * @return
   */
  public static UIComponent findComponentMatchingClientId(UIComponent startComp, String clientCompId) {

      String components[] = { };
      if (clientCompId != null) {
          components = clientCompId.split(":");
      }
      UIComponent component = null;

      //get the component

      if (components.length > 0) {
          String componentId = components[0];
          component = startComp.findComponent(componentId);

          if (component != null) {
              for (int i = 1; i < components.length; ++i) {
                  //if the component is in a table, then the clientId
                  //contains an integer vaue that indicates the row index
                  //to parse this out, we use a try/catch block
                  try {
                      Integer.parseInt(components[i]);
                  } catch (NumberFormatException nf) {
                      //the id is not a number, so lets try a get the component
                      if (component != null) {
                          component =
                                  findComponent(component, components[i]);
                      }
                  }
              }
          }
      }

      //if we are here then we have a component or null
      return component;
  }

  public static RichRegion findParentRegion(UIComponent component) {
    UIComponent parent = component.getParent();
    RichRegion region = null;
    while (parent!=null)
    {
      if (parent instanceof RichRegion)
      {
        region = (RichRegion) parent;
        break;
      }
      parent = parent.getParent();
    }
    return region;
  }

  /**
   * If an action event is queued through the use of a function key, and the component of the event
   * has immediate propert set to true, we need to skip the Process Validations phase, and jump to render response right away.
   * Otherwise, we might get validation errors after the associated action(listener) has been executed.
   * The validation and model update phases are not skipped automatically because
   * the immediate property used to queue the event from the client, using AdfCustomEvent.queue only determines
   * the JSF phase, but does not skip phases like a server side event with immediate=true.
   * @param event
   */
  public static void jumpToRenderResponseIfNeeded(ActionEvent event)
  {
    if (event!=null)
    {
      UIComponent comp = event.getComponent();
      if (comp instanceof UIXCommand && ((UIXCommand)comp).isImmediate())
      {
        FacesContext.getCurrentInstance().renderResponse();
      }
    }
  }
}
