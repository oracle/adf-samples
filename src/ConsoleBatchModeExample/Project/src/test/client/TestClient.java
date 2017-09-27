/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.client;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.jbo.uicli.binding.JUCtrlRangeBinding;
public class TestClient  {
  private static final String BINDING_CONTAINER_NAME = "TestClientUIModel";
  public static void main(String[] args) {
    BindingContext     bndCtx = BindingContextFactory.create("DataBindings.cpx");
    DCBindingContainer bndCnt = bndCtx.findBindingContainer("TestClientUIModel");
    JUCtrlRangeBinding depts  = (JUCtrlRangeBinding)bndCnt.findCtrlBinding("Departments");
    JUCtrlRangeBinding people = (JUCtrlRangeBinding)bndCnt.findCtrlBinding("People");
    bndCnt.refreshControl();
    List deptsList  = depts.getRangeSet();
    List peopleList = people.getRangeSet();
    Iterator deptsIter = deptsList.iterator();
    Iterator peopleIter = peopleList.iterator();
    while (deptsIter.hasNext()) {
      Map attrs = (Map)deptsIter.next();
      System.out.println(attrs.get("Deptno")+","+attrs.get("Dname"));      
    }
    while (peopleIter.hasNext()) {
      Map attrs = (Map)peopleIter.next();
      System.out.println(attrs.get("name")+","+attrs.get("age"));      
    }
    bndCtx.release();        
  }
}
