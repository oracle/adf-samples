/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view;

import javax.faces.event.ActionEvent;

public class SayHello
{
  
  private String name;
  private String helloMessage;
  
  public SayHello()
  {
  }

  public void sayHello(ActionEvent actionEvent)
  {
    System.err.println("Hello "+getName());
  }

  public String sayHello()
  {
    setHelloMessage("Hello "+getName());
    return null;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public void setHelloMessage(String helloMessage)
  {
    this.helloMessage = helloMessage;
  }

  public String getHelloMessage()
  {
    return helloMessage;
  }

  public void reset(ActionEvent actionEvent)
  {
    setName(null);
    setHelloMessage(null);
    // Add event code here...
  }
}
