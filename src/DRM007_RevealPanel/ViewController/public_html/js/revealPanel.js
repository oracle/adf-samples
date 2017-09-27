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


/**
 * Function used to proxy the client click event on the topic panel back up
 * to a server side handler
 * @param clickEvent
 */
function revealTopicPanelClicked(clickEvent) {

    var topicId = clickEvent.getSource().getProperty("topicSelected");

    AdfLogger.LOGGER.info("Entering toggle event on topic panel " + topicId);

    //Simply send a callback to the server asking to fire the toggle event
    var serverEvent = new AdfCustomEvent(clickEvent.getSource(), "revealPanelToggle", 
    {
        panelId : topicId
    }, false);
    serverEvent.queue(true);
}


/**
 * Client side version of the topic click handler.
 * In this case, the server is not notified and all the panel state is handled
 * here on the client
 * @param clickEvent
 */
function revealTopicPanelClickedJS(clickEvent) {
    //Slightly different arguments in this case as we need more information 
    //To build the client side structures to manage the grid(s)
    var locator = clickEvent.getSource().getProperty("revealPanelLocator");

    var locatorSegments = locator.split(":");
    groupId = locatorSegments[0];
    topicId = locatorSegments[1];

    AdfLogger.LOGGER.info("Entering toggle event on topic panel " + topicId);

    toggleOnClient(clickEvent.getSource(), groupId, topicId);
}

/**
 * The actual implementation of the client toggle 
 * TODO The data structures here assume a stable set of components once the structures are built
 *      This should really be changed to do just-in-time lookup  so that everything will continue to 
 *      work if the grid is in a region that is replaced and then restored.
 *      For the purposes of this example though 
 * @param sourceTopicGrid 
 * @param revealGroup 
 * @param revealTopicId 
 */
function toggleOnClient(sourceTopicGrid, revealGroup, revealTopicId) {

    if (typeof revealPanelGroupMap == "undefined") {
        //create the overall control structure
        revealPanelGroupMap = new Object();
    }

    var thisGroupControl = revealPanelGroupMap[revealGroup];
    if (thisGroupControl == null) {
        thisGroupControl = initGroupControl(sourceTopicGrid, revealGroup)
    }

    //now locate the correct topic in the group array
    var topicControl = thisGroupControl.topics[topicId];
    
    //It is possible (if we've been out of the fragment and back) that the references are no longer valid
    //So test that and re-build the array if needed
    if (topicControl.revealComponent.isDead()){
        thisGroupControl = initGroupControl(sourceTopicGrid, revealGroup);
        topicControl = thisGroupControl.topics[topicId];
        AdfLogger.LOGGER.info("Component reference was invalid - control array has been rebuilt");
    }
    
    
    var lastTopicId = thisGroupControl.selectedTopic;
    var requestedNewState = !topicControl.revealed;
    if (topicControl != null) {
        //if we found the array then reset the current one
        if (lastTopicId >= 0 ) {
            var lastOpenTopicControl = thisGroupControl.topics[lastTopicId];
            if (lastOpenTopicControl.revealed = true){
                lastOpenTopicControl.revealed = false;
                lastOpenTopicControl.triangleComponent.setStyleClass("triangleMarkerHidden");
                lastOpenTopicControl.triangleComponent.setVisible(false);
                lastOpenTopicControl.revealComponent.setStyleClass("revealPanelHidden");
                lastOpenTopicControl.revealComponent.setVisible(false);               
            }
        }

        if (requestedNewState) {
            thisGroupControl.selectedTopic = topicId;

            topicControl.revealed = true;
            topicControl.triangleComponent.setVisible(true);
            topicControl.revealComponent.setVisible(true);
            topicControl.revealComponent.setStyleClass("revealPanelAnimated");

            //define a callback to reveal the triangle once the re-scale is done
            var animatedComponentReal = AdfAgent.AGENT.getElementById(topicControl.revealComponent.getClientId());
            var finishRevealFunction = function (event) {
                topicControl.triangleComponent.setStyleClass("triangleMarker");
                event.target.removeEventListener("webkitTransitionEnd", finishRevealFunction);
            };
            animatedComponentReal.addEventListener("webkitTransitionEnd", finishRevealFunction, false);
        }
    }
}


/**
 * Function used to wrap the creation of the group control structure and it's basic intialization
 * @param sourceTopicGrid 
 * @param revealGroup 
 */
function initGroupControl(sourceTopicGrid, revealGroup){
    var thisGroupControl = new Object();
    //need to populate the objects to hide / reveal within the group
    var thisGroupArray = initializeGroupArray(sourceTopicGrid, revealGroup);
    thisGroupControl.selectedTopic =  - 1;
    thisGroupControl.topics = thisGroupArray;
    //Store that for later  
    revealPanelGroupMap[revealGroup] = thisGroupControl; 
    return thisGroupControl;
}

/**
 * This function will create a data structure that lists all of the "Topics"
 * contained within the revealPanel, storing the component references for
 * the things we need to show and hide and then setting the initial state
 * of them all to hidden
 * @param startTopic
 * @param groupId
 */
function initializeGroupArray(startTopic, groupId) {
    var newGroupArray = new Array();

    //The event comes from the "topic grid" this is embedded in one cell of the 
    //overall reveal grid.  So find the parent which will be the main reveal grid
    if (startTopic instanceof AdfRichPanelGridLayout) {
        var parentGrid = startTopic.getParent();
        AdfAssert.assertPrototype(parentGrid, AdfRichPanelGridLayout);

        //Then visit it's top level children and populate the data structure
        var callbackState = {
            groupId : groupId, groupArray : newGroupArray
        }
        parentGrid.visitChildren(revealPanelChildrenCallback, callbackState, false);
    }
    return newGroupArray;
}

/**
 * Callback function.
 * For each top level child of the reveal grid we need to populate the correct
 * values into the management array.
 * Each logical reveal row is effectivly 3 components, the topic panel itself which
 * is always visible, the triangle pointer panel and the main reveal panel
 * We store the component reference for the last two to make it easy to hide / show them
 * Each sub-panel within the row is identified by the last segment of the locator
 * Because of the way locators work you could in fact have two groups within the same
 * panelGridLayout as long as they have different groupIds.
 */
revealPanelChildrenCallback = function (component) {
    var locator = component.getProperty("revealPanelLocator");

    if (locator != undefined) {
        var locatorSegs = locator.split(":");
        var compGroupId = locatorSegs[0];
        var compTopicId = locatorSegs[1];
        var compPart = locatorSegs[2];
    }

    if (compGroupId == this.groupId && compPart > 0) {
        var topicEntry = this.groupArray[compTopicId];
        if (topicEntry == null) {
            topicEntry = new Object();
            topicEntry.revealed = false;
            this.groupArray[compTopicId] = topicEntry;
        }
        if (compPart == 1) {
            component.setStyleClass("triangleMarkerHidden");
            topicEntry.triangleComponent = component;
        }
        else if (compPart == 2) {
            component.setStyleClass("revealPanelHidden");
            topicEntry.revealComponent = component;
        }
        else {
            AdfLogger.LOGGER.error("Ignoring invalid component part number for: " + locator);
        }
    }

    //We only want to traverse the top level children so return 1
    return 1;
}