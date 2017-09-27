/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package davelaar.demo.ui.util;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.RichPopup;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

/**
 * Class that contains utility methods for manipulating the client-side
 * rich components in server-side code using the Extended render Kit Services.
 * 
 * @author Steven Davelaar
 */
public class RichClientUtils
{
  public RichClientUtils()
  {
  }
  
  /** Show popup window.
   * @param popup The popup to be shown
   * @param alignId If specified, align after end with this id.
   * If null, no alignment is configured (and by default the popup will open 
   * in the middle of the browser window).
   */
  public void showPopup(RichPopup popup, String alignId )
  {
    FacesContext context = FacesContext.getCurrentInstance();
    String popupClientId = popup.getClientId(FacesContext.getCurrentInstance());
    
    // Send Javascript to the client to open the popup window, since
    // opening an <af:popup> is totally on the client side.
    StringBuilder script = new StringBuilder();
    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupClientId).append("'); ")
          .append("if (!popup.isPopupVisible()) { ")
          .append("var hints = {}; ");
    if (alignId != null) {
        script.append("hints[AdfRichPopup.HINT_ALIGN_ID] = '").append(alignId).append("'; ") 
          .append("hints[AdfRichPopup.HINT_ALIGN] = AdfRichPopup.ALIGN_AFTER_END; ");
    }
    script.append("popup.show(hints);}");
    ExtendedRenderKitService erks = 
      Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
    erks.addScript(context, script.toString());
    
  }
  
  public void hidePopup(RichPopup popup)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    String popupClientId = popup.getClientId(context);
    StringBuilder script = new StringBuilder();
    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupClientId).append("'); ")
          .append("popup.hide();");
    ExtendedRenderKitService erks = 
      Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
    erks.addScript(context, script.toString());    
  }

  public static RichClientUtils getInstance()
  {
    return new RichClientUtils();
  }
  
  public void setInputFocus(String clientId) 
  {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      ExtendedRenderKitService service = Service.getRenderKitService(facesContext, ExtendedRenderKitService.class);  
          service.addScript(facesContext, "comp = AdfPage.PAGE.findComponent('"+clientId+"');
" +   
                                          "comp.focus()");  
      
  }

  public void writeJavaScriptToClient(String script) 
  {
      FacesContext fctx = FacesContext.getCurrentInstance();
      ExtendedRenderKitService erks =
          Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
      erks.addScript(fctx, script);
  }

}
