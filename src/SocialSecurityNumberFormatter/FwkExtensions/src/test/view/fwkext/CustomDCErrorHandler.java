/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.fwkext;

import java.util.List;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCControlBinding;
import oracle.adf.model.binding.DCErrorHandlerImpl;

import oracle.jbo.JboException;
import oracle.jbo.domain.DataCreationException;
import oracle.jbo.uicli.binding.JUCtrlValueBinding;


public class CustomDCErrorHandler extends DCErrorHandlerImpl {
    public CustomDCErrorHandler(boolean setToThrow) {
        super(setToThrow);
    }

    /**
     * Overridden ADF binding framework method to customize the way
     * that Exceptions are reported to the client.
     * 
     * Here we set the "append codes" flag to false on each JboException
     * in the exception (and any detail JboExceptions it contains)
     * 
     * @param bc BindingContainer
     * @param ex exception being reported
     */
    public void reportException(DCBindingContainer bc, Exception ex) {
        //Force JboException's reported to the binding layer to avoid
        //printing out the JBO-XXXXX product prefix and code.
        disableAppendCodes(ex);
        setAttrInfoOnDataCreationExceptionIfNeeded(bc, ex);
        super.reportException(bc, ex);
    }

    private void setAttrInfoOnDataCreationExceptionIfNeeded(DCBindingContainer bc, 
                                                            Exception ex) {
        if (ex instanceof JboException) {
            JboException jex = (JboException)ex;
            Object[] detailExceptions = jex.getDetails();
            if (detailExceptions != null && detailExceptions.length > 0) {
                for (int z = 0, numEx = detailExceptions.length; z < numEx; 
                     z++) {
                    Exception detailEx = (Exception)detailExceptions[z];
                    if (detailEx instanceof DataCreationException) {
                        DataCreationException dce = 
                            (DataCreationException)detailEx;
                        if (dce.getAttrName() == null) {
                            List<DCControlBinding> controlBindings = 
                                bc.getControlBindings();
                            for (DCControlBinding binding: controlBindings) {
                                if (binding instanceof JUCtrlValueBinding) {
                                    JUCtrlValueBinding valueBinding = 
                                        (JUCtrlValueBinding)binding;
                                    List<Exception> attrExceptions = 
                                        valueBinding.getErrors();
                                    if (attrExceptions != null) {
                                        for (Exception exception: 
                                             attrExceptions) {
                                            if (exception == jex) {
                                                String viewDefName = valueBinding.getIteratorBinding().getViewObject().getDefFullName();
                                                String attrName = valueBinding.getAttributeDef().getName();
                                                Object attrValue = dce.getAttrValue();
                                                dce.setAttrInfo(0, 
                                                                viewDefName, 
                                                                attrName, 
                                                                attrValue);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        setAttrInfoOnDataCreationExceptionIfNeeded(bc, 
                                                                   detailEx);
                    }
                }
            }
        }
    }

    private void disableAppendCodes(Exception ex) {
        if (ex instanceof JboException) {
            JboException jboEx = (JboException)ex;
            jboEx.setAppendCodes(false);
            Object[] detailExceptions = jboEx.getDetails();
            if ((detailExceptions != null) && (detailExceptions.length > 0)) {
                for (int z = 0, numEx = detailExceptions.length; z < numEx; 
                     z++) {
                    disableAppendCodes((Exception)detailExceptions[z]);
                }
            }
        }
    }

}
