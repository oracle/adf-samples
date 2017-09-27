/*-------------------------------------------------------------------------------------------
 * This code is distributed under the MIT License (MIT)
 *
 * Copyright (c) 2012 Duncan Mills
 *-------------------------------------------------------------------------------------------
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all 
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 *-------------------------------------------------------------------------------------------*/



/**
 * This function is called from a clientListener defined as a child of the 
 * main af:document
 * You can also define a af:clientAttribute called NOTIFICATION_INTERVAL
 * with a numerical value in milliseconds which will determine how often 
 * resize notifications are sent back to the server. This helps prevent a
 * flood of ajax requests as the user resizes the window
 * @param event 
 */
function registerWindowResizeListener(event) {

    var document = event.getSource();
    var callbackInterval = document.getProperty("NOTIFICATION_INTERVAL");
    
    //Generate a sensible callback interval as a default if none has been sepecified
    if (callbackInterval == undefined || callbackInterval == null 
        || isNaN(callbackInterval-0) ||callbackInterval < 0){
        callbackInterval = 200;
    }
    
    AdfLogger.LOGGER.info("Registering resize listener with callback interval of " + callbackInterval);
    
    var resizeCallback = function() { queueWindowResizeEvent(document, callbackInterval);};
    
    //Handle slight differences between browsers and add a listener
    if (window.addEventListener){
        window.addEventListener("resize",resizeCallback,false)
    }
    else if (window.attachEvent){
        window.attachEvent("onresize",resizeCallback)
    }
    //Finally queue an immediate event just to make the server aware of the
    //Initial state of things
    queueWindowResizeEvent(document, 0);
}

/**
 * This function works out the actual sizing and optionally uses 
 * a timer to delay the propagation to the server 
 * @param document - <af:document> object (needs an appropriate <af:serverListener>
 * @param interval - How long a timer to create (ms)
 */
function queueWindowResizeEvent(document, interval){
    //Slight differences in browsers so we use some inbuilt 
    //ADF functions to handle this
    var newWidth = AdfAgent.AGENT.getWindowClientWidth(window);
    var newHeight = AdfAgent.AGENT.getWindowClientHeight(window);  
    // And queue the event back to the server 
    if (interval == 0){
        //Dispatch immediately
        dispatchWindowResizeEvent(document, newWidth, newHeight);
    }
    else {
      //First clear up any existing timeout
      var resizeTimer = AdfPage.PAGE.getPageProperty("WINDOW_RESIZE_TIMER");
      if (resizeTimer != undefined){
        window.clearTimeout(resizeTimer);  
      }
      //now create a timeout with the specified interval
      var timeoutFunction = function() {dispatchWindowResizeEvent(document, newWidth, newHeight);}
      resizeTimer = window.setTimeout(timeoutFunction, interval);
      //And save that away so we don't end up queuing loads of events
      AdfPage.PAGE.setPageProperty("WINDOW_RESIZE_TIMER",resizeTimer);
    }  
}

/**
 * Function that actually queues the event for the server to pick up 
 * @param document 
 * @param newWidth 
 * @param newHeight 
 */
function dispatchWindowResizeEvent(document, newWidth, newHeight){
    var resizeEvent = new AdfCustomEvent(document, "windowResizeEvent",{width : newWidth, height : newHeight },false);
    resizeEvent.queue(true);    
}

