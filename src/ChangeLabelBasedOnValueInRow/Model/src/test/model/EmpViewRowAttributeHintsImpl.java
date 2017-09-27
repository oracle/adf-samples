/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;


import java.text.SimpleDateFormat;

import java.util.Calendar;

import oracle.jbo.AttributeHints;
import oracle.jbo.LocaleContext;
import oracle.jbo.domain.Date;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowAttrHintsImpl;
import oracle.jbo.server.ViewRowImpl;

public class EmpViewRowAttributeHintsImpl extends ViewRowAttrHintsImpl  {
    protected EmpViewRowAttributeHintsImpl(AttributeDefImpl attr, ViewRowImpl viewRow)
    {
       super(attr,viewRow);
    }

// Only need to override getHint() for custom hints that aren't one
// of the standard hints. They are typesafe getter methods for all of
// the built-in hints, like the getLabel() I'm overriding below.

    @Override
    public String getHint(LocaleContext locale, String sHintName) {
        if ("mindate".equals(sHintName)) {
            Date hiredate = (Date)getViewRow().getAttribute("Hiredate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(hiredate.dateValue(c));
            c.add(Calendar.DATE,-3);
            String ret = sdf.format(c.getTime());
            return ret;
        }
        if ("maxdate".equals(sHintName)) {
            Date hiredate = (Date)getViewRow().getAttribute("Hiredate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(hiredate.dateValue(c));
            c.add(Calendar.DATE,3);
            String ret = sdf.format(c.getTime());
            return ret;
        }      
        System.out.println("### "+sHintName);
        return super.getHint(locale, sHintName);
    }
    @Override
    public String getLabel(LocaleContext locale) {
        if (getViewAttributeDef().getName().equals("Job")) {
          return "Job"+getViewRow().getAttribute("Sal");
        }
        return super.getLabel(locale);
    }
}
