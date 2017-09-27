/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package viewcontroller.backing;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class CreateNewEmployeePageUsingAMMethodViaEntity {
    private BindingContainer bindings;

    public CreateNewEmployeePageUsingAMMethodViaEntity() {
    }

    public BindingContainer getBindings() {
        return this.bindings;
    }

    public void setBindings(BindingContainer bindings) {
        this.bindings = bindings;
    }
    /*
     * Workaround for bug# 5930784 where in some circumstances
     * the ADF/JSF controller navigates even though there are
     * exceptions that have been reported when the action binding
     * is executed. This is the standard ADF binding code that
     * JDeveloper generated when double-clicking on a declaratively
     * bound button in the page.
     */
    public String onCreateNewEmployee() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = 
            bindings.getOperationBinding("createNewEmployee");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return "back";
    }
}
