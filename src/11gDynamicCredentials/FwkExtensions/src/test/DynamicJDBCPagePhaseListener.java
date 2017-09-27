/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.controller.v2.context.LifecycleContext;
import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;
import oracle.adf.model.bc4j.DCJboDataControl;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCExecutableBinding;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.DataControl;

import oracle.jbo.ApplicationModule;
import oracle.jbo.uicli.binding.JUControlBinding;
import oracle.jbo.uicli.binding.JUCtrlActionBinding;
/**
 * This ADF Page Phase Listener class causes the data controls in the current
 * page definition to be referenced (and thus checked out from the AM pool on
 * first reference) before the prepareModel phase of the ADF page lifecycle.
 * 
 * The page phase listener is configured in a given web project by
 * mentioning it in a META-INF/adf-settings.xml file that looks like this:
 * 
 * <adf-settings xmlns="http://xmlns.oracle.com/adf/settings">
 *   <adfc-controller-config xmlns="http://xmlns.oracle.com/adf/controller/config">
 *     <lifecycle>
 *       <phase-listener>
 *         <listener-id>DynamicJDBCPagePhaseListener</listener-id>
 *         <class>test.DynamicJDBCPagePhaseListener</class>
 *       </phase-listener>
 *     </lifecycle>
 *   </adfc-controller-config>
 * </adf-settings>
 * 
 */
public class DynamicJDBCPagePhaseListener implements PagePhaseListener {
    public DynamicJDBCPagePhaseListener() {
    }

    public void afterPhase(PagePhaseEvent event) {
    }

    private HttpSession getHttpSession(PagePhaseEvent event) {
        return ((HttpServletRequest)event.getLifecycleContext().getEnvironment().getRequest()).getSession(true);
    }
    public void beforePhase(PagePhaseEvent event) {
        if (event.getPhaseId() == Lifecycle.PREPARE_MODEL_ID) {   
            // If the user is not currently logged in
            if (!DynamicJDBCLoginHelper.isLoggedIn(getHttpSession(event))) {
              DCBindingContainer bc = (DCBindingContainer)event.getLifecycleContext().getBindingContainer();
              // Force the Data Control to be referenced before the prepareModel phase
              // to cause the possible JDBC connection failure to be signalled now instead of
              // during the page rendering.
              List<DCJboDataControl> bcdcs = getADFBCDataControlsList(bc);          
            }
        }
    }
    
    /*
     * Return a list of ADFBC data controls used by this page.
     */
    private List<DCJboDataControl> getADFBCDataControlsList(DCBindingContainer bc) {
      List<DCJboDataControl> bcdcs = new ArrayList<DCJboDataControl>();
      List<JUControlBinding> ctrlBindings = (List<JUControlBinding>)bc.getControlBindings();
      if (ctrlBindings != null) {
        for (JUControlBinding ctrlBinding: ctrlBindings) {
          DCIteratorBinding iter = ctrlBinding.getIteratorBinding();
          DCDataControl dc = null;
          if (iter != null) {
            dc = iter.getDataControl();
          }
          else if (ctrlBinding instanceof JUCtrlActionBinding) {
            dc = ((JUCtrlActionBinding)ctrlBinding).getDataControl();
          }
          if (dc != null && dc instanceof DCJboDataControl && 
              !bcdcs.contains(dc)) {
            DCJboDataControl bcdc = (DCJboDataControl)dc;
            bcdcs.add(bcdc);
          }
        }
      }
      List<DCExecutableBinding> exeBindings = (List<DCExecutableBinding>)bc.getIterBindingList();
      if (exeBindings != null) {
        for (DCExecutableBinding exeBinding: exeBindings) {
          DataControl dc = null;        
          if (exeBinding instanceof DCIteratorBinding) {
            dc = ((DCIteratorBinding)exeBinding).getDataControl();
          }
          if (dc != null && dc instanceof DCJboDataControl && 
              !bcdcs.contains(dc)) {
            DCJboDataControl bcdc = (DCJboDataControl)dc;
            bcdcs.add(bcdc);
          }
        }
      }
      return bcdcs;
    }    
}
