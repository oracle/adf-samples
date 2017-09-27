/**
 * Client side functions for the Auto-Reduce Search & Pagingation Sample
 */

function handleTileRowToggle(actionEvent) {
    // Client-side toggle between the tiled view and the rows view:
    var eventSource = actionEvent.getSource();
    var showTileView = eventSource.getProperty("tileView") == "yes";
    var tileCtb = eventSource.findComponent("tileCtb");
    var rowCtb = eventSource.findComponent("rowCtb");
    var tileView = eventSource.findComponent("tileView");
    var rowView = eventSource.findComponent("rowView");
    var tileRowScroll = eventSource.findComponent("tileRowScroll");
    if (tileView == null || tileView == null || tileCtb == null || rowCtb == null || tileRowScroll == null) {
        AdfLogger.LOGGER.severe("Could not find a client component from the handleTileRowToggle handler's event source:" + "\n eventSource=" + eventSource + "\n tileCtb=" + tileCtb + "\n rowCtb=" + rowCtb + "\n tileView=" + tileView + "\n rowView=" + rowView + "\n tileRowScroll=" + tileRowScroll);
        return;
    }
    // Toggle the selected states of the client-optimized toolbar buttons:
    tileCtb.setSelected(showTileView);
    rowCtb.setSelected(!showTileView);
    // Toggle the visible states of the tiled and row content views:
    tileView.setVisible(showTileView);
    rowView.setVisible(!showTileView);
}

/**
 * Handle autoSubmitting a search. The delay here is managed by the autoCompleteDelay
 * parameter or will default to 1 second if this is not supplied
 * This function must be processed on a key up event so that it's informed of interesting
 * keys such as backspace and enter.
 * @param actionEvent
 */
function invokeAutoSearch(actionEvent) {
    var keyCode = actionEvent.getKeyCode();
    var raisingComponent = actionEvent.getSource();
    switchOffAutoComplete(raisingComponent);

    var configuredDelay = raisingComponent.getProperty('autoCompleteDelay');

    AdfLogger.LOGGER.config("Processing keystroke from: " + raisingComponent.getClientId() + "; KeyCode=" + keyCode);
    
    // First of all process the special case of pressing the Enter / Return key
    // Interpret this to invoke the callback immediately
    // This will also remove any pending timer
    if (keyCode == 13) {
      queryCallback(raisingComponent);
    } else { 
        // The keys you choose to pay attention to will vary depending on your 
        // needs. Here I'm ignoring those keys unlikely to be in a SQL Object name 
        if (keyCode == 8 || keyCode == 46 || keyCode == 32 || (keyCode >= 48 && keyCode <= 90) || (keyCode >= 96 && keyCode <= 111) || (keyCode >= 186 && keyCode <= 192) ){
          var waitTimeSeconds = (configuredDelay == null)? 1 : configuredDelay;
          if (window.autoReduceSearchDelayTimer == undefined || window.autoReduceSearchDelayTimer == null) {
                var queryFunction = function () {
                    queryCallback(raisingComponent);
                };
                window.autoReduceSearchDelayTimer = setTimeout(queryFunction, (waitTimeSeconds * 1000));
            }
        }
    }
}

/**
 * This function queues an event on the channel back to the server 
 * The name of this event "clientPushQueryEvent" matches the af:serverListener type attribute
 * @param raisingComponent 
 */
function queryCallback(raisingComponent) {
    //Clear the timer
    window.autoReduceSearchDelayTimer = null;
    
    //Queue the event
    var queryEvent = new AdfCustomEvent(raisingComponent, "clientPushQueryEvent", { }, false);
    queryEvent.queue(true);
    AdfLogger.LOGGER.info("Sending queryCallbackEvent to server to process value " + raisingComponent.getSubmittedValue());
}

/** 
 * In 11.1.1.6 of ADF Faces we don't have an attribute on the component 
 * to switch off browser autocomplete, which, in this use case, just gets in 
 * the way
 * So this function switches it off if we need to 
 */
function switchOffAutoComplete(raisingComponent){
    if (window.autoReduceAutoCompleteOff == undefined || window.autoReduceAutoCompleteOff == null){
       var raisingType = raisingComponent.getComponentType();
       if (raisingType == "oracle.adf.RichInputText"){
         var domRoot = AdfAgent.AGENT.getElementById(raisingComponent.getClientId());
         var inputArr = domRoot.getElementsByTagName("input");
         inputArr[0].setAttribute("autocomplete","off");
       }
       window.autoReduceAutoCompleteOff = true; 
    }    
}
