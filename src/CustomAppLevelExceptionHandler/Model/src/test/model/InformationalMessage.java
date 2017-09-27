/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;

import oracle.jbo.JboWarning;

public class InformationalMessage extends JboWarning {
    public static final String INFO_PROPERTY = "$InformationalMessage$INFO";
    public InformationalMessage(String message)
    {
        super(message, "", new Object[]{INFO_PROPERTY});
    }
}
