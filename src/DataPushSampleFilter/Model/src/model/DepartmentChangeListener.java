/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package model;

/**
 * This is the custom listener interface that should define the various types of changes
 * the user interface must be able to show real time.
 * 
 * With the back end service (DepartmentService in this example) you can register listeners 
 * of this type that will be notified.
 */
    public interface DepartmentChangeListener {


  void onDepartmentUpdate(Integer rowKey, Department dept);
  void onNewDepartment(Integer rowKey,Department dept);
  void onRemoveDepartment(Integer rowKey, Department dept);

}
