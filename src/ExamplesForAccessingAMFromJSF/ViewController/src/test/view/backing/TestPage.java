/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import java.util.Map;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
/*
 * Example illustrating the correct way to programmatically invoke
 * custom methods exposed on the client interface of the Application Module,
 * View Object, and View Row. The backing bean methods find the appropriate
 * ADF action binding by name from the current binding container, optionally
 * set parameters programmatically if necessary (assuming the declaratively-
 * configured parameter EL expressions are not sufficient for your needs),
 * invoke the action binding, and optionally process the result of the
 * method invocation in some way. OperationBinding is the more generic
 * interface for the ADF action binding whose implementation class
 * is JUCtrlActionBinding.
 * 
 * 
 * ============
 * !! NOTICE !!
 * ============
 *
 * There is ## NO ## use of Configuration.createRootApplicationModule() here!
 * Using that API to access the application module would check out an 
 * ADDITIONAL application module instance from the pool instead of
 * referencing the existing application module that the the ADF framework
 * has automatically checked out of the AM pool for your page during the
 * request the first time its referenced by any page definition bindings.
 */
public class TestPage {
    public TestPage() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    /*
     * This example illustrates does not specifically set any 
     * method action parameter values, so the parameter values
     * are picked up from the EL expressions configured on the
     * method action binding arguments in the page definition 
     * metadata.
     */
    public String onClickApplicationModuleMethodWithResult() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("customApplicationModuleMethodWithResult");
        Object result = operationBinding.execute();
        System.out.println("## Result = " + result);
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    /*
     * This example illustrates how to set the method action parameters
     * programmatically. This overrides any declarative-supplied values
     * configured in the EL expressions on the method action binding arguments
     * in the page definition metadata.
     */
    public String onClickViewObjectMethodWithResult() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("customViewObjectMethodWithResult");
        // Set the parameters
        Map paramsMap = operationBinding.getParamsMap();
        paramsMap.put("stringArg","customStringValue");
        paramsMap.put("intArg", 101 );
        Object result = operationBinding.execute();
        System.out.println("## Result = " + result);
        if (!operationBinding.getErrors().isEmpty()) {
            return null; // Stay on the same page if there is an error
        }
        return null; // Here, you can return an outcome to navigate on success
    }
    
    /*
     * This example illustrates how to set some of the method action parameters
     * programmatically. This overrides any declarative-supplied values
     * configured in the EL expressions on the method action binding arguments
     * in the page definition metadata.
     */
    public String onClickViewRowMethodWithResult() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("customViewRowMethodWithResult");
        // Set only one the parameters, the other defaults from EL in pageDef
        Map paramsMap = operationBinding.getParamsMap();
        paramsMap.put("stringArg","customStringValue");
        // Not setting the "intArg" param explicitly here!
        Object result = operationBinding.execute();
        System.out.println("## Result = " + result);
        if (!operationBinding.getErrors().isEmpty()) {
            return null; // Stay on the same page if there is an error
        }
        return null; // Here, you can return an outcome to navigate on success
    }



}
