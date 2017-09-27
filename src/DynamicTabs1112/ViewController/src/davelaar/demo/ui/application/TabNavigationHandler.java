/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package davelaar.demo.ui.application;

import davelaar.demo.ui.view.dyntab.DynTabManager;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidadinternal.application.NavigationHandlerImpl;

/**
 * Class that supports dynamic tabs, if navigation outcome contains a colon,
 * the string after the colon is used to open a dyanmic tab by that name using the
 * dynamic tabs manager
 *
 * @author Steven Davelaar
 */
public class TabNavigationHandler
  extends NavigationHandlerImpl
{
  private static final ADFLogger sLog = ADFLogger.createADFLogger(TabNavigationHandler.class);

  public TabNavigationHandler(NavigationHandler navHandler)
  {
    super(navHandler);
  }

  /**
   * Overridden method. Launch tab when tabName specified after colon,
   * @param facesContext
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
      String tabName = outcome.substring(outcome.indexOf(":") + 1);
      sLog.info("Navigating to " + shellAction + ", opening dynamic tab " + tabName);
      
      // launch dyn tab
      DynTabManager.getCurrentInstance().launchTab(tabName);
      
      // navigate to uishell page if needed (usually not needed because only 
      // page is UIShell page) 
      super.handleNavigation(facesContext, action, shellAction);
    }
    else
    {
      super.handleNavigation(facesContext, action, outcome);
    }
  }
}

