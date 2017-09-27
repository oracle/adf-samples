/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaItem;
import oracle.jbo.domain.TypeFactory;
import oracle.jbo.server.ViewObjectImpl;
public class CustomViewObjectImpl extends ViewObjectImpl {
    @Override
    public String getCriteriaItemClause(ViewCriteriaItem vci) {
        if (vci.getViewCriteria().getRootViewCriteria().isCriteriaForRowMatch()) {
            return getCriteriaItemClauseForCache(vci);
        }
        else {
            return getCriteriaItemClauseForDatabaseUse(vci);
        }
    }
    protected String getCriteriaItemClauseForDatabaseUse(ViewCriteriaItem vci) {
      return null;        
    }
    protected String getCriteriaItemClauseForCache(ViewCriteriaItem vci) {
        return null;        
    }
    protected Object getViewCriteriaItemValueAsAttributeType(ViewCriteriaItem vci) {
    /*
     * View Criteria Items get treated as strings so convert to the actual
     * type of the attribute
     */
        try {
            return TypeFactory.getInstance(vci.getAttributeDef().getJavaType(),
                                           vci.getValue(0));
        } catch (oracle.jbo.domain.DataCreationException e) {
            return vci.getValue(0);
        }
    }    
    
}
