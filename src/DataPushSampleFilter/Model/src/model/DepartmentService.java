/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package model;

import java.util.ArrayList;
import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;


public class DepartmentService
{
  private static DepartmentService instance = new DepartmentService();
  private List<DepartmentChangeListener> listeners = new ArrayList<DepartmentChangeListener>();

  private Thread changesThread;
  
  public static DepartmentService getInstance()
  {
    return instance;
  }

  /**
   * If first listener is added, the update process is started
   * @param listener
   */
  public void addChangeListener(DepartmentChangeListener listener)
  {
    if (listeners.size()==0)
    {
      startUpdateProcess();
    }
    listeners.add(listener);
  }

  /**
   * If last listener is removed, the update process is stopped
   * @param listener
   */
  public void removeChangeListener(DepartmentChangeListener listener)
  {
    listeners.remove(listener);
    if (listeners.size()==0)
    {
      stopUpdateProcess();
    }
  }

  private DepartmentService()
  {
    departments.add(new Department("sales", 1L));
    departments.add(new Department("management", 2L));
    departments.add(new Department("support", 3L));
  }

  int counter = 0;
  int depcounter = 3;

  private final List<Department> departments = new ArrayList<Department>();

  public List<Department> getDepartments()
  {
    return departments;
  }

  /**
   * Add new department
   */
  public void newData()
  {
    // Add event code here...
    depcounter++;
    Department newdep = new Department("Depname" + depcounter, new Long(depcounter));
    for (DepartmentChangeListener listener : listeners)
    {
      listener.onNewDepartment(new Integer(depcounter),newdep);      
    }
    getDepartments().add(newdep);
  }

  /**
   * Update name of first 3 departments with 2 seconds in between
   */
  public void changeData()
  {
    // Add event code here...
    counter++;
    System.out.println("changeData");
    List<Department> rows = getDepartments();
    Department dataRow = null;
    for (int i = 0; i < 3; i++)
    {
      dataRow = rows.get(i);
      //    String newDepartmentNameValue = dataRow.getName();
      dataRow.setName(dataRow.getName() + counter);
      for (DepartmentChangeListener listener : listeners)
      {
        listener.onDepartmentUpdate(i,dataRow);
      }

      Long stoptime = 2000L;
      try
      {
        Thread.sleep(stoptime);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }

  private void stopUpdateProcess()
  {
    // calling stop is not safe, but good enough for this sample
    if (changesThread!=null)
    {
      changesThread.stop();
    }
  }
  private void startUpdateProcess()
  {
    Runnable dataChanger = new Runnable()
    {
      public void run()
      {
        // wait 5 seconds before we start new update/insert
        try
        {
          Thread.sleep(5000);
          boolean newData = false;
          for (int i = 0; i < 10; i++)
          {
            // update existin deps or create new dep every 3 seconds
            Thread.sleep(3000);
            if (newData)
            {
              newData();
            }
            else
            {
              changeData();
            }
            newData = !newData;
          }
          for (int i = 0; i < 3; i++)
          {
            // renove existing every 5 seconds
            Thread.sleep(5000);
            // remove by index in the list, not copletely safe because the actual rowKey that we 
            // use when calling onRemoveDepartment on the listener might be different
            Department dep = getDepartments().remove(0);
            for (DepartmentChangeListener listener : listeners)
            {
              listener.onRemoveDepartment(i, dep);
            }
          }
        }
        catch (InterruptedException e)
        {
          throw new RuntimeException(e);
        }
      }
    };

    // start the process
    changesThread = new Thread(dataChanger);
    changesThread.start();
  }
}
