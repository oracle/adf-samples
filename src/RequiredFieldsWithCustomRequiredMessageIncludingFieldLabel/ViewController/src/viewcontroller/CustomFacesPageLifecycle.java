/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package viewcontroller;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import java.text.MessageFormat;

import oracle.jbo.CSMessageBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import oracle.adf.controller.faces.lifecycle.FacesPageLifecycle;
import oracle.adf.controller.v2.context.LifecycleContext;
import oracle.adf.controller.v2.context.PageLifecycleContext;
import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlAttrsBinding;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.ControlBinding;


import oracle.binding.DataControl;

import oracle.jbo.AttrValException;
import oracle.jbo.AttributeDef;
import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.ValidationException;
import oracle.jbo.domain.Number;
import oracle.jbo.uicli.binding.JUCtrlAttrsBinding;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

public class CustomFacesPageLifecycle extends FacesPageLifecycle {
    public void prepareModel(LifecycleContext ctx) {
        /*
           * Force a one-time check to make sure we can find the data control.
           * This allows us to more proactively warn the user if they have
           * not setup their datasource correctly.
           */

        if (!(ctx.getBindingContext().getErrorHandler() instanceof 
              CustomErrorHandler)) {
            ctx.getBindingContext().setErrorHandler(new CustomErrorHandler(true));
        }
        super.prepareModel(ctx);
    }

    public void reportErrors(PageLifecycleContext ctx) {
        DCBindingContainer bc = (DCBindingContainer)ctx.getBindingContainer();
        if (bc != null) {
            List<Exception> exceptions = bc.getExceptionsList();
            if (exceptions != null) {
                Locale userLocale = 
                    ctx.getBindingContext().getLocaleContext().getLocale();
                /*
                   * Iterate over the top-level exceptions in the exceptions list
                   * and call addError() to add each one to the Faces errors list
                   * in an appropriate way.
                   */
                for (Exception exception: exceptions) {
                    translateExceptionToFacesErrors(exception, userLocale, bc);
                }
            }
        }
    }

    /**
     * Translates exceptions to JSF FacesError objects for reporting to JSF.
     * 
     * @param ex exception to translate
     * @param locale current user's preferred locale
     * @param bc ADF binding container
     * @throws KnowErrorStopException
     */
    protected void translateExceptionToFacesErrors(Exception ex, Locale locale, 
                                                   BindingContainer bc) {
        List globalErrors = new ArrayList();
        Map attributeErrors = new HashMap();
        processException(ex, globalErrors, attributeErrors, null, locale);
        int numGlob = globalErrors.size();
        int numAttr = attributeErrors.size();
        if (numGlob > 0) {
            for (int z = 0; z < numGlob; z++) {
                String msg = (String)globalErrors.get(z);
                if (msg != null) {
                    JSFUtils.addFacesErrorMessage(msg);
                }
            }
        }
        if (numAttr > 0) {
            Iterator i = attributeErrors.keySet().iterator();
            while (i.hasNext()) {
                String attrNameKey = (String)i.next();
                /*
                   * Only add the error to show to the user if it was related
                   * to a field they can see on the screen. We accomplish this
                   * by checking whether there is a control binding in the current
                   * binding container by the same name as the attribute with
                   * the related exception that was reported.
                   */
                ControlBinding cb = 
                    ADFUtils.findControlBinding(bc, attrNameKey);
                if (cb != null) {
                    String componentClientId = null;
                    if (cb instanceof FacesCtrlAttrsBinding) {
                      FacesCtrlAttrsBinding facesControlBinding = (FacesCtrlAttrsBinding)cb;
                      componentClientId = facesControlBinding.getComponentClientId();
                    }
                    String msg = (String)attributeErrors.get(attrNameKey);
                    if (cb instanceof JUCtrlAttrsBinding) {
                        attrNameKey = ((JUCtrlAttrsBinding)cb).getLabel();
                    }
                    JSFUtils.addFacesErrorMessage(componentClientId, 
                                                  attrNameKey, msg);
                }
            }
        }
    }

