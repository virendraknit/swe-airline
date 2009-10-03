<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add Airplane</title>
	
	<style type="text/css">
		table.airplaneList {
			border-width: 1px 1px 1px 1px;
			border-spacing: 2px;
			border-style: none none none none;
			border-color: gray gray gray gray;
			border-collapse: separate;
			background-color: white;
		}
		table.airplaneList th {
			border-width: thin thin thin thin;
			padding: 2px 2px 2px 2px;
			border-style: inset inset inset inset;
			border-color: gray gray gray gray;
			background-color: white;
			-moz-border-radius: 0px 0px 0px 0px;
		}
		table.airplaneList td {
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
	<h1>Add Airplane</h1>
	<c:if test="${not empty requestScope.error}">
 		<span style="color: red;">
 			<ul>
 				<li>${error}</li>
 			</ul>
 		</span>
 	
 	</c:if>
 	
 	<c:if test="${not empty requestScope.addedAirplane}">
 		<span style="color: green;">
 			<ul>
 				<li>Successfully added the ${addedAirplane.type} airplane with ${addedAirplane.numSeats} seats.</li>
 			</ul>
 		</span>
 	</c:if>
 	
 	<b>Please enter the information to add an airplane:</b>
 	
 	<form action="${basePath}/addAirplane">
	 	Airplane Type: <input type="text" name="airplaneType"> <br /> 
	 	Number of Seats: <input type="text" name="numSeats"> <br />
	 	<input type="submit" value="Add Airplane" />
 	</form>
 	 
 	<br />
 	<a href="${basePath}/jsp/headquartersMenu.jsp">Headquarters Menu</a><br>
	<a href="${basePath}/jsp/home.jsp">Main Menu</a>
	
	<h2>Existing Airplanes</h2>
	<display:table name="requestScope.airplanes" cellspacing="10px" class="airplaneList">
 		<display:column property="id" title="Airplane Id" />
 		<display:column property="type" title="Airplane Type" />
 		<display:column property="numSeats" title="Number of Seats" />
 	</display:table>
</body>
</html>