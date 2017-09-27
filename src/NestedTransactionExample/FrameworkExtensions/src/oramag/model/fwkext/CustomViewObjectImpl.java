/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oramag.model.fwkext;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.AttributeDef;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaItem;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.common.JboCompOper;
import oracle.jbo.server.ViewObjectImpl;
/*
 * This view object framework extension class works around Bug 7639222 where
 * the quick query view criteria incorrectly uses the "LIKE" operator by default
 * instead of the "StartsWith" operator as users would expect.
 * 
 * The "EmpView" and "DeptView" view objects in this workspace are declaratively
 * configured to use this class as its base view object class at runtime.
 * 
 * See sections "33.1 Globally Extending ADF Business Components Functionality"
 * and "33.2 Creating a Layer of Framework Extensions" in the Fusion Developer's
 * Guide for Oracle Application Development Framework for more information
 * on this powerful framework extension capability.
 */
public class CustomViewObjectImpl extends ViewObjectImpl {
    @Override
    public void applyViewCriteria(ViewCriteria vc, boolean bAppend) {
        if (vc != null) {
            if (ViewCriteriaManager.QUICKSEARCH_VIEW_CRITERIA_NAME.equals(vc.getName())) {
                /* Workaround bug# 7660871 vvvvvvvvvvvvv */
                vc.setName(null);
                /* Workaround bug# 7660871 ^^^^^^^^^^^^^ */                
                ViewCriteriaRow vcr = (ViewCriteriaRow)vc.first();
                if (vcr != null) {
                    for (AttributeDef attr : vcr.getStructureDef().getAttributeDefs()) {
                        if (attr.isQueriable() &&
                            attr.getJavaType().equals(String.class)) {
                            ViewCriteriaItem vci = vcr.getCriteriaItem(attr.getIndex());
                            if (vci != null &&
                                (vci.getOperator() == null ||
                                 vci.getOperator().equals(JboCompOper.OPER_LIKE))) {
                                vci.setOperator(JboCompOper.OPER_STARTS_WITH);
                            }
                        }
                    }
                }
            }
        }
        super.applyViewCriteria(vc, bAppend);
    }
}
