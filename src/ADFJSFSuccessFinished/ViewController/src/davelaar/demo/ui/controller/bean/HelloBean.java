package davelaar.demo.ui.controller.bean;

import java.util.Date;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.util.ResetUtils;


/**
 * Hello Bean class created for demo supporting presentation "What You Need To Know About JSF To Be Succesfull With ADF"
 *
 * @author Steven Davelaar
 */
public class HelloBean
{
  String name;
  Date date;
  String greeting;
  String helloMessage;

  public String sayHello()
  {
    String message = getGreeting()+" "+getName()+" on "+getDate();
    setHelloMessage(message);
    return null;
  }
  
  /** 
   * This method only clears the model values, and assumes that
   * an af:resetActionListener is used with the button that calls this method
   * That works fine as long as thri is no child af:region displayed in the page fragment
   */
  public void reset(ActionEvent event)
  {
    setName(null);
    setDate(null);
    setHelloMessage(null);
  }

  /** 
   * This method clears the model values, and uses ResetUtils.reset so
   * the current region and any child regions will all be reste
   */
  public void resetRegionSave(ActionEvent event)
  {
    setName(null);
    setDate(null);
    setHelloMessage(null);
    ResetUtils.reset(event.getComponent());
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public void setDate(Date date)
  {
    this.date = date;
  }

  public Date getDate()
  {
    return date;
  }

  public void setHelloMessage(String helloMessage)
  {
    this.helloMessage = helloMessage;
  }

  public String getHelloMessage()
  {
    return helloMessage;
  }

  public void setGreeting(String greeting)
  {
    this.greeting = greeting;
  }

  public String getGreeting()
  {
    return greeting;
  }  
}
