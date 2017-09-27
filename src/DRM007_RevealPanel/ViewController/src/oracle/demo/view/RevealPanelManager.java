/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.demo.view;
/*******************************************************************************
 *
 * Copyright (c) 2013 Oracle Corporation.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MIT License
 *
 * Contributors:
 *   Duncan Mills
 *
 *
 *******************************************************************************/ 


import java.util.ArrayList;
import java.util.List;

/**
 * Simple management class to handle the state of a single revealPanel
 */
public class RevealPanelManager {

    private int _panelCount = 1;
    private int _toggleTarget = -1;
    private List<Boolean> _revealedList;


    /**
     * Switches the state of the currently selected panel 
     */
    public void toggleState() {
        if (_toggleTarget >= 0 && _panelCount > 0) {
            boolean currentState = false;
            if (_revealedList != null){
                currentState = _revealedList.get(_toggleTarget);
            }
            resetStates();
  
            if (!currentState) {
                _revealedList.set(_toggleTarget, true);
            }
            _toggleTarget = -1;
        }
    }

    
    /**
     * Used to inject a panelCount into the management structure. If not called then an array upper limit of 
     * 10 will be used
     * @param panelCount
     */
    public void setPanelCount(Long panelCount) {
        int candidateCount = panelCount.intValue();
       
        if (candidateCount > 0) {
            //reset the list & re-create in the new size
            _revealedList = null;
            _panelCount = candidateCount;
            resetStates();    
        }
    }

    
    /**
     * Invoked, probably from a setPropertyListener / setActionListener to set the id of the 
     * panel to act on.  This may disclose or hide depending on the current state of the selected
     * panel
     * @param toggleNo - index number of the panel
     */
    public void setToggleTarget(int toggleNo) {
        this._toggleTarget = toggleNo;
    }

    /**
     *Called by the panel to see if it should be visible or not
     * @return List of Booleans indexed by panel number
     */
    public List<Boolean> getRevealed() {
        return _revealedList;
    }
    
    
    /**
     * Either creates or reinitializes the array to close all the 
     * panels
     */
    private void resetStates() {
        
        if (_revealedList == null) {
            _revealedList = new ArrayList<Boolean>(_panelCount);
            for (int i=0; i < _panelCount; i++) {
              _revealedList.add(i,false);
            }
        }
        else{
            for (int i = 0; i < _panelCount; i++) {
                _revealedList.set(i, false);
            }            
        }
    }    

}
