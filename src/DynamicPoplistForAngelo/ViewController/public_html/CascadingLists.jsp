<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://xmlns.oracle.com/adf/faces" prefix="af"%>
<%@ taglib uri="http://xmlns.oracle.com/adf/faces/html" prefix="afh"%>
<f:view>
  <afh:html>
    <afh:head title="CascadingLists">
      <meta http-equiv="Content-Type"
            content="text/html; charset=windows-1252"/>
    </afh:head>
    <afh:body>
      <af:messages/>
      <h:form>
        <af:panelGroup>
          <af:panelHeader text="Edit Locations" partialTriggers="navList2"/>
        </af:panelGroup>
        <af:outputText value="This counter will update when the page refreshes and stay fixed when PPR happens on a sub-part of this page:  [ #{PPRDemo.counter} ]"/>
        <af:panelForm>
          <af:inputText value="#{bindings.LocationId.inputValue}"
                        label="#{bindings.LocationId.label}"
                        required="#{bindings.LocationId.mandatory}"
                        columns="#{bindings.LocationId.displayWidth}"
                        readOnly="true">
            <af:validator binding="#{bindings.LocationId.validator}"/>
            <f:convertNumber groupingUsed="false"
                             pattern="#{bindings.LocationId.format}"/>
          </af:inputText>
          <af:inputText value="#{bindings.StreetAddress.inputValue}"
                        label="#{bindings.StreetAddress.label}"
                        required="#{bindings.StreetAddress.mandatory}"
                        columns="#{bindings.StreetAddress.displayWidth}">
            <af:validator binding="#{bindings.StreetAddress.validator}"/>
          </af:inputText>
          <af:inputText value="#{bindings.City.inputValue}"
                        label="#{bindings.City.label}"
                        showRequired="#{bindings.City.mandatory}"
                        columns="#{bindings.City.displayWidth}">
            <af:validator binding="#{bindings.City.validator}"/>
          </af:inputText>
          <af:inputText value="#{bindings.StateProvince.inputValue}"
                        label="#{bindings.StateProvince.label}"
                        required="#{bindings.StateProvince.mandatory}"
                        columns="#{bindings.StateProvince.displayWidth}">
            <af:validator binding="#{bindings.StateProvince.validator}"/>
          </af:inputText>
          <af:inputText value="#{bindings.PostalCode.inputValue}"
                        label="#{bindings.PostalCode.label}"
                        required="#{bindings.PostalCode.mandatory}"
                        columns="#{bindings.PostalCode.displayWidth}">
            <af:validator binding="#{bindings.PostalCode.validator}"/>
          </af:inputText>
          <af:panelLabelAndMessage label="#{bindings.CountryId.label}"
                                   partialTriggers="navList1"
                                   showRequired="true">
            <af:panelHorizontal>
              <af:selectOneChoice id="navList1" autoSubmit="true"
                                  value="#{bindings.Regions.inputValue}"
                                  valueChangeListener="#{CascadingLists.onRegionListChanged}">
                <f:selectItems value="#{bindings.Regions.items}"/>
              </af:selectOneChoice>
              <af:selectOneChoice value="#{bindings.CountryId.inputValue}"
                                  partialTriggers="navList1" id="list2"
                                  autoSubmit="true"
                                  valueChangeListener="#{CascadingLists.onCountryChanged}">
                <f:selectItems value="#{bindings.CountryId.items}"/>
              </af:selectOneChoice>
            </af:panelHorizontal>
          </af:panelLabelAndMessage>
          <f:facet name="footer">
            <af:panelButtonBar>
              <af:commandButton actionListener="#{bindings.Commit.execute}"
                                text="Commit"/>
              <af:commandButton actionListener="#{bindings.Rollback.execute}"
                                text="Rollback"
                                disabled="false" immediate="true">
                <af:resetActionListener/>
              </af:commandButton>
              <af:commandButton actionListener="#{bindings.Create.execute}"
                                text="Create"
                                disabled="#{!bindings.Create.enabled}"/>
              <af:commandButton actionListener="#{bindings.Delete.execute}"
                                text="Delete"
                                disabled="#{!bindings.Delete.enabled}"
                                immediate="true"/>
              <af:commandButton actionListener="#{bindings.Previous.execute}"
                                text="Previous"
                                disabled="#{!bindings.Previous.enabled}"/>
              <af:commandButton actionListener="#{bindings.Next.execute}"
                                text="Next"
                                disabled="#{!bindings.Next.enabled}"/>
            </af:panelButtonBar>
          </f:facet>
        </af:panelForm>
      </h:form>
    </afh:body>
  </afh:html>
</f:view>