/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.ateam.errorhandling.demo.mobile;

import java.util.ResourceBundle;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.exception.AdfException;
import oracle.adfmf.util.BundleFactory;
import oracle.adfmf.util.Utility;

public class ErrorBean
{
  private static final String XLF_BUNDLE_NAME = "oracle.ateam.errorhandling.demo.mobile.ViewControllerBundle";

  public ErrorBean()
  {
  }

  public void throwExInPrimaryThread(ActionEvent actionEvent)
  {
    throw new AdfException("My error message",AdfException.ERROR);
  }

  public void throwRBExInPrimaryThread(ActionEvent actionEvent)
  {
    try
    {
      throw new AdfException(AdfException.ERROR, XLF_BUNDLE_NAME, "MY_ERROR_MESSAGE",null);      
    }
    catch (Exception e)
    {
      // this may seem strange construct, to rethrow the same exception again, but that is now what
      // is actually happening. In the constructor of AdfException a MissingResourceException is thrown
      // that we want to show in the UI. 
      throw new AdfException(e);
    }
  }
  
  public void throwExInBackgroundThread(ActionEvent actionEvent)
  {
    Runnable runnable = new Runnable()
    {
      public void run()
      {
        // this exception will be lost, not error popup shown in mobile app
        throw new AdfException("My (lost) error message in background",AdfException.ERROR);
      }
    };
    Thread thread = new Thread(runnable);
    thread.start();      
  }

  public void addJSMessageInBackgroundThread(ActionEvent actionEvent)
  {
    Runnable runnable = new Runnable()
    {
      public void run()
      {
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureName(),
          "adf.mf.api.amx.addMessage", new Object[] {AdfException.ERROR, "My error message in background",null,null });
      }
    };
    Thread thread = new Thread(runnable);
    thread.start();      
  }

  public void addRBJSMessageInBackgroundThread(ActionEvent actionEvent)
  {
    Runnable runnable = new Runnable()
    {
      public void run()
      {
        // To get translated message we should be able to use Utility.getResourceAsString as shown below

        // String message = Utility.getResourceString(XLF_BUNDLE_NAME, "MY_ERROR_MESSAGE_BG");

        // However this throws MissingResourceException because under the covers ResourceBundle.getBundle is used
        // which does not support xliff resource bundle. 
        // So, we need to use BundleFactory ourself, and then pass in bundle in same overloaded method:

ResourceBundle bundle = BundleFactory.getBundle(XLF_BUNDLE_NAME);
String message = Utility.getResourceString(bundle, "MY_ERROR_MESSAGE_BG",null);
AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureName(),
  "adf.mf.api.amx.addMessage", new Object[] {AdfException.ERROR, message,null,null });     
        
        // Another way to retrieve a translated string in code is as follows, but that requires knowledge of
        // the page var the bundle is stored:
        // String message = (String) AdfmfJavaUtilities.evaluateELExpression("#{viewcontrollerBundle.MY_ERROR_MESSAGE_BG}");

        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureName(),
          "adf.mf.api.amx.addMessage", new Object[] {AdfException.ERROR, message,null,null });     
      }
    };
    Thread thread = new Thread(runnable);
    thread.start();      
  }

}
