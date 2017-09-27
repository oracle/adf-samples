package adsdemo.activemodels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import model.ActiveDataObject;
import model.ActiveDataObjectChangeListener;
import model.ActiveDataObjectService;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.view.rich.event.ActiveDataEntry;
import oracle.adf.view.rich.event.ActiveDataListener;
import oracle.adf.view.rich.event.ActiveDataUpdateEvent;
import oracle.adf.view.rich.model.ActiveCollectionModelDecorator;
import oracle.adf.view.rich.model.ActiveDataModel;

import oracle.adfinternal.view.faces.activedata.ActiveDataEventUtil;
import oracle.adfinternal.view.faces.model.binding.FacesCtrlHierBinding;

import oracle.jbo.Key;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;

import report.metadata.ReportManager;


/**
 * This class decorates the standard ADF Faces graph data model to add support for data push.
 */
public class DataObjectActiveCollectionModel
  extends ActiveCollectionModelDecorator
  implements ActiveDataObjectChangeListener
{
  private MyActiveDataModel _activeDataModel = new MyActiveDataModel();
  private CollectionModel tableData = null;

  private String reportId;
  private String tableBindingId;
  
  public DataObjectActiveCollectionModel()
  {
    super();    
  }

  public DataObjectActiveCollectionModel(String reportId, String tableBindingId)
  {
    super();    
    this.reportId = reportId;
    this.tableBindingId = tableBindingId;
    init();
  }

  @PostConstruct
  public void init()
  {    
    FacesCtrlHierBinding treeData = getTableBinding();
    tableData = treeData.getCollectionModel();
    ActiveDataObjectService service = getActiveDataObjectService();
    service.addChangeListener(this);
  }

  private ActiveDataObjectService getActiveDataObjectService()
  {
    JUCtrlHierBinding treeData = getTableBinding();
    ActiveDataObjectService service = (ActiveDataObjectService) treeData.getDCIteratorBinding().getDataControl().getDataProvider();
    return service;
  }

  private FacesCtrlHierBinding getTableBinding()
  {
    DCBindingContainer dcBindings =
      (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();    
    FacesCtrlHierBinding tableBinding =
      (FacesCtrlHierBinding) dcBindings.getControlBinding(getTableBindingId());
    return tableBinding;
  }



  public void onNew(Integer rowKey, ActiveDataObject dataObject)
  {
  }

  public ActiveDataModel getActiveDataModel()
  {
    return _activeDataModel;
  }

  public MyActiveDataModel getMyActiveDataModel()
  {
    return _activeDataModel;
  }

  public void onRemove(Integer rowKey, ActiveDataObject dataObject)
  {
  }


  public void onUpdate(Integer rowKey, ActiveDataObject dataObject)
  {
    System.err.println("UPDATE EVENT");
    MyActiveDataModel asm = getMyActiveDataModel();

    // start the preparation for the ADS update
    asm.prepareDataChange();

    ArrayList keyList = new ArrayList();
    Key valueKey = new Key(new Object[]{rowKey, new Integer(0) });
    keyList.add(valueKey);

    // Using ADS utility class to create data update event
    ActiveDataUpdateEvent event =
      ActiveDataEventUtil.buildActiveDataUpdateEvent(ActiveDataEntry.ChangeType.UPDATE,
                                                     // type
        asm.getCurrentChangeCount(), // changeCount
        new Object[]
    {keyList }, // rowKey
        null,
        //insertKey new row will be added at the top, if this is not desired, the relatvieKey should be passed in as argument
        getActiveAttributes(), // attribute/property names that change
        getActiveAttributeValues(dataObject)) // the payload for the above attributes
    ;
    // deliver the new Event object to the ADS framework
    asm.notifyDataChange(event);
  }
  
  protected String[] getActiveAttributes()
  {
    List<String> attrNames = ReportManager.getInstance().getReport(getReportId()).getActiveAttributeNames();
    return attrNames.toArray(new String[attrNames.size()]);
  }

  protected Object[] getActiveAttributeValues(ActiveDataObject dataObject)
  {
    List<String> attrNames = ReportManager.getInstance().getReport(getReportId()).getActiveAttributeNames();
    Object[] values = new Object[attrNames.size()];
    int counter = 0;
    for (String attr : attrNames)
    {
      values[counter] = dataObject.getAttributeValue(attr);
      counter++;
    }
    return values;
  }

  public void setTableBindingId(String graphBindingId)
  {
    this.tableBindingId = graphBindingId;
  }

  public String getTableBindingId()
  {
    return tableBindingId;
  }

  @Override
  public void startActiveData(Collection<Object> collection, int i,
                              ActiveDataListener activeDataListener)
  {
    super.startActiveData(collection, i, activeDataListener);
  }

  @Override
  public void stopActiveData(Collection<Object> collection,
                             ActiveDataListener activeDataListener)
  {
    super.stopActiveData(collection, activeDataListener);
  }

  public void setReportId(String reportId)
  {
    this.reportId = reportId;
  }

  public String getReportId()
  {
    return reportId;
  }

  public String getDataObjectName()
  {
    return ReportManager.getInstance().getReport(getReportId()).getDataControlCollectionBeanClass();
  }
  
  public void cleanUp()
  {
    getActiveDataObjectService().removeChangeListener(this); 
  }

  protected CollectionModel getCollectionModel()
  {
    return tableData;
  }
}


