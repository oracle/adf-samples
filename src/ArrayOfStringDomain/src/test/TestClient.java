package test;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;
import test.common.*;
import oracle.jbo.domain.*;

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
    String _am = "test.MyService", _cf = "MyServiceLocal";
    ApplicationModule am = Configuration.createRootApplicationModule(_am,_cf);
    System.out.println("### Testing EmpView ###");
    EmpView empView = (EmpView)am.findViewObject("EmpView");
    empView.setNamesToFind(new String[]{"KING","JONES"});
    empView.executeQuery();
    while (empView.hasNext()) {
      System.out.println(empView.next().getAttribute("Ename"));
    }
    System.out.println("### Testing EmpViewWithNamedBindingStyle ###");
    EmpViewWithNamedBindingStyle empViewNamedBinds = (EmpViewWithNamedBindingStyle)am.findViewObject("EmpViewWithNamedBindingStyle");
    empViewNamedBinds.setNamesToFind(new String[]{"SMITH","FORD"});
    empViewNamedBinds.executeQuery();
    while (empViewNamedBinds.hasNext()) {
      System.out.println(empViewNamedBinds.next().getAttribute("Ename"));
    }
    Configuration.releaseRootApplicationModule(am,true);    
  }
}