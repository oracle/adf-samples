<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>untitled</title>
  </head>
  <body>
    <H2>
      <html:errors/>You selected the following employees... 
    </H2>
    <table border="0">
      <c:forEach var="Row" items="${bindings.getSelectedEmployees1.rangeSet}">
        <tr>
          <td>
            <c:out value="${Row.Ename}"/>
          </td>
        </tr>
      </c:forEach>
    </table>
    <P>
      <a href="EmployeeList.do">Go back to refine your selection</a>
    </P>
  </body>
</html>
