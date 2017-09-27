/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import model.DepartmentChangeListener;
import model.Department;
import model.DepartmentService;

import oracle.adf.view.rich.activedata.ActiveDataEventUtil;
import oracle.adf.view.rich.event.ActiveDataEntry;
import oracle.adf.view.rich.event.ActiveDataUpdateEvent;
import oracle.adf.view.rich.model.ActiveCollectionModelDecorator;
import oracle.adf.view.rich.model.ActiveDataModel;


import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.SortableModel;

/**
 * This class decorates the standard ADF Faces collection model to add support for data push.
 * The standard collection model is created using the SortableModel convenience class that can take a list of pojo's.
 * 
 * IMPORTANT: The collection model has the concept of a row key, which uniquely identifies an instance in the collection.
 * The SortableModel used here simply uses an Integer as the row key, where the first row is Integer(0), the second row is
 * Integer(1) etcetera.
 * 
 * The row key is used to identify 
 */
public class DepartmentActiveCollectionModel extends ActiveCollectionModelDecorator implements DepartmentChangeListener
{
    private MyActiveDataModel _activeDataModel = new MyActiveDataModel();
    private CollectionModel _model = null;

    private List<SelectItem> allDepartments = new ArrayList<SelectItem>();
    private List<Department> newDepartments = new ArrayList<Department>();
    private List<Department> selectedDepartments = new ArrayList<Department>();
    private List<Long> selectedIds = new ArrayList<Long>();
    private boolean showNewDepartments = true;
    private boolean changeStreamStarted = false;

    @PostConstruct
    public void init()
    {
      for (Department dep : DepartmentService.getInstance().getDepartments())
      {
        allDepartments.add(new SelectItem(dep.getId(),dep.getName()));        
      }
    }
    
  public void startListening(ActionEvent event)
  {
    _model = null;
    setSelectedDepartments();
    DepartmentService.getInstance().addChangeListener(this);
    changeStreamStarted = true;
  }

  private void setSelectedDepartments()
  {
    selectedDepartments.clear();
    for (Department dept : DepartmentService.getInstance().getDepartments())
    {
      if (getSelectedIds().contains(dept.getId()))
      {
        selectedDepartments.add(dept);        
      }       
    }
    if (isShowNewDepartments())
    {
      selectedDepartments.addAll(newDepartments);
    }
  }

  public void changeSelection(ActionEvent event)
  {
    _activeDataModel = new MyActiveDataModel();
    setSelectedDepartments();
  }
    
    public CollectionModel getCollectionModel() {
       if (_model == null) {
          _model = new SortableModel(getSelectedDepartments());        
       }
       return _model;
     }

    public void onDepartmentUpdate(Integer rowKey, Department dept)
    {
      // check whether user is interested in this department change
      if (!getSelectedIds().contains(dept.getId()))
      {
        return;
      }
     System.out.println("changeEvent for EXISTING department"+dept.getId());  
          
      MyActiveDataModel asm = getMyActiveDataModel();
      
      // start the preparation for the ADS update
      asm.prepareDataChange();
      
      // Now that we added selectionIds we can no longer use the RowKey passed in into this method
      // bacuase that rowKey is the index of the department in the list of all departments in the back end service
      // we need to get the index of the department in the list of selected departments
      Integer updateRowKey = getSelectedDepartments().indexOf(dept);

      // Using ADS utility class to create data update event
      ActiveDataUpdateEvent event = 
        ActiveDataEventUtil.buildActiveDataUpdateEvent(
        ActiveDataEntry.ChangeType.UPDATE, // type
        asm.getCurrentChangeCount(), // changeCount
        new Object[] {updateRowKey}, // rowKey
        null, //insertKey (null as we don't insert stuff here;
        new String[] {"name"}, // attribue/property name that changes
        new Object[] { dept.getName()}   // the payload for the above attribute
        );
      // deliver the new Event object to the ADS framework
      asm.notifyDataChange(event);
    }

  public void onNewDepartment(Integer rowKey, Department dept)
  {
    if (!isShowNewDepartments())
    {
      return;
    }
   System.out.println("changeEvent for NEW  department"+dept.getId());  

    // we also add the department to the list of selected departments, so we don't loose
    // it when the end user changes the selection while the back end updates are running
    // and get proper rowKey
    selectedDepartments.add(dept);
    newDepartments.add(dept);
    Integer newRowKey = getSelectedDepartments().indexOf(dept);
        
    MyActiveDataModel asm = getMyActiveDataModel();
    
    // start the preparation for the ADS update
    asm.prepareDataChange();

    // Using ADS utility class to create data update event
    ActiveDataUpdateEvent event = 
      ActiveDataEventUtil.buildActiveDataUpdateEvent(
      ActiveDataEntry.ChangeType.INSERT_BEFORE, // type
      asm.getCurrentChangeCount(), // changeCount
      new Object[] {newRowKey}, // rowKey
      new Object[] {new Integer(0)}, //insertKey new row will be added at the top, if this is not desired, the relatvieKey should be passed in as argument
      new String[] {"id","name"}, // attribute/property names that change
      new Object[] {dept.getId(), dept.getName()}   // the payload for the above attributes
      );
    // deliver the new Event object to the ADS framework
    asm.notifyDataChange(event);
  }

    public ActiveDataModel getActiveDataModel() {
         return _activeDataModel;
    }

    public MyActiveDataModel getMyActiveDataModel() {
        return _activeDataModel;
    }

  @Override
  public void onRemoveDepartment(Integer rowKey, Department dept)
  {
    newDepartments.remove(dept);
    Integer removeRowKey = getSelectedDepartments().indexOf(dept);

    MyActiveDataModel asm = getMyActiveDataModel();
    asm.prepareDataChange();
    // Using ADS utility class to create data update event
    ActiveDataUpdateEvent event = 
      ActiveDataEventUtil.buildActiveDataUpdateEvent(
      ActiveDataEntry.ChangeType.REMOVE, // type
      asm.getCurrentChangeCount(), // changeCount
      new Object[] {removeRowKey}, // rowKey
      null, //insertKey (null as we don't insert stuff)
      null, // attribue/property name that changes
      null   // the payload for the above attribute
      );
    // deliver the new Event object to the ADS framework
    asm.notifyDataChange(event);
  }

  public void setShowNewDepartments(boolean showNewDepartments)
  {
    this.showNewDepartments = showNewDepartments;
  }

  public boolean isShowNewDepartments()
  {
    return showNewDepartments;
  }

  public void setSelectedIds(List<Long> selectedIds)
  {
    this.selectedIds = selectedIds;
  }

  public List<Long> getSelectedIds()
  {
    return selectedIds;
  }

  public List<SelectItem> getAllDepartments()
  {
    return allDepartments;
  }

  public void setChangeStreamStarted(boolean changeStreamStarted)
  {
    this.changeStreamStarted = changeStreamStarted;
  }

  public boolean isChangeStreamStarted()
  {
    return changeStreamStarted;
  }

  public List<Department> getSelectedDepartments()
  {
    return selectedDepartments;
  }
}


