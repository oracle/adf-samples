/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package davelaar.demo.ui.controller.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.util.ResetUtils;

import org.apache.myfaces.trinidad.util.ComponentReference;


/**
 * Hello Bean class created for demo supporting presentation "What You Need To Know About JSF To Be Succesfull With ADF"
 *
 * @author Steven Davelaar
 */
public class HelloBeanSong
{
  String name;
  String helloMessage;
  Date date;
  boolean chooseSong = false;
  List<SelectItem> songs = new ArrayList<SelectItem>();
  String songUrl;
  String greeting;
  private ComponentReference greetingField;

  @PostConstruct
  public void init()
  {
    songs.add(new SelectItem("http://www.youtube.com/embed/GvF6ezDRSjE?feature=player_detailpage","I Just Came To Say hello - VDJ Xone"));
    songs.add(new SelectItem("http://www.youtube.com/embed/AiC7ZX5K9L4?feature=player_detailpage","Hello - Lionel Ritchie"));
    songs.add(new SelectItem("http://www.youtube.com/embed/CpTLdI3Jpn4?feature=player_detailpage","The Hello Song"));    
    songs.add(new SelectItem("http://www.youtube.com/embed/W_x-HF8gbjw?feature=player_detailpage","Hello Party Mix"));    

  }
  
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
    setChooseSong(false);
    setSongUrl(null);
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

  public void setChooseSong(boolean chooseSong)
  {
    this.chooseSong = chooseSong;
  }

  public boolean isChooseSong()
  {
    return chooseSong;
  }

  public void setSongs(List<SelectItem> songs)
  {
    this.songs = songs;
  }

  public List<SelectItem> getSongs()
  {
    return songs;
  }

  public void setSongUrl(String songUrl)
  {
    this.songUrl = songUrl;
  }

  public String getSongUrl()
  {
    return songUrl;
  }

  public void setGreeting(String greeting)
  {
    this.greeting = greeting;
  }

  public String getGreeting()
  {
    return greeting;
  }
  
  public void suggestPreferredGreeting(String name)
  {
    if ("Steven".equalsIgnoreCase(name))
    {
      setGreeting("Goedendag");
    }
    else if ("Angela".equalsIgnoreCase(name))
    {
      setGreeting("Gutentag");
    }
    else if ("Nathalie".equalsIgnoreCase(name))
    {
      setGreeting("Bonjour");
    }
    else if ("Barack".equalsIgnoreCase(name))
    {
      setGreeting("Hi");
    }
    else 
    {
      setGreeting("Hello");
    }
  }

  public void nameChanged(ValueChangeEvent valueChangeEvent)
  {
    setName((String) valueChangeEvent.getNewValue());
    suggestPreferredGreeting(getName());
    getGreetingField().resetValue();
    AdfFacesContext.getCurrentInstance().addPartialTarget(getGreetingField());
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

  public boolean isShowYouTubeFrame()
  {
    return isChooseSong() && getSongUrl()!=null;
  }

  public void chooseSongChanged(ValueChangeEvent valueChangeEvent)
  {
    AdfFacesContext context = AdfFacesContext.getCurrentInstance();
    context.addPartialTarget(valueChangeEvent.getComponent().getParent());
  }

}
