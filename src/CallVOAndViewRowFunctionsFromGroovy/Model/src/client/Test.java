/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package client;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.*;

public class Test {
    public static void main(String[] args) {
        String amDef = "test.AppModule";
        String config = "AppModuleLocal";
        ApplicationModule am = Configuration.createRootApplicationModule(amDef, config);
        ViewObject vo = am.findViewObject("EmpView1");
        Row r = vo.first();
        System.out.println(r.getAttribute("ValueUsingVOFunction")+","+r.getAttribute("ValueUsingVORowFunction"));
         // Work with your appmodule and view object here
        Configuration.releaseRootApplicationModule(am, true);
    }

}
