<%@ taglib uri="http://xmlns.oracle.com/adf/ui/jsp/adftags" prefix="adf"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>Upload Text and Image File</title>
</head>
<body>
  <html:errors/>
  <html:form action="/showAndUploadFiles.do">
    <input type="hidden" name="<c:out value='${bindings.statetokenid}'/>" value="<c:out value='${bindings.statetoken}'/>"/>
    <input type="submit" name="event_Previous" value="Previous" <c:out value="${bindings.Previous.enabledString}" />/>
    <input type="submit" name="event_Next" value="Next" <c:out value="${bindings.Next.enabledString}" />/>
  </html:form>
  <P>To upload a new text file, icon, and logical file name:</P>
  <OL>
    <LI>Click (Create)</LI>
    <LI>Enter logical file name, text file path, and image file path</LI>
    <LI>Then click (Save).</LI>
  </OL>
  <table border="1" cellspacing="0">
    <tr>
      <th>&nbsp;</th>
      <th>
        <c:out value="${bindings.UploadedFileView.labels['Name']}"/>
      </th>
      <th>
        <c:out value="${bindings.UploadedFileView.labels['Created']}"/>
      </th>
      <th>
        <c:out value="${bindings.UploadedFileView.labels['Modified']}"/>
      </th>     
    </tr>
    <c:forEach var="Row" items="${bindings.UploadedFileView.rangeSet}">
      <tr>
        <td>
          <c:out value="${Row.currencyString}"/>&nbsp;
        </td>
        <td>
          <adf:render model="Row.Icon"/>
          <c:out value="${Row['Name']}"/>&nbsp;
          (<adf:render model="Row.FileContents"/>)
        </td>
        <td>
          <c:out value="${Row['Created']}"/>&nbsp;
        </td>
        <td>
          <c:out value="${Row['Modified']}"/>&nbsp;
        </td>        
      </tr>
    </c:forEach>
  </table>
  <html:form action="/showAndUploadFiles.do">
    <input type="hidden" name="<c:out value='${bindings.statetokenid}'/>" value="<c:out value='${bindings.statetoken}'/>"/>
    <table border="0" cellpadding="2" cellspacing="0">
      <tr>
        <td align="right">Logical File Name:
        </td>
        <td>
          <html:text property="Name"/>
        </td>
      </tr>
      <tr>
        <td align="right">Text File Name to Upload:
        </td>
        <td>
          <html:file property="FileContents"/>
        </td>
      </tr>
        <td align="right">Icon/Image File Name to Upload:
        </td>
        <td>
          <html:file property="Icon"/>
        </td>        
      </tr>
    </table>
    <br/>
    <input type="submit" name="event_Create" value="Create" />
    <input type="submit" name="event_Delete" value="Delete" <c:out value="${bindings.Delete.enabledString}" />/>
    <input type="submit" name="event_Commit" value="Save" />
  </html:form>
</body>
</html>
