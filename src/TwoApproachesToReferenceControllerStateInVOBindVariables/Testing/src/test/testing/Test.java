/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.testing;

import oracle.adf.share.ADFContext;

import oracle.jbo.*;
import oracle.jbo.client.Configuration;
import oracle.jbo.domain.*;
import oracle.jbo.domain.Number;

import test.model.common.ExampleModule;

public class Test {
    public static final String AM = "test.model.ExampleModule";
    public static final String CF = "ExampleModuleLocal";
    public static void main(String[] args) {
        testWithoutSettingExplicitInitialValues();
        testSettingExplicitInitialValues();
    }
    public static void testSettingExplicitInitialValues() {
        System.out.println("Testing setting explicit initial values...");
        ExampleModule am = (ExampleModule)Configuration.createRootApplicationModule(AM,CF);
        /*
         * Invoke the custom method on the client interface to set the value
         * of the favorite color property.
         */        
        am.setSessionFavoriteColor("emgreen");
        ViewObject vo = am.findViewObject("ExampleVO");
        vo.executeQuery();
        Row r = vo.first();
        System.out.println(r.getAttribute("Info")+","+r.getAttribute("FavoriteColor"));
        
        vo = am.findViewObject("ExampleVO2");
        /*
         * Setup the "mock" UserInfo object in the session scope for testing
         */
        UserInfo testUserInfo = new UserInfo();
        testUserInfo.setFavoriteColor("doblue");
        ADFContext.getCurrent().getSessionScope().put("UserInfo",testUserInfo);
        vo.executeQuery();
        r = vo.first();
        System.out.println(r.getAttribute("Info")+","+r.getAttribute("FavoriteColor"));
        Configuration.releaseRootApplicationModule(am, true);
    }    
    public static void testWithoutSettingExplicitInitialValues() {
        System.out.println("Testing without setting explicit initial values...");
        ExampleModule am = (ExampleModule)Configuration.createRootApplicationModule(AM,CF);
        ViewObject vo = am.findViewObject("ExampleVO");
        vo.executeQuery();
        Row r = vo.first();
        System.out.println(r.getAttribute("Info")+","+r.getAttribute("FavoriteColor"));
        vo = am.findViewObject("ExampleVO2");
        vo.executeQuery();
        r = vo.first();
        System.out.println(r.getAttribute("Info")+","+r.getAttribute("FavoriteColor"));
        Configuration.releaseRootApplicationModule(am, true);
    }
}
