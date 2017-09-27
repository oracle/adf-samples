/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oramag.model;
import oracle.jbo.AttributeDef;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaItem;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.common.JboCompOper;
import oracle.jbo.server.ViewObjectImpl;
public class WorkaroundForBug7639222ViewObjectImpl extends ViewObjectImpl {
    /*
     * This overridden framework method forces the quick search view criteria
     * to use the "STARTS_WITH" operator instead of the "LIKE" operator.
     */
    @Override
    public void applyViewCriteria(ViewCriteria vc, boolean bAppend) {
        if (vc != null) {
            if (ViewCriteriaManager.QUICKSEARCH_VIEW_CRITERIA_NAME.equals(vc.getName())) {
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
