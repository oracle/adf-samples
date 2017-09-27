package adsdemo.beans;

import adsdemo.builders.BarGraphBuilder;

import adsdemo.builders.GaugeBuilder;
import adsdemo.builders.LineGraphBuilder;
import adsdemo.builders.ReportBuilder;
import adsdemo.builders.TableBuilder;

import adsdemo.utils.JsfUtils;

import java.io.IOException;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.controller.ControllerContext;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.dt.Page;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DropEvent;

import org.apache.myfaces.trinidad.change.AttributeComponentChange;
import org.apache.myfaces.trinidad.change.RemoveChildComponentChange;

import org.w3c.dom.DocumentFragment;

import report.metadata.ReportDefinition;
import report.metadata.ReportManager;

import adsdemo.utils.MdsUtils;

import java.util.ArrayList;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adfdt.model.dvt.objects.CtrlCubicHier;
import oracle.adfdt.model.objects.PageDefinition;

import org.w3c.dom.Element;

import report.metadata.AttributeDefinition;


public class DynamicGraphControllerBean
{

  public String currentReportId = getReports().get(0).getId();
  public String editReportId;
  public String editReportBindingId;

  private List<String> editReportSelectedMeasureIds;

  public DynamicGraphControllerBean()
  {
  }

  public ReportDefinition getCurrentReport()
  {
    return ReportManager.getInstance().getReport(getCurrentReportId());
  }

  public List<ReportDefinition> getReports()
  {
    return ReportManager.getInstance().getReports();
  }

  public DnDAction graphDropped(DropEvent dropEvent)
  {
    // Add event code here...
    String dragId = dropEvent.getDragComponent().getId();
    UIComponent dropTarget = dropEvent.getDropComponent();    
    String panelBoxHeader = null;
    if ("stackedBarChart".equals(dragId))
    {
      addBarChart(dropTarget);  
      panelBoxHeader=getCurrentReport().getName()+" Stacked Bar Chart";
    }
    else if ("pieChart".equals(dragId))
    {
      addMultiPieChart(dropTarget);      
      panelBoxHeader=getCurrentReport().getName()+" Pie Chart";
    }
    else if ("lineChart".equals(dragId))
    {
      addLineChart(dropTarget);      
      panelBoxHeader=getCurrentReport().getName()+" Line Chart";
    }
    else if ("gauge".equals(dragId))
    {
      addGauge(dropTarget);      
      panelBoxHeader=getCurrentReport().getName()+" Gauge";
    }
    else if ("table".equals(dragId))
    {
      addTable(dropTarget);      
      panelBoxHeader=getCurrentReport().getName()+" Table";
    }
    AttributeComponentChange acc = new AttributeComponentChange("text",panelBoxHeader);
    MdsUtils.saveToMds(acc, dropTarget);
    // tried redirect to see updated panel box header, but then dragged content is no longer shown
//    redirectToSelf();    
    return DnDAction.MOVE;
  }

  public void addBarChart(UIComponent dropComponent)
  {
    String pageURI = MdsUtils.getPhysicalPagePath(dropComponent);
    Page currentPage = MdsUtils.getPageObject(pageURI);
    if (getCurrentReport().getBarGraphDataMap()==null)
    {
      JsfUtils.addError("Bar chart not supported for this report");
      return;
    }
    ReportBuilder bgBuilder = new BarGraphBuilder(currentPage,"BAR_VERT_STACK",getCurrentReport().getDataControlCollectionName()+"Bar",getCurrentReport(),getCurrentReport().getBarGraphDataMap());
    DocumentFragment df = bgBuilder.build();
    MdsUtils.saveToMds(df,dropComponent);
  }

  public void addMultiPieChart(UIComponent dropComponent)
  {
    String pageURI = MdsUtils.getPhysicalPagePath(dropComponent);
    Page currentPage = MdsUtils.getPageObject(pageURI);
    if (getCurrentReport().getPieGraphDataMap()==null)
    {
      JsfUtils.addError("Pie chart not supported for this report");
      return;
    }
    ReportBuilder bgBuilder = new BarGraphBuilder(currentPage,"PIE_MULTI",getCurrentReport().getDataControlCollectionName()+"Pie",getCurrentReport(),getCurrentReport().getPieGraphDataMap());
    DocumentFragment df = bgBuilder.build();
    MdsUtils.saveToMds(df,dropComponent);
  }

