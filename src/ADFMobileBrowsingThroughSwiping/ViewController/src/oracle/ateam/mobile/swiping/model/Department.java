/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.ateam.mobile.swiping.model;


public class Department
{
  private String id;
  private String name;
  private String location;

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

  public Department(String id, String name, String location)
  {
    super();
    this.id= id;
    this.name = name;
    this.location = location;
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

  public void setLocation(String location)
  {
    this.location = location;
  }


  public String getLocation()
  {
    return location;
  }

  public boolean equals(Object obj)
  {
    if (obj instanceof Department)
    {
      Department otherDep = (Department) obj;
      if (getId()==null && otherDep.getId()==null)
      {
        return true;
      }
      return getId().equals(otherDep.getId());      
    }
    return false;
  }
}
