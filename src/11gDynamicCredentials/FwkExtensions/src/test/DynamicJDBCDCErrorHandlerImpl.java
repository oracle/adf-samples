/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.SQLException;

import java.util.List;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCErrorHandlerImpl;

import oracle.jbo.DMLException;
import oracle.jbo.common.JBOClass;
/**
 * This class is configured as the custom ADFM error handler in order to throw
 * the failed connection attempt exception instead of only caching it.
 * 
 * It is configured by setting the ErrorHandlerClass attribute of the
 * root &lt;Application> element of the DataBindings.cpx file in each project
 * of this workspace.
 *
 * For example:
 * 
 * <Application xmlns="http://xmlns.oracle.com/adfm/application"
 *            id="DataBindings"
 *            Package="test" version="9.0.5.16.0" SeparateXMLFiles="false"
 *            ClientType="Generic"
 *            ErrorHandlerClass="test.DynamicJDBCDCErrorHandlerImpl">
 *            <!-- etc. -->
 * </Application>
 *
 */
public class DynamicJDBCDCErrorHandlerImpl extends DCErrorHandlerImpl {
  public DynamicJDBCDCErrorHandlerImpl() {
    super(true);
  }
  public DynamicJDBCDCErrorHandlerImpl(boolean b) {
    super(b);
  }

  @Override
  public void reportException(DCBindingContainer formBnd, Exception e) {
    super.reportException(formBnd, e);
    if (e instanceof DMLException) {
      Object[] details = ((DMLException)e).getDetails();
      if (details != null && details.length > 0) {
        if (details[0] instanceof SQLException) {
          SQLException s = (SQLException)details[0];
          int errorCode = s.getErrorCode();
          if (DynamicJDBCLoginHelper.isFailedDBConnectErrorCode(s)) {
            markResponseCompleteIfUsingJSF();
            throw (DMLException)e;
          }
        }
      }
    }
  }

    /*
     * If we are running in a Faces environment, invoke
     * the FacesContext.responseComplete() method after
     * the session invalidate. We use Java reflection
     * so that our code can still work in a Non-Faces
     * environment, too.
     */
    private void markResponseCompleteIfUsingJSF() {
        try {
            Class c = JBOClass.forName("javax.faces.context.FacesContext");
            Method m = c.getMethod("getCurrentInstance", null);
            Object obj = m.invoke(null, null);
          if (obj != null) {
            m = c.getMethod("responseComplete",null);
            m.invoke(obj,null);
          }
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            // Ignore, we're not running in a faces context.
        }        
    }
}
