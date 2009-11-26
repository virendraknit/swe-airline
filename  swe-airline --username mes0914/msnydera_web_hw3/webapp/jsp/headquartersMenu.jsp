<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Airline Headquarters</title>
</head>
<body>
	<c:set var="basePath" value="${pageContext.request.contextPath}" />
	
	<%--
	<c:choose>
 		<c:when test="${sessionScope.currentUser != null}">
	 		${sessionScope.currentUser.username} |
	 		<a href="${basePath}/logOff">Log Out</a><br /> 
	 	</c:when>
	 	<c:otherwise>
	 		<a href="${basePath}/jsp/login.jsp">Log In</a><br />
	 	</c:otherwise>
 	</c:choose>
 	--%>
 	
	<h1>Airline Headquarters</h1>
	
	<c:if test="${not empty requestScope.error}">
 		<span style="color: red;">
 			<ul>
 				<li><c:out value="${requestScope.error}" escapeXml="false" /></li>
 			</ul>
 		</span>
 	</c:if>
 	
 	<p>Welcome to the Airline Headquarters.  Please choose what you would like to do:</p>
	<a href="${basePath}/prepareAddAirplane">Add Airplane</a><br>
	<a href="${basePath}/prepareAddAirport">Add Airport</a><br>
	<a href="${basePath}/prepareCreateFlight">Create Flight</a><br>
	
	<c:choose>
		<c:when test="${not empty requestScope.error}">
			<a href="jsp/home.jsp">Main Menu</a>
		</c:when>
		<c:otherwise>
			<a href="home.jsp">Main Menu</a>
		</c:otherwise>
	</c:choose>
</body>
</html>