/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;


public class Util {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Map m = new HashMap() {
        public Object get(Object key) {
                try {
                    return df.parse((String)key);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            }
    };
    public Map getToDate() {
      return m;
    }
}
