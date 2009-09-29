<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Search Results Page</title>
</head>
<body>
	<c:set var="basePath" value="${pageContext.request.contextPath}" />
	<h1>Available Flights</h1>
	<c:if test="${not empty requestScope.error}">
 		<span style="color: red;">
 			<ul>
 				<li>${error}</li>
 			</ul>
 		</span>
 	
 	</c:if>
 	
	<c:forEach var="flight" items="${requestScope.flights}">
		<c:out value="${flight.id}"></c:out> <br />
	</c:forEach>
	
 	<form action="${basePath}/reserveFlight">
 		<select name="flightId">
 			<c:forEach var="flight" items="${requestScope.flights}">
 				<option>${flight.id }</option>
			</c:forEach>
 		</select>
 		<br />
 		<label>Number of Seats</label>
 		<input type="text" name="numSeats" />
 		<br />
 		<input type="submit" value="Reserve Seats" />
 	</form>
 	<br />
 	<a href="${basePath}/prepareSearch">Run New Search</a><br>
	<a href="${basePath}/jsp/home.jsp">Main Menu</a>
</body>
</html>