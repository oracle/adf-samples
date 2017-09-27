package report.metadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportManager
{
  
  private static ReportManager instance = null;
    
  private Map<String,ReportDefinition> reports = new HashMap<String,ReportDefinition>();

  /**
   *  constructor still public might be initalized through managed bean
   *  before getInstance gets called
   */
  public ReportManager()
  {
    super();
    setUpSampleReports();    
    instance = this;
  }
  
  public static ReportManager getInstance()
  {
    if (instance==null)
    {
      instance = new ReportManager();
    }
    return instance;
  }
  
  public ReportDefinition getReport(String id)
  {
    return reports.get(id);
  }
  
  public List<ReportDefinition> getReports()
  {
    return new ArrayList<ReportDefinition>(reports.values());
  }

  private void setUpSampleReports()
  {
    // sample report 1
    addReport1();
    addReport2();
  }

  private void addReport1()
  {
    ReportDefinition rep1 = new ReportDefinition();
    rep1.setId("QueueStates");
    rep1.setName("Queue States");
    rep1.setDataControlName("ActiveDataObjectService");
    rep1.setDataControlCollectionName("queueStates");
    rep1.setDataControlCollectionBeanClass("model.QueueState");

    AttributeDefinition attr1 = new AttributeDefinition();
    attr1.setName("queue");
    attr1.setLabel("Queue");
    attr1.setWidthInPixels(200);
    rep1.addAttributeDefinition(attr1);        

    AttributeDefinition attr2 = new AttributeDefinition();
    attr2.setName("state");
    attr2.setLabel("State");
    attr2.setWidthInPixels(200);
    rep1.addAttributeDefinition(attr2);        

    AttributeDefinition attr3 = new AttributeDefinition();
    attr3.setName("count");
    attr3.setLabel("Count");
    attr3.setWidthInPixels(100);
    attr3.setActive(true);
    rep1.addAttributeDefinition(attr3);        

    GaugeDataMap gaugeDataMap = new GaugeDataMap();
    gaugeDataMap.addThresholdsItem(new DataMapItem("3","literal",null));
    gaugeDataMap.addThresholdsItem(new DataMapItem("6","literal",null));
    gaugeDataMap.addItem(new DataMapItem("count",null,"metric"));
    gaugeDataMap.addItem(new DataMapItem("state",null,"topLabel"));
    gaugeDataMap.addItem(new DataMapItem("queue",null,"bottomLabel"));
    gaugeDataMap.addItem(new DataMapItem("0","literal","minimum"));
    gaugeDataMap.addItem(new DataMapItem("10","literal","maximum"));
    rep1.setGaugeDataMap(gaugeDataMap);

    GraphDataMap lineGraphDataMap = new GraphDataMap();
    lineGraphDataMap.addSeriesItem(new DataMapItem("queue",null,null));
    lineGraphDataMap.addSeriesDataItem(new DataMapItem("count",null,null));
    lineGraphDataMap.addGroupItem(new DataMapItem("state",null,null));
    rep1.setLineGraphDataMap(lineGraphDataMap);

    GraphDataMap barGraphDataMap = new GraphDataMap();
    barGraphDataMap.addSeriesItem(new DataMapItem("state",null,null));
    barGraphDataMap.addGroupDataItem(new DataMapItem("count",null,null));
    barGraphDataMap.addGroupItem(new DataMapItem("queue",null,null));
    rep1.setBarGraphDataMap(barGraphDataMap);
    rep1.setPieGraphDataMap(barGraphDataMap);

    reports.put(rep1.getId(), rep1);
  }


  private void addReport2()
  {
    ReportDefinition rep1 = new ReportDefinition();
    rep1.setId("AgentPerformance");
    rep1.setName("Agent Performance");
    rep1.setDataControlName("ActiveDataObjectService");
    rep1.setDataControlCollectionName("agentPerformances");
    rep1.setDataControlCollectionBeanClass("model.AgentPerformance");

    AttributeDefinition attr1 = new AttributeDefinition();
    attr1.setName("agentName");
    attr1.setLabel("Agent Name");
    attr1.setWidthInPixels(200);
    rep1.addAttributeDefinition(attr1);        

//    todayHeldContacts;
//      private int todayRedirects;
//      private int todayTransfers
    AttributeDefinition attr2 = new AttributeDefinition();
    attr2.setName("todayHeldContacts");
    attr2.setLabel("Today Held Contacts");
    attr2.setWidthInPixels(100);
    attr2.setActive(true);
    rep1.addAttributeDefinition(attr2);        

    AttributeDefinition attr3 = new AttributeDefinition();
    attr3.setName("todayRedirects");
    attr3.setLabel("Today Redirects");
    attr3.setWidthInPixels(100);
    attr3.setActive(true);
    rep1.addAttributeDefinition(attr3);        

    AttributeDefinition attr4 = new AttributeDefinition();
    attr4.setName("todayTransfers");
    attr4.setLabel("Today Transfers");
    attr4.setWidthInPixels(100);
    attr4.setActive(true);
    rep1.addAttributeDefinition(attr4);        

//    GaugeDataMap gaugeDataMap = new GaugeDataMap();
//    gaugeDataMap.addThresholdsItem(new DataMapItem("3","literal",null));
//    gaugeDataMap.addThresholdsItem(new DataMapItem("6","literal",null));
//    gaugeDataMap.addItem(new DataMapItem("count",null,"metric"));
//    gaugeDataMap.addItem(new DataMapItem("state",null,"topLabel"));
//    gaugeDataMap.addItem(new DataMapItem("queue",null,"bottomLabel"));
//    gaugeDataMap.addItem(new DataMapItem("0","literal","minimum"));
//    gaugeDataMap.addItem(new DataMapItem("10","literal","maximum"));
//    rep1.setGaugeDataMap(gaugeDataMap);

    GraphDataMap lineGraphDataMap = new GraphDataMap();
    lineGraphDataMap.addSeriesDataItem(new DataMapItem("todayHeldContacts",null,null));
    lineGraphDataMap.addSeriesDataItem(new DataMapItem("todayRedirects",null,null));
    lineGraphDataMap.addSeriesDataItem(new DataMapItem("todayTransfers",null,null));
    lineGraphDataMap.addGroupItem(new DataMapItem("agentName",null,null));
    rep1.setLineGraphDataMap(lineGraphDataMap);
    
    rep1.setBarGraphDataMap(lineGraphDataMap);
    rep1.setPieGraphDataMap(lineGraphDataMap);

//    GraphDataMap barGraphDataMap = new GraphDataMap();
//    barGraphDataMap.addSeriesItem(new DataMapItem("state",null,null));
//    barGraphDataMap.addGroupDataItem(new DataMapItem("count",null,null));
//    barGraphDataMap.addGroupItem(new DataMapItem("agentName",null,null));
//    rep1.setBarGraphDataMap(barGraphDataMap);
//    rep1.setPieGraphDataMap(barGraphDataMap);

    reports.put(rep1.getId(), rep1);
  }
  
}
