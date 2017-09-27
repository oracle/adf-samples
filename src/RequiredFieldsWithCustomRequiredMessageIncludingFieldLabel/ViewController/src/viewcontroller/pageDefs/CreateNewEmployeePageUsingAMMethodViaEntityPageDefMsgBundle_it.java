/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package viewcontroller.pageDefs;

import oracle.jbo.common.JboResourceBundle;

public class CreateNewEmployeePageUsingAMMethodViaEntityPageDefMsgBundle_it extends JboResourceBundle {


    static final Object[][] sMessageStrings = 
    { { "createNewEmployee_Hiredate_LABEL", "Data d'Assunzione" }, 
      { "createNewEmployee_Sal_LABEL", "Stipendio" }, 
      { "createNewEmployee_Deptno_LABEL", "Dipartimento" }, 
      { "createNewEmployee_Ename_LABEL", "Nome Dipendente" }, 
      { "createNewEmployee_Job_LABEL", "Descrizione Ruolo" }, 
      { "createNewEmployee_Comm_LABEL", "Commissione" },
       {"Deptno_null","<Scegli Dipartmento>"} };

    /**This is the default constructor (do not remove)
     */
    public CreateNewEmployeePageUsingAMMethodViaEntityPageDefMsgBundle_it() {
    }

    /**
     * @return an array of key-value pairs.
     */
    public Object[][] getContents() {
        return super.getMergedArray(sMessageStrings, super.getContents());
    }
}
