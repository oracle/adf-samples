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
  <table border="1" width="100%">
    <tr>
      <th>&nbsp;</th>
      <th>
        <c:out value="${bindings.EmpView.labels['Empno']}"/>
      </th>
      <th>
        <c:out value="${bindings.EmpView.labels['Ename']}"/>
      </th>
      <th>
        <c:out value="${bindings.EmpView.labels['Job']}"/>
      </th>
      <th>
        <c:out value="${bindings.EmpView.labels['Mgr']}"/>
      </th>
      <th>
        <c:out value="${bindings.EmpView.labels['Hiredate']}"/>
      </th>
      <th>
        <c:out value="${bindings.EmpView.labels['Sal']}"/>
      </th>
      <th>
        <c:out value="${bindings.EmpView.labels['Comm']}"/>
      </th>
      <th>
        <c:out value="${bindings.EmpView.labels['Deptno']}"/>
      </th>
    </tr>
    <c:forEach var="Row" items="${bindings.EmpView.rangeSet}">
      <tr>
        <td>
          <a href="employeeList.do?event=confirmDelete&rowKeyStr=<c:out value='${Row.rowKeyStr}'/>">Delete</a>
        </td>
        <td>
          <c:out value="${Row['Empno']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['Ename']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['Job']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['Mgr']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['Hiredate']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['Sal']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['Comm']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['Deptno']}"/>&nbsp;
        </td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>
