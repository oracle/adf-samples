package report.metadata;

import java.util.ArrayList;
import java.util.List;

public class GraphDataMap
{
  
  private List<DataMapItem> seriesItems = new ArrayList<DataMapItem>();
  private List<DataMapItem> seriesDataItems = new ArrayList<DataMapItem>();
  private List<DataMapItem> groupItems = new ArrayList<DataMapItem>();
  private List<DataMapItem> groupDataItems = new ArrayList<DataMapItem>();
    
  public GraphDataMap()
  {
    super();
  }

  public void setSeriesItems(List<DataMapItem> seriesItems)
  {
    this.seriesItems = seriesItems;
  }
  
  public void addSeriesItem(DataMapItem item)
  {
    seriesItems.add(item);
  }

  public List<DataMapItem> getSeriesItems()
  {
    return seriesItems;
  }

  public void setSeriesDataItems(List<DataMapItem> seriesDataItems)
  {
    this.seriesDataItems = seriesDataItems;
  }

  public void addSeriesDataItem(DataMapItem item)
  {
    seriesDataItems.add(item);
  }


  public List<DataMapItem> getSeriesDataItems()
  {
    return seriesDataItems;
  }

  public void setGroupItems(List<DataMapItem> groupItems)
  {
    this.groupItems = groupItems;
  }

  public void addGroupItem(DataMapItem item)
  {
    groupItems.add(item);
  }

  public List<DataMapItem> getGroupItems()
  {
    return groupItems;
  }

  public void setGroupDataItems(List<DataMapItem> groupDataItems)
  {
    this.groupDataItems = groupDataItems;
  }

  public void addGroupDataItem(DataMapItem item)
  {
    groupDataItems.add(item);
  }

  public List<DataMapItem> getGroupDataItems()
  {
    return groupDataItems;
  }
}
