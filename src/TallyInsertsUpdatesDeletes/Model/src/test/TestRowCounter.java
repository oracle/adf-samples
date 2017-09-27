/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.*;

public class TestRowCounter {
  public static void main(String[] args) {
     String        amDef = "test.AppModule";
     String        config = "appModuleLocal";
     ApplicationModule am = Configuration.createRootApplicationModule(amDef,config);
     ViewObject vo = am.findViewObject("EmpView1");
     Row newEmp = vo.createRow();
     newEmp.setAttribute("Empno", new Number(1234));
     vo.insertRow(newEmp);
     am.getTransaction().postChanges();
     newEmp = vo.createRow();
     newEmp.setAttribute("Empno", new Number(1235));
     vo.insertRow(newEmp);     
     am.getTransaction().postChanges();
     newEmp = vo.createRow();
     newEmp.setAttribute("Empno", new Number(7839));
     vo.insertRow(newEmp);     

     try {
         am.getTransaction().commit();
     }
     catch (Exception e) {
         System.out.println("Caught: "+e.getMessage());
     }
      newEmp.setAttribute("Empno", new Number(1236));
      am.getTransaction().commit();
     
     Configuration.releaseRootApplicationModule(am,true);   
  }
}
