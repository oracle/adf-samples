<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>untitled</title>
</head>
<body title="Edit Departments Test Page">
  <html:errors/>
  <html:form action="/editDepartments.do">
    <input type="hidden" name="<c:out value='${bindings.statetokenid}'/>" value="<c:out value='${bindings.statetoken}'/>"/>
    <table border="0" width="100%" cellpadding="2" cellspacing="0">
      <tr>
        <td>
          <c:out value="${bindings['Deptno'].label}"/>
        </td>
        <td>
          <html:text property="Deptno"/>
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['Dname'].label}"/>
        </td>
        <td>
          <html:text property="Dname"/>
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['Loc'].label}"/>
        </td>
        <td>
          <html:text property="Loc"/>
        </td>
      </tr>
    </table>
    <br/>
    <c:out value="${bindings.editingMode}"/>
    <br/>
    <br/>
    <input type="submit" name="event_Previous" value="Previous" <c:out value="${bindings.Previous.enabledString}" />/>
    <input type="submit" name="event_Next" value="Next" <c:out value="${bindings.Next.enabledString}" />/>
    <input type="submit" name="event_Commit" value="Commit" />
    <input type="submit" name="event_Rollback" value="Rollback" <c:out value="${bindings.Rollback.enabledString}" />/>
  </html:form>
</body>
</html>
