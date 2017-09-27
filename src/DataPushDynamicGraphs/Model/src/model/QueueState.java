package model;

import java.math.BigDecimal;

import java.util.Queue;

public class QueueState extends ActiveDataObject
{

//  Queue   Agent State     Count   Percent
 
  private String queue;                          
  private String state;
  private int count;
  private BigDecimal percent;
  
  public QueueState()
  {
    super();
  }

  public QueueState(String queue, String state, int count, BigDecimal percent)
  {
    super();
    this.queue = queue;
    this.state=state;
    this.count= count;
    this.percent = percent;
  }

  public void setState(String state)
  {
    this.state = state;
  }

  public String getState()
  {
    return state;
  }

  public void setQueue(String queue)
  {
    this.queue = queue;
  }

  public String getQueue()
  {
    return queue;
  }

  public void setCount(int count)
  {
    this.count = count;
  }

  public int getCount()
  {
    return count;
  }

  public void setPercent(BigDecimal percent)
  {
    this.percent = percent;
  }

  public BigDecimal getPercent()
  {
    return percent;
  }
}
