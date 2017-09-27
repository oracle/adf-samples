/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.util;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.controller.faces.context.FacesPageLifecycleContext;
import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCExecutableBinding;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.BindingContainer;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.uicli.binding.JUMethodIteratorDef;

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
    FacesPageLifecycleContext ctx = 
      (FacesPageLifecycleContext)event.getLifecycleContext();
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
    FacesPageLifecycleContext ctx = 
      (FacesPageLifecycleContext)event.getLifecycleContext();
    int phaseId = event.getPhaseId();
    if (phaseId == Lifecycle.PREPARE_RENDER_ID) {
      bc = ctx.getBindingContainer();
      onPagePreRender();
      bc = null;
    } else if (phaseId == Lifecycle.PREPARE_MODEL_ID) {
      bc = ctx.getBindingContainer();
      afterPrepareModel();
      bc = null;
    }
  }

  /**
   * Subclass this method to execute code during page load, before
   * the ADF prepareModel phase
   */
  public void onPageLoad() {
    // Subclasses can override this.
  }

  /**
   * Subclass this method to execute code after the ADF prepareModel phase
   */
  protected void afterPrepareModel() {
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
    } else {
      return (BindingContainer)EL.get("#{bindings}");
    }
  }
  /**
   * Get the ADF Binding Container cast as a DCBindingContainer
   * 
   * Since the binding container can change mid-request due to page navigation
   * this method insures that we reference the correct binding container
   * when code is invoked in a PagePhaseListener.
   * 
   * @return DCBindingContainer object
   */
  protected DCBindingContainer getDCBindingContainer() {
    if (bc != null) {
      return (DCBindingContainer)bc;
    } else {
      return (DCBindingContainer)EL.get("#{bindings}");
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
   * Execute an operation binding by name.
   * @param name
   * @return
   */
  protected Object executeOperationBinding(String name) {
    return ((DCBindingContainer)getBindingContainer()).getOperationBinding(name).execute();
  }

  /**
   * (Re)execute a method iterator binding's query return the new result
   * from the underlying, associated method action binding.
   * 
   * Since ADF design time does not allow defining an Execute operation
   * on a method iterator whose return type is a single structured object
   * (i.e. rather than a collection type, where it is supported), this method
   * can be used by a backing bean to rebuild the row set used by the
   * ADFM data binding layer to display the updated databound results of
   * re-executing such a method.
   * 
   * @param iterBindingName Method iterator binding name
   * @return 
   */
  protected Object executeQueryForMethodIterator(String iterBindingName) {
    DCIteratorBinding iter = getIteratorBinding(iterBindingName);
    if (!isMethodIterator(iter)) {
      throw new RuntimeException(iterBindingName+" not found or not a method iterator");
    }
    iter.executeQuery();
    String relatedActionBindingName = ((JUMethodIteratorDef)iter.getDef()).getActionBindingName();
    return getBindingContainer().getOperationBinding(relatedActionBindingName).getResult();
  }

  /**
   * Add a JSF message to the FacesContext
   * @param msg
   */
  protected void addJSFMessage(String msg) {
    FacesContext fc = FacesContext.getCurrentInstance();
    FacesMessage fm = new FacesMessage(msg);
    fc.addMessage(null, fm);
  }

  /**
   * Set the current row in the iterator whose iterator binding name is 
   * passed in to be the row whose primary key has the value passed in.
   * If such a row does not exist in the iterator's RowSet, then do nothing.
   * 
   * @param iterName
   * @param keyValue
   */
  protected void setCurrentRowWithKeyValueIfItExistsInIterator(String iterName, 
                                                               Object keyValue) {
    DCIteratorBinding iterBinding = getIteratorBinding(iterName);
    Key keyToFind = new Key(new Object[] { keyValue });
    RowSetIterator rsi = iterBinding.getRowSetIterator();
    Row[] rowsFound = rsi.findByKey(keyToFind, 1);
    if (rowsFound != null && rowsFound.length > 0) {
      rsi.setCurrentRow(rowsFound[0]);
    }
  }  
  protected void clearVariable(String var) {
    DCIteratorBinding variables = getIteratorBinding("variables");
    Row variablesRow = variables.getCurrentRow();
    variablesRow.setAttribute(var,null);
  }
  private boolean isMethodIterator(DCIteratorBinding iter) {
    return iter != null && iter.getDef() instanceof JUMethodIteratorDef;
  }
}
