/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;

import java.util.Map;

import oracle.adf.model.bean.DCDataRow;

import oracle.jbo.ExprValueSupplier;
import oracle.jbo.JboReservedVarNames;
import oracle.jbo.Row;
import oracle.jbo.StructureDef;
import oracle.jbo.ViewObject;
import oracle.jbo.common.ViewCriteriaRowImpl;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;

public class SessionHelper {
    public static Map userData(ExprValueSupplier valSupplier) {
        if (valSupplier instanceof Row) {
            return userDataFromRow((Row)valSupplier);
        } else {
            StructureDef sd =
                (StructureDef)valSupplier.getExprVarVal(JboReservedVarNames.RESERVED_VAR_STRUCTURE_DEF);
            if (sd instanceof ViewObject) {
                return userData((ViewObject)sd);
            } else if (sd instanceof Row) {
                return userDataFromRow((Row)sd);
            } else {
                System.out.println("Didn't handle case when valueSupplier was a " +
                                   sd.getClass().getName());
            }
        }
        return null;
    }

    public static Map userData(ViewObject vo) {
        return ((ViewObjectImpl)vo).getRootApplicationModule().getSession().getUserData();
    }

    private static Map userDataFromRow(Row row) {
        ViewObject vo = null;
        if (row instanceof DCDataRow) {
            Object dp = ((DCDataRow)row).getDataProvider();
            if (dp instanceof ViewCriteriaRowImpl) {
                vo = ((ViewCriteriaRowImpl)dp).getViewCriteria().getViewObject();
            }
        } else {
            vo = ((ViewRowImpl)row).getViewObject();
        }
        if (vo != null) {
            return userData(vo);
        }
        return null;
    }
}
