<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://xmlns.oracle.com/adf/ui/jsp/adftags" prefix="adf"%>
<adf:uimodelreference model="TestCrudUIModel" lifecycle="test.view.TestCrudLifecycle"/>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
    <form method="POST" action="TestCrudWithLifecycle.jsp">
      <input type="hidden" name="<c:out value='${bindings.statetokenid}'/>" value="<c:out value='${bindings.statetoken}'/>"/>
      <table border="0" width="100%" cellpadding="2" cellspacing="0">
        <tr>
          <td>
            <c:out value="${bindings['Id'].label}"/>
          </td>
          <td>
            <input type="text" name="<c:out value='${bindings.Id.path}'/>" value="<c:out value='${bindings.Id}'/>"/>
          </td>
        </tr>
        <tr>
          <td>
            <c:out value="${bindings['OptionOne'].label}"/>
          </td>
          <td><input type="CHECKBOX" value="Y" name="<c:out value='${bindings.OptionOne.path}'/>"
              <c:if test="${bindings.OptionOne == 'Y'}">checked</c:if>
              />
              
          </td>
        </tr>
        <tr>
          <td>
            <c:out value="${bindings['OptionTwo'].label}"/>
          </td>
          <td><input type="CHECKBOX" value="Y" name="<c:out value='${bindings.OptionTwo.path}'/>"
              <c:if test="${bindings.OptionTwo == 'Y'}">checked</c:if>
              />
          </td>
        </tr>
      </table>
      <br/>
      <input type="submit" name="event_Commit" value="Commit" />
      <input type="submit" name="event_Previous" value="Previous" <c:out value="${bindings.Previous.enabledString}" />/>
      <input type="submit" name="event_Next" value="Next" <c:out value="${bindings.Next.enabledString}" />/>
      <input type="submit" name="event_Create" value="Create" <c:out value="${bindings.Create.enabledString}" />/>
    </form>
  </body>
</html>
