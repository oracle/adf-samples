package adsdemo.builders;

import java.beans.PropertyVetoException;

import oracle.adf.view.rich.dt.Page;

import oracle.adfdt.model.binding.BindingInfo;
import oracle.adfdt.model.dvt.objects.CtrlCubicHier;
import oracle.adfdt.model.objects.Application;
import oracle.adfdt.model.objects.DataControl;
import oracle.adfdt.model.objects.IteratorBinding;
import oracle.adfdt.model.objects.PageDefinition;
import oracle.adfdt.view.common.binding.utils.ADFBindingGenerator;

import oracle.binding.meta.NamedDefinition;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;

import report.metadata.DataMapItem;
import report.metadata.GraphDataMap;
import report.metadata.ReportDefinition;


public abstract class ReportBuilder
{
  public static String NAMESPACE_ADF_FACES = "http://xmlns.oracle.com/adf/faces/rich";

  public static String NAMESPACE_ADF_FACES_DVT = "http://xmlns.oracle.com/dss/adf/faces";

    public static String NAMESPACE_ADFM_DVT = "http://xmlns.oracle.com/adfm/dvt";

    public static String NAMESPACE_ADFM = "http://xmlns.oracle.com/adfm/uimodel";

  
  protected Page page;
  protected Application application;
  protected PageDefinition pageDef;
  protected String dataControlName;
  protected String rootIteratorName;
  protected String reportIteratorBinds;
  protected String reportIteratorName;
  protected String beanClass;
  protected String reportBindingType;
  protected String reportBindingId;
  protected GraphDataMap graphDataMap;
  protected ReportDefinition report;

  public ReportBuilder(Page page,String reportBindingType, String reportBindingId, ReportDefinition report, GraphDataMap graphDataMap)
  {
    this.page = page;
    this.dataControlName = report.getDataControlName();
    this.rootIteratorName = dataControlName+"Iterator";
    this.application = page.getDesignTimeApplication();
    // TODO throws error when page def does not exist yet
    this.pageDef = page.getDesignTimePageDefinition();
    this.reportIteratorBinds = report.getDataControlCollectionName();
    this.reportIteratorName = reportIteratorBinds+"Iterator";
    this.beanClass = report.getDataControlCollectionBeanClass();    
    this.reportBindingType = reportBindingType;
    this.reportBindingId = reportBindingId;
    this.graphDataMap = graphDataMap;
    this.report = report;
  }
  
  public DocumentFragment build()
  {
    createBindings();
    return createReportContainer(page.getViewDocument());
  }

