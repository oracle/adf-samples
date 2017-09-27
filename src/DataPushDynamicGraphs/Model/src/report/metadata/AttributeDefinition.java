package report.metadata;

public class AttributeDefinition
{
  private String label;
  private String name;
  private int widthInPixels;
  private boolean active = false;
  
  public AttributeDefinition()
  {
    super();
  }

  public void setLabel(String label)
  {
    this.label = label;
  }

  public String getLabel()
  {
    return label;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public void setWidthInPixels(int widthInPixels)
  {
    this.widthInPixels = widthInPixels;
  }

  public int getWidthInPixels()
  {
    return widthInPixels;
  }

  public void setActive(boolean active)
  {
    this.active = active;
  }

  public boolean isActive()
  {
    return active;
  }
}
