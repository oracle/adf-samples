package adsdemo.builders;

import oracle.adf.view.rich.dt.Page;

import oracle.adfdt.model.dvt.objects.CtrlCubicHier;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import report.metadata.DataMapItem;
import report.metadata.GaugeDataMap;
import report.metadata.ReportDefinition;


public class GaugeBuilder
  extends ReportBuilder
{

  protected GaugeDataMap gaugeDataMap;
  
  public GaugeBuilder(Page page, String reportBindingType, String reportBindingId, ReportDefinition report, GaugeDataMap gaugeDataMap)
  {
    super(page,reportBindingType,reportBindingId, report, null);
    this.gaugeDataMap = gaugeDataMap;
  }

  protected void createReportComponent(Element container)
  {
    Document doc = container.getOwnerDocument();

    Element tag =
      createReportElement(doc, reportBindingId, null, "dvt:gauge", getGaugeValueExpression());
    tag.setAttribute("gaugeType", reportBindingType);
    tag.setAttribute("gaugeSetDirection", "GSD_ACROSS");
    tag.setAttribute("gaugeSetColumnCount", "2");
    tag.setAttribute("inlineStyle", "height:1600px;width:800px");
    container.appendChild(tag);
    Element bg = createGaugeBackgroundWithSpecialEffects(doc);
    tag.appendChild(bg);
    Element ths = createGaugeThresholdSet(doc);
    tag.appendChild(ths);
    Element gf = createElement(doc, "dvt:gaugeFrame");
    tag.appendChild(gf);
    Element in = createElement(doc, "dvt:indicator");
    tag.appendChild(in);
    Element inb = createElement(doc, "dvt:indicatorBase");
    tag.appendChild(inb);
    Element gpa = createElement(doc, "dvt:gaugePlotArea");
    tag.appendChild(gpa);
    Element tl = createElement(doc, "dvt:tickLabel");
    tag.appendChild(tl);
    Element tm = createElement(doc, "dvt:tickMark");
    tag.appendChild(tm);
    Element tol = createElement(doc, "dvt:topLabel");
    tag.appendChild(tol);
    Element bol = createElement(doc, "dvt:bottomLabel");
    tag.appendChild(bol);
    Element ml = createElement(doc, "dvt:metricLabel");
    ml.setAttribute("position", "LP_WITH_BOTTOM_LABEL");
    tag.appendChild(ml);
  }

  protected void createGraphDataMap(CtrlCubicHier graph)
  {
    Element dm = graph.getDocument().createElementNS(NAMESPACE_ADFM_DVT, "gaugeDataMap");
    //      DataMap dm = new DataMap("graphDataMap");
    //      dm.setTransactionManager(pageDef.getTransactionManager());
    graph.appendChild(dm);
    Element thresholds =
      graph.getDocument().createElementNS(NAMESPACE_ADFM_DVT, "thresholds");
    dm.appendChild(thresholds);
    for (DataMapItem item : gaugeDataMap.getThresholdsItems())
    {
      Element item1 =
        graph.getDocument().createElementNS(NAMESPACE_ADFM_DVT, "item");
      populateDataMapItem(item, item1);
      thresholds.appendChild(item1);      
    }

    for (DataMapItem item : gaugeDataMap.getItems())
    {
      Element item1 =
        graph.getDocument().createElementNS(NAMESPACE_ADFM_DVT, "item");
      populateDataMapItem(item, item1);
      dm.appendChild(item1);      
    }
  }


  protected String getReportBindingElementName()
  {
    return "gauge";
  }

  protected String getGaugeValueExpression()
  {
    return "#{pageFlowScope.ActiveDataModelProvider.gaugeMap['"+report.getId()+":"+reportBindingId+"']}";
  }
  
}
