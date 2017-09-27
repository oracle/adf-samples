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
  <html:form action="/dataPage1.do">
    <input type="hidden" name="<c:out value='${bindings.statetokenid}'/>" value="<c:out value='${bindings.statetoken}'/>"/>
    <table border="1" cellpadding="3" cellspacing="2" width="100%">
      <tr>
        <td>
          <c:out value="${bindings['Deptno1'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['Deptno1']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['Dname1'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['Dname1']}"/>&nbsp;
        </td>
      </tr>
      <tr>
        <td>
          <c:out value="${bindings['Loc1'].label}"/>
        </td>
        <td>
          <c:out value="${bindings['Loc1']}"/>&nbsp;
        </td>
      </tr>
    </table>
    <table border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td>
          <input type="submit" name="event_First" value="First" <c:out value="${bindings.First.enabledString}"/>/>
        </td>
        <td>
          <input type="submit" name="event_Previous" value="Previous" <c:out value="${bindings.Previous.enabledString}"/>/>
        </td>
        <td>
          <input type="submit" name="event_Next" value="Next" <c:out value="${bindings.Next.enabledString}"/>/>
        </td>
        <td>
          <input type="submit" name="event_Last" value="Last" <c:out value="${bindings.Last.enabledString}"/>/>
          <input type="submit" value="DoIt" name="event_DoIt"/>
        </td>
      </tr>
    </table>
  </html:form>
</body>
</html>
