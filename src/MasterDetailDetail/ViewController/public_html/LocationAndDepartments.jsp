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
  <html:form action="/LocationAndDepartments.do">
    <input type="hidden" name="<c:out value='${bindings.statetokenid}'/>" value="<c:out value='${bindings.statetoken}'/>"/>
    <table border="1" cellpadding="3" cellspacing="2" width="100%">
      <tr>
        <td>
          <c:out value="${bindings['LocationId'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['LocationId']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['StreetAddress'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['StreetAddress']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['PostalCode'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['PostalCode']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['City'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['City']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['StateProvince'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['StateProvince']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['CountryId'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['CountryId']}"/>&nbsp;
        </td>
      </tr>
    </table>
  </html:form>
  <table border="1" width="100%">
    <tr>
      <th>&nbsp;</th>
      <th>
        <c:out value="${bindings.Departments.labels['DepartmentId']}"/>
      </th>
      <th>
        <c:out value="${bindings.Departments.labels['DepartmentName']}"/>
      </th>
      <th>
        <c:out value="${bindings.Departments.labels['ManagerId']}"/>
      </th>
      <th>
        <c:out value="${bindings.Departments.labels['LocationId']}"/>
      </th>
    </tr>
    <c:forEach var="Row" items="${bindings.Departments.rangeSet}">
      <tr>
        <td>
          <c:out value="${Row.currencyString}"/>
        </td>
        <td>
          <c:out value="${Row['DepartmentId']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['DepartmentName']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['ManagerId']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['LocationId']}"/>&nbsp;
        </td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>
