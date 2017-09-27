/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.ateam.errorhandling.demo.mobile;

import java.util.ResourceBundle;

import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.exception.AdfException;
import oracle.adfmf.util.BundleFactory;
import oracle.adfmf.util.Utility;

public class MessageUtils
{

  public static void handleError(Throwable throwable, String severity)
  {
    if (AdfmfJavaUtilities.isBackgroundThread())
    {
      addJavaScriptMessage(severity, throwable.getLocalizedMessage());
    }
    else
    {
      throw new AdfException(throwable, severity);
    }
  }

  public static void handleError(Throwable throwable)
  {
    if (AdfmfJavaUtilities.isBackgroundThread())
    {
      addJavaScriptMessage(AdfException.ERROR, throwable.getLocalizedMessage());
    }
    else
    {
      throw new AdfException(throwable);
    }
  }

  public static void handleError(String message, String severity)
  {
    if (AdfmfJavaUtilities.isBackgroundThread())
    {
      addJavaScriptMessage(severity, message);
    }
    else
    {
      throw new AdfException(message, severity);
    }
  }

  public static void handleError(String severity, String bundleName, String messageKey, Object[] params)
  {
    ResourceBundle bundle = BundleFactory.getBundle(bundleName);
    String message = Utility.getResourceString(bundle, messageKey, params);
    if (AdfmfJavaUtilities.isBackgroundThread())
    {
      addJavaScriptMessage(severity, message);
    }
    else
    {
      throw new AdfException(message, severity);
    }
  }

  public static void addJavaScriptMessage(String severity, String message)
  {
    AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureName(),
                                                              "adf.mf.api.amx.addMessage", new Object[]
        { severity, message, null, null });
  }
}
