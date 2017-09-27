/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view.backing;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.component.core.input.CoreInputText;

import oracle.adf.view.faces.context.AdfFacesContext;

import view.util.EL;

public class TestPage {
    private CoreInputText valueAInputField;
    private CoreInputText valueBInputField;

    public TestPage() {
    }

    public void onRowTypeChanged(ValueChangeEvent valueChangeEvent) {
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("RowTypeChanged","x");
    }

    public void setValueAInputField(CoreInputText valueAInputField) {
        this.valueAInputField = valueAInputField;
    }

    public CoreInputText getValueAInputField() {
        return valueAInputField;
    }

    public void setValueBInputField(CoreInputText valueBInputField) {
        this.valueBInputField = valueBInputField;
    }

    public CoreInputText getValueBInputField() {
        return valueBInputField;
    }
}
