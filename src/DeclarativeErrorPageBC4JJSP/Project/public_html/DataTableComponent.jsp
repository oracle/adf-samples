<%@ page language="java" import = "oracle.jbo.html.*, oracle.jbo.*" %>
<%@ taglib uri="/webapp/DataTags.tld" prefix="jbo" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%-- This JSP component display a table bound to a datasource and optionally
     links to an edit form.
     It is called by the DataTable tag --%>
<%
   String editTargetParam = request.getParameter("edittarget");
   String dsParam = request.getParameter("datasource");
%>
<%-- Restore the data binding to the datasource passed as parameter --%>
<jbo:ViewObject id="voBrowse" name='<%=dsParam%>' />

<%-- Display the current range of rows in a grid with a column for each attribute --%>
<table class="clsTable" cellspacing="1" cellpadding="3" border="0">
  <tr class="clsTableRow"><%
  if (editTargetParam != null)
  {
%>
    <th class="clsTableHeader">&nbsp;</th>
    <th class="clsTableHeader"><a href="<jbo:UrlEvent targeturlparam='edittarget' event='create' datasource='<%=dsParam%>' extraparameters='<%="originURL=" + request.getParameter("originURL")%>'/>"><bean:message key="DataTable.new" /></a></th><%
  }
%>      
  <%-- Display all the attribute names in the table header --%>
  <jbo:AttributeIterate id="df" datasource="<%=dsParam%>">
    <th class="clsTableHeader" title="<jbo:ShowHint hintname='TOOLTIP'></jbo:ShowHint>" ><jbo:ShowHint hintname="LABEL"></jbo:ShowHint></th>
  </jbo:AttributeIterate>
  </tr><%
  // Retrieve the current row to match it again all the other row of the range
  // and display it in a different color.
  Row currentRow = voBrowse.getCurrentRow();
%>
  <%-- Iterate through all the rows in the range without changing the currency --%>
  <jbo:RowsetIterate datasource="<%=dsParam%>" changecurrentrow="false" userange="true">
  <jbo:Row id="aRow" datasource="<%=dsParam%>" action="Active"/><%
  String rowStyle;
  if (aRow == currentRow)
     rowStyle = "clsCurrentTableRow";
  else
     rowStyle = "clsTableRow";
%>
  <tr class="<%=rowStyle%>"><%
  if (editTargetParam != null)
  {
%>
    <td> <a href="<jbo:UrlEvent targeturlparam='edittarget' event='delete' datasource='<%=dsParam%>' addrowkey='true'/>"><bean:message key="DataTable.delete" /></a> </td>
    <td> <a href="<jbo:UrlEvent targeturlparam='edittarget' event='edit' datasource='<%=dsParam%>' addrowkey='true' extraparameters='<%="originURL=" + request.getParameter("originURL")%>'/>"><bean:message key="DataTable.edit" /></a> </td><%
  }
%><%-- Iterate through all the attribute of the row and Render their value --%>
  <jbo:AttributeIterate id="dfv" datasource="<%=dsParam%>">
    <td title="<jbo:ShowHint hintname='TOOLTIP'></jbo:ShowHint>" > <jbo:RenderValue datasource="<%=dsParam%>"/> </td>
  </jbo:AttributeIterate>
  </tr>
  </jbo:RowsetIterate>
</table>
