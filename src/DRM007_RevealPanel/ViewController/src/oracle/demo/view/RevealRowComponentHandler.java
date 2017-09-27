/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.demo.view;
/*******************************************************************************
 *
 * Copyright (c) 2013 Oracle Corporation.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MIT License
 *
 * Contributors:
 *   Duncan Mills
 *
 *
 *******************************************************************************/ 

import java.util.Map;

import javax.faces.component.UIComponent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.layout.RichPanelGridLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.render.ClientEvent;

public class RevealRowComponentHandler {
    
    private RevealPanelManager _manager;

    public void setManager(RevealPanelManager _manager) {
        this._manager = _manager;
    }

    public RevealPanelManager getManager() {
        return _manager;
    }
    
    public void handlePanelToggle(ClientEvent clientEvent) {
        Map payload = clientEvent.getParameters();
        int toggleTopic = ((Double)payload.get("panelId")).intValue();
        getManager().setToggleTarget(toggleTopic);
        getManager().toggleState();
        AdfFacesContext context = AdfFacesContext.getCurrentInstance();
        context.addPartialTarget(findRefreshGrid(clientEvent.getComponent()));
    }
    
    private UIComponent findRefreshGrid (UIComponent declComp){
        UIComponent c = declComp.getParent();
        while (c != null && !(c instanceof RichPanelGridLayout)){
            c = c.getParent();
        }
        return c;
    }
}
