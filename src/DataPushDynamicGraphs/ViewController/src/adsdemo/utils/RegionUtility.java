package adsdemo.utils;

import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.fragment.UIXRegion;

import org.apache.myfaces.trinidad.context.RequestContext;

public class RegionUtility
{

  public static String getRegionURI(FacesContext facesContext,
                                    UIComponent component,
                                    UIXRegion region)
  {
    // We need the viewId of the region, use the ContextCallback mechanism
    // to get it
    RegionPagePathCallback callback = new RegionPagePathCallback(region);
    facesContext.getViewRoot().invokeOnComponent(facesContext,
                                                 component.getClientId(facesContext),
                                                 callback);
    String pagePath = callback.getRegionViewId();

    return pagePath;
  }

  // This code is taken from MDSDocumentChangeManager

  private static class RegionPagePathCallback
    implements ContextCallback
  {
    RegionPagePathCallback(UIXRegion region)
    {
      _region = region;
    }

    public void invokeContextCallback(FacesContext facesContext,
                                      UIComponent uiComponent)
    {
      // This call happens between processBeginRegion and processEndRegion,
      // this is a safe place to get the view id from the region that we are
      // interested in. This region is the one enclosing uiComponent here.
      _regionViewId = _region.getRegionModel().getViewId(facesContext);
      if (_regionViewId != null)
      {
        //If this is reached, then the component to be customized is part
        //of pageTemplate definition file or region
        RequestContext reqContext = RequestContext.getCurrentInstance();
        if (reqContext != null)
        {
          //The viewId we get for regions may not be the physicalURI
          //convert it using pageResolver
          _regionViewId =
              reqContext.getPageResolver().getPhysicalURI(_regionViewId);
        }
      }
    }

    public String getRegionViewId()
    {
      return _regionViewId;
    }
    private String _regionViewId;
    private UIXRegion _region;
  }
}