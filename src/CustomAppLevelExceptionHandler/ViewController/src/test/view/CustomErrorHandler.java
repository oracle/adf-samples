/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCErrorHandlerImpl;

import oracle.jbo.JboWarning;

import test.model.InformationalMessage;

public class CustomErrorHandler extends DCErrorHandlerImpl {
    public CustomErrorHandler() {
        super(true);
    }
    private static final String REPORTED_ALREADY = "ReportedAlready";
    private static final String INFO_PROPERTY = "INFO";
    
    private boolean isInformationalMessage(JboWarning w) {
        Object[] params = w.getErrorParameters();
        return params != null && params.length > 0 && InformationalMessage.INFO_PROPERTY.equals(params[0]);
    }
    private boolean isAlreadyReported(JboWarning w) {
        return Boolean.TRUE.equals(w.getProperty(REPORTED_ALREADY));
    }
    private void setAlreadyReportedFlag(JboWarning w) {
        w.setProperty(REPORTED_ALREADY,Boolean.TRUE);
    }
    @Override
    public String getDisplayMessage(BindingContext ctx, Exception th) {
        if (th instanceof JboWarning) {
            JboWarning w = (JboWarning)th;
            if (isInformationalMessage(w)) {
                if (!isAlreadyReported(w)) {
                  setAlreadyReportedFlag(w);
                  addInformationMessage(w);
                }
              return null;
            }
        }
        return super.getDisplayMessage(ctx, th);
    }

    @Override
    public void reportException(DCBindingContainer formBnd, Exception ex) {
        super.reportException(formBnd, ex);
    }
    /**
     * Local method and not used outside
     * @param warning
     * @return
     */
    private static String getMessage(JboWarning warning)
    {
      warning.setAppendCodes(false);
      String message = warning.getLocalizedMessage(FacesContext.getCurrentInstance().getViewRoot().getLocale());
      if (message == null || message.length() == 0)
      {
        message = warning.getMessage();
      }
      return message;
    }

    /**
     * Add the information message
     * @param severity
     * @param sniWarning
     */
    private static void addMessage(FacesMessage.Severity severity, 
                                    JboWarning sniWarning)
    {
      FacesContext ctx = FacesContext.getCurrentInstance();
      String summary = getMessage(sniWarning);
      FacesMessage fm = new FacesMessage(severity, summary, summary);
      ctx.addMessage(null, fm);
    }

    /**
     * Add JSF Information Message.
     * @param argInformationMessage - information message object
     */
    private static void addInformationMessage(JboWarning argInformationMessage)
    {
      addMessage(FacesMessage.SEVERITY_INFO, argInformationMessage);
    }

}
