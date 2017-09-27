/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.managed;

public class UserInfo {
  String nameSearch;
  boolean clearPageDefinitionVariablesOnSubmit = false;
  boolean clearPageDefinitionVariablesOnPageLoad = false;
  public UserInfo() {
  }
  public void setNameSearch(String valuePrefix) {
    this.nameSearch = valuePrefix;
  }
  public String getNameSearch() {
    return nameSearch;
  }
  public void setClearPageDefinitionVariablesOnSubmit(boolean clearPageDefinitionVariablesOnSubmit) {
    this.clearPageDefinitionVariablesOnSubmit = clearPageDefinitionVariablesOnSubmit;
  }
  public boolean isClearPageDefinitionVariablesOnSubmit() {
    return clearPageDefinitionVariablesOnSubmit;
  }
  public void setClearPageDefinitionVariablesOnPageLoad(boolean clearPageDefinitionVariablesOnPageLoad) {
    this.clearPageDefinitionVariablesOnPageLoad = clearPageDefinitionVariablesOnPageLoad;
  }
  public boolean isClearPageDefinitionVariablesOnPageLoad() {
    return clearPageDefinitionVariablesOnPageLoad;
  }
}
