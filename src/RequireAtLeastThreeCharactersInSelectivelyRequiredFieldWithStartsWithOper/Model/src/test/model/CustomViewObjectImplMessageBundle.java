/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;

import oracle.jbo.common.JboResourceBundle;

public class CustomViewObjectImplMessageBundle extends JboResourceBundle {
    public static final String CANNOT_START_WITH_WILDCARD = "21000";
    public static final String MUST_HAVE_THREE_LEADING_NON_WILDCARD_CHARS = "21001";
    
    static final Object[][] sMessageStrings = 
    {
        { CANNOT_START_WITH_WILDCARD,
          "Please provide a value for {2} that does not start with a wildcard character" },
        { MUST_HAVE_THREE_LEADING_NON_WILDCARD_CHARS,
          "Please provide a value for {2} that begins with at least three non-wildcard characters" },
    };


    /**@return an array of key-value pairs.
     */
    public Object[][] getContents() {
      return super.getMergedArray(sMessageStrings, super.getContents());
    }
}