  public void addLineChart(UIComponent dropComponent)
  {
    String pageURI = MdsUtils.getPhysicalPagePath(dropComponent);
    Page currentPage = MdsUtils.getPageObject(pageURI);
    if (getCurrentReport().getLineGraphDataMap()==null)
    {
      JsfUtils.addError("Line chart not supported for this report");
      return;
    }
    ReportBuilder bgBuilder = new LineGraphBuilder(currentPage,"LINE_VERT_ABS",getCurrentReport().getDataControlCollectionName()+"Line",getCurrentReport(),getCurrentReport().getLineGraphDataMap());
    DocumentFragment df = bgBuilder.build();
    MdsUtils.saveToMds(df,dropComponent);
  }

  public void addGauge(UIComponent dropComponent)
  {
    String pageURI = MdsUtils.getPhysicalPagePath(dropComponent);
    Page currentPage = MdsUtils.getPageObject(pageURI);
    if (getCurrentReport().getGaugeDataMap()==null)
    {
      JsfUtils.addError("Gauge not supported for this report");
      return;
    }
    ReportBuilder bgBuilder = new GaugeBuilder(currentPage,"DIAL",getCurrentReport().getDataControlCollectionName()+"Gauge",getCurrentReport(),getCurrentReport().getGaugeDataMap());
    DocumentFragment df = bgBuilder.build();
    MdsUtils.saveToMds(df,dropComponent);
  }

  public void addTable(UIComponent dropComponent)
  {
    String pageURI = MdsUtils.getPhysicalPagePath(dropComponent);
    Page currentPage = MdsUtils.getPageObject(pageURI);

    ReportBuilder bgBuilder = new TableBuilder(currentPage,getCurrentReport().getDataControlCollectionName()+"Table",getCurrentReport());
    DocumentFragment df = bgBuilder.build();
    MdsUtils.saveToMds(df,dropComponent);
  }


  public void refresh(ActionEvent actionEvent)
  {
    JsfUtils.redirectToSelf();
  }
  

  public void setCurrentReportId(String currentReportId)
  {
    this.currentReportId = currentReportId;
  }

  public String getCurrentReportId()
  {
    return currentReportId;
  }

  public void removeReport(ActionEvent actionEvent)
  {    
    AttributeComponentChange acc = new AttributeComponentChange("text","Drag and drop a graph inside this box");
    MdsUtils.saveToMds(acc,actionEvent.getComponent().getParent().getParent().getParent());        
    RemoveChildComponentChange change =new RemoveChildComponentChange(actionEvent.getComponent().getParent().getParent().getId());
    // TODO Remove associated bindings in page def
    MdsUtils.saveToMds(change,actionEvent.getComponent().getParent().getParent().getParent());    
  }
  
  public List<SelectItem> getEditReportMeasures()
  {
    ArrayList<SelectItem> items = new ArrayList<SelectItem>();
    if (editReportId==null)
    {
      return items;
    }
    for(AttributeDefinition attr : ReportManager.getInstance().getReport(editReportId).getAttributes())
    {
      if (attr.isActive())
      {
        SelectItem si = new SelectItem(attr.getName(),attr.getLabel());
        items.add(si);
      }
    }
    return items;
  }

  public List<String> getEditReportSelectedMeasureIds()  
  {
    if (editReportSelectedMeasureIds==null)
    {
      // TODO populate based on current binding datamap
      ArrayList<String> items = new ArrayList<String>();
      return items;
    }
    return editReportSelectedMeasureIds;
  }

  public void setEditReportSelectedMeasureIds(List<String> items)  
  {
    this.editReportSelectedMeasureIds = items;
  }

  public void setEditReportId(String editReportId)
  {
    this.editReportId = editReportId;
  }

  public String getEditReportId()
  {
    return editReportId;
  }

  public void setEditReportBindingId(String editReportBindingId)
  {
    this.editReportBindingId = editReportBindingId;
    editReportSelectedMeasureIds = null;
  }

  public String getEditReportBindingId()
  {
    return editReportBindingId;
  }


  public void showMeasuresPopup(ActionEvent actionEvent)
  {
    RichPopup popup = (RichPopup) JsfUtils.findComponentInRoot("measurePopup");
    RichPopup.PopupHints hints = new RichPopup.PopupHints();
    popup.show(hints);
  }

  public void measureDialogListener(DialogEvent dialogEvent)
  {
    if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok)
    {
      String pageURI = MdsUtils.getPhysicalPagePath(dialogEvent.getComponent());
      Page currentPage = MdsUtils.getPageObject(pageURI);
      PageDefinition pageDef = currentPage.getDesignTimePageDefinition();
      CtrlCubicHier binding = (CtrlCubicHier) pageDef.findControlBinding(getEditReportBindingId());
      // TODO change items in data map based on selected meaure attrs
//      Element dataMap = (Element) binding.getChildNodes().item(0);
//      Element data = (Element) dataMap.getFirstChild().getFirstChild();
//      System.err.println(dataMap);
    }
  }
}
