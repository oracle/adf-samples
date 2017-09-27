package report.metadata;

public class DataMapItem
{
  private String value;
  private String valueType;
  private String type;
  
  public DataMapItem()
  {
    super();
  }

  public DataMapItem(String value, String valueType, String type)
  {
    super();
    this.value = value;
    this.valueType = valueType;
    this.type = type;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  public String getValue()
  {
    return value;
  }

  public void setValueType(String valueType)
  {
    this.valueType = valueType;
  }

  public String getValueType()
  {
    return valueType;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public String getType()
  {
    return type;
  }
}
