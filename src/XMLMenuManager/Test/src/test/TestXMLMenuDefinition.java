/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import java.util.List;
import oracle.srdemo.view.menu.Item;
import oracle.srdemo.view.menu.XMLMenuDefinition;
public class TestXMLMenuDefinition {
  private static final String spaces="                                        ";
  public static void main(String[] args) throws Throwable {
    XMLMenuDefinition xmd = new XMLMenuDefinition();
    try {
      System.out.println("Trying to get items");
      List<Item> items = xmd.getItems();
    }
    catch (RuntimeException e) {
      System.out.println(e.getMessage());
    }
    try {
      System.out.println("Trying to get model");
      Object model = xmd.getModel();
    }
    catch (RuntimeException e) {
      System.out.println(e.getMessage());
    }
    xmd.setMenuDefinitionFile("TestMenus.xml");
    List<Item> items = xmd.getItems();
    dumpItems(items,0);
  }
  private static void dumpItems(List<Item> items, int level) {
    String prefix = spaces.substring(0,level*3);
    for (Item i : items) {
      System.out.println(prefix+i.toString());
      if (i.isContainer()) {
        dumpItems(i.getItems(),level++);
      }
    }    
  }
}
