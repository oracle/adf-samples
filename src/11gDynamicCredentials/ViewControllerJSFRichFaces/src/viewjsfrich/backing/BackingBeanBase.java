/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package viewjsfrich.backing;
import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class BackingBeanBase {
    public String onLogoutButtonPressed() throws IOException {
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse response = (HttpServletResponse)ectx.getResponse();
        HttpSession session = (HttpSession)ectx.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        FacesContext.getCurrentInstance().responseComplete();
        response.sendRedirect("Login.jspx");
        return null;
    }
}
