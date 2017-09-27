package davelaar.demo.ui.controller.bean;

import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.util.ResetUtils;

import org.apache.myfaces.trinidad.util.ComponentReference;


/**
 * Hello Bean class created for demo supporting presentation "What You Need To Know About JSF To Be Succesfull With ADF"
 *
 * @author Steven Davelaar
 */
public class HelloBeanAutoSuggest
{
  String name;
  Date date;
  String greeting;
  String helloMessage;
  private ComponentReference greetingField;

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

  public void nameChanged(ValueChangeEvent valueChangeEvent)
  {
    String name = (String) valueChangeEvent.getNewValue();
    String greeting = getPreferredGreeting(name);      
    setGreeting(greeting);
    // we need to programatically add greeting field as partial target. If we would add a 
    // partialTrigger property to the greeting field that points to the name field, we would
    // get a required field error when tabbing out the name field, because then the ADF-optimized
    // JSF lifecycle will include the greeting field in its lifecycle processing!
    AdfFacesContext.getCurrentInstance().addPartialTarget(getGreetingField());
    // Need to call reset value for following scenario: user changes or clears greeting manually in the page
    // then he enters a new name, and without tabbing out of the field, he directly clicks sayHello button
    // the happens the following: in process validations phase, the nameChanged metod fired which set the correct greeting
    // but as greeting was changed manually in the page, then in the updfate model phase, the manually entered value
    // overwrites the value derived from the new name. To fix this, the greetigns UI component must be synched with underlying
    // model value, and then it will no longer call setGreeting in model update phase!    
    getGreetingField().resetValue();
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

  public void setGreetingField(RichInputText greetingField)
  {
    this.greetingField = ComponentReference.newUIComponentReference(greetingField);
  }

  public RichInputText getGreetingField()
  {
    if (greetingField!=null)
    {
      return (RichInputText) greetingField.getComponent();
    }
    return null;
  }
  
}
