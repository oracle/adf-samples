/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package model.common;

import oracle.jbo.common.JboResourceBundle;

public class DeptImplMsgBundle_it extends DeptImplMsgBundle {

    static final Object[][] sMessageStrings = 
    { { "Deptno_LABEL", "Numero Dipartimento" }, 
      { "Dept_Rule_0", "Nome del dipartimento non puo essere X se la localita e Y" }, 
      { "Dname_LABEL", "Nome Dipartimento" },
      { "Loc_LABEL", "Localita" },
      { "TransientNumberAttribute_LABEL","Campo Numerico con Maschera"}
    };

    /**This is the default constructor (do not remove)
     */
    public DeptImplMsgBundle_it() {
    }

    /**@return an array of key-value pairs.
     */
    public Object[][] getContents() {
        return super.getMergedArray(sMessageStrings, super.getContents());
    }
}
