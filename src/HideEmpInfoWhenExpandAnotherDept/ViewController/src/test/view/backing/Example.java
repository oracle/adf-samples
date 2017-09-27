/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import java.util.Map;

import java.util.Set;

import oracle.adf.view.faces.component.core.data.CoreTree;
import oracle.adf.view.faces.component.core.layout.CorePanelBox;
import oracle.adf.view.faces.component.core.layout.CorePanelForm;
import oracle.adf.view.faces.event.DisclosureEvent;
import oracle.adf.view.faces.model.PathSet;

public class Example {
    private Map treeLevel;
    private CorePanelBox employeeInfoPanelBox;

    /**
     * Setter for JSF Managed Property "treeLevel".
     *
     * The treeLevel map is configured in the faces-config.xml file
     * to have the following (viewdef,facetname) key/value pairs:
     *
     * 1. (  key="test.model.DeptView",
     *     value="DeptNode")
     * 2. (  key="test.model.EmpView",
     *     value="EmpNode")
     *
     * @param treeLevel map of (viewdef,facetname) pairs
     */
    public void setTreeLevel(Map treeLevel) {
      this.treeLevel = treeLevel;
    }
    
    /**
     * Getter for JSF Managed Property "treeLevel".
     *
     * This property defines a Map of (key,value) pairs used by the
     * af:switcher tag in the Example page to switch the facet to
     * display based on the fully-qualified name of the view object
     * whose data is represented by a given tree node. The af:switcher
     * tag has its facetName property set to the following EL expression:
     * 
     *    #{Example.treeLevel[node.hierType.viewDefName]}
     * 
     * @return map of (viewdef,facetname) pairs
     */
    public Map getTreeLevel() {
      return treeLevel;
    }    

    public Example() {
    }

    /*
     * Hide the employees info panel box when a department node is expanded
     */
    public void onTreeNodeDisclosed(DisclosureEvent disclosureEvent) {
      CoreTree ct=(CoreTree)disclosureEvent.getSource();
      Object rwKey =ct.getRowKey();
        if(rwKey.toString().indexOf(",")==-1){
            PathSet ps = ct.getTreeState();
            Set ks = ps.getKeySet();
            ks.clear();
            ks.add(rwKey);
            getEmployeeInfoPanelBox().setRendered(false);
        }      
        // Add event code here...
    }

    /*
     * Show the employees info panel box when an employee link is clicked
     */
    public String onClickEmployeeCommandLink() {
        getEmployeeInfoPanelBox().setRendered(true);
        return null;
    }

    public void setEmployeeInfoPanelBox(CorePanelBox employeeInfoPanelBox) {
        this.employeeInfoPanelBox = employeeInfoPanelBox;
    }

    public CorePanelBox getEmployeeInfoPanelBox() {
        return employeeInfoPanelBox;
    }
}