  protected DocumentFragment createReportContainer(Document doc)
  {
    DocumentFragment df = doc.createDocumentFragment();
    Element pgl = doc.createElementNS(NAMESPACE_ADF_FACES, "af:panelGroupLayout");
    df.appendChild(pgl);
    pgl.setAttribute("id", reportBindingId+"PglCont");
    pgl.setAttribute("layout", "scroll");
    // add pgl with delte icon
    //  <af:panelGroupLayout layout="horizontal" halign="end">
    //     <af:commandImageLink icon="/images/deleteRow.png" rendered="#{pageFlowScope.ActiveDataModelProvider.editMode}"
    //                          actionListener="#{pageFlowScope.DynamicGraphBean.removeReport}"/>
    //   </af:panelGroupLayout>
    Element pgl2 = doc.createElementNS(NAMESPACE_ADF_FACES, "af:panelGroupLayout");
    pgl2.setAttribute("id", reportBindingId+"PglDel");
    pgl2.setAttribute("layout", "horizontal");
    pgl2.setAttribute("halign", "end");
    pgl2.setAttribute("rendered", "#{pageFlowScope.ActiveDataModelProvider.editMode}");
    pgl.appendChild(pgl2);

//    <af:commandButton text="Measures" id="cb8"
//                      actionListener="#{pageFlowScope.DynamicGraphBean.showMeasuresPopup}">
//      <af:setPropertyListener type="action" from="AgentPerformance"
//                              to="#{pageFlowScope.DynamicGraphBean.editReportId}"/>
//      <af:setPropertyListener type="action" from="qqq"
//                              to="#{pageFlowScope.DynamicGraphBean.editReportBindingId}"/>
//    </af:commandButton>

    Element mb = doc.createElementNS(NAMESPACE_ADF_FACES, "af:commandImageLink");
    mb.setAttribute("id", reportBindingId+"MeasureLink");
    mb.setAttribute("icon", "/images/editRow.png");
    mb.setAttribute("shortDesc", "Edit Report Measures");
    mb.setAttribute("actionListener", "#{pageFlowScope.DynamicGraphBean.showMeasuresPopup}");
    pgl2.appendChild(mb);    

    Element spl1 = doc.createElementNS(NAMESPACE_ADF_FACES, "af:setPropertyListener");
    spl1.setAttribute("type", "action");
    spl1.setAttribute("from", report.getId());
    spl1.setAttribute("to", "#{pageFlowScope.DynamicGraphBean.editReportId}");
    mb.appendChild(spl1);

    Element spl2 = doc.createElementNS(NAMESPACE_ADF_FACES, "af:setPropertyListener");
    spl2.setAttribute("type", "action");
    spl2.setAttribute("from", reportBindingId);
    spl2.setAttribute("to", "#{pageFlowScope.DynamicGraphBean.editReportBindingId}");
    mb.appendChild(spl2);

    Element cl = doc.createElementNS(NAMESPACE_ADF_FACES, "af:commandImageLink");
    cl.setAttribute("id", reportBindingId+"DelLink");
    cl.setAttribute("icon", "/images/deleteRow.png");
    cl.setAttribute("shortDesc", "Remove Report");
       cl.setAttribute("actionListener", "#{pageFlowScope.DynamicGraphBean.removeReport}");
    pgl2.appendChild(cl);    


    createReportComponent(pgl);
    return df;
  }
  

  protected abstract void createReportComponent(Element container);

  protected Element createDvtLegendArea(Document doc)
  {
    Element la =
      doc.createElementNS(NAMESPACE_ADF_FACES_DVT, "dvt:legendArea");
    la.setAttribute("automaticPlacement", "AP_NEVER");
    return la;
  }

  protected Element createElement(Document doc, String element)
  {
    Element el = doc.createElementNS(NAMESPACE_ADF_FACES_DVT, element);
    return el;
  }

  protected Element createDvtYlAxis(Document doc)
  {
    Element yla = doc.createElementNS(NAMESPACE_ADF_FACES_DVT, "dvt:y1Axis");
    return yla;
  }

  protected Element createDvtOlAxis(Document doc)
  {
    Element ola = doc.createElementNS(NAMESPACE_ADF_FACES_DVT, "dvt:o1Axis");
    return ola;
  }

  protected Element createDvtSeriesSet(Document doc)
  {
    Element ss = doc.createElementNS(NAMESPACE_ADF_FACES_DVT, "dvt:seriesSet");
    Element ser = doc.createElementNS(NAMESPACE_ADF_FACES_DVT, "dvt:series");
    ss.appendChild(ser);
    return ss;
  }

  protected Element createDvtGraphPlotArea(Document doc)
  {
    Element gpa =
      doc.createElementNS(NAMESPACE_ADF_FACES_DVT, "dvt:graphPlotArea");
    return gpa;
  }

  protected Element createDvtSpecialEffects(Document doc)
  {
    Element se =
      doc.createElementNS(NAMESPACE_ADF_FACES_DVT, "dvt:specialEffects");
    return se;
  }

  protected Element createDvtGraphPieFrame(Document doc)
  {
    Element se =
      doc.createElementNS(NAMESPACE_ADF_FACES_DVT, "dvt:graphPieFrame");
    return se;
  }

  protected Element createDvtSliceLabel(Document doc)
  {
    Element se =
      doc.createElementNS(NAMESPACE_ADF_FACES_DVT, "dvt:sliceLabel");
    return se;
  }

  protected Element createDvtPieLabel(Document doc)
  {
    Element se = doc.createElementNS(NAMESPACE_ADF_FACES_DVT, "dvt:pieLabel");
    se.setAttribute("rendered", "true");
    return se;
  }

