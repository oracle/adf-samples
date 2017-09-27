<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://xmlns.oracle.com/adf/faces" prefix="af"%>
<%@ taglib uri="http://xmlns.oracle.com/adf/faces/html" prefix="afh"%>
<f:view>
  <afh:html>
    <afh:head title="pickDept">
      <meta http-equiv="Content-Type"
            content="text/html; charset=windows-1252"/>
    </afh:head>
    <afh:body>
      <af:messages/>
      <h:form>
        <af:table value="#{bindings.DeptView1.collectionModel}" var="row"
                  rows="#{bindings.DeptView1.rangeSize}"
                  first="#{bindings.DeptView1.rangeStart}"
                  emptyText="#{bindings.DeptView1.viewable ? \'No rows yet.\' : \'Access Denied.\'}"
                  selectionState="#{bindings.DeptView1.collectionModel.selectedRow}"
                  selectionListener="#{bindings.DeptView1.collectionModel.makeCurrent}">
          <af:column headerText="#{bindings.DeptView1.labels.Deptno}"
                     sortProperty="Deptno" sortable="false">
            <af:outputText value="#{row.Deptno}">
              <f:convertNumber groupingUsed="false"
                               pattern="#{bindings.DeptView1.formats.Deptno}"/>
            </af:outputText>
          </af:column>
          <af:column headerText="#{bindings.DeptView1.labels.Dname}"
                     sortProperty="Dname" sortable="false">
            <af:outputText value="#{row.Dname}"/>
          </af:column>
          <af:column headerText="#{bindings.DeptView1.labels.Loc}"
                     sortProperty="Loc" sortable="false">
            <af:outputText value="#{row.Loc}"/>
          </af:column>
          <f:facet name="selection">
            <af:tableSelectOne text="Select and">
              <af:commandButton text="Submit">
                <af:returnActionListener value="#{row.Deptno}"/>
                <af:setActionListener from="#{row.Deptno}"
                                      to="#{bindings.Deptno.inputValue}"/>
              </af:commandButton>
            </af:tableSelectOne>
          </f:facet>
        </af:table>
      </h:form>
    </afh:body>
  </afh:html>
</f:view>