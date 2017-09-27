/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.fod;

import oracle.fod.common.EmpView;

import oracle.jbo.ApplicationModule;
import oracle.jbo.client.Configuration;

public class TestClient  {
  /*
   * NOTE: You need to have previous created the Oracle8 type named
   * ====  TABLE_OF_VARCHAR by doing the following at the SQL*Plus
   *       command prompt:
   *       
   *       create type table_of_varchar as table of varchar2(20)
   *       /
   *       
   */
  public static void main(String[] args) {
    String _am = "oracle.fod.AppModule", _cf = "AppModuleLocal";
    ApplicationModule am = Configuration.createRootApplicationModule(_am,_cf);
    System.out.println("### Testing EmpView ###");
    EmpView empView = (EmpView)am.findViewObject("EmpView1");
    empView.setNamesToFind(new String[]{"KING","JONES"});
    empView.executeQuery();
    while (empView.hasNext()) {
      System.out.println(empView.next().getAttribute("Ename"));
    }    
    Configuration.releaseRootApplicationModule(am,true);    
  }
}
