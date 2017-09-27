<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
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
  <table border="1" cellpadding="3" cellspacing="0">
    <tr>
      <th>
        <c:out value="${bindings.people.labels['age']}"/>
      </th>
      <th>
        <c:out value="${bindings.people.labels['name']}"/>
      </th>
    </tr>
    <c:forEach var="Row" items="${bindings.people.rangeSet}">
      <tr>
        <td>
          <c:out value="${Row['age']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['name']}"/>&nbsp;
        </td>
      </tr>
    </c:forEach>
  </table>
  <html:link page="/SetupParameters.do">
    <bean:message key="link.SetupParameters"/>
  </html:link>
</body>
</html>
