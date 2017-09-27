/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.view.util;

import oracle.adf.controller.faces.context.FacesPageLifecycleContext;
import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
/**
 * This is a base class for JSF backing beans that want to have an
 * onPageLoad() and/or onPagePreRender() method invoked on them during
 * the ADF page lifecycle.
 * 
 * To use it, there are two steps to follow:
 *   (1) Have your JSF backing bean extend this class
 *   (2) Update the ControllerClass property of the page's pageDefinition
 *       to contain the EL Expression #{YourBackingBeanName} so that
 *       the ADF page lifecycle using your backing bean (which inherits
 *       an implementation of ADF's PagePhaseController interface by
 *       extending this class) as the page phase listener for the page
 *       in question. You can set the ControllerClass property in the
 *       Property Inspector by selecting the root pageDefinition node
 *       in the Structure Window for a pageDefinition)
 */
public class OnPageLoadBackingBeanBase implements PagePhaseListener {
  // Invoke the onPageLoad() method before the prepareModel phase
  private BindingContainer bc = null;
  /**
   * Before the ADF page lifecycle's prepareModel phase, invoke a
   * custom onPageLoad() method. Subclasses override the onPageLoad()
   * to do something interesting during this event.
   * @param event page phase event
   */
  public void beforePhase(PagePhaseEvent event) {
    FacesPageLifecycleContext ctx = (FacesPageLifecycleContext)event.getLifecycleContext();
    if (event.getPhaseId() == Lifecycle.PREPARE_MODEL_ID) {
      bc = ctx.getBindingContainer();
      onPageLoad();
      bc = null;
    }
  }
  /**
   * After the ADF page lifecycle's prepareRender phase, invoke a
   * custom onPagePreRender() method. Subclasses override the onPagePreRender()
   * to do something interesting during this event.
   * @param event page phase event
   */    
  public void afterPhase(PagePhaseEvent event) {
    FacesPageLifecycleContext ctx = (FacesPageLifecycleContext)event.getLifecycleContext();
    if (event.getPhaseId() == Lifecycle.PREPARE_RENDER_ID) {
      bc = ctx.getBindingContainer();
      onPagePreRender();
      bc = null;
    }    
  }
  /**
   * Subclass this method to execute code during page load.
   */
  public void onPageLoad() {
    // Subclasses can override this.
  }
  /**
   * Subclass this method to execute code during page pre-render.
   */  
  public void onPagePreRender() {
    // Subclasses can override this.
  }
  /**
   * Determine whether the current page request represents a postback.
   * @return true if current page request represents a postback
   */
  protected boolean isPostback() {
    return EL.test("#{adfFacesContext.postback}");
  }
  /**
   * Get the ADF Binding Container.
   * 
   * Since the binding container can change mid-request due to page navigation
   * this method insures that we reference the correct binding container
   * when code is invoked in a PagePhaseListener.
   * 
   * @return BindingContainer object
   */
  protected BindingContainer getBindingContainer() {
    if (bc != null) {
      return bc;
    }
    else {
      return (BindingContainer)EL.get("#{bindings}");
    }
  }
  /**
   * Get an ADF iterator binding in the current binding container by name.
   * @param name iterator binding name
   * @return DCIteratorBinding object
   */
  protected DCIteratorBinding getIteratorBinding(String name) {
    return ((DCBindingContainer)getBindingContainer()).findIteratorBinding(name);
  }
  /**
   * Get a control binding that has an input value as an AttributeBinding
   * @param name
   * @return attribute binding
   */
  protected AttributeBinding getAttributeBinding(String name) {
    return (AttributeBinding)getBindingContainer().getControlBinding(name);
  }
}
