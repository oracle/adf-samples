package adsdemo.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.internal.ViewPortContextFwk;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCUtil;
import oracle.adf.view.rich.change.MDSDocumentChangeManager;
import oracle.adf.view.rich.component.fragment.UIXRegion;
import oracle.adf.view.rich.dt.Page;

import org.apache.myfaces.trinidad.change.AddChildDocumentChange;
import org.apache.myfaces.trinidad.change.AttributeComponentChange;
import org.apache.myfaces.trinidad.change.ComponentChange;
import org.apache.myfaces.trinidad.change.DocumentChange;
import org.apache.myfaces.trinidad.context.RequestContext;

import org.w3c.dom.DocumentFragment;

public class MdsUtils
{

  public static String getPhysicalPagePath(UIComponent component)
  {
    String pagePath = getPagePath(FacesContext.getCurrentInstance(), component);
    String pageURI = getDocument(FacesContext.getCurrentInstance(), pagePath);
    return pageURI;
  }

  public static Page getPageObject(String pagePath)
    {
      // first get the binding context
      FacesContext ftx = FacesContext.getCurrentInstance();
      ExternalContext extContext = ftx.getExternalContext();
      HttpServletRequest servletRequest = 
        (HttpServletRequest) extContext.getRequest();
      BindingContext bindingContext = 
        DCUtil.getBindingContext(servletRequest);

      // lastly, return the instance of the design-time page.
      return Page.getInstance(bindingContext, pagePath);

    }

  public static String getPagePath(FacesContext facesContext, 
                                       UIComponent component)
    {
      boolean facetRef = false;
      UIComponent currAncestor = component.getParent();

      while (currAncestor != null)
      {
        if (currAncestor instanceof UIXRegion)
        {
          UIXRegion region = (UIXRegion) currAncestor;

          return RegionUtility.getRegionURI(facesContext, component, region);
        }
        //else if (currAncestor instanceof UIXPageTemplate)
        //{
        //  if (!facetRef)
        //  {
        //    //If it reaches this loop, The component is part of pageTemplate
        //    // definition document. get the doc and apply the change           
        //    return ((UIXPageTemplate) currAncestor).getViewId();
        //  }
        //  else
        //  {
        //    // This is made false to add the id's in the path to the list
        //    // to make sure that we parse the correct path while parsing the
        //    //XML Document in _getComponentNode() method
        //    facetRef = false;
        //  }
        // }
        // else if (currAncestor instanceof ComponentRefTag.FacetWrapper)
        // {
        //  //If this is reached, the surrounding pageTemplate def doesn't 
        //  // contain the required component but the component is child of
        //  //pageTemplate component so skip the id's found in between(which
        //  // are part of pageTemplate def document) so we no more need to parse
        //  // the pageTemplate def file.
        //  facetRef = true;
        //}

        currAncestor = currAncestor.getParent();
      }
      return null;
    }

  public static String getDocument(FacesContext context, String pagePath)
   {
     if (pagePath == null)
     {
       pagePath = context.getViewRoot().getViewId();
       //Bug 5745464 MDS-00013 FROM MDSDOCUMENTCHANGEMANAGER WHEN 
       //CLICKING A TAB INSIDE A REGION
       ControllerContext ctrl =  ControllerContext.getInstance();
       if (ctrl != null)
       {
         ViewPortContextFwk root = (ViewPortContextFwk) ctrl.getCurrentRootViewPort();
         if (root != null)
         {
           pagePath = root.getPhysicalURI(pagePath);           
         }
       }
       else
       {
         //Bug 5692317 view Id may not be the physical URI of the page       
         RequestContext reqContext = RequestContext.getCurrentInstance();
         if (reqContext != null)
         {
           pagePath = reqContext.getPageResolver().getPhysicalURI(pagePath);
         }
       }
     }

     // Bug 6209943 - em:blk error updatinga component attribute fora file 
     // residing inweb context root
     // pagePath need not be the absolute path
     if (pagePath != null && (!pagePath.startsWith("/")))
     {
       pagePath = "/" + pagePath;
     }
     return pagePath;
   }

  public static void saveToMds(DocumentFragment df, UIComponent parent)
  {
    //    Content updatableContent =
    //      ContentFactory.getInstance().getContent(pageURI);
    //    AbstractUsageImpl usage =
    //      (AbstractUsageImpl) updatableContent.createUsage("cb8",
    //                                                       "region",
    //                                                       "http://xmlns.oracle.com/adf/faces/rich");
    // Get the taskflow fragment
    //    DocumentFragment docFrag = usage.getFragment();
    //    Document doc = docFrag.getOwnerDocument();

    try
    {
      // Get the ID of the component we'll be adding just before
      String insertBeforeId = null;
      try
      {
    //     insertBeforeId = container.getChildren().get(0).getId();
      }
      catch (Exception e)
      {
        // Usually ArrayIndexOutOfBounds, no need to log even otherwise.
      }

      // Get the change manager, e.g. MDS
      // dont use only return doc change manager in page composer edit mode ...
      //      ChangeManager apm =
      //        RequestContext.getCurrentInstance().getChangeManager();



      // Add an "add child" document change, i.e. inserting the fragment
      DocumentChange change = null;
      if (insertBeforeId != null)
      {
        change = new AddChildDocumentChange(insertBeforeId, df);
      }
      else
      {
        change = new AddChildDocumentChange(df);
      }

      saveToMds(change, parent);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void saveToMds(AttributeComponentChange change, UIComponent component)
  {
    MDSDocumentChangeManager mdsChangeManager =
      new MDSDocumentChangeManager();

    UIComponent container = component;
    mdsChangeManager.addComponentChange(FacesContext.getCurrentInstance(),
                                       container, change);
    RequestContext.getCurrentInstance().addPartialTarget(component.getParent());
  }

  public static void saveToMds(DocumentChange change, UIComponent parent)
  {
    MDSDocumentChangeManager mdsChangeManager =
      new MDSDocumentChangeManager();

    UIComponent container = parent;
    mdsChangeManager.addDocumentChange(FacesContext.getCurrentInstance(),
                                       container, change);
    RequestContext.getCurrentInstance().addPartialTarget(container.getParent());
  }
}
