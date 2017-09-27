/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.demo.view.util;

import oracle.adf.model.BindingContext;

import oracle.binding.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.model.binding.DCIteratorBinding;

/**
 * Copyright (c) 2012 Oracle Corporation
 * Author: Duncan Mills
 * (very) Simple utility functions for dealing with day-to-day interactions with
 * ADF Bindings
 */
public class BindingUtils {
    
    /**
     * Gets the in-scope binding container cast to the more strongly typed
     * DCBindingContainer
     * @return BindingContainer
     */
    public static final DCBindingContainer getDCBindingContainer(){
        return (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    
    
    public static final DCIteratorBinding findIteratorBindingByName(String iteratorName){
        DCBindingContainer bindingContainer = getDCBindingContainer();
        return findIteratorBindingByName(bindingContainer,iteratorName);
    }
    
    
    public static final DCIteratorBinding findIteratorBindingByName(DCBindingContainer bindings, String iteratorName){
        return bindings.findIteratorBinding(iteratorName);
    }
    
    public static final OperationBinding findOperationBinding(String methodName){
        DCBindingContainer bindingContainer = getDCBindingContainer();
        return findOperationBinding(bindingContainer, methodName);
    }
    
    public static final OperationBinding findOperationBinding(DCBindingContainer bindings,String methodName){
        return bindings.getOperationBinding(methodName);
    }    
    
    
    
}
