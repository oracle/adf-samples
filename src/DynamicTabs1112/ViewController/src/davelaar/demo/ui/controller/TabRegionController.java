/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package davelaar.demo.ui.controller;


import davelaar.demo.ui.view.dyntab.DynTabManager;

import oracle.adf.model.RegionContext;
import oracle.adf.model.RegionController;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;

public class TabRegionController 
  implements RegionController
{
  private static final ADFLogger sLog = ADFLogger.createADFLogger(TabRegionController.class);

  public TabRegionController()
  {
    super();
  }

  @Override
  public boolean refreshRegion(RegionContext rc)
  {
    int refreshFlag = rc.getRefreshFlag();
    rc.getRegionBinding().refresh(refreshFlag);
    setTabDirtyState(rc);
    changeTabLabel(rc);
    
    return false;
  }

  protected void changeTabLabel(RegionContext rc)
  {
    DynTabManager tabManager = (DynTabManager) ADFContext.getCurrent().getPageFlowScope().get("dynTabManager");
    String tabId = (String) ADFContext.getCurrent().getPageFlowScope().get("dynTabId");
    if (((DCBindingContainer)rc.getRegionBinding()).isRefreshed() && tabManager!=null)
    {
      String label = getTabLabel();
      if (label!=null)
      {
        sLog.info("Changing tab ("+tabId+") label to "+label);       
        tabManager.changeTabLabel(tabId,label);
      }
    }
  }

  protected void setTabDirtyState(RegionContext rc)
  {
    DynTabManager tabManager = (DynTabManager) ADFContext.getCurrent().getPageFlowScope().get("dynTabManager");
    if (tabManager!=null)
    {
      DCBindingContainer cont = (DCBindingContainer) rc.getRegionBinding(); 
      if (cont.getIterBindingList().size()>0)
      {
        DCIteratorBinding ib = null;
        // the first iterator binding could be a variables iterator binding, which does not have a datacontrol
        // so skip to the first iterator binding with a data control
        for (Object iterBinding : cont.getIterBindingList())
        {
          final DCIteratorBinding thisIterBinding = (DCIteratorBinding)iterBinding;
          if (thisIterBinding.getDataControl() != null) 
          {
            ib = thisIterBinding;
            break;
          }
        }
        if (ib != null && ib.isRefreshed())
        {
          boolean changes = ib.getDataControl().isTransactionDirty() || ib.getDataControl().isTransactionModified();                                                                    
          String tabId = (String) ADFContext.getCurrent().getPageFlowScope().get("dynTabId");
          sLog.info("Marking current tab dirty state for "+rc.getRegionBinding().getName()+": "+changes);       
          tabManager.markTabDirty(tabId,changes);
        }     
      }     
    }
  }

  @Override
  /**
   * If immediate is set to false on the tabs, this method fires before a tab is closed, so if there 
   * are pending changes that are not yet sent to the server, this method will mark the tab dirty just in time
   */
  public boolean validateRegion(RegionContext regionCtx)
  {
    setTabDirtyState(regionCtx);
    return false;
  }

  @Override
  public boolean isRegionViewable(RegionContext regionCtx)
  {
    return false;
  }

  @Override
  public String getName()
  {
    return null;
  }

  /**
   * Override this method for specific taskflows so you can construct the label anyway you want
   * @return
   */
  protected String getTabLabel()
  {
    return null;
  }
}
