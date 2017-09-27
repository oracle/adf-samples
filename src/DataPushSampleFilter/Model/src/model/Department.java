/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package model;

public class Department
{

  public Department(String name, Long id)
  {
    this.name = name;
    this.id = id;
  }

  public Long getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setId(Long id)
  {
    this.id = id;
  }
  protected String name;
  protected Long id;
}
