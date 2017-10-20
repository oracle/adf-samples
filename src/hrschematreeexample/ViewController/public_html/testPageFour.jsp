<%@ page contentType="text/html;charset=windows-1252" import="oracle.xml.parser.v2.XMLDocument" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>untitled</title>
</head>
<body>
  <html:errors/>
  
  <c:set var="voxml" value="${bindings.DepartmentXML.attributeValue}"/>
  <%
    ((XMLDocument)pageContext.getAttribute("voxml")).print(System.err);
  %>
  <a href="testPageFour.do?event=Previous">Previous</a>
  <a href="testPageFour.do?event=Next">Next</a>  
  <table border="1" cellpadding="2" cellspacing="0">
    <tr>
      <th align="left">Id</th>
      <th align="left">Name</th>
      <th align="left">&nbsp;</th>
    </tr>
    <x:forEach select="$voxml/DepartmentsViewRow">
      <tr>
        <td><x:out select="DepartmentId"/></td>
        <td><x:out select="DepartmentName"/></td>
        <td>
          <x:choose>
            <x:when select="EmployeesView/EmployeesViewRow">
              <table border="1" cellpadding="2" cellspacing="0">
                <tr>
                  <th align="left">Id</th>
                  <th align="left">Name</th>
                  <th align="left">Email</th>
                  <th align="left">Direct Reports</th>
                </tr>
                <x:forEach select="EmployeesView/EmployeesViewRow">
                  <tr>
                    <td><x:out select="EmployeeId"/></td>
                    <td><x:out select="FirstName"/> <x:out select="LastName"/></td>
                    <td><x:out select="Email"/></td>
                    <td>
                      <x:choose>
                        <x:when select="EmployeesView/EmployeesViewRow">
                          <table border="1" cellpadding="2" cellspacing="0">
                            <tr>
                              <th align="left">Id</th>
                              <th align="left">Name</th>
                              <th align="left">Email</th>
                            </tr>
                            <x:forEach select="EmployeesView/EmployeesViewRow">
                              <tr>
                                <td><x:out select="EmployeeId"/></td>
                                <td><x:out select="FirstName"/> <x:out select="LastName"/></td>
                                <td><x:out select="Email"/></td>
                              </tr>
                            </x:forEach>
                          </table>
                        </x:when>
                        <x:otherwise>
                          No Direct Reports
                        </x:otherwise>
                      </x:choose>
                    
                    </td>
                  </tr>
                </x:forEach>
              </table>
              <x:choose>
                <x:when select="JobHistoryView/JobHistoryViewRow">
                  <table border="1" cellpadding="2" cellspacing="0">
                    <tr>
                      <th align="left">Start Date</th>
                      <th align="left">End Date</th>
                    </tr>
                    <x:forEach select="JobHistoryView/JobHistoryViewRow">
                      <tr>
                        <td><x:out select="StartDate"/>&nbsp;</td>
                        <td><x:out select="EndDate"/>&nbsp;</td>
                      </tr>
                    </x:forEach>
                  </table>
                </x:when>
                <x:otherwise>
                  No Job History Data
                </x:otherwise>
              </x:choose>
            </x:when>
            <x:otherwise>
              No employees
            </x:otherwise>
          </x:choose>
        </td>
      </tr>
    </x:forEach>
  </table>  
  <form>
    <input type="submit" name="event_getXmlDocument" value="getXmlDocument" <c:out value="${bindings.getXmlDocument.enabledString}" />/>
  </form>
</body>
</html>

