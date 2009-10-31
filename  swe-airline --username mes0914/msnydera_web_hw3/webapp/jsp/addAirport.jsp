<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add Airport</title>
	
	<style type="text/css">
		table.airportList {
			border-width: 1px 1px 1px 1px;
			border-spacing: 2px;
			border-style: none none none none;
			border-color: gray gray gray gray;
			border-collapse: separate;
			background-color: white;
		}
		table.airportList th {
			border-width: thin thin thin thin;
			padding: 2px 2px 2px 2px;
			border-style: inset inset inset inset;
			border-color: gray gray gray gray;
			background-color: white;
			-moz-border-radius: 0px 0px 0px 0px;
		}
		table.airportList thead {
			border-width: thin thin thin thin;
			padding: 2px 2px 2px 2px;
			border-style: inset inset inset inset;
			border-color: gray gray gray gray;
			background-color: white;
			-moz-border-radius: 0px 0px 0px 0px;
		}
		table.airportList td {
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
	<h1>Add Airport</h1>
	<c:if test="${not empty requestScope.error}">
 		<span style="color: red;">
 			<ul>
 				<li><c:out value="${requestScope.error}" escapeXml="false" /></li>
 			</ul>
 		</span>
 	
 	</c:if>
 	
 	<c:if test="${not empty requestScope.addedAirportCode}">
 		<span style="color: green;">
 			<ul>
 				<li>Successfully added the ${addedAirportCode} airport code.</li>
 			</ul>
 		</span>
 	</c:if>
 	
 	<b>Please enter the information to add an airport:</b>
 	
 	<form action="${basePath}/addAirport">
	 	Airport Code: <input type="text" name="airportCode"> <br /> 
	 	<input type="submit" value="Add Airport" />
 	</form>
 	 
 	<br />
 	<a href="${basePath}/jsp/headquartersMenu.jsp">Headquarters Menu</a><br>
	<a href="${basePath}/jsp/home.jsp">Main Menu</a>
	
	<h2>Existing Airports</h2>
	<c:if test="${not empty requestScope.airportCodes}">
		<table class="airportList">
			<th>Airport Code</th>
			<c:forEach var="airportCode" items="${requestScope.airportCodes}" >
				<tr><td>${airportCode}</td></tr>
			</c:forEach>
		</table>
 	</c:if>
 	<%--
	<display:table name="requestScope.airportCodes" cellspacing="10px" class="airportList">
		<display:column title="Airport Code" />
 	</display:table>
 	--%>
</body>
</html>