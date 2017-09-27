package report.metadata;

import java.util.ArrayList;
import java.util.List;

public class GaugeDataMap
{

  private List<DataMapItem> items = new ArrayList<DataMapItem>();
  private List<DataMapItem> thresholdsItems = new ArrayList<DataMapItem>();

  public GaugeDataMap()
  {
    super();
  }

  public void setItems(List<DataMapItem> items)
  {
    this.items = items;
  }

  public void addItem(DataMapItem item)
  {
    items.add(item);
  }

  public List<DataMapItem> getItems()
  {
    return items;
  }

  public void setThresholdsItems(List<DataMapItem> thresholdsItems)
  {
    this.thresholdsItems = thresholdsItems;
  }

  public void addThresholdsItem(DataMapItem item)
  {
    thresholdsItems.add(item);
  }

  public List<DataMapItem> getThresholdsItems()
  {
    return thresholdsItems;
  }
}
