<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Example of Checking Next Row's Value</title>
    <style>
      td.break {
         border-bottom-width:1px;
         border-bottom-style:solid;
         border-color:navy;
      }
    </style>
  </head>
  <body>
    <jsp:include page="pagingControl.jsp">
      <jsp:param name="rangeBindingName" value="EmpView"/>
      <jsp:param name="targetPageName" value="TestPage.do"/>
    </jsp:include>
    <table cellspacing="0" border="0" cellpadding="3">
      <%-- Cache to not have to calculate on each iteration --%>
      <c:set var="rangeSet" value="${bindings.EmpView.rangeSet}"/>
      <tr>
        <th>Ename</th>
        <th>Deptno</th>
      </tr> 
      <c:forEach items="${rangeSet}" var="Row" varStatus="loop">
        <c:choose>
          <c:when test="${!loop.last && Row.Deptno.value != rangeSet[loop.index + 1].Deptno.value}">
            <c:set var="classToUse" value="break"/>
          </c:when>
          <c:otherwise>
            <c:set var="classToUse" value=""/>
          </c:otherwise>
        </c:choose>
        <tr>
          <td class="<c:out value='${classToUse}'/>">
            <c:out value="${Row.Ename}"/>
          </td>
          <td class="<c:out value='${classToUse}'/>">
            <c:out value="${Row.Deptno}"/>
          </td>
        </tr>
      </c:forEach>
  </body>
</html>
