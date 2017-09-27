/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;

public class Person  {
  private String name;
  private int _age;
  public Person() {
  }


  public void setName(String name) {
    this.name = name;
  }


  public String getName() {
    return name;
  }


  public void setAge(int age) {
    _age = age;
  }


  public int getAge() {
    return _age;
  }
}
