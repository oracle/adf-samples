<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://xmlns.oracle.com/adf/faces" prefix="af"%>
<%@ taglib uri="http://xmlns.oracle.com/adf/faces/html" prefix="afh"%>
<f:view>
  <afh:html>
    <afh:head title="untitled1">
      <meta http-equiv="Content-Type"
            content="text/html; charset=windows-1252"/>
    </afh:head>
    <afh:body>
      <af:messages/>
      <h:form>
        <af:panelBox text="NOTE" width="100%">
          <af:outputText value="You typically would not have a button that releases the application module in stateless mode. This example page is just to help you understand how the prepareSession(), afterConnect(), passivateState(), activateState(), and reset() methods of the ApplicationModule come into play when used in a web application."/>
        </af:panelBox>
        <af:objectSeparator/>
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
              <af:commandButton text="Release Stateless"
                                action="#{Untitled1.onClickReleaseStateless}"/>
              <af:commandButton text="Invalidate Session And Redirect"
                                action="#{Untitled1.onClickInvalidateSessionAndRedirectButton}"/>
            </af:panelButtonBar>
          </f:facet>
        </af:panelForm>
        <af:objectSeparator/>
        <af:panelTip>
          <af:outputText value="Try using the application module configuration editor's  'Pooling and Scalability' tab to change the settings of the following properties and notice how they affect the console log output."/>
          <af:panelList>
            <af:outputText value="Disconnect Application Module Upon Release (default = unchecked -> jbo.doconnectionpooling=false)"/>
            <af:outputText value="Failover Transaction State Upon Managed Release (default = unchecked -> jbo.dofailover=false)"/>
            <af:outputText value="Enable Application Module Pooling (default = checked -> jbo.ampool.doampooling=true)"/>
          </af:panelList>
        </af:panelTip>
      </h:form>
    </afh:body>
  </afh:html>
</f:view>
<%-- 
  oracle-jdev-comment:preferred-managed-bean-name:Untitled1
--%>
<%-- 
  oracle-jdev-comment:preferred-managed-bean-name:Untitled1
--%>