    /**
     * Populate the list of global errors and attribute errors by
     * processing the exception passed in, and recursively processing
     * the detail exceptions wrapped inside of any oracle.jbo.JboException
     * instances.
     *
     * If the error is an attribute-level validation error, we can tell
     * because it should be an instanceof oracle.jbo.AttrValException
     * For each attribute-level error, we retrieve the name of the attribute
     * in error by calling an appropriate getter method on the exception
     * object which exposes this information to us. Since attribute-level
     * errors could be wrapping other more specific attribute-level errors
     * that were the real cause (especially due to Bundled Exception Mode).
     * We continue to recurse the detail exceptions and we only consider
     * relevant to report the exception that is the most deeply nested, since
     * it will have the most specific error message for the user. If multiple
     * exceptions are reported for the same attribute, we simplify the error
     * reporting by only reporting the first one and ignoring the others.
     * An example of this might be that the user has provided a key value
     * that is a duplicate of an existing value, but also since the attribute
     * set failed due to that reason, a subsequent check for mandatory attribute
     * ALSO raised an error about the attribute's still being null.
     *
     * If it's not an attribute-level error, we consider it a global error
     * and report each one.
     *
     * @param ex the exception to be analyzed
     * @param globalErrs list of global errors to populate
     * @param attrErrs map of attrib-level errors to populate, keyed by attr name
     * @param attrName attribute name of wrapping exception (if any)
     * @param locale the user's preferred locale as determined by the browser
     */
    private void processException(Exception ex, List globalErrs, Map attrErrs, 
                                  String attrName, Locale locale) {
        if (!(ex instanceof JboException)) {
            String msg = ex.getLocalizedMessage();
            if (msg == null) {
                msg = firstLineOfStackTrace(ex, true);
            }
            globalErrs.add(msg);
            /*
             * If this was an unexpected error, print out stack trace
             */
            reportUnexpectedException(ex);
            return;
        }
        String localizedErrorMessage = null;
        if (ex instanceof AttrValException) {
            AttrValException ave = (AttrValException)ex;
            attrName = ave.getAttrName();
            Object obj = attrErrs.get(attrName);
            /*
               * If we haven't already recorded an error for this attribute
               * and if it's a leaf detail, then log it as the first error for
               * this attribute. If there are details, then we'll recurse
               * into the details below. But, in the meantime we've recorded
               * What attribute had the validation error in the attrName
               */
            Object[] details = ave.getDetails();
            if (obj != null) {
                /*
                   * We've already logged an attribute-validation error for this
                   * attribute, so ignore subsequent attribute-level errors
                   * for the same attribute. Note that this is not ignoring
                   * NESTED errors of an attribute-level exception, just the
                   * second and subsequent PEER errors of the first attribute-level
                   * error. This means the user might receive errors on several
                   * different attributes, but for each attribute we're choosing
                   * to tell them about just the first problem to fix.
                   */
                return;
            } else {
                /*
                   * If there aren't any further, nested details, then log first error
                   */
                if ((details == null) || (details.length == 0)) {
                    /*
                     * If the AttrValException is for a mandator attribute error...
                     */
                    if (ave.getErrorCode().equals(CSMessageBundle.EXC_VAL_ATTR_MANDATORY)) {
                        AttributeBinding binding = 
                            ADFUtils.findControlBinding(ave.getAttrName());
                        if (binding != null) {
                            /*
                             * The base error message for the mandatory attribute
                             * validation is registered as a custom message
                             * bundle on the ADFBC model project (in the
                             * "Business Components" options panel of the
                             * project properties. It has a message placeholder
                             * "{0}" in the string that MessageFormat will
                             * fill in with the UI label that we retrieve from
                             * the control binding of the same name as the
                             * attribute that threw the exception.
                             */
                            localizedErrorMessage = 
                                    MessageFormat.format(ave.getLocalizedBaseMessage(locale), 
                                                         null,null,binding.getLabel());
                        }
                    } else {
                        localizedErrorMessage = 
                                ave.getLocalizedMessage(locale);
                    }
                }
            }
        }
        JboException jboex = (JboException)ex;
        /*
           * It is a JboException so recurse into the exception tree
           */
        Object[] details = jboex.getDetails();
        /*
           * We only want to report Errors for the "leaf" exceptions
           * So if there are details, then don't add an errors to the lists
           */
        if ((details != null) && (details.length > 0)) {
            for (int j = 0, count = details.length; j < count; j++) {
                processException((Exception)details[j], globalErrs, attrErrs, 
                                 attrName, locale);
            }
        } else {
            /*
               * Add a new Error to the collection
               */
            if (attrName == null) {
                String errorCode = jboex.getErrorCode();
                globalErrs.add(jboex.getLocalizedMessage(locale));
            } else {
                attrErrs.put(attrName, 
                             localizedErrorMessage != null ? localizedErrorMessage : 
                             jboex.getLocalizedMessage(locale));
            }
            if (!(jboex instanceof ValidationException)) {
                reportUnexpectedException(jboex);
            }
        }
    }

