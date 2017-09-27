/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import javax.faces.context.FacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class SetUserMapValue {
    private BindingContainer bindings;

    public SetUserMapValue() {
    }

    public BindingContainer getBindings() {
        if (this.bindings == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            this.bindings =
                    (BindingContainer)fc.getApplication().evaluateExpressionGet(fc, "#{bindings}",
                        BindingContainer.class);
        }
        return this.bindings;
    }

    public String onClickSetUserMapButton() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("setUserMapValue");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        operationBinding = bindings.getOperationBinding("Execute");
        result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }        
        return null;
    }
}
