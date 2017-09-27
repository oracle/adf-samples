/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jbo.DMLException;
import oracle.jbo.JboException;
import oracle.jbo.common.ampool.ApplicationPoolException;
import oracle.jbo.html.struts11.BC4JActionMapping;
import oracle.jbo.html.struts11.BC4JRequestProcessor;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyRequestProcessor extends BC4JRequestProcessor {
  private static final String APPPOOLEXCEPTION = "__AppPoolExceptionOccurred";
  protected boolean initPageFromPath(BC4JActionMapping mapping, HttpServletRequest request, HttpServletResponse response)
  {
    boolean b = false;
    try {
      b = super.initPageFromPath(mapping, request, response);
    }
    catch (ApplicationPoolException apEx) 
    {
      request.setAttribute(APPPOOLEXCEPTION,apEx);
    }    
    return b;
  }

  protected ActionForward processActionPerform(HttpServletRequest request, HttpServletResponse response, Action action, ActionForm form, ActionMapping mapping) throws IOException, ServletException
  {
    if (request.getAttribute(APPPOOLEXCEPTION) != null) {
      Exception ex = (Exception)request.getAttribute(APPPOOLEXCEPTION);
      if (ex instanceof ApplicationPoolException &&
          isOrCausedByExceptionClass((ApplicationPoolException)ex,DMLException.class)) {
        ex = new DatabaseIsDownException();
      }
      return super.processException(request,
                                    response,
                                    ex,
                                    null,
                                    mapping);
    }
    else {
      return super.processActionPerform(request, response, action, form, mapping);
    }
  }
  public static boolean isOrCausedByExceptionClass(JboException j,
    Class excepClass) {
    boolean ret = false;
    if (j.getClass().equals(excepClass)) {
      return true;
    }
    Object[] details = j.getDetails();
    for (int z = 0, count = details.length; !ret && (z < count); z++) {
      Exception cur = (Exception) details[z];
      if (cur.getClass().equals(excepClass)) {
        return true;
      }
      if (cur instanceof JboException) {
        ret = isOrCausedByExceptionClass((JboException) cur, excepClass);
      }
    }
    return ret;
  }  
}
