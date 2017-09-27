/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.demo.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class DemoPageEventHandler {
    public DemoPageEventHandler() {
    }

    public String raiseErrorMessage() {
        queueMessage(FacesMessage.SEVERITY_ERROR,"An Error Message","Error messages should be in a popup");
        return null;
    }
    
    public String raiseInformationalMessage() {
        queueMessage(FacesMessage.SEVERITY_INFO,"An Informational Message","Info messages should be displayed inline");
        return null;
    }
    
    public String raiseWarningMessage() {
        queueMessage(FacesMessage.SEVERITY_WARN,"A Warning Message","Warning messages should be displayed inline");
        return null;
    }

    public String raiseFatalMessage() {
        queueMessage(FacesMessage.SEVERITY_FATAL,"A Fatal Error Message","Fatal Errors should be displayed in a popup");
        return null;
    }

    public String raiseSeveralMessages() {
        queueMessage(FacesMessage.SEVERITY_INFO,"An Informational Message","Blaaa Blaaa");
        queueMessage(FacesMessage.SEVERITY_INFO,"Another Informational Message","More Blaaa Blaaa");
        queueMessage(FacesMessage.SEVERITY_ERROR,"An Error Message","This error message should cause a popup to raise");
        return null;
    }

    
    
    private void queueMessage(javax.faces.application.FacesMessage.Severity sev, String msg, String msgDetail){
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage m = new FacesMessage(sev,msg,msgDetail);
        ctx.addMessage(null, m);
    }
    
}
