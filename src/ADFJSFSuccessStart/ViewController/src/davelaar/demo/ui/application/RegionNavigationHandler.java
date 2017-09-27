/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package davelaar.demo.ui.application;


import davelaar.demo.ui.controller.bean.DynamicRegionManager;
import davelaar.demo.ui.util.JsfUtils;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidadinternal.application.NavigationHandlerImpl;


/**
 * Class that supports dynamic regions, if navigation outcome contains a colon,
 * the string after the colon is used to set the current taskflow name on the
 * main dynamic region manager
 *
 * @author Steven Davelaar
 */
public class RegionNavigationHandler
  extends NavigationHandlerImpl
{
  private static final ADFLogger sLog = ADFLogger.createADFLogger(RegionNavigationHandler.class);
  private NavigationHandler delegateHandler;
  
  public RegionNavigationHandler(NavigationHandler navHandler)
  {
    super(navHandler);
    this.delegateHandler = navHandler;
  }

  /**
   * Overridden method.
   * Set current task flow name in dynamic region manager
   * @param FacesContext
   * @param action
   * @param outcome
   */
  public void handleNavigation(FacesContext facesContext, String action, String outcome)
  {
    sLog.info("handleNavigation action=" + action + ", outcome=" + outcome);
    if (outcome != null && outcome.indexOf(":")>-1)
    {
      int pos = outcome.indexOf(":");
      String shellAction = outcome.substring(0, pos);
      String regionName = outcome.substring(outcome.indexOf(":") + 1);
      sLog.info("Navigating to " + shellAction + ", with dynamic region " + regionName);
      // set the current taskflow name
      getMainRegionManager().setCurrentTaskFlowName(regionName);
      // navigate to uishell page if needed (usually not needed because only 
      // page is UIShell page) 
      delegateHandler.handleNavigation(facesContext, action, shellAction);
    }
    else
    {
      delegateHandler.handleNavigation(facesContext, action, outcome);
    }
  }

  public DynamicRegionManager getMainRegionManager()
  {
    return (DynamicRegionManager) 
      JsfUtils.getExpressionValue("#{pageFlowScope.mainRegionManager}");
  }


//  /**
//   * Returns the PrimaryRegionManager for the current pageFlowScope.
//   * This instance is not cached because we can have multiple pageFlowScopes,
//   * e.g. when the user opens multiple browser windows.
//   *
//   * @return
//   */
//  public DynamicRegionManager getMainRegionManager()
//  {
//    return (DynamicRegionManager) 
//      JsfUtils.getExpressionValue("#{pageFlowScope.mainRegionManager}");
//  }

}

