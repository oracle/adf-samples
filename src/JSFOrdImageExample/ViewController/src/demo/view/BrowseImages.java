/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.view;
import demo.view.util.EL;
public class BrowseImages {
  public String getSource() {
      String url = (String)EL.get("#{bindings.Image.inputValue.source}");
      int index = url.indexOf("|null");
      if (index > -1) {
          url = url.substring(0,index)+url.substring(index+5);
      }
      return url;
  }
}
