/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;
import javax.faces.event.ValueChangeEvent;
public class TestPage {
  Integer mode ;
  Integer mode2 ;
  String name1;
  String name2;
  String name3;
  String name4;
  
  public TestPage() {
  }
  public void setMode(Integer mode) {
    System.out.println("### Mode set to "+ mode);
    this.mode = mode;
  }
  public Integer getMode() {
    return mode;
  }
  public void setMode2(Integer mode2) {
    System.out.println("### Mode2 set to "+ mode);
    this.mode2 = mode2;
  }
  public Integer getMode2() {
    return mode2;
  }
  public void onOccurrenceDropdownValueChanged(ValueChangeEvent valueChangeEvent) {
    System.out.println("### Dropdown value changed to "+ valueChangeEvent.getNewValue());
  }
  public void onOccurrenceRadioValueChanged(ValueChangeEvent valueChangeEvent) {
    System.out.println("### Radio value changed to "+ valueChangeEvent.getNewValue());
  }
  public String onButton1Clicked() {
    // Add event code here...
    System.out.println("### Button1 Clicked");
    return null;
  }
  public String onButton2Clicked() {
    // Add event code here...
    System.out.println("### Button2 Clicked");
    return null;
  }
  public String onButton3Clicked() {
    // Add event code here...
    System.out.println("### Button3 Clicked");
    return null;
  }
  public String onButton4Clicked() {
    // Add event code here...
    System.out.println("### Button4 Clicked");
    return null;
  }
  public void setName1(String name1) {
    this.name1 = name1;
  }
  public String getName1() {
    return name1;
  }
  public void setName2(String name2) {
    this.name2 = name2;
  }
  public String getName2() {
    return name2;
  }
  public void setName3(String name3) {
    this.name3 = name3;
  }
  public String getName3() {
    return name3;
  }
  public void setName4(String name4) {
    this.name4 = name4;
  }
  public String getName4() {
    return name4;
  }
}
