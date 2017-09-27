<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://xmlns.oracle.com/adf/ui/jsp/adftags" prefix="adf"%>
<html>
  <head>
	  <title>Login Page - Model 1 / No Controller</title>
	</head>
	<body>
  <h3>Model 1 / No Controller Login Form</h3>
  <c:if test="${not empty param.failed}">
    <b>Login Failed. Please Try Again.</b>
  </c:if>
    <form method="post" action="main.jsp" name="LoginForm">
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