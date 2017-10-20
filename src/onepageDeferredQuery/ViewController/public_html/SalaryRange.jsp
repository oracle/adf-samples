<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>Model 2 Example for Single-Page Query Needing to Collect Bind Variables</title>
</head>
<body>
 <h3>Model 2 Example for Single-Page Query Needing to Collect Bind Variables</h3>
  <html:errors/>
  <form method="post" action="SalaryRange.do">Find Employees with Salary between 
  <input type="text" size="5" name="low"/> and 
  <input type="text" size="5" name="high"/>
  <input type="submit" value="Go" name="event_FindIt"/></form>
    <c:if test="${not empty EventName}">
      <table border="1" width="100%">
        <tr>
          <th>
            <c:out value="${bindings.EmpsInSalaryRange.labels['Employeeid']}"/>
          </th>
          <th>
            <c:out value="${bindings.EmpsInSalaryRange.labels['Employeename']}"/>
          </th>
          <th>
            <c:out value="${bindings.EmpsInSalaryRange.labels['Salary']}"/>
          </th>
        </tr>
        <c:forEach var="Row" items="${bindings.EmpsInSalaryRange.rangeSet}">
          <tr>
            <td>
              <c:out value="${Row['Employeeid']}"/>&nbsp;
            </td>
            <td>
              <c:out value="${Row['Employeename']}"/>&nbsp;
            </td>
            <td>
              <c:out value="${Row['Salary']}"/>&nbsp;
            </td>
          </tr>
        </c:forEach>
      </table>Event name is:
      <c:out value="${EventName}"/>
    </c:if>

</body>
</html>
