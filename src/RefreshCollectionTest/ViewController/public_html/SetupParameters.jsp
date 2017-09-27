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
  <html:form action="SetupParameters.do">
    <table cellspacing="0" cellpadding="3" border="1">
      <tr>
        <td>String Parameter:</td>
        <td>
          <html:text property="stringParam"/>
        </td>
      </tr>
      <tr>
        <td>Integer Parameter:</td>
        <td>
          <html:text property="intParam"/>
        </td>
      </tr>
    </table>
    <input type="submit" value="Show Results" name="event_ShowResults"/>
  </html:form>
</body>
</html>
