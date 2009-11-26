<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>View Reservations</title>
	
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
	</style>
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
 	
	<h1>Existing Reservations</h1>
	<c:if test="${not empty requestScope.error}">
 		<span style="color: red;">
 			<ul>
 				<li><c:out value="${requestScope.error}" escapeXml="false" /></li>
 			</ul>
 		</span>
 	
 	</c:if>

	<display:table name="requestScope.reservations" cellspacing="10px" class="searchResults">
 		<display:column property="id" title="Reservation #" />
 		<display:column property="customer.name" title="Cust. Name" />
 		<display:column property="status" title="Status" />
 		<display:column property="numSeats" title="# Seats" />
 		<display:column property="flight.id" title="Flight #" />
 		<display:column property="flight.departureAirport.airportCode" title="Departure Code" />
 		<display:column property="flight.destinationAirport.airportCode" title="Destination Code" />
 		<display:column property="flight.departureDate" format="{0,date,MM-dd-yyyy}" title="Flight Date" />
 		<display:column property="totalCost" format="$ {0,number,000.00}" title="Cost" />
 	</display:table>
 	
 	<br />
 	<b>Please choose the reservation to view details for:</b>
 	<br />
 	<span style="font-style: italic;">(canceling reservations can be done from the details page)</span>
 	<br />
 	<br />
 	
 	<form action="${basePath}/showReservation">
 		Reservation #
 		<select name="reservationId">
 			<c:forEach var="reservation" items="${requestScope.reservations}">
 				<option>${reservation.id }</option>
			</c:forEach>
 		</select>
 	
	 	<input type="submit" value="Reservation Details" />
 	</form>
 	 
 	<br />
	<a href="${basePath}/jsp/home.jsp">Main Menu</a>
	
</body>
</html>