/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package viewcontrollerjsf.backing;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;


public class Main {
    public Main() {
    }

    public String onLogout() {
        FacesContext    fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        HttpSession session = (HttpSession)ectx.getSession(false);
        if (session != null) {
          session.invalidate();
        }
        try {
            ectx.redirect("login.jsp");
            fctx.responseComplete();            
        } catch (IOException e) {
            // TODO
        }
        return null;
    }
}
