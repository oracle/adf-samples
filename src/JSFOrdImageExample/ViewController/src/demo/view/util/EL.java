/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.view.util;

import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
/**
 * Helper class for Using EL in JSF 1.1 (JDeveloper 10.1.3)
 */
public class EL {
  public static boolean test(String booleanExpr) {
    return Boolean.TRUE.equals(get(booleanExpr));
  }
  public static String getAsStringWithDefault(String expr, 
                                              String defaultExpr) {
    return (String)getWithDefault(expr, defaultExpr);
  }
  public static String getAsString(String expr) {
    return (String)get(expr);
  }
  public static Integer getAsInteger(String expr) {
      return (Integer)get(expr);
  }
  public static Object get(String expr) {
    FacesContext fc = FacesContext.getCurrentInstance();
    ValueBinding vb = fc.getApplication().createValueBinding(expr);
    return vb.getValue(fc);
  }
  public static Object getWithDefault(String expr, String defaultExpr) {
    Object exprVal = get(expr);
    return exprVal != null ? exprVal : get(defaultExpr);
  }
  public static void set(String expr, String value) {
    Object valToSet = value;
    if (isELExpr(value)) {
      valToSet = get(value);
    }
    set(expr, valToSet);
  }
  public static void set(String expr, Object value) {
    FacesContext fc = FacesContext.getCurrentInstance();
    ValueBinding vb = fc.getApplication().createValueBinding(expr);
    vb.setValue(fc, value);
  }
  private static boolean isELExpr(Object o) {
    if (o instanceof String) {
      String str = (String)o;
      str.trim();
      return str.startsWith("#{") && str.endsWith("}");
    }
    return false;
  }
  public static Object invokeMethod(String expr, Class[] paramTypes, Object[] params) {
      FacesContext fc = FacesContext.getCurrentInstance();
      MethodBinding mb = fc.getApplication().createMethodBinding(expr,paramTypes);
      return mb.invoke(fc,params);
  }
  public static Object invokeMethod(String expr, Class paramType, Object param) {
      return invokeMethod(expr,new Class[]{paramType},new Object[]{param});
  }  
}
