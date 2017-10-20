<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <title>Test including TestPage.jsp</title>
  </head>
  <body>
    <html:errors/>
    <%--
    <table border="5">
      <tr>
        <td><jsp:include flush="false" page="pageThatIncludesTestPage.do"/></td>
      </tr>
    </table>
    --%>
     </table>
     <hr>
     <p>Other Stuff Here</p>
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
         <th>
           <c:out value="${bindings.Departments.labels['DepartmentXML']}"/>
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
           <td>
             <c:out value="${Row['DepartmentXML']}"/>&nbsp;
           </td>
         </tr>
       </c:forEach>
    <table border="5">
      <tr>
        <td><jsp:include flush="false" page="pageThatIncludesTestPage.do"/></td>
      </tr>
    </table>
     </table>
  </body>
</html>