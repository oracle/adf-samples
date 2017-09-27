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
  <html:form action="/RegionAndCountries.do">
    <input type="hidden" name="<c:out value='${bindings.statetokenid}'/>" value="<c:out value='${bindings.statetoken}'/>"/>
    <table border="1" cellpadding="3" cellspacing="2" width="100%">
      <tr>
        <td>
          <c:out value="${bindings['RegionId'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['RegionId']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['RegionName'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['RegionName']}"/>&nbsp;
        </td>
      </tr>
    </table>
  </html:form>
  <table border="1" width="100%">
    <tr>
      <th>&nbsp;</th>
      <th>
        <c:out value="${bindings.Countries.labels['CountryId']}"/>
      </th>
      <th>
        <c:out value="${bindings.Countries.labels['CountryName']}"/>
      </th>
      <th>
        <c:out value="${bindings.Countries.labels['RegionId']}"/>
      </th>
    </tr>
    <c:forEach var="Row" items="${bindings.Countries.rangeSet}">
      <tr>
        <td>
          <a href="RegionAndCountries.do?rowKeyStr=<c:out value='${Row.rowKeyStr}' />&event=ShowCountry">Select</a>
        </td>
        <td>
          <c:out value="${Row['CountryId']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['CountryName']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['RegionId']}"/>&nbsp;
        </td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>
