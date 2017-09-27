/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
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
}
