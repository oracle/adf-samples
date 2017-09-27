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
  <html:form action="/CallStoredProcedure.do">
    <P>
      <input type="hidden" name="<c:out value='${bindings.statetokenid}'/>" value="<c:out value='${bindings.statetoken}'/>"/>Click to call stored procedure:
      <input type="submit" name="event_callStoredProcedure" value="Call Stored Procedure" <c:out value="${bindings.callStoredProcedure1.enabledString}" />/>
    </P>
    <P>Message: 
    <input type="text" name="message"/></P>
  </html:form>
</body>
</html>
