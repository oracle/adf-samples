/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
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
  RichInputText nameField;
  RichInputText greetingField;
  
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
    this.greetingField = greetingField;
  }

  public RichInputText getGreetingField()
  {
    return greetingField;
  }

  public void setNameField(RichInputText nameField)
  {
    this.nameField = nameField;
  }

  public RichInputText getNameField()
  {
    return nameField;
  }
}
