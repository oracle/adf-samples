/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.ateam.errorhandling.demo.mobile;

import java.util.ResourceBundle;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.exception.AdfException;
import oracle.adfmf.util.BundleFactory;
import oracle.adfmf.util.Utility;

public class ErrorBeanClean
{
  private static final String XLF_BUNDLE_NAME = "oracle.ateam.errorhandling.demo.mobile.ViewControllerBundle";

  public ErrorBeanClean()
  {
  }

  public void throwExInPrimaryThread(ActionEvent actionEvent)
  {
    MessageUtils.handleError("My error message",AdfException.ERROR);
  }

  public void throwRBExInPrimaryThread(ActionEvent actionEvent)
  {
    MessageUtils.handleError(AdfException.ERROR, XLF_BUNDLE_NAME, "MY_ERROR_MESSAGE",null);      
  }
  
  public void throwExInBackgroundThread(ActionEvent actionEvent)
  {
    Runnable runnable = new Runnable()
    {
      public void run()
      {
        MessageUtils.handleError("My error message in background",AdfException.ERROR);
      }
    };
    Thread thread = new Thread(runnable);
    thread.start();      
  }

  public void throwRBExInBackgroundThread(ActionEvent actionEvent)
  {
    Runnable runnable = new Runnable()
    {
      public void run()
      {
        MessageUtils.handleError(AdfException.ERROR, XLF_BUNDLE_NAME, "MY_ERROR_MESSAGE_BG",null);      
      }
    };
    Thread thread = new Thread(runnable);
    thread.start();      
  }


}
