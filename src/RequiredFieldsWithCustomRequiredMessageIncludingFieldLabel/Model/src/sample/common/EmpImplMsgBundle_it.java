/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package sample.common;

import oracle.jbo.common.JboResourceBundle;

public class EmpImplMsgBundle_it extends JboResourceBundle {
    static final Object[][] sMessageStrings = 
    { { "Ename_LABEL", "Nome Dipendente" },
      { "Deptno_LABEL", "Dipartimento" },
      { "Empno_LABEL", "Matricola" },
      { "Job_LABEL", "Descrizione Ruolo" }, 
      { "Hiredate_LABEL", "Data d'Assunzione" },
      { "Comm_LABEL", "Commissione" }, 
      { "Sal_LABEL", "Stipendio" },
      };

    /**This is the default constructor (do not remove)
     */
    public EmpImplMsgBundle_it() {
    }

    /**
     * @return an array of key-value pairs.
     */
    public Object[][] getContents() {
        return super.getMergedArray(sMessageStrings, super.getContents());
    }
}
