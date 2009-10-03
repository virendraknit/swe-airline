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
	
	<h1>Airline Headquarters</h1>
 	<p>Welcome to the Airline Headquarters.  Please choose what you would like to do:</p>
	<a href="${basePath}/prepareAddAirplane">Add Airplane</a><br>
	<a href="addAirport">Add Airport</a><br>
	<a href="createFlight">Create Flight</a><br>
	<a href="home.jsp">Main Menu</a>
</body>
</html>