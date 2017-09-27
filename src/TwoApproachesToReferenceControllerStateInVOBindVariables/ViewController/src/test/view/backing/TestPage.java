/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import test.controller.util.EL;

public class TestPage {
    public TestPage() {
    }

    public void onFavoriteColorChanged(ValueChangeEvent valueChangeEvent) {
      /* The valueChangeEvent fires before the updateModel phase of the
       * lifecycle in which the binding would be updated to have the new
       * value. This call forces the binding to be updated with the new value
       * immediately
       */
      EL.setValueChangeEventComponentToNewValue(valueChangeEvent);
      /*
       * Invoke the method action binding to set the new favorite color
       * in the application module via the custom client-exposed method
       * of the same name.
       */
      invokeOperationBinding("setSessionFavoriteColor");
        /*
         * Force the "ExampleVO2" view instance to be requeried.
         */
      invokeOperationBinding("Execute1");
    }

    private void invokeOperationBinding(String op) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding(op).execute();
    }
}
