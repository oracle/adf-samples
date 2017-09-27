/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view;

import java.util.Collection;

import java.util.concurrent.atomic.AtomicInteger;
import oracle.adf.view.rich.activedata.BaseActiveDataModel;
import oracle.adf.view.rich.event.ActiveDataUpdateEvent;


public class MyActiveDataModel extends BaseActiveDataModel {

    // -------------- API from BaseActivceDataMode

    @Override
    protected void startActiveData(Collection<Object> rowKeys,
                                   int startChangeCount)
    {
      /* we don't do anything here as there is no need for it in this example
       * usually you could use some listenerCount to see if the maximum of the
       * allowed listeners are already attached. If not, you could register
       * some listener here...
       */
    }

    @Override
    protected void stopActiveData(Collection<Object> rowKeys)
    {
      // same as above... no need to disconnect here
    }

    @Override
    public int getCurrentChangeCount()
    {
      return changeCounter.get();
    }
    
    // -------------- custom API ..........

    /**
     * Does not much; simple increments the change counter...
     */
    public void prepareDataChange()
    {
      changeCounter.incrementAndGet();
    }

    /**
     * Delivers an <code>ActiveDataUpdateEvent</code> object to the ADS system.
     * 
     * @param event the ActiveDataUpdateEvent object
     */
    public void notifyDataChange(ActiveDataUpdateEvent event)
    {
      // delegate even to internal fireActiveDataUpdate() method
      fireActiveDataUpdate(event);
    }

    // properties
    //private final AtomicInteger listenerCount = new AtomicInteger(0);
    private final AtomicInteger changeCounter = new AtomicInteger();

}

