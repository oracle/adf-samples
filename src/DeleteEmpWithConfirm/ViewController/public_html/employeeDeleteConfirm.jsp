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
  <html:form action="/employeeDeleteConfirm.do">
    <input type="hidden" name="<c:out value='${bindings.statetokenid}'/>" value="<c:out value='${bindings.statetoken}'/>"/>
    <table border="1" cellpadding="3" cellspacing="2" width="100%">
      <tr>
        <td>
          <c:out value="${bindings['Empno'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['Empno']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['Ename'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['Ename']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['Job'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['Job']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['Mgr'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['Mgr']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['Hiredate'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['Hiredate']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['Sal'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['Sal']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['Comm'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['Comm']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['Deptno'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['Deptno']}"/>&nbsp;
        </td>
      </tr>
    </table>
  </html:form>
  <a href="employeeDeleteConfirm.do?event=Delete">Confirm Delete</a>
  <a href="employeeDeleteConfirm.do?event=DontDelete">Don't Delete</a>
</body>
</html>
