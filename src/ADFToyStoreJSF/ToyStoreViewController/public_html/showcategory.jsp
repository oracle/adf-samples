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
            <af:panelHeader text="#{res[param.id]}">
              <af:table rows="#{bindings.ProductsInCategory.rangeSize}"
                        first="#{bindings.ProductsInCategory.rangeStart}"
                        emptyText="#{bindings.ProductsInCategory.viewable ? \'No rows yet.\' : \'Access Denied.\'}"
                        var="row"
                        value="#{bindings.ProductsInCategory.collectionModel}"
                        selectionState="#{bindings.ProductsInCategory.collectionModel.selectedRow}"
                        selectionListener="#{bindings.ProductsInCategory.collectionModel.makeCurrent}">
                <af:column headerText="#{bindings.ProductsInCategory.labels.Productid}"
                           sortable="true" sortProperty="Productid">
                  <af:outputText value="#{row.Productid}"/>
                </af:column>
                <af:column headerText="#{bindings.ProductsInCategory.labels.Name}"
                           sortable="true" sortProperty="Name">
                  <af:commandLink text="#{row.Name}" action="showproduct"
                                  immediate="true">
                    <f:param name="id" value="#{row.Productid}"/>
                  </af:commandLink>
                </af:column>
              </af:table>
            </af:panelHeader>
          </af:panelGroup>
        </af:panelPage>
      </h:form>
    </afh:body>
  </afh:html>
</f:view>
