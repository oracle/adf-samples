package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.JboException;

public class ActiveDataObject
{
  public ActiveDataObject()
  {
    super();
  }

  private static ADFLogger sLog = ADFLogger.createADFLogger(ActiveDataObject.class);
  
  public Object getAttributeValue(String attrName)
  {
    String methodName = "get" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
    try
    {
      Method getter = this.getClass().getMethod(methodName, null);
      Object value = getter.invoke(this, null);
      return value;
    }
    catch (IllegalAccessException e)
    {
      throw new JboException("Error invoking getter method for attribute " + attrName + " in class " +
                                 this.getClass().getName() + " " +
                                 e.getLocalizedMessage());
    }
    catch (InvocationTargetException e)
    {
     throw new JboException("Error invoking getter method for attribute " + attrName + " in class " +
                                this.getClass().getName() + " " +
                                e.getLocalizedMessage());
   }
    catch (NoSuchMethodException e)
    {
      throw new JboException("Cannot find public method "+methodName+" in "+getClass().getName());
    }
  }

}
