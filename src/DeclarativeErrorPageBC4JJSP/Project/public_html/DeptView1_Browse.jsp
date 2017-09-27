<%@ page language="java" import="oracle.jbo.*" errorPage="errorpage.jsp" contentType="text/html;charset=windows-1252" %>
<%@ taglib  uri="/webapp/DataTags.tld"  prefix="jbo" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<html>
<head>
<META NAME="GENERATOR" CONTENT="Oracle JDeveloper">
<LINK REL=STYLESHEET TYPE="text/css" HREF="bc4j.css">
<TITLE><bean:message key="browse.title"/></TITLE>
</head>
<body>

<h3><bean:message key="browse.header" arg0="DeptView1"/></h3>
<table border="0">
  <tr>
    <td ALIGN="right"><jbo:DataScroller datasource="TestModule.DeptView1" /></td>
  </tr>
  <tr>
    <td><jbo:DataTable datasource="TestModule.DeptView1" /></td>
  </tr>
</table>

</body>
</html>
