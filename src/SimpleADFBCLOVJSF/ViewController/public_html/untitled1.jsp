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
        <af:panelForm>
          <af:inputText value="#{bindings.Empno.inputValue}"
                        label="#{bindings.Empno.label}"
                        required="#{bindings.Empno.mandatory}"
                        columns="#{bindings.Empno.displayWidth}">
            <af:validator binding="#{bindings.Empno.validator}"/>
            <f:convertNumber groupingUsed="false"
                             pattern="#{bindings.Empno.format}"/>
          </af:inputText>
          <af:inputText value="#{bindings.Ename.inputValue}"
                        label="#{bindings.Ename.label}"
                        required="#{bindings.Ename.mandatory}"
                        columns="#{bindings.Ename.displayWidth}">
            <af:validator binding="#{bindings.Ename.validator}"/>
          </af:inputText>
          <af:inputText value="#{bindings.Job.inputValue}"
                        label="#{bindings.Job.label}"
                        required="#{bindings.Job.mandatory}"
                        columns="#{bindings.Job.displayWidth}">
            <af:validator binding="#{bindings.Job.validator}"/>
          </af:inputText>
          <af:inputText value="#{bindings.Mgr.inputValue}"
                        label="#{bindings.Mgr.label}"
                        required="#{bindings.Mgr.mandatory}"
                        columns="#{bindings.Mgr.displayWidth}">
            <af:validator binding="#{bindings.Mgr.validator}"/>
            <f:convertNumber groupingUsed="false"
                             pattern="#{bindings.Mgr.format}"/>
          </af:inputText>
          <af:selectInputDate value="#{bindings.Hiredate.inputValue}"
                              label="#{bindings.Hiredate.label}"
                              required="#{bindings.Hiredate.mandatory}">
            <af:validator binding="#{bindings.Hiredate.validator}"/>
            <f:convertDateTime pattern="#{bindings.Hiredate.format}"/>
          </af:selectInputDate>
          <af:inputText value="#{bindings.Sal.inputValue}"
                        label="#{bindings.Sal.label}"
                        required="#{bindings.Sal.mandatory}"
                        columns="#{bindings.Sal.displayWidth}">
            <af:validator binding="#{bindings.Sal.validator}"/>
            <f:convertNumber groupingUsed="false"
                             pattern="#{bindings.Sal.format}"/>
          </af:inputText>
          <af:inputText value="#{bindings.Comm.inputValue}"
                        label="#{bindings.Comm.label}"
                        required="#{bindings.Comm.mandatory}"
                        columns="#{bindings.Comm.displayWidth}">
            <af:validator binding="#{bindings.Comm.validator}"/>
            <f:convertNumber groupingUsed="false"
                             pattern="#{bindings.Comm.format}"/>
          </af:inputText>
          <af:selectInputText value="#{bindings.Deptno.inputValue}"
                              label="#{bindings.Deptno.label}"
                              required="#{bindings.Deptno.mandatory}"
                              columns="#{bindings.Deptno.displayWidth}"
                              action="dialog:ChooseDept" id="deptnoField"
                              autoSubmit="true">
            <af:validator binding="#{bindings.Deptno.validator}"/>
            <f:convertNumber groupingUsed="false"
                             pattern="#{bindings.Deptno.format}"/>
          </af:selectInputText>
          <af:inputText value="#{bindings.Dname.inputValue}"
                        label="#{bindings.Dname.label}"
                        required="#{bindings.Dname.mandatory}"
                        columns="#{bindings.Dname.displayWidth}"
                        readOnly="true" partialTriggers="deptnoField">
            <af:validator binding="#{bindings.Dname.validator}"/>
          </af:inputText>
          <af:inputText value="#{bindings.Loc.inputValue}"
                        label="#{bindings.Loc.label}"
                        required="#{bindings.Loc.mandatory}"
                        columns="#{bindings.Loc.displayWidth}" readOnly="true"
                        partialTriggers="deptnoField">
            <af:validator binding="#{bindings.Loc.validator}"/>
          </af:inputText>
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
        <af:commandButton actionListener="#{bindings.Commit.execute}"
                          text="Save"/>
        <af:commandButton actionListener="#{bindings.Rollback.execute}"
                          text="Cancel Changes"
                          immediate="true">
          <af:resetActionListener/>
        </af:commandButton>
      </h:form>
    </afh:body>
  </afh:html>
</f:view>