/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package davelaar.demo.model.base;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.AttributeDef;
import oracle.jbo.JboException;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.domain.Number;

/**
 * Base class with common functionality for application modules
 * 
 * @author Steven Davelaar
 */
public class BaseApplicationModuleImpl
  extends ApplicationModuleImpl
  implements BaseApplicationModule
{

  private static final ADFLogger sLog = ADFLogger.createADFLogger(BaseApplicationModuleImpl.class);
  
  public BaseApplicationModuleImpl()
  {
    super();
  }

  public void queryByKeyValue(String viewObjectUsage, String keyValue)
  {
    queryByKeyValueInternal(viewObjectUsage, keyValue);
  }

  public void queryByKeyValue(String viewObjectUsage, Number keyValue)
  {
    queryByKeyValueInternal(viewObjectUsage, keyValue);
  }

  /**
   * We cannot export this method because java.lang.Object is not seriablizable
   * as type for key value.
   * @param viewObjectUsage
   * @param keyValue
   */
  protected void queryByKeyValueInternal(String viewObjectUsage, Object keyValue)
  {
    sLog.info("executing queryByKeyValueInternal");
    ViewObject vo = getViewObject(viewObjectUsage);
    AttributeDef[] keyAttrs = vo.getKeyAttributeDefs();
    if (keyAttrs==null || keyAttrs.length==0)
    {
      throw new JboException("View object "+vo.getViewObject().getName()+" does not have primary key, cannot execute queryByKeyValue");      
    }
    else if (keyAttrs.length>1)
    {
      throw new JboException("View object "+vo.getViewObject().getName()+" has composite primary key, cannot execute queryByKeyValue");      
    }
    String keyAttr = keyAttrs[0].getName();    
    sLog.info("executing query with key="+keyAttr+", value="+keyValue+" for "+viewObjectUsage);
    ViewCriteria vc = vo.createViewCriteria();
    ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
    vcRow.setAttribute(keyAttr, keyValue);  
    vc.addElement(vcRow);
    vo.applyViewCriteria(vc);
    vo.executeQuery();
  }

  public ViewObject getViewObject(String viewObjectUsage)
  {
    if (viewObjectUsage==null)
    {
      throw new JboException("argument viewObjectUsage cannot be null in call to getViewObject");      
    }
    ViewObject vo = this.findViewObject(viewObjectUsage);
    if (vo==null)
    {
      throw new JboException("No view object usage named "+viewObjectUsage+" found in "+getName());
    }
    return vo;
  }
  
}