  protected Element createDvtBackgroundWithSpecialEffects(Document doc)
  {
    Element bg =
      doc.createElementNS(NAMESPACE_ADF_FACES_DVT, "dvt:background");
    Element se = createDvtSpecialEffects(doc);
    bg.appendChild(se);
    return bg;
  }

  protected Element createGaugeBackgroundWithSpecialEffects(Document doc)
  {
    Element bg =
      doc.createElementNS(NAMESPACE_ADF_FACES_DVT, "dvt:gaugeBackground");
    Element se = createDvtSpecialEffects(doc);
    bg.appendChild(se);
    return bg;
  }
  protected Element createGaugeThresholdSet(Document doc)
  {
    Element bg =
      doc.createElementNS(NAMESPACE_ADF_FACES_DVT, "dvt:thresholdSet");
    Element th1 = createDvtThreshold(doc,"Low","#d62800");
    bg.appendChild(th1);
    Element th2 = createDvtThreshold(doc,"Medium","#ffcf21");
    bg.appendChild(th2);
    Element th3 = createDvtThreshold(doc,"High","#84ae31");
    bg.appendChild(th3);
    return bg;
  }

  private Element createDvtThreshold(Document doc, String text,
                                     String fillColor)
  {
    Element th =
      doc.createElementNS(NAMESPACE_ADF_FACES_DVT, "dvt:threshold");
    th.setAttribute("text", text);
    th.setAttribute("fillColor", fillColor);
    return th;
  }

  protected Element createReportElement(Document doc, String id,
                                       String type, String namespaceType,
                                       String beanExpression)
  {
    Element tag = doc.createElementNS(NAMESPACE_ADF_FACES_DVT, namespaceType);
    tag.setAttribute("id", id);
    tag.setAttribute("value", beanExpression);
    if (type!=null)
    {
      tag.setAttribute("subType", type);      
    }
    //    tag.setAttribute("contentDelivery", "immediate");
    return tag;
  }

  protected void createBindings()
  {
    try
    {
      createRootIterator();
      IteratorBinding accIter = createReportAccessorIterator();    
      createGraphBinding(accIter);
    }
    catch (PropertyVetoException e)
    {
      e.printStackTrace();
    }
  }
  
  protected abstract String getReportBindingElementName();

  protected void createGraphBinding(IteratorBinding accIter)
    throws PropertyVetoException
  {
    if (pageDef.findControlBinding(reportBindingId)!=null)
    {
      return;
    }

      //      <graph IterBinding="agentStatesIterator" id="agentStates"
      //             xmlns="http://xmlns.oracle.com/adfm/dvt" type="BAR_VERT_STACK">
      //        <graphDataMap leafOnly="true">
      //          <series>
      //            <item value="state"/>
      //          </series>
      //          <groups>
      //            <data>
      //              <item value="count"/>
      //            </data>
      //            <item value="queue"/>
      //          </groups>
      //        </graphDataMap>
      //      </graph>
      DataControl dataControl = accIter.getDataControl();
      NamedDefinition nd = dataControl.getDefinition("root");
      CtrlCubicHier graph =
        (CtrlCubicHier) ADFBindingGenerator.createControlBinding(application, pageDef,
                                                             dataControl, nd,
                                                             new BindingInfo(NAMESPACE_ADFM_DVT,
                                                                             getReportBindingElementName()),
                                                             accIter);
      graph.setId(reportBindingId);
      graph.setIterBindingName(reportIteratorName);
      graph.setTransactionManager(pageDef.getTransactionManager());
      graph.setAttribute("type", reportBindingType);
      pageDef.addControlBinding(graph);
      createGraphDataMap(graph);

  }

