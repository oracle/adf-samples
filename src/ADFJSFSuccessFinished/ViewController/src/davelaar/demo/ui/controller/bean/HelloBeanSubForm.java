package davelaar.demo.ui.controller.bean;

import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.util.ResetUtils;

import org.apache.myfaces.trinidad.util.ComponentReference;


/**
 * Hello Bean class created for demo supporting presentation "What You Need To Know About JSF To Be Succesfull With ADF"
 *
 * @author Steven Davelaar
 */
public class HelloBeanSubForm
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
  
  public void reset(ActionEvent event)
  {
    setName(null);
    setDate(null);
    setGreeting(null);
    setHelloMessage(null);
    // the component passed in is NOT the real strating component for reset action
    // It is used to traverse up the component tree until a a form, subform, region 
    // popup, carrousel or panelCollection is found, which is used as the real
    // starting point in the UI tree
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

  public void suggestPreferredGreeting(ActionEvent event)
  {
    String greeting = getPreferredGreeting(getName());      
    setGreeting(greeting);
  }

  private String getPreferredGreeting(String name)
  {
    if ("Steven".equalsIgnoreCase(name))
    {
      return "Goedendag";
    }
    else if ("Angela".equalsIgnoreCase(name))
    {
      return "Gutentag";
    }
    else if ("Nathalie".equalsIgnoreCase(name))
    {
      return "Bonjour";
    }
    else if ("Barack".equalsIgnoreCase(name))
    {
      return "Hi";
    }
    else 
    {
      return "Hello";
    }    
  }

}

