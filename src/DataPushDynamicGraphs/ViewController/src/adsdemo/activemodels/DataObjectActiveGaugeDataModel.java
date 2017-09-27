package adsdemo.activemodels;

import java.util.List;

import javax.annotation.PostConstruct;

import model.ActiveDataObject;
import model.ActiveDataObjectChangeListener;
import model.ActiveDataObjectService;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.dvt.binding.common.CubicBinding;
import oracle.adf.view.faces.bi.model.ActiveGaugeDataModelDecorator;
import oracle.adf.view.faces.bi.model.GaugeDataModel;
import oracle.adf.view.rich.event.ActiveDataEntry;
import oracle.adf.view.rich.event.ActiveDataUpdateEvent;
import oracle.adf.view.rich.model.ActiveDataModel;

import oracle.adfinternal.view.faces.activedata.ActiveDataEventUtil;

import oracle.jbo.Key;

import report.metadata.ReportManager;


/**
 * This class decorates the standard ADF Faces graph data model to add support for data push.
 */
public class DataObjectActiveGaugeDataModel
  extends ActiveGaugeDataModelDecorator
  implements ActiveDataObjectChangeListener
{
  private MyActiveDataModel _activeDataModel = new MyActiveDataModel();
  private GaugeDataModel graphData = null;

  private String reportId;
  private String graphBindingId;
  
  public DataObjectActiveGaugeDataModel()
  {
    super();    
  }

  public DataObjectActiveGaugeDataModel(String reportId, String graphBindingId)
  {
    super();    
    this.reportId = reportId;
    this.graphBindingId = graphBindingId;
    init();
  }


  @PostConstruct
  public void init()
  {    
    CubicBinding treeData = getGraphBinding();
    graphData = (GaugeDataModel) treeData.getDataModel();
    ActiveDataObjectService service = getActiveDataObjectService();
    service.addChangeListener(this);
  }

  private ActiveDataObjectService getActiveDataObjectService()
  {
    CubicBinding treeData = getGraphBinding();
    ActiveDataObjectService service = (ActiveDataObjectService) treeData.getDCIteratorBinding().getDataControl().getDataProvider();
    return service;
  }

  private CubicBinding getGraphBinding()
  {
    DCBindingContainer dcBindings =
      (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();    
    CubicBinding graphBinding =
      (CubicBinding) dcBindings.getControlBinding(getGraphBindingId());
    return graphBinding;
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
    Key jboKey = new Key(new Object[]{new Integer(0), rowKey});

    // Using ADS utility class to create data update event
    ActiveDataUpdateEvent event =
      ActiveDataEventUtil.buildActiveDataUpdateEvent(ActiveDataEntry.ChangeType.UPDATE,
                                                     // type
        asm.getCurrentChangeCount(), // changeCount
        new Object[]
    {rowKey,new Integer(0) }, // rowKey
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

  public void setGraphBindingId(String graphBindingId)
  {
    this.graphBindingId = graphBindingId;
  }

  public String getGraphBindingId()
  {
    return graphBindingId;
  }

  public GaugeDataModel getModel()
  {
    return graphData;
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

}


