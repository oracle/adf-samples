/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.ateam.adfmobile.rowcurrency.model;

import java.util.ArrayList;
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
      departments.add(new Department("1","Development"));
      departments.add(new Department("2","Sales"));
      departments.add(new Department("3","Marketing"));
      departments.add(new Department("4","Support"));
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

