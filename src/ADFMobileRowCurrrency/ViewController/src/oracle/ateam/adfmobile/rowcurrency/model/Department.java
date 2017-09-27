/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.ateam.adfmobile.rowcurrency.model;

public class Department
{
  private String id;
  private String name;
  
  public Department()
  {
    super();
  }

  public Department(String id, String name)
  {
    super();
    this.id= id;
    this.name = name;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getId()
  {
    return id;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

}
