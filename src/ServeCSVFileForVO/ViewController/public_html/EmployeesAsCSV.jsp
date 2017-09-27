<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"
%><%@ page contentType="application/csv;charset=windows-1252"
%><%
response.setHeader("Content-Disposition","filename=\"Employees.csv\"");
%>
<c:forEach var="Attr" varStatus="j" items="${bindings.CSVEmpView.labelSet}"
><c:if test="${j.index != 0}"
>,</c:if>"<c:out value='${Attr}'
/>"</c:forEach>
<c:forEach var="Row" items="${bindings.CSVEmpView.rangeSet}"
><c:out value="${Row.Empno}"
/>,<c:out value="${Row.Ename}"
/>,<c:out value="${Row.Job}"
/>,<c:out value="${Row.Mgr}"
/>,<c:out value="${Row.Hiredate}"
/>,<c:out value="${Row.Sal}"
/>,<c:out value="${Row.Comm}"
/>,<c:out value="${Row['Deptno']}"
/>
</c:forEach>