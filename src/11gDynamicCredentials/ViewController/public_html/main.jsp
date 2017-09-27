<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html2"%>
<html>
<head>
<title>Browse Departments</title>
</head>
<body>
  <a href="main.do?event=Logout">Logout</a>
  <html:errors/>
  <table border="1" width="100%">
    <tr>
      <th>&nbsp;</th>
      <th>
        <c:out value="${bindings.DeptView.labels['Deptno']}"/>
      </th>
      <th>
        <c:out value="${bindings.DeptView.labels['Dname']}"/>
      </th>
      <th>
        <c:out value="${bindings.DeptView.labels['Loc']}"/>
      </th>
    </tr>
    <c:forEach var="Row" items="${bindings.DeptView.rangeSet}">
      <tr>
        <td>&nbsp;
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
  </table><html2:form action="/main.do">
      <button type="button">
        Button
      </button>
    </html2:form>
  </body>
</html>
