package model;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

public class ActiveDataObjectService
{

  List<QueueState> queueStates = new ArrayList<QueueState>();
  List<AgentPerformance> agentPerformances = new ArrayList<AgentPerformance>();
  
  public static final String QUEUE_STATE_DATA_OBJECT = QueueState.class.getName();
  public static final String AGENT_PERFORMANCE_DATA_OBJECT = AgentPerformance.class.getName();

  private List<ActiveDataObjectChangeListener> queueStateListeners =
    new ArrayList<ActiveDataObjectChangeListener>();

  private List<ActiveDataObjectChangeListener> agentPerformanceListeners =
    new ArrayList<ActiveDataObjectChangeListener>();

  private boolean queueStateUpdatesRunning = false;
  private boolean agentPerformanceUpdatesRunning = false;

  /**
   * If first listener is added, the update process is started
   * @param listener
   */
  public void addChangeListener(ActiveDataObjectChangeListener listener)
  {
    if (QUEUE_STATE_DATA_OBJECT.equals(listener.getDataObjectName()))
    {
      if (queueStateListeners.size() == 0 && !queueStateUpdatesRunning)
      {
        queueStateUpdatesRunning = true;
        startQueueStateUpdates();
      }
      queueStateListeners.add(listener);      
    }
    else if (AGENT_PERFORMANCE_DATA_OBJECT.equals(listener.getDataObjectName()))
    {
      if (agentPerformanceListeners.size() == 0 && !agentPerformanceUpdatesRunning)
      {
        agentPerformanceUpdatesRunning = true;
        startAgentPerformanceUpdates();
      }
      agentPerformanceListeners.add(listener);      
    }
  }

  public void removeChangeListener(ActiveDataObjectChangeListener listener)
  {
    if (QUEUE_STATE_DATA_OBJECT.equals(listener.getDataObjectName()))
    {
      queueStateListeners.remove(listener);
    }
    else if (AGENT_PERFORMANCE_DATA_OBJECT.equals(listener.getDataObjectName()))
    {
      agentPerformanceListeners.remove(listener);
    }
  }

  public ActiveDataObjectService()
  {
    super();
    initQueueStates();
    initAgentPerformances();
  }

  private void initQueueStates()
  {
    queueStates.add(new QueueState("English sales", "Idle", 2,
                                   new BigDecimal(14.3)));
    queueStates.add(new QueueState("English sales", "Active", 4,
                                   new BigDecimal(28.6)));
    queueStates.add(new QueueState("English sales", "Aftercall work", 3,
                                   new BigDecimal(21.4)));
    queueStates.add(new QueueState("English sales", "Aux state 1", 1,
                                   new BigDecimal(7.1)));
    queueStates.add(new QueueState("English sales", "Aux state 2", 0,
                                   new BigDecimal(0)));
    queueStates.add(new QueueState("English sales", "Hold", 2,
                                   new BigDecimal(14.3)));
    queueStates.add(new QueueState("English sales", "Engaged", 2,
                                   new BigDecimal(14.3)));
    queueStates.add(new QueueState("Spanish sales", "Idle", 1,
                                   new BigDecimal(3.7)));
    queueStates.add(new QueueState("Spanish sales", "Active", 3,
                                   new BigDecimal(11.1)));
    queueStates.add(new QueueState("Spanish sales", "Aftercall work", 4,
                                   new BigDecimal(14.8)));
    queueStates.add(new QueueState("Spanish sales", "Aux state 1", 7,
                                   new BigDecimal(25.9)));
    queueStates.add(new QueueState("Spanish sales", "Aux state 2", 3,
                                   new BigDecimal(11.1)));
    queueStates.add(new QueueState("Spanish sales", "Hold", 2,
                                   new BigDecimal(7.4)));
    queueStates.add(new QueueState("Spanish sales", "Engaged", 7,
                                   new BigDecimal(25.9)));
  }

  private void initAgentPerformances()
  {
    agentPerformances.add(new AgentPerformance("Barret Davis", 2,4,8));
    agentPerformances.add(new AgentPerformance("Brian Reynolds", 6,1,4));
    agentPerformances.add(new AgentPerformance("Sean Burke", 5,5,6));
    agentPerformances.add(new AgentPerformance("Dan Prentice", 9,2,4));
    agentPerformances.add(new AgentPerformance("Gabe Stanek", 5,3,9));
    agentPerformances.add(new AgentPerformance("Steven Davelaar", 1,7,6));
    agentPerformances.add(new AgentPerformance("Ronan Fox", 8,4,6));
    agentPerformances.add(new AgentPerformance("Jeff Chu", 8,5,4));
    agentPerformances.add(new AgentPerformance("Adrian Cooley", 3,9,2));
    agentPerformances.add(new AgentPerformance("Eric Belmon", 5,5,4));
  }

  public List<QueueState> getQueueStates()
  {
    return queueStates;
  }

  public List<AgentPerformance> getAgentPerformances()
  {
    return agentPerformances;
  }

  private void startQueueStateUpdates()
  {
    Runnable dataChanger = new Runnable()
    {
      public void run()
      {
        try
        {
          // wait 5 seconds with updates
          Thread.sleep(5000L);
          for (int i = 0; i < 100; i++)
          {
            int index = ((int) (Math.random() * 10));
            int count = ((int) (Math.random() * 10));
            QueueState as = queueStates.get(index);
            as.setCount(count);
            for (ActiveDataObjectChangeListener listener: queueStateListeners)
            {
              listener.onUpdate(index, as);
            }

            Thread.sleep(1000L);
          }
          queueStateUpdatesRunning = false;
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
    };
    // start the process
    Thread changesThread = new Thread(dataChanger);
    changesThread.start();


  }

  private void startAgentPerformanceUpdates()
  {
    Runnable dataChanger = new Runnable()
    {
      public void run()
      {
        try
        {
          // wait 5 seconds with updates
          Thread.sleep(5000L);
          for (int i = 0; i < 100; i++)
          {
            int index = ((int) (Math.random() * 10));
            AgentPerformance ap = agentPerformances.get(index);
            if (ap==null)
            {
              continue;
            }
            int contacts  = ((int) (Math.random() * 10));
            ap.setTodayHeldContacts(contacts);
            int transfers  = ((int) (Math.random() * 10));
            ap.setTodayTransfers(transfers);
            int redirects  = ((int) (Math.random() * 10));
            ap.setTodayRedirects(redirects);

            for (ActiveDataObjectChangeListener listener: agentPerformanceListeners)
            {
              listener.onUpdate(index, ap);
            }

            Thread.sleep(1000L);
          }
          agentPerformanceUpdatesRunning = false;
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
    };
    // start the process
    Thread changesThread = new Thread(dataChanger);
    changesThread.start();


  }

}
