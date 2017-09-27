<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>Download Filtered VO Result as CSV File Example</title>
</head>
<body>
  <html:errors/>
  <P>
    <html:form action="/BrowseEmployees.do">
      <h2>Search For...</h2>
      <input type="hidden" name="<c:out value='${bindings.statetokenid}'/>" value="<c:out value='${bindings.statetoken}'/>"/>
      <table border="0" width="100%" cellpadding="2" cellspacing="0">
        <tr>
          <td>
            <c:out value="${bindings['Empno'].label}"/>
          </td>
          <td>
            <html:text property="Empno"/>
          </td>
        </tr>
        <tr>
          <td>
            <c:out value="${bindings['Ename'].label}"/>
          </td>
          <td>
            <html:text property="Ename"/>
          </td>
        </tr>
        <tr>
          <td>
            <c:out value="${bindings['Job'].label}"/>
          </td>
          <td>
            <html:text property="Job"/>
          </td>
        </tr>
        <tr>
          <td>
            <c:out value="${bindings['Mgr'].label}"/>
          </td>
          <td>
            <html:text property="Mgr"/>
          </td>
        </tr>
        <tr>
          <td>
            <c:out value="${bindings['Sal'].label}"/>
          </td>
          <td>
            <html:text property="Sal"/>
          </td>
        </tr>
        <tr>
          <td>
            <c:out value="${bindings['Deptno'].label}"/>
          </td>
          <td>
            <html:text property="Deptno"/>
          </td>
        </tr>
      </table>
      <br/>
      <input name="event_Search" type="submit" value="Go"/>
    </html:form>
  </P>
  <table border="1" cellspacing="0">
    <tr>
      <th>
        <c:out value="${bindings.EmpView1.labels['Empno']}"/>
      </th>
      <th>
        <c:out value="${bindings.EmpView1.labels['Ename']}"/>
      </th>
      <th>
        <c:out value="${bindings.EmpView1.labels['Sal']}"/>
      </th>
      <th>
        <c:out value="${bindings.EmpView1.labels['Deptno']}"/>
      </th>
    </tr>
    <c:forEach var="Row" items="${bindings.EmpView1.rangeSet}">
      <tr>
        <td>
          <c:out value="${Row['Empno']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['Ename']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['Sal']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['Deptno']}"/>&nbsp;
        </td>
      </tr>
    </c:forEach>
  </table>
  <html:form action="/BrowseEmployees.do">
    <input type="hidden" name="<c:out value='${bindings.statetokenid}'/>" value="<c:out value='${bindings.statetoken}'/>"/>
    <input type="submit" name="event_PreviousSet" value="PreviousSet" <c:out value="${bindings.PreviousSet.enabledString}" />/>
    <input type="submit" name="event_NextSet" value="NextSet" <c:out value="${bindings.NextSet.enabledString}" />/>
    <input type="submit" value="Download Results as CSV" name="event_DownloadCSV"/>
  </html:form>
</body>
</html>
