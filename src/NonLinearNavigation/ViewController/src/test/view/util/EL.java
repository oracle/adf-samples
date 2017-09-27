/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.util;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 * Helper class for Using EL in JSF 1.2 (JDeveloper 11g)
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
        return fc.getApplication().evaluateExpressionGet(fc,expr,Object.class);
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
        ELContext elc = fc.getELContext();
        ExpressionFactory ef = fc.getApplication().getExpressionFactory();
        ValueExpression ve = ef.createValueExpression(elc, expr, Object.class);
        ve.setValue(elc, value);
    }

    private static boolean isELExpr(Object o) {
        if (o instanceof String) {
            String str = (String)o;
            str.trim();
            return str.startsWith("#{") && str.endsWith("}");
        }
        return false;
    }

    public static Object invokeMethod(String expr, Class[] paramTypes, 
                                      Object[] params) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext elc = fc.getELContext();
        ExpressionFactory ef = fc.getApplication().getExpressionFactory();
        MethodExpression me = 
            ef.createMethodExpression(elc, expr, Object.class, paramTypes);
        return me.invoke(elc, params);
    }

    public static Object invokeMethod(String expr, Class paramType, 
                                      Object param) {
        return invokeMethod(expr, new Class[] { paramType }, 
                            new Object[] { param });
    }
    public static void setValueChangeEventComponentToNewValue(ValueChangeEvent vce) {
        vce.getComponent()
           .getValueExpression("value")
           .setValue(FacesContext.getCurrentInstance().getELContext(),vce.getNewValue());
        
    }
    
}
