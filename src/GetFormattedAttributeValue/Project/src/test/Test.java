/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import oracle.jbo.ApplicationModule;
import oracle.jbo.LocaleContext;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.client.Configuration;

public class Test  {
  public static void main(String[] args) {
    String        amDef = "test.mypackage.TestModule";
    String        config = "TestModuleLocal";
    ApplicationModule am = Configuration.createRootApplicationModule(amDef,config);
    ViewObject vo = am.findViewObject("EmpView");
    Row r = vo.first();
    System.out.println("Unformatted: "+r.getAttribute("Sal"));
    LocaleContext ctx = am.getSession().getLocaleContext();
    System.out.println("Formatted: "+getFormattedAttrValue(r,"Sal",ctx));
    Configuration.releaseRootApplicationModule(am,true);
  }
  private static String getFormattedAttrValue(Row r, String attrName,LocaleContext ctx) {
    return r.getStructureDef()
            .findAttributeDef(attrName)
            .getUIHelper()
            .getFormattedAttribute(r,ctx);
  }
}
