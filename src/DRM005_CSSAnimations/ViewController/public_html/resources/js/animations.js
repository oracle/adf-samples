function animateFlipPanel(event) {
    var platformStr = AdfAgent.AGENT.getPlatform();
    var platformVer = AdfAgent.AGENT.getVersion();
    var fpId = event.getSource().getProperty('flipPanel');
    var raisingComponent = event.getSource();
    //First animation sends us to 90deg
    var transition = "rotateOut";
    flipPanel = AdfPage.PAGE.findComponentByAbsoluteId(fpId);
    initiateClientFlip(raisingComponent, flipPanel, transition);
}

/**
 * Kick off the animation - rotate the panel to 90 degrees which will make it disappear as it's sideways on to
 * us.
 * Then register the callback to rotate back once the animation is finished
 * @param raisingComponent - the button that kicked things off
 * @param flipPanel - the PGL to animate (assign style to)
 * @param transition - the style to apply
 */
function initiateClientFlip(raisingComponent, flipPanel, transition) {
    //Set the style to animate the rotation
    flipPanel.setStyleClass(transition);

    //Setup a callback to reverse the animation once the transition is finished
    //Note that we remove the transition listener once it's executed so that 
    //It is not called by the flipBack transition as well 
    //Note also that we have to attach the listener to the underlying DOM object
    //get the DOM object that represents this panelGroupLayout
    var flipPanelReal = AdfAgent.AGENT.getElementById(flipPanel.getClientId());
    var reverseTransition = "rotateReturn"

    //Define the callback 
    var flipBackFunction = function (event) {
        animateFlipBack(raisingComponent, flipPanel, reverseTransition);
        event.target.removeEventListener("webkitTransitionEnd", flipBackFunction);
    };

    //Add the transition listener to queue up both the animation back and the server side change 
    // which will apparently flip the contents of the panel
    flipPanelReal.addEventListener("webkitTransitionEnd", flipBackFunction, false);
}

/**
 * Once the initial animation is done this method is invoked to change the contents of the panel and then
 * animate back to the starting point giving that full 180 degree flip appearance
 * @param raisingComponent
 * @param flipPanel
 * @param reverseTransition
 */
function animateFlipBack(raisingComponent, flipPanel, reverseTransition) {
    // Call the event on the server which will cause the switcher to 
    // change it's contents and issue a PPR event
    raiseServerFlipEvent(raisingComponent);
    //Now start the return animation
    flipPanel.setStyleClass(reverseTransition);
}

/**
 * Simple function to queue up the server side event on the button that
 * instigated the flip
 * @param raisingComponent
 */
function raiseServerFlipEvent(raisingComponent) {
    var flipEvent = new AdfCustomEvent(raisingComponent, "flipEvent", 
    {
    },
false);
    flipEvent.queue(true);
}


/**
 * Client listener which will kick off the animation to fade the dialog and register
 * a callback to correctly reset the popup once the animation is complete
 * @param event
 */
function animateFadingPopup(event) {
    var fadePopup = event.getSource();
    var fadeCandidate = false;

    //Ensure that the popup is initially Opaque
    //This handles the situation where the user has dismissed
    //the popup whilst in the process of fading
    var fadeContainer = findFadeContainer(fadePopup);
    if (fadeContainer != null) {
        fadeCandidate = true;
        fadeContainer.setStyleClass("popupFadeReset");
    }

    //Only continue if we can actually fade this popup
    if (fadeCandidate) {

        //See if a delay has been specified
        var waitTimeSeconds = event.getSource().getProperty('preFadeDelay');
        //Default to 5 seconds if not supplied
        if (waitTimeSeconds == undefined) {
            waitTimeSeconds = 5;
        }

        // Now call the fade after the specified time
        var fadeFunction = function () {
            initiatePopupFade(fadePopup);
        };
        var fadeDelayTimer = setTimeout(fadeFunction, (waitTimeSeconds * 1000));
    }
}

/**
 * Function which will kick off the animation to fade the dialog and register
 * a callback to correctly reset the popup once the animation is complete
 * @param popup the popup we are animating
 */
function initiatePopupFade(popup) {

    //Only continue if the popup has not already been dismissed 
    if (popup.isPopupVisible()) {
        //The skin styles that define the animation 
        var fadeoutAnimationStyle = "popupFadeAnimate";
        var fadeAnimationResetStyle = "popupFadeReset";

        var fadeContainer = findFadeContainer(popup);
        if (fadeContainer != null) {
            var fadeContainerReal = AdfAgent.AGENT.getElementById(fadeContainer.getClientId());

            //Define the callback this will correctly reset the popup once it's disappeared
            var fadeCallbackFunction = function (event) {
                closeFadedPopup(popup, fadeContainer, fadeAnimationResetStyle);
                event.target.removeEventListener("webkitTransitionEnd", fadeCallbackFunction);
            };

            //Initiate the fade
            fadeContainer.setStyleClass(fadeoutAnimationStyle);

            //Register the callback to execute once fade is done
            fadeContainerReal.addEventListener("webkitTransitionEnd", fadeCallbackFunction, false);
        }
    }
}

/**
 * The thing we actually fade will be the only child
 * of the popup assuming that this is a dialog or window
 * @param popup
 * @return the component, or null if this is not valid for fading
 */
function findFadeContainer(popup) {
    var children = popup.getDescendantComponents();
    var fadeContainer = children[0];
    if (fadeContainer != undefined) {
        var compType = fadeContainer.getComponentType();
        if (compType == "oracle.adf.RichPanelWindow" || compType == "oracle.adf.RichDialog") {
            return fadeContainer;
        }
    }
    return null;
}

/**
 * Callback function to correctly cancel and reset the style in the popup
 * @param popup id of the popup so we can close it properly
 * @param contatiner the window / dialog within the popup to actually style
 * @param resetStyle the syle that sets the opacity back to solid
 */
function closeFadedPopup(popup, container, resetStyle) {
    container.setStyleClass(resetStyle);
    popup.cancel();
}