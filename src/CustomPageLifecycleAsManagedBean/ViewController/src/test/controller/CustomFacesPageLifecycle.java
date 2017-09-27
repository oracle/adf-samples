/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.controller;
import com.sun.faces.RIConstants;

import java.util.HashMap;
import java.util.Map;

import oracle.adf.controller.faces.lifecycle.FacesPageLifecycle;

import oracle.binding.AttributeBinding;
public class CustomFacesPageLifecycle extends FacesPageLifecycle {
  /*
   * This Map is an anonymous subclass that overrides the get/put methods
   * of the map to return/set the value of the AttributeBinding passed in.
   */
  private Map myMap = new HashMap() {
    public Object get(Object attrBinding) {
      if (RIConstants.IMMUTABLE_MARKER.equals(attrBinding)) {
        return false;
      }
      if (!(attrBinding instanceof AttributeBinding)) {
        throw new RuntimeException("Expecting an attribute binding instead of a "+attrBinding.getClass().getName());
      }
      AttributeBinding b = (AttributeBinding)attrBinding;
      return b.getInputValue();
    }
    public Object put(Object attrBinding, Object value) {
      if (!(attrBinding instanceof AttributeBinding)) {
        throw new RuntimeException("Expecting an attribute binding instead of a "+attrBinding.getClass().getName());
      }
      AttributeBinding b = (AttributeBinding)attrBinding;
      b.setInputValue(value);
      return value;
    }
  };
  public Map getAttributeBindingWrapper() {
    return myMap;
  }
  public void setAttributeBindingWrapper(Map map) {
    /* no-op */
  }  
  public CustomFacesPageLifecycle() {
  }
  
  public String buttonClicked() {
    System.out.println("### Button Clicked ###");
    return null;
  }
}
