<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<html>
  <head>
	  <title>Login Page - Struts Based</title>
	</head>
	<body>
  <h3>Struts-based Login Form</h3>
  <c:if test="${not empty param.failed}">
    <b>Login Failed. Please Try Again.</b>
  </c:if>
    <form method="post" action="main.do" name="LoginForm">
	    <P>
	      Username:
		  <input name="form:username" size="15" maxlength="100">
	    </P>
	    <P>
	      Password: 
		  <input type="password" name="form:password" size="15" maxlength="100">
      </P>
      <P>
		  <input type="submit" value="login">
      <input type="hidden" name="form:_loginpage" value="yes">
	    </P>
	  </form>
 </body>
</html>
