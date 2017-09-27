<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%--
 | Setup page variables uses for displaying the navigation
 | "control" at the top that looks like:
 | Previous N - M of Z Next
 +--%>
<c:set var="rangeBinding" value="${bindings[param.rangeBindingName]}"/>
<c:set var="totalRows" value="${rangeBinding.estimatedRowCount}"/>
<c:set var="firstRowShown" value="${rangeBinding.rangeStart + 1}"/>
<c:choose>
  <c:when test="${not empty param.extraParams}">
    <c:set var="queryStrBase" value='?${param.extraParams}&'/>
  </c:when>
  <c:otherwise>
    <c:set var="queryStrBase" value="?"/>
  </c:otherwise>
</c:choose>
<c:choose>
  <c:when test="${rangeBinding.rangeSize == -1}">
    <c:set var="rowsPerPage" value="${totalRows}"/>
  </c:when>
  <c:otherwise>
    <c:set var="rowsPerPage" value="${rangeBinding.rangeSize}"/>
  </c:otherwise>
</c:choose>
<c:choose>
  <c:when test="${firstRowShown + rowsPerPage - 1 > totalRows}">
    <c:set var="lastRowShown" value="${totalRows}"/>
  </c:when>
  <c:otherwise>
    <c:set var="lastRowShown" value="${firstRowShown + rowsPerPage - 1}"/>
  </c:otherwise>
</c:choose>
<c:if test="${totalRows > rangeBinding.rangeSize}">
  <c:choose>
    <c:when test="${firstRowShown > 1}">
      <a href="<c:out value="${param.targetPageName}${queryStrBase}"/>event=PreviousSet">Previous</a> 
    </c:when>
    <c:otherwise>
      <font color="#e0e0e0">Previous</font>
    </c:otherwise>
  </c:choose>
</c:if>
  <c:out value="${rangeBinding.rangeStart + 1}"/>-
  <c:out value="${lastRowShown}"/> of
  <c:out value="${totalRows}"/>
<c:if test="${totalRows > rangeBinding.rangeSize}">
  <c:choose>
    <c:when test="${lastRowShown < totalRows}">
      <a href="<c:out value="${param.targetPageName}${queryStrBase}"/>event=NextSet">Next</a> 
    </c:when>
    <c:otherwise>
      <font color="#e0e0e0">Next</font>
    </c:otherwise>
  </c:choose>
</c:if>
