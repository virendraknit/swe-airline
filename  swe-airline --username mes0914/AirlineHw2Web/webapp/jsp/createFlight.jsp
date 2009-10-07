<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Create Flight Page</title>
	<link type="text/css" rel="stylesheet" href="css/datepickercontrol.css"/>
	<script type="text/javascript" src="script/datepickercontrol.js" />
	<script type="text/javascript">
		function load(){
			DatePickerControl.init();
		}
	</script>
	<style type="text/css">
		table.allInfo {
			border-width: 1px 1px 1px 1px;
			border-spacing: 2px;
			border-style: none none none none;
			border-color: gray gray gray gray;
			border-collapse: separate;
			background-color: white;
		}
		table.allInfo th {
			border-width: thin thin thin thin;
			padding: 2px 2px 2px 2px;
			border-style: inset inset inset inset;
			border-color: gray gray gray gray;
			background-color: white;
			-moz-border-radius: 0px 0px 0px 0px;
		}
		table.allInfo td {
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
	<h1>Create Flight</h1>
	<c:if test="${not empty requestScope.error}">
 		<span style="color: red;">
 			<ul>
 				<li><c:out value="${requestScope.error}" escapeXml="false" /></li>
 			</ul>
 		</span>
 	</c:if>
 	
 	<c:if test="${not empty requestScope.addedFlight}">
 		<span style="color: green;">
 			<ul>
 				<li>Successfully added Flight Id ${addedFlight.id}.  Additional information about the flight can be seen in the "Existing Flights" table below.</li>
 			</ul>
 		</span>
 	</c:if>
 	
 	<h2>Please enter the information to create a flight:</h2>

 	<form action="${basePath}/createFlight">
 		<label>Select Departing Airport:</label>
	 	<select name="departureAirport" id="departureAirport">
	 		<c:forEach var="airport" items="${requestScope.airports}">
	 			<option>${airport}</option>
	 		</c:forEach>
	 	</select>
	 	<br />
	 	<br />
	 	
	 	<label>Select Destination Airport:</label>
	 	<select name="destinationAirport" id="destinationAirport">
	 		<c:forEach var="airport" items="${requestScope.airports}">
	 			<option>${airport}</option>
	 		</c:forEach>
	 	</select>
	 	<br />
	 	<br />
	 	
	 	<label>Departure Date (MM/DD/YYYY):</label>
		<input type="text" name="flightDate" size="10" id="flightDate" datepicker="true" datepicker_format="MM/DD/YYYY" readonly="readonly"/>
		<a href="javascript:void(0)" onclick="clearDate()">Clear Date</a>
	 	<br />
	 	<br />
	 	
	 	<label>Seat Cost:</label>
	 	&#36;<input type="text" name="cost" />
	 	<br />
	 	<br />
	 	
	 	<label>Select Airplane Id:</label>
	 	<select name="airplaneId" id="airplaneId">
	 		<c:forEach var="airplane" items="${requestScope.airplanes}">
	 			<option>${airplane.id}</option>
	 		</c:forEach>
	 	</select>
	 	<br />
	 	
	 	<input type="submit" value="Create Flight">
 	</form>
 	<br />
 	
	<a href="${basePath}/jsp/headquartersMenu.jsp">Headquarters Menu</a><br>
	<a href="${basePath}/jsp/home.jsp">Main Menu</a>
	
	<h4>Existing Flights</h4>
	<display:table name="requestScope.flights" cellspacing="10px" class="allInfo">
 		<display:column property="id" title="Flight #" />
 		<display:column property="departureAirportCode" title="Depart Code" />
 		<display:column property="destinationAirportCode" title="Dest Code" />
 		<display:column property="departureDate" format="{0,date,MM-dd-yyyy}" title="Flight Date" />
 		<display:column property="cost" format="$ {0,number,000.00}" title="Cost" />
 		<display:column property="availableSeats" title="Avail Seats" />
 		<display:column property="airplaneId" title="Airplane Id" />
 	</display:table>
	
	<h4>Airplane Descriptions</h4>
	<display:table name="requestScope.airplanes" cellspacing="10px" class="allInfo">
 		<display:column property="id" title="Airplane Id" />
 		<display:column property="type" title="Airplane Type" />
 		<display:column property="numSeats" title="Number of Seats" />
 	</display:table>
</body>
<script type="text/javascript">
	//load();

	function clearDate(){
		document.getElementById("flightDate").value = "";
	}
</script>
</html>