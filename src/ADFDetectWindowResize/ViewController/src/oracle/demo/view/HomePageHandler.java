/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.demo.view;
/*-------------------------------------------------------------------------------------------
 * This code is distributed under the MIT License (MIT)
 *
 * Copyright (c) 2012 Duncan Mills
 *-------------------------------------------------------------------------------------------
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all 
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 *-------------------------------------------------------------------------------------------*/

import java.util.Map;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.render.ClientEvent;


/**
 * Request scoped managed bean used to hold event handlers for the main page
 * Has the state holding UIManager injected
 */
public class HomePageHandler {
    private UIManager _uiManager;
    private RichPanelFormLayout infoForm;

    public void setUiManager(UIManager _uiManager) {
        this._uiManager = _uiManager;
    }

    public UIManager getUiManager() {
        return _uiManager;
    }

    /**
     * Handler for the custom event queued by JavaScript in the client. 
     * Specifically this is registered by the af:serverListener to react to 
     * a custom event called <em>windowResizeEvent</em>
     * @param clientEvent
     */
    public void windowResizeClientEventHandler(ClientEvent clientEvent) {
        Map payload = clientEvent.getParameters();
        int width = (int)((Double)payload.get("width")).doubleValue();
        int height = (int)((Double)payload.get("height")).doubleValue();
        
        //Update the UI Manager
        getUiManager().setWindowWidth(width);
        getUiManager().setWindowHeight(height);
        
        //In this example PPR the form so that the fields on screen
        //can display the new values
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        fctx.addPartialTarget(getInfoForm());
    }

    public void setInfoForm(RichPanelFormLayout infoForm) {
        this.infoForm = infoForm;
    }

    public RichPanelFormLayout getInfoForm() {
        return infoForm;
    }
}
