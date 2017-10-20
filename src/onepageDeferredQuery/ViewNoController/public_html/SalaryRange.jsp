<%@ taglib uri="http://xmlns.oracle.com/adf/ui/jsp/adftags" prefix="adf"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<adf:uimodelreference model="SalaryRangeUIModel"
                      lifecycle="mypackage1.SalaryRangePageLifecycle" />
<html>
  <head>
    <title>Model 1 Example for Single-Page Query Needing to Collect Bind Variables</title>
  </head>
  <body>
    <h3>Model 1 Example for Single-Page Query Needing to Collect Bind Variables</h3>
    <form method="post" action="SalaryRange.jsp">Find Employees with Salary between
      <input type="text" size="5" name="low"/>and
      <input type="text" size="5" name="high"/>
      <input type="submit" value="Go" name="event_FindIt"/>
    </form>
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
