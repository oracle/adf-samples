<%@ page language="java" import = "oracle.jbo.html.*" %>
<%@ taglib  uri="/webapp/DataTags.tld"  prefix="jbo" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%-- This JSP component display a toolbar use to navigate through the RowSet
     by scrolling of the datasource range of rows.
     It is called by the DataRecord tag --%>
<%
   String targetURL = request.getParameter("targetURL");
   String dsParam = request.getParameter("datasource");
%>
<jbo:ViewObject id="voScroll" name='<%=dsParam%>' />
<bean:define id="scroller" type="oracle.jbo.html.jsp.datatags.DataScroller" name="jboScroller" scope="session"/>
<% String v1 = "window.self.location.href = '";
   String v2 = "&value=' + this.options[this.selectedIndex].value"; %>

<table class="clsScroller" cellspacing="0" cellpadding="3">
<tr>
<% if (scroller.getRangeStart() > 1)
   {
%>  <td><a href="<jbo:UrlEvent targeturlparam='targetURL' event='previousSet' datasource='<%=dsParam%>' />"><bean:message key="DataScroller.previous" /></a></td><%
   }
   else
   { 
%>  <td><bean:message key="DataScroller.previous" /></td><%
   }
%>
<form name="form1" style="margin:0px" method="GET" action="<%=targetURL%>">
<td align="center">
<select onchange="<%=v1%><jbo:UrlEvent targeturlparam='targetURL' event='gotoSet' datasource='<%=dsParam%>'/><%=v2%>"
        onfocus="this._lastValue = this.selectedIndex">
<% for (int i=0; i<scroller.getSteps(); i++)
   { %>
<option <%=scroller.getSelected(i)%> value="<%=String.valueOf(scroller.getValue(i))%>"><bean:message key="DataScroller.step" arg0="<%=String.valueOf(scroller.getStart(i))%>" arg1="<%= String.valueOf(scroller.getEnd(i))%>" arg2="<%=String.valueOf(scroller.getTotalCount())%>"/></option>
<% } %>
</select>
    </td>
    </form>
<%
   if (scroller.getLeftOver() > 0)
   {
%>  <td><a href="<jbo:UrlEvent targeturlparam='targetURL' event='nextSet' datasource='<%=dsParam%>' />"><bean:message key="DataScroller.next" /></a></td><%
   }
   else
   {
%>  <td><bean:message key="DataScroller.next" /></td><%
   } %>
</tr>
</table>







