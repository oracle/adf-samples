/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PeopleService  {
  private static Person[] _people;
  public PeopleService() {}
  public Person getPerson(int i) {
    return _people[i];
  }
  public Collection getPeople() {
    List l = new ArrayList();
    l.add(_people[0]);
    l.add(_people[1]);
    return l;
  }
  static {
    _people = new Person[2];
    _people[0] = new Person();
    _people[1] = new Person();
    _people[0].setAge(1);
    _people[0].setName("Steve");
    _people[1].setAge(2);
    _people[1].setName("Paul");
  }  
}
