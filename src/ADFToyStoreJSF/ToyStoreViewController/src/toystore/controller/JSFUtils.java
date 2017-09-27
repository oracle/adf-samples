/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package toystore.controller;
import java.io.IOException;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
public class JSFUtils {
  public static String getRootViewId() {
    return getFacesContext().getViewRoot().getViewId();
  }
  public static String getRootViewComponentId() {
    return getFacesContext().getViewRoot().getId();
  }
  
  public static FacesContext getFacesContext() {
    return FacesContext.getCurrentInstance();
  }
  public static Object EL(String expr) {
    FacesContext ctx = getFacesContext();
    return ctx.getApplication().createValueBinding(expr).getValue(ctx);
  }
  public static String ELAsString(String expr) {
    Object obj = EL(expr);
    if (obj != null) {
      if (obj instanceof String) {
        return (String)obj;
      }
      else {
        return obj.toString();
      }
    }
    return null;
  }
  public static void redirect(String url) throws IOException {
    getFacesContext().getExternalContext().redirect(url);
  }
  public static Object getManagedBean(String beanName) {
    return EL("#{" + beanName + "}");
  }
  public static String bindingValue(String bindingName) {
    return ELString("#{bindings." + bindingName + "}");
  }
  public static String ELString(String expr) {
    Object o = EL(expr);
    return o instanceof String ? (String)o : o.toString();
  }
  public static void addMessage(String message) {
    addMessage(null, message);
  }
  public static void addMessage(String componentId, String message) {
    getFacesContext().addMessage(componentId, new FacesMessage(message));
  }
  private static Map getRequestMap() {
    return getFacesContext().getExternalContext().getRequestMap();
  }
  public static String getRequestAttribute(String attrName) {
    return (String)getRequestMap().get(attrName);
  }
  public static void setRequestAttribute(String attrName, String value) {
    getRequestMap().put(attrName, value);
  }
  public static void setRequestAttribute(String attrName) {
    getRequestMap().put(attrName, "");
  }
  public static void addFacesErrorMessage(String attrName, String msg) {
    // TODO: Need a way to associate attribute specific messages
    //       with the UIComponent's Id! For now, just using the view id.
    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,attrName,msg);
    System.out.println(fm.getDetail());
    getFacesContext().addMessage(getRootViewComponentId(),fm);
  }
  public static void addFacesErrorMessage(String msg) {
    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,"");
    getFacesContext().addMessage(getRootViewComponentId(),fm);
  }
  
}
