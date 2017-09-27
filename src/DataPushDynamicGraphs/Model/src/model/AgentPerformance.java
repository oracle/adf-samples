package model;

public class AgentPerformance extends ActiveDataObject
{
  
  private String agentName;
  private int todayHeldContacts;
  private int todayRedirects;
  private int todayTransfers;
  
  public AgentPerformance()
  {
    super();
  }

  public AgentPerformance(String name, int todayHeldContacts, int todayRedirects, int todayTransfers)
  {
    super();
    this.agentName = name;
    this.todayHeldContacts = todayHeldContacts;
    this.todayRedirects = todayRedirects;
    this.todayTransfers = todayTransfers;
  }

  public void setAgentName(String agentName)
  {
    this.agentName = agentName;
  }

  public String getAgentName()
  {
    return agentName;
  }

  public void setTodayHeldContacts(int todayHeldContacts)
  {
    this.todayHeldContacts = todayHeldContacts;
  }

  public int getTodayHeldContacts()
  {
    return todayHeldContacts;
  }

  public void setTodayRedirects(int todayRedirects)
  {
    this.todayRedirects = todayRedirects;
  }

  public int getTodayRedirects()
  {
    return todayRedirects;
  }

  public void setTodayTransfers(int todayTransfers)
  {
    this.todayTransfers = todayTransfers;
  }

  public int getTodayTransfers()
  {
    return todayTransfers;
  }
}
