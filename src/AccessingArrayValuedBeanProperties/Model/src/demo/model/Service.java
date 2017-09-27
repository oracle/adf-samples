/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.model;
import java.util.ArrayList;
import java.util.List;

public class Service  {
  public Service() {
  }
  
  public List findNominalRecords(String match) {
    /*
     * Simulate returning matching beans based on some match string passed in.
     */
    List list = new ArrayList();
    NominalRecord nr = new NominalRecord();
    nr.setFirstname("Steve");
    nr.setSurname("Muench");
    nr.setDescriptionCode(new String[]{"100","101"});
    nr.setDescriptionText(new String[]{"Facial Scar","Beard"});
    list.add(nr);
    nr = new NominalRecord();
    nr.setFirstname("John");
    nr.setSurname("Bales");
    nr.setDescriptionCode(new String[]{"101","102"});
    nr.setDescriptionText(new String[]{"Beard","Tatoo"});
    list.add(nr);
    return list;
  }
}
