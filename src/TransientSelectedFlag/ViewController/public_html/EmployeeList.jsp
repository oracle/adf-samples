<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>untitled</title>
</head>
<body>
  <html:errors/><html:form action="EmployeeList.do">
    <input type="submit" name="event_SelectEmployees" value="Select">
    <input type="submit" name="event_SelectEmployeesAndProceed" value="Select and Proceed">
    <input type="submit" name="event_ResetSelection" value="Reset Selection">
  <table border="0" cellpadding="2">
    <c:forEach var="Row" items="${bindings.EmployeeList.rangeSet}">
      <tr>
        <td>
          <input type="checkbox" name="empnos" value="<c:out value="${Row.Empno}"/>" 
          <c:if test="${Row.Selected}">CHECKED</c:if> >
        </td>
        <td>
          <c:out value="${Row.Ename}"/>&nbsp;
        </td>
      </tr>
    </c:forEach>
  </table>
</html:form>  
</body>
</html>
