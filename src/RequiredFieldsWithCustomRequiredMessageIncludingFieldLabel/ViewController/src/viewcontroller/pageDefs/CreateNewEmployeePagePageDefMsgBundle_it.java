/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package viewcontroller.pageDefs;

import oracle.jbo.common.JboResourceBundle;

public class CreateNewEmployeePagePageDefMsgBundle_it extends JboResourceBundle {
    static final Object[][] sMessageStrings = 
    { { "Deptno_null", "<Scegli Dipartmento>" } };

    /**This is the default constructor (do not remove)
     */
    public CreateNewEmployeePagePageDefMsgBundle_it() {
    }

    /**
     * @return an array of key-value pairs.
     */
    public Object[][] getContents() {
        return super.getMergedArray(sMessageStrings, super.getContents());
    }
}
