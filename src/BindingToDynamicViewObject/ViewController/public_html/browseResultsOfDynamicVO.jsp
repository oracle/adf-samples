<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
  <head>
    <title>Binding to Dynamic Query Example</title>
  </head>
  <body>
    <html:errors/>
    <form method="post" action="browseResultsOfDynamicVO.do">
       Enter a SQL Query: <input type="submit" value="Show Results" name="event_Query">
      <textarea style="width: 100%" name="sql" rows="3"  ><c:out value="${param.sql}"/></textarea><br>
      <input type="hidden" name="_sql" value="<c:out value="${param.sql}"/>"/>
      <c:if test="${empty bindings.exceptionsList}">
        <input type="hidden" name="<c:out value='${bindings.statetokenid}'/>" value="<c:out value='${bindings.statetoken}'/>"/>
        <input type="submit" name="event_PreviousSet" value="PreviousSet" <c:out value="${bindings.PreviousSet.enabledString}" />/>
        <input type="submit" name="event_NextSet" value="NextSet" <c:out value="${bindings.NextSet.enabledString}" />/>
      </c:if>
    </form>
    <c:choose>
      <c:when test="${not empty bindings.exceptionsList}">
        Error in the query...
      </c:when>
      <c:when test="${bindings.DynamicViewObjectIterator.estimatedRowCount == 0}">
         No rows returned.
      </c:when>
      <c:otherwise>
        <table border="1" cellpadding="2" cellspacing="0">
          <tr>
            <c:forEach var="attributeLabel" items="${bindings.DynamicViewObject.labelSet}">
              <th>
                <c:out value="${attributeLabel}"/>
              </th>
            </c:forEach>
          </tr>
          <c:forEach var="Row" items="${bindings.DynamicViewObject.rangeSet}">
            <tr>
              <c:forEach var="attrValue" items="${Row.attributeValues}">
                <td>
                  <c:out value="${attrValue}"/>&nbsp;
                </td>
              </c:forEach>
            </tr>
          </c:forEach>
        </table>    
      </c:otherwise>
    </c:choose>
  </body>
</html>
