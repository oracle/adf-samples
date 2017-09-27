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
              <af:commandMenuItem text="Edit Profile"
                                  action="editaccount"
                                  rendered="#{AppUserManager.signedOn}"/>
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
              <af:commandMenuItem text="Help" action="help" immediate="true"/>
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
              <af:inputText value="#{bindings.Shipaddr1.inputValue}"
                            label="#{bindings.Shipaddr1.label}"
                            required="#{bindings.Shipaddr1.mandatory}"
                            columns="#{bindings.Shipaddr1.displayWidth}"
                            rows="1">
                <af:validator binding="#{bindings.Shipaddr1.validator}"/>
              </af:inputText>
              <af:inputText value="#{bindings.Shipcity.inputValue}"
                            label="#{bindings.Shipcity.label}"
                            required="#{bindings.Shipcity.mandatory}"
                            columns="#{bindings.Shipcity.displayWidth}">
                <af:validator binding="#{bindings.Shipcity.validator}"/>
              </af:inputText>
              <af:inputText value="#{bindings.Shipstate.inputValue}"
                            label="#{bindings.Shipstate.label}"
                            required="#{bindings.Shipstate.mandatory}"
                            columns="#{bindings.Shipstate.displayWidth}">
                <af:validator binding="#{bindings.Shipstate.validator}"/>
              </af:inputText>
              <af:inputText value="#{bindings.Shipzip.inputValue}"
                            label="#{bindings.Shipzip.label}"
                            required="#{bindings.Shipzip.mandatory}"
                            columns="#{bindings.Shipzip.displayWidth}">
                <af:validator binding="#{bindings.Shipzip.validator}"/>
              </af:inputText>
              <af:selectOneChoice value="#{bindings.Shipcountry.inputValue}"
                                  label="#{bindings.Shipcountry.label}">
                <f:selectItems value="#{bindings.Shipcountry.items}"/>
              </af:selectOneChoice>
              <af:selectOneChoice value="#{bindings.Courier.inputValue}"
                                  label="#{bindings.Courier.label}">
                <f:selectItems value="#{bindings.Courier.items}"/>
              </af:selectOneChoice>
              <af:inputText value="#{bindings.Shiptofirstname.inputValue}"
                            label="#{bindings.Shiptofirstname.label}"
                            required="#{bindings.Shiptofirstname.mandatory}"
                            columns="#{bindings.Shiptofirstname.displayWidth}">
                <af:validator binding="#{bindings.Shiptofirstname.validator}"/>
              </af:inputText>
              <af:inputText value="#{bindings.Shiptolastname.inputValue}"
                            label="#{bindings.Shiptolastname.label}"
                            required="#{bindings.Shiptolastname.mandatory}"
                            columns="#{bindings.Shiptolastname.displayWidth}">
                <af:validator binding="#{bindings.Shiptolastname.validator}"/>
              </af:inputText>
              <af:inputText value="#{bindings.Creditcard.inputValue}"
                            label="#{bindings.Creditcard.label}"
                            required="#{bindings.Creditcard.mandatory}"
                            columns="#{bindings.Creditcard.displayWidth}">
                <af:validator binding="#{bindings.Creditcard.validator}"/>
              </af:inputText>
              <af:selectOneChoice value="#{bindings.Cardtype.inputValue}"
                                  label="#{bindings.Cardtype.label}">
                <f:selectItems value="#{bindings.Cardtype.items}"/>
              </af:selectOneChoice>
              <af:panelLabelAndMessage label="#{bindings.Exprdate.label}">
                <af:panelHorizontal>
                  <af:selectOneChoice value="#{bindings.ExprMonth.inputValue}">
                    <f:selectItems value="#{bindings.ExprMonth.items}"/>
                  </af:selectOneChoice>
                  <af:selectOneChoice value="#{bindings.ExprYear.inputValue}">
                    <f:selectItems value="#{bindings.ExprYear.items}"/>
                  </af:selectOneChoice>
                </af:panelHorizontal>
              </af:panelLabelAndMessage>
              <f:facet name="footer">
                <h:panelGroup>
                  <af:commandButton text="FinalizeOrder"
                                    disabled="#{!bindings.finalizeOrder.enabled}"
                                    action="#{ConfirmShippingInfoPage.onFinalizeOrder}"/>
                </h:panelGroup>
              </f:facet>
            </af:panelForm>
          </af:panelGroup>
        </af:panelPage>
      </h:form>
    </afh:body>
  </afh:html>
</f:view>
