package report.metadata;

import java.util.ArrayList;
import java.util.List;


public class ReportDefinition
{
  private String id;
  private String name;
  private String description;
  private String collectionModelBeanExpression;
  private String dataControlName;
  private String dataControlCollectionName;
  private String dataControlCollectionBeanClass;
  
  private GraphDataMap barGraphDataMap;
  private GraphDataMap pieGraphDataMap;
  private GraphDataMap lineGraphDataMap;
  private GaugeDataMap gaugeDataMap;

  private List<AttributeDefinition> attributes = new ArrayList<AttributeDefinition>();

  public ReportDefinition()
  {
    super();
  }

  public void addAttributeDefinition(AttributeDefinition attr)
  {
    attributes.add(attr);
  }

  public void setAttributes(List<AttributeDefinition> attributes)
  {
    this.attributes = attributes;
  }

  public List<AttributeDefinition> getAttributes()
  {
    return attributes;
  }

  public List<String> getActiveAttributeNames()
  {
    List<String> activeAttrs = new ArrayList<String>();
    for (AttributeDefinition attr : getAttributes())
    {
      if (attr.isActive())
      {
        activeAttrs.add(attr.getName());
      }
    }
    return activeAttrs;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getId()
  {
    return id;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public void setCollectionModelBeanExpression(String collectionModelClass)
  {
    this.collectionModelBeanExpression = collectionModelClass;
  }

  public String getCollectionModelBeanExpression()
  {
    return collectionModelBeanExpression;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getDescription()
  {
    return description;
  }

  public void setBarGraphDataMap(GraphDataMap barGraphDataMap)
  {
    this.barGraphDataMap = barGraphDataMap;
  }

  public GraphDataMap getBarGraphDataMap()
  {
    return barGraphDataMap;
  }

  public void setPieGraphDataMap(GraphDataMap pieGraphDataMap)
  {
    this.pieGraphDataMap = pieGraphDataMap;
  }

  public GraphDataMap getPieGraphDataMap()
  {
    return pieGraphDataMap;
  }

  public void setLineGraphDataMap(GraphDataMap lineGraphDataMap)
  {
    this.lineGraphDataMap = lineGraphDataMap;
  }

  public GraphDataMap getLineGraphDataMap()
  {
    return lineGraphDataMap;
  }

  public void setGaugeDataMap(GaugeDataMap gaugeDataMap)
  {
    this.gaugeDataMap = gaugeDataMap;
  }

  public GaugeDataMap getGaugeDataMap()
  {
    return gaugeDataMap;
  }

  public void setDataControlName(String dataControlName)
  {
    this.dataControlName = dataControlName;
  }

  public String getDataControlName()
  {
    return dataControlName;
  }

  public void setDataControlCollectionName(String dataControlCollectionName)
  {
    this.dataControlCollectionName = dataControlCollectionName;
  }

  public String getDataControlCollectionName()
  {
    return dataControlCollectionName;
  }

  public void setDataControlCollectionBeanClass(String dataControlCollectionBeanClass)
  {
    this.dataControlCollectionBeanClass = dataControlCollectionBeanClass;
  }

  public String getDataControlCollectionBeanClass()
  {
    return dataControlCollectionBeanClass;
  }
}
