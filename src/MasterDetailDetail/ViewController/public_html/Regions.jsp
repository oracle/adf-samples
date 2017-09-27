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
        <c:out value="${bindings.Regions.labels['RegionId']}"/>
      </th>
      <th>
        <c:out value="${bindings.Regions.labels['RegionName']}"/>
      </th>
    </tr>
    <c:forEach var="Row" items="${bindings.Regions.rangeSet}">
      <tr>
        <td>
          <a href="Regions.do?rowKeyStr=<c:out value='${Row.rowKeyStr}' />&event=ShowRegion">Select</a>
        </td>
        <td>
          <c:out value="${Row['RegionId']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['RegionName']}"/>&nbsp;
        </td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>