  protected void createGraphDataMap(CtrlCubicHier graph)
  {
    Element dm =
      graph.getDocument().createElementNS(NAMESPACE_ADFM_DVT, "graphDataMap");
    //      DataMap dm = new DataMap("graphDataMap");
    //      dm.setTransactionManager(pageDef.getTransactionManager());
    dm.setAttribute("leafOnly", "true");
    //      graph.setDatamap(dm);
    graph.appendChild(dm);
    Element series =
      graph.getDocument().createElementNS(NAMESPACE_ADFM_DVT, "series");
    dm.appendChild(series);

    for (DataMapItem item : graphDataMap.getSeriesItems())
    {
      Element item1 =
        graph.getDocument().createElementNS(NAMESPACE_ADFM_DVT, "item");
      populateDataMapItem(item, item1);
      series.appendChild(item1);      
    }

    if (graphDataMap.getSeriesDataItems().size()>0)
    {
      Element data =
        graph.getDocument().createElementNS(NAMESPACE_ADFM_DVT, "data");
      series.appendChild(data);

      for (DataMapItem item : graphDataMap.getSeriesDataItems())
      {
        Element item1 =
          graph.getDocument().createElementNS(NAMESPACE_ADFM_DVT, "item");
        populateDataMapItem(item, item1);
        data.appendChild(item1);      
      }      
    }

    Element groups =
      graph.getDocument().createElementNS(NAMESPACE_ADFM_DVT, "groups");
    dm.appendChild(groups);

    for (DataMapItem item : graphDataMap.getGroupItems())
    {
      Element item1 =
        graph.getDocument().createElementNS(NAMESPACE_ADFM_DVT, "item");
      populateDataMapItem(item, item1);
      groups.appendChild(item1);      
    }

    if (graphDataMap.getGroupDataItems().size()>0)
    {
      Element data =
        graph.getDocument().createElementNS(NAMESPACE_ADFM_DVT, "data");
      groups.appendChild(data);

      for (DataMapItem item : graphDataMap.getGroupDataItems())
      {
        Element item1 =
          graph.getDocument().createElementNS(NAMESPACE_ADFM_DVT, "item");
        populateDataMapItem(item, item1);
        data.appendChild(item1);      
      }      
    }
  }

  private IteratorBinding createReportAccessorIterator()
    throws PropertyVetoException
  {
    IteratorBinding accIter =
      (IteratorBinding) pageDef.findExecutable(reportIteratorName);
    if (accIter == null)
    {
      accIter =
          (IteratorBinding) ADFBindingGenerator.createExecutableBinding(application,
                                                                        pageDef,
                                                                        new BindingInfo("http://xmlns.oracle.com/adfm/uimodel",
                                                                                        "accessorIterator"));
      accIter.setId(reportIteratorName);
      
      accIter.setAttribute("MasterBinding", rootIteratorName);
      accIter.setAttribute("Binds", reportIteratorBinds);
      accIter.setDataControlName(dataControlName);
      accIter.setRangeSize(-1);
      accIter.setAttribute("BeanClass", beanClass);
      pageDef.addIterator(accIter);
    }
    return accIter;
  }

  private void createRootIterator()
    throws PropertyVetoException
  {
    IteratorBinding root =
      (IteratorBinding) pageDef.findExecutable(rootIteratorName);
    if (root == null)
    {
      root =
          (IteratorBinding) ADFBindingGenerator.createExecutableBinding(application,
                                                                        pageDef,
                                                                        new BindingInfo("http://xmlns.oracle.com/adfm/uimodel",
                                                                                        "iterator"));
      root.setId(rootIteratorName);
      root.setAttribute("Binds", "root");
      root.setDataControlName(dataControlName);
      root.setRangeSize(25);
      pageDef.addIterator(root);
    }
  }


  protected void populateDataMapItem(DataMapItem item, Element item1)
  {
    if (item.getValue() != null)
    {
      item1.setAttribute("value", item.getValue());
    }
    if (item.getValueType() != null)
    {
      item1.setAttribute("valueType", item.getValueType());
    }
    if (item.getType() != null)
    {
      item1.setAttribute("type", item.getType());
    }
  }
  
  protected String getReportValueExpression()
  {
    return "#{pageFlowScope.ActiveDataModelProvider.graphMap['"+report.getId()+":"+reportBindingId+"']}";
  }
}
