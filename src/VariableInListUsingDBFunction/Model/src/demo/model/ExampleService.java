/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.model;

import java.util.ArrayList;
import java.util.List;

public class ExampleService
{
   public static List<String> findListOfDnamesToRetrieve() 
   {
     List<String> ret = new ArrayList<String>();
     ret.add("OPERATIONS");
     ret.add("SALES");
     return ret;
   }
}
