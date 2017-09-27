<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>untitled</title>
</head>
<body>
  <html:errors/>
  <%--
   | This is an example of referring to a AM-level custom property
   | named "SomeAMLevelProperty" on the application module that is
   | the data provider for the data control related to the
   | DeptView1Iterator.
   +--%>
   <h1><c:out value="${bindings.DeptView1Iterator.dataControl.dataProvider.properties.SomeAMLevelProperty}"/></h1>
  <%--
   | This is an example of referring to a VO-level custom property
   | named "SomeVOLevelProperty" on the DeptView view object of which
   | "DeptView1" is an instance in the DemoModel AppModule, and to which
   | the "DeptView1Iterator" is bound.
   +--%>
  <h2><c:out value="${bindings.DeptView1Iterator.viewObject.properties.SomeVOLevelProperty}"/></h2>
  <html:form action="/EditDepartments.do">
    <input type="hidden" name="<c:out value='${bindings.statetokenid}'/>" value="<c:out value='${bindings.statetoken}'/>"/>
    <table border="1" cellpadding="2" cellspacing="0">
      <tr>
        <td>
          <c:out value="${bindings['Deptno'].label}"/>
        </td>
        <td>
          <html:text property="Deptno"/>
        </td>
        <td>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['Dname'].label}"/>
        </td>
        <td>
          <html:text property="Dname"/>
        </td>
        <td>
          <%--
           | This is an example of referring to a VO Attribute-level custom
           | property named "SomeAttrLevelProperty" on the DeptView view object
           | Dname is an attribute binding in the binding container.
           +--%>          
           <c:out value="${bindings.Dname.attributeDef.properties.SomeAttrLevelProperty}"/>
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['Loc'].label}"/>
        </td>
        <td>
          <html:text property="Loc"/>
        </td>
        <td>&nbsp;
        </td>
      </tr>
    </table>
    <br/>
    <input type="submit" name="event_Commit" value="Commit" />
    <input type="submit" name="event_Previous" value="Previous" <c:out value="${bindings.Previous.enabledString}" />/>
    <input type="submit" name="event_Next" value="Next" <c:out value="${bindings.Next.enabledString}" />/>
  </html:form>
</body>
</html>
