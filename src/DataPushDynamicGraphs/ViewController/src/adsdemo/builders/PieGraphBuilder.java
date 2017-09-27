package adsdemo.builders;

import oracle.adf.view.rich.dt.Page;

import oracle.adfdt.model.dvt.objects.CtrlCubicHier;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;

import report.metadata.GraphDataMap;
import report.metadata.ReportDefinition;

public class PieGraphBuilder
  extends ReportBuilder
{
  
  public PieGraphBuilder(Page page, String reportBindingType, String reportBindingId, ReportDefinition report, GraphDataMap graphDataMap)
  {
    super(page,reportBindingType,reportBindingId, report, graphDataMap);
  }

  protected void createReportComponent(Element container)
  {
    Document doc = container.getOwnerDocument();
    Element tag =
      createReportElement(doc, reportBindingId, reportBindingType, "dvt:pieGraph", getReportValueExpression());
    container.appendChild(tag);
    Element bg = createDvtBackgroundWithSpecialEffects(doc);
    tag.appendChild(bg);
    Element gpf = createDvtGraphPieFrame(doc);
    tag.appendChild(gpf);
    Element ss = createDvtSeriesSet(doc);
    tag.appendChild(ss);
    Element sla = createDvtSliceLabel(doc);
    tag.appendChild(sla);
    Element pla = createDvtPieLabel(doc);
    tag.appendChild(pla);
    Element la = createDvtLegendArea(doc);
    tag.appendChild(la);
  }

  protected String getReportBindingElementName()
  {
    return "graph";
  }

}
