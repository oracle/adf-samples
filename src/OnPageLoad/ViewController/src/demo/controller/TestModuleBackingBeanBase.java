/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.controller;
import demo.model.common.TestModule;
import demo.view.util.JSFUtils;
import oracle.adf.controller.faces.context.FacesPageLifecycleContext;
import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.binding.BindingContainer;
import oracle.jbo.ApplicationModule;
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
public class TestModuleBackingBeanBase implements PagePhaseListener {
    // Invoke the onPageLoad() method before the prepareModel phase
    private BindingContainer bc = null;
    /**
     * Before the ADF page lifecycle's prepareModel phase, invoke a
     * custom onPageLoad() method. Subclasses override the onPageLoad()
     * to do something interesting during the 
     * @param event
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
     * to do something interesting during the 
     * @param event
     */    
    public void afterPhase(PagePhaseEvent event) {
      FacesPageLifecycleContext ctx = (FacesPageLifecycleContext)event.getLifecycleContext();
      if (event.getPhaseId() == Lifecycle.PREPARE_RENDER_ID) {
        bc = ctx.getBindingContainer();
        onPagePreRender();
        bc = null;
      }    }
    public void onPageLoad() {
      // Subclasses can override this.
    }
    public void onPagePreRender() {
      // Subclasses can override this.
    }
    protected boolean isPostback() {
      return Boolean.TRUE.equals(JSFUtils.resolveExpression("#{adfFacesContext.postback}"));
    }
    protected TestModule getTestModule() {
      return (TestModule)getApplicationModuleForDataControl("TestModuleDataControl");
    }
    protected ApplicationModule getApplicationModuleForDataControl(String name) {
      return (ApplicationModule)JSFUtils.resolveExpression("#{data."+name+".dataProvider}");
    }
    protected BindingContainer getBindingContainer() {
      return bc != null ? bc :(BindingContainer)JSFUtils.resolveExpression("#{bindings}");
    }
    protected DCIteratorBinding getIteratorBinding(String name) {
      return ((DCBindingContainer)getBindingContainer()).findIteratorBinding(name);
    }
  }
