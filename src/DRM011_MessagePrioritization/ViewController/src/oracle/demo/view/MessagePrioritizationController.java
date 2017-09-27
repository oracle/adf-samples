/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.demo.view;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Simple bean that contains a method used to control the display state of messages - inline or popup
 */
public class MessagePrioritizationController {
    
    
    public boolean isInlineMessage(){
        boolean lowPriorityMessage = true;
        Iterator<FacesMessage> iter = FacesContext.getCurrentInstance().getMessages();
        while (iter.hasNext()){
            FacesMessage msg = iter.next();
            if (msg.getSeverity() == FacesMessage.SEVERITY_ERROR || msg.getSeverity() == FacesMessage.SEVERITY_FATAL){
                lowPriorityMessage = false;
                break;
            }
        }      
        return lowPriorityMessage;
    }

}
