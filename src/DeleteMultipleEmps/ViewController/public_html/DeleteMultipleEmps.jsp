<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>untitled</title>
</head>
<body>
  <html:errors/><form method="post" action="">
  <P>Check the employees that you want to delete, then press 
  <input type="submit" value="Delete" name="event_DeleteMultiple"/></P>
  <table border="0" cellpadding="2">
    <c:forEach var="Row" items="${bindings.EmpView.rangeSet}">
      <tr>
        <td><input name="empnos" type="CHECKBOX" value="<c:out value="${Row['Empno']}"/>">
        </td>
        <td>
          <c:out value="${Row['Ename']}"/>&nbsp;
        </td>
      </tr>
    </c:forEach>
  </table>
</form>
</body>
</html>
