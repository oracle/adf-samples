package adsdemo.beans;

import java.util.HashMap;

import java.util.Iterator;
import java.util.Map;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.dvt.binding.common.CubicBinding;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlHierBinding;

import adsdemo.activemodels.DataObjectActiveCollectionModel;
import adsdemo.activemodels.DataObjectActiveDataModel;
import adsdemo.activemodels.DataObjectActiveGaugeDataModel;


public class ActiveDataModelProvider 
{
  private boolean editMode = false;
  private Map graphMap = new GraphMap();
  private Map gaugeMap = new GaugeMap();
  private Map collectionMap = new CollectionMap();

  public ActiveDataModelProvider()
  {
    super();
  }

  public void setEditMode(boolean editMode)
  {
    this.editMode = editMode;
    if (isEditMode())
    {
      reset();
    }
  }
  
  public void reset()
  {
    // deregister current model listeners
    Iterator values = getGraphMap().values().iterator();
    while (values.hasNext())
    {
      DataObjectActiveDataModel model = (DataObjectActiveDataModel) values.next();
      model.cleanUp();
    }
    values = getGaugeMap().values().iterator();
    while (values.hasNext())
    {
      DataObjectActiveGaugeDataModel model = (DataObjectActiveGaugeDataModel) values.next();
      model.cleanUp();
    }
    values = getCollectionMap().values().iterator();
    while (values.hasNext())
    {
      DataObjectActiveCollectionModel model = (DataObjectActiveCollectionModel) values.next();
      model.cleanUp();
    }
    // clear the map with providers, so we get new providers that will
    // correctly restart ADS when w eleave edit mode
    getGraphMap().clear();
    getGaugeMap().clear();  
    getCollectionMap().clear();
  
  }

  public boolean isEditMode()
  {
    return editMode;
  }
  
  public Map getGraphMap()
  {
    return graphMap; 
  }

  public Map getGaugeMap()
  {
    return gaugeMap; 
  }

  public Map getCollectionMap()
  {
    return collectionMap; 
  }

  class GraphMap extends HashMap
  {
    public Object get(Object key)
    {
      String keyString = (String) key;
      int pos = keyString.indexOf(":");
      String reportId = keyString.substring(0, pos);
      String graphBindingId = keyString.substring(pos+1);
      if (isEditMode())
      {
        // always return the graph binding from current binding container
        DCBindingContainer dcBindings =
          (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();    
        CubicBinding graphBinding = (CubicBinding) dcBindings.getControlBinding(graphBindingId);
        return graphBinding.getDataModel();      
      }
      else
      {
        // get the model from the map
        // if not exists, create an instance first
        Object model = super.get(key);
        if (model==null)
        {
          model= new DataObjectActiveDataModel(reportId,graphBindingId);
          put(key,model);
        }
        return model;
      }
    }    
  }

  class GaugeMap extends HashMap
  {
    public Object get(Object key)
    {
      String keyString = (String) key;
      int pos = keyString.indexOf(":");
      String reportId = keyString.substring(0, pos);
      String graphBindingId = keyString.substring(pos+1);
      if (isEditMode())
      {
        // always return the graph binding data model from current binding container
        DCBindingContainer dcBindings =
          (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();    
        CubicBinding graphBinding = (CubicBinding) dcBindings.getControlBinding(graphBindingId);
        return graphBinding.getDataModel();      
      }
      else
      {
        // get the model from the map
        // if not exists, create an instance first
        Object model = super.get(key);
        if (model==null)
        {
          model= new DataObjectActiveGaugeDataModel(reportId,graphBindingId);
          put(key,model);
        }
        return model;
      }
    }    
  }

  class CollectionMap extends HashMap
  {
    public Object get(Object key)
    {
      String keyString = (String) key;
      int pos = keyString.indexOf(":");
      String reportId = keyString.substring(0, pos);
      String graphBindingId = keyString.substring(pos+1);
      if (isEditMode())
      {
        // always return the graph binding data model from current binding container
        DCBindingContainer dcBindings =
          (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();    
        FacesCtrlHierBinding graphBinding = (FacesCtrlHierBinding) dcBindings.getControlBinding(graphBindingId);
        return graphBinding.getCollectionModel();      
      }
      else
      {
        // get the model from the map
        // if not exists, create an instance first
        Object model = super.get(key);
        if (model==null)
        {
          model= new DataObjectActiveCollectionModel(reportId,graphBindingId);
          put(key,model);
        }
        return model;
      }
    }    
  }
}
