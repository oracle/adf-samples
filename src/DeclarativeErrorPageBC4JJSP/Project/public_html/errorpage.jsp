<%@ page language = "java" isErrorPage="true" import = "java.util.*, java.io.*, oracle.jbo.*, oracle.jdeveloper.html.*, oracle.jbo.html.databeans.*, org.apache.struts.Globals" contentType="text/html;charset=windows-1252" %>
<%@ page import="oracle.jdeveloper.html.HTMLElement" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<html>
<head>
<META NAME="GENERATOR" CONTENT="Oracle JDeveloper">
<LINK REL=STYLESHEET TYPE="text/css" HREF="bc4j.css">
<TITLE><bean:message key="errorpage.title"/></TITLE>
</head>
<body>
<% // This page is also use to display errors from the Struts exception handler
   if (exception == null)
      exception = (Exception) request.getAttribute(Globals.EXCEPTION_KEY);
%>

<center><H2><bean:message key="errorpage.apperror"/></H2></center>

<div CLASS="errorText">
<bean:message key="errorpage.message"/> <%= HTMLElement.quote(exception.getMessage()) %>
<BR>
<%
   if (exception instanceof oracle.jbo.JboException)
   {
      oracle.jbo.JboException ex2 = (oracle.jbo.JboException)exception;
      Object[] details = ex2.getDetails();
      for (int i = 0; i < details.length; i++)
      {
         Object detail = details[i];
         Exception e = (Exception) detail;
%><H3>&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="errorpage.message"/> <%= HTMLElement.quote(e.getLocalizedMessage())%></H3>
<BR><%
      }
   } %>
<%-- Un-comment the following lines to display the stack trace
<PRE><%
      StringWriter writer = new StringWriter();
      PrintWriter prn = new PrintWriter(writer);

      exception.printStackTrace(prn);
      prn.flush();

      out.print(writer.toString()); %>
</PRE>
--%>
</div>
</body>
</html>
