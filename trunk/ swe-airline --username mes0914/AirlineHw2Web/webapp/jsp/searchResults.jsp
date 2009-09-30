<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Search Results Page</title>
	
	<style type="text/css">
		table.searchResults {
			border-width: 1px 1px 1px 1px;
			border-spacing: 2px;
			border-style: none none none none;
			border-color: gray gray gray gray;
			border-collapse: separate;
			background-color: white;
		}
		table.searchResults th {
			border-width: thin thin thin thin;
			padding: 2px 2px 2px 2px;
			border-style: inset inset inset inset;
			border-color: gray gray gray gray;
			background-color: white;
			-moz-border-radius: 0px 0px 0px 0px;
		}
		table.searchResults td {
			border-width: thin thin thin thin;
			padding: 2px 2px 2px 2px;
			border-style: inset inset inset inset;
			border-color: gray gray gray gray;
			background-color: white;
			-moz-border-radius: 0px 0px 0px 0px;
		}
	/*
		.searchResults{
			border: 1px solid black;
		}
		
		.searchResults tr {
			border: 1px solid black;
			border-style: 
		}
		*/
	</style>
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
 	
<!-- 	// Unique Id of the flight-->
<!--	private int id;-->
<!--	private Date departureDate;-->
<!--	private String departureAirportCode;-->
<!--	private String destinationAirportCode;-->
<!---->
<!--	// The cost of the flight.-->
<!--	private double cost;-->
<!---->
<!--	// Unique Id of the airplane that is used for the flight-->
<!--	private int airplaneId;-->
<!---->
<!--	// Number of seats that are available for passengers on the flight.-->
<!--	private int availableSeats;-->
 	
 	<display:table name="requestScope.flights" cellspacing="10px" class="searchResults">
 		<display:column property="id" title="Flight #" />
 		<display:column property="departureAirportCode" title="Depart Code" />
 		<display:column property="destinationAirportCode" title="Dest Code" />
 		<display:column property="departureDate" format="{0,date,MM-dd-yyyy}" title="Flight Date" />
 		<display:column property="cost" format="$ {0,number,000.00}" title="Cost" />
 		<display:column property="availableSeats" title="Avail Seats" />
 		<display:column property="airplaneId" title="Airplane Id" />
 	</display:table>
 	
 	<table>
 		<tr>
 			<th>Flight #</th>
 			<th>Depart Code</th>
 			<th>Dest Code</th>
 			<th>Flight Date</th>
 			<th></th>
 			<th></th>
 			<th></th>
 		</tr>
 	
 	</table>
 	
<!--  	
	<c:forEach var="flight" items="${requestScope.flights}">
		<c:out value="${flight.id}"></c:out> <br />
	</c:forEach>
-->
	<br />
	<b>Please make your choices for a reservation:</b>
	<br />
 	<form action="${basePath}/reserveFlight">
 		Which Flight # would you like?
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