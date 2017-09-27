<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://xmlns.oracle.com/adf/faces" prefix="af"%>
<%@ taglib uri="http://xmlns.oracle.com/adf/faces/html" prefix="afh"%>
<f:view>
  <f:loadBundle basename="toystore.view.Resources" var="res"/>
  <afh:html>
    <afh:head title="#{res[view.viewId]}">
      <meta http-equiv="Content-Type"
            content="text/html; charset=windows-1252"/>
    </afh:head>
    <afh:body>
      <h:form>
        <af:panelPage title="#{res[view.viewId]}">
          <f:facet name="menuGlobal">
            <af:menuButtons>
              <af:commandMenuItem text="Login" action="#{Global.onLogin}"
                                  rendered="#{!AppUserManager.signedOn}"
                                  immediate="true"/>
              <af:commandMenuItem text="Logout"
                                  rendered="#{AppUserManager.signedOn}"
                                  action="#{Global.onLogout}" immediate="true"/>
              <af:commandMenuItem text="Search" action="search"
                                  immediate="true"/>
              <af:commandMenuItem text="Your Cart" action="yourcart"
                                  immediate="true"/>
              <af:commandMenuItem text="Help" action="help"
                                  immediate="true"/>                                  
            </af:menuButtons>
          </f:facet>
          <f:facet name="branding">
            <af:objectImage source="/images/branding.gif"/>
          </f:facet>
          <f:facet name="appCopyright"/>
          <f:facet name="appPrivacy"/>
          <f:facet name="appAbout"/>
          <af:panelGroup>
            <af:panelGroup>
              <af:commandLink text="#{res.A}" action="showcategory"
                              disabled="#{param.id == \'A\'}" immediate="true">
                <f:param name="id" value="A"/>
              </af:commandLink>
              <af:commandLink text="#{res.G}" action="showcategory"
                              disabled="#{param.id == \'G\'}" immediate="true">
                <f:param name="id" value="G"/>
              </af:commandLink>
              <af:commandLink text="#{res.P}" action="showcategory"
                              disabled="#{param.id == \'P\'}" immediate="true">
                <f:param name="id" value="P"/>
              </af:commandLink>
              <af:commandLink text="#{res.T}" action="showcategory"
                              disabled="#{param.id == \'T\'}" immediate="true">
                <f:param name="id" value="T"/>
              </af:commandLink>
              <af:commandLink text="#{res.M}" action="showcategory"
                              disabled="#{param.id == \'M\'}" immediate="true">
                <f:param name="id" value="M"/>
              </af:commandLink>
              <f:facet name="separator">
                <af:objectSpacer width="5"/>
              </f:facet>
            </af:panelGroup>
            <af:objectSeparator/>
            <af:messages/>
            <af:panelForm>
              <af:inputText value="#{bindings.Username.inputValue}"
                            label="#{bindings.Username.label}"
                            required="#{bindings.Username.mandatory}"
                            columns="#{bindings.Username.displayWidth}">
                <af:validator binding="#{bindings.Username.validator}"/>
              </af:inputText>
              <af:inputText value="#{bindings.Password.inputValue}"
                            label="#{bindings.Password.label}"
                            required="#{bindings.Password.mandatory}"
                            columns="#{bindings.Password.displayWidth}"
                            secret="true">
                <af:validator binding="#{bindings.Password.validator}"/>
              </af:inputText>
              <af:inputText value="#{bindings.Email.inputValue}"
                            label="#{bindings.Email.label}"
                            required="#{bindings.Email.mandatory}"
                            columns="#{bindings.Email.displayWidth}">
                <af:validator binding="#{bindings.Email.validator}"/>
              </af:inputText>
              <af:inputText value="#{bindings.Firstname.inputValue}"
                            label="#{bindings.Firstname.label}"
                            required="#{bindings.Firstname.mandatory}"
                            columns="#{bindings.Firstname.displayWidth}">
                <af:validator binding="#{bindings.Firstname.validator}"/>
              </af:inputText>
              <af:inputText value="#{bindings.Lastname.inputValue}"
                            label="#{bindings.Lastname.label}"
                            required="#{bindings.Lastname.mandatory}"
                            columns="#{bindings.Lastname.displayWidth}">
                <af:validator binding="#{bindings.Lastname.validator}"/>
              </af:inputText>
              <af:inputText value="#{bindings.Address.inputValue}"
                            label="#{bindings.Address.label}"
                            required="#{bindings.Address.mandatory}"
                            columns="#{bindings.Address.displayWidth}">
                <af:validator binding="#{bindings.Address.validator}"/>
              </af:inputText>
              <af:inputText value="#{bindings.City.inputValue}"
                            label="#{bindings.City.label}"
                            required="#{bindings.City.mandatory}"
                            columns="#{bindings.City.displayWidth}">
                <af:validator binding="#{bindings.City.validator}"/>
              </af:inputText>
              <af:inputText value="#{bindings.State.inputValue}"
                            label="#{bindings.State.label}"
                            required="#{bindings.State.mandatory}"
                            columns="#{bindings.State.displayWidth}">
                <af:validator binding="#{bindings.State.validator}"/>
              </af:inputText>
              <af:inputText value="#{bindings.Zip.inputValue}"
                            label="#{bindings.Zip.label}"
                            required="#{bindings.Zip.mandatory}"
                            columns="#{bindings.Zip.displayWidth}">
                <af:validator binding="#{bindings.Zip.validator}"/>
              </af:inputText>
              <af:selectOneChoice value="#{bindings.AccountsCountry.inputValue}"
                                  label="#{bindings.AccountsCountry.label}">
                <f:selectItems value="#{bindings.AccountsCountry.items}"/>
              </af:selectOneChoice>
              <af:inputText value="#{bindings.Phone.inputValue}"
                            label="#{bindings.Phone.label}"
                            required="#{bindings.Phone.mandatory}"
                            columns="#{bindings.Phone.displayWidth}">
                <af:validator binding="#{bindings.Phone.validator}"/>
              </af:inputText>
              <af:commandButton
                                text="Register"
                                action="#{RegisterPage.onRegister}"/>
            </af:panelForm>
          </af:panelGroup>
        </af:panelPage>
      </h:form>
    </afh:body>
  </afh:html>
</f:view>
