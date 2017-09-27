<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/trinidad/html" prefix="trh"%>
<%@ taglib uri="http://myfaces.apache.org/trinidad" prefix="tr"%>
<f:view>
  <trh:html>
    <trh:head title="login">
      <meta http-equiv="Content-Type"
            content="text/html; charset=windows-1252"/>
    </trh:head>
    <trh:body>
      <h:form id="form">
        <trh:tableLayout width="50%" halign="center">
          <trh:rowLayout>
            <trh:cellFormat>
              <!--TODO: Migrator from ADF Faces 10.1.3 to Trinidad removed the following attribute from the node below since it does not exist in Trinidad anymore
width="100%"-->
              <tr:panelBox text="Failed Login"
                           rendered="#{not empty param.failed}">
                <tr:outputText value="Invalid username/password... Try again."/>
              </tr:panelBox>
            </trh:cellFormat>
          </trh:rowLayout>
          <trh:rowLayout>
            <trh:cellFormat>
              <tr:panelFormLayout id="form">
                <f:facet name="footer">
                  <tr:commandButton text="Login" action="main"/>
                </f:facet>
                <tr:inputText id="username" label="Username"
                              value="#{bindings.username.inputValue}"/>
                <tr:inputText id="password" label="Password" secret="true"
                              value="#{bindings.password.inputValue}"/>
                <tr:inputHidden id="_loginpage" value="true"/>
              </tr:panelFormLayout>
            </trh:cellFormat>
          </trh:rowLayout>
        </trh:tableLayout>
      </h:form>
    </trh:body>
  </trh:html>
</f:view>