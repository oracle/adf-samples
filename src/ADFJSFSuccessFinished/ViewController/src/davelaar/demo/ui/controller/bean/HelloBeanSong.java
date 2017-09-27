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

  public void chooseSongChanged(ValueChangeEvent valueChangeEvent)
  {
    // Two ways to show the song drop down list:
    int option = 2;
    if (option==1)
    {
      // 1. partialTrigger on Panel Form Layout
      // Jump directly to render response so we do not get validation errors
      // on required name and date fields. These validation errors happen when 
      // panelFormLayout has partialTriggers property that listens to chhoose song checkbox.
      // The partialtrigger causes the optimized ADF-Lifecyle to also include the other fields within
      // the panelFormLayout to be processed
      // Note this only works when chooseSong field is set to immediate=true
      // we also need to update the bean value first because we also mskip the model update phase!
      setChooseSong((Boolean)valueChangeEvent.getNewValue());
      FacesContext context = FacesContext.getCurrentInstance();
      context.renderResponse();      
    }
    else
    {
      // 2. Adding the panelFormLayout programatically as partial target
      // This technique is preferred as it always works. By adding it progranmtically,
      // the ADF-optimzed lifecycle still only processes the song checkbox, so no
      // premature validations are fired.
      AdfFacesContext.getCurrentInstance().addPartialTarget(valueChangeEvent.getComponent().getParent());      
    }

  }

  public boolean isShowYouTubeFrame()
  {
    return isChooseSong() && getSongUrl()!=null;
  }

}
