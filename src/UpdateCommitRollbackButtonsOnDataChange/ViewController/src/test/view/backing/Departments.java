/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
 package test.view.backing;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.component.core.nav.CoreCommandButton;
import oracle.adf.view.faces.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class Departments {
    private CoreCommandButton commitButton;
    private CoreCommandButton rollbackButton;
    private BindingContainer bindings;

    public Departments() {
    }

    public void onDeptnoDnameOrLocValueChange(ValueChangeEvent valueChangeEvent) {
        pprCommitAndRollbackButtonIfNeeded();
    }

    private void pprCommitAndRollbackButtonIfNeeded() {
        AdfFacesContext afc = AdfFacesContext.getCurrentInstance();
        if (getCommitButton().isDisabled()) {
            afc.addPartialTarget(getCommitButton());
        }
        if (getRollbackButton().isDisabled()) {
            afc.addPartialTarget(getRollbackButton());
        }
    }

    public void setCommitButton(CoreCommandButton commitButton) {
        this.commitButton = commitButton;
    }

    public CoreCommandButton getCommitButton() {
        return commitButton;
    }

    public void setRollbackButton(CoreCommandButton rollbackButton) {
        this.rollbackButton = rollbackButton;
    }

    public CoreCommandButton getRollbackButton() {
        return rollbackButton;
    }

    public BindingContainer getBindings() {
        return this.bindings;
    }

    public void setBindings(BindingContainer bindings) {
        this.bindings = bindings;
    }
}
