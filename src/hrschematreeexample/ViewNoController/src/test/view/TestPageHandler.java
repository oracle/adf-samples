/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
import com.sun.java.util.collections.HashMap;
import oracle.adf.controller.lifecycle.LifecycleContext;
import oracle.adf.controller.lifecycle.PageLifecycle;
import oracle.jbo.ViewObject;
import oracle.jbo.XMLInterface;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
public class TestPageHandler extends PageLifecycle  {
  public void prepareModel(LifecycleContext ctx) throws Exception {
    super.prepareModel(ctx);
    ViewObject vo = ctx.getBindingContainer().findIteratorBinding("DepartmentsIterator").getViewObject();
    HashMap h = new HashMap();
    h.put("test.model.DepartmentsView",new String[]{"DepartmentId","DepartmentName","EmployeesView","JobHistoryView"});
    h.put("test.model.EmployeesView",new String[]{"EmployeeId","FirstName","LastName","Email","EmployeesView"});
    h.put("test.model.JobHistoryView",new String[]{"StartDate","StartDate"});
    Node n = vo.writeXML(XMLInterface.XML_OPT_LIMIT_RANGE,h);
    Document d = n.getOwnerDocument();
    ((XMLDocument)d).adoptNode(n);
    d.appendChild(n);    
    ctx.getHttpServletRequest().setAttribute("voxml",d);
    /*
     * DEBUGGING TO SEE WHAT THE XML DOCUMENT LOOKS LIKE
     * 
     * COMMENT OUT AFTER GETTING THE JSP PAGE RIGHT
     */
    ((XMLNode)n).print(System.out);
  }

}
