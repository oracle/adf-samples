<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/trinidad/html" prefix="trh"%>
<%@ taglib uri="http://myfaces.apache.org/trinidad" prefix="tr"%>
<f:view> 
  <trh:html>
    <trh:head title="main">
      <meta http-equiv="Content-Type"
            content="text/html; charset=windows-1252"/>
    </trh:head>
    <trh:body>
      <tr:messages/>
       
      <h:form>
        <!--TODO: Migrator from ADF Faces 10.1.3 to Trinidad removed the following attribute from the node below since it does not exist in Trinidad anymore
selectionState="\#{bindings.DeptView.collectionModel.selectedRow}"-->
        <tr:table value="#{bindings.DeptView.collectionModel}" var="row"
                  rows="#{bindings.DeptView.rangeSize}"
                  first="#{bindings.DeptView.rangeStart}"
                  emptyText="#{bindings.DeptView.viewable ? \'No rows yet.\' : \'Access Denied.\'}"
                  selectionListener="#{bindings.DeptView.collectionModel.makeCurrent}"
                  rowSelection="single">
          <tr:column headerText="#{bindings.DeptView.labels.Deptno}"
                     sortProperty="Deptno" sortable="true">
            <tr:outputText value="#{row.Deptno}">
              <f:convertNumber groupingUsed="false"
                               pattern="#{bindings.DeptView.formats.Deptno}"/>
            </tr:outputText>
          </tr:column>
          <tr:column headerText="#{bindings.DeptView.labels.Dname}"
                     sortProperty="Dname" sortable="true">
            <tr:outputText value="#{row.Dname}"/>
          </tr:column>
          <tr:column headerText="#{bindings.DeptView.labels.Loc}"
                     sortProperty="Loc" sortable="true">
            <tr:outputText value="#{row.Loc}"/>
          </tr:column>
          <f:facet name="actions">
            <tr:commandButton text="Submit"/>
          </f:facet>
        </tr:table>
        <tr:commandButton text="Logout" action="#{Main.onLogout}"/>
      </h:form>
    </trh:body>
  </trh:html>
</f:view>
<%-- 
  oracle-jdev-comment:preferred-managed-bean-name:Main
--%>