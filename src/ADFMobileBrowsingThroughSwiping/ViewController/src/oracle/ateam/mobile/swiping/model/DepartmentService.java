/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.ateam.mobile.swiping.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DepartmentService
{
  List departments = null;
  public DepartmentService()
  {
    super();
  }
  
  private List getDepartmentList()
  {
    if (departments==null)
    {
      departments = new ArrayList();
      departments.add(new Department("1","Development","Redwood Shores"));
      departments.add(new Department("2","Sales","Redwood Shores"));
      departments.add(new Department("3","Marketing","Boston"));
      departments.add(new Department("4","Support","Boston"));
      departments.add(new Department("5","Consulting","Detroit"));
      departments.add(new Department("6","Procurement","Detroit"));
      departments.add(new Department("7","Facilities","Redwood Shores"));
      departments.add(new Department("8","Helpdesk","Redwood Shores"));
    }
    return departments;
  }
  
  public Department[] getDepartments()
  {
    List depList = getDepartmentList();
    Department[] deps =(Department[]) depList.toArray(new Department[depList.size()]);
    return deps;    
  }

}
