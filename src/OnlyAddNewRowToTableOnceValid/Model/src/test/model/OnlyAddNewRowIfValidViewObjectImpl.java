/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;

import oracle.jbo.server.Entity;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewDefImpl;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;

public class OnlyAddNewRowIfValidViewObjectImpl extends ViewObjectImpl {
    public static final String ONLY_ADD_NEW_ROW_IF_VALID = "OnlyAddNewRowIfValid";
    public void setOnlyAddNewRowIfValid(boolean b) {
        setProperty(ONLY_ADD_NEW_ROW_IF_VALID,Boolean.toString(b));
    }
    public boolean isOnlyAddNewRowIfValid() {
        return Boolean.TRUE.equals(getProperty(ONLY_ADD_NEW_ROW_IF_VALID));
    }
    @Override
    protected boolean rowQualifies(ViewRowImpl row) {
        if ("true".equals(getProperty(ONLY_ADD_NEW_ROW_IF_VALID))) {
            ViewRowImpl vri = (ViewRowImpl)row;
            EntityImpl primaryEntity = vri.getEntity(0);
            if (primaryEntity != null &&
                primaryEntity.getEntityState() == Entity.STATUS_NEW &&
                !primaryEntity.isValid()) {
                return false;
            }            
        }
        return super.rowQualifies(row);
    }
}
