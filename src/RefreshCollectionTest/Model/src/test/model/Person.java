/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;

public class Person  {
  public Person() {
  }
  String name;
  int age;


  public void setName(String name) {
    this.name = name;
  }


  public String getName() {
    return name;
  }


  public void setAge(int age) {
    this.age = age;
  }


  public int getAge() {
    return age;
  }
}
