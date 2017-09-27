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
      <c:forEach var="attributeLabel" items="${bindings.DeptView1.labelSet}">
        <th>
          <c:out value="${attributeLabel}"/>
        </th>
      </c:forEach>
    </tr>
    <c:forEach var="Row" items="${bindings.DeptView1.rangeSet}">
      <tr>
        <td>
          <c:out value="${Row.currencyString}"/>
        </td>
        <c:forEach var="attrValue" items="${Row.attributeValues}">
          <td>
            <c:out value="${attrValue}"/>&nbsp;
          </td>
        </c:forEach>
      </tr>
    </c:forEach>
  </table>
  <html:form action="/TestPage.do">
    <input type="hidden" name="<c:out value='${bindings.statetokenid}'/>" value="<c:out value='${bindings.statetoken}'/>"/>
    <input type="submit" name="event_PreviousSet" value="PreviousSet" <c:out value="${bindings.PreviousSet.enabledString}" />/>
    <input type="submit" name="event_NextSet" value="NextSet" <c:out value="${bindings.NextSet.enabledString}" />/>
    <input type="submit" name="event_Next" value="Next" <c:out value="${bindings.Next.enabledString}" />/>
    <input type="submit" name="event_Previous" value="Previous" <c:out value="${bindings.Previous.enabledString}" />/>
    <table border="1" cellpadding="3" cellspacing="2" width="100%">
      <tr>
        <td>
          <c:out value="${bindings['Deptno'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['Deptno']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['Dname'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['Dname']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['Loc'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['Loc']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['Foo'].label}"/>
        </td>
        <td>
          <html:text property="Foo"/>
        </td>
      </tr>
    </table>
  </html:form>
</body>
</html>
