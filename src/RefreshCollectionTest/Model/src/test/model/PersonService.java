/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;
import java.util.ArrayList;
import java.util.Collection;

public class PersonService  {
  Collection people;
  public PersonService() {
  }

  public void setupPeopleCollection(int a, String b) {
    people = new ArrayList();
    for (int z = 0; z < a; z++) {
      Person p = new Person();
      p.setName(b+Integer.toString(z+1));
      p.setAge(z+1);
      people.add(p);
    }
  }

  public void setPeople(Collection people) {
    this.people = people;
  }


  public Collection getPeople() {
    return people;
  }
  
}
