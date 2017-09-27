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
        <af:panelForm>
          <af:panelLabelAndMessage label="#{bindings.Deptno.label}">
            <af:outputText value="#{bindings.Deptno.inputValue}">
              <f:convertNumber groupingUsed="false"
                               pattern="#{bindings.Deptno.format}"/>
            </af:outputText>
          </af:panelLabelAndMessage>
          <af:panelLabelAndMessage label="#{bindings.Dname.label}">
            <af:outputText value="#{bindings.Dname.inputValue}"/>
          </af:panelLabelAndMessage>
          <af:panelLabelAndMessage label="#{bindings.Loc.label}">
            <af:outputText value="#{bindings.Loc.inputValue}"/>
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
        <af:commandButton text="Click to Set Filter Session Attribute">
          <af:setActionListener from="#{\'Foo\'}" to="#{sessionScope.Filter}"/>
        </af:commandButton>
      </h:form>
    </afh:body>
  </afh:html>
</f:view>