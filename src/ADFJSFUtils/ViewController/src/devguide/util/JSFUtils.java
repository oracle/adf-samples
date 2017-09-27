/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package devguide.util;
import java.io.IOException;

import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import javax.servlet.http.HttpSession;
/**
 * General useful static utilies for workign with JSF
 * @author Duncan Mills
 * $Id: JSFUtils.java,v 1.4 2006/01/19 01:42:48 steve Exp $
 */
public class JSFUtils {

  private static final String NO_RESOURCE_FOUND = "Missing resource: ";

  /**
   * Method for taking a reference to a JSF binding expression and returning
   * the matching object (or creating it)
   * @param expression
   * @return Managed object
   */
  public static Object resolveExpression(String expression) {
    FacesContext ctx = getFacesContext(); 
    Application app = ctx.getApplication();
    ValueBinding bind = app.createValueBinding(expression);
    return bind.getValue(ctx);
  }

  /**
   * Method for taking a reference to a JSF binding expression and returning
   * the matching Boolean
   * @param expression
   * @return Managed object
   */
  public static Boolean resolveExpressionAsBoolean(String expression) {
    return (Boolean)resolveExpression(expression);
  }

  /**
   * Method for taking a reference to a JSF binding expression and returning
   * the matching String (or creating it)
   * @param expression
   * @return Managed object
   */
  public static String resolveExpressionAsString(String expression) {
    return (String)resolveExpression(expression);
  }
  public static void invalidateSessionAndRedirect(String page) throws IOException {
    HttpSession session = (HttpSession)getFacesContext().getExternalContext().getSession(false);
    if (session != null) {
      session.invalidate();
    }
    redirect(page);
  }
  public static void redirect(String page) throws IOException {
    getFacesContext().getExternalContext().redirect(page);
  }

  /**
   * Convenience method for resolving a reference to a managed bean by name
   * rather than by expression
   * @param beanName
   * @return Managed object
   */
  public static Object getManagedBeanValue(String beanName) {
    StringBuffer buff = new StringBuffer("#{");
    buff.append(beanName);
    buff.append("}");
    return resolveExpression(buff.toString());
  }

  /**
   * Method for setting a new object into a JSF managed bean
   * Note: will fail silently if the supplied object does
   * not match the type of the managed bean
   * @param expression
   * @param newValue
   */
  public static void setExpressionValue(String expression, 
                                        Object newValue) {
    FacesContext ctx = getFacesContext();                                        
    Application app = ctx.getApplication();
    ValueBinding bind = app.createValueBinding(expression);

    //Check that the input newValue can be cast to the property type
    //expected by the managed bean. 
    //If the managed Bean expects a primitive we rely on Auto-Unboxing
    //I could do a more comprehensive check and conversion from the object 
    //to the equivilent primitive but life is too short
    Class bindClass = bind.getType(ctx);
    if (bindClass.isPrimitive() || bindClass.isInstance(newValue)) {
      bind.setValue(ctx, newValue);
    }
  }

  /**
   * Convenience method for setting the value of a managed bean by name
   * rather than by expression
   * @param ctx FacesContext
   * @param beanName
   * @param newValue
   */
  public static void setManagedBeanValue(String beanName, 
                                         Object newValue) {
    StringBuffer buff = new StringBuffer("#{");
    buff.append(beanName);
    buff.append("}");
    setExpressionValue(buff.toString(), newValue);
  }


  /**
   * Convenience method for setting Session variables
   * @param key object key
   * @param object value to store
   */
  public static

  void storeOnSession(String key, Object object) {
    FacesContext ctx = getFacesContext();
    Map sessionState = ctx.getExternalContext().getSessionMap();
    sessionState.put(key, object);
  }

  /**
   * Convenience method for getting Session variables
   * @param ctx FacesContext
   * @param key object key
   */
  public static Object getFromSession(String key) {
    FacesContext ctx = getFacesContext();
    Map sessionState = ctx.getExternalContext().getSessionMap();
    return sessionState.get(key);
  }


  /**
   * Pulls a String resource from the property bundle that
   * is defined under the application &lt;message-bundle&gt; element in
   * the faces config. Respects Locale
   * @param key
   * @return Resource value or placeholder error String
   */
  public static String getStringFromBundle(String key) {
    ResourceBundle bundle = getBundle();
    return getStringSafely(bundle, key, null);
  }


  /**
   * Convenience method to construct a <code>FacesMesssage</code>
   * from a defined error key and severity
   * This assumes that the error keys follow the convention of
   * using <b>_detail</b> for the detailed part of the
   * message, otherwise the main message is returned for the
   * detail as well.
   * @param key for the error message in the resource bundle
   * @param severity
   * @return Faces Message object
   */
  public static FacesMessage getMessageFromBundle(String key, 
                                                  FacesMessage.Severity severity) {
    ResourceBundle bundle = getBundle();
    String summary = getStringSafely(bundle, key, null);
    String detail = getStringSafely(bundle, key + "_detail", summary);
    FacesMessage message = new FacesMessage(summary, detail);
    message.setSeverity(severity);
    return message;
  }
  
  public static void addFacesErrorMessage(String msg) {
    FacesContext ctx = getFacesContext();
    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,"");
    ctx.addMessage(getRootViewComponentId(),fm);
  }  
  
  public static void addFacesErrorMessage(String attrName, 
                                          String msg) {
    // TODO: Need a way to associate attribute specific messages
    //       with the UIComponent's Id! For now, just using the view id.
    //TODO: make this use the internal getMessageFromBundle?
     FacesContext ctx = getFacesContext();
     FacesMessage fm =  new FacesMessage(FacesMessage.SEVERITY_ERROR,attrName,msg);
     ctx.addMessage(getRootViewComponentId(),fm);
  }
  public static void addFacesInformationalMessage(String msg) {
    
  }
  
  // Informational getters
   public static String getRootViewId() {
     return getFacesContext().getViewRoot().getViewId();
   }
   public static String getRootViewComponentId() {
     return getFacesContext().getViewRoot().getId();
   }  
  
   public static FacesContext getFacesContext() {
     return FacesContext.getCurrentInstance();
   }
  /*
   * Internal method to pull out the correct local
   * message bundle
   */
  private static ResourceBundle getBundle() {
    FacesContext ctx = getFacesContext();
    UIViewRoot uiRoot = ctx.getViewRoot();
    Locale locale = uiRoot.getLocale();
    ClassLoader ldr = Thread.currentThread().getContextClassLoader();
    return ResourceBundle.getBundle(ctx.getApplication().getMessageBundle(), 
                                    locale, ldr);
  }
  public static Object getRequestAttribute(String name) {
    return getFacesContext().getExternalContext().getRequestMap().get(name);
  }  
  public static void setRequestAttribute(String name, Object value) {
    getFacesContext().getExternalContext().getRequestMap().put(name,value);
  }

  /*
       * Internal method to proxy for resource keys that don't exist
       */

  private static String getStringSafely(ResourceBundle bundle, String key, 
                                        String defaultValue) {
    String resource = null;
    try {
      resource = bundle.getString(key);
    } catch (MissingResourceException mrex) {
      if (defaultValue != null) {
        resource = defaultValue;
      } else {
        resource = NO_RESOURCE_FOUND + key;
      }
    }
    return resource;
  }
}
