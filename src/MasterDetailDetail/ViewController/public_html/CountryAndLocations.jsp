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
  <html:form action="/CountryAndLocations.do">
    <input type="hidden" name="<c:out value='${bindings.statetokenid}'/>" value="<c:out value='${bindings.statetoken}'/>"/>
    <table border="1" cellpadding="3" cellspacing="2" width="100%">
      <tr>
        <td>
          <c:out value="${bindings['CountryId'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['CountryId']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['CountryName'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['CountryName']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['RegionId'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['RegionId']}"/>&nbsp;
        </td>
      </tr>
    </table>
  </html:form>
  <table border="1" width="100%">
    <tr>
      <th>&nbsp;</th>
      <th>
        <c:out value="${bindings.Locations.labels['LocationId']}"/>
      </th>
      <th>
        <c:out value="${bindings.Locations.labels['StreetAddress']}"/>
      </th>
      <th>
        <c:out value="${bindings.Locations.labels['PostalCode']}"/>
      </th>
      <th>
        <c:out value="${bindings.Locations.labels['City']}"/>
      </th>
      <th>
        <c:out value="${bindings.Locations.labels['StateProvince']}"/>
      </th>
      <th>
        <c:out value="${bindings.Locations.labels['CountryId']}"/>
      </th>
    </tr>
    <c:forEach var="Row" items="${bindings.Locations.rangeSet}">
      <tr>
        <td>
          <a href="CountryAndLocations.do?rowKeyStr=<c:out value='${Row.rowKeyStr}' />&event=ShowLocation">Select</a>
        </td>
        <td>
          <c:out value="${Row['LocationId']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['StreetAddress']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['PostalCode']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['City']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['StateProvince']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['CountryId']}"/>&nbsp;
        </td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>
