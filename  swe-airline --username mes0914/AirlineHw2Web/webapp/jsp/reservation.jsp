<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Reservation Page</title>
	<style type="text/css">
		.labelHeading{
			font-weight: bold;
		}
	</style>
</head>
<body>
	<c:set var="basePath" value="${pageContext.request.contextPath}" />
	<h1>Reservation Information</h1>
	<table>
		<tr>
			<td class="labelHeading">Reservation #:</td>
			<td>${requestScope.reservation.id}</td>
		</tr>
		<tr>
			<td class="labelHeading">Number Seats Reserved:</td>
			<td>${requestScope.reservation.numSeats}</td>
		</tr>
		<tr>
			<td class="labelHeading">Flight #:</td>
			<td>${requestScope.reservation.flight.id}</td>
		</tr>
		<tr>
			<td class="labelHeading">Departing Airport Code:</td>
			<td>${requestScope.reservation.flight.departureAirportCode}</td>
		</tr>
		<tr>
			<td class="labelHeading">Destination Airport Code:</td>
			<td>${requestScope.reservation.flight.destinationAirportCode}</td>
		</tr>
		<tr>
			<td class="labelHeading">Flight Date:</td>
			<td>${requestScope.reservation.flight.displayDate}</td>
		</tr>
		<tr>
			<td class="labelHeading">Total Cost:</td>
			<td>&#36;${requestScope.reservation.totalCost}</td>
		</tr>
	</table>
			
<%--			
	<span class="labelHeading">Reservation #:</span> ${requestScope.reservation.id} <br />
	<span class="labelHeading">Number Seats Reserved:</span> ${requestScope.reservation.numSeats} <br />
	<span class="labelHeading">Flight #:</span> ${requestScope.reservation.flight.id} <br />
	<span class="labelHeading">Departing Airport Code:</span> ${requestScope.reservation.flight.departureAirportCode} <br />
	<span class="labelHeading">Destination Airport Code:</span> ${requestScope.reservation.flight.destinationAirportCode} <br />
	<span class="labelHeading">Flight Date:</span> ${requestScope.reservation.flight.displayDate} <br />
	<span class="labelHeading">Total Cost:</span> &#36;${requestScope.reservation.totalCost} <br />
--%>
			
 	<br />
 	<a href="${basePath}/prepareSearch">Run New Search</a><br>
	<a href="${basePath}/jsp/home.jsp">Main Menu</a>
</body>
</html>