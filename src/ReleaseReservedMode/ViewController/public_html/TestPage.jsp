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
        <c:out value="${bindings.DeptView1.labels['Deptno']}"/>
      </th>
      <th>
        <c:out value="${bindings.DeptView1.labels['Dname']}"/>
      </th>
      <th>
        <c:out value="${bindings.DeptView1.labels['Loc']}"/>
      </th>
    </tr>
    <c:forEach var="Row" items="${bindings.DeptView1.rangeSet}">
      <tr>
        <td>
          <c:out value="${Row.currencyString}"/>
        </td>
        <td>
          <c:out value="${Row['Deptno']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['Dname']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['Loc']}"/>&nbsp;
        </td>
      </tr>
    </c:forEach>
  </table>
  <html:form action="/TestPage.do">
    <input type="hidden" name="<c:out value='${bindings.statetokenid}'/>" value="<c:out value='${bindings.statetoken}'/>"/>
    <input type="submit" name="event_Previous" value="Previous" <c:out value="${bindings.Previous.enabledString}" />/>
    <input type="submit" name="event_Next" value="Next" <c:out value="${bindings.Next.enabledString}" />/>
  </html:form>
</body>
</html>
