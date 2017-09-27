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
            <af:panelGroup layout="vertical"
                           rendered="#{not empty bindings.ShoppingCart.rangeSet}">
              <af:table rows="#{bindings.ShoppingCart.rangeSize}"
                        first="#{bindings.ShoppingCart.rangeStart}"
                        emptyText="#{bindings.ShoppingCart.viewable ? \'No rows yet.\' : \'Access Denied.\'}"
                        var="row"
                        value="#{bindings.ShoppingCart.collectionModel}"
                        selectionState="#{bindings.ShoppingCart.collectionModel.selectedRow}"
                        selectionListener="#{bindings.ShoppingCart.collectionModel.makeCurrent}">
                <af:column sortable="false" headerText="">
                  <af:commandLink actionListener="#{bindings.removeItemFromCart.execute}"
                                  action="removeItemFromCart" text="Remove"
                                  disabled="#{!bindings.removeItemFromCart.enabled}"/>
                </af:column>
                <af:column headerText="#{bindings.ShoppingCart.labels.Itemid}"
                           sortable="true" sortProperty="Itemid">
                  <af:outputText value="#{row.Itemid}"/>
                </af:column>
                <af:column headerText="#{bindings.ShoppingCart.labels.Name}"
                           sortable="true" sortProperty="Name">
                  <af:outputText value="#{row.Name}"/>
                </af:column>
                <af:column headerText="#{bindings.ShoppingCart.labels.InStock}"
                           sortable="true" sortProperty="InStock">
                  <af:outputText value="#{row.InStock}"/>
                </af:column>
                <af:column headerText="#{bindings.ShoppingCart.labels.Listprice}"
                           sortable="true" sortProperty="Listprice">
                  <af:outputText value="#{row.Listprice}"/>
                </af:column>
                <af:column headerText="#{bindings.ShoppingCart.labels.Quantity}"
                           sortable="true" sortProperty="Quantity">
                  <af:inputText value="#{row.Quantity}" simple="true"
                                required="#{bindings.Quantity.mandatory}"
                                columns="#{bindings.Quantity.displayWidth}">
                    <f:convertNumber groupingUsed="false"
                                     pattern="#{bindings.ShoppingCart.formats.Quantity}"/>
                  </af:inputText>
                  <f:facet name="footer">
                    <h:panelGroup>
                      <af:commandButton actionListener="#{bindings.removeShoppingCartItemsWithZeroQuantity.execute}"
                                        text="Update Quantities"
                                        disabled="#{!bindings.removeShoppingCartItemsWithZeroQuantity.enabled}"/>
                    </h:panelGroup>
                  </f:facet>
                </af:column>
                <af:column headerText="#{bindings.ShoppingCart.labels.ExtendedTotal}"
                           sortable="true" sortProperty="ExtendedTotal">
                  <af:outputText value="#{row.ExtendedTotal}"/>
                  <f:facet name="footer">
                    <h:panelGroup>
                      <af:outputText value="#{bindings.getCartTotal1.inputValue}">
                        <af:convertNumber pattern="$0.00"/>
                      </af:outputText>
                    </h:panelGroup>
                  </f:facet>
                </af:column>
                <f:facet name="footer">
                  <af:outputText value="Cart Total:"/>
                </f:facet>
              </af:table>
              <af:commandButton text="Check Out" action="reviewcheckout"/>
            </af:panelGroup>
            <af:outputText value="Your shopping cart is empty."
                           rendered="#{empty bindings.ShoppingCart.rangeSet}"/>
          </af:panelGroup>
        </af:panelPage>
      </h:form>
    </afh:body>
  </afh:html>
</f:view>
