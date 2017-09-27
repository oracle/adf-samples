package adsdemo.builders;

import java.beans.PropertyVetoException;

import oracle.adf.view.rich.dt.Page;

import oracle.adfdt.model.binding.BindingInfo;
import oracle.adfdt.model.objects.CtrlHier;
import oracle.adfdt.model.objects.DataControl;
import oracle.adfdt.model.objects.IteratorBinding;
import oracle.adfdt.view.common.binding.utils.ADFBindingGenerator;

import oracle.binding.meta.NamedDefinition;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;

import report.metadata.AttributeDefinition;
import report.metadata.ReportDefinition;


public class TableBuilder
  extends ReportBuilder
{
  public TableBuilder(Page page, String reportBindingId, ReportDefinition reportDefinition)
  {
    super(page, null, reportBindingId, reportDefinition, null);
  }

  protected void createReportComponent(Element container)
  {
    Document doc = container.getOwnerDocument();
    Element pc = doc.createElementNS(NAMESPACE_ADF_FACES, "af:panelCollection");
    pc.setAttribute("id", reportBindingId+"Pc");
    container.appendChild(pc);

    Element tab = doc.createElementNS(NAMESPACE_ADF_FACES, "af:table");
    tab.setAttribute("id", reportBindingId+"Tab");
    tab.setAttribute("value", getReportValueExpression());
    tab.setAttribute("var", "row");
    tab.setAttribute("rows", "10");
    tab.setAttribute("fetchSize", "10");
    pc.appendChild(tab);
    for (AttributeDefinition attr : report.getAttributes())
    {
      Element col = doc.createElementNS(NAMESPACE_ADF_FACES, "af:column");
      col.setAttribute("id",attr.getName());
      col.setAttribute("headerText", attr.getLabel());
      tab.appendChild(col);
      Element ot = doc.createElementNS(NAMESPACE_ADF_FACES, "af:outputText");
      ot.setAttribute("id", attr.getName()+"Value");
      ot.setAttribute("value", "#{row."+attr.getName()+"}");
      col.appendChild(ot);
    }
  }

  protected String getReportBindingElementName()
  {
    return "tree";
  }

  protected String getReportValueExpression()
  {
    return "#{pageFlowScope.ActiveDataModelProvider.collectionMap['"+report.getId()+":"+reportBindingId+"']}";
  }

  @Override
  protected void createGraphBinding(IteratorBinding accIter)
    throws PropertyVetoException
  {
    if (pageDef.findControlBinding(reportBindingId)!=null)
    {
      return;
    }

//    <tree IterBinding="agentPerformancesIterator" id="agentPerformances1">
//      <nodeDefinition DefName="model.AgentPerformance"
//                      Name="agentPerformances10">
//        <AttrNames>
//          <Item Value="agentName"/>
//          <Item Value="todayHeldContacts"/>
//          <Item Value="todayRedirects"/>
//          <Item Value="todayTransfers"/>
//        </AttrNames>
//      </nodeDefinition>
//    </tree>
    DataControl dataControl = accIter.getDataControl();
    NamedDefinition nd = dataControl.getDefinition("root");
    CtrlHier tree =
      (CtrlHier) ADFBindingGenerator.createControlBinding(application, pageDef,
                                                           dataControl, nd,
                                                           new BindingInfo(NAMESPACE_ADFM,
                                                                           getReportBindingElementName()),
                                                           accIter);
    tree.setId(reportBindingId);
    tree.setIterBindingName(reportIteratorName);
    tree.setTransactionManager(pageDef.getTransactionManager());
    Element nodeDef =
      tree.getDocument().createElementNS(NAMESPACE_ADFM, "nodeDefinition");
    nodeDef.setAttribute("DefName", report.getDataControlCollectionBeanClass());
    nodeDef.setAttribute("Name", report.getDataControlCollectionName()+"0");
    tree.appendChild(nodeDef);
    Element attrNames =
      tree.getDocument().createElementNS(NAMESPACE_ADFM, "AttrNames");
    nodeDef.appendChild(attrNames);
    for (AttributeDefinition attr : report.getAttributes())
    {
      Element item =
        tree.getDocument().createElementNS(NAMESPACE_ADFM, "Item");
      item.setAttribute("Value",attr.getName());
      attrNames.appendChild(item);
    }
    pageDef.addControlBinding(tree);
  }
}
