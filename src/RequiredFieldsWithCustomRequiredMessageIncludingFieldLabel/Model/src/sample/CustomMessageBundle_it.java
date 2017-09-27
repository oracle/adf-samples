/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package sample;

import java.util.ListResourceBundle;

import oracle.jbo.CSMessageBundle;

public class CustomMessageBundle_it extends CustomMessageBundle {
    private static final Object[][] sMessageStrings = 
        new String[][] { 
          { CSMessageBundle.EXC_VAL_ATTR_MANDATORY, 
            "Bisogna fornire un valore per {2}" }
          };

    /**Return String Identifiers and corresponding Messages in a two-dimensional array.
     */
    protected Object[][] getContents() {
        return sMessageStrings;
    }
}
