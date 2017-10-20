<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <title>Test including TestPage.jsp</title>
  </head>
  <body>
    <table border="5">
      <tr>
        <td><jsp:include flush="false" page="testPage.do"/></td>
      </tr>
    </table>
  </body>
</html>
