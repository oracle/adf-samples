package adsdemo.builders;

import oracle.adf.view.rich.dt.Page;

import oracle.adfdt.model.dvt.objects.CtrlCubicHier;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;

import report.metadata.DataMapItem;
import report.metadata.GraphDataMap;
import report.metadata.ReportDefinition;


public class BarGraphBuilder
  extends ReportBuilder
{
  public BarGraphBuilder(Page page, String reportBindingType, String reportBindingId, ReportDefinition report, GraphDataMap graphDataMap)
  {
    super(page,reportBindingType,reportBindingId, report, graphDataMap);
  }

  protected void createReportComponent(Element container)
  {
    Document doc = container.getOwnerDocument();

    // create root barChart tag
    Element tag =
      createReportElement(doc, reportBindingId, reportBindingType,
                         "dvt:barGraph", getReportValueExpression());
    container.appendChild(tag);

    Element bg = createDvtBackgroundWithSpecialEffects(doc);
    tag.appendChild(bg);

    Element gpa = createDvtGraphPlotArea(doc);
    tag.appendChild(gpa);
    Element ss = createDvtSeriesSet(doc);
    tag.appendChild(ss);
    Element ola = createDvtOlAxis(doc);
    tag.appendChild(ola);
    Element yla = createDvtYlAxis(doc);
    tag.appendChild(yla);
    Element la = createDvtLegendArea(doc);
    tag.appendChild(la);
  }

  protected String getReportBindingElementName()
  {
    return "graph";
  }
}
