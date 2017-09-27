/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;

import oracle.jbo.AttrValException;
import oracle.jbo.AttributeDef;
import oracle.jbo.CSMessageBundle;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaItem;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.common.JboCompOper;
import oracle.jbo.common.MetaObjectBase;
import oracle.jbo.rules.JboValidatorContext;
import oracle.jbo.server.ViewObjectImpl;

public class CustomViewObjectImpl extends ViewObjectImpl {
    /*
     * This relies on the fact that both the <af:query> form processQuery
     * logic, as well as the Servicer Interface find operation both
     * invoke the executeQuery() method explicitly after applying view
     * criteria item values.
     */
    @Override
    public void executeQuery() {
        performCustomViewCriteriaValidation();
        super.executeQuery();
    }    
    private void performCustomViewCriteriaValidation() {
        ViewCriteria[] vcs =
            getApplyViewCriterias(ViewCriteria.CRITERIA_MODE_QUERY);
        if (vcs != null & vcs.length > 0) {
            for (ViewCriteria v : vcs) {
                ViewCriteriaRow vcr = (ViewCriteriaRow)v.first();
                if (vcr != null) {
                    ViewCriteriaItem[] vcis =
                        vcr.getCriteriaItemArray();
                    if (vcis != null && vcis.length > 0) {
                        for (int j = 0; j < vcis.length; j++) {
                            ViewCriteriaItem vci = vcis[j];
                            if (vci != null &&
                                ViewCriteriaItem.VCITEM_SELECTIVELY_REQUIRED ==
                                vci.getRequired() &&
                                vci.getOperator().equals(JboCompOper.OPER_STARTS_WITH)) {
                                Object o = vci.getValue();
                                if (o != null && o instanceof String) {
                                    String vciValue = (String)o;
                                    String trimmedValue =
                                        vciValue.trim();
                                    if (trimmedValue.startsWith("_") ||
                                        trimmedValue.startsWith("%")) {
                                        AttrValException attrEx =
                                            new AttrValException(JboValidatorContext.TYP_VIEW_CRITERIA,
                                                                 CustomViewObjectImplMessageBundle.class,
                                                                 CustomViewObjectImplMessageBundle.CANNOT_START_WITH_WILDCARD,
                                                                 vci.getViewCriteria().getName(),
                                                                 vci.getName());
                                        attrEx.setNeedsEntityToVOMapping(false);
                                        throw attrEx;

                                    }
                                    trimmedValue =
                                            trimmedValue.replace("_",
                                                                 "");
                                    trimmedValue =
                                            trimmedValue.replace("%",
                                                                 "");
                                    if (trimmedValue.length() < 3) {
                                        AttrValException attrEx =
                                            new AttrValException(JboValidatorContext.TYP_VIEW_CRITERIA,
                                                                 CustomViewObjectImplMessageBundle.class,
                                                                 CustomViewObjectImplMessageBundle.MUST_HAVE_THREE_LEADING_NON_WILDCARD_CHARS,
                                                                 vci.getViewCriteria().getName(),
                                                                 vci.getName());
                                        attrEx.setNeedsEntityToVOMapping(false);
                                        throw attrEx;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

 
}
