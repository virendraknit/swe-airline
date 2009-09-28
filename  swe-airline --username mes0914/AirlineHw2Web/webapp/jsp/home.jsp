<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Airline Home</title>
</head>
 <body>
 	<c:set var="basePath" value="${pageContext.request.contextPath}" />
 	
	<h1>Airline Application Home</h1>
 	<p>Please choose what you would like to do:</p>
	<a href="${basePath}/prepareSearch">Search and Reserve Flights</a><br>
	<a href="${basePath}/jsp/headquartersMenu.jsp">Create Airplanes, Airports, and/or Flights</a>
	
</body>
</html>