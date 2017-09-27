/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package client;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.*;
public class TestClient {
  public static void main(String[] args) {
    String        amDef = "model.TestModule";
    String        config = "TestModuleLocal";
    ApplicationModule am =
    Configuration.createRootApplicationModule(amDef,config);
    ViewObject vo = am.findViewObject("DeptView1");
    Row row1 = vo.first();
    Row row2 = vo.next();

    test(0,row2,vo);

    row1.setAttribute("Dname","XXFoo");
    row2.setAttribute("Dname","XXBar");

    test(1,row2,vo);
    try {
      am.getTransaction().commit();
    }
    catch (JboException jex) {
      System.out.println("Caught JboException:"+jex.getMessage());
    }
    /*
     * Re-retrieving the row using findby key seems to avoid seeing the problem
     *
     *   row2 = vo.findByKey(new Key(new Object[]{new Number(20)}),1)[0];
     */
    test(2,row2,vo);

    row2.setAttribute("Dname","ZZBar");
    Row currentRow = vo.getCurrentRow();
    vo.previous();
    vo.next();
   
    test(3,row2,vo);
    Configuration.releaseRootApplicationModule(am,true);
  }
  private static void test(int i, Row r,ViewObject vo) {
    System.out.println("--[Time:"+i+"]--");
    Row currentRow = vo.getCurrentRow();
    System.out.println("CurrentRow => "+currentRow.getAttribute("Deptno")+","+
    currentRow.getAttribute("Dname"));
    System.out.println("PassedRow => "+r.getAttribute("Deptno")+","+
    r.getAttribute("Dname"));    
  }
  
}
