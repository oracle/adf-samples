<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://xmlns.oracle.com/adf/faces" prefix="af"%>
<%@ taglib uri="http://xmlns.oracle.com/adf/faces/html" prefix="afh"%>
<f:view>
  <afh:html>
    <afh:head title="TestPageReadOnly">
      <meta http-equiv="Content-Type"
            content="text/html; charset=windows-1252"/>
    </afh:head>
    <afh:body>
      <af:messages/>
      <h:form>
        <af:commandLink text="Try out the Entity-Based View Object Page"
                        action="success"/>
        <af:objectSeparator>
          <f:verbatim>
             
          </f:verbatim>
        </af:objectSeparator>
        <af:table value="#{bindings.ReadOnlyProducts.collectionModel}" var="row"
                  rows="#{bindings.ReadOnlyProducts.rangeSize}"
                  first="#{bindings.ReadOnlyProducts.rangeStart}"
                  emptyText="#{bindings.ReadOnlyProducts.viewable ? \'No rows yet.\' : \'Access Denied.\'}"
                  selectionState="#{bindings.ReadOnlyProducts.collectionModel.selectedRow}"
                  selectionListener="#{bindings.ReadOnlyProducts.collectionModel.makeCurrent}"
                  id="MyTable">
          <af:column sortProperty="ProdId" sortable="true"
                     headerText="#{bindings.ReadOnlyProducts.labels.ProdId}">
            <af:outputText value="#{row.ProdId}">
              <f:convertNumber groupingUsed="false"
                               pattern="#{bindings.ReadOnlyProducts.formats.ProdId}"/>
            </af:outputText>
          </af:column>
          <af:column sortProperty="Name" sortable="true"
                     headerText="#{bindings.ReadOnlyProducts.labels.Name}">
            <af:outputText value="#{row.Name}"/>
          </af:column>
          <f:facet name="selection">
            <af:tableSelectOne text="Select and" autoSubmit="true"/>
          </f:facet>
        </af:table>
        <af:commandButton
                          text="Rollback"
                          immediate="true"
                          action="#{TestPageReadOnly.onRollback}">
          <af:resetActionListener/>
        </af:commandButton>
        <af:panelForm partialTriggers="MyTable">
          <af:panelLabelAndMessage label="#{bindings.ProdId.label}">
            <af:outputText value="#{bindings.ProdId.inputValue}">
              <f:convertNumber groupingUsed="false"
                               pattern="#{bindings.ProdId.format}"/>
            </af:outputText>
          </af:panelLabelAndMessage>
          <af:panelLabelAndMessage label="#{bindings.Name.label}">
            <af:outputText value="#{bindings.Name.inputValue}"/>
          </af:panelLabelAndMessage>
          <f:facet name="footer">
            <af:panelButtonBar>
              <af:commandButton actionListener="#{bindings.First.execute}"
                                text="First"
                                disabled="#{!bindings.First.enabled}"/>
              <af:commandButton actionListener="#{bindings.Previous.execute}"
                                text="Previous"
                                disabled="#{!bindings.Previous.enabled}"/>
              <af:commandButton actionListener="#{bindings.Next.execute}"
                                text="Next"
                                disabled="#{!bindings.Next.enabled}"/>
              <af:commandButton actionListener="#{bindings.Last.execute}"
                                text="Last"
                                disabled="#{!bindings.Last.enabled}"/>
            </af:panelButtonBar>
          </f:facet>
        </af:panelForm>
      </h:form>
    </afh:body>
  </afh:html>
</f:view>