/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.tests;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;
import oracle.jbo.client.JboUtil;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.*;

import test.model.common.MailService;

public class Test1 {
    public static void main(String[] args) {
        String amDef = "test.model.MailService";
        String config = "MailServiceLocal";
        JUnitFixtureLoginEnvInfoProvider e = new JUnitFixtureLoginEnvInfoProvider("userone","welcome1");
        MailService am = (MailService)Configuration.createRootApplicationModule(amDef, config,e);
        am.createNewMailAndPrimaryRecipient();
         // Work with your appmodule and view object here
        Configuration.releaseRootApplicationModule(am, true);
    }
}
