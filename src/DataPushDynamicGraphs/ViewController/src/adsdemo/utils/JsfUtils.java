package adsdemo.utils;

import java.io.IOException;

import java.util.Iterator;

import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import oracle.adf.controller.ControllerContext;
import oracle.adf.share.logging.ADFLogger;

public class JsfUtils
{
  private static final ADFLogger sLog = ADFLogger.createADFLogger(JsfUtils.class);

  public static void addError(String messageText)
  {
    FacesMessage message = new FacesMessage(messageText);
    message.setSeverity(FacesMessage.SEVERITY_ERROR);
    FacesContext.getCurrentInstance().addMessage(null, message);
  }

   public static void addMessage(String componentId, String messageText,
                                  FacesMessage.Severity severity)
  {
    FacesMessage message = new FacesMessage(messageText);
    message.setSeverity(severity);
    FacesContext.getCurrentInstance().addMessage(componentId, message);
  }

  public static void redirectToSelf() {
      FacesContext fctx = FacesContext.getCurrentInstance();
      ExternalContext ectx = fctx.getExternalContext();
      String viewId = fctx.getViewRoot().getViewId();
      ControllerContext controllerCtx = null;
      controllerCtx = ControllerContext.getInstance();
      String activityURL = controllerCtx.getGlobalViewActivityURL(viewId);
      try {
          ectx.redirect(activityURL);
          fctx.responseComplete();
      } catch (IOException e) {
          //Can't redirect
          sLog.severe("Error redirecting to self (" + activityURL + "): " +
                     e.getMessage());
          fctx.renderResponse();
      }
  }

  public static ValueExpression createValueExpression(String jsfExpression)
  {
    FacesContext fc = FacesContext.getCurrentInstance();
    ValueExpression ve =  fc.getApplication().getExpressionFactory().createValueExpression(fc.getELContext(),jsfExpression,Object.class);
    return ve;     
  }

  public static UIComponent findComponentInRoot(String id)
  {
    UIComponent ret = null;

    FacesContext context = FacesContext.getCurrentInstance();
    if (context != null)
    {
      UIComponent root = context.getViewRoot();
      ret = findComponent(root, id);
    }

    return ret;
  }

  /**
   * <p>Return the {@link UIComponent} (if any) with the specified
   * <code>id</code>, searching recursively starting at the specified
   * <code>base</code>, and examining the base component itself, followed
   * by examining all the base component's facets and children.
   * Unlike findComponent method of {@link UIComponentBase}, which
   * skips recursive scan each time it finds a {@link NamingContainer},
   * this method examines all components, regardless of their namespace
   * (assuming IDs are unique).
   *
   * @param base Base {@link UIComponent} from which to search
   * @param id Component identifier to be matched
   */
  public static UIComponent findComponent(UIComponent base, String id)
  {
    if (id==null || "".equals(id))
    {
      return null;
    }
    // Is the "base" component itself the match we are looking for?
    if (id.equals(base.getId()))
    {
      return base;
    }
    // check for direct child
    UIComponent result = base.findComponent(id);
    if (result!=null)
    {
      return result;
    }

    // Search through our facets and children
    UIComponent kid = null;
    Iterator kids = base.getFacetsAndChildren();
    while (kids.hasNext() && (result == null))
    {
      kid = (UIComponent) kids.next();
      if (id.equals(kid.getId()))
      {
        result = kid;
        break;
      }
      result = findComponent(kid, id);
      if (result != null)
      {
        break;
      }
    }
    return result;
  }
  
  
}
