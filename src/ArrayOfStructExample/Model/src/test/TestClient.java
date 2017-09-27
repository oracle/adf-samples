/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.*;

import test.common.AppModule;

public class TestClient {
    public static void main(String[] args) {
        String amDef = "test.AppModule";
        String config = "AppModuleLocal";
        AppModule am = (AppModule)Configuration.createRootApplicationModule(amDef, config);
        am.testEntityBasedScoredEmployees();
        am.testReadOnlyScoredEmployees();
        ViewObject roVO = am.findViewObject("EntityBasedScoredEmployees");
        ViewObject eoVO = am.findViewObject("ReadOnlyScoredEmployees");
        System.out.println("Testing read-only VO with scored array data joined in...");
        while (roVO.hasNext()) {
            Row r = roVO.next();
            System.out.println(r.getAttribute("Score")+","+r.getAttribute("Empno")+","+r.getAttribute("Ename"));
        }
        System.out.println("Testing entity-based VO with scored array data joined in...");
        while (eoVO.hasNext()) {
            Row r = eoVO.next();
            System.out.println(r.getAttribute("Score")+","+r.getAttribute("Empno")+","+r.getAttribute("Ename"));
        }
         // Work with your appmodule and view object here
        Configuration.releaseRootApplicationModule(am, true);
    }

}
