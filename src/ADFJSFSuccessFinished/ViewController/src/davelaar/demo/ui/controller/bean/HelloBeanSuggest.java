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
public class HelloBeanSuggest
{
  String name;
  Date date;
  String greeting;
  String helloMessage;
  private ComponentReference nameField;
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

  public void suggestPreferredGreeting(ActionEvent event)
  {
    // we can only get the submitted value (getNameField().getSubmittedValue()) at this point because we are in 
    // apply request values phase and conversion of the submitted type to the component type has not been done yet. 
    // With this name field it doesn't matter because it is a string and no validations are defined on the field, 
    // but with a date field, this would be an issue, because we do want to pass an invalid submittedValue
    // so, the cleanest way to do this is to first procewss the validators and then get the local 
    // (validated and converted) value. Also, by calling the validator first, we will get a nice reuired error
    // when the suggest button is clicked while the name field was empty!

    // the additional lines below are included to illustrate the (programmatic) JSF lifecycle steps we take
    // when debugging line-by-line through this code
    String submittedValue = (String) getNameField().getSubmittedValue();
    Object localValue = getNameField().getLocalValue();
    Object value = getNameField().getValue();
    getNameField().processValidators(FacesContext.getCurrentInstance());
    if (getNameField().isValid())
    {
      localValue = getNameField().getLocalValue(); // will have new value now
      value = getNameField().getValue(); // will have new value as well 
      getNameField().processUpdates(FacesContext.getCurrentInstance());
      localValue = getNameField().getLocalValue(); // value is cleared, because value has been pushed to model
      submittedValue = (String) getNameField().getSubmittedValue(); // value is cleared, because value has been pushed to model
      value = getNameField().getValue(); // will still have new value now, model update executed: causes setName() to be called      
      String greeting = getPreferredGreeting((String) value);      
      // we can either set the greeting in the bean and then force the greeting field to reset its local value,
//      setGreeting(greeting);
//      getGreetingField().resetValue();

      // or we can directly set the submitted value on the greeting field
      // note that calling setValue wouldn't always work because the UI component local value
      // takes orecedence and will be different when the user modifed the greeting just before
      // clicking the Suggest button! 
      getGreetingField().setSubmittedValue(greeting);      
    }
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

  public void setNameField(RichInputText nameField)
  {
    this.nameField = ComponentReference.newUIComponentReference(nameField);
  }

  public RichInputText getNameField()
  {
    if (nameField!=null)
    {
      return (RichInputText) nameField.getComponent();
    }
    return null;
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
