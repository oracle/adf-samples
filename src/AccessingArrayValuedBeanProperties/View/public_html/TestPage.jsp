<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://xmlns.oracle.com/adf/ui/jsp/adftags" prefix="adf"%>
<adf:uimodelreference model="TestPageUIModel"/>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>untitled</title>
  </head>
  <body>
    <table border="1" width="100%">
      <tr>
        <th>First Name</th>
        <th>Surname</th>
        <th>Description</th>
      </tr>
      <c:forEach var="Row" items="${bindings.findNominalRecords1.rangeSet}">
        <tr>
          <td valign="top">
            <c:out value="${Row.firstname}"/>&nbsp;
          </td>
          <td valign="top">
            <c:out value="${Row.surname}"/>&nbsp;
          </td>
          <td>
            <table>
              <c:forEach varStatus="status" var="curCode"
                         items="${Row.row.dataProvider.descriptionCode}">
                <tr>
                  <td>
                    <c:out value="${curCode}"/>
                  </td>
                  <td>
                    <c:out value="${Row.row.dataProvider.descriptionText[status.index]}"/>
                  </td>
                </tr>
              </c:forEach>
            </table>
          </td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>
