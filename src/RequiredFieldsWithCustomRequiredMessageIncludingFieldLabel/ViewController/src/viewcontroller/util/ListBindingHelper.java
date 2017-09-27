/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package viewcontroller.util;


import com.sun.faces.RIConstants;

import java.util.HashMap;
import java.util.Map;

import oracle.jbo.uicli.binding.JUCtrlListBinding;

/**
 * This class can be registered as an application-level managed bean
 * and used to workaround the bug (#5930745) that if a list binding has a null
 * value and also has a registered mandatory attribute exception,
 * which it will after the user submits the form, then the ADF list binding
 * incorrectly returns the value null instead of the numerical 
 * index of the null-entry in the list.
 * 
 * Assuming you've registerd this bean with a name "Helper" in 
 * faces-config.xml, then in a JSPX page that uses a <af:selectOneChoice>
 * whose value attribute would normally have the EL expression:
 * 
 *   value="#{bindings.SomeListBindingName.inputValue}"
 *   
 * instead, replace this with the EL expression:
 * 
 *   value="#{Helper.listBindings.SomeListBindingName}"
 *   
 * This class contains a Map-valued property named "listBindings"
 * and that Map overrides the get() and set() methods of the
 * java.util.HashMap object to accept the name of the list binding
 * as an argument, look it up in the current binding container,
 * call getInputValue() on it, and if that method returns null
 * and the list binding's hasNullValue() method returns true indicating
 * that it allows null to be one of the legal values in the list,
 * then this method returns the integer value of the null value entry
 * instead of the "raw" null value.
 * 
 */
public class ListBindingHelper {
   private JUCtrlListBinding findListBinding(String name) {
    Object binding = ELHelper.get("#{bindings."+name+"}");
    if (binding != null && binding instanceof JUCtrlListBinding) {
        return (JUCtrlListBinding)binding;
    }
    else {
      throw new RuntimeException(name+" is not the name of a list binding.");
    }      
   }
   private Map listBindings = new HashMap() {
       public Object get(Object key) {
           if (RIConstants.IMMUTABLE_MARKER.equals(key)) {
             return null;
           }
           JUCtrlListBinding listBinding = findListBinding((String)key);
           Object value = listBinding.getInputValue();
           if (value == null && listBinding.hasNullValue()) {
             return new Integer(listBinding.getNullValueIndex());
           }
           return value;

       }
       public Object put(Object key, Object value) {
           findListBinding((String)key).setInputValue(value);
           return null;
       }       
   };
   
   public Map getListBindings() {
       return listBindings;
   }
    public void setListBindings(Map m) {

    }
}
