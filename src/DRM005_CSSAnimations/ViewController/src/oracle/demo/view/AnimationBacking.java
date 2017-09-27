/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.demo.view;

import java.util.Map;

import javax.faces.context.FacesContext;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.layout.RichDecorativeBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.render.ClientEvent;

public class AnimationBacking {
    private static ADFLogger _logger = ADFLogger.createADFLogger(AnimationBacking.class);
    private UIManager _uiManager;
    private RichPanelGroupLayout flipPanel;
    private RichDecorativeBox containerBox;
    private RichPanelGroupLayout refreshPanel;
    private RichPopup fadingPopup;

    public AnimationBacking() {
    }

    /**
     * Gets the correct client component ID for this panel in the context in which the 
     * panel is placed. This provides a safe way of getting the client ID if the component
     * is embedded in a region or similar where the computed path may change depending on the use
     * @return clientID for use in JavaScript
     */
    public String  getFlipClientId() {
        return flipPanel.getClientId(FacesContext.getCurrentInstance());
    }
    
    /**
     * Event raised from JavaScript to tell us that the flip animation is underway and we 
     * need to change the content on the panel 
     * @param clientEvent
     */
    public void handlePanelFipEvent(ClientEvent clientEvent) {
        String currentSide = _uiManager.getFlipAnimationSelectedSide();
        String targetSide = UIManager.A_SIDE;
        if (currentSide.equals(UIManager.A_SIDE)){
            targetSide = UIManager.B_SIDE;
        }
        _logger.info("AnimationBacking","handlePanelFipEvent","Flipping to " + targetSide);
        getUiManager().setFlipAnimationSelectedSide(targetSide);
        
        // And queue the client refresh
       AdfFacesContext.getCurrentInstance().addPartialTarget(getRefreshPanel());
    }
    
    public void handleMsgEvent(ClientEvent clientEvent) {
        Map params = clientEvent.getParameters();
        for(Object entry : params.values()) {
            _logger.info("Map Entry " +  entry);
        }
        
    }    

    // Standard getters / setters
    //--------------------------------------------------------------------------------------------------------


    public void setFlipPanel(RichPanelGroupLayout flipAPanel) {
        this.flipPanel = flipAPanel;
    }

    public RichPanelGroupLayout getFlipPanel() {
        return flipPanel;
    }
    

    /**
     *Injection point for the UI Manager
     * @param _uiManager
     */
    public void setUiManager(UIManager _uiManager) {
        this._uiManager = _uiManager;
    }

    public UIManager getUiManager() {
        return _uiManager;
    }

    public void setContainerBox(RichDecorativeBox containerBox) {
        this.containerBox = containerBox;
    }

    public RichDecorativeBox getContainerBox() {
        return containerBox;
    }

    public void setRefreshPanel(RichPanelGroupLayout refreshPanel) {
        this.refreshPanel = refreshPanel;
    }

    public RichPanelGroupLayout getRefreshPanel() {
        return refreshPanel;
    }

    public void setFadingPopup(RichPopup fadingPopup) {
        this.fadingPopup = fadingPopup;
    }

    public RichPopup getFadingPopup() {
        return fadingPopup;
    }
}
