<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Airline Login</title>
</head>
 <body>
 	<c:set var="basePath" value="${pageContext.request.contextPath}" />
 	
	<h1>Airline Login</h1>
	<c:if test="${not empty requestScope.error}">
 		<span style="color: red;">
 			<ul>
 				<li><c:out value="${requestScope.error}" escapeXml="false" /></li>
 			</ul>
 		</span>
 	</c:if>
 	<p>Please Login:</p>
 	<form action="${basePath}/login">
	 	Username <input type="text" name="username" /> <br />
	 	Password <input type="password" name="password" /> <br />
	 	<input type="submit" value="Login" />
 	</form>
</body>
</html>