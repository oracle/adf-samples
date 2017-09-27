/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.RegionNavigationEvent;

import test.view.util.RegionHelper;

public class TestPage extends RegionHelper {

    private RichCommandButton sendNewMessageButton;
    private RichPanelGroupLayout panelGroupLayoutWrappingSendButton;
    private static final String CREATE_NEW_EMAIL ="/WEB-INF/flows/create-new-email.xml#create-new-email";
    public String onSendNewMessageButtonClicked() {
        setSendButtonDisabledPropertyTo(true);
        setDynamicTaskFlowId(CREATE_NEW_EMAIL);
        return null;
    }

    public void setSendNewMessageButton(RichCommandButton sendNewMessageButton) {
        this.sendNewMessageButton = sendNewMessageButton;
    }

    public RichCommandButton getSendNewMessageButton() {
        return sendNewMessageButton;
    }

    public void setPanelGroupLayoutWrappingSendButton(RichPanelGroupLayout panelGroupLayoutWrappingSendButton) {
        this.panelGroupLayoutWrappingSendButton = panelGroupLayoutWrappingSendButton;
    }

    public RichPanelGroupLayout getPanelGroupLayoutWrappingSendButton() {
        return panelGroupLayoutWrappingSendButton;
    }

    public void onRegionNavigated(RegionNavigationEvent regionNavigationEvent) {
        if (regionNavigationEvent.getNewViewId() == null ) {
              setSendButtonDisabledPropertyTo(false);
              setDynamicTaskFlowId("");
            }
    }
    private void setSendButtonDisabledPropertyTo(boolean b) {
        getSendNewMessageButton().setDisabled(b);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getPanelGroupLayoutWrappingSendButton());
    }
    public void setDynamicTaskFlowId(String id) {
        AdfFacesContext.getCurrentInstance().getViewScope().put("taskFlowId",id);
    }
    public String getDynamicTaskFlowId() {
        String tfId = (String)AdfFacesContext.getCurrentInstance().getViewScope().get("taskFlowId");
        return tfId == null ? "" : tfId;
    }
}