    /**
     * Prints the stack trace for an unexpected exception to standard out.
     *
     * @param ex The unexpected exception to report.
     */
    protected void reportUnexpectedException(Exception ex) {
        ex.printStackTrace();
    }

    /**
     * Picks off the exception name and the first line of information
     * from the stack trace about where the exception occurred and
     * returns that as a single string.
     */
    private String firstLineOfStackTrace(Exception ex, boolean logToError) {
        if (logToError) {
            ex.printStackTrace(System.err);
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        LineNumberReader lnr = 
            new LineNumberReader(new StringReader(sw.toString()));
        try {
            String lineOne = lnr.readLine();
            String lineTwo = lnr.readLine();
            return lineOne + " " + lineTwo;
        } catch (IOException e) {
            return null;
        }
    }

    public void validateModelUpdates(LifecycleContext lfContext) {
        forceValidationOfNewRowWithAllNullMandatoryAttributes(lfContext);
        super.validateModelUpdates(lfContext);
    }

    /*
     * If there are any attributes bindings for updateable, mandatory attributes
     * and if all of such bindings have a null value, then force the new row
     * to transition to the new status and be validated even though the end-user
     * hasn't entered any data yet.
     */
    private void forceValidationOfNewRowWithAllNullMandatoryAttributes(LifecycleContext lfContext) {
        boolean someMandatoryUpdateableAttributesExists = false;
        boolean allMandatoryUpdateableAttributesWereNull = true;
        JUCtrlAttrsBinding lastMandatoryAttrBindingThatWasNull = null;
        DCBindingContainer bc = (DCBindingContainer)lfContext.getBindingContainer();
        List<JUCtrlAttrsBinding> attrBindings = bc.getAttributeBindings();
        for (JUCtrlAttrsBinding ab: attrBindings) {
            if (ab.isUpdateable() && ab.isMandatory()) {
                someMandatoryUpdateableAttributesExists = true;
                if (!isNullOrEmpty(ab)) {
                    allMandatoryUpdateableAttributesWereNull = false;
                    break;
                } else {
                    lastMandatoryAttrBindingThatWasNull = ab;
                }
            }
        }
        if (someMandatoryUpdateableAttributesExists && 
            allMandatoryUpdateableAttributesWereNull && 
            lastMandatoryAttrBindingThatWasNull != null) {
            dirtyNullBindingBySettingItNullAgain(lastMandatoryAttrBindingThatWasNull);
        }
    }
    private void dirtyNullBindingBySettingItNullAgain(JUCtrlAttrsBinding ab) {
        Object nullValueToSet = null;
        /*
         * Dirty the last mandatory attribuate binding we processed
         * to force the row to be validated.
         */
        if (ab instanceof JUCtrlListBinding) {
            JUCtrlListBinding lb = (JUCtrlListBinding)ab;
            if (lb.hasNullValue()) {
                nullValueToSet = lb.getNullValueString();
            }
        }
        ab.processNewInputValue(nullValueToSet);            
        ab.setInputValue(ab,0,nullValueToSet);
        
    }
    private boolean isNullOrEmpty(JUCtrlAttrsBinding ab) {
        if (ab instanceof JUCtrlListBinding) {
            JUCtrlListBinding lb = (JUCtrlListBinding)ab;
            if (lb.hasNullValue()) {
              return lb.getSelectedIndex() == lb.getNullValueIndex();
            } else {
              return false;
            }
        }
        else {
            return isNullOrEmpty(ab.getInputValue());
        }
    }
    private boolean isNullOrEmpty(Object obj) {
        if (obj == null) return true;
        if (obj instanceof String) {
            if (((String)obj).length() == 0) {
                return true;
            }
        }
        return false;
    }
}
