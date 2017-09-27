<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://xmlns.oracle.com/adf/faces" prefix="af"%>
<%@ taglib uri="http://xmlns.oracle.com/adf/faces/html" prefix="afh"%>
<f:view>
  <afh:html>
    <afh:head title="TestPage">
      <meta http-equiv="Content-Type"
            content="text/html; charset=windows-1252"/>
    </afh:head>
    <afh:body>
      <af:messages/>
      <h:form>
        <af:commandLink text="Try out the Read-Only Page" action="success"/>
        <af:objectSeparator/>
        <af:table value="#{bindings.Products.collectionModel}" var="row"
                  rows="#{bindings.Products.rangeSize}"
                  first="#{bindings.Products.rangeStart}"
                  emptyText="#{bindings.Products.viewable ? \'No rows yet.\' : \'Access Denied.\'}"
                  selectionState="#{bindings.Products.collectionModel.selectedRow}"
                  selectionListener="#{bindings.Products.collectionModel.makeCurrent}"
                  id="products">
          <af:column headerText="#{bindings.Products.labels.ProdId}"
                     sortProperty="ProdId" sortable="true">
            <af:outputText value="#{row.ProdId}">
              <f:convertNumber groupingUsed="false"
                               pattern="#{bindings.Products.formats.ProdId}"/>
            </af:outputText>
          </af:column>
          <af:column headerText="#{bindings.Products.labels.Name}"
                     sortProperty="Name" sortable="true">
            <af:outputText value="#{row.Name}"/>
          </af:column>
          <af:column headerText="#{bindings.Products.labels.Image}"
                     sortProperty="Image" sortable="true">
            <af:outputText value="#{row.Image}"/>
          </af:column>
          <af:column headerText="#{bindings.Products.labels.Description}"
                     sortProperty="Description" sortable="true">
            <af:outputText value="#{row.Description}"/>
          </af:column>
          <f:facet name="selection">
            <af:tableSelectOne text="Select and" autoSubmit="true">
              <af:commandButton actionListener="#{bindings.Commit.execute}"
                                text="Commit"
                                disabled="#{!bindings.Commit.enabled}"/>
              <af:commandButton text="Rollback" immediate="true"
                                action="#{TestPage.onRollback}">
                <af:resetActionListener/>
              </af:commandButton>
            </af:tableSelectOne>
          </f:facet>
        </af:table>
        <af:panelForm partialTriggers="products">
          <af:inputText value="#{bindings.ProdId.inputValue}"
                        label="#{bindings.ProdId.label}"
                        required="#{bindings.ProdId.mandatory}"
                        columns="#{bindings.ProdId.displayWidth}">
            <af:validator binding="#{bindings.ProdId.validator}"/>
            <f:convertNumber groupingUsed="false"
                             pattern="#{bindings.ProdId.format}"/>
          </af:inputText>
          <af:inputText value="#{bindings.Name.inputValue}"
                        label="#{bindings.Name.label}"
                        required="#{bindings.Name.mandatory}"
                        columns="#{bindings.Name.displayWidth}">
            <af:validator binding="#{bindings.Name.validator}"/>
          </af:inputText>
          <af:inputText value="#{bindings.Image.inputValue}"
                        label="#{bindings.Image.label}"
                        required="#{bindings.Image.mandatory}"
                        columns="#{bindings.Image.displayWidth}">
            <af:validator binding="#{bindings.Image.validator}"/>
          </af:inputText>
          <af:inputText value="#{bindings.Description.inputValue}"
                        label="#{bindings.Description.label}"
                        required="#{bindings.Description.mandatory}"
                        columns="#{bindings.Description.displayWidth}">
            <af:validator binding="#{bindings.Description.validator}"/>
          </af:inputText>
          <f:facet name="footer"/>
        </af:panelForm>
      </h:form>
    </afh:body>
  </afh:html>
</f:view